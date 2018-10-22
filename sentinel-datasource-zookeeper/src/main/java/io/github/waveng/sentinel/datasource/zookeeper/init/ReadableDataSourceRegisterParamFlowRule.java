package io.github.waveng.sentinel.datasource.zookeeper.init;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.csp.sentinel.datasource.Converter;
import com.alibaba.csp.sentinel.datasource.ReadableDataSource;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowRuleManager;
import com.alibaba.fastjson.JSON;

import io.github.waveng.sentinel.datasource.NodeType;
import io.github.waveng.sentinel.datasource.zookeeper.config.ZkRuleConfig;

public class ReadableDataSourceRegisterParamFlowRule {
    private static Logger logger = LoggerFactory.getLogger(ReadableDataSourceRegisterParamFlowRule.class);

    public static void register2SystemRule() {
        logger.info("[Sentinel] register2Property ParamFlowRuleManager");
        ReadableDataSource<String, List<ParamFlowRule>> readDataSource = ReadableDataSourceRegister
                .readDataSource(NodeType.NODE_PARAM_FLOW, ZkRuleConfig.getParamFlowDataId(), new Converter<String, List<ParamFlowRule>>() {

                    @Override
                    public List<ParamFlowRule> convert(String source) {
                        return JSON.parseArray(source, ParamFlowRule.class);
                    }
                });
        ParamFlowRuleManager.register2Property(readDataSource.getProperty());

    }
}
