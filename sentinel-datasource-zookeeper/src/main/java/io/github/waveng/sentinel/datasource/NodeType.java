package io.github.waveng.sentinel.datasource;

public enum NodeType {
    NODE_FLOW,
    NODE_DEGRADE,
    NODE_SYSTEM,
    NODE_AUTHORITY;
    public String toString(){
        return this.name().toLowerCase();
    }
}
