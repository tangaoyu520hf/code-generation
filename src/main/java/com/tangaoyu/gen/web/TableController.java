package com.tangaoyu.gen.web;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.tangaoyu.gen.model.Table;
import com.tangaoyu.gen.service.TableColumnService;
import com.tangaoyu.gen.service.TableService;
import com.tangaoyu.gen.util.HeaderUtil;
import com.tangaoyu.gen.util.StringUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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

	@GetMapping("page")
	public ResponseEntity page(Table genTable, Page page) {
        page = tableService.selectPage(page,new EntityWrapper<>(genTable));
		return ResponseEntity.ok().body(page);
	}

	/**
	 * 获取数据库中的所有表名
	 * @return
	 */
	@GetMapping("getTablesByDB")
	public ResponseEntity getTablesByDB() {
		List<Table> tableList = tableService.findTableListFormDb(new Table());
		return ResponseEntity.ok().body(tableList);
	}


	@GetMapping("getTablesColumnByDB")
	public ResponseEntity getTablesColumnByDB(Table genTable) {
		//如果tableid为新增 则需要检测表是否已经存在
		if (StringUtils.isBlank(genTable.getId())&&checkTableExist(genTable)){
			return ResponseEntity.ok().headers(HeaderUtil.createFailureAlert("下一步失败！" + genTable.getName() + " 表已经添加！")).body(null);
		}
		//新增
		if(StringUtils.isBlank(genTable.getId())) {
			List<Table> tableListFormDb = tableService.findTableListFormDb(genTable);
			if (CollectionUtils.isNotEmpty(tableListFormDb)) {
				genTable = tableListFormDb.get(0);
			}
			// 设置字段说明
			if (StringUtils.isBlank(genTable.getComments())){
				genTable.setComments(genTable.getName());
			}
			genTable.setClassName(StringUtils.toCapitalizeCamelCase(genTable.getName()));
		}else{
			genTable = tableService.getTableAndColumnsById(genTable.getId());
		}
		genTable = tableService.getTableAndColumnFormDb(genTable);
		return ResponseEntity.ok().body(genTable);
	}

	/**
	 * 判断表中是否已经存在
	 * @param genTable
	 * @return
	 */
	private boolean checkTableExist(Table genTable) {
		Table queryTable = new Table();
		queryTable.setName(genTable.getName());
		int count = tableService.selectCount(new EntityWrapper<>(queryTable));
		if(count>0){
			return true;
        }
		return false;
	}

	@PostMapping(value = "save")
	public ResponseEntity save(@Validated @RequestBody Table genTable) {
		//如果tableid为新增 则需要检测表是否已经存在
		if (StringUtils.isBlank(genTable.getId())&&checkTableExist(genTable)){
			return ResponseEntity.ok().headers(HeaderUtil.createFailureAlert("下一步失败！" + genTable.getName() + " 表已经添加！")).body(null);
		}
		tableService.insertAndSaveClounm(genTable);
		return ResponseEntity.ok().body(null);
	}
	/*
	@RequestMapping(value = "delete")
	public String delete(Table genTable, RedirectAttributes redirectAttributes) {
		genTableService.delete(genTable);
		addMessage(redirectAttributes, "删除业务表成功");
		return "redirect:" + adminPath + "/gen/genTable/?repage";
	}*/

}
