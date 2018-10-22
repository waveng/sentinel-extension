package io.github.waveng.sentinel.datasource.zookeeper.util;

import org.springframework.util.ClassUtils;

/**
 * 
 * @author wangbo 2018年10月15日 上午8:51:41
 * @version 0.0.1
 * @since 0.0.1
 */
public class Util {
    
    public static String getPath(String path) {
        if (path.startsWith("/")) {
            return path;
        } else {
            return "/" + path;
        }
    }
    
    public static String getPath(String groupId, String dataId) {
        String path = "";
        if (groupId.startsWith("/")) {
            path += groupId;
        } else {
            path += "/" + groupId;
        }
        if (dataId.startsWith("/")) {
            path += dataId;
        } else {
            path += "/" + dataId;
        }
        return path;
    }
    
    public static String getTypePath(String app, String ip, String port, String typePath) {
        return "/" + app + "/" + ip +":"+ port + "/rule-node-type/" + typePath;
    }
    
    public static boolean isParamFlowRule(){
        return ClassUtils.isPresent("com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowRuleManager", Thread.currentThread().getContextClassLoader());
    }
}
