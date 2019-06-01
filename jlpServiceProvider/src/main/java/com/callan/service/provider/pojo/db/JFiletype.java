package com.callan.service.provider.pojo.db;

import java.util.Date;

public class JFiletype {
	/*
	 * 流水号
	 */
	private Long id;
	/*
	 * 文件后缀名称
	 */
	private String name;
	/*
	 * 创建时间
	 */
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
