package com.callan.service.provider.pojo.project;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

public class ProjectTagDataModel {
	/*
	 * 课题ID
	 */
	@Range(min = 1, max = Long.MAX_VALUE, message = "projectId值应大于0")
	private Long projectId;

	/*
	 * 业务分类ID(与标签分类相同)
	 */
	@Range(min = 1, max = Long.MAX_VALUE, message = "tableClassId值应大于0")
	private Long tableClassId;

	/*
	 * 患者主索引
	 */
	@Range(min = 1, max = Long.MAX_VALUE, message = "patientGlobalId值应大于0")
	private Long patientGlobalId;
	/*
	 * 当前行唯一业务ID
	 */
	@Range(min = 1, max = Long.MAX_VALUE, message = "dataId值应大于0")
	private Long dataId;

	/*
	 * 标签ID
	 */
	@Range(min = 1, max = Long.MAX_VALUE, message = "tagId值应大于0")
	private Long tagId;

	/*
	 * 标签值
	 */
	@Length(min = 0, max = 50, message = "tagValue范围应在0与50之间")
	private String tagValue;

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public Long getTableClassId() {
		return tableClassId;
	}

	public void setTableClassId(Long tableClassId) {
		this.tableClassId = tableClassId;
	}

	public Long getPatientGlobalId() {
		return patientGlobalId;
	}

	public void setPatientGlobalId(Long patientGlobalId) {
		this.patientGlobalId = patientGlobalId;
	}

	public Long getDataId() {
		return dataId;
	}

	public void setDataId(Long dataId) {
		this.dataId = dataId;
	}

	public Long getTagId() {
		return tagId;
	}

	public void setTagId(Long tagId) {
		this.tagId = tagId;
	}

	public String getTagValue() {
		return tagValue;
	}

	public void setTagValue(String tagValue) {
		this.tagValue = tagValue;
	}
}
