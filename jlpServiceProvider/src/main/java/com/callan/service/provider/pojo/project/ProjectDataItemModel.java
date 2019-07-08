package com.callan.service.provider.pojo.project;

import org.hibernate.validator.constraints.Length;

public class ProjectDataItemModel {
	/*
	 * 患者主索引
	 */
	@Length(min = 1, max = Integer.MAX_VALUE, message = "patientGlobalId值应大于0")
	private Long patientGlobalId;
	/*
	 * 当前行唯一业务ID
	 */
	@Length(min = 1, max = Integer.MAX_VALUE, message = "dataId值应大于0")
	private Long dataId;

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

}
