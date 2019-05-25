package com.callan.service.provider.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callan.service.provider.dao.mapper.JRightMapper;
import com.callan.service.provider.dao.mapper.JUserMapper;
import com.callan.service.provider.pojo.base.CacheResponse;
import com.callan.service.provider.pojo.cache.NativeCacheable;
import com.callan.service.provider.pojo.db.JRight;
import com.callan.service.provider.pojo.db.JUser;
import com.callan.service.provider.service.IJRightService;
import com.callan.service.provider.service.IJShowDetailViewService;
import com.callan.service.provider.service.IJUserService;

@Service
public class JUserServiceImpl implements IJUserService {
	@Autowired
	private JUserMapper  jUserMapper;
	
	@Override
	public JUser getOne(Long id) {
		return jUserMapper.getOne(id);
	}

	@Override
	public void updatePwd(JUser user) {
		jUserMapper.updatePwd(user);
	}

	@Override
	public JUser login(String logincode, String loginpwd) {
		List<JUser> list = jUserMapper.getUserByCodeAndPwd(logincode,loginpwd);
		if(list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}
}
