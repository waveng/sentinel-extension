package io.github.waveng.sentinel.datasource.zookeeper.init;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.csp.sentinel.init.InitFunc;
import com.alibaba.csp.sentinel.init.InitOrder;

import io.github.waveng.sentinel.datasource.zookeeper.config.ZkRuleConfig;
import io.github.waveng.sentinel.datasource.zookeeper.util.Util;
/**
 * 
 * @author wangbo 2018年10月15日 上午8:51:41
 * @version 0.0.1
 * @since 0.0.1
 */
@InitOrder(InitOrder.HIGHEST_PRECEDENCE)
public class ZookeeperDataSourceInitFunc implements InitFunc {
    private static Logger logger = LoggerFactory.getLogger(ZookeeperDataSourceInitFunc.class);
    @Override
    public void init() throws Exception {
        if(ZkRuleConfig.isClient()){
            logger.info("[Sentinel][ZookeeperDataSourceInitFunc] info: initial DataSourceRegister ...");
            if(ZkRuleConfig.isRemoteAddress()){
                ReadableDataSourceRegister.registerAll();
                if(Util.isParamFlowRule()){
                    ReadableDataSourceRegisterParamFlowRule.register2SystemRule();
                }
                logger.info("[Sentinel][ZookeeperDataSourceInitFunc] info: initial DataSourceRegister ok");
            }else{
                logger.info("[Sentinel][ZookeeperDataSourceInitFunc] WARN: initial DataSourceRegister failed, Remote address DataSource of not setting");
            }
        }
    }

}
