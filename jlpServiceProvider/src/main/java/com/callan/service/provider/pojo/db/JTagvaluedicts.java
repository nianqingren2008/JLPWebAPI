package com.callan.service.provider.pojo.db;

import java.util.Date;

public class JTagvaluedicts {
    private Long id;

    private Long tagid;

    private String valuetype;

    private String value;

    private String minvalue;

    private String maxvalue;

    private Date createdate;

    private String activeflag;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTagid() {
        return tagid;
    }

    public void setTagid(Long tagid) {
        this.tagid = tagid;
    }

    public String getValuetype() {
        return valuetype;
    }

    public void setValuetype(String valuetype) {
        this.valuetype = valuetype == null ? null : valuetype.trim();
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value == null ? null : value.trim();
    }

    public String getMinvalue() {
        return minvalue;
    }

    public void setMinvalue(String minvalue) {
        this.minvalue = minvalue == null ? null : minvalue.trim();
    }

    public String getMaxvalue() {
        return maxvalue;
    }

    public void setMaxvalue(String maxvalue) {
        this.maxvalue = maxvalue == null ? null : maxvalue.trim();
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