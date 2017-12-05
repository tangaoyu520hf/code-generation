i18n: {
  messages: {
    ch: {
      ${ClassName?uncap_first}:{
        <#list table.columnList as c>
        ${c.simpleJavaField}:'<#if c.comments??>${c.comments}</#if>',
        </#list>
      },
    validate:{
        <#list table.columnList as c>
        ${c.simpleJavaField}:'请输入<#if c.comments??>${c.comments}</#if>',
        </#list>
      },
    }
  }
}
