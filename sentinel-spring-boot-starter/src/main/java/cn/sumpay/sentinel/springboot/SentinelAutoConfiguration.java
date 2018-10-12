package cn.sumpay.sentinel.springboot;

import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import com.alibaba.dubbo.config.ConsumerConfig;
import com.alibaba.dubbo.config.ProviderConfig;

import cn.sumpay.sentinel.springboot.annotation.EnableSentinel;

@Order(Ordered.HIGHEST_PRECEDENCE)
@Configuration
@EnableConfigurationProperties(SentinelProperties.class)
@AutoConfigureBefore({SentineInitAutoConfiguration.class, SentinelZkAutoConfiguration.class, SentinelAspectAutoConfiguration.class})
public class SentinelAutoConfiguration {
   
    @Bean
    @ConditionalOnMissingBean(annotation = EnableSentinel.class)
    public ConsumerConfig disabledConsumerFilter() {
        ConsumerConfig consumerConfig = new ConsumerConfig();
        consumerConfig.setFilter("-sentinel.dubbo.consumer.filter,-dubbo.application.context.name.filter");
        return consumerConfig;
    }
    
    @Bean
    @ConditionalOnMissingBean(annotation = EnableSentinel.class)
    public ProviderConfig disabledProviderFilter() {
        ProviderConfig config = new ProviderConfig();
        config.setFilter("-sentinel.dubbo.provider.filter,-dubbo.application.context.name.filter");
        return config;
    }
    
}
