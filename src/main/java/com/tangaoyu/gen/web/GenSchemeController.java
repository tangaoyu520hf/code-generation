package com.tangaoyu.gen.web;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.tangaoyu.gen.model.*;
import com.tangaoyu.gen.service.GenSchemeService;
import com.tangaoyu.gen.service.TableColumnService;
import com.tangaoyu.gen.service.TableService;
import com.tangaoyu.gen.util.GenUtils;
import com.tangaoyu.gen.util.StringUtils;
import javafx.scene.control.Tab;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 生成方案 前端控制器
 * </p>
 *
 * @author tangaoyu
 * @since 2017-05-14
 */
@RestController
@RequestMapping("/genScheme")
public class GenSchemeController {
    @Autowired
    private GenSchemeService genSchemeService;

    @Autowired
    private TableService tableService;

    @Autowired
    private TableColumnService tableColumnService;

    @GetMapping("page")
    public ResponseEntity page(GenScheme genScheme, Page page) {
        page = genSchemeService.selectPage(page,new EntityWrapper<>(genScheme));
        return ResponseEntity.ok().body(page);
    }

    /**
     * 生成方法详情相关数据信息
     * @return
     */
    @GetMapping("viewData")
    public ResponseEntity viewData(GenScheme genScheme){
        Map<String,Object> model = new HashMap();
        if(StringUtils.isNotBlank(genScheme.getId())){
            genScheme = genSchemeService.selectById(genScheme.getId());
        }
        if (StringUtils.isBlank(genScheme.getPackageName())){
            genScheme.setPackageName("com.thinkgem.jeesite.modules");
        }
        model.put("genScheme", genScheme);
        model.put("config", GenUtils.getConfig());
        model.put("tableList", tableService.selectList(new EntityWrapper<>()));
        return ResponseEntity.ok().body(model);
    }

    /**
     * 保存生成方案
     * @param genScheme
     * @return
     */
    @PostMapping("save")
    public ResponseEntity save(@Validated @RequestBody GenScheme genScheme){
        this.genSchemeService.insertOrUpdate(genScheme);
        //生成代码
        if(genScheme.getGen()){
            generateCode(genScheme);
        }
        return ResponseEntity.ok().body(null);
    }

    /**
     * 生成代码
     * @param genScheme
     * @return
     */
    @GetMapping("generateCode")
    public ResponseEntity generateTheCode(GenScheme genScheme){
        GenScheme queryGenScheme = genSchemeService.selectById(genScheme.getId());
        queryGenScheme.setGen(genScheme.getGen());
        //生成代码
        generateCode(queryGenScheme);
        return ResponseEntity.ok().body(null);
    }


    /**
     * 代码生成
     * @param genScheme
     * @return
     */
    private String generateCode(GenScheme genScheme){

        StringBuilder result = new StringBuilder();

        // 查询主表及字段列
        Table genTable = tableService.getTableAndColumnsById(genScheme.getGenTableId());

        // 获取模板列表
        List<GenTemplate> templateList = GenUtils.getTemplateList(genScheme.getCategory(), false);
/*        List<GenTemplate> childTableTemplateList = GenUtils.getTemplateList(genScheme.getCategory(), true);

        // 如果有子表模板，则需要获取子表列表
        if (childTableTemplateList.size() > 0){
            Table parentTable = new Table();
            parentTable.setParentTable(genTable.getName());
            
            List<Table> childTables = tableService.selectList(new EntityWrapper<>(parentTable));
            childTables.forEach(childTable -> {
                // 生成子表模板代码
                childTable = tableService.getTableAndColumnsById(childTable.getId());
                childTable.setParent(genTable);
                genScheme.setGenTable(childTable);
                Map<String, Object> childTableModel = GenUtils.getDataModel(genScheme);
                for (GenTemplate tpl : childTableTemplateList){
                    result.append(GenUtils.generateToFile(tpl, childTableModel, genScheme.getReplaceFile()));
                }
            });
            genTable.setChildList(childTables);
        }*/

        // 生成主表模板代码
        genScheme.setGenTable(genTable);
        Map<String, Object> model = GenUtils.getDataModel(genScheme);
        for (GenTemplate tpl : templateList){
            result.append(GenUtils.generateToFile(tpl, model, genScheme.getReplaceFile()));
        }
        return result.toString();
    }
}
