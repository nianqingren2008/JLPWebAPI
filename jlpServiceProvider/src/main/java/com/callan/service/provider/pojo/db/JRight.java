package com.callan.service.provider.pojo.db;

import java.util.Date;

/**
 * 系统权限表
 * 
 * @author callan
 *
 */
public class JRight {
	/*
	 * 流水号
	 */
	private Long id;
	/*
	 * 名称
	 */
	private String name;
	/*
	 * 访问地址
	 */
	private String apiurl;
	/*
	 * 启用标志
	 */
	private String activeflag;
	/*
	 * 创建时间
	 */
	private Date createdate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getApiurl() {
		return apiurl;
	}

	public void setApiurl(String apiurl) {
		this.apiurl = apiurl;
	}

	public String getActiveflag() {
		return activeflag;
	}

	public void setActiveflag(String activeflag) {
		this.activeflag = activeflag;
	}

	public Date getCreatedate() {
		return createdate;
	}

	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}

}
