package io.github.waveng.sentinel.datasource.zookeeper.util;

import java.util.List;

import com.alibaba.csp.sentinel.datasource.Converter;
import com.alibaba.fastjson.JSON;
import com.taobao.csp.sentinel.dashboard.datasource.entity.rule.AuthorityRuleEntity;
import com.taobao.csp.sentinel.dashboard.datasource.entity.rule.DegradeRuleEntity;
import com.taobao.csp.sentinel.dashboard.datasource.entity.rule.FlowRuleEntity;
import com.taobao.csp.sentinel.dashboard.datasource.entity.rule.SystemRuleEntity;
/**
 * 
 * @author wangbo 2018年10月15日 上午8:51:41
 * @version 0.0.1
 * @since 0.0.1
 */
public class ConverterWritUtil {
    public final static Converter<List<FlowRuleEntity>, byte[]>      FLOW_RULES_2BYTES        = new Converter<List<FlowRuleEntity>, byte[]>() {

                                                                                          @Override
                                                                                          public byte[] convert(List<FlowRuleEntity> source) {
                                                                                              return JSON.toJSONBytes(
                                                                                                      source);
                                                                                          }

                                                                                      };
    public final static Converter<List<DegradeRuleEntity>, byte[]>   DEGRADE_RULE_2BYTES      = new Converter<List<DegradeRuleEntity>, byte[]>() {

                                                                                          @Override
                                                                                          public byte[] convert(List<DegradeRuleEntity> source) {
                                                                                              return JSON.toJSONBytes(
                                                                                                      source);
                                                                                          }

                                                                                      };
    public final static Converter<List<SystemRuleEntity>, byte[]>    SYSTEM_RULE_2BYTES       = new Converter<List<SystemRuleEntity>, byte[]>() {

                                                                                          @Override
                                                                                          public byte[] convert(List<SystemRuleEntity> source) {
                                                                                              return JSON.toJSONBytes(
                                                                                                      source);
                                                                                          }

                                                                                      };
    public final static Converter<List<AuthorityRuleEntity>, byte[]> AUTHORITY_RULE_2BYTES    = new Converter<List<AuthorityRuleEntity>, byte[]>() {

                                                                                          @Override
                                                                                          public byte[] convert(List<AuthorityRuleEntity> source) {
                                                                                              return JSON.toJSONBytes(
                                                                                                      source);
                                                                                          }

                                                                                      };
}
