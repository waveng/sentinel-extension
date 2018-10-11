package cn.sumpay.sentinel.datasource.zookeeper.util;

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
}
