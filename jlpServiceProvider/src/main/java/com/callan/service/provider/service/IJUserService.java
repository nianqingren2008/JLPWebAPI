package com.callan.service.provider.service;

import com.callan.service.provider.pojo.db.JUser;

public interface IJUserService {

	/**
	 * 根据ID获取用户
	 * @param id
	 * @return
	 */
	JUser getOne(Long id);
	
	/**
	 * 修改密码
	 * @param user
	 */
	void updatePwd(JUser user);

	/**
	 * 根据登录名和密码获取用户
	 * @param logincode
	 * @param loginpwd
	 * @return
	 */
	JUser login(String logincode, String loginpwd);
}