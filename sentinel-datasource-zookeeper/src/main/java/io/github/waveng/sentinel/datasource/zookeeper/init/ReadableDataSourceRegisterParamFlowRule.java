package io.github.waveng.sentinel.datasource.zookeeper.init;

import java.util.List;

import com.alibaba.csp.sentinel.datasource.Converter;
import com.alibaba.csp.sentinel.datasource.ReadableDataSource;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowRuleManager;
import com.taobao.csp.sentinel.dashboard.util.RuleUtils;

import io.github.waveng.sentinel.datasource.NodeType;
import io.github.waveng.sentinel.datasource.zookeeper.config.ZkRuleConfig;

public class ReadableDataSourceRegisterParamFlowRule {


    public static void register2SystemRule() {
        ReadableDataSource<String, List<ParamFlowRule>> readDataSource = ReadableDataSourceRegister
                .readDataSource(NodeType.NODE_SYSTEM, ZkRuleConfig.getSystemDataId(), new Converter<String, List<ParamFlowRule>>() {

                    @Override
                    public List<ParamFlowRule> convert(String source) {
                        return RuleUtils.parseParamFlowRule(source);
                    }
                });
        ParamFlowRuleManager.register2Property(readDataSource.getProperty());

    }
}
