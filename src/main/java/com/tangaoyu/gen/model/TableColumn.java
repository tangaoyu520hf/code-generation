package com.tangaoyu.gen.model;

import java.math.BigDecimal;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 业务表字段
 * </p>
 *
 * @author tangaoyu
 * @since 2017-05-11
 */
@TableName("gen_table_column")
public class TableColumn extends Model<TableColumn> {
	public static final String DEL_FLAG_NORMAL = "0";
	public static final String DEL_FLAG_DELETE = "1";

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
	private String id;
    /**
     * 归属表编号
     */
	@TableField("gen_table_id")
	private String genTableId;
    /**
     * 名称
     */
	private String name;
    /**
     * 描述
     */
	private String comments;
    /**
     * 列的数据类型的字节长度
     */
	@TableField("jdbc_type")
	private String jdbcType;
    /**
     * JAVA类型
     */
	@TableField("java_type")
	private String javaType;
    /**
     * JAVA字段名
     */
	@TableField("java_field")
	private String javaField;
    /**
     * 是否主键
     */
	@TableField("is_pk")
	private String isPk;
    /**
     * 是否可为空
     */
	@TableField("is_null")
	private String isNull;
    /**
     * 是否为插入字段
     */
	@TableField("is_insert")
	private String isInsert;
    /**
     * 是否编辑字段
     */
	@TableField("is_edit")
	private String isEdit;
    /**
     * 是否列表字段
     */
	@TableField("is_list")
	private String isList;
    /**
     * 是否查询字段
     */
	@TableField("is_query")
	private String isQuery;
    /**
     * 查询方式（等于、不等于、大于、小于、范围、左LIKE、右LIKE、左右LIKE）
     */
	@TableField("query_type")
	private String queryType;
    /**
     * 字段生成方案（文本框、文本域、下拉框、复选框、单选框、字典选择、人员选择、部门选择、区域选择）
     */
	@TableField("show_type")
	private String showType;
    /**
     * 字典类型
     */
	@TableField("dict_type")
	private String dictType;
    /**
     * 其它设置（扩展字段JSON）
     */
	private String settings;
    /**
     * 排序（升序）
     */
	private BigDecimal sort;
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


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getGenTableId() {
		return genTableId;
	}

	public void setGenTableId(String genTableId) {
		this.genTableId = genTableId;
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

	public String getJdbcType() {
		return jdbcType;
	}

	public void setJdbcType(String jdbcType) {
		this.jdbcType = jdbcType;
	}

	public String getJavaType() {
		return javaType;
	}

	public void setJavaType(String javaType) {
		this.javaType = javaType;
	}

	public String getJavaField() {
		return javaField;
	}

	public void setJavaField(String javaField) {
		this.javaField = javaField;
	}

	public String getIsPk() {
		return isPk;
	}

	public void setIsPk(String isPk) {
		this.isPk = isPk;
	}

	public String getIsNull() {
		return isNull;
	}

	public void setIsNull(String isNull) {
		this.isNull = isNull;
	}

	public String getIsInsert() {
		return isInsert;
	}

	public void setIsInsert(String isInsert) {
		this.isInsert = isInsert;
	}

	public String getIsEdit() {
		return isEdit;
	}

	public void setIsEdit(String isEdit) {
		this.isEdit = isEdit;
	}

	public String getIsList() {
		return isList;
	}

	public void setIsList(String isList) {
		this.isList = isList;
	}

	public String getIsQuery() {
		return isQuery;
	}

	public void setIsQuery(String isQuery) {
		this.isQuery = isQuery;
	}

	public String getQueryType() {
		return queryType;
	}

	public void setQueryType(String queryType) {
		this.queryType = queryType;
	}

	public String getShowType() {
		return showType;
	}

	public void setShowType(String showType) {
		this.showType = showType;
	}

	public String getDictType() {
		return dictType;
	}

	public void setDictType(String dictType) {
		this.dictType = dictType;
	}

	public String getSettings() {
		return settings;
	}

	public void setSettings(String settings) {
		this.settings = settings;
	}

	public BigDecimal getSort() {
		return sort;
	}

	public void setSort(BigDecimal sort) {
		this.sort = sort;
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

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
