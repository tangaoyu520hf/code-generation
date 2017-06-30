/**
* Copyright (c) 中软国际科技服务（湖南）有限公司
* FileName: ${ClassName}.java
* Author:   ${functionAuthor}
* Date:     ${functionVersion}
*/

package ${packageName}.${moduleName}.model;

<#list table.importList as i>
import ${i};
</#list>
/**
* <p>
* ${functionName}
* </p>
* @author ${functionAuthor}
* @since ${functionVersion}
*/
@TableName("${table.name}")
public class ${ClassName} <#--extends Model<${ClassName}>--> {

    private static final long serialVersionUID = 1L;
<#-- 生成字段属性 -->
<#list table.columnList as c>

    <#if c.comments??>
    /**
    * ${c.comments}
    */
    </#if>
	<#-- 主键 -->
    <#if table.tableColumnPk?? && table.tableColumnPk.name == c.name>
    @TableId("${c.name}")
    <#-- 其它字段 -->
    <#elseif "createTime" == c.simpleJavaField || "createId" == c.simpleJavaField>
    @TableField(value = "${c.name}", fill = FieldFill.INSERT)
    <#elseif "updateTime" == c.simpleJavaField || "updateId" == c.simpleJavaField>
    @TableField(value = "${c.name}", fill = FieldFill.UPDATE)
    <#else>
    @TableField("${c.name}")
    </#if>
    <#-- 校验 主键及公共字段不需要加校验 -->
    <#if table.tableColumnPk?? && table.tableColumnPk.name != c.name && c.isNotBaseField >
    <#list c.simpleAnnotationList as a>
    @${a}
    </#list>
    </#if>
    <#if "is_delete" == c.name>
    @TableLogic
    </#if>
    private ${c.simpleJavaType} ${c.simpleJavaField};
</#list>
<#-- 范围条件字段 -->
<#list table.columnList as c>
    <#if c.isQuery?? && c.isQuery == "1" && c.queryType == "between">
    private ${c.simpleJavaType} begin${c.simpleJavaField?cap_first};		<#if c.comments??>// 开始 ${c.comments}</#if>
    private ${c.simpleJavaType} end${c.simpleJavaField?cap_first};		<#if c.comments??>// 结束 ${c.comments}</#if>
    </#if>
</#list>
<#-- 子表列表字段 -->
<#list table.childList as c>
private List<${c.className?cap_first}> ${c.className?uncap_first}List = null;		// 子表列表
</#list>
<#-- 生成get和set方法 -->
<#list table.columnList as c>

    <#if c.comments??>
    /**
    * 获取${c.comments}
    */
    </#if>
    <#-- 其它字段 -->
    <#if "Date" == c.simpleJavaType>
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    </#if>
    public ${c.simpleJavaType} get${c.simpleJavaField?cap_first}() {
        return ${c.simpleJavaField};
    }

    <#if c.comments??>
    /**
    * 设置${c.comments}
    */
    </#if>
    public void set${c.simpleJavaField?cap_first}(${c.simpleJavaType} ${c.simpleJavaField}) {
        this.${c.simpleJavaField} = ${c.simpleJavaField};
    }
</#list>
<#-- 范围条件字段get和set方法 -->
<#list table.columnList as c>
    <#if c.isQuery?? && c.isQuery == "1" && c.queryType == "between">
    public ${c.simpleJavaType} getBegin${c.simpleJavaField?cap_first}() {
        return begin${c.simpleJavaField?cap_first};
    }

    public void setBegin${c.simpleJavaField?cap_first}(${c.simpleJavaType} begin${c.simpleJavaField?cap_first}) {
        this.begin${c.simpleJavaField?cap_first} = begin${c.simpleJavaField?cap_first};
    }

    public ${c.simpleJavaType} getEnd${c.simpleJavaField?cap_first}() {
        return end${c.simpleJavaField?cap_first};
    }

    public void setEnd${c.simpleJavaField?cap_first}(${c.simpleJavaType} end${c.simpleJavaField?cap_first}) {
        this.end${c.simpleJavaField?cap_first} = end${c.simpleJavaField?cap_first};
    }
    </#if>
</#list>
<#-- 子表列表get和set方法 -->
<#list table.childList as c>
    public List<${c.className?cap_first}> get${c.className?cap_first}List() {
        return ${c.className?uncap_first}List;
    }

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
