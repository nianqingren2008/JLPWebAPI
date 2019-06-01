package com.callan.service.provider.pojo.db;

import java.util.Date;

/**
 * 查询记录明细
 *
 */
public class JQueryrecordDetails {
	/*
	 * 流水号  J_QUERYRECORDDETAILS
	 */
	private Long id;
	/*
	 * 查询ID
	 */
	private Long queryid;
	/*
	 * 查询序号
	 */
	private Short detailid;
	/*
	 * 左括号
	 */
	private String leftbrackets;
	/*
	 * 字段名称
	 */
	private String fieldname;
	/*
	 * 关系类型
	 */
	private String relationtype;
	/*
	 * 字段值类型
	 */
	private Integer fieldvaluetype;
	/*
	 * 字段值
	 */
	private String fieldvalue;
	/*
	 * 右括号
	 */
	private String rightbrackets;
	/*
	 * 逻辑类型
	 */
	private String logicaltype;
	/*
	 * 更新时间
	 */
	private Date updatedate;
	/*
	 * 创建时间
	 */
	private Date createdate;
	/*
	 * 有效标志
	 */
	private String activeflag;

//	private JQueryrecord queryrecord;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getQueryid() {
		return queryid;
	}

	public void setQueryid(Long queryid) {
		this.queryid = queryid;
	}

	public Short getDetailid() {
		return detailid;
	}

	public void setDetailid(Short detailid) {
		this.detailid = detailid;
	}

	public String getLeftbrackets() {
		return leftbrackets;
	}

	public void setLeftbrackets(String leftbrackets) {
		this.leftbrackets = leftbrackets;
	}

	public String getFieldname() {
		return fieldname;
	}

	public void setFieldname(String fieldname) {
		this.fieldname = fieldname;
	}

	public String getRelationtype() {
		return relationtype;
	}

	public void setRelationtype(String relationtype) {
		this.relationtype = relationtype;
	}

	public Integer getFieldvaluetype() {
		return fieldvaluetype;
	}

	public void setFieldvaluetype(Integer fieldvaluetype) {
		this.fieldvaluetype = fieldvaluetype;
	}

	public String getFieldvalue() {
		return fieldvalue;
	}

	public void setFieldvalue(String fieldvalue) {
		this.fieldvalue = fieldvalue;
	}

	public String getRightbrackets() {
		return rightbrackets;
	}

	public void setRightbrackets(String rightbrackets) {
		this.rightbrackets = rightbrackets;
	}

	public String getLogicaltype() {
		return logicaltype;
	}

	public void setLogicaltype(String logicaltype) {
		this.logicaltype = logicaltype;
	}

	public Date getUpdatedate() {
		return updatedate;
	}

	public void setUpdatedate(Date updatedate) {
		this.updatedate = updatedate;
	}

	public Date getCreatedate() {
		return createdate;
	}

	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}

	public String getActiveflag() {
		return activeflag;
	}

	public void setActiveflag(String activeflag) {
		this.activeflag = activeflag;
	}

//	public JQueryrecord getQueryrecord() {
//		return queryrecord;
//	}
//
//	public void setQueryrecord(JQueryrecord queryrecord) {
//		this.queryrecord = queryrecord;
//	}

}
