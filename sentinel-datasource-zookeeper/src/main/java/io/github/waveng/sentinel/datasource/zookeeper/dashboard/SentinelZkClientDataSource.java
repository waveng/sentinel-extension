package io.github.waveng.sentinel.datasource.zookeeper.dashboard;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.csp.sentinel.command.vo.NodeVo;
import com.alibaba.csp.sentinel.datasource.Converter;
import com.taobao.csp.sentinel.dashboard.client.datasource.SentinelApiClientDataSource;
import com.taobao.csp.sentinel.dashboard.datasource.entity.rule.AuthorityRuleEntity;
import com.taobao.csp.sentinel.dashboard.datasource.entity.rule.DegradeRuleEntity;
import com.taobao.csp.sentinel.dashboard.datasource.entity.rule.FlowRuleEntity;
import com.taobao.csp.sentinel.dashboard.datasource.entity.rule.ParamFlowRuleEntity;
import com.taobao.csp.sentinel.dashboard.datasource.entity.rule.SystemRuleEntity;

import io.github.waveng.sentinel.datasource.NodeType;
import io.github.waveng.sentinel.datasource.Readable;
import io.github.waveng.sentinel.datasource.Writable;
import io.github.waveng.sentinel.datasource.zookeeper.ZookeeperReadableDataSource;
import io.github.waveng.sentinel.datasource.zookeeper.ZookeeperWritableDataSource;
import io.github.waveng.sentinel.datasource.zookeeper.util.ConverterReadUtil;
import io.github.waveng.sentinel.datasource.zookeeper.util.ConverterWritUtil;
/**
 * 
 * @author wangbo 2018年10月15日 上午8:51:41
 * @version 0.0.1
 * @since 0.0.1
 */
public class SentinelZkClientDataSource extends SentinelApiClientDataSource{
    private static Logger logger = LoggerFactory.getLogger(SentinelZkClientDataSource.class);
    
    private Readable<String, List<FlowRuleEntity>> readableFlowDataSource;
    private Readable<String, List<DegradeRuleEntity>> readableDegradeDataSource;
    private Readable<String, List<SystemRuleEntity>> readableSystemDataSource;
    private Readable<String, List<AuthorityRuleEntity>> readableAuthorityDataSource;
    
    private Writable<List<FlowRuleEntity>> writableFlowDataSource;
    private Writable<List<DegradeRuleEntity>> writableDegradeDataSource;
    private Writable<List<SystemRuleEntity>> writableSystemDataSource;
//   private Writable<List<AuthorityRuleEntity>> writableAuthority = null;
    
    public SentinelZkClientDataSource() {
        super();
        initWritableFlow();
        initWritableDegradee();
        initWritableSystem();
        initWritableAuthority();
        
        intiReadableFlow();
        intiReadableDegradee();
        initReadableSystem();
        initReadableAuthority();
    }

    private  void initWritableFlow() {
        writableFlowDataSource = createWritable(NodeType.NODE_FLOW, ConverterWritUtil.FLOW_RULES_2BYTES);
    }

    private  void initWritableDegradee() {
        writableDegradeDataSource = createWritable(NodeType.NODE_DEGRADE, ConverterWritUtil.DEGRADE_RULE_2BYTES);
    }

    private  void initWritableSystem() {
        writableSystemDataSource = createWritable(NodeType.NODE_SYSTEM, ConverterWritUtil.SYSTEM_RULE_2BYTES);
    }
    
    private void initWritableAuthority() {
//      writableAuthority = createWritable(NodeType.NODE_AUTHORITY, ConverterWritUtil.AUTHORITY_RULE_2BYTES);
    }
    
    private  void intiReadableFlow() {
        readableFlowDataSource = createReadable(NodeType.NODE_FLOW, ConverterReadUtil.CONVERTER_FLOW_RULES_ENTITY);
    }

    private  void intiReadableDegradee() {
        readableDegradeDataSource = createReadable(NodeType.NODE_DEGRADE, ConverterReadUtil.CONVERTER_DEGRADE_RULE_ENTITY);
    }

    private  void initReadableSystem() {
        readableSystemDataSource = createReadable(NodeType.NODE_DEGRADE, ConverterReadUtil.CONVERTER_SYSTEM_RULE_ENTITY);
    }
    
    private  void initReadableAuthority() {
        readableAuthorityDataSource = createReadable(NodeType.NODE_AUTHORITY, ConverterReadUtil.CONVERTER_AUTHORITY_RULE_ENTITY);
    }


    private static <S> Writable<S> createWritable(NodeType nodeType, Converter<S, byte[]> converter) {
        return new ZookeeperWritableDataSource<>(nodeType, converter);
    }
    
    private static <T> Readable<String, T> createReadable(NodeType nodeType, Converter<String, T> converter) {
        return new ZookeeperReadableDataSource<>(nodeType, converter);
    }

    @Override
    public List<NodeVo> fetchResourceOfMachine(String ip, int port, String type) {
        return super.fetchResourceOfMachine(ip, port, type);
    }

    @Override
    public List<NodeVo> fetchClusterNodeOfMachine(String ip, int port, boolean includeZero) {
        return super.fetchClusterNodeOfMachine(ip, port, includeZero);
    }

    @Override
    public List<FlowRuleEntity> fetchFlowRuleOfMachine(String app, String ip, int port) {
        List<FlowRuleEntity> data = null;
        if(readableFlowDataSource != null){
            try {
                data = readableFlowDataSource.read(app, ip, port);
            } catch (Exception e) {
                logger.warn("Reading flow rule error!", e);
            }
        }
        if(data == null || data.isEmpty()){
            data = super.fetchFlowRuleOfMachine(app, ip, port);
        }
        return data;
    }

    @Override
    public List<DegradeRuleEntity> fetchDegradeRuleOfMachine(String app, String ip, int port) {
        List<DegradeRuleEntity> data = null;
        if(readableDegradeDataSource != null){
            try {
                data = readableDegradeDataSource.read(app, ip, port);
            } catch (Exception e) {
                logger.warn("Reading degrade rule error!", e);
            }
        }
        if(data == null || data.isEmpty()){
            data = super.fetchDegradeRuleOfMachine(app, ip, port);
        }
        return data;
    }

    @Override
    public List<SystemRuleEntity> fetchSystemRuleOfMachine(String app, String ip, int port) {
        List<SystemRuleEntity> data = null;
        if(readableSystemDataSource != null){
            try {
                data = readableSystemDataSource.read(app, ip, port);
            } catch (Exception e) {
                logger.warn("Reading system rule error!", e);
            }
        }
        if(data == null || data.isEmpty()){
            data = super.fetchSystemRuleOfMachine(app, ip, port);
        }
        return data;
    }

    @Override
    public CompletableFuture<List<ParamFlowRuleEntity>> fetchParamFlowRulesOfMachine(String app, String ip, int port) {
        
        return super.fetchParamFlowRulesOfMachine(app, ip, port);
    }

    @Override
    public List<AuthorityRuleEntity> fetchAuthorityRulesOfMachine(String app, String ip, int port) {
        List<AuthorityRuleEntity> data = null;
        if(readableAuthorityDataSource != null){
            try {
                data = readableAuthorityDataSource.read(app, ip, port);
            } catch (Exception e) {
                logger.warn("Reading authority rule error!", e);
            }
        }
        if(data == null || data.isEmpty()){
            data = super.fetchAuthorityRulesOfMachine(app, ip, port);
        }
        return data;
    }

    @Override
    public boolean setFlowRuleOfMachine(String app, String ip, int port, List<FlowRuleEntity> rules) {
        if(writableFlowDataSource == null){
            return super.setFlowRuleOfMachine(app, ip, port, rules);
        }
        try {
            writableFlowDataSource.write(app, ip, port, rules);
            return true;
        } catch (Exception e) {
            logger.warn("Write flow rule error!", e);
        }
        return false;
    }

    @Override
    public boolean setDegradeRuleOfMachine(String app, String ip, int port, List<DegradeRuleEntity> rules) {
        if(writableDegradeDataSource == null){
            return super.setDegradeRuleOfMachine(app, ip, port, rules);
        }
        try {
            writableDegradeDataSource.write(app, ip, port, rules);
            return true;
        } catch (Exception e) {
            logger.warn("Write degradee rule error!", e);
        }
        return false;
    }

    @Override
    public boolean setSystemRuleOfMachine(String app, String ip, int port, List<SystemRuleEntity> rules) {
        if(writableSystemDataSource == null){
            return super.setSystemRuleOfMachine(app, ip, port, rules);
        }
        try {
            writableSystemDataSource.write(app, ip, port, rules);
            return true;
        } catch (Exception e) {
            logger.warn("Write system rule error!", e);
        }
        return false;
    }

    @Override
    public CompletableFuture<Void> setParamFlowRuleOfMachine(String app, String ip, int port, List<ParamFlowRuleEntity> rules) {
        return super.setParamFlowRuleOfMachine(app, ip, port, rules);
    }
}
