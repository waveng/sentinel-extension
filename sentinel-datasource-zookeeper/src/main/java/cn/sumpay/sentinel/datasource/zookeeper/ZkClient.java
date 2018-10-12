package cn.sumpay.sentinel.datasource.zookeeper;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

import com.alibaba.csp.sentinel.log.RecordLog;

import cn.sumpay.sentinel.datasource.zookeeper.config.ZkRuleConfig;

public class ZkClient {
    private static final int RETRY_TIMES = 3;
    private static final int SLEEP_TIME = 1000;
    private static final String NAMESPACE = "sentinel";
    
    private static CuratorFramework zkClient = null;
    static{
        zkClient = initZookeeperr(ZkRuleConfig.getRemoteAddress());
    }
    
    public static CuratorFramework initZookeeperr(final String serverAddr) {
        try {
            CuratorFramework  client = CuratorFrameworkFactory.builder().
            connectString(serverAddr).
            retryPolicy(new ExponentialBackoffRetry(SLEEP_TIME, RETRY_TIMES)).
            namespace( NAMESPACE ).
            build();
            
            client.start();
            return client;
        } catch (Exception e) {
            RecordLog.warn("[ZookeeperDataSource] Error occurred when initializing Zookeeper data source", e);
            e.printStackTrace();
        }
        return null;
    }

    public static CuratorFramework zkClient() {
        return zkClient;
    }
    
}
