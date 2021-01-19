package com.woniu.generator.infra.repository.impl;

import com.woniu.domain.BaseMapper;
import com.woniu.domain.impl.BaseRepositoryImpl;
import com.woniu.generator.domain.entity.DevMetadataColumn;
import com.woniu.generator.domain.repository.DevMetadataColumnRepository;
import com.woniu.generator.infra.mapper.DevMetadataColumnMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 元数据列Service业务层处理
 */
@Service
public class DevMetadataColumnRepositoryImpl extends BaseRepositoryImpl<DevMetadataColumn> implements DevMetadataColumnRepository {
    @Autowired
    private DevMetadataColumnMapper devMetadataColumnMapper;

    @Override
    public BaseMapper<DevMetadataColumn> getMapper() {
        return devMetadataColumnMapper;
    }

    @Override
    public List<DevMetadataColumn> findAllByMetadataId(Long metadataId) {
        return devMetadataColumnMapper.findAllByMetadataId(metadataId);
    }

    @Override
    public int deleteByMetadataId(Long metadataId) {
        return devMetadataColumnMapper.deleteByMetadataId(metadataId);
    }
}
