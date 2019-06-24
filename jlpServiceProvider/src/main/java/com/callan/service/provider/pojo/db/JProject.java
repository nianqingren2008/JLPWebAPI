package com.callan.service.provider.pojo.db;

import java.util.Date;

public class JProject {
    private Long id;

    private Long userid;

    private String projectname;

    private String projectenname;

    private String projecttype;

    private String projectdescribe;

    private Date startdate;

    private Date enddate;

    private String sponsor;

    private String projectregistno;

    private String ethicalrecordno;

    private Long patientcount;

    private Long medicalrecordcount;

    private String status;

    private String sharetype;

    private Date updatedate;

    private Date createdate;

    private String activeflag;

    private String imageurl;

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
        this.projectname = projectname == null ? null : projectname.trim();
    }

    public String getProjectenname() {
        return projectenname;
    }

    public void setProjectenname(String projectenname) {
        this.projectenname = projectenname == null ? null : projectenname.trim();
    }

    public String getProjecttype() {
        return projecttype;
    }

    public void setProjecttype(String projecttype) {
        this.projecttype = projecttype == null ? null : projecttype.trim();
    }

    public String getProjectdescribe() {
        return projectdescribe;
    }

    public void setProjectdescribe(String projectdescribe) {
        this.projectdescribe = projectdescribe == null ? null : projectdescribe.trim();
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
        this.sponsor = sponsor == null ? null : sponsor.trim();
    }

    public String getProjectregistno() {
        return projectregistno;
    }

    public void setProjectregistno(String projectregistno) {
        this.projectregistno = projectregistno == null ? null : projectregistno.trim();
    }

    public String getEthicalrecordno() {
        return ethicalrecordno;
    }

    public void setEthicalrecordno(String ethicalrecordno) {
        this.ethicalrecordno = ethicalrecordno == null ? null : ethicalrecordno.trim();
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
        this.status = status == null ? null : status.trim();
    }

    public String getSharetype() {
        return sharetype;
    }

    public void setSharetype(String sharetype) {
        this.sharetype = sharetype == null ? null : sharetype.trim();
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
        this.activeflag = activeflag == null ? null : activeflag.trim();
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl == null ? null : imageurl.trim();
    }

    public String getDatastatus() {
        return datastatus;
    }

    public void setDatastatus(String datastatus) {
        this.datastatus = datastatus == null ? null : datastatus.trim();
    }
}