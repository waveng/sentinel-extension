package io.github.waveng.sentinel.dubbo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.boot.dubbo.annotation.EnableDubboConfiguration;

import io.github.waveng.sentinel.dubbo.consumer.DemoServiceImpl;
import io.github.waveng.sentinel.springboot.annotation.EnableSentinelAspect;

@SpringBootApplication
@EnableDubboConfiguration
@EnableSentinelAspect
@RestController
public class ConsumerMain {

    @Autowired
    private DemoServiceImpl     demoServiceImpl;

    public static void main(String[] args) {
        SpringApplication.run(ConsumerMain.class, args);
       
    }

    @RequestMapping("/get/{size}")
    public void get(@PathVariable int size) {
        for (int i = 0; i < size; i++) {
            System.err.println(demoServiceImpl.sayHello("王波"));
        }
        System.err.println(demoServiceImpl.sayHello("王波"));
    }

}
