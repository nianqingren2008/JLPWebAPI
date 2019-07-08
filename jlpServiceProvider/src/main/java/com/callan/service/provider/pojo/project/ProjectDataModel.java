package com.callan.service.provider.pojo.project;

import java.util.List;

import org.hibernate.validator.constraints.Length;

public class ProjectDataModel {
	/*
	 * 课题ID
	 */
	@Length(min = 1, max = Integer.MAX_VALUE, message = "projectId值应大于0")
	private Long projectId;

	/*
	 * 业务分类ID(与标签分类相同)
	 */
	@Length(min = 1, max = Integer.MAX_VALUE, message = "tableClassId值应大于0")
	private Long tableClassId;

	private List<ProjectDataItemModel> items;

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

	public List<ProjectDataItemModel> getItems() {
		return items;
	}

	public void setItems(List<ProjectDataItemModel> items) {
		this.items = items;
	}

}
