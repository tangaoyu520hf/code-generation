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
<#-- 如果有逻辑删除 则使用mytatisPlus逻辑删除 -->
<#list table.columnList as c>
<#if "delete_flag" == c.name>
import com.baomidou.mybatisplus.annotations.TableLogic;
</#if>
</#list>
/**
* <p>
* ${functionName}
* </p>
* @author ${functionAuthor}
* @since ${functionVersion}
*/
@TableName("${table.name}")
public class ${ClassName} extends Model<${ClassName}> {

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
    <#elseif !c.isNotBaseField>
    @TableField(value = "${c.name}", validate = FieldStrategy.IGNORED)
    <#else>
    @TableField("${c.name}")
    </#if>
    <#-- 校验 主键及公共字段不需要加校验 -->
    <#if table.tableColumnPk?? && table.tableColumnPk.name != c.name && c.isNotBaseField >
    <#list c.simpleAnnotationList as a>
    @${a}
    </#list>
    </#if>
    <#if "delete_flag" == c.name>
    @TableLogic
    </#if>
    private ${c.simpleJavaType} ${c.simpleJavaField};
</#list>

<#-- 生成get和set方法 -->
<#list table.columnList as c>

    <#-- 其它字段 -->
    public ${c.simpleJavaType} get${c.simpleJavaField?cap_first}() {
        return ${c.simpleJavaField};
    }

    public void set${c.simpleJavaField?cap_first}(${c.simpleJavaType} ${c.simpleJavaField}) {
        this.${c.simpleJavaField} = ${c.simpleJavaField};
    }
</#list>

<#if table.tableColumnPk??>
    @Override
    protected Serializable pkVal() {
        return this.${table.tableColumnPk.simpleJavaField};
    }
</#if>
}
