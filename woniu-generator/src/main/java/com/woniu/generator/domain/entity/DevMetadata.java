package com.woniu.generator.domain.entity;

import com.woniu.domain.BaseEntity;
import io.swagger.annotations.ApiModelProperty;

/**
 * 元数据对象 dev_metadata
 */
public class DevMetadata extends BaseEntity {

    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("表名称")
    private String tableName;

    @ApiModelProperty("类名称")
    private String className;

    @ApiModelProperty("包名")
    private String packageName;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }
}
