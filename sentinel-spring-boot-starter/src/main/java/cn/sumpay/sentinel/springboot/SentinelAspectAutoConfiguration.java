package cn.sumpay.sentinel.springboot;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

import com.alibaba.csp.sentinel.annotation.aspectj.SentinelResourceAspect;

import cn.sumpay.sentinel.springboot.annotation.EnableSentinelAspect;

@ConditionalOnBean(annotation = EnableSentinelAspect.class)
@ConditionalOnClass(SentinelResourceAspect.class)
public class SentinelAspectAutoConfiguration {
    
    @Bean
    @ConditionalOnMissingBean(SentinelResourceAspect.class)
    public SentinelResourceAspect sentinelResourceAspect() {
        return new SentinelResourceAspect();
    }
}
