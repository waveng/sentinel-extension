package cn.sumpay.sentinel.datasource.zookeeper.config;

import com.alibaba.csp.sentinel.config.SentinelConfig;
import com.alibaba.csp.sentinel.log.RecordLog;
import com.alibaba.csp.sentinel.util.StringUtil;

public class ZkRuleConfig {
    public static final String REMOTE_ADDRESS = "csp.sentinel.datasource.address";
    
    public static final String GROUP_ID = "csp.sentinel.datasource.groupId";
    
    public static final String FLOW_DATA_ID = "csp.sentinel.datasource.dataid.flow";
    
    public static final String DEGRADE_DATA_ID = "csp.sentinel.datasource.dataid.degrade";
    
    public static final String SYSTEM_DATA_ID = "csp.sentinel.datasource.dataid.system";
    
    public static final String AUTHORITY_DATA_ID = "csp.sentinel.datasource.dataid.authority";
    
    static{
        loadProps();
    }
    
    private static void loadProps() {
        try {
            String systemValue = System.getProperty(REMOTE_ADDRESS);
            if (!StringUtil.isEmpty(systemValue)) {
                SentinelConfig.setConfig(REMOTE_ADDRESS, systemValue);
            }
        } catch (Exception e) {
            RecordLog.info(e.getMessage(), e);
        }
        try {
            String systemValue = System.getProperty(GROUP_ID);
            if (!StringUtil.isEmpty(systemValue)) {
                SentinelConfig.setConfig(GROUP_ID, systemValue);
            }
        } catch (Exception e) {
            RecordLog.info(e.getMessage(), e);
        }
        try {
            String systemValue = System.getProperty(FLOW_DATA_ID);
            if (!StringUtil.isEmpty(systemValue)) {
                SentinelConfig.setConfig(FLOW_DATA_ID, systemValue);
            }
        } catch (Exception e) {
            RecordLog.info(e.getMessage(), e);
        }
        try {
            String systemValue = System.getProperty(DEGRADE_DATA_ID);
            if (!StringUtil.isEmpty(systemValue)) {
                SentinelConfig.setConfig(DEGRADE_DATA_ID, systemValue);
            }
        } catch (Exception e) {
            RecordLog.info(e.getMessage(), e);
        }
        try {
            String systemValue = System.getProperty(SYSTEM_DATA_ID);
            if (!StringUtil.isEmpty(systemValue)) {
                SentinelConfig.setConfig(SYSTEM_DATA_ID, systemValue);
            }
        } catch (Exception e) {
            RecordLog.info(e.getMessage(), e);
        }
        try {
            String systemValue = System.getProperty(AUTHORITY_DATA_ID);
            if (!StringUtil.isEmpty(systemValue)) {
                SentinelConfig.setConfig(AUTHORITY_DATA_ID, systemValue);
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
        SentinelConfig.setConfigIfAbsent(REMOTE_ADDRESS, remoteAddress);
    }
    
    public static void setGroupId(String groupId) {
        SentinelConfig.setConfigIfAbsent(GROUP_ID, groupId);
    }
    
    public static void setFlowDataId(String flowDataId) {
        SentinelConfig.setConfigIfAbsent(FLOW_DATA_ID, flowDataId);
    }
    
    public static void setDegradeDataId(String degradeDataId) {
        SentinelConfig.setConfigIfAbsent(DEGRADE_DATA_ID, degradeDataId);
    }
    
    public static void setSystemDataId(String systemDataId) {
        SentinelConfig.setConfigIfAbsent(SYSTEM_DATA_ID, systemDataId);
    }
    
    public static void setAuthorityDataId(String val) {
        SentinelConfig.setConfigIfAbsent(AUTHORITY_DATA_ID, val);
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
