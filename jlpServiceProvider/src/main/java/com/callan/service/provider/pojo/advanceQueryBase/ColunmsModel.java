package com.callan.service.provider.pojo.advanceQueryBase;

public class ColunmsModel {
	/*
	 * 名称
	 */
	public String title;
	/*
	 * 索引字段
	 */
	public String dataIndex;
	/*
	 * 键值
	 */
	public String key;
	/*
	 * 是否大字段
	 */
	public Boolean isLongStr;
	/*
	 * 是否能搜索
	 */
	public Boolean isSearched;

	/*
	 * 是否为标签值
	 */
	public Boolean isTag;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDataIndex() {
		return dataIndex;
	}

	public void setDataIndex(String dataIndex) {
		this.dataIndex = dataIndex;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public Boolean getIsLongStr() {
		return isLongStr;
	}

	public void setIsLongStr(Boolean isLongStr) {
		this.isLongStr = isLongStr;
	}

	public Boolean getIsSearched() {
		return isSearched;
	}

	public void setIsSearched(Boolean isSearched) {
		this.isSearched = isSearched;
	}

	public Boolean getIsTag() {
		return isTag;
	}

	public void setIsTag(Boolean isTag) {
		this.isTag = isTag;
	}

}