package io.github.waveng.sentinel.springboot;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import io.github.waveng.sentinel.datasource.zookeeper.config.ZkRuleConfig;
import io.github.waveng.sentinel.springboot.annotation.Enables;
/**
 * 
 * @author wangbo 2018年10月15日 上午8:51:41
 * @version 0.0.1
 * @since 0.0.1
 */
@Configuration
@ConditionalOnBean(annotation = Enables.class)
@EnableConfigurationProperties(SentinelProperties.class)
@ConditionalOnProperty(prefix = "csp.sentinel.zookeeper", name = "address", havingValue = "")
public class SentinelZkAutoConfiguration {
    
    @Autowired
    private SentinelProperties sentinelProperties;
    
    @PostConstruct
    public void initZkConfig() {
        ZookeeperProperties zk = sentinelProperties.getZookeeper();
        ZkRuleConfig.setRemoteAddress(zk.getAddress());
        ZkRuleConfig.setFlowDataId(zk.getDataidFlow());
        ZkRuleConfig.setDegradeDataId(zk.getDataidDegrade());
        ZkRuleConfig.setSystemDataId(zk.getDataidSystem());
        ZkRuleConfig.setAuthorityDataId(zk.getDataidAuthority());
    }
}
