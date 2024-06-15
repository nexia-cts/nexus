package com.nexia.nexus.builder.extension.wrap;

public interface ChangeableWrap<T> extends Wrap<T> {
    void setWrap(T wrap);
}
