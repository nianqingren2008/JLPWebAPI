package com.callan.service.provider.pojo.advanceQueryBase;

public class QueryDetailModel {
	
	/*
	 * 左括号
	 */
	private String leftqueto;
	/*
	 * 字段名称
	 */
	private String condition;
	/*
	 * 关系类型(AND OR)
	 */
	private String relation;
	/*
	 * 字段值类型
	 */
	private Integer fieldType;
	/*
	 * 字段值
	 */
	private String condValue;
	/*
	 * 右括号
	 */
	private String rightqueto;
	/*
	 * 逻辑类型(= != 大于 小于  NOTLIKE)
	 */
	private String combinator;
	
	public String getLeftqueto() {
		return leftqueto;
	}
	public void setLeftqueto(String leftqueto) {
		this.leftqueto = leftqueto;
	}
	public String getCondition() {
		return condition;
	}
	public void setCondition(String condition) {
		this.condition = condition;
	}
	public String getRelation() {
		return relation;
	}
	public void setRelation(String relation) {
		this.relation = relation;
	}
	public Integer getFieldType() {
		return fieldType;
	}
	public void setFieldType(Integer fieldType) {
		this.fieldType = fieldType;
	}
	public String getCondValue() {
		return condValue;
	}
	public void setCondValue(String condValue) {
		this.condValue = condValue;
	}
	public String getRightqueto() {
		return rightqueto;
	}
	public void setRightqueto(String rightqueto) {
		this.rightqueto = rightqueto;
	}
	public String getCombinator() {
		return combinator;
	}
	public void setCombinator(String combinator) {
		this.combinator = combinator;
	}
	
}
