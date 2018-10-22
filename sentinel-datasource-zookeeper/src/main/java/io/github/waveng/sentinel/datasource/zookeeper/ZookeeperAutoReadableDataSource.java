package io.github.waveng.sentinel.datasource.zookeeper;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.curator.framework.recipes.cache.ChildData;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.cache.NodeCacheListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.csp.sentinel.concurrent.NamedThreadFactory;
import com.alibaba.csp.sentinel.datasource.AbstractDataSource;
import com.alibaba.csp.sentinel.datasource.Converter;
import com.alibaba.csp.sentinel.transport.config.TransportConfig;
import com.alibaba.csp.sentinel.util.AppNameUtil;
import com.alibaba.csp.sentinel.util.HostNameUtil;
import com.alibaba.csp.sentinel.util.StringUtil;

import io.github.waveng.sentinel.datasource.NodeType;
import io.github.waveng.sentinel.datasource.zookeeper.util.Util;

/**
 * A read-only {@code DataSource} with ZooKeeper backend.
 *
 */
public class ZookeeperAutoReadableDataSource<T> extends AbstractDataSource<String, T> {

    private static Logger logger = LoggerFactory.getLogger(ZookeeperAutoReadableDataSource.class);
            
    private final String nodeType;
    
    private final ExecutorService pool = new ThreadPoolExecutor(1, 1, 0, TimeUnit.MILLISECONDS,
        new ArrayBlockingQueue<Runnable>(1), new NamedThreadFactory("sentinel-zookeeper-ds-update"),
        new ThreadPoolExecutor.DiscardOldestPolicy());

    private NodeCacheListener listener;
    private final String path;

    private ZkClient zkClient = null;
    private NodeCache nodeCache = null;

    public ZookeeperAutoReadableDataSource(final NodeType nodeType, final String dataId, Converter<String, T> parser) {
        super(parser);
        if (StringUtil.isBlank(dataId)) {
            throw new IllegalArgumentException(String.format("Bad argument: dataId=[%s]", dataId));
        }
        this.path = Util.getPath(AppNameUtil.getAppName() + "/" + HostNameUtil.getIp() + ":" + TransportConfig.getPort(), dataId);
        this.nodeType = nodeType.toString();
        
        init();
    }

    /**
     * This constructor is Nacos-style.
     */
    public ZookeeperAutoReadableDataSource(final NodeType nodeType, final String groupId, final String dataId,
                               Converter<String, T> parser) {
        super(parser);
        if (StringUtil.isBlank(groupId) || StringUtil.isBlank(dataId)) {
            throw new IllegalArgumentException(String.format("Bad argument: groupId=[%s], dataId=[%s]", groupId, dataId));
        }
        this.path = Util.getPath(groupId  + "/" + HostNameUtil.getIp() + ":" + TransportConfig.getPort(), dataId);
        this.nodeType = nodeType.toString();
        
        init();
    }

    private void init() {
        initZookeeperr();
        loadInitialConfig();
    }

    private void loadInitialConfig() {
        try {
            T newValue = loadConfig();
            if (newValue == null) {
                logger.warn("[Sentinel][ZookeeperAutoReadableDataSource] WARN: initial config is null, you may have to check your data source");
            }
            getProperty().updateValue(newValue);
        } catch (Exception ex) {
            logger.warn("[Sentinel][ZookeeperAutoReadableDataSource] Error when loading initial config", ex);
        }
    }

    private void initZookeeperr() {
        try {
            this.zkClient = ZkClientFactory.getZkClient();
            if (this.zkClient.checkExists(this.path) == null) {
                this.zkClient.createForPath(this.path);
            }
            
            String typeAllPath = Util.getTypePath(AppNameUtil.getAppName(), HostNameUtil.getIp(), TransportConfig.getPort(), nodeType);
            if (this.zkClient.checkExists(typeAllPath) == null) {
                this.zkClient.createForPath(typeAllPath, this.path.getBytes());
            }
            this.zkClient.forPath(typeAllPath, this.path.getBytes());
            initZookeeperListener();
        } catch (Exception e) {
            logger.warn("[Sentinel][ZookeeperAutoReadableDataSource] Error occurred when initializing Zookeeper data source", e);
            e.printStackTrace();
        }
    }
    
    private void initZookeeperListener() {
        try {

            this.listener = new NodeCacheListener() {
                @Override
                public void nodeChanged() {
                    String configInfo = null;
                    ChildData childData = nodeCache.getCurrentData();
                    if (null != childData && childData.getData() != null) {

                        configInfo = new String(childData.getData());
                    }
                    logger.info(String.format("[Sentinel][ZookeeperAutoReadableDataSource] New property value received for (%s): %s", path, configInfo));
                    T newValue = ZookeeperAutoReadableDataSource.this.parser.convert(configInfo);
                    // Update the new value to the property.
                    getProperty().updateValue(newValue);
                }
            };

            this.nodeCache = new NodeCache(this.zkClient.getZkClient(), this.path);
            this.nodeCache.getListenable().addListener(this.listener, this.pool);
            this.nodeCache.start();
            this.zkClient.addNodeCaches(nodeCache);
        } catch (Exception e) {
            logger.warn("[Sentinel][ZookeeperAutoReadableDataSource] Error occurred when initializing Zookeeper data source", e);
            e.printStackTrace();
        }
    }

    @Override
    public String readSource() throws Exception {
        if (this.zkClient == null) {
            throw new IllegalStateException("Zookeeper has not been initialized or error occurred");
        }
        byte[] data = this.zkClient.forPath(this.path);
        if (data != null) {
            return new String(data);
        }
        return null;
    }

    @Override
    public void close() throws Exception {
        if (this.nodeCache != null) {
            this.nodeCache.getListenable().removeListener(listener);
            this.nodeCache.close();
        }
        if (this.zkClient != null) {
            this.zkClient.close();
        }
        pool.shutdown();
    }

}
