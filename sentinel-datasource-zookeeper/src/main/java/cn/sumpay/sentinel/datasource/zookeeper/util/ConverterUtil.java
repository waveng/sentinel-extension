package cn.sumpay.sentinel.datasource.zookeeper.util;

import java.util.List;

import com.alibaba.csp.sentinel.datasource.Converter;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityRule;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.system.SystemRule;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

public class ConverterUtil {
    public final static Converter<List<FlowRule>, byte[]>      FLOW_RULES_2BYTES        = new Converter<List<FlowRule>, byte[]>() {

                                                                                          @Override
                                                                                          public byte[] convert(List<FlowRule> source) {
                                                                                              return JSON.toJSONBytes(
                                                                                                      source);
                                                                                          }

                                                                                      };
    public final static Converter<List<DegradeRule>, byte[]>   DEGRADE_RULE_2BYTES      = new Converter<List<DegradeRule>, byte[]>() {

                                                                                          @Override
                                                                                          public byte[] convert(List<DegradeRule> source) {
                                                                                              return JSON.toJSONBytes(
                                                                                                      source);
                                                                                          }

                                                                                      };
    public final static Converter<List<SystemRule>, byte[]>    SYSTEM_RULE_2BYTES       = new Converter<List<SystemRule>, byte[]>() {

                                                                                          @Override
                                                                                          public byte[] convert(List<SystemRule> source) {
                                                                                              return JSON.toJSONBytes(
                                                                                                      source);
                                                                                          }

                                                                                      };
    public final static Converter<List<AuthorityRule>, byte[]> AUTHORITY_RULE_2BYTES    = new Converter<List<AuthorityRule>, byte[]>() {

                                                                                          @Override
                                                                                          public byte[] convert(List<AuthorityRule> source) {
                                                                                              return JSON.toJSONBytes(
                                                                                                      source);
                                                                                          }

                                                                                      };

    public final static Converter<String, List<FlowRule>>      CONVERTER_FLOW_RULES     = new Converter<String, List<FlowRule>>() {

                                                                                          @Override
                                                                                          public List<FlowRule> convert(String source) {
                                                                                              return JSON.parseObject(
                                                                                                      source,
                                                                                                      new TypeReference<List<FlowRule>>() {
                                                                                                                                                                                        });
                                                                                          }

                                                                                      };
    public final static Converter<String, List<DegradeRule>>   CONVERTER_DEGRADE_RULE   = new Converter<String, List<DegradeRule>>() {

                                                                                          @Override
                                                                                          public List<DegradeRule> convert(String source) {
                                                                                              return JSON.parseObject(
                                                                                                      source,
                                                                                                      new TypeReference<List<DegradeRule>>() {
                                                                                                                                                                                        });
                                                                                          }
                                                                                      };
    public final static Converter<String, List<SystemRule>>    CONVERTER_SYSTEM_RULE    = new Converter<String, List<SystemRule>>() {

                                                                                          @Override
                                                                                          public List<SystemRule> convert(String source) {
                                                                                              return JSON.parseObject(
                                                                                                      source,
                                                                                                      new TypeReference<List<SystemRule>>() {
                                                                                                                                                                                        });
                                                                                          }

                                                                                      };
    public final static Converter<String, List<AuthorityRule>> CONVERTER_AUTHORITY_RULE = new Converter<String, List<AuthorityRule>>() {

                                                                                          @Override
                                                                                          public List<AuthorityRule> convert(String source) {
                                                                                              return JSON.parseObject(
                                                                                                      source,
                                                                                                      new TypeReference<List<AuthorityRule>>() {
                                                                                                                                                                                        });
                                                                                          }

                                                                                      };
}
