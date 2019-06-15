package com.callan.service.provider.pojo.task;

import java.util.Date;

public class JTaskdownload {
    private Long id;

    private Long taskid;

    private Long projectid;

    private Long queryid;

    private Short dataexportclass;

    private String exportfields;

    private String projectstatuses;

    private String tagdata;

    private String tagtranspose;

    private Short filetypeid;

    private Short imageexportclass;

    private String imageclass;

    private Long fileid;

    private Long imagefileid;

    private Date createdate;

    private String activeflag;
    
    private String url;
    
    

    public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTaskid() {
        return taskid;
    }

    public void setTaskid(Long taskid) {
        this.taskid = taskid;
    }

    public Long getProjectid() {
        return projectid;
    }

    public void setProjectid(Long projectid) {
        this.projectid = projectid;
    }

    public Long getQueryid() {
        return queryid;
    }

    public void setQueryid(Long queryid) {
        this.queryid = queryid;
    }

    public Short getDataexportclass() {
        return dataexportclass;
    }

    public void setDataexportclass(Short dataexportclass) {
        this.dataexportclass = dataexportclass;
    }

    public String getExportfields() {
        return exportfields;
    }

    public void setExportfields(String exportfields) {
        this.exportfields = exportfields == null ? null : exportfields.trim();
    }

    public String getProjectstatuses() {
        return projectstatuses;
    }

    public void setProjectstatuses(String projectstatuses) {
        this.projectstatuses = projectstatuses == null ? null : projectstatuses.trim();
    }

    public String getTagdata() {
        return tagdata;
    }

    public void setTagdata(String tagdata) {
        this.tagdata = tagdata == null ? null : tagdata.trim();
    }

    public String getTagtranspose() {
        return tagtranspose;
    }

    public void setTagtranspose(String tagtranspose) {
        this.tagtranspose = tagtranspose == null ? null : tagtranspose.trim();
    }

    public Short getFiletypeid() {
        return filetypeid;
    }

    public void setFiletypeid(Short filetypeid) {
        this.filetypeid = filetypeid;
    }

    public Short getImageexportclass() {
        return imageexportclass;
    }

    public void setImageexportclass(Short imageexportclass) {
        this.imageexportclass = imageexportclass;
    }

    public String getImageclass() {
        return imageclass;
    }

    public void setImageclass(String imageclass) {
        this.imageclass = imageclass == null ? null : imageclass.trim();
    }

    public Long getFileid() {
        return fileid;
    }

    public void setFileid(Long fileid) {
        this.fileid = fileid;
    }

    public Long getImagefileid() {
        return imagefileid;
    }

    public void setImagefileid(Long imagefileid) {
        this.imagefileid = imagefileid;
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