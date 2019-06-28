package com.callan.service.provider.pojo.project;

public class ProjectDataDefaultStatusModel {
	/*
	 * 课题ID
	 */
    private Long projectId;

    /*
     * 课题数据状态编号
     */
    private Integer projectDataStatusId;

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
    
}
