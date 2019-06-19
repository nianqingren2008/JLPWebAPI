package com.callan.service.provider.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callan.service.provider.dao.mapper.JDownloadFileMapper;
import com.callan.service.provider.dao.mapper.JSystemConfigMapper;
import com.callan.service.provider.pojo.base.CacheResponse;
import com.callan.service.provider.pojo.cache.CacheKey;
import com.callan.service.provider.pojo.cache.LocalCacheable;
import com.callan.service.provider.pojo.db.JDownloadfile;
import com.callan.service.provider.pojo.db.JRoleRight;
import com.callan.service.provider.pojo.db.JSystemconfig;
import com.callan.service.provider.service.IJDownloadFileService;
import com.callan.service.provider.service.IJRoleRightService;
import com.callan.service.provider.service.IJSystemConfigService;

@Service
public class JSystemConfigServiceImpl implements IJSystemConfigService {
	@Autowired
	private JSystemConfigMapper systemConfigMapper;

	@Override
	public JSystemconfig getOne(long id) {
		IJSystemConfigService base = (IJSystemConfigService) AopContext.currentProxy();
		Map<Long, JSystemconfig> data = (Map<Long, JSystemconfig>) base.getAll4Id().getData();
		JSystemconfig roleRight = data.get(id);
		return roleRight;

	}

	@LocalCacheable
	@Override
	public CacheResponse getByClassTypeAndKeyName(@CacheKey String classType,@CacheKey  String keyName) {
		CacheResponse response = new CacheResponse();
		response.setCode(0);
		response.setData(systemConfigMapper.getByClassTypeAndKeyName(classType, keyName));
		return response;
	}

	@LocalCacheable
	@Override
	public CacheResponse getAll4Id() {
		List<JSystemconfig> all = systemConfigMapper.getAll();
		Map<Long, JSystemconfig> map = new HashMap<>();
		for (JSystemconfig entity : all) {
			map.put(entity.getId(), entity);
		}
		CacheResponse response = new CacheResponse();
		response.setCode(0);
		response.setData(map);
		return response;
	}

	@Override
	public List<JSystemconfig> getAll() {
		return systemConfigMapper.getAll();
	}
}
