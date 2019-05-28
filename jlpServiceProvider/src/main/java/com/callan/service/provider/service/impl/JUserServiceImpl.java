package com.callan.service.provider.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callan.service.provider.dao.mapper.JUserMapper;
import com.callan.service.provider.pojo.base.CacheResponse;
import com.callan.service.provider.pojo.cache.NativeCacheable;
import com.callan.service.provider.pojo.db.JShowView;
import com.callan.service.provider.pojo.db.JUser;
import com.callan.service.provider.service.IJShowViewService;
import com.callan.service.provider.service.IJUserService;

@Service
public class JUserServiceImpl implements IJUserService {
	@Autowired
	private JUserMapper  jUserMapper;
	
	@Override
	public JUser getOne(Long id) {
		IJUserService base = (IJUserService) AopContext.currentProxy();
		Map<Long, JUser> data = (Map<Long, JUser>) base.getAll4Id().getData();
		JUser user = data.get(id);
		return user;
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

	@NativeCacheable
	@Override
	public CacheResponse getAll4Id() {
		List<JUser> list = jUserMapper.getAll();
		Map<Long, JUser> map = new HashMap<Long, JUser>();
		for (JUser user : list) {
			map.put(user.getId(), user);
		}
		CacheResponse response = new CacheResponse();
		response.setCode(0);
		response.setData(map);
		return response;
	}

	@Override
	public Long getByToken(String authorization) {
		
		return jUserMapper.getByToken(authorization);
	}
}
