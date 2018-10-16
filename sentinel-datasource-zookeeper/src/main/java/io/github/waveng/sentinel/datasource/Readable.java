package io.github.waveng.sentinel.datasource;

public interface Readable <S, T>  extends AutoCloseable{
    T read(String app, String ip, int port) throws Exception;
    
}
