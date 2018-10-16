# sentinel-extension
## spring boot static
### 配置项
```
csp:
  sentinel:
    application:
      dashboard: 127.0.0.1:8080
      name: dubbo-sentine-provider
      port: 8724
    zookeeper:
      address: 192.168.1.2:2181
      dataid-authority: 
      dataid-degrade: 
      dataid-flow: 
      dataid-system: 
      group-id: 
```

### 启用sentinel dubbo filter
依赖
```
<dependency>
    <groupId>com.alibaba.csp</groupId>
    <artifactId>sentinel-dubbo-adapter</artifactId>
    <version>x.x.x</version>
</dependency>
```
启用
```
@SpringBootApplication
@EnableDubboConfiguration
@EnableSentinel
public class ProviderMain { 
    public static void main(String[] args) {
        
        
        SpringApplication.run(ProviderMain.class);
    }

}
```

### 启用注解方式

依赖
```
<dependency>
    <groupId>com.alibaba.csp</groupId>
    <artifactId>sentinel-annotation-aspectj</artifactId>
</dependency>
```
启用
```
@SpringBootApplication
@EnableDubboConfiguration
@EnableSentinelAspect
public class ConsumerMain {

    public static void main(String[] args) {
        SpringApplication.run(ConsumerMain.class, args);
       
    }


}
```

使用注解暴露接口
```
@Service
public class DemoServiceImpl{

    @DubboConsumer(version="1.0.0", timeout = 30000)
    private DemoService demoService;
    
    @SentinelResource(value = "sayHello", entryType = EntryType.OUT)
    public String sayHello(String name) {
        return demoService.sayHello(name);
    }

}

```

### 启用 sentinel-datasource-zookeeper
当配置了 zookeeper.address 配置时启用，否则不启用
```
zookeeper:
      address: 192.168.1.2:2181
```

