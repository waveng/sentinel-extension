package io.github.waveng.sentinel.springboot;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.env.Environment;

import com.alibaba.csp.sentinel.config.SentinelConfig;
import com.alibaba.csp.sentinel.transport.config.TransportConfig;
import com.alibaba.csp.sentinel.util.StringUtil;

import io.github.waveng.sentinel.datasource.zookeeper.config.ZkRuleConfig;
import io.github.waveng.sentinel.datasource.zookeeper.dashboard.SentinelZkClientDataSource;

/**
 * 
 * @author wangbo 2018年10月15日 上午8:51:41
 * @version 0.0.1
 * @since 0.0.1
 */
@AutoConfigureOrder(Ordered.HIGHEST_PRECEDENCE)
@Configuration
@EnableConfigurationProperties(SentinelProperties.class)
@AutoConfigureBefore({SentineFilterAutoConfiguration.class, SentinelAspectAutoConfiguration.class})
public class SentinelAutoConfiguration {
    @Autowired
    private Environment env;
    
    @Autowired
    private SentinelProperties sentinelProperties;
    
    @PostConstruct
    public void init(){
        initAppConfig();
        initZkConfig();
    }
    public void initZkConfig() {
        ZookeeperProperties zk = sentinelProperties.getZookeeper();
        if(zk == null){
            return ;
        }
        if(StringUtil.isNotBlank(zk.getAddress())){
            ZkRuleConfig.setRunMode(zk.getRunMode());
            ZkRuleConfig.setRemoteAddress(zk.getAddress());
            ZkRuleConfig.setFlowDataId(zk.getDataidFlow());
            ZkRuleConfig.setDegradeDataId(zk.getDataidDegrade());
            ZkRuleConfig.setSystemDataId(zk.getDataidSystem());
            ZkRuleConfig.setAuthorityDataId(zk.getDataidAuthority());
        }
    }
    
    public void initAppConfig() {
        ApplicationProperties application = sentinelProperties.getApplication();
        if(application == null){
            return ;
        }
        if(StringUtil.isBlank(System.getProperty("project.name"))){
            String name = env.getProperty("project.name");
            if (StringUtil.isBlank(name)){
                name = application.getName();
            }
            if (StringUtil.isBlank(name)){
                name = env.getProperty("spring.application.name");
            }
            if (StringUtil.isNotBlank(name)){
                System.setProperty("project.name", name);
            }
        }
        
        if (StringUtil.isNotBlank(application.getDashboard()))
            SentinelConfig.setConfig(TransportConfig.CONSOLE_SERVER, application.getDashboard());
        if (StringUtil.isNotBlank(application.getPort()))
            SentinelConfig.setConfig(TransportConfig.SERVER_PORT, application.getPort());
    }
    
    @Configuration
    @ConditionalOnClass(name={"com.taobao.csp.sentinel.dashboard.client.datasource.SentinelClientDataSource"})
    @AutoConfigureOrder(Ordered.HIGHEST_PRECEDENCE)
    public class CreateSentinelClientDataSource{
        
        @Bean
        @ConditionalOnProperty(prefix="csp.sentinel.zookeeper", name="run-mode", havingValue="dashboard")
        public SentinelZkClientDataSource sentinelClientDataSource(){
            return new SentinelZkClientDataSource();
        }
    }
    
}
