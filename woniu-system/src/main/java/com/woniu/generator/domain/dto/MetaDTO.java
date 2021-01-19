package com.woniu.generator.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.woniu.generator.domain.entity.DevMetadata;
import com.woniu.generator.domain.entity.DevMetadataColumn;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

public class MetaDTO extends DevMetadata {

    @ApiModelProperty("列定义")
    private List<MetaColumnDTO> columns;

    /**
     * 生成controller时的url路径
     */
    @JsonIgnore
    private String urlPath;
    /**
     * 首字母大写的名字
     */
    @JsonIgnore
    private String domain;
    /**
     * 首字母小写的名字
     */
    @JsonIgnore
    private String camelhump;

    public static class MetaColumnDTO extends DevMetadataColumn {

        /**
         * java get 方法名称
         */
        @JsonIgnore
        private String javaGetCode;
        /**
         * java set 方法名称
         */
        @JsonIgnore
        private String javaSetCode;

        public String getJavaGetCode() {
            return javaGetCode;
        }

        public void setJavaGetCode(String javaGetCode) {
            this.javaGetCode = javaGetCode;
        }

        public String getJavaSetCode() {
            return javaSetCode;
        }

        public void setJavaSetCode(String javaSetCode) {
            this.javaSetCode = javaSetCode;
        }
    }

    public List<MetaColumnDTO> getColumns() {
        return columns;
    }

    public void setColumns(List<MetaColumnDTO> columns) {
        this.columns = columns;
    }

    public String getUrlPath() {
        return urlPath;
    }

    public void setUrlPath(String urlPath) {
        this.urlPath = urlPath;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getCamelhump() {
        return camelhump;
    }

    public void setCamelhump(String camelhump) {
        this.camelhump = camelhump;
    }
}
