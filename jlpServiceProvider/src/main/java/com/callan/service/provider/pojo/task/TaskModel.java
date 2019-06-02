package com.callan.service.provider.pojo.task;

import java.util.Date;

/*
 *  任务信息模板
 */
public class TaskModel {
 
    // 任务ID
    public long id;
    
    // 任务名称
    public String name;
    
    // 任务状态
    public String status;
    
    // 任务类别
    public String taskType;
    
    // 任务进度
    public String progress;
    
    // 任务创建时间
    public Date createDate;
    
    // 任务开始时间
    public Date startDate;
    
    // 任务结束时间
    public Date endDate;
    
    // 文件路径
    public String fileUrl;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTaskType() {
		return taskType;
	}

	public void setTaskType(String taskType) {
		this.taskType = taskType;
	}

	public String getProgress() {
		return progress;
	}

	public void setProgress(String progress) {
		this.progress = progress;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getFileUrl() {
		return fileUrl;
	}

	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}
    
    
}
