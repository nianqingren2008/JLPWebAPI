package com.callan.service.provider.pojo.advanceQueryBase;

import java.util.List;

public class Queries {
	private List<QueryConds> queryConds;
	private QueryIncludesEX queryIncludesEX;
	
	public List<QueryConds> getQueryConds() {
		return queryConds;
	}
	public void setQueryConds(List<QueryConds> queryConds) {
		this.queryConds = queryConds;
	}
	public QueryIncludesEX getQueryIncludesEX() {
		return queryIncludesEX;
	}
	public void setQueryIncludesEX(QueryIncludesEX queryIncludesEX) {
		this.queryIncludesEX = queryIncludesEX;
	}
	
}
