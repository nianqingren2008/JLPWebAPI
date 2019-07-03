package com.callan.service.provider.pojo.user;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

public class UserModel {
	/*
	 * 登录名
	 */
	@NotNull
	@Length(min = 4, max = 16, message = "登录名长度应该在4与16之间")
	private String userCode;
	/*
	 * 密码
	 */
	@NotNull
	@Length(min = 4, max = 16, message = "密码长度不对")
	private String userPwd;
	/*
	 * 验证码
	 */
	private String VerificationCode;

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public String getUserPwd() {
		return userPwd;
	}

	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}

	public String getVerificationCode() {
		return VerificationCode;
	}

	public void setVerificationCode(String verificationCode) {
		VerificationCode = verificationCode;
	}

}
