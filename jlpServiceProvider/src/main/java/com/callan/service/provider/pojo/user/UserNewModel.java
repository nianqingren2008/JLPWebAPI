package com.callan.service.provider.pojo.user;

public class UserNewModel {
	/*
	 * 用户姓名
	 */
	private String userName;
	/*
	 * 用户登录编码
	 */
	private String userLoginCode;
	/*
	 * 用户角色编号
	 */
	private Long userRole;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserLoginCode() {
		return userLoginCode;
	}

	public void setUserLoginCode(String userLoginCode) {
		this.userLoginCode = userLoginCode;
	}

	public Long getUserRole() {
		return userRole;
	}

	public void setUserRole(Long userRole) {
		this.userRole = userRole;
	}

}
