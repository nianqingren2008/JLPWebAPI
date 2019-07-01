package com.callan.service.provider.pojo.queryField;

public class QueryFieldModel {
	/*
	 * 显示名称
	 */
	private String label;
	/*
	 * 字段值
	 */
	private String value;
	/*
	 * 拼音简码
	 */
	private String keyWord;
	/*
	 * 编码
	 */
	private String code;

	/*
	 * 是否为标签字段
	 */
	private Boolean isTag;

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getKeyWord() {
		return keyWord;
	}

	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Boolean getIsTag() {
		return isTag;
	}

	public void setIsTag(Boolean isTag) {
		this.isTag = isTag;
	}

}
