package com.woniu.generator.api.controller.dto;

import io.swagger.annotations.ApiModelProperty;

public class SimpleDTO {

    @ApiModelProperty("标题")
    private String title;
    @ApiModelProperty("正文")
    private String content;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
