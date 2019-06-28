package com.callan.service.provider.pojo.db;

import java.util.Date;
import java.util.List;

public class JTagdicts {
    private Long id;

    private String name;

    private String description;

    private String tagtype;

    private String valuetype;

    private Long userid;

    private Long projectid;

    private Date createdate;

    private String activeflag;

    private String showflag;

    private String tagattached;

    private Long totalnum;

    private Long currentnum;

    private List<JTagvaluedicts>  valueDictsList;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public String getTagtype() {
        return tagtype;
    }

    public void setTagtype(String tagtype) {
        this.tagtype = tagtype == null ? null : tagtype.trim();
    }

    public String getValuetype() {
        return valuetype;
    }

    public void setValuetype(String valuetype) {
        this.valuetype = valuetype == null ? null : valuetype.trim();
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

    public String getShowflag() {
        return showflag;
    }

    public void setShowflag(String showflag) {
        this.showflag = showflag == null ? null : showflag.trim();
    }

    public String getTagattached() {
        return tagattached;
    }

    public void setTagattached(String tagattached) {
        this.tagattached = tagattached == null ? null : tagattached.trim();
    }

    public Long getTotalnum() {
        return totalnum;
    }

    public void setTotalnum(Long totalnum) {
        this.totalnum = totalnum;
    }

    public Long getCurrentnum() {
        return currentnum;
    }

    public void setCurrentnum(Long currentnum) {
        this.currentnum = currentnum;
    }

	public List<JTagvaluedicts> getValueDictsList() {
		return valueDictsList;
	}

	public void setValueDictsList(List<JTagvaluedicts> valueDictsList) {
		this.valueDictsList = valueDictsList;
	}
    
}