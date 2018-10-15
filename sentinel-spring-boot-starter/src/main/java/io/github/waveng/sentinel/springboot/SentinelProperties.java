package io.github.waveng.sentinel.springboot;

import org.springframework.boot.context.properties.ConfigurationProperties;
/**
 * 
 * @author wangbo 2018年10月15日 上午8:51:41
 * @version 0.0.1
 * @since 0.0.1
 */
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
