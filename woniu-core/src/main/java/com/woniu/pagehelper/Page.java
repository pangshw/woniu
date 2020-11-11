package com.woniu.mybatis.pagehelper;

import java.util.List;

public class Page<E> {
    private long totalElements;
    private List<E> content;

    public long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(long totalElements) {
        this.totalElements = totalElements;
    }

    public List<E> getContent() {
        return content;
    }

    public void setContent(List<E> content) {
        this.content = content;
    }
}
