package com.callan.service.provider.pojo.statistic;

import org.apache.commons.lang3.StringUtils;

public class StatisticFieldModel {
	private String tableName;

	private String fieldName;

	private String fieldTitle;

	private String fieldTrans;

	
	public String getTableName() {
		return tableName;
	}


	public void setTableName(String tableName) {
		this.tableName = tableName;
	}


	public String getFieldName() {
		return fieldName;
	}


	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}


	public String getFieldTitle() {
		return fieldTitle;
	}


	public void setFieldTitle(String fieldTitle) {
		this.fieldTitle = fieldTitle;
	}


	public String getFieldTrans() {
		return fieldTrans;
	}


	public void setFieldTrans(String fieldTrans) {
		this.fieldTrans = fieldTrans;
	}


	public String toString(boolean IsGroup) {
		String ret = "";
		if (StringUtils.isBlank(fieldTrans)) {
			ret = tableName + "." + fieldName;
		} else {
			ret = String.format(fieldTrans, tableName + "." + fieldName);
			if (!IsGroup) {
				ret += " AS " + fieldName;
			}
		}
		return ret;
	}
}
