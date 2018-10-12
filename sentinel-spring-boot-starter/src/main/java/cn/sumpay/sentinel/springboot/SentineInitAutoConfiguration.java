package cn.sumpay.sentinel.springboot;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import com.alibaba.csp.sentinel.config.SentinelConfig;
import com.alibaba.csp.sentinel.transport.config.TransportConfig;
import com.alibaba.csp.sentinel.util.AppNameUtil;
import com.alibaba.csp.sentinel.util.StringUtil;

import cn.sumpay.sentinel.springboot.annotation.Enables;

@Configuration
@ConditionalOnBean(annotation = Enables.class)
@EnableConfigurationProperties(SentinelProperties.class)
public class SentineInitAutoConfiguration {

    @Autowired
    private SentinelProperties sentinelProperties;

    @PostConstruct
    public void initZkConfig() {
        ApplicationProperties application = sentinelProperties.getApplication();
        if (StringUtil.isNotBlank(application.getName()))
            SentinelConfig.setConfig(AppNameUtil.APP_NAME, application.getName());
        if (StringUtil.isNotBlank(application.getDashboard()))
            SentinelConfig.setConfig(TransportConfig.CONSOLE_SERVER, application.getDashboard());
        if (StringUtil.isNotBlank(application.getPort()))
            SentinelConfig.setConfig(TransportConfig.SERVER_PORT, application.getPort());
    }
}
