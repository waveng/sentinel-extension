package cn.sumpay.sentinel.dubbo.consumer;

import org.springframework.stereotype.Service;

import com.alibaba.boot.dubbo.annotation.DubboConsumer;
import com.alibaba.csp.sentinel.EntryType;
import com.alibaba.csp.sentinel.annotation.SentinelResource;

import cn.sumpay.sentinel.dubbo.api.DemoService;


@Service
public class DemoServiceImpl{

    @DubboConsumer(version="1.0.0", timeout = 30000)
    private DemoService demoService;
    
    @SentinelResource(value = "sayHello", entryType = EntryType.OUT)
    public String sayHello(String name) {
        return demoService.sayHello(name);
    }
    
    public String error(){  
        return "error";
        
    }
}
