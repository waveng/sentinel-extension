package io.github.waveng.sentinel.datasource.zookeeper.init;

import java.util.List;

import com.alibaba.csp.sentinel.datasource.Converter;
import com.alibaba.csp.sentinel.datasource.ReadableDataSource;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityRule;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityRuleManager;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRule;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRuleManager;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.alibaba.csp.sentinel.slots.system.SystemRule;
import com.alibaba.csp.sentinel.slots.system.SystemRuleManager;

import io.github.waveng.sentinel.datasource.zookeeper.ZookeeperAutoReadableDataSource;
import io.github.waveng.sentinel.datasource.zookeeper.config.ZkRuleConfig;
import io.github.waveng.sentinel.datasource.zookeeper.util.ConverterReadUtil;
/**
 * 
 * @author wangbo 2018年10月15日 上午8:51:41
 * @version 0.0.1
 * @since 0.0.1
 */
public class ReadableDataSourceRegister {

    public static void registerAll() {
        if(ZkRuleConfig.isFlowDataId()){
            register2FlowRule();
        }
        if(ZkRuleConfig.isDegradeDataId()){
            register2DegradeRule();
        }
        if(ZkRuleConfig.isSystemDataId()){
            register2SystemRule();
        }
        if(ZkRuleConfig.isAuthorityDataId()){
            register2AuthorityRule();
        }
    }
    
    public static void register2FlowRule() {
        
        ReadableDataSource<String, List<FlowRule>> readDataSource = readDataSource(ZkRuleConfig.FLOW_DATA_ID, ZkRuleConfig.getFlowDataId(), ConverterReadUtil.CONVERTER_FLOW_RULES);
        FlowRuleManager.register2Property(readDataSource.getProperty());
    }

    public static void register2DegradeRule() {
        ReadableDataSource<String, List<DegradeRule>> readDataSource = readDataSource(ZkRuleConfig.DEGRADE_DATA_ID, ZkRuleConfig.getDegradeDataId(), ConverterReadUtil.CONVERTER_DEGRADE_RULE);
        DegradeRuleManager.register2Property(readDataSource.getProperty());
    }

    public static void register2SystemRule() {
        ReadableDataSource<String, List<SystemRule>> readDataSource = readDataSource(ZkRuleConfig.SYSTEM_DATA_ID, ZkRuleConfig.getSystemDataId(), ConverterReadUtil.CONVERTER_SYSTEM_RULE);
        SystemRuleManager.register2Property(readDataSource.getProperty());
        
    }

    public static void register2AuthorityRule() {
        ReadableDataSource<String, List<AuthorityRule>> readDataSource = readDataSource(ZkRuleConfig.AUTHORITY_DATA_ID, ZkRuleConfig.getAuthorityDataId(), ConverterReadUtil.CONVERTER_AUTHORITY_RULE);
        AuthorityRuleManager.register2Property(readDataSource.getProperty());
    }

    
    private static <T> ReadableDataSource<String, T> readDataSource(String typePath, String dataId, Converter<String,T> converter) {
        if (ZkRuleConfig.isGroupId()) {
            return new ZookeeperAutoReadableDataSource<>(typePath, ZkRuleConfig.getGroupId(), dataId, converter);
        } else {
            return new ZookeeperAutoReadableDataSource<>(typePath, dataId,converter);
            
        }
    }

}
