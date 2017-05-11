package com.tangaoyu.gen.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 业务表
 * </p>
 *
 * @author tangaoyu
 * @since 2017-05-11
 */
@TableName("gen_table")
public class Table extends Model<Table> {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
	private String id;
    /**
     * 名称
     */
	private String name;
    /**
     * 描述
     */
	private String comments;
    /**
     * 实体类名称
     */
	@TableField("class_name")
	private String className;
    /**
     * 关联父表
     */
	@TableField("parent_table")
	private String parentTable;
    /**
     * 关联父表外键
     */
	@TableField("parent_table_fk")
	private String parentTableFk;
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

	/**
	 * 获取数据库名称
	 */
	@JsonIgnore
	public String getDbName(){
		return "mysql";
	}

	private List<TableColumn> columnList = new ArrayList<>();	// 表列

	private List<String> pkList; // 当前表主键列表

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

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getParentTable() {
		return parentTable;
	}

	public void setParentTable(String parentTable) {
		this.parentTable = parentTable;
	}

	public String getParentTableFk() {
		return parentTableFk;
	}

	public void setParentTableFk(String parentTableFk) {
		this.parentTableFk = parentTableFk;
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

	public List<TableColumn> getColumnList() {
		return columnList;
	}

	public void setColumnList(List<TableColumn> columnList) {
		this.columnList = columnList;
	}

	public List<String> getPkList() {
		return pkList;
	}

	public void setPkList(List<String> pkList) {
		this.pkList = pkList;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
