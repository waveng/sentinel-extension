package cn.sumpay.sentinel.springboot.annotation;

import com.alibaba.csp.sentinel.annotation.SentinelResource;

public enum SentinelMode {
    /**
     * 使用  dubbo filter 方式配置Sentinel， 该方式将所有的接口都将通过Sentinel
     */
    FILTER, 
    /**
     * 使用  使用Spring AOP 的方式配置Sentinel，使用该方式 通过 {@link SentinelResource} 注解的方式配置接口
     */
    AOP; 
    
    public String toString(){
        return this.name().toUpperCase();
    }
}
