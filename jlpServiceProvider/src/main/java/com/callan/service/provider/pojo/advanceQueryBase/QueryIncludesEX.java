package com.callan.service.provider.pojo.advanceQueryBase;

import java.util.List;

public class QueryIncludesEX {
	private String condType;
	private List<QueryCollectionModel> includes;
	private List<QueryCollectionModel> excludes;
	
	public String getCondType() {
		return condType;
	}
	public void setCondType(String condType) {
		this.condType = condType;
	}
	public List<QueryCollectionModel> getIncludes() {
		return includes;
	}
	public void setIncludes(List<QueryCollectionModel> includes) {
		this.includes = includes;
	}
	public List<QueryCollectionModel> getExcludes() {
		return excludes;
	}
	public void setExcludes(List<QueryCollectionModel> excludes) {
		this.excludes = excludes;
	}
	
	
}
