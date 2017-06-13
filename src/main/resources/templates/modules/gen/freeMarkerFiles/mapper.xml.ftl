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
</mapper>
