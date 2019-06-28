package com.callan.service.provider.pojo.project;

import com.callan.service.provider.pojo.advanceQueryBase.Sorted;

public class ProjectQueryModel {
	/*
     * 课题ID
     */
    private Long projectId;

    /*
     * 数据分类ID
     */
    private Long tableClassId;

    /*
     * 患者主索引(默认值为0)
     */
    private Long patientGlobalId;
    
    /*
     * 视图展示ID
     */
    private String[] queryShowFields;

    /*
     * 数据状态过滤条件
     */
    private Long[] projectstatusIDs;

    /*
     * 排序字段
     */
    private Sorted[] sorted;

    /*
     * 查询条件
     */
    private singleAdvanceQueryModel queries;

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

	public String[] getQueryShowFields() {
		return queryShowFields;
	}

	public void setQueryShowFields(String[] queryShowFields) {
		this.queryShowFields = queryShowFields;
	}

	public Long[] getProjectstatusIDs() {
		return projectstatusIDs;
	}

	public void setProjectstatusIDs(Long[] projectstatusIDs) {
		this.projectstatusIDs = projectstatusIDs;
	}

	public Sorted[] getSorted() {
		return sorted;
	}

	public void setSorted(Sorted[] sorted) {
		this.sorted = sorted;
	}

	public singleAdvanceQueryModel getQueries() {
		return queries;
	}

	public void setQueries(singleAdvanceQueryModel queries) {
		this.queries = queries;
	}
    
    
}
