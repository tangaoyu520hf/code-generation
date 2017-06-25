/**
* Copyright (c) 中软国际科技服务（湖南）有限公司
* FileName: ${ClassName}Service.java
* Author:   ${functionAuthor}
* Date:     ${functionVersion}
*/
package ${packageName}.${moduleName}.service.impl;

import ${packageName}.${moduleName}.model.${ClassName};
import ${packageName}.${moduleName}.dao.${ClassName}Dao;
import ${packageName}.${moduleName}.service.${ClassName}Service;
import ${superServiceImplClassPackage};
import org.springframework.stereotype.Service;

 /**
 * <p>
 * ${functionName} 服务实现类
 * </p>
 *
 * @author ${functionAuthor}
 * @since ${functionVersion}
 */
@Service
public class ${ClassName}ServiceImpl extends ${superServiceImplClass}<${ClassName}Dao, ${ClassName}> implements ${ClassName}Service {

<#list table.childList as c>
    @Autowired
    private ${c.className?cap_first}Service ${c.className?uncap_first}Service;

</#list>
    @Transactional
    boolean saveOrUpdateAndChild(${ClassName} ${className}) {
        this.insertOrUpdate(entity);
    <#list table.childList as c>
        List<${c.className?uncap_first}> ${c.className?uncap_first}List = ${className}.get${c.className?cap_first}List()
        if(null!=${c.className?uncap_first}List && ${c.className?uncap_first}List.size()>0){
            ${c.className?uncap_first}List.forEach(${c.className?uncap_first}->{
                <#list c.columnList as cc>
                <#if c.parentTableFk == cc.name>
                    <#if table.tableColumnPk??>
                    ${c.className?uncap_first}.set${cc.simpleJavaField?cap_first}(${className}.get${table.tableColumnPk.name?cap_first}());
                    </#if>
                </#if>
                </#list>
            });
            ${c.className?uncap_first}Service.insertOrUpdateBatch(${c.className?uncap_first}List)
        }
    </#list>
        return true;
    }
}
