package com.woniu.vo;

import io.swagger.annotations.ApiModelProperty;

public class PageRequest {
    @ApiModelProperty(value = "当前第几页", example = "1", required = true)
    private int pageIndex;
    @ApiModelProperty(value = "每页数据行数", example = "20", required = true)
    private int pageSize;

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
