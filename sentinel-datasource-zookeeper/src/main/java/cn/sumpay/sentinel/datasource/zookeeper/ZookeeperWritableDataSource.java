package cn.sumpay.sentinel.datasource.zookeeper;

import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;

import com.alibaba.csp.sentinel.datasource.Converter;
import com.alibaba.csp.sentinel.log.RecordLog;
import com.alibaba.csp.sentinel.util.StringUtil;

import cn.sumpay.sentinel.datasource.AbstractWritableDataSource;
import cn.sumpay.sentinel.datasource.zookeeper.util.Util;

public class ZookeeperWritableDataSource<T, S> extends AbstractWritableDataSource<T, S> {
    
    private final String path;
    
    

    private CuratorFramework zkClient = null;

    public ZookeeperWritableDataSource(final String path, Converter<T, S> parser) {
        super(parser);
        if (StringUtil.isBlank(path)) {
            throw new IllegalArgumentException(String.format("Bad argument:  path=[%s]", path));
        }
        this.path = Util.getPath(path);
        initZookeeper();
    }
    
    /**
     * This constructor is Nacos-style.
     */
    public ZookeeperWritableDataSource(final String groupId, final String dataId,
                               Converter<T, S> parser) {
        super(parser);
        if (StringUtil.isBlank(groupId) || StringUtil.isBlank(dataId)) {
            throw new IllegalArgumentException(String.format("Bad argument: groupId=[%s], dataId=[%s]", groupId, dataId));
        }
        this.path = Util.getPath(groupId, dataId);
    
        initZookeeper();
    }

    private void initZookeeper() {
        try {
    
            this.zkClient = ZkClient.zkClient();
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
