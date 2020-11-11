package com.woniu.domain;

public interface BaseServie<T> extends BaseMapper<T> {

    BaseMapper<T> getMapper();

    int save(T data);
}
