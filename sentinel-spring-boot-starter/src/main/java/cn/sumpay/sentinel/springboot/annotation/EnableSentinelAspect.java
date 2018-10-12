package cn.sumpay.sentinel.springboot.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * Enable Sentinel (for provider or consumer) for spring boot application
 * 
 * @author wangbo
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Enables
public @interface EnableSentinelAspect {
    
}
