package com.callan.service.provider.pojo.db;

import java.util.Date;
import java.util.List;
/**
 * 用户角色表
 *
 */
public class JRole {
	/*
	 * 流水号 
	 */
	private Long id;
	/*
	 * 角色名
	 */
	private String name;
	/*
	 * 启用标识
	 */
	private String  activeflag;
	/*
	 * 创建时间
	 */
	private Date  createdate;
	
	private List<JRoleRight> list;
	
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
	public List<JRoleRight> getList() {
		return list;
	}
	public void setList(List<JRoleRight> list) {
		this.list = list;
	}
	
	
}
