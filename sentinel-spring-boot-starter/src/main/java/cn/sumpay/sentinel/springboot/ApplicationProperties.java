package cn.sumpay.sentinel.springboot;

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

}
