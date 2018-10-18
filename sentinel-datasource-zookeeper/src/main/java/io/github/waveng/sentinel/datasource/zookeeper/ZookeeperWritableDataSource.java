package io.github.waveng.sentinel.datasource.zookeeper;

import com.alibaba.csp.sentinel.datasource.Converter;

import io.github.waveng.sentinel.datasource.AbstractWritable;
import io.github.waveng.sentinel.datasource.NodeType;
import io.github.waveng.sentinel.datasource.zookeeper.util.Util;
/**
 * 
 * @author wangbo 2018年10月15日 上午8:51:41
 * @version 0.0.1
 * @since 0.0.1
 */
public class ZookeeperWritableDataSource<T> extends AbstractWritable<T, byte[]> {
    
    private final String nodeType;
    private ZkClient zkClient;
    
    public ZookeeperWritableDataSource(final NodeType nodeType, Converter<T, byte[]> parser) {
        super(parser);
        this.nodeType = nodeType.toString();
        this.zkClient = ZkClientFactory.getZkClient();
    }
    
    
    @Override
    public void write(String path, byte[] rule) throws Exception {
        if (this.zkClient == null) {
            throw new IllegalStateException("Zookeeper has not been initialized or error occurred");
        }
        zkClient.forPath(path, rule);
    }

    @Override
    public void close() throws Exception {
        if (this.zkClient != null) {
            this.zkClient.close();
        }
    }

    @Override
    public String getPath(String app, String ip, int port) throws Exception {
        if (this.zkClient == null) {
            throw new IllegalStateException("Zookeeper has not been initialized or error occurred");
        }
        byte[] data = zkClient.forPath(Util.getTypePath(app, ip, String.valueOf(port), this.nodeType));
        String path = new String(data);
        if (zkClient.checkExists(path) == null) {
            zkClient.createForPath(path);
        }
        return path;
    }

}
