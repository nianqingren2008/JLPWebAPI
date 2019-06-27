package com.callan.service.provider.pojo.advanceQueryBase;

import java.util.List;

public class Queries {
	private List<QueryDetailModel> queryConds;
	private QueryIncludesEX queryIncludesEX;
	
	public List<QueryDetailModel> getQueryConds() {
		return queryConds;
	}
	public void setQueryConds(List<QueryDetailModel> queryConds) {
		this.queryConds = queryConds;
	}
	public QueryIncludesEX getQueryIncludesEX() {
		return queryIncludesEX;
	}
	public void setQueryIncludesEX(QueryIncludesEX queryIncludesEX) {
		this.queryIncludesEX = queryIncludesEX;
	}
	
}
