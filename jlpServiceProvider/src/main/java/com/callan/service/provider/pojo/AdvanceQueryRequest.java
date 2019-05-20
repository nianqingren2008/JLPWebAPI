package com.callan.service.provider.pojo;

import java.util.List;

import com.callan.service.provider.pojo.advanceQueryBase.Queries;
import com.callan.service.provider.pojo.advanceQueryBase.Sorted;

public class AdvanceQueryRequest {
	/*
	 * 排序字段
	 */
	private List<Sorted> sorted;
	/*
	 * 
	 */
	private String [] filtered;
	/*
	 * 视图ID
	 */
	private Long viewId;
	/*
	 * 纳排查询条件
	 */
	private Queries queries;
	/*
	 * 查询显示项
	 */
	private List<Long> queryShowFields;
	/*
	 * 患者主索引
	 */
	private Long patientGlobalId;
	
	public List<Sorted> getSorted() {
		return sorted;
	}
	public void setSorted(List<Sorted> sorted) {
		this.sorted = sorted;
	}
	public String[] getFiltered() {
		return filtered;
	}
	public void setFiltered(String[] filtered) {
		this.filtered = filtered;
	}
	public Long getViewId() {
		return viewId;
	}
	public void setViewId(Long viewId) {
		this.viewId = viewId;
	}
	public Queries getQueries() {
		return queries;
	}
	public void setQueries(Queries queries) {
		this.queries = queries;
	}
	public List<Long> getQueryShowFields() {
		return queryShowFields;
	}
	public void setQueryShowFields(List<Long> queryShowFields) {
		this.queryShowFields = queryShowFields;
	}
	public Long getPatientGlobalId() {
		return patientGlobalId;
	}
	public void setPatientGlobalId(Long patientGlobalId) {
		this.patientGlobalId = patientGlobalId;
	}
	
}
