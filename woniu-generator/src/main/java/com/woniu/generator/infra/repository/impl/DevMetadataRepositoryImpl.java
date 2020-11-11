package com.woniu.generator.infra.repository.impl;


import com.woniu.domain.BaseMapper;
import com.woniu.domain.impl.BaseRepositoryImpl;
import com.woniu.generator.domain.dto.MetaDTO;
import com.woniu.generator.domain.entity.DevMetadata;
import com.woniu.generator.domain.entity.DevMetadataColumn;
import com.woniu.generator.domain.repository.DevMetadataColumnRepository;
import com.woniu.generator.domain.repository.DevMetadataRepository;
import com.woniu.generator.infra.mapper.DevMetadataMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 元数据Service业务层处理
 */
@Service
public class DevMetadataRepositoryImpl extends BaseRepositoryImpl<DevMetadata> implements DevMetadataRepository {

    @Autowired
    private DevMetadataMapper metadataMapper;
    @Autowired
    private DevMetadataColumnRepository metadataColumnRepository;

    @Override
    public BaseMapper<DevMetadata> getMapper() {
        return metadataMapper;
    }

    @Override
    public MetaDTO getMetaById(Long id) {
        DevMetadata metadata = metadataMapper.findById(id);
        if (metadata == null) {
            return null;
        }

        MetaDTO metaDTO = new MetaDTO();
        BeanUtils.copyProperties(metadata, metaDTO);

        metaDTO.setColumns(new ArrayList<>());
        List<DevMetadataColumn> columns = metadataColumnRepository.findAllByMetadataId(metaDTO.getId());
        for (DevMetadataColumn column : columns) {
            MetaDTO.MetaColumnDTO columnDTO = new MetaDTO.MetaColumnDTO();
            BeanUtils.copyProperties(column, columnDTO);
            metaDTO.getColumns().add(columnDTO);
        }
        return metaDTO;
    }

    @Override
    public int saveMeta(MetaDTO model) {
        DevMetadata metadata = new DevMetadata();
        BeanUtils.copyProperties(model, metadata);
        int row = this.save(metadata);
        if (row == 0) return row;

        if (model.getColumns() != null) {
            for (MetaDTO.MetaColumnDTO columnDTO : model.getColumns()) {
                DevMetadataColumn column = new DevMetadataColumn();
                BeanUtils.copyProperties(columnDTO, column);
                column.setMetadataId(metadata.getId());
                metadataColumnRepository.save(column);
            }
        }

        return row;
    }

    @Override
    public void deleteMeta(List<Long> ids) {
        for (Long id : ids) {
            metadataColumnRepository.deleteByMetadataId(id);
            this.deleteById(id);
        }
    }
}
