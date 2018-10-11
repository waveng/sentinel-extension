package cn.sumpay.sentinel.datasource.zookeeper;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;

import com.alibaba.csp.sentinel.datasource.Converter;
import com.alibaba.csp.sentinel.log.RecordLog;
import com.alibaba.csp.sentinel.util.StringUtil;

import cn.sumpay.sentinel.datasource.AbstractWritableDataSource;
import cn.sumpay.sentinel.datasource.zookeeper.util.Util;

public class ZookeeperWritableDataSource<T, S> extends AbstractWritableDataSource<T, S> {

    private static final int RETRY_TIMES = 3;
    private static final int SLEEP_TIME = 1000;
    
    private final String path;
    
    

    private CuratorFramework zkClient = null;

    public ZookeeperWritableDataSource(final String serverAddr, final String path, Converter<T, S> parser) {
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
    public ZookeeperWritableDataSource(final String serverAddr, final String groupId, final String dataId,
                               Converter<T, S> parser) {
        super(parser);
        if (StringUtil.isBlank(serverAddr) || StringUtil.isBlank(groupId) || StringUtil.isBlank(dataId)) {
            throw new IllegalArgumentException(String.format("Bad argument: serverAddr=[%s], groupId=[%s], dataId=[%s]", serverAddr, groupId, dataId));
        }
        this.path = Util.getPath(groupId, dataId);
    
        init(serverAddr);
    }

    private void init(final String serverAddr) {
        initZookeeper(serverAddr);
    }

    private void initZookeeper(final String serverAddr) {
        try {
    
            this.zkClient = CuratorFrameworkFactory.newClient(serverAddr, new ExponentialBackoffRetry(SLEEP_TIME, RETRY_TIMES));
            this.zkClient.start();
            Stat stat = this.zkClient.checkExists().forPath(this.path);
            if (stat == null) {
                this.zkClient.create().creatingParentContainersIfNeeded().withMode(CreateMode.PERSISTENT).forPath(this.path, null);
            }
        } catch (Exception e) {
            RecordLog.warn("[ZookeeperDataSource] Error occurred when initializing Zookeeper data source", e);
            e.printStackTrace();
        }
    }
    
    @Override
    public void doWrite(S rule) throws Exception {
        zkClient.setData().forPath(path, (byte[]) rule);
    }

    @Override
    public void close() throws Exception {
        if (this.zkClient != null) {
            this.zkClient.close();
        }
    }

}
