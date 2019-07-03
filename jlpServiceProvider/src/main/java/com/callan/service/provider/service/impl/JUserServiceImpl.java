package com.callan.service.provider.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callan.service.provider.config.JLPLog;
import com.callan.service.provider.config.ThreadPoolConfig;
import com.callan.service.provider.dao.mapper.JUserMapper;
import com.callan.service.provider.pojo.db.JUser;
import com.callan.service.provider.service.IJUserService;

@Service
public class JUserServiceImpl implements IJUserService {
	@Autowired
	private JUserMapper  jUserMapper;
	
	@Override
	public JUser getOne(Long id) {
//		IJUserService base = (IJUserService) AopContext.currentProxy();
//		Map<Long, JUser> data = (Map<Long, JUser>) base.getAll4Id().getData();
//		JUser user = data.get(id);
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

//	@LocalCacheable
//	@Override
//	public CacheResponse getAll4Id() {
//		List<JUser> list = jUserMapper.getAll();
//		Map<Long, JUser> map = new HashMap<Long, JUser>();
//		for (JUser user : list) {
//			map.put(user.getId(), user);
//		}
//		CacheResponse response = new CacheResponse();
//		response.setCode(0);
//		response.setData(map);
//		return response;
//	}

	@Override
	public Long getUserRoleByToken(String authorization) {
		return jUserMapper.getUserRoleByToken(authorization);
	}

	@Override
	public Long getIdByToken(String authorization) {
		try {
			return jUserMapper.getIdByToken(authorization);
		}catch(Exception e) {
			JLPLog log = ThreadPoolConfig.getBaseContext();
			log.error(e);
		}
		return null;
	}

	@Override
	public JUser getUserByToken(String authorization) {
		return jUserMapper.getUserByToken(authorization);
	}

	@Override
	public void update(JUser users) {
		jUserMapper.update(users);
	}

	@Override
	public List<JUser> getAll() {
		return jUserMapper.getAll();
	}

	@Override
	public List<JUser> getByLogincode(String userLoginCode) {
		return jUserMapper.getByLogincode(userLoginCode);
	}

	@Override
	public void save(JUser user) {
		jUserMapper.insert(user);
	}
}
