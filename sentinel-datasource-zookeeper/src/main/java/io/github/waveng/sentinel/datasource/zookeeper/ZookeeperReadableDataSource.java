package io.github.waveng.sentinel.datasource.zookeeper;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.ChildData;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.cache.NodeCacheListener;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;

import com.alibaba.csp.sentinel.concurrent.NamedThreadFactory;
import com.alibaba.csp.sentinel.datasource.AbstractDataSource;
import com.alibaba.csp.sentinel.datasource.Converter;
import com.alibaba.csp.sentinel.log.RecordLog;
import com.alibaba.csp.sentinel.util.StringUtil;

import io.github.waveng.sentinel.datasource.zookeeper.util.Util;

/**
 * A read-only {@code DataSource} with ZooKeeper backend.
 *
 */
public class ZookeeperReadableDataSource<T> extends AbstractDataSource<String, T> {

    private final ExecutorService pool = new ThreadPoolExecutor(1, 1, 0, TimeUnit.MILLISECONDS,
        new ArrayBlockingQueue<Runnable>(1), new NamedThreadFactory("sentinel-zookeeper-ds-update"),
        new ThreadPoolExecutor.DiscardOldestPolicy());

    private NodeCacheListener listener;
    private final String path;

    private CuratorFramework zkClient = null;
    private NodeCache nodeCache = null;

    public ZookeeperReadableDataSource(final String path, Converter<String, T> parser) {
        super(parser);
        if (StringUtil.isBlank(path)) {
            throw new IllegalArgumentException(String.format("Bad argument: path=[%s]", path));
        }
        this.path = Util.getPath(path);

        init();
    }

    /**
     * This constructor is Nacos-style.
     */
    public ZookeeperReadableDataSource(final String groupId, final String dataId,
                               Converter<String, T> parser) {
        super(parser);
        if (StringUtil.isBlank(groupId) || StringUtil.isBlank(dataId)) {
            throw new IllegalArgumentException(String.format("Bad argument: groupId=[%s], dataId=[%s]", groupId, dataId));
        }
        this.path = Util.getPath(groupId, dataId);

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
                RecordLog.warn("[ZookeeperDataSource] WARN: initial config is null, you may have to check your data source");
            }
            getProperty().updateValue(newValue);
        } catch (Exception ex) {
            RecordLog.warn("[ZookeeperDataSource] Error when loading initial config", ex);
        }
    }

    private void initZookeeperr() {
        try {
            this.zkClient = ZkClient.zkClient();
            Stat stat = this.zkClient.checkExists().forPath(this.path);
            if (stat == null) {
                this.zkClient.create().creatingParentContainersIfNeeded().withMode(CreateMode.PERSISTENT).forPath(this.path, null);
            }
            initZookeeperListener();
        } catch (Exception e) {
            RecordLog.warn("[ZookeeperDataSource] Error occurred when initializing Zookeeper data source", e);
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
                    RecordLog.info(String.format("[ZookeeperDataSource] New property value received for (%s): %s", path, configInfo));
                    T newValue = ZookeeperReadableDataSource.this.parser.convert(configInfo);
                    // Update the new value to the property.
                    getProperty().updateValue(newValue);
                }
            };

            this.nodeCache = new NodeCache(this.zkClient, this.path);
            this.nodeCache.getListenable().addListener(this.listener, this.pool);
            this.nodeCache.start();
        } catch (Exception e) {
            RecordLog.warn("[ZookeeperDataSource] Error occurred when initializing Zookeeper data source", e);
            e.printStackTrace();
        }
    }

    @Override
    public String readSource() throws Exception {
        if (this.zkClient == null) {
            throw new IllegalStateException("Zookeeper has not been initialized or error occurred");
        }
        byte[] data = this.zkClient.getData().forPath(this.path);
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
