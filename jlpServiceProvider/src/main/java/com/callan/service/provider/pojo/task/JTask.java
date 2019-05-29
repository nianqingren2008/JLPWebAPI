package com.callan.service.provider.pojo.task;

import java.util.Date;

public class JTask {
   //流水号
   private long Id;
 
   // 用户ID
   private long Userid ;
   //任务名称
   private String Name ;
   
  // 任务类型(1:结果下载 2：图像下载 )
   private String Tasktype ;
   
  // 任务状态(1：未开始，2：进行中，3：已完成，4：已撤销)
   private String Status ;
   
  // 完成进度
   private String Progress ;
   
   // 任务开始时间
   private Date Startdate ;
   
  // 任务结束时间(完成时间/撤销时间)
   private Date Enddate ;
   
  // 任务创建时间
   private Date Createdate ;
   
  // 启用标志
   private String Activeflag ;

public long getId() {
	return Id;
}

public void setId(long id) {
	Id = id;
}

public long getUserid() {
	return Userid;
}

public void setUserid(long userid) {
	Userid = userid;
}

public String getName() {
	return Name;
}

public void setName(String name) {
	Name = name;
}

public String getTasktype() {
	return Tasktype;
}

public void setTasktype(String tasktype) {
	Tasktype = tasktype;
}

public String getStatus() {
	return Status;
}

public void setStatus(String status) {
	Status = status;
}

public String getProgress() {
	return Progress;
}

public void setProgress(String progress) {
	Progress = progress;
}

public Date getStartdate() {
	return Startdate;
}

public void setStartdate(Date startdate) {
	Startdate = startdate;
}

public Date getEnddate() {
	return Enddate;
}

public void setEnddate(Date enddate) {
	Enddate = enddate;
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
