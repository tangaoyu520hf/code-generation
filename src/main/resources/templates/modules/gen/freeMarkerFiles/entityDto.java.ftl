/*
* Copyright (c) 中软国际科技服务（湖南）有限公司
* FileName: ${ClassName}Dto.java
* Author:   ${functionAuthor}
* Date:     ${functionVersion}
*/
package ${packageName}.${moduleName}.model.dto;

<#list table.importList as i>
import ${i};
</#list>
/**
* <p>
* ${functionName}
* </p>
* @author ${functionAuthor}
* @date ${functionVersion}
*/
public class ${ClassName}Dto <#--extends Model<${ClassName}>--> {

<#-- 生成字段属性 -->
<#list table.columnList as c>

    <#if c.comments??>
    /**
    * ${c.comments}
    */
    </#if>
    <#-- 公共字段不需要生成 -->
    <#if c.isNotBaseField >
    <#-- 校验 主键不需要加校验 -->
    <#if table.tableColumnPk?? && table.tableColumnPk.name != c.name >
    <#list c.simpleAnnotationList as a>
    @${a}
    </#list>
    </#if>
    <#if "Date" == c.simpleJavaType>
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    </#if>
    private ${c.simpleJavaType} ${c.simpleJavaField};
    </#if>
</#list>
<#-- 范围条件字段 -->
<#list table.columnList as c>
    <#if c.isQuery?? && c.isQuery == "1" && c.queryType == "between">

    <#if "Date" == c.simpleJavaType>
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    </#if>
    private ${c.simpleJavaType} begin${c.simpleJavaField?cap_first};		<#if c.comments??>// 开始 ${c.comments}</#if>
    <#if "Date" == c.simpleJavaType>
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    </#if>
    private ${c.simpleJavaType} end${c.simpleJavaField?cap_first};		<#if c.comments??>// 结束 ${c.comments}</#if>
    </#if>
</#list>
<#-- 子表列表字段 -->
<#list table.childList as c>
private List<${c.className?cap_first}> ${c.className?uncap_first}List = null;		// 子表列表
</#list>
<#-- 生成get和set方法 -->
<#list table.columnList as c>

    <#if c.isNotBaseField >

    <#if c.comments??>
    /**
    * 获取${c.comments}
    * @return ${c.comments}
    */
    </#if>
    public ${c.simpleJavaType} get${c.simpleJavaField?cap_first}() {
        return ${c.simpleJavaField};
    }

    <#if c.comments??>
    /**
    * 设置${c.comments}
    * @param ${c.simpleJavaField} ${c.comments}
    */
    </#if>
    public void set${c.simpleJavaField?cap_first}(${c.simpleJavaType} ${c.simpleJavaField}) {
        this.${c.simpleJavaField} = ${c.simpleJavaField};
    }
    </#if>
</#list>
<#-- 范围条件字段get和set方法 -->
<#list table.columnList as c>
    <#if c.isQuery?? && c.isQuery == "1" && c.queryType == "between">

    /**
    * 获取开始
    * @return 开始
    */
    public ${c.simpleJavaType} getBegin${c.simpleJavaField?cap_first}() {
        return begin${c.simpleJavaField?cap_first};
    }
    /**
    * 设置开始
    * @param begin${c.simpleJavaField?cap_first} 开始
    */
    public void setBegin${c.simpleJavaField?cap_first}(${c.simpleJavaType} begin${c.simpleJavaField?cap_first}) {
        this.begin${c.simpleJavaField?cap_first} = begin${c.simpleJavaField?cap_first};
    }

    /**
    * 获取结束
    * @return 结束
    */
    public ${c.simpleJavaType} getEnd${c.simpleJavaField?cap_first}() {
        return end${c.simpleJavaField?cap_first};
    }
    /**
    * 设置结束
    * @param end${c.simpleJavaField?cap_first} 结束
    */
    public void setEnd${c.simpleJavaField?cap_first}(${c.simpleJavaType} end${c.simpleJavaField?cap_first}) {
        this.end${c.simpleJavaField?cap_first} = end${c.simpleJavaField?cap_first};
    }
    </#if>
</#list>
<#-- 子表列表get和set方法 -->
<#list table.childList as c>

    /**
    * 获取子集
    * @return 子集
    */
    public List<${c.className?cap_first}> get${c.className?cap_first}List() {
        return ${c.className?uncap_first}List;
    }

    /**
    * 设置子集
    * @param ${c.className?uncap_first}List 子集
    */
    public void set${c.className?cap_first}List(List<${c.className?cap_first}> ${c.className?uncap_first}List) {
        this.${c.className?uncap_first}List = ${c.className?uncap_first}List;
    }
</#list>


<#--<#if table.tableColumnPk??>
    @Override
    protected Serializable pkVal() {
        return this.${table.tableColumnPk.simpleJavaField};
    }
</#if>-->
}
