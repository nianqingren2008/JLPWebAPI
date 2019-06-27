package com.callan.service.provider.pojo.advanceQueryBase;

import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;

public class QueryCollectionModel {
	/*
	 * 左括号
	 */
	private String leftqueto;
	private String id;
	
	@JSONField(label = "abstract")
	private String abstract1;
	/*
	 * 查询条件
	 */
	private List<QueryDetailModel> conds;
	/*
	 * 右括号
	 */
	private String rightqueto;
	/*
	 * 集合操作类别
	 */
	private String setCombinator;
	private String type;
		
		
	
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
	public void setConds(List<QueryDetailModel> conds) {
		this.conds = conds;
	}
	public List<QueryDetailModel> getConds() {
		return conds;
	}
	public String getRightqueto() {
		return rightqueto;
	}
	public void setRightqueto(String rightqueto) {
		this.rightqueto = rightqueto;
	}
	public String getSetCombinator() {
		return setCombinator;
	}
	public void setSetCombinator(String setCombinator) {
		this.setCombinator = setCombinator;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
}
