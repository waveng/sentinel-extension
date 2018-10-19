package io.github.waveng.sentinel.datasource.zookeeper.init;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ClassUtils;

import com.alibaba.csp.sentinel.init.InitFunc;
import com.alibaba.csp.sentinel.init.InitOrder;

import io.github.waveng.sentinel.datasource.zookeeper.config.ZkRuleConfig;
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
            logger.info("[ZookeeperDataSourceInitFunc] info: initial DataSourceRegister ...");
            if(ZkRuleConfig.isRemoteAddress()){
                ReadableDataSourceRegister.registerAll();
                if(isPresent()){
                    ReadableDataSourceRegisterParamFlowRule.register2SystemRule();
                }
                logger.info("[ZookeeperDataSourceInitFunc] info: initial DataSourceRegister ok");
            }else{
                logger.info("[ZookeeperDataSourceInitFunc] WARN: initial DataSourceRegister failed, Remote address DataSource of not setting");
            }
        }
    }
    
    private static boolean isPresent(){
        return ClassUtils.isPresent("com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowRuleManager", Thread.currentThread().getContextClassLoader());
    }

}
