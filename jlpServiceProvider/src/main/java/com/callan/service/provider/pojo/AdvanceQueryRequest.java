package com.callan.service.provider.pojo;

import java.util.List;

import com.callan.service.provider.pojo.advanceQueryBase.Queries;
import com.callan.service.provider.pojo.advanceQueryBase.Sorted;

public class AdvanceQueryRequest {
	private List<Sorted> sorted;
	private String [] filtered;
	private Long viewId;
	private Queries queries;
	private List<Integer> queryShowFields;
	
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
	public List<Integer> getQueryShowFields() {
		return queryShowFields;
	}
	public void setQueryShowFields(List<Integer> queryShowFields) {
		this.queryShowFields = queryShowFields;
	}
	
}
