package cn.sumpay.sentinel.datasource.zookeeper;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.alibaba.csp.sentinel.concurrent.NamedThreadFactory;
import com.alibaba.csp.sentinel.datasource.AbstractDataSource;
import com.alibaba.csp.sentinel.datasource.Converter;
import com.alibaba.csp.sentinel.log.RecordLog;
import com.alibaba.csp.sentinel.util.StringUtil;

import cn.sumpay.sentinel.datasource.zookeeper.util.Util;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.ChildData;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.cache.NodeCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;

/**
 * A read-only {@code DataSource} with ZooKeeper backend.
 *
 */
public class ZookeeperReadableDataSource<T> extends AbstractDataSource<String, T> {

    private static final int RETRY_TIMES = 3;
    private static final int SLEEP_TIME = 1000;

    private final ExecutorService pool = new ThreadPoolExecutor(1, 1, 0, TimeUnit.MILLISECONDS,
        new ArrayBlockingQueue<Runnable>(1), new NamedThreadFactory("sentinel-zookeeper-ds-update"),
        new ThreadPoolExecutor.DiscardOldestPolicy());

    private NodeCacheListener listener;
    private final String path;

    private CuratorFramework zkClient = null;
    private NodeCache nodeCache = null;

    public ZookeeperReadableDataSource(final String serverAddr, final String path, Converter<String, T> parser) {
        super(parser);
        if (StringUtil.isBlank(serverAddr) || StringUtil.isBlank(path)) {
            throw new IllegalArgumentException(String.format("Bad argument: serverAddr=[%s], path=[%s]", serverAddr, path));
        }
        this.path = Util.getPath(path);

        init(serverAddr);
    }

    /**
     * This constructor is Nacos-style.
     */
    public ZookeeperReadableDataSource(final String serverAddr, final String groupId, final String dataId,
                               Converter<String, T> parser) {
        super(parser);
        if (StringUtil.isBlank(serverAddr) || StringUtil.isBlank(groupId) || StringUtil.isBlank(dataId)) {
            throw new IllegalArgumentException(String.format("Bad argument: serverAddr=[%s], groupId=[%s], dataId=[%s]", serverAddr, groupId, dataId));
        }
        this.path = Util.getPath(groupId, dataId);

        init(serverAddr);
    }

    private void init(final String serverAddr) {
        initZookeeperr(serverAddr);
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

    private void initZookeeperr(final String serverAddr) {
        try {
            this.zkClient = CuratorFrameworkFactory.newClient(serverAddr, new ExponentialBackoffRetry(SLEEP_TIME, RETRY_TIMES));
            this.zkClient.start();
            Stat stat = this.zkClient.checkExists().forPath(this.path);
            if (stat == null) {
                this.zkClient.create().creatingParentContainersIfNeeded().withMode(CreateMode.PERSISTENT).forPath(this.path, null);
            }
            initZookeeperListener(serverAddr);
        } catch (Exception e) {
            RecordLog.warn("[ZookeeperDataSource] Error occurred when initializing Zookeeper data source", e);
            e.printStackTrace();
        }
    }
    
    private void initZookeeperListener(final String serverAddr) {
        try {

            this.listener = new NodeCacheListener() {
                @Override
                public void nodeChanged() {
                    String configInfo = null;
                    ChildData childData = nodeCache.getCurrentData();
                    if (null != childData && childData.getData() != null) {

                        configInfo = new String(childData.getData());
                    }
                    RecordLog.info(String.format("[ZookeeperDataSource] New property value received for (%s, %s): %s",
                        serverAddr, path, configInfo));
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
