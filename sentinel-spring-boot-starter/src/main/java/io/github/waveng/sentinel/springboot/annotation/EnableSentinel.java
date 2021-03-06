package io.github.waveng.sentinel.springboot.annotation;

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
 * @version 0.0.1
 * @since 0.0.1
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface EnableSentinel {
    
}
