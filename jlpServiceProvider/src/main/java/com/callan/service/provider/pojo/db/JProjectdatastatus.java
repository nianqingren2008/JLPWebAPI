package com.callan.service.provider.pojo.db;

import java.util.Date;

public class JProjectdatastatus {
    private Long id;

    private Long userid;

    private Long projectid;

    private Long patientglobalid;

    private Long status;

    private String activeflag;

    private Date createdate;

    private String changestatus;

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

    public Long getPatientglobalid() {
        return patientglobalid;
    }

    public void setPatientglobalid(Long patientglobalid) {
        this.patientglobalid = patientglobalid;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public String getActiveflag() {
        return activeflag;
    }

    public void setActiveflag(String activeflag) {
        this.activeflag = activeflag == null ? null : activeflag.trim();
    }

    public Date getCreatedate() {
        return createdate;
    }

    public void setCreatedate(Date createdate) {
        this.createdate = createdate;
    }

    public String getChangestatus() {
        return changestatus;
    }

    public void setChangestatus(String changestatus) {
        this.changestatus = changestatus == null ? null : changestatus.trim();
    }
}