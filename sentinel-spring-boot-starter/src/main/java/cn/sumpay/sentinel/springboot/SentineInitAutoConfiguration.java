package cn.sumpay.sentinel.springboot;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import com.alibaba.csp.sentinel.config.SentinelConfig;
import com.alibaba.csp.sentinel.transport.config.TransportConfig;
import com.alibaba.csp.sentinel.util.StringUtil;

import cn.sumpay.sentinel.springboot.annotation.Enables;

@Configuration
@ConditionalOnBean(annotation = Enables.class)
@EnableConfigurationProperties(SentinelProperties.class)
public class SentineInitAutoConfiguration {

    @Autowired
    private Environment env;
    
    @Autowired
    private SentinelProperties sentinelProperties;

    @PostConstruct
    public void initZkConfig() {
        ApplicationProperties application = sentinelProperties.getApplication();
        String name = application.getName();
        if (StringUtil.isBlank(application.getName())){
            name = env.getProperty("spring.application.name");
        }
        if (StringUtil.isNotBlank(name)){
            System.setProperty("project.name", name);
        }
        if (StringUtil.isNotBlank(application.getDashboard()))
            SentinelConfig.setConfig(TransportConfig.CONSOLE_SERVER, application.getDashboard());
        if (StringUtil.isNotBlank(application.getPort()))
            SentinelConfig.setConfig(TransportConfig.SERVER_PORT, application.getPort());
    }
}
