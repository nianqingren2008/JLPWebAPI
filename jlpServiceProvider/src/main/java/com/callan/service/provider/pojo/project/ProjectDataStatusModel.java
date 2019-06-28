package com.callan.service.provider.pojo.project;

public class ProjectDataStatusModel {
	/*
	 * 课题ID
	 */
	private Long projectid;
	/*
	 * 患者主索引
	 */
	private Long patientGlobalId;
	/*
	 * 课题数据状态ID
	 */
	private Integer projectDataStatusId;

	public Long getProjectid() {
		return projectid;
	}

	public void setProjectid(Long projectid) {
		this.projectid = projectid;
	}

	public Long getPatientGlobalId() {
		return patientGlobalId;
	}

	public void setPatientGlobalId(Long patientGlobalId) {
		this.patientGlobalId = patientGlobalId;
	}

	public Integer getProjectDataStatusId() {
		return projectDataStatusId;
	}

	public void setProjectDataStatusId(Integer projectDataStatusId) {
		this.projectDataStatusId = projectDataStatusId;
	}

}
