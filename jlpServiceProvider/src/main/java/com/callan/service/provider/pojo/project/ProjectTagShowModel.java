package com.callan.service.provider.pojo.project;

import javax.validation.constraints.NotNull;

public class ProjectTagShowModel {
	/*
	 * 标签ID
	 */
	@NotNull
	private Long id;
	/*
	 * 显示状态
	 */
	@NotNull
	private Boolean isShow;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Boolean getIsShow() {
		return isShow;
	}

	public void setIsShow(Boolean isShow) {
		this.isShow = isShow;
	}

}
