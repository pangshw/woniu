package com.woniu.domain;

import java.util.List;

public interface BaseMapper<T> {

    public T findById(Long id);

    public List<T> list(T obj);

    /**
     * 新增元数据
     */
    public int insert(T data);

    /**
     * 修改元数据
     */
    public int update(T data);

    /**
     * 删除元数据
     */
    public int deleteById(Long id);

    /**
     * 批量删除元数据
     */
    public int deleteByIds(List<Long> ids);
}
