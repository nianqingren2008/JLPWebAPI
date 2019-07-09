package com.callan.service.provider.pojo.statistic;

import java.util.List;

public class StatisticModel {
	// 统计视图ID
	private Long Id;
	// 字段值
	private List<String> keys;

	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
	}

	public List<String> getKeys() {
		return keys;
	}

	public void setKeys(List<String> keys) {
		this.keys = keys;
	}

}
