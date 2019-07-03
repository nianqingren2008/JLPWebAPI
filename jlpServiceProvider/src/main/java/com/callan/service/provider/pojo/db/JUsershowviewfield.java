package com.callan.service.provider.pojo.db;

public class JUsershowviewfield {
    private Long id;

    private Long userid;

    private String pagename;

    private Long fieldid;

    private String activeflag;

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

    public String getPagename() {
        return pagename;
    }

    public void setPagename(String pagename) {
        this.pagename = pagename == null ? null : pagename.trim();
    }

    public Long getFieldid() {
        return fieldid;
    }

    public void setFieldid(Long fieldid) {
        this.fieldid = fieldid;
    }

    public String getActiveflag() {
        return activeflag;
    }

    public void setActiveflag(String activeflag) {
        this.activeflag = activeflag == null ? null : activeflag.trim();
    }
}