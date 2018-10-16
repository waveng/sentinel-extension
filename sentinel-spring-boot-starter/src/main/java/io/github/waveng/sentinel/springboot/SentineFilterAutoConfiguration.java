package io.github.waveng.sentinel.springboot;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.alibaba.dubbo.config.ConsumerConfig;
import com.alibaba.dubbo.config.ProviderConfig;

import io.github.waveng.sentinel.springboot.annotation.EnableSentinel;
import io.github.waveng.sentinel.springboot.annotation.Enables;
/**
 * 
 * @author wangbo 2018年10月15日 上午8:51:41
 * @version 0.0.1
 * @since 0.0.1
 */
@Configuration
@ConditionalOnBean(annotation = Enables.class)
public class SentineFilterAutoConfiguration {

    
    @Bean
    @ConditionalOnMissingBean(annotation = EnableSentinel.class)
    @ConditionalOnClass(name = {"com.alibaba.csp.sentinel.adapter.dubbo.SentinelDubboConsumerFilter"})
    public ConsumerConfig disabledConsumerFilter() {
        ConsumerConfig consumerConfig = new ConsumerConfig();
        consumerConfig.setFilter("-sentinel.dubbo.consumer.filter,-dubbo.application.context.name.filter");
        return consumerConfig;
    }
    
    @Bean
    @ConditionalOnMissingBean(annotation = EnableSentinel.class)
    @ConditionalOnClass(name = {"com.alibaba.csp.sentinel.adapter.dubbo.SentinelDubboProviderFilter"})
    public ProviderConfig disabledProviderFilter() {
        ProviderConfig config = new ProviderConfig();
        config.setFilter("-sentinel.dubbo.provider.filter,-dubbo.application.context.name.filter");
        return config;
    }
}
