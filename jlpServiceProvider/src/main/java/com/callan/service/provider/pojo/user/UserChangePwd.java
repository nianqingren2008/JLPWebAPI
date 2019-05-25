package com.callan.service.provider.pojo.user;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class UserChangePwd {
	
	/*
	 * 旧密码
	 */
	@NotBlank(message = "密码不能为空")
	@Size(max = 32, min=32, message="密码长度不对")
	public String userPwd;
	
	/*
	 * 新密码
	 */
	@NotBlank(message = "密码不能为空")
	@Size(max = 32, min=32, message="密码长度不对")
	public String userNewPwd;

	public String getUserPwd() {
		return userPwd;
	}

	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}

	public String getUserNewPwd() {
		return userNewPwd;
	}

	public void setUserNewPwd(String userNewPwd) {
		this.userNewPwd = userNewPwd;
	}

}
