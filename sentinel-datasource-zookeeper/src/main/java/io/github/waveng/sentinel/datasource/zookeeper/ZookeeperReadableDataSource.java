package io.github.waveng.sentinel.datasource.zookeeper;

import com.alibaba.csp.sentinel.datasource.Converter;

import io.github.waveng.sentinel.datasource.AbstractReadable;
import io.github.waveng.sentinel.datasource.NodeType;
import io.github.waveng.sentinel.datasource.zookeeper.util.Util;

/**
 * A read-only {@code DataSource} with ZooKeeper backend.
 * @param <S>
 *
 */
public class ZookeeperReadableDataSource<T> extends AbstractReadable<String, T> {

    private final String nodeType;

    private ZkClient zkClient = null;

    public ZookeeperReadableDataSource(final NodeType nodeType, Converter<String, T> parser) {
        super(parser);
        this.nodeType = nodeType.toString();
        this.zkClient = ZkClientFactory.getZkClient();
    }


    @Override
    public void close() throws Exception {
        if (this.zkClient == null) {
            this.zkClient.close();
        }
    }

    @Override
    public String read(String path) throws Exception {
        if (this.zkClient == null) {
            throw new IllegalStateException("Zookeeper has not been initialized or error occurred");
        }
        byte[] data = this.zkClient.forPath(path);
        if (data != null) {
            return new String(data);
        }
        return null;
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
