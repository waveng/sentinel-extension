# sentinel-extension
阿里 sentinel 的sentinel-datasource-zookeeper扩展，支持以zk 作为动态规则配置存储  

### 使用：直接引用即可  

### 配置项：
    zk访问IP+端口：csp.sentinel.datasource.address 
        
    存储节点（ 默认取SentinelConfig.getAppName()）：csp.sentinel.datasource.groupid
        
    flow存储节点（ 默认 default-dataId-flow）：csp.sentinel.datasource.dataid.flow
        
    degrade存储节点（默认default-dataId-degrade）：csp.sentinel.datasource.dataid.degrade
        
    system存储节点（默认default-dataId-system）：csp.sentinel.datasource.dataid.system
        
    authority存储节点（默认default-dataId-authority）：csp.sentinel.datasource.dataid.authority

    配置项可以配置在sentinel配置文件中，也可以配置在 jvm 环境中，也可以通过System.getProperty设置,或者通过 ZkRuleConfig 类进行设置

  使用使用 sentinel-spring-boot-starter 需要在配置中添加以下配置项
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
  
### sentinel-dashboard 支持
要在控制台中推送动态规则，也可以引用该包，但仅支持本人修改后的 dashboard
见分支 https://github.com/waveng/Sentinel/tree/custom-dashboard
需要在配置中添加配置启用
csp.sentinel.datasource.mode=dashboard
csp.sentinel.datasource.address 

使用 sentinel-spring-boot-starter 需要在配置中添加
```
csp.sentinel.zookeeper.run-mode=dashboard
csp.sentinel.zookeeper.address=127.0.0.1:2181
```