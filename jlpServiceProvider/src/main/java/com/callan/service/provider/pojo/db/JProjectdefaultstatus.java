package com.callan.service.provider.pojo.db;

import java.util.Date;

public class JProjectdefaultstatus {
    private Long id;

    private Long projectid;

    private Long datastatusid;

    private Date createdate;

    private String activeflag;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProjectid() {
        return projectid;
    }

    public void setProjectid(Long projectid) {
        this.projectid = projectid;
    }

    public Long getDatastatusid() {
        return datastatusid;
    }

    public void setDatastatusid(Long datastatusid) {
        this.datastatusid = datastatusid;
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
}