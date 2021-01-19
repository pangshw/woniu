package com.woniu.generator.domain.entity;

import com.woniu.domain.BaseEntity;
import io.swagger.annotations.ApiModelProperty;

/**
 * 元数据列对象 dev_metadata_column
 */
public class DevMetadataColumn extends BaseEntity {

    @ApiModelProperty("元数据ID")
    private Long metadataId;

    @ApiModelProperty("列名称")
    private String name;

    @ApiModelProperty("数据库表字段")
    private String tableField;

    @ApiModelProperty("列类型")
    private String dataType;

    @ApiModelProperty("字段列度")
    private Integer size;

    @ApiModelProperty("字段精度")
    private Integer scale;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("java类型")
    private String javaType;

    @ApiModelProperty("java字段名称")
    private String javaField;

    @ApiModelProperty("顺序号")
    private Integer seqNo;

    public Long getMetadataId() {
        return metadataId;
    }

    public void setMetadataId(Long metadataId) {
        this.metadataId = metadataId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTableField() {
        return tableField;
    }

    public void setTableField(String tableField) {
        this.tableField = tableField;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Integer getScale() {
        return scale;
    }

    public void setScale(Integer scale) {
        this.scale = scale;
    }

    public String getJavaType() {
        return javaType;
    }

    public void setJavaType(String javaType) {
        this.javaType = javaType;
    }

    public String getJavaField() {
        return javaField;
    }

    public void setJavaField(String javaField) {
        this.javaField = javaField;
    }

    public Integer getSeqNo() {
        return seqNo;
    }

    public void setSeqNo(Integer seqNo) {
        this.seqNo = seqNo;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
