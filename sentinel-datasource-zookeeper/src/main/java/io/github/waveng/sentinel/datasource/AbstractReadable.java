package io.github.waveng.sentinel.datasource;

import com.alibaba.csp.sentinel.datasource.Converter;
/**
 * 
 * @author wangbo 2018年10月15日 上午8:51:41
 * @version 0.0.1
 * @since 0.0.1
 */
public abstract class AbstractReadable<S, T> implements Readable<S, T>{
    
    protected final Converter<S, T> parser;
    
    public AbstractReadable(Converter<S, T> parser) {
        super();
        this.parser = parser;
    }

    @Override
    public T read(String app, String ip, int port) throws Exception {
        return converter(read(getPath(app, ip, port)));
    }

    
    private T converter(S conf) throws Exception {
        T value = parser.convert(conf);
        return value;
    }
    
    public abstract S read(String path) throws Exception;
    
    public abstract String getPath(String app, String ip, int port)  throws Exception;
    
}
