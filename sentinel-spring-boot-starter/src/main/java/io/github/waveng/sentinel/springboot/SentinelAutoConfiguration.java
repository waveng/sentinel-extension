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
@EnableConfigurationProperties(value ={ ZookeeperProperties.class, ApplicationProperties.class})
@AutoConfigureBefore({SentinelAspectAutoConfiguration.class})
public class SentinelAutoConfiguration {
    @Autowired
    private Environment env;
    
    private static String LOG_DIR = "csp.sentinel.log.dir";
    
    @Autowired
    private ZookeeperProperties zookeeperProperties;
    @Autowired
    private ApplicationProperties applicationProperties;
    
    @PostConstruct
    public void init(){
        initAppConfig();
        initZkConfig();
        ZkRuleConfig.printLog();
    }
    public void initAppConfig() {
        
        if(StringUtil.isBlank(System.getProperty(LOG_DIR)) && StringUtil.isNotBlank(applicationProperties.getLogdir())){
            System.setProperty(LOG_DIR, applicationProperties.getLogdir());
        }
        
        if(StringUtil.isBlank(System.getProperty("project.name"))){
            String name = env.getProperty("project.name");
            if (StringUtil.isBlank(name)){
                name = applicationProperties.getName();
            }
            if (StringUtil.isBlank(name)){
                name = env.getProperty("spring.application.name");
            }
            if (StringUtil.isNotBlank(name)){
                System.setProperty("project.name", name);
            }
        }
       
        
        String runMode = applicationProperties.getRunMode();
        if(StringUtil.isBlank(runMode)){
            runMode = "client";
        }
        
        ZkRuleConfig.setRunMode(runMode);
        
        if (StringUtil.isNotBlank(applicationProperties.getDashboard()))
            SentinelConfig.setConfig(TransportConfig.CONSOLE_SERVER, applicationProperties.getDashboard());
        if (StringUtil.isNotBlank(applicationProperties.getPort()))
            SentinelConfig.setConfig(TransportConfig.SERVER_PORT, applicationProperties.getPort());
    }
    public void initZkConfig() {
        if(StringUtil.isNotBlank(zookeeperProperties.getAddress())){
            ZkRuleConfig.setRemoteAddress(zookeeperProperties.getAddress());
            ZkRuleConfig.setGroupId(zookeeperProperties.getGroupId());
            ZkRuleConfig.setFlowDataId(zookeeperProperties.getDataidFlow());
            ZkRuleConfig.setDegradeDataId(zookeeperProperties.getDataidDegrade());
            ZkRuleConfig.setSystemDataId(zookeeperProperties.getDataidSystem());
            ZkRuleConfig.setAuthorityDataId(zookeeperProperties.getDataidAuthority());
            ZkRuleConfig.setParamFlowDataId(zookeeperProperties.getDataidParamFlow());
        }
    }
    
    @AutoConfigureOrder(Ordered.HIGHEST_PRECEDENCE)
    @Configuration
    @ConditionalOnClass(name={"com.taobao.csp.sentinel.dashboard.client.datasource.SentinelClientDataSource"})
    @ConditionalOnProperty(prefix="csp.sentinel.zookeeper", name="run-mode", havingValue="dashboard")
    public class CreateSentinelClientDataSource{
        
        @Bean
        public SentinelZkClientDataSource sentinelClientDataSource(){
            return new SentinelZkClientDataSource();
        }
    }
    
}
