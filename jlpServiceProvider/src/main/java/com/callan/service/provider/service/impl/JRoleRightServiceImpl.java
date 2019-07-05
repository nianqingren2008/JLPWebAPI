package com.callan.service.provider.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callan.service.provider.dao.mapper.JRoleRightMapper;
import com.callan.service.provider.pojo.base.CacheResponse;
import com.callan.service.provider.pojo.cache.LocalCacheable;
import com.callan.service.provider.pojo.db.JRoleRight;
import com.callan.service.provider.service.IJRoleRightService;

@Service
public class JRoleRightServiceImpl implements IJRoleRightService {
	@Autowired
	private JRoleRightMapper  roleRightMapper;
	
	@Override
	public List<JRoleRight> getByRoleId(Long roleId) {
		IJRoleRightService base = (IJRoleRightService) AopContext.currentProxy();
		Map<Long, List<JRoleRight>> data = (Map<Long, List<JRoleRight>>) base.getAll4RoleId().getData();
		List<JRoleRight> roleRight = data.get(roleId);
		return roleRight;
	}

	@LocalCacheable
	@Override
	public CacheResponse getAll4RoleId() {
		List<JRoleRight> all = roleRightMapper.getAll();
		Map<Long,List<JRoleRight>> map = new HashMap<>();
		List<JRoleRight> roleRightList = null;
		for(JRoleRight roleRight : all) {
			if(map.get(roleRight.getRoleid()) == null) {
				roleRightList = new ArrayList<JRoleRight>();
			}
			roleRightList.add(roleRight);
			map.put(roleRight.getRoleid(),roleRightList);
		}
		
		CacheResponse response = new CacheResponse();
		response.setCode(0);
		response.setData(map);
		return response;
	}

	@Override
	public void update(JRoleRight roleRight) {
		roleRightMapper.insert(roleRight);
	}

	@Override
	public void save(JRoleRight roleright) {
		roleRightMapper.update(roleright);
	}

}
