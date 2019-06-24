package com.callan.service.provider.pojo.project;

import java.util.Date;

public class JProjectdatastatusdict {
	/*
     * 流水号
     */
    private Long Id;
    /*
     * 名称
     */
    private String Name;
    /*
     * 课题ID
     */
    private Long Projectid;
    /*
     * 启用标志
     */
    private String Activeflag;
    /*
     * 创建日期
     */
    private Date Createdate;
    /*
     * 共享级别(1：课题，2：用户)
     */
    private String Statustype;
    
	public Long getId() {
		return Id;
	}
	public void setId(Long id) {
		Id = id;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public Long getProjectid() {
		return Projectid;
	}
	public void setProjectid(Long projectid) {
		Projectid = projectid;
	}
	public String getActiveflag() {
		return Activeflag;
	}
	public void setActiveflag(String activeflag) {
		Activeflag = activeflag;
	}
	public Date getCreatedate() {
		return Createdate;
	}
	public void setCreatedate(Date createdate) {
		Createdate = createdate;
	}
	public String getStatustype() {
		return Statustype;
	}
	public void setStatustype(String statustype) {
		Statustype = statustype;
	}
}
