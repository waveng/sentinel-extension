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
    
    private Readable<String, List<FlowRuleEntity>> readableFlow;
    private Readable<String, List<DegradeRuleEntity>> readableDegrade;
    private Readable<String, List<SystemRuleEntity>> readableSystem;
    private Readable<String, List<AuthorityRuleEntity>> readableAuthority;
    
    private Writable<List<FlowRuleEntity>> writableFlow;
    private Writable<List<DegradeRuleEntity>> writableDegrade;
    private Writable<List<SystemRuleEntity>> writableSystem;
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
        writableFlow = createWritable(NodeType.NODE_FLOW, ConverterWritUtil.FLOW_RULES_2BYTES);
    }

    private  void initWritableDegradee() {
        writableDegrade = createWritable(NodeType.NODE_DEGRADE, ConverterWritUtil.DEGRADE_RULE_2BYTES);
    }

    private  void initWritableSystem() {
        writableSystem = createWritable(NodeType.NODE_SYSTEM, ConverterWritUtil.SYSTEM_RULE_2BYTES);
    }
    
    private void initWritableAuthority() {
//      writableAuthority = createWritable(NodeType.NODE_AUTHORITY, ConverterWritUtil.AUTHORITY_RULE_2BYTES);
    }
    
    private  void intiReadableFlow() {
        readableFlow = createReadable(NodeType.NODE_FLOW, ConverterReadUtil.CONVERTER_FLOW_RULES_ENTITY);
    }

    private  void intiReadableDegradee() {
        readableDegrade = createReadable(NodeType.NODE_DEGRADE, ConverterReadUtil.CONVERTER_DEGRADE_RULE_ENTITY);
    }

    private  void initReadableSystem() {
        readableSystem = createReadable(NodeType.NODE_DEGRADE, ConverterReadUtil.CONVERTER_SYSTEM_RULE_ENTITY);
    }
    
    private  void initReadableAuthority() {
        readableAuthority = createReadable(NodeType.NODE_AUTHORITY, ConverterReadUtil.CONVERTER_AUTHORITY_RULE_ENTITY);
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
        try {
            data = readableFlow.read(app, ip, port);
        } catch (Exception e) {
            logger.error("readable flow error !", e);
        }
        if(data == null){
            data = super.fetchFlowRuleOfMachine(app, ip, port);
        }
        return data;
    }

    @Override
    public List<DegradeRuleEntity> fetchDegradeRuleOfMachine(String app, String ip, int port) {
        List<DegradeRuleEntity> data = null;
        try {
            data = readableDegrade.read(app, ip, port);
        } catch (Exception e) {
            logger.error("readable degrade error !", e);
        }
        if(data == null){
            data = super.fetchDegradeRuleOfMachine(app, ip, port);
        }
        return data;
    }

    @Override
    public List<SystemRuleEntity> fetchSystemRuleOfMachine(String app, String ip, int port) {
        List<SystemRuleEntity> data = null;
        try {
            data = readableSystem.read(app, ip, port);
        } catch (Exception e) {
            logger.error("readable degrade error !", e);
        }
        if(data == null){
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
        try {
            data = readableAuthority.read(app, ip, port);
        } catch (Exception e) {
            logger.error("readable degrade error !", e);
        }
        if(data == null){
            data = super.fetchAuthorityRulesOfMachine(app, ip, port);
        }
        return data;
    }

    @Override
    public boolean setFlowRuleOfMachine(String app, String ip, int port, List<FlowRuleEntity> rules) {
        try {
            writableFlow.write(app, ip, port, rules);
            return true;
        } catch (Exception e) {
            logger.error("write flow error!", e);
        }
        return false;
    }

    @Override
    public boolean setDegradeRuleOfMachine(String app, String ip, int port, List<DegradeRuleEntity> rules) {
        try {
            writableDegrade.write(app, ip, port, rules);
            return true;
        } catch (Exception e) {
            logger.error("write degradee error!", e);
        }
        return false;
    }

    @Override
    public boolean setSystemRuleOfMachine(String app, String ip, int port, List<SystemRuleEntity> rules) {
        try {
            
            writableSystem.write(app, ip, port, rules);
            return true;
        } catch (Exception e) {
            logger.error("write degradee error!", e);
        }
        return false;
    }

    @Override
    public CompletableFuture<Void> setParamFlowRuleOfMachine(String app, String ip, int port, List<ParamFlowRuleEntity> rules) {
        return super.setParamFlowRuleOfMachine(app, ip, port, rules);
    }
}
