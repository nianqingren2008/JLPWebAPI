package com.callan.service.provider.pojo.queryField;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.callan.service.provider.pojo.advanceQueryBase.QueryDetailModel;

public class QueryRecordModel {
	/*
	 * 主键字段(新增时为0)
	 */
	private Long id;
	/*
	 * 查询名称
	 */
	@NotNull
	@Length(min = 1, max = 30, message = "查询条件名称应在30个字符以内")
	private String qrName;
	/*
	 * 查询条件
	 */
	private List<QueryDetailModel> queries;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getQrName() {
		return qrName;
	}

	public void setQrName(String qrName) {
		this.qrName = qrName;
	}

	public List<QueryDetailModel> getQueries() {
		return queries;
	}

	public void setQueries(List<QueryDetailModel> queries) {
		this.queries = queries;
	}

}
