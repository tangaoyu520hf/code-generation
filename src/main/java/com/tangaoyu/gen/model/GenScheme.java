package com.tangaoyu.gen.model;

import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.groups.ConvertGroup;
import java.io.Serializable;

/**
 * <p>
 * 生成方案
 * </p>
 *
 * @author tangaoyu
 * @since 2017-05-14
 */
@TableName("gen_scheme")
public class GenScheme extends Model<GenScheme> {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @NotNull
	private String id;
    /**
     * 名称
     */
	private String name;
    /**
     * 分类
     */
	private String category;
    /**
     * 生成包路径
     */
	@TableField("package_name")
	private String packageName;
    /**
     * 生成模块名
     */
	@TableField("module_name")
	private String moduleName;
    /**
     * 生成子模块名
     */
	@TableField("sub_module_name")
	private String subModuleName;
    /**
     * 生成功能名
     */
	@TableField("function_name")
	private String functionName;
    /**
     * 生成功能名（简写）
     */
	@TableField("function_name_simple")
	private String functionNameSimple;
    /**
     * 生成功能作者
     */
	@TableField("function_author")
	private String functionAuthor;
    /**
     * 生成表编号
     */
	@TableField("gen_table_id")
	private String genTableId;
    /**
     * 创建者
     */
	@TableField("create_by")
	private String createBy;
    /**
     * 创建时间
     */
	@TableField("create_date")
	private Date createDate;
    /**
     * 更新者
     */
	@TableField("update_by")
	private String updateBy;
    /**
     * 更新时间
     */
	@TableField("update_date")
	private Date updateDate;
    /**
     * 备注信息
     */
	private String remarks;
    /**
     * 删除标记（0：正常；1：删除）
     */
	@TableField("del_flag")
	private String delFlag;

	@TableField(exist = false)
	private Boolean genIs;

	@TableField(exist = false)
	private Table genTable;		// 业务表名

	@TableField(exist = false)
	private boolean replaceFile;	// 是否替换现有文件    0：不替换；1：替换文件


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public String getSubModuleName() {
		return subModuleName;
	}

	public void setSubModuleName(String subModuleName) {
		this.subModuleName = subModuleName;
	}

	public String getFunctionName() {
		return functionName;
	}

	public void setFunctionName(String functionName) {
		this.functionName = functionName;
	}

	public String getFunctionNameSimple() {
		return functionNameSimple;
	}

	public void setFunctionNameSimple(String functionNameSimple) {
		this.functionNameSimple = functionNameSimple;
	}

	public String getFunctionAuthor() {
		return functionAuthor;
	}

	public void setFunctionAuthor(String functionAuthor) {
		this.functionAuthor = functionAuthor;
	}

	public String getGenTableId() {
		return genTableId;
	}

	public void setGenTableId(String genTableId) {
		this.genTableId = genTableId;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}

	public Boolean getGenIs() {
		return genIs;
	}

	public void setGenIs(Boolean genIs) {
		this.genIs = genIs;
	}

	public Table getGenTable() {
		return genTable;
	}

	public void setGenTable(Table genTable) {
		this.genTable = genTable;
	}

	public Boolean getReplaceFile() {
		return replaceFile;
	}

	public void setReplaceFile(Boolean replaceFile) {
		this.replaceFile = replaceFile;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
