package com.callan.service.provider.pojo.db;

import java.util.Date;

public class JProjectdatastatusdict {
    private Long id;

    private String name;

    private Long projectid;

    private String activeflag;

    private Date createdate;

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
        this.name = name == null ? null : name.trim();
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
        this.activeflag = activeflag == null ? null : activeflag.trim();
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
        this.statustype = statustype == null ? null : statustype.trim();
    }
}