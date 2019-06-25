package com.callan.service.provider.pojo.db;

import java.util.Date;

public class JProjectdatastatusdict {
	/*
     * 流水号
     */
    private Long id;
    /*
     * 名称
     */
    private String name;
    /*
     * 课题ID
     */
    private Long projectid;
    /*
     * 启用标志
     */
    private String activeflag;
    /*
     * 创建日期
     */
    private Date createdate;
    /*
     * 共享级别(1：课题，2：用户)
     */
    private String statustype;
    
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
	public Long getProjectid() {
		return projectid;
	}
	public void setProjectid(Long projectid) {
		this.projectid = projectid;
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
	public String getStatustype() {
		return statustype;
	}
	public void setStatustype(String statustype) {
		this.statustype = statustype;
	}
    
}
