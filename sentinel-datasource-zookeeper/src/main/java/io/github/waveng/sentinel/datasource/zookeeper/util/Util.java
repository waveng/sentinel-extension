package io.github.waveng.sentinel.datasource.zookeeper.util;
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
        return "/" + app + "/" + ip +":"+ port + "/" + typePath;
    }
}
