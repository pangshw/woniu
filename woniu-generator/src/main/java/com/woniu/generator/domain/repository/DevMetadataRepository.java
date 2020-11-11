package com.woniu.generator.domain.repository;

import com.woniu.domain.BaseRepository;
import com.woniu.generator.domain.dto.MetaDTO;
import com.woniu.generator.domain.entity.DevMetadata;

import java.util.List;

/**
 * 元数据Service接口
 */
public interface DevMetadataRepository extends BaseRepository<DevMetadata> {

    MetaDTO getMetaById(Long id);

    int saveMeta(MetaDTO model);

    /**
     * 批量删除，连明细列一起删除
     *
     * @param ids
     * @return
     */
    void deleteMeta(List<Long> ids);
}
