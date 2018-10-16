package io.github.waveng.sentinel.datasource.zookeeper.util;

import java.util.List;

import com.alibaba.csp.sentinel.datasource.Converter;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityRule;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.system.SystemRule;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.taobao.csp.sentinel.dashboard.datasource.entity.rule.AuthorityRuleEntity;
import com.taobao.csp.sentinel.dashboard.datasource.entity.rule.DegradeRuleEntity;
import com.taobao.csp.sentinel.dashboard.datasource.entity.rule.FlowRuleEntity;
import com.taobao.csp.sentinel.dashboard.datasource.entity.rule.SystemRuleEntity;

/**
 * @author wangbo 2018年10月15日 上午8:51:41
 * @version 0.0.1
 * @since 0.0.1
 */
public class ConverterReadUtil {

    public final static Converter<String, List<FlowRule>>            CONVERTER_FLOW_RULES            = new Converter<String, List<FlowRule>>() {

                                                                                                         @Override
                                                                                                         public List<FlowRule> convert(String source) {
                                                                                                             return JSON
                                                                                                                     .parseObject(
                                                                                                                             source,
                                                                                                                             new TypeReference<List<FlowRule>>() {
                                                                                                                                                                                                                              });
                                                                                                         }

                                                                                                     };
    public final static Converter<String, List<DegradeRule>>         CONVERTER_DEGRADE_RULE          = new Converter<String, List<DegradeRule>>() {

                                                                                                         @Override
                                                                                                         public List<DegradeRule> convert(String source) {
                                                                                                             return JSON
                                                                                                                     .parseObject(
                                                                                                                             source,
                                                                                                                             new TypeReference<List<DegradeRule>>() {
                                                                                                                                                                                                                              });
                                                                                                         }
                                                                                                     };
    public final static Converter<String, List<SystemRule>>          CONVERTER_SYSTEM_RULE           = new Converter<String, List<SystemRule>>() {

                                                                                                         @Override
                                                                                                         public List<SystemRule> convert(String source) {
                                                                                                             return JSON
                                                                                                                     .parseObject(
                                                                                                                             source,
                                                                                                                             new TypeReference<List<SystemRule>>() {
                                                                                                                                                                                                                              });
                                                                                                         }

                                                                                                     };
    public final static Converter<String, List<AuthorityRule>>       CONVERTER_AUTHORITY_RULE        = new Converter<String, List<AuthorityRule>>() {

                                                                                                         @Override
                                                                                                         public List<AuthorityRule> convert(String source) {
                                                                                                             return JSON
                                                                                                                     .parseObject(
                                                                                                                             source,
                                                                                                                             new TypeReference<List<AuthorityRule>>() {
                                                                                                                                                                                                                              });
                                                                                                         }

                                                                                                     };

    public final static Converter<String, List<FlowRuleEntity>>      CONVERTER_FLOW_RULES_ENTITY     = new Converter<String, List<FlowRuleEntity>>() {

                                                                                                         @Override
                                                                                                         public List<FlowRuleEntity> convert(String source) {
                                                                                                             return JSON
                                                                                                                     .parseObject(
                                                                                                                             source,
                                                                                                                             new TypeReference<List<FlowRuleEntity>>() {
                                                                                                                                                                                                                              });
                                                                                                         }

                                                                                                     };
    public final static Converter<String, List<DegradeRuleEntity>>   CONVERTER_DEGRADE_RULE_ENTITY   = new Converter<String, List<DegradeRuleEntity>>() {

                                                                                                         @Override
                                                                                                         public List<DegradeRuleEntity> convert(String source) {
                                                                                                             return JSON
                                                                                                                     .parseObject(
                                                                                                                             source,
                                                                                                                             new TypeReference<List<DegradeRuleEntity>>() {
                                                                                                                                                                                                                              });
                                                                                                         }
                                                                                                     };
    public final static Converter<String, List<SystemRuleEntity>>    CONVERTER_SYSTEM_RULE_ENTITY    = new Converter<String, List<SystemRuleEntity>>() {

                                                                                                         @Override
                                                                                                         public List<SystemRuleEntity> convert(String source) {
                                                                                                             return JSON
                                                                                                                     .parseObject(
                                                                                                                             source,
                                                                                                                             new TypeReference<List<SystemRuleEntity>>() {
                                                                                                                                                                                                                              });
                                                                                                         }

                                                                                                     };
    public final static Converter<String, List<AuthorityRuleEntity>> CONVERTER_AUTHORITY_RULE_ENTITY = new Converter<String, List<AuthorityRuleEntity>>() {

                                                                                                         @Override
                                                                                                         public List<AuthorityRuleEntity> convert(String source) {
                                                                                                             return JSON
                                                                                                                     .parseObject(
                                                                                                                             source,
                                                                                                                             new TypeReference<List<AuthorityRuleEntity>>() {
                                                                                                                                                                                                                              });
                                                                                                         }

                                                                                                     };
}
