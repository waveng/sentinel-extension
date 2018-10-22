package io.github.waveng.sentinel.springboot;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 
 * @author wangbo 2018年10月15日 上午8:51:41
 * @version 0.0.1
 * @since 0.0.1
 */
@ConfigurationProperties(prefix = "csp.sentinel.zookeeper")
public class ZookeeperProperties {
        /**
         * zk访问地址
         */
        private String address;
        /**
         * 引用唯一 id, 在多机部署的情况下，希望每个部署之间的配置不共享，则该项唯一
         */
        private String groupId;
        /**
         * 流控规则节点
         */
        private String dataidFlow;
        /**
         * 降级规则节点
         */
        private String dataidDegrade;
        /**
         * 系统规则节点
         */
        private String dataidSystem;
        /**
         * 黑白名单规则节点
         */
        private String dataidAuthority;
        
        /**
         * 热点数据规则节点
         */
        private String dataidParamFlow;

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getGroupId() {
            return groupId;
        }

        public void setGroupId(String groupId) {
            this.groupId = groupId;
        }

        public String getDataidFlow() {
            return dataidFlow;
        }

        public void setDataidFlow(String dataidFlow) {
            this.dataidFlow = dataidFlow;
        }

        public String getDataidDegrade() {
            return dataidDegrade;
        }

        public void setDataidDegrade(String dataidDegrade) {
            this.dataidDegrade = dataidDegrade;
        }

        public String getDataidSystem() {
            return dataidSystem;
        }

        public void setDataidSystem(String dataidSystem) {
            this.dataidSystem = dataidSystem;
        }

        public String getDataidAuthority() {
            return dataidAuthority;
        }

        public void setDataidAuthority(String dataidAuthority) {
            this.dataidAuthority = dataidAuthority;
        }

        public String getDataidParamFlow() {
            return dataidParamFlow;
        }

        public void setDataidParamFlow(String dataidParamFlow) {
            this.dataidParamFlow = dataidParamFlow;
        }

    }