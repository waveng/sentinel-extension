package cn.sumpay.sentinel.datasource.zookeeper.init;

import com.alibaba.csp.sentinel.init.InitFunc;
import com.alibaba.csp.sentinel.init.InitOrder;
import com.alibaba.csp.sentinel.log.RecordLog;

import cn.sumpay.sentinel.datasource.zookeeper.config.ZkRuleConfig;

@InitOrder(InitOrder.HIGHEST_PRECEDENCE)
public class ZookeeperDataSourceInitFunc implements InitFunc {

    @Override
    public void init() throws Exception {
        RecordLog.info("[ZookeeperDataSourceInitFunc] info: initial DataSourceRegister ...");
        if(ZkRuleConfig.isRemoteAddress()){
            DataSourceRegister.registerAll();
            RecordLog.info("[ZookeeperDataSourceInitFunc] info: initial DataSourceRegister ok");
        }else{
            RecordLog.info("[ZookeeperDataSourceInitFunc] WARN: initial DataSourceRegister failed, Remote address DataSource of not setting");
        }
    }
}
