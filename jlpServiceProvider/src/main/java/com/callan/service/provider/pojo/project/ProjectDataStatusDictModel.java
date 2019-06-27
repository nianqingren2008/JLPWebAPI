package com.callan.service.provider.pojo.project;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

public class ProjectDataStatusDictModel {
	/*
     * 课题ID
     */
    private Long projectId;
    /*
     * 编号(新增时为0)
     */
    private Integer projectDataStatusId;
    /*
     * 状态名称
     */
    @NotNull
    @Length(min = 1,max = 20,message = "name有效值应在1与20之间")
	private String name;
    /*
     * 数据状态共享级别
     */
    public String statusType;
	public Long getProjectId() {
		return projectId;
	}
	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}
	public Integer getProjectDataStatusId() {
		return projectDataStatusId;
	}
	public void setProjectDataStatusId(Integer projectDataStatusId) {
		this.projectDataStatusId = projectDataStatusId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getStatusType() {
		return statusType;
	}
	public void setStatusType(String statusType) {
		this.statusType = statusType;
	}
    
    
}
