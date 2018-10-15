package io.github.waveng.sentinel.springboot;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

import com.alibaba.csp.sentinel.annotation.aspectj.SentinelResourceAspect;

import io.github.waveng.sentinel.springboot.annotation.EnableSentinelAspect;
/**
 * 
 * @author wangbo 2018年10月15日 上午8:51:41
 * @version 0.0.1
 * @since 0.0.1
 */
@ConditionalOnBean(annotation = EnableSentinelAspect.class)
@ConditionalOnClass(SentinelResourceAspect.class)
public class SentinelAspectAutoConfiguration {
    
    @Bean
    @ConditionalOnMissingBean(SentinelResourceAspect.class)
    public SentinelResourceAspect sentinelResourceAspect() {
        return new SentinelResourceAspect();
    }
}
