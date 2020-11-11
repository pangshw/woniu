package com.woniu.vo;

import io.swagger.annotations.ApiModelProperty;

public class PivotRequest {

    @ApiModelProperty("行分级")
    private String rowGroup;

    @ApiModelProperty("列分组")
    private String  columnGroup;

    public String getRowGroup() {
        return rowGroup;
    }

    public void setRowGroup(String rowGroup) {
        this.rowGroup = rowGroup;
    }

    public String getColumnGroup() {
        return columnGroup;
    }

    public void setColumnGroup(String columnGroup) {
        this.columnGroup = columnGroup;
    }
}
