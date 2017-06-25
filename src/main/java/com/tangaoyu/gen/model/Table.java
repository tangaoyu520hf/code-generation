package com.tangaoyu.gen.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tangaoyu.gen.util.StringUtils;
import org.apache.commons.collections.CollectionUtils;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;
import java.util.*;

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
	@Length(min=1, max=200)
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

	@TableField(exist = false)
	private List<TableColumn> columnList = new ArrayList<>();	// 表列

	@TableField(exist = false)
	private List<String> pkList; // 当前表主键列表

	@JsonIgnore
	@TableField(exist = false)
	private Table parent;	// 父表对象

	@TableField(exist = false)
	private List<Table> childList = Collections.EMPTY_LIST;   // 子表列表
	@TableField(exist = false)
	private TableColumn tableColumnPk;

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

	@JsonIgnore
	public Table getParent() {
		return parent;
	}

	public void setParent(Table parent) {
		this.parent = parent;
	}

	@JsonIgnore
	public List<Table> getChildList() {
		return childList;
	}

	public void setChildList(List<Table> childList) {
		this.childList = childList;
	}

	public TableColumn getTableColumnPk() {
		List<TableColumn> columnList = this.getColumnList();
		Optional<TableColumn> first = columnList.stream().filter(tableColumn -> tableColumn.getIsPk().equals("1")).findFirst();
		return first.orElse(null);
	}


	public void setTableColumnPk(TableColumn tableColumnPk) {
		this.tableColumnPk = tableColumnPk;
	}

	/**
	 * 获取导入依赖包字符串
	 * @return
	 */
	public List<String> getImportList(){
		Set<String> importList = new HashSet<>(); // 引用列表

		importList.add("com.baomidou.mybatisplus.activerecord.Model");
		importList.add("com.baomidou.mybatisplus.annotations.TableName");

		importList.add("com.baomidou.mybatisplus.annotations.TableId");
		importList.add("com.baomidou.mybatisplus.annotations.TableField");

		for (TableColumn column : getColumnList()){
			if (("1".equals(column.getIsQuery()) && "between".equals(column.getQueryType())
					||!column.getIsNotBaseField())){
				// 导入类型依赖包， 如果类型中包含“.”，则需要导入引用。
				if (StringUtils.indexOf(column.getJavaType(), ".") != -1 && !importList.contains(column.getJavaType())){
					importList.add(column.getJavaType());
				}
			}
			// 导入JSR303、Json等依赖包
			for (String ann : column.getAnnotationList()){
				if (!importList.contains(StringUtils.substringBeforeLast(ann, "("))){
					importList.add(StringUtils.substringBeforeLast(ann, "("));
				}
			}
		}
		// 如果有子表，则需要导入List相关引用
		if (getChildList() != null && getChildList().size() > 0){
			if (!importList.contains("java.util.List")){
				importList.add("java.util.List");
			}
		}
		return new ArrayList(importList);
	}

	/**
	 * 转换filed实体为xmlmapper中的basecolumn字符串信息
	 *
	 * @return
	 */
	public String getFieldNames() {
		StringBuilder names = new StringBuilder();
		if (CollectionUtils.isNotEmpty(this.columnList)) {
			for (int i = 0; i < columnList.size(); i++) {
				TableColumn tableColumn = columnList.get(i);
				if (i == columnList.size() - 1) {
					names.append(cov2col(tableColumn));
				} else {
					names.append(cov2col(tableColumn)).append(", ");
				}
			}
		}
		return names.toString();
	}

	/**
	 * 获取驼峰外键
	 * @return
	 */
	public String getCamelCaseParentTableFk(){
		if(StringUtils.isNotBlank(this.parentTableFk)){
			return StringUtils.toCapitalizeCamelCase(this.parentTableFk);
		}
		return "";
	}

	/**
	 * mapper xml中的字字段添加as
	 *
	 * @param field 字段实体
	 * @return 转换后的信息
	 */
	private String cov2col(TableColumn field) {
		if (null != field) {
			return field.getName() + " AS " + field.getSimpleJavaField();
		}
		return "";
	}


	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
