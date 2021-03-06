package ${packageName}.${moduleName}.web;
import com.sendinfo.framework.boot.model.Pager;
import com.sendinfo.framework.core.model.ResponseBean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ${packageName}.${moduleName}${pkgBizModuleName}.model.${ClassName};
import ${packageName}.${moduleName}${pkgBizModuleName}.service.dto.${ClassName}DTO;
import ${packageName}.${moduleName}${pkgBizModuleName}.service.mapper.${ClassName}Convert;
import ${packageName}.${moduleName}${pkgBizModuleName}.service.${ClassName}Service;

import java.util.List;

/**
 * <p>
 * ${functionName}
 * </p>
 *
 * @author ${functionAuthor}
 * @date ${functionVersion}
 */
@RestController
@RequestMapping("/api/${table.className?uncap_first}")
public class ${ClassName}Controller{
   /**
   * 日志打印
   */
   private final Logger logger = LoggerFactory.getLogger(this.getClass());

   @Autowired
   private ${ClassName}Service baseService;

   @Autowired
   private ${ClassName}Convert ${ClassName?uncap_first}Convert;

    <#if category ?? && category=='curd_many' && (table.childList?size>0)>
    <#list table.childList as c>
    @Autowired
    private ${c.className?cap_first}Service ${c.className?uncap_first}Service;

    </#list>
    </#if>
    /**
     * 分页查询
     * @param model 分页对象
     * @param page 分页对象
     * @return ResponseBean
     */
    @GetMapping("/page")
    public ResponseBean page(${ClassName} model,Pager page) {
        EntityWrapper<${ClassName}> entityWrapper = new EntityWrapper<>(model);
        Pager<${ClassName}> rs = this.baseService.selectComplexPage(page, entityWrapper);
        return ResponseBean.ok().body(rs);
    }

    /**
     * 获取详情 （修改时查询用）
     * @param key 查询key
     * @return ResponseBean
     */
    @GetMapping("/{key}")
    public ResponseBean getModel(@PathVariable String key) {
        ${ClassName} model = this.baseService.selectById(key);
        <#if category ?? && category=='curd_many' && (table.childList?size>0)>
        <#list table.childList as c>
        ${c.className?cap_first} ${c.className?uncap_first} = new ${c.className?cap_first}();
        ${c.className?uncap_first}.set${table.camelCaseParentTableFk}(model.get${table.tableColumnPk.name?cap_first}());
        ${className}.set${c.className?cap_first}List(${c.className?uncap_first}Service.selectComplexList(new EntityWrapper()(${c.className?uncap_first})));
        </#list>
        </#if>
        if(null==model){
            return ResponseBean.badRequest("该数据不存在").build();
        }
        return ResponseBean.ok().body(model);
    }

    /**
     * 获取详情
     * @param key 查询key
     * @return ResponseBean
     */
    @GetMapping("/view")
    public ResponseBean get(@RequestParam String key) {
        return getModel(key);
    }

    /**
     * 新增
     * @param dto 实体信息
     * @return ResponseBean
     */
    @PostMapping
    public ResponseBean add(@Validated @RequestBody ${ClassName}DTO dto) {
        ${ClassName} model = ${ClassName?uncap_first}Convert.${className}DTOTo${ClassName}(dto);
        return saveOrUpdate(model);
    }

    /**
     * 更新
     * @param dto 实体信息
     * @return ResponseBean
     */
    @PutMapping
    public ResponseBean update(@Validated @RequestBody ${ClassName}DTO dto) {
        ${ClassName} model = ${ClassName?uncap_first}Convert.${className}DTOTo${ClassName}(dto);
        return saveOrUpdate(model);
    }

    /**
     * 删除
     * @param list 删除ids
     * @return ResponseBean
     */
    @DeleteMapping
    public ResponseBean deletes(@RequestBody List<String> list) {
        boolean batchIds = this.baseService.deleteBatchIds(list);
        return ResponseBean.ok().build();
    }

    /**
     * 保存或更新
     * @param model 实体信息
     * @return  ResponseBean
     */
    private ResponseBean saveOrUpdate(${ClassName} model) {
    <#if category?? && category=='curd_many'&& (table.childList?size>0)>
        boolean success = this.baseService.saveOrUpdateAndChild(model);
    <#else >
        boolean success = this.baseService.saveOrUpdate(model);
    </#if >
        return ResponseBean.ok().build();
    }


}
