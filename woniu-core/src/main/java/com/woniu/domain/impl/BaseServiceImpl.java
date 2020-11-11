package com.woniu.domain.impl;

import com.woniu.domain.BaseEntity;
import com.woniu.domain.BaseMapper;
import com.woniu.domain.BaseServie;
import com.woniu.util.BeanUtils;

import java.util.Calendar;
import java.util.List;

public abstract class BaseServiceImpl<T> implements BaseServie<T> {

    @Override
    public BaseMapper<T> getMapper() {
        return null;
    }

    @Override
    public T findById(Long id) {
        BaseMapper<T> mapper = getMapper();
        if (mapper == null) return null;

        return mapper.findById(id);
    }

    @Override
    public List<T> list(T obj) {
        BaseMapper<T> mapper = getMapper();
        if (mapper == null) return null;

        return mapper.list(obj);
    }

    @Override
    public int insert(T data) {
        BaseMapper<T> mapper = getMapper();
        if (mapper == null) return 0;

        if (data instanceof BaseEntity) {
            BaseEntity baseEntity = (BaseEntity) data;
            baseEntity.setCreateTime(Calendar.getInstance().getTime());
            baseEntity.setUpdateTime(Calendar.getInstance().getTime());
            baseEntity.setVersion(0L);
        }
        return mapper.insert(data);
    }

    @Override
    public int update(T data) {
        BaseMapper<T> mapper = getMapper();
        if (mapper == null) return 0;

        if (data instanceof BaseEntity) {
            BaseEntity baseEntity = (BaseEntity) data;
            baseEntity.setUpdateTime(Calendar.getInstance().getTime());
            if (baseEntity.getVersion() == null) {
                baseEntity.setVersion(0L);
            } else {
                baseEntity.setVersion(baseEntity.getVersion() + 1);
            }
        }
        return mapper.update(data);
    }

    @Override
    public int save(T data) {
        BaseEntity baseEntity = (BaseEntity) data;
        if (baseEntity.getId() == null) {
            return insert(data);
        }

        T obj = findById(baseEntity.getId());
        if (obj == null) {
            return insert(data);
        }

        BeanUtils.copyPropertiesIgnoreNull(data, obj);
        return update(obj);
    }

    @Override
    public int deleteById(Long id) {
        BaseMapper<T> mapper = getMapper();
        if (mapper == null) return 0;
        return mapper.deleteById(id);
    }

    @Override
    public int deleteByIds(List<Long> ids) {
        BaseMapper<T> mapper = getMapper();
        if (mapper == null) return 0;
        return mapper.deleteByIds(ids);
    }
}
