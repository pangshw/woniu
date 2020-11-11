CREATE TABLE IF NOT EXISTS ${table} (
    id bigint NOT NULL AUTO_INCREMENT,
    version bigint DEFAULT NULL,
    create_time datetime DEFAULT NULL,
    update_time datetime DEFAULT NULL,
<#list fields as attr>
    <#if attr.dataType == 'String'>
    ${attr.tableField} varchar(${attr.size}) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '${attr.name}',
    </#if>
    <#if attr.dataType == 'Integer' && attr.size == 64 >
    ${attr.tableField} bigint DEFAULT NULL COMMENT '${attr.name}',
    </#if>
    <#if attr.dataType == 'Integer' && attr.size == 32 >
    ${attr.tableField} int DEFAULT NULL COMMENT '${attr.name}',
    </#if>
    <#if attr.dataType == 'Date' >
    ${attr.tableField} date DEFAULT NULL COMMENT '${attr.name}',
    </#if>
    <#if attr.dataType == 'DateTime' >
        ${attr.tableField} datetime DEFAULT NULL COMMENT '${attr.name}',
    </#if>
</#list>
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='${name}';

