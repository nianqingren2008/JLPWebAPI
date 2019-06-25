package com.callan.service.provider.pojo.db;

import java.util.Date;

public class JExportdataclass {
    private Short id;

    private String title;

    private String info;

    private Date createdate;

    private String activeglag;

    private String type;

    public Short getId() {
        return id;
    }

    public void setId(Short id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info == null ? null : info.trim();
    }

    public Date getCreatedate() {
        return createdate;
    }

    public void setCreatedate(Date createdate) {
        this.createdate = createdate;
    }

    public String getActiveglag() {
        return activeglag;
    }

    public void setActiveglag(String activeglag) {
        this.activeglag = activeglag == null ? null : activeglag.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }
}