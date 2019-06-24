package com.callan.service.provider.pojo.project;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

public class ProjectModel {
	/*
	 * 课题编号
	 */
	private Long courseID;
	/*
	 * 课题名称
	 */
	@NotNull
	@Length(max = 100, min = 1, message = "courseName有效值应在1与100之间")
	private String courseName;

	/*
	 * 图像地址
	 */
	@Length(max = 128, min = 0, message = "imageUrl有效值应在0与128之间")
	private String imageUrl;
	/*
	 * 课题英文
	 */
	@Length(max = 255, min = 0, message = "englishName有效值应在0与255之间")
	private String englishName;
	/*
	 * 课题类别
	 */
	@NotNull
	@Length(max = 50, min = 0, message = "cateroy有效值应在0与50之间")
	private String cateroy;
	/*
	 * 课题描述
	 */
	@Length(max = 2000, min = 0, message = "description有效值应在0与2000之间")
	private String description;

	/*
	 * 课题时间
	 */
	private String[] timeproject;

	/*
	 * 课题发起组织
	 */
	@Length(max = 128, min = 0, message = "founders有效值应在0与128之间")
	private String founders;
	/*
	 * 课题注册号
	 */
	@Length(max = 100, min = 0, message = "registerid有效值应在0与100之间")
	private String registerid;
	/*
	 * 伦理备案号
	 */
	@Length(max = 100, min = 0, message = "ethicalnumber有效值应在0与100之间")
	private String ethicalnumber;

	/*
	 * 课题共享类别
	 */
	@NotNull
	private Boolean isShared;

	public Long getCourseID() {
		return courseID;
	}

	public void setCourseID(Long courseID) {
		this.courseID = courseID;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getEnglishName() {
		return englishName;
	}

	public void setEnglishName(String englishName) {
		this.englishName = englishName;
	}

	public String getCateroy() {
		return cateroy;
	}

	public void setCateroy(String cateroy) {
		this.cateroy = cateroy;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String[] getTimeproject() {
		return timeproject;
	}

	public void setTimeproject(String[] timeproject) {
		this.timeproject = timeproject;
	}

	public String getFounders() {
		return founders;
	}

	public void setFounders(String founders) {
		this.founders = founders;
	}

	public String getRegisterid() {
		return registerid;
	}

	public void setRegisterid(String registerid) {
		this.registerid = registerid;
	}

	public String getEthicalnumber() {
		return ethicalnumber;
	}

	public void setEthicalnumber(String ethicalnumber) {
		this.ethicalnumber = ethicalnumber;
	}

	public Boolean getIsShared() {
		return isShared;
	}

	public void setIsShared(Boolean isShared) {
		this.isShared = isShared;
	}

	
}
