package io.github.waveng.sentinel.datasource;

import com.alibaba.csp.sentinel.datasource.Converter;
import com.alibaba.csp.sentinel.datasource.WritableDataSource;
/**
 * 
 * @author wangbo 2018年10月15日 上午8:51:41
 * @version 0.0.1
 * @since 0.0.1
 */
public abstract class AbstractWritableDataSource<T, S> implements WritableDataSource<T>{
    
    protected final Converter<T, S> parser;
    
    public AbstractWritableDataSource(Converter<T, S> parser) {
        super();
        this.parser = parser;
    }

    @Override
    public void write(T conf) throws Exception {
        doWrite(converter(conf));
    }


    private S converter(T conf) throws Exception {
        S value = parser.convert(conf);
        return value;
    }
    
    public abstract void doWrite(S conf) throws Exception;
    
    
}
