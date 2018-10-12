package cn.sumpay.sentinel.springboot;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import cn.sumpay.sentinel.datasource.zookeeper.config.ZkRuleConfig;
import cn.sumpay.sentinel.springboot.annotation.Enables;

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
