/**
* Copyright (c) 中软国际科技服务（湖南）有限公司
* FileName: ${ClassName}Controller.java
* Author:   ${functionAuthor}
* Date:     ${functionVersion}
*/
package ${packageName}.${moduleName}.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.icss.cloud.product.common.model.ResponseBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ${packageName}.${moduleName}.model.${ClassName};
import ${packageName}.${moduleName}.service.${ClassName}Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

/**
 * <p>
 * ${functionName}
 * </p>
 *
 * @author ${functionAuthor}
 * @since ${functionVersion}
 */
@RestController
@RequestMapping("/${table.className?uncap_first}")
public class ${ClassName}Controller{
   /**
   * 日志打印
   */
   private final Logger logger = LoggerFactory.getLogger(this.getClass());

   @Autowired
   private ${ClassName}Service baseService;

    /**
     * 分页查询
     * @param model 分页对象
     * @param page 分页对象
     * @return ResponseBean
     */
    @GetMapping("/page")
    public ResponseBean page(${ClassName} model,Page page) {
        EntityWrapper<${ClassName}> entityWrapper = new EntityWrapper<>(model);
        Page<${ClassName}> rs = this.baseService.selectComplexPage(page, entityWrapper);
        ResponseBean responseBean = new ResponseBean(rs);
        return responseBean;
    }

    /**
     * 获取详情 （修改时查询用）
     * @param id
     */
    @GetMapping("/{key}")
    public ResponseBean getModel(@PathVariable String key) {
        ${ClassName} model = this.baseService.selectById(key);
        if(null==model){
            return new ResponseBean("IsNotExist","该数据不存在");
        }
        return new ResponseBean(model);
    }

    /**
     * 获取详情
     * @param id
     */
    @GetMapping("/view")
    public ResponseBean get(@RequestParam String key) {
        return getModel(key);
    }

    /**
     * 新增
     * @param model
     * @return
     */
    @PostMapping("/add")
    public ResponseBean addProduct(@Validated @RequestBody ${ClassName} model) {
        return saveOrUpdate(model);
    }

    /**
     * 更新
     * @param model
     * @return
     */
    @PostMapping("/update")
    public ResponseBean updateProduct(@Validated @RequestBody ${ClassName} model) {
        return saveOrUpdate(model);
    }

    /**
     * 删除
     * @param list
     * @return
     */
    @PostMapping("/deletes")
    public ResponseBean deleteProducts(@RequestBody List<String> list) {
        boolean batchIds = this.baseService.deleteBatchIds(list);
        return new ResponseBean();
    }

    /**
     * 保存或更新
     * @param model
     * @return
     */
    private ResponseBean saveOrUpdate(${ClassName} model) {
        boolean success = this.baseService.insertOrUpdate(model);
        return new ResponseBean();
    }
}
