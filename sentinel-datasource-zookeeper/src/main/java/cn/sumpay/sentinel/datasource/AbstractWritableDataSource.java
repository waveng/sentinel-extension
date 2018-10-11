package cn.sumpay.sentinel.datasource;

import com.alibaba.csp.sentinel.datasource.Converter;
import com.alibaba.csp.sentinel.datasource.WritableDataSource;

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
