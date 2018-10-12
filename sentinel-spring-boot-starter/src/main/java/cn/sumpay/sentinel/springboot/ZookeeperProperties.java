package cn.sumpay.sentinel.springboot;

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

    }