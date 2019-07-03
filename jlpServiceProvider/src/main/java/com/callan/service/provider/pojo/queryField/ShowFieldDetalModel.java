package com.callan.service.provider.pojo.queryField;

import java.util.List;

import javax.validation.constraints.NotNull;

public class ShowFieldDetalModel {
	/*
	 * 视图编号
	 */
	@NotNull
	private String pageName;

	/*
	 * 选中字段
	 */
	public List<Long> fieldKeys;

	public String getPageName() {
		return pageName;
	}

	public void setPageName(String pageName) {
		this.pageName = pageName;
	}

	public List<Long> getFieldKeys() {
		return fieldKeys;
	}

	public void setFieldKeys(List<Long> fieldKeys) {
		this.fieldKeys = fieldKeys;
	}

}
