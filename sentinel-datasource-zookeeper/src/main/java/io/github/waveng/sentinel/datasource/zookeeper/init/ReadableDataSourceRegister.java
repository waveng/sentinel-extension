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
import com.taobao.csp.sentinel.dashboard.util.RuleUtils;

import io.github.waveng.sentinel.datasource.NodeType;
import io.github.waveng.sentinel.datasource.zookeeper.ZookeeperAutoReadableDataSource;
import io.github.waveng.sentinel.datasource.zookeeper.config.ZkRuleConfig;
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
        
        ReadableDataSource<String, List<FlowRule>> readDataSource = readDataSource(NodeType.NODE_FLOW, ZkRuleConfig.getFlowDataId(),
         new Converter<String, List<FlowRule>>() {
            @Override
            public List<FlowRule> convert(String source) {
                return RuleUtils.parseFlowRule(source);
            }
        });
        FlowRuleManager.register2Property(readDataSource.getProperty());
    }

    public static void register2DegradeRule() {
        ReadableDataSource<String, List<DegradeRule>> readDataSource = readDataSource(NodeType.NODE_DEGRADE, ZkRuleConfig.getDegradeDataId(), 
                new Converter<String, List<DegradeRule>>() {
            @Override
            public List<DegradeRule> convert(String source) {
                return RuleUtils.parseDegradeRule(source);
            }
        });
        DegradeRuleManager.register2Property(readDataSource.getProperty());
    }

    public static void register2SystemRule() {
        ReadableDataSource<String, List<SystemRule>> readDataSource = readDataSource(NodeType.NODE_SYSTEM, ZkRuleConfig.getSystemDataId(), 
                new Converter<String, List<SystemRule>>() {
            @Override
            public List<SystemRule> convert(String source) {
                return RuleUtils.parseSystemRule(source);
            }
        });
        SystemRuleManager.register2Property(readDataSource.getProperty());
        
    }

    public static void register2AuthorityRule() {
        ReadableDataSource<String, List<AuthorityRule>> readDataSource = readDataSource(NodeType.NODE_AUTHORITY, ZkRuleConfig.getAuthorityDataId(), 
                new Converter<String, List<AuthorityRule>>() {

            @Override
            public List<AuthorityRule> convert(String source) {
                return RuleUtils.parseAuthorityRule(source);
            }

        });
        AuthorityRuleManager.register2Property(readDataSource.getProperty());
    }

    
    public static <T> ReadableDataSource<String, T> readDataSource(final NodeType nodeType, String dataId, Converter<String,T> converter) {
        if (ZkRuleConfig.isGroupId()) {
            return new ZookeeperAutoReadableDataSource<>(nodeType, ZkRuleConfig.getGroupId(), dataId, converter);
        } else {
            return new ZookeeperAutoReadableDataSource<>(nodeType, dataId,converter);
            
        }
    }

}
