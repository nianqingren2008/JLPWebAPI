package com.callan.service.provider.pojo.project;

public class ProjectDataStatusChangeModel {
	/*
	 * 课题ID
	 */
	private Long projectId;
	/*
	 * 需要变更的状态ID
	 */
	private Integer oldStatusId;
	/*
	 * 变更状态ID
	 */
	private Integer newStatusId;

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public Integer getOldStatusId() {
		return oldStatusId;
	}

	public void setOldStatusId(Integer oldStatusId) {
		this.oldStatusId = oldStatusId;
	}

	public Integer getNewStatusId() {
		return newStatusId;
	}

	public void setNewStatusId(Integer newStatusId) {
		this.newStatusId = newStatusId;
	}

}
