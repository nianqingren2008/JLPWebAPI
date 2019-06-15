package com.callan.service.provider.pojo.db;

public class JPageviewdetail {
    private Integer id;

    private String pagecode;

    private String viewcode;

    private Integer sortno;

    private String activeflag;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPagecode() {
        return pagecode;
    }

    public void setPagecode(String pagecode) {
        this.pagecode = pagecode == null ? null : pagecode.trim();
    }

    public String getViewcode() {
        return viewcode;
    }

    public void setViewcode(String viewcode) {
        this.viewcode = viewcode == null ? null : viewcode.trim();
    }

    public Integer getSortno() {
        return sortno;
    }

    public void setSortno(Integer sortno) {
        this.sortno = sortno;
    }

    public String getActiveflag() {
        return activeflag;
    }

    public void setActiveflag(String activeflag) {
        this.activeflag = activeflag == null ? null : activeflag.trim();
    }
}