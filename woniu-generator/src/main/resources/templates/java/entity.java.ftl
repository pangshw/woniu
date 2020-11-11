package ${package}.domain.entity;

import com.woniu.domain.BaseEntity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;

/**
* ${name}
*/
public class ${entity} extends BaseEntity {
<#list fields as attr>

    /**
    * ${attr.name}
    */
<#if attr.dataType == 'DateTime'>
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
</#if>
<#if attr.dataType == 'Date'>
    @JsonFormat(pattern = "yyyy-MM-dd")
</#if>
    @ApiModelProperty("${attr.name}")
    private ${attr.javaType} ${attr.javaField};
</#list>
<#list fields as attr>

    /**
    * ${attr.name}
    */
    public ${attr.javaType} ${attr.javaGetCode}() {
        return ${attr.javaField};
    }

    public void ${attr.javaSetCode}(${attr.javaType} ${attr.javaField}) {
        this.${attr.javaField} = ${attr.javaField};
    }
</#list>
}
