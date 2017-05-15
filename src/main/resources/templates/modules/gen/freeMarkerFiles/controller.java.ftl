/**
* Copyright (c) 中软国际科技服务（湖南）有限公司
* FileName: ${ClassName}.java
* Author:   ${functionAuthor}
* Date:     ${functionVersion}
*/
package ${packageName}.${moduleName}.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.*;
import com.icss.cloud.common.web.controller.BaseOperateController;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.icss.cloud.common.model.ResponseBean;
import com.baomidou.mybatisplus.plugins.Page;
import org.apache.commons.collections.CollectionUtils;
import ${packageName}.${moduleName}.model.${ClassName};
import ${packageName}.${moduleName}.service.${ClassName}Service;

import java.util.List;

/**
 * <p>
 * ${functionName} 前端控制器
 * </p>
 *
 * @author ${functionAuthor}
 * @since ${functionVersion}
 */
@Controller
@RequestMapping("/${table.className?uncap_first}")
public class ${ClassName}Controller extends BaseOperateController<${ClassName},${ClassName}Service>{



}
