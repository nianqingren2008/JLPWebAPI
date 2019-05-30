package com.callan.service.provider.pojo.db;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 纳排查询条件项目表
 *
 */
public class JAdvancedqrItem {
	/*
	 * 主键
	 */
	private Long id;
	/*
	 * 主表ID
	 */
	private Long qrid;
	/*
	 * 分类(1：Query 2：include 3：exclude)
	 */
	private String itemtype;
	/*
	 * 查询项编号
	 */
	private Integer itemno;
	/*
	 * 左括号
	 */
	private String leftqueto;
	/*
	 * 数据模板ID (前端需求)
	 */
	private String modelid;
	/*
	 * 查询ID
	 */
	private Long queryid;
	/*
	 * 右括号
	 */
	private String rightqueto;
	/*
	 * 集合操作类型(1:交集 2:并集 3:差集)
	 */
	private String setcombinatortype;
	/*
	 * 数据模板类型 (前端需求)
	 */
	private String modeltype;
	/*
	 * 创建时间
	 */
	@JSONField(format="yyyy/MM/dd HH:mm:ss")
	private Date createdate;
	/*
	 * 启用标志
	 */
	private String activeflag;
	
	private JQueryrecord queryrecord;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getQrid() {
		return qrid;
	}

	public void setQrid(Long qrid) {
		this.qrid = qrid;
	}

	public String getItemtype() {
		return itemtype;
	}

	public void setItemtype(String itemtype) {
		this.itemtype = itemtype;
	}

	public Integer getItemno() {
		return itemno;
	}

	public void setItemno(Integer itemno) {
		this.itemno = itemno;
	}

	public String getLeftqueto() {
		return leftqueto;
	}

	public void setLeftqueto(String leftqueto) {
		this.leftqueto = leftqueto;
	}

	public String getModelid() {
		return modelid;
	}

	public void setModelid(String modelid) {
		this.modelid = modelid;
	}

	public Long getQueryid() {
		return queryid;
	}

	public void setQueryid(Long queryid) {
		this.queryid = queryid;
	}

	public String getRightqueto() {
		return rightqueto;
	}

	public void setRightqueto(String rightqueto) {
		this.rightqueto = rightqueto;
	}

	public String getSetcombinatortype() {
		return setcombinatortype;
	}

	public void setSetcombinatortype(String setcombinatortype) {
		this.setcombinatortype = setcombinatortype;
	}

	public String getModeltype() {
		return modeltype;
	}

	public void setModeltype(String modeltype) {
		this.modeltype = modeltype;
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

	public JQueryrecord getQueryrecord() {
		return queryrecord;
	}

	public void setQueryrecord(JQueryrecord queryrecord) {
		this.queryrecord = queryrecord;
	}

}
