package io.github.waveng.sentinel.datasource.zookeeper.init;

import com.alibaba.csp.sentinel.init.InitFunc;
import com.alibaba.csp.sentinel.init.InitOrder;
import com.alibaba.csp.sentinel.log.RecordLog;

import io.github.waveng.sentinel.datasource.zookeeper.config.ZkRuleConfig;
/**
 * 
 * @author wangbo 2018年10月15日 上午8:51:41
 * @version 0.0.1
 * @since 0.0.1
 */
@InitOrder(InitOrder.HIGHEST_PRECEDENCE)
public class ZookeeperDataSourceInitFunc implements InitFunc {

    @Override
    public void init() throws Exception {
        if(ZkRuleConfig.isClient()){
            RecordLog.info("[ZookeeperDataSourceInitFunc] info: initial DataSourceRegister ...");
            if(ZkRuleConfig.isRemoteAddress()){
                ReadableDataSourceRegister.registerAll();
                RecordLog.info("[ZookeeperDataSourceInitFunc] info: initial DataSourceRegister ok");
            }else{
                RecordLog.info("[ZookeeperDataSourceInitFunc] WARN: initial DataSourceRegister failed, Remote address DataSource of not setting");
            }
        }
    }
}
