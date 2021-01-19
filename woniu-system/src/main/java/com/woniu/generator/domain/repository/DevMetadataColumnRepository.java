package com.woniu.generator.domain.repository;


import com.woniu.domain.BaseRepository;
import com.woniu.generator.domain.entity.DevMetadataColumn;

import java.util.List;

/**
 * 元数据列Service接口
 */
public interface DevMetadataColumnRepository extends BaseRepository<DevMetadataColumn> {

    List<DevMetadataColumn> findAllByMetadataId(Long metadataId);

    int deleteByMetadataId(Long metadataId);
}
