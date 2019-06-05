package com.callan.service.provider.pojo.db;

import java.util.Date;

public class JStatisconfdetail {
    private Long id;

    private Long statisconfid;

    private String detailtype;

    private Long fieldid;

    private String fieldtypetrans;

    private Integer sortno;

    private Date createdate;

    private String activeflag;

    private String defaulvalue;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getStatisconfid() {
        return statisconfid;
    }

    public void setStatisconfid(Long statisconfid) {
        this.statisconfid = statisconfid;
    }

    public String getDetailtype() {
        return detailtype;
    }

    public void setDetailtype(String detailtype) {
        this.detailtype = detailtype == null ? null : detailtype.trim();
    }

    public Long getFieldid() {
        return fieldid;
    }

    public void setFieldid(Long fieldid) {
        this.fieldid = fieldid;
    }

    public String getFieldtypetrans() {
        return fieldtypetrans;
    }

    public void setFieldtypetrans(String fieldtypetrans) {
        this.fieldtypetrans = fieldtypetrans == null ? null : fieldtypetrans.trim();
    }

    public Integer getSortno() {
        return sortno;
    }

    public void setSortno(Integer sortno) {
        this.sortno = sortno;
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

    public String getDefaulvalue() {
        return defaulvalue;
    }

    public void setDefaulvalue(String defaulvalue) {
        this.defaulvalue = defaulvalue == null ? null : defaulvalue.trim();
    }
}