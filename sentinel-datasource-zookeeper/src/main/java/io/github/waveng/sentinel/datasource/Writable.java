package io.github.waveng.sentinel.datasource;

public interface Writable<T> extends AutoCloseable{
    public void write(String app, String ip, int port, T conf) throws Exception;
}
