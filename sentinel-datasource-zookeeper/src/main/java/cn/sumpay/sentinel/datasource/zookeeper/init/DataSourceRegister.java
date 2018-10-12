package cn.sumpay.sentinel.datasource.zookeeper.init;

import java.util.List;

import com.alibaba.csp.sentinel.datasource.Converter;
import com.alibaba.csp.sentinel.datasource.ReadableDataSource;
import com.alibaba.csp.sentinel.datasource.WritableDataSource;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityRule;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityRuleManager;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRule;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRuleManager;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.alibaba.csp.sentinel.slots.system.SystemRule;
import com.alibaba.csp.sentinel.slots.system.SystemRuleManager;
import com.alibaba.csp.sentinel.transport.util.WritableDataSourceRegistry;

import cn.sumpay.sentinel.datasource.zookeeper.ZookeeperReadableDataSource;
import cn.sumpay.sentinel.datasource.zookeeper.ZookeeperWritableDataSource;
import cn.sumpay.sentinel.datasource.zookeeper.config.ZkRuleConfig;
import cn.sumpay.sentinel.datasource.zookeeper.util.ConverterUtil;

public class DataSourceRegister {

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
        
        ReadableDataSource<String, List<FlowRule>> readDataSource = readDataSource(ZkRuleConfig.getFlowDataId(), ConverterUtil.CONVERTER_FLOW_RULES);
        WritableDataSource<List<FlowRule>> writablDataSource = writablDataSource(ZkRuleConfig.getFlowDataId(), ConverterUtil.FLOW_RULES_2BYTES);
        FlowRuleManager.register2Property(readDataSource.getProperty());
        WritableDataSourceRegistry.registerFlowDataSource(writablDataSource);
    }

    public static void register2DegradeRule() {
        ReadableDataSource<String, List<DegradeRule>> readDataSource = readDataSource(ZkRuleConfig.getDegradeDataId(), ConverterUtil.CONVERTER_DEGRADE_RULE);
        WritableDataSource<List<DegradeRule>> writablDataSource = writablDataSource(ZkRuleConfig.getDegradeDataId(), ConverterUtil.DEGRADE_RULE_2BYTES);
        DegradeRuleManager.register2Property(readDataSource.getProperty());
        WritableDataSourceRegistry.registerDegradeDataSource(writablDataSource);
    }

    public static void register2SystemRule() {
        ReadableDataSource<String, List<SystemRule>> readDataSource = readDataSource(ZkRuleConfig.getSystemDataId(), ConverterUtil.CONVERTER_SYSTEM_RULE);
        WritableDataSource<List<SystemRule>> writablDataSource = writablDataSource(ZkRuleConfig.getSystemDataId(), ConverterUtil.SYSTEM_RULE_2BYTES);
        SystemRuleManager.register2Property(readDataSource.getProperty());
        WritableDataSourceRegistry.registerSystemDataSource(writablDataSource);
        
    }

    public static void register2AuthorityRule() {
        ReadableDataSource<String, List<AuthorityRule>> readDataSource = readDataSource(ZkRuleConfig.getAuthorityDataId(), ConverterUtil.CONVERTER_AUTHORITY_RULE);
        WritableDataSource<List<AuthorityRule>> writablDataSource = writablDataSource(ZkRuleConfig.getAuthorityDataId(), ConverterUtil.AUTHORITY_RULE_2BYTES);
        AuthorityRuleManager.register2Property(readDataSource.getProperty());
        WritableDataSourceRegistry.registerAuthorityDataSource(writablDataSource);
    }

    
    private static <T> ReadableDataSource<String, T> readDataSource(String dataId, Converter<String,T> converter) {
        if (ZkRuleConfig.isGroupId()) {
            return new ZookeeperReadableDataSource<>(ZkRuleConfig.getGroupId(), dataId, converter);
        } else {
            return new ZookeeperReadableDataSource<>(dataId,converter);
            
        }
    }

    private static <S> ZookeeperWritableDataSource<S, byte[]> writablDataSource(String dataId, Converter<S, byte[]> converter) {
        if (ZkRuleConfig.isGroupId()) {
            return new ZookeeperWritableDataSource<>(ZkRuleConfig.getGroupId(), dataId, converter);
        } else {
            return new ZookeeperWritableDataSource<>(dataId, converter);
        }
    }

}
