package io.github.waveng.sentinel.springboot;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 
 * @author wangbo 2018年10月15日 上午8:51:41
 * @version 0.0.1
 * @since 0.0.1
 */
@ConfigurationProperties(prefix = "csp.sentinel.application")
public class ApplicationProperties {
    /**
     * 客户端的 port，用于上报相关信息（默认为 8719）, 同台机器上由多台时，需要指定不同的端口
     */
    private String port;
    /**
     * 控制台的地址 IP + 端口
     */
    private String dashboard;

    /**
     * 应用名称，会在控制台中显示
     */
    private String name;
    /**
     * sentinel 日志
     */
    private String logdir;
    
    /**
     * 运行在 client 还是 dashboard;
     */
    private String runMode;
    

    public String getRunMode() {
        return runMode;
    }

    public void setRunMode(String runMode) {
        this.runMode = runMode;
    }
    
    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getDashboard() {
        return dashboard;
    }

    public void setDashboard(String dashboard) {
        this.dashboard = dashboard;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogdir() {
        return logdir;
    }

    public void setLogdir(String logdir) {
        this.logdir = logdir;
    }

}
