package com.callan.service.provider.pojo.statistic;

import java.util.List;

public class StatisticModel {
	// 统计视图ID
	private Long id;
	// 字段值
	private List<String> Key;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public List<String> getKey() {
		return Key;
	}
	public void setKey(List<String> key) {
		Key = key;
	}
	
	
	
}
