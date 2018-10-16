package io.github.waveng.sentinel.datasource.zookeeper;

class ZkClientFactory {
    private static ZkClient zkClient;
    static{
        zkClient =  new ZkClient();
    }
    public static ZkClient getZkClient() {
        return zkClient;
    }
            
}
