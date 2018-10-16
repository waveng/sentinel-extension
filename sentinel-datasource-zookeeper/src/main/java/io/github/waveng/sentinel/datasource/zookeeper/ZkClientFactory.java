package io.github.waveng.sentinel.datasource.zookeeper;

class ZkClientFactory {
    
    private static ZkClient zkClient =  new ZkClient();

    public static ZkClient getZkClient() {
        return zkClient;
    }
            
}
