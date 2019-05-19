package com.callan.service.provider.pojo.advanceQueryBase;

import com.alibaba.fastjson.annotation.JSONField;

public class QueryIncludesEXCondition {
	private String leftqueto;
	private String id;
	
	@JSONField(label = "abstract")
	private String abstract1;
	
	private QueryConds conds;
	
	public String getLeftqueto() {
		return leftqueto;
	}
	public void setLeftqueto(String leftqueto) {
		this.leftqueto = leftqueto;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getAbstract1() {
		return abstract1;
	}
	public void setAbstract1(String abstract1) {
		this.abstract1 = abstract1;
	}
	public QueryConds getConds() {
		return conds;
	}
	public void setConds(QueryConds conds) {
		this.conds = conds;
	}
	
}
