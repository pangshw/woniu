<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="${package}.infra.mapper.${domain}Mapper">

    <resultMap type="${entity}" id="${domain}Result">
<#list fields as attr>
        <result property="${attr.javaField}" column="${attr.tableField}"/>
</#list>
    </resultMap>

    <sql id="select${domain}Vo">
        select id, version, <#list fields as attr>${attr.tableField}, </#list>create_time, update_time from ${table}
    </sql>

    <select id="list" parameterType="${entity}" resultMap="${domain}Result">
        <include refid="select${domain}Vo"/>
        <where>
<#list fields as attr>
    <#if attr.dataType == 'String'>
            <if test="${attr.javaField} != null and ${attr.javaField} != ''"> and ${attr.tableField} like concat('%', ${r"#{"}${attr.javaField}${r"}"}, '%') </if>
    <#else>
            <if test="${attr.javaField} != null"> and ${attr.tableField} = ${r"#{"}${attr.javaField}${r"}"} </if>
    </#if>
</#list>
        </where>
    </select>

    <select id="findById" parameterType="Long" resultMap="${domain}Result">
        <include refid="select${domain}Vo"/>
        where id = ${r"#{"}id${r"}"}
    </select>

    <insert id="insert" parameterType="${entity}" useGeneratedKeys="true" keyProperty="id">
        insert into ${table}
        <trim prefix="(" suffix=")" suffixOverrides=",">
            <if test="version != null">version,</if>
            <if test="createTime != null">create_time,</if>
            <if test="updateTime != null">update_time,</if>
<#list fields as attr>
            <if test="${attr.javaField} != null">${attr.tableField},</if>
</#list>
        </trim>
        <trim prefix="values (" suffix=")" suffixOverrides=",">
            <if test="version != null">${r"#{"}version${r"}"},</if>
            <if test="createTime != null">${r"#{"}createTime${r"}"},</if>
            <if test="updateTime != null">${r"#{"}updateTime${r"}"},</if>
<#list fields as attr>
            <if test="${attr.javaField} != null">${r"#{"}${attr.javaField}${r"}"},</if>
</#list>
        </trim>
    </insert>

    <update id="update" parameterType="${entity}">
        update ${table}
        <trim prefix="SET" suffixOverrides=",">
            <if test="version != null">version = ${r"#{"}version${r"}"},</if>
            <if test="createTime != null">create_time = ${r"#{"}createTime${r"}"},</if>
            <if test="updateTime != null">update_time = ${r"#{"}updateTime${r"}"},</if>
<#list fields as attr>
            <if test="${attr.javaField} != null">${attr.tableField} = ${r"#{"}${attr.javaField}${r"}"},</if>
</#list>
        </trim>
        where id = ${r"#{"}id${r"}"}
    </update>

    <delete id="deleteById" parameterType="Long">
        delete from ${table} where id = ${r"#{"}id${r"}"}
    </delete>

    <delete id="deleteByIds" parameterType="String">
        delete from ${table} where id in
        <foreach item="id" collection="list" open="(" separator="," close=")">
            ${r"#{"}id${r"}"}
        </foreach>
    </delete>
</mapper>
