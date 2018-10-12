package cn.sumpay.sentinel.dubbo.provider;

import java.util.Date;

import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Service;

import cn.sumpay.sentinel.dubbo.api.DemoService;


@Service(interfaceClass=DemoService.class,version="1.0.0")
@Component
public class DemoServiceImpl implements DemoService{

    @Override
    public String sayHello(String name) {
        String res = new Date() + "hello " + name;
        System.err.println(res);
        return res;
    }
    
}
