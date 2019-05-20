package com.callan.service.provider.pojo.advanceQueryBase;

import java.util.List;

public class QueryIncludesEX {
	private String condType;
	private List<QueryIncludesEXCondition> includes;
	private List<QueryIncludesEXCondition> excludes;
	
	public String getCondType() {
		return condType;
	}
	public void setCondType(String condType) {
		this.condType = condType;
	}
	public List<QueryIncludesEXCondition> getIncludes() {
		return includes;
	}
	public void setIncludes(List<QueryIncludesEXCondition> includes) {
		this.includes = includes;
	}
	public List<QueryIncludesEXCondition> getExcludes() {
		return excludes;
	}
	public void setExcludes(List<QueryIncludesEXCondition> excludes) {
		this.excludes = excludes;
	}
	
	
}
