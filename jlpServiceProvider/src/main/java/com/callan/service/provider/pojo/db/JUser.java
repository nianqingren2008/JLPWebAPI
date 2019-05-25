package com.callan.service.provider.pojo.db;

import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.alibaba.fastjson.annotation.JSONField;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;

@Api
public class JUser {
	/*
	 * 用户ID
	 */
	@JSONField(label = "Id")
	@ApiParam("用户ID")
	public Long Id;

	/*
	 * 登录号
	 */
	@JSONField(label = "Logincode")
	@ApiParam("登录名")
	@NotBlank(message = "登录名不能为空")
	@Size(max = 16, min = 4, message = "登录名长度应该在4-16位之间")
	public String Logincode;

	/*
	 * 姓名
	 */
	@JSONField(label = "Name")
	@ApiParam("姓名")
	public String Name;
	/*
	 * 登录密码
	 */
	@JSONField(label = "Loginpwd")
	@ApiParam("登录密码")
	@NotBlank(message = "密码不能为空")
	@Size(max = 32, min = 32, message = "密码长度不对")
	public String Loginpwd;

	/*
	 * 性别
	 */
	@JSONField(label = "Sex")
	@ApiParam("性别")
	public String Sex;

	/*
	 * 身份证
	 */
	@JSONField(label = "Idcards")
	@ApiParam("身份证")
	public String Idcards;

	/*
	 * 生日
	 */
	@JSONField(label = "Birthday")
	@ApiParam("生日")
	public Date Birthday;

	/*
	 * 手机号
	 */
	@JSONField(label = "Telnum")
	@ApiParam("手机号")
	public String Telnum;

	/*
	 * 秘钥
	 */
	@JSONField(label = "Secretkey")
	@ApiParam("秘钥")
	public String Secretkey;

	/*
	 * 登录凭证
	 */
	@JSONField(label = "Token")
	@ApiParam("登录凭证")
	public String Token;

	/*
	 * 启用标识
	 */
	@JSONField(label = "Activeflag")
	@ApiParam("启用标识")
	public String Activeflag;

	/*
	 * 用户角色
	 */
	@JSONField(label = "Userrole")
	@ApiParam("用户角色")
	public Long Userrole;

	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
	}

	public String getLogincode() {
		return Logincode;
	}

	public void setLogincode(String logincode) {
		Logincode = logincode;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getLoginpwd() {
		return Loginpwd;
	}

	public void setLoginpwd(String loginpwd) {
		Loginpwd = loginpwd;
	}

	public String getSex() {
		return Sex;
	}

	public void setSex(String sex) {
		Sex = sex;
	}

	public String getIdcards() {
		return Idcards;
	}

	public void setIdcards(String idcards) {
		Idcards = idcards;
	}

	public Date getBirthday() {
		return Birthday;
	}

	public void setBirthday(Date birthday) {
		Birthday = birthday;
	}

	public String getTelnum() {
		return Telnum;
	}

	public void setTelnum(String telnum) {
		Telnum = telnum;
	}

	public String getSecretkey() {
		return Secretkey;
	}

	public void setSecretkey(String secretkey) {
		Secretkey = secretkey;
	}

	public String getToken() {
		return Token;
	}

	public void setToken(String token) {
		Token = token;
	}

	public String getActiveflag() {
		return Activeflag;
	}

	public void setActiveflag(String activeflag) {
		Activeflag = activeflag;
	}

	public Long getUserrole() {
		return Userrole;
	}

	public void setUserrole(Long userrole) {
		Userrole = userrole;
	}

}
