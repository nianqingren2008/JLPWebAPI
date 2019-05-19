package com.callan.service.provider.pojo.base;

public class ResponseColumn {
	private String title;
	private String dataIndex;
	private String key;
	private Boolean isLongStr;
	private Boolean isSearched;
	private Boolean isTag;
	
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
