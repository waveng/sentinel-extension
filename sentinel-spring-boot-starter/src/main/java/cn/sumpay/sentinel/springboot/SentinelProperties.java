package cn.sumpay.sentinel.springboot;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "csp.sentinel")
public class SentinelProperties {
    
    /**
     * Application
     */
    private ApplicationProperties application;
    
    /**
     * zookeeper datasource setting
     */
    private ZookeeperProperties zookeeper;

    
    public ApplicationProperties getApplication() {
        return application;
    }

    public void setApplication(ApplicationProperties application) {
        this.application = application;
    }

    public ZookeeperProperties getZookeeper() {
        return zookeeper;
    }

    public void setZookeeper(ZookeeperProperties zookeeper) {
        this.zookeeper = zookeeper;
    }

}
