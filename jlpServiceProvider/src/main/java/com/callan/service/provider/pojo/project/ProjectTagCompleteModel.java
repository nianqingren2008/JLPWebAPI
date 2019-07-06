package com.callan.service.provider.pojo.project;

import com.alibaba.fastjson.annotation.JSONField;

public class ProjectTagCompleteModel {
	/*
	 * 标签ID
	 */
	@JSONField(name = "_id")
	private Long _id;
	/*
	 * 标签名称
	 */
	private String name;
	/*
	 * 字段类型（1：枚举类型 2:范围类型）
	 */
	private String fieldType;
	/*
	 * 字段值
	 */
	private String fieldValue;
	/*
	 * 标签层级
	 */
	private String tagLevel;
	/*
	 * 默认显示
	 */
	private Boolean isShow;

	/*
	 * 标签完成度
	 */
	private String tagComplete;

	public Long get_id() {
		return _id;
	}

	public void set_id(Long _id) {
		this._id = _id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFieldType() {
		return fieldType;
	}

	public void setFieldType(String fieldType) {
		this.fieldType = fieldType;
	}

	public String getFieldValue() {
		return fieldValue;
	}

	public void setFieldValue(String fieldValue) {
		this.fieldValue = fieldValue;
	}

	public String getTagLevel() {
		return tagLevel;
	}

	public void setTagLevel(String tagLevel) {
		this.tagLevel = tagLevel;
	}

	public Boolean getIsShow() {
		return isShow;
	}

	public void setIsShow(Boolean isShow) {
		this.isShow = isShow;
	}

	public String getTagComplete() {
		return tagComplete;
	}

	public void setTagComplete(String tagComplete) {
		this.tagComplete = tagComplete;
	}

}
