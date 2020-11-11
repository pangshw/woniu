package com.woniu.generator.infra.mapper;

import com.woniu.domain.BaseMapper;
import com.woniu.generator.domain.entity.DevMetadataColumn;

import java.util.List;

/**
 * 元数据列Mapper接口
 */
public interface DevMetadataColumnMapper extends BaseMapper<DevMetadataColumn> {

    List<DevMetadataColumn> findAllByMetadataId(Long metadataId);

    int deleteByMetadataId(Long metadataId);
}
