package com.callan.service.provider.pojo.advanceQueryBase;

public class QueryIncludesEX {
	private String condType;
	private QueryIncludesEXCondition includes;
	private QueryIncludesEXCondition excludes;
	
	public String getCondType() {
		return condType;
	}
	public void setCondType(String condType) {
		this.condType = condType;
	}
	public QueryIncludesEXCondition getIncludes() {
		return includes;
	}
	public void setIncludes(QueryIncludesEXCondition includes) {
		this.includes = includes;
	}
	public QueryIncludesEXCondition getExcludes() {
		return excludes;
	}
	public void setExcludes(QueryIncludesEXCondition excludes) {
		this.excludes = excludes;
	}
	
}
