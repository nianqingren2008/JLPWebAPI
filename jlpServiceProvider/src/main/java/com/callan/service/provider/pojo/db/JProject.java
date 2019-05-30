package com.callan.service.provider.pojo.db;

import java.util.Date;

/**
 * 课题主表
 */
public class JProject {
	/*
	 * 主键 J_PROJECT
	 */
	private Long id;
	/*
	 * 用户ID
	 */
	private Long userid;
	/*
	 * 项目名称
	 */
	private String projectname;
	/*
	 * 项目英文名称
	 */
	private String projectenname;
	/*
	 * 项目分类
	 */
	private String projecttype;
	/*
	 * 项目描述
	 */
	private String projectdescribe;
	/*
	 * 项目起始时间
	 */
	private Date startdate;
	/*
	 * 项目完结时间
	 */
	private Date enddate;
	/*
	 * 发起者
	 */
	private String sponsor;
	/*
	 * 项目注册号
	 */
	private String projectRegistNo;
	/*
	 * 伦理备案号
	 */
	private String ethicalrecordno;
	/*
	 * 患者总数
	 */
	private Long patientcount;
	/*
	 * 病例总数
	 */
	private Long medicalrecordcount;
	/*
	 * 课题状态
	 */
	private String status;
	/*
	 * 课题共享类型
	 */
	private String sharetype;
	/*
	 * 课题更新时间
	 */
	private Date updatedate;
	/*
	 * 课题创建时间
	 */
	private Date createdate;
	/*
	 * 启用标志
	 */
	private String activeflag;
	/*
	 * 课题图片
	 */
	private String imageurl;
	/*
	 * 暂时弃用--课题数据变更状态(1：正常 2：变更状态(当标签显示数量变更后))
	 */
	private String datastatus;

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

	public String getProjectname() {
		return projectname;
	}

	public void setProjectname(String projectname) {
		this.projectname = projectname;
	}

	public String getProjectenname() {
		return projectenname;
	}

	public void setProjectenname(String projectenname) {
		this.projectenname = projectenname;
	}

	public String getProjecttype() {
		return projecttype;
	}

	public void setProjecttype(String projecttype) {
		this.projecttype = projecttype;
	}

	public String getProjectdescribe() {
		return projectdescribe;
	}

	public void setProjectdescribe(String projectdescribe) {
		this.projectdescribe = projectdescribe;
	}

	public Date getStartdate() {
		return startdate;
	}

	public void setStartdate(Date startdate) {
		this.startdate = startdate;
	}

	public Date getEnddate() {
		return enddate;
	}

	public void setEnddate(Date enddate) {
		this.enddate = enddate;
	}

	public String getSponsor() {
		return sponsor;
	}

	public void setSponsor(String sponsor) {
		this.sponsor = sponsor;
	}

	public String getProjectRegistNo() {
		return projectRegistNo;
	}

	public void setProjectRegistNo(String projectRegistNo) {
		this.projectRegistNo = projectRegistNo;
	}

	public String getEthicalrecordno() {
		return ethicalrecordno;
	}

	public void setEthicalrecordno(String ethicalrecordno) {
		this.ethicalrecordno = ethicalrecordno;
	}

	public Long getPatientcount() {
		return patientcount;
	}

	public void setPatientcount(Long patientcount) {
		this.patientcount = patientcount;
	}

	public Long getMedicalrecordcount() {
		return medicalrecordcount;
	}

	public void setMedicalrecordcount(Long medicalrecordcount) {
		this.medicalrecordcount = medicalrecordcount;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSharetype() {
		return sharetype;
	}

	public void setSharetype(String sharetype) {
		this.sharetype = sharetype;
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

	public String getImageurl() {
		return imageurl;
	}

	public void setImageurl(String imageurl) {
		this.imageurl = imageurl;
	}

	public String getDatastatus() {
		return datastatus;
	}

	public void setDatastatus(String datastatus) {
		this.datastatus = datastatus;
	}
}
