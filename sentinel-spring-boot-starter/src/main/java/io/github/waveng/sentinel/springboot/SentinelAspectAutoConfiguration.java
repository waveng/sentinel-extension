package io.github.waveng.sentinel.springboot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.context.annotation.Bean;

import com.alibaba.csp.sentinel.annotation.aspectj.SentinelResourceAspect;

import io.github.waveng.sentinel.springboot.annotation.EnableSentinel;
/**
 * 
 * @author wangbo 2018年10月15日 上午8:51:41
 * @version 0.0.1
 * @since 0.0.1
 */
@ConditionalOnBean(annotation = EnableSentinel.class)
@ConditionalOnClass(SentinelResourceAspect.class)
@ConditionalOnMissingClass(value = { "com.alibaba.csp.sentinel.adapter.dubbo.SentinelDubboConsumerFilter",
"com.alibaba.csp.sentinel.adapter.dubbo.SentinelDubboProviderFilter" })
public class SentinelAspectAutoConfiguration {
    private static Logger logger = LoggerFactory.getLogger(SentinelAspectAutoConfiguration.class);
    @Bean
    @ConditionalOnMissingBean(SentinelResourceAspect.class)
    public SentinelResourceAspect sentinelResourceAspect() {
        logger.info("[Sentinel] EnableSentinel: enable annotation, SentinelResourceAspect");
        return new SentinelResourceAspect();
    }
}
