package com.callan.service.provider.pojo.project;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

public class ProjectTagModel {
	/*
     * 标签ID
     */
    @NotNull
    private Long id;
    /*
     * 课题ID
     */
    @NotNull
    private Long projectId;
    /*
     * 标签名称
     */
    @NotNull
    @Length(min = 1,max = 50,message = "name有效值应在1与50之间")
    private String name;
    /*
     * 字段类型（1：枚举类型 2:范围类型）
     */
    @NotNull
    @Length(min = 1,max = 1,message = "fieldType有效值应在1与1之间")
    private String fieldType;
    /*
     * 字段值
     */
    @NotNull
    private String fieldValue;
    /*
     * 标签层级
     */
    @NotNull
    @Length(min = 1,max = 2,message = "tagLevel有效值应在1与2之间")
    private String tagLevel;
    /*
     * 默认显示
     */
    @NotNull
    private Boolean isShow;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getProjectId() {
		return projectId;
	}
	public void setProjectId(Long projectId) {
		this.projectId = projectId;
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
}
