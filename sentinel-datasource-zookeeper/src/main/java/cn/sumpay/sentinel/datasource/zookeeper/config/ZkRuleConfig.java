package cn.sumpay.sentinel.datasource.zookeeper.config;

import com.alibaba.csp.sentinel.config.SentinelConfig;
import com.alibaba.csp.sentinel.log.RecordLog;
import com.alibaba.csp.sentinel.util.StringUtil;

public class ZkRuleConfig {
    public static final String REMOTE_ADDRESS = "csp.sentinel.datasource.address";
    
    public static final String GROUP_ID = "csp.sentinel.datasource.groupid";
    
    public static final String FLOW_DATA_ID = "csp.sentinel.datasource.dataid.flow";
    
    public static final String DEGRADE_DATA_ID = "csp.sentinel.datasource.dataid.degrade";
    
    public static final String SYSTEM_DATA_ID = "csp.sentinel.datasource.dataid.system";
    
    public static final String AUTHORITY_DATA_ID = "csp.sentinel.datasource.dataid.authority";
    

    public static final String DEFAULT_FLOW_DATAID = "default-dataId-flow";
    public static final String DEFAULT_DEGRADE_DATAID = "default-dataId-degrade";
    public static final String DEFAULT_SYSTEM_DATAID = "defaul-dataIdt-system";
    public static final String DEFAULT_AUTHORITY_DATAID = "default-dataId-authority";
    
    static{
        initialize();
        loadProps();
    }
    
    private static void initialize() {
        
        SentinelConfig.setConfigIfAbsent(GROUP_ID, SentinelConfig.getAppName());
        SentinelConfig.setConfigIfAbsent(FLOW_DATA_ID, DEFAULT_FLOW_DATAID);
        SentinelConfig.setConfigIfAbsent(DEGRADE_DATA_ID, DEFAULT_DEGRADE_DATAID);
        SentinelConfig.setConfigIfAbsent(SYSTEM_DATA_ID, DEFAULT_SYSTEM_DATAID);
        SentinelConfig.setConfigIfAbsent(AUTHORITY_DATA_ID, DEFAULT_AUTHORITY_DATAID);
    }
    
    private static void loadProps() {
        try {
            if (!StringUtil.isEmpty(System.getProperty(REMOTE_ADDRESS))) {
                SentinelConfig.setConfig(REMOTE_ADDRESS, System.getProperty(REMOTE_ADDRESS));
            }
        } catch (Exception e) {
            RecordLog.info(e.getMessage(), e);
        }
        try {
            if (!StringUtil.isEmpty(System.getProperty(GROUP_ID))) {
                SentinelConfig.setConfig(GROUP_ID, System.getProperty(GROUP_ID));
            }
        } catch (Exception e) {
            RecordLog.info(e.getMessage(), e);
        }
        
        try {
            if (!StringUtil.isEmpty(System.getProperty(FLOW_DATA_ID))) {
                SentinelConfig.setConfig(FLOW_DATA_ID, System.getProperty(FLOW_DATA_ID));
            }
        } catch (Exception e) {
            RecordLog.info(e.getMessage(), e);
        }
        
        try {
            if (!StringUtil.isEmpty(System.getProperty(DEGRADE_DATA_ID))) {
                SentinelConfig.setConfig(DEGRADE_DATA_ID, System.getProperty(DEGRADE_DATA_ID));
            }
        } catch (Exception e) {
            RecordLog.info(e.getMessage(), e);
        }
        try {
            if (!StringUtil.isEmpty(System.getProperty(SYSTEM_DATA_ID))) {
                SentinelConfig.setConfig(SYSTEM_DATA_ID, System.getProperty(SYSTEM_DATA_ID));
            }
        } catch (Exception e) {
            RecordLog.info(e.getMessage(), e);
        }
        try {
            if (!StringUtil.isEmpty(System.getProperty(AUTHORITY_DATA_ID))) {
                SentinelConfig.setConfig(AUTHORITY_DATA_ID, System.getProperty(AUTHORITY_DATA_ID));
            }
        } catch (Exception e) {
            RecordLog.info(e.getMessage(), e);
        }
        
        
        RecordLog.info(REMOTE_ADDRESS + " value: " + SentinelConfig.getConfig(REMOTE_ADDRESS));
        RecordLog.info(GROUP_ID + " value: " + SentinelConfig.getConfig(GROUP_ID));
        RecordLog.info(FLOW_DATA_ID + " value: " + SentinelConfig.getConfig(FLOW_DATA_ID));
        RecordLog.info(DEGRADE_DATA_ID + " value: " + SentinelConfig.getConfig(DEGRADE_DATA_ID));
        RecordLog.info(SYSTEM_DATA_ID + " value: " + SentinelConfig.getConfig(SYSTEM_DATA_ID));
        RecordLog.info(AUTHORITY_DATA_ID + " value: " + SentinelConfig.getConfig(AUTHORITY_DATA_ID));

    }
    
    
    public static String getRemoteAddress() {
        return SentinelConfig.getConfig(REMOTE_ADDRESS);
    }
    
    public static String getGroupId() {
        return SentinelConfig.getConfig(GROUP_ID);
    }
    
    public static String getFlowDataId() {
        return SentinelConfig.getConfig(FLOW_DATA_ID);
    }
    
    public static String getDegradeDataId() {
        return SentinelConfig.getConfig(DEGRADE_DATA_ID);
    }
    
    public static String getSystemDataId() {
        return SentinelConfig.getConfig(SYSTEM_DATA_ID);
    }
    
    public static String getAuthorityDataId() {
        return SentinelConfig.getConfig(AUTHORITY_DATA_ID);
    }
    
    public static void setRemoteAddress(String remoteAddress) {
        SentinelConfig.setConfig(REMOTE_ADDRESS, remoteAddress);
    }
    
    public static void setGroupId(String groupId) {
        SentinelConfig.setConfig(GROUP_ID, groupId);
    }
    
    public static void setFlowDataId(String flowDataId) {
        SentinelConfig.setConfig(FLOW_DATA_ID, flowDataId);
    }
    
    public static void setDegradeDataId(String degradeDataId) {
        SentinelConfig.setConfig(DEGRADE_DATA_ID, degradeDataId);
    }
    
    public static void setSystemDataId(String systemDataId) {
        SentinelConfig.setConfig(SYSTEM_DATA_ID, systemDataId);
    }
    
    public static void setAuthorityDataId(String val) {
        SentinelConfig.setConfig(AUTHORITY_DATA_ID, val);
    }
    
    public static boolean isRemoteAddress() {
        return !StringUtil.isBlank(SentinelConfig.getConfig(REMOTE_ADDRESS));
    }
    
    public static boolean isGroupId() {
        return !StringUtil.isBlank(SentinelConfig.getConfig(GROUP_ID));
    }
    
    public static boolean isFlowDataId() {
        return !StringUtil.isBlank(SentinelConfig.getConfig(FLOW_DATA_ID));
    }
    
    public static boolean isDegradeDataId() {
        return !StringUtil.isBlank(SentinelConfig.getConfig(DEGRADE_DATA_ID));
    }
    
    public static boolean isSystemDataId() {
        return !StringUtil.isBlank(SentinelConfig.getConfig(SYSTEM_DATA_ID));
    }
    
    public static boolean isAuthorityDataId() {
        return !StringUtil.isBlank(SentinelConfig.getConfig(AUTHORITY_DATA_ID));
    }
    
}
