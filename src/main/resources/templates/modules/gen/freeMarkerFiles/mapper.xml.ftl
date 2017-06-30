<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="${packageName}.${moduleName}.dao.${ClassName}Dao">

<#if enableCache?? && enableCache >
	<!-- 开启二级缓存 -->
	<cache type="org.mybatis.caches.ehcache.LoggingEhcache"/>

</#if>
<#if baseResultMap?? && baseResultMap >
	<!-- 通用查询映射结果 -->
	<resultMap id="BaseResultMap" type="${packageName}.${moduleName}.model.${ClassName}">
		<#--主键排在第一位-->
	<#list table.columnList as c>
	<#if table.tableColumnPk?? && table.tableColumnPk.name == c.name>
		<id column="${c.name}" property="${c.simpleJavaField}" />
	<#else >
        <result column="${c.name}" property="${c.simpleJavaField}" />
	</#if>
	</#list>
	</resultMap>

</#if>
    <!-- 通用查询结果列 -->
    <sql id="Base_Column_List">
	${table.fieldNames}
    </sql>

    <!-- 通用複雜查询结果语句 -->
    <sql id="Base_Complex_Query">
	SELECT
	<choose>
		<when test="ew != null and ew.sqlSelect != null">${'$'}{ew.sqlSelect}</when>
		<otherwise>
			<include refid="Base_Column_List"></include>
		</otherwise>
	</choose>
	FROM ${table.name} mt
	<where>
		<if test="ew!=null">
			<if test="ew.entity!=null">
			<#list table.columnList as c>
				<#if "is_delete" != c.name>
				<if test="ew.entity.${c.simpleJavaField} !=null and ew.entity.${c.simpleJavaField} != ''"> AND mt.${c.name} ${c.queryConditionByType}</if>
				</#if>
			</#list>
			</if>
		</if>
	<#list table.columnList as c>
		<#if "is_delete" == c.name>
		AND mt.${c.name}= 'N'
		</#if>
	</#list>
		<if test="ew!=null">
            <if test="ew!=null and ew.sqlSegment!=null and ew.notEmptyOfWhere">${'$'}{ew.sqlSegment}</if>
		</if>
	</where>
	<if test="ew!=null and ew.sqlSegment!=null and ew.emptyOfWhere">${'$'}{ew.sqlSegment}</if>
    </sql>

    <select id="selectComplexPage" resultType="${packageName}.${moduleName}.model.${ClassName}">
		<include refid="Base_Complex_Query"></include>
    </select>

    <select id="selectComplexMapsPage" resultType="map">
        <include refid="Base_Complex_Query"></include>
    </select>

    <select id="selectComplexList" resultType="${packageName}.${moduleName}.model.${ClassName}">
        <include refid="Base_Complex_Query"></include>
    </select>
</mapper>
