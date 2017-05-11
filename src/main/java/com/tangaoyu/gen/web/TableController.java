package com.tangaoyu.gen.web;

import com.tangaoyu.gen.model.Table;
import com.tangaoyu.gen.service.TableColumnService;
import com.tangaoyu.gen.service.TableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 业务表Controller
 * @author ThinkGem
 * @version 2013-10-15
 */
@RestController
@RequestMapping(value = "table")
public class TableController {

	@Autowired
	private TableService tableService;
	@Autowired
	private TableColumnService tableColumnService;

/*	@RequestMapping(value = {"list", ""})
	public String list(Table genTable, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = UserUtils.getUser();
		if (!user.isAdmin()){
			genTable.setCreateBy(user);
		}
        Page<GenTable> page = genTableService.find(new Page<GenTable>(request, response), genTable); 
        model.addAttribute("page", page);
		return "modules/gen/genTableList";
	}*/

	@GetMapping(value = "form")
	public ResponseEntity form(Table genTable, Model model) {
		List<Table> tableList = tableService.findTableListFormDb(new Table());

		genTable = tableService.getTableFormDb(tableList.get(0));



		return ResponseEntity.ok().body(genTable);
		// 获取物理表列表
/*		List<GenTable> tableList = genTableService.findTableListFormDb(new GenTable());
		model.addAttribute("tableList", tableList);
		// 验证表是否存在
		if (StringUtils.isBlank(genTable.getId()) && !genTableService.checkTableName(genTable.getName())){
			addMessage(model, "下一步失败！" + genTable.getName() + " 表已经添加！");
			genTable.setName("");
		}
		// 获取物理表字段
		else{
			genTable = genTableService.getTableFormDb(genTable);
		}
		model.addAttribute("genTable", genTable);
		model.addAttribute("config", GenUtils.getConfig());
		return "modules/gen/genTableForm";*/

	}

/*	@RequestMapping(value = "save")
	public String save(Table genTable, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, genTable)){
			return form(genTable, model);
		}
		// 验证表是否已经存在
		if (StringUtils.isBlank(genTable.getId()) && !genTableService.checkTableName(genTable.getName())){
			addMessage(model, "保存失败！" + genTable.getName() + " 表已经存在！");
			genTable.setName("");
			return form(genTable, model);
		}
		genTableService.save(genTable);
		addMessage(redirectAttributes, "保存业务表'" + genTable.getName() + "'成功");
		return "redirect:" + adminPath + "/gen/genTable/?repage";
	}
	
	@RequestMapping(value = "delete")
	public String delete(Table genTable, RedirectAttributes redirectAttributes) {
		genTableService.delete(genTable);
		addMessage(redirectAttributes, "删除业务表成功");
		return "redirect:" + adminPath + "/gen/genTable/?repage";
	}*/

}
