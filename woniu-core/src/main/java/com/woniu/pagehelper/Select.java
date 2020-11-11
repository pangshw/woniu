package com.woniu.mybatis.pagehelper;

import java.util.List;

@FunctionalInterface
public interface Select<E> {
    List<E> doSelect();
}
