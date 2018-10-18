package io.github.waveng.sentinel.dubbo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.alibaba.boot.dubbo.annotation.EnableDubboConfiguration;

import io.github.waveng.sentinel.springboot.annotation.EnableSentinel;
import io.github.waveng.sentinel.springboot.annotation.EnableSentinelAspect;

@SpringBootApplication
@EnableDubboConfiguration
@EnableSentinelAspect
public class ProviderMain { 
    public static void main(String[] args) {
        
        
        SpringApplication.run(ProviderMain.class);
    }

}
