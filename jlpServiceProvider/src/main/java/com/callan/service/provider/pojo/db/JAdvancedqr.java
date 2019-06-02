package com.callan.service.provider.pojo.db;

import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 纳排查询记录表
 *
 */
public class JAdvancedqr {
	/*
	 * 主键
	 */
	private Long id;
	/*
	 * 用户ID
	 */
	private Long userid;
	/*
	 * 课题主键
	 */
	private Long projectid;
	/*
	 * 名称
	 */
	private String aqname;
	/*
	 * 排序号
	 */
	private Integer sortno;
	/*
	 * 创建时间
	 */
	@JSONField(format="yyyy/MM/dd HH:mm:ss")
	private Date createdate;
	/*
	 * 启用标志
	 */
	private String activeflag;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserid() {
		return userid;
	}

	public void setUserid(Long userid) {
		this.userid = userid;
	}

	public Long getProjectid() {
		return projectid;
	}

	public void setProjectid(Long projectid) {
		this.projectid = projectid;
	}

	public String getAqname() {
		return aqname;
	}

	public void setAqname(String aqname) {
		this.aqname = aqname;
	}

	public Integer getSortno() {
		return sortno;
	}

	public void setSortno(Integer sortno) {
		this.sortno = sortno;
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
}
