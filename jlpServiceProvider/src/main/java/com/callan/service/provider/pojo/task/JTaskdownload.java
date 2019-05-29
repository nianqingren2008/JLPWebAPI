package com.callan.service.provider.pojo.task;

import java.util.Date;

public class JTaskdownload {
	
    // 流水号
    private long Id ;
    
    // 任务ID
    private long Taskid ;
    
    // 课题ID
    private long Projectid ;
    
    // 查询ID
    private long Queryid ;
    
    // 数据导出策略
    private short Dataexportclass ;
    
    // 导出字段(多个，以逗号隔开)
    private String Exportfields ;
    
    // 课题导出状态(多个，以逗号隔开)
    private String Projectstatuses ;
    
    // 标签数据ID
    private String Tagdata ;
    
    // 标签转置ID
    private String Tagtranspose ;
    
    // 文件类别
    private byte Filetypeid ;
    
    // 图像导出策略
    private short Imageexportclass ;
    
    // 图像分类(CT/MR/US等等)
    private String Imageclass ;
    
    // 文件ID
    private long Fileid ;
    
    // 图像ID
    private long Imagefileid ;
    
    // 创建时间
    private Date Createdate ;
    
    // 启用标志
    private String Activeflag ;

	public long getId() {
		return Id;
	}

	public void setId(long id) {
		Id = id;
	}

	public long getTaskid() {
		return Taskid;
	}

	public void setTaskid(long taskid) {
		Taskid = taskid;
	}

	public long getProjectid() {
		return Projectid;
	}

	public void setProjectid(long projectid) {
		Projectid = projectid;
	}

	public long getQueryid() {
		return Queryid;
	}

	public void setQueryid(long queryid) {
		Queryid = queryid;
	}

	public short getDataexportclass() {
		return Dataexportclass;
	}

	public void setDataexportclass(short dataexportclass) {
		Dataexportclass = dataexportclass;
	}

	public String getExportfields() {
		return Exportfields;
	}

	public void setExportfields(String exportfields) {
		Exportfields = exportfields;
	}

	public String getProjectstatuses() {
		return Projectstatuses;
	}

	public void setProjectstatuses(String projectstatuses) {
		Projectstatuses = projectstatuses;
	}

	public String getTagdata() {
		return Tagdata;
	}

	public void setTagdata(String tagdata) {
		Tagdata = tagdata;
	}

	public String getTagtranspose() {
		return Tagtranspose;
	}

	public void setTagtranspose(String tagtranspose) {
		Tagtranspose = tagtranspose;
	}

	public byte getFiletypeid() {
		return Filetypeid;
	}

	public void setFiletypeid(byte filetypeid) {
		Filetypeid = filetypeid;
	}

	public short getImageexportclass() {
		return Imageexportclass;
	}

	public void setImageexportclass(short imageexportclass) {
		Imageexportclass = imageexportclass;
	}

	public String getImageclass() {
		return Imageclass;
	}

	public void setImageclass(String imageclass) {
		Imageclass = imageclass;
	}

	public long getFileid() {
		return Fileid;
	}

	public void setFileid(long fileid) {
		Fileid = fileid;
	}

	public long getImagefileid() {
		return Imagefileid;
	}

	public void setImagefileid(long imagefileid) {
		Imagefileid = imagefileid;
	}

	public Date getCreatedate() {
		return Createdate;
	}

	public void setCreatedate(Date createdate) {
		Createdate = createdate;
	}

	public String getActiveflag() {
		return Activeflag;
	}

	public void setActiveflag(String activeflag) {
		Activeflag = activeflag;
	}

    
    
}
