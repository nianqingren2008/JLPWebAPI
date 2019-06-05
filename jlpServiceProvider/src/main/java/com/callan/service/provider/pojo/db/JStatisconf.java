package com.callan.service.provider.pojo.db;

import java.util.Date;

public class JStatisconf {
	//  流水号
    private Long id;

 // 代码
    private String code;
 // 名称
    private String title;

    private Date createdate;

    private String activeflag;

    private String pagecode;

    private String pagetitle;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
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

    public String getPagecode() {
        return pagecode;
    }

    public void setPagecode(String pagecode) {
        this.pagecode = pagecode == null ? null : pagecode.trim();
    }

    public String getPagetitle() {
        return pagetitle;
    }

    public void setPagetitle(String pagetitle) {
        this.pagetitle = pagetitle == null ? null : pagetitle.trim();
    }
}