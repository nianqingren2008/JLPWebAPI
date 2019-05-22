package com.callan.service.provider.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callan.service.provider.dao.mapper.JRightMapper;
import com.callan.service.provider.pojo.base.CacheResponse;
import com.callan.service.provider.pojo.cache.NativeCacheable;
import com.callan.service.provider.pojo.db.JRight;
import com.callan.service.provider.service.IJRightService;
import com.callan.service.provider.service.IJShowDetailViewService;

@Service
public class JRightServiceImpl implements IJRightService {
	@Autowired
	private JRightMapper  jRightMapper;
	
	@Override
	public JRight getOne(Long id) {
		IJRightService base = (IJRightService) AopContext.currentProxy();
		Map<Long, JRight> data = (Map<Long, JRight>) base.getAll4Id().getData();
		JRight jRight = data.get(id);
		return jRight;
	}
	
	@NativeCacheable
	@Override
	public CacheResponse getAll4Id() {
		List<JRight> list = jRightMapper.getAll();
		Map<Long, JRight> map = new HashMap<Long, JRight>();
		for (JRight jRight : list) {
			map.put(jRight.getId(), jRight);
		}
		CacheResponse response = new CacheResponse();
		response.setCode(0);
		response.setData(map);
		return response;
	}

	@Override
	public JRight getOne(Long id, boolean activityFlag) {
		IJRightService base = (IJRightService) AopContext.currentProxy();
		Map<Long, JRight> data = (Map<Long, JRight>) base.getAll4Id().getData();
		JRight jRight = data.get(id);
		if(activityFlag && jRight != null) {
			if("1".equals(jRight.getActiveflag())){
				return jRight;
			}else {
				return null;
			}
		}
		return jRight;
	}
}
