package com.callan.service.provider.pojo.tableclassdict;

import java.util.Date;

public class JTableclassdict {
	 
    // 流水号
    public Integer Id;
     
    // 表名
    public String Code;
     
    // 表说明
    public String Name;
     
    // 启用标志
    public String Activeflag;
     
    // 创建时间
    public Date Createdate;
     
    // 关联视图编号
    public Integer Viewid;

	public Integer getId() {
		return Id;
	}

	public void setId(Integer id) {
		Id = id;
	}

	public String getCode() {
		return Code;
	}

	public void setCode(String code) {
		Code = code;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getActiveflag() {
		return Activeflag;
	}

	public void setActiveflag(String activeflag) {
		Activeflag = activeflag;
	}

	public Date getCreatedate() {
		return Createdate;
	}

	public void setCreatedate(Date createdate) {
		Createdate = createdate;
	}

	public Integer getViewid() {
		return Viewid;
	}

	public void setViewid(Integer viewid) {
		Viewid = viewid;
	}
    
    
    
}
