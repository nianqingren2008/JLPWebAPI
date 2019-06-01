package com.callan.service.provider.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callan.service.provider.dao.mapper.JFiletypeMapper;
import com.callan.service.provider.pojo.base.CacheResponse;
import com.callan.service.provider.pojo.cache.NativeCacheable;
import com.callan.service.provider.pojo.db.JFiletype;
import com.callan.service.provider.service.IJFiletypeService;
import com.callan.service.provider.service.IJSystemConfigService;

@Service
public class JFiletypeServiceImpl implements IJFiletypeService {
	@Autowired
	private JFiletypeMapper filetypeMapper;

	@Override
	@NativeCacheable
	public CacheResponse getAll() {
		CacheResponse response = new CacheResponse();
		response.setCode(0);
		response.setData(filetypeMapper.getAll());
		return response;
	}

	@Override
	public JFiletype getOne(long id) {
		IJSystemConfigService base = (IJSystemConfigService) AopContext.currentProxy();
		Map<Long, JFiletype> data = (Map<Long, JFiletype>) base.getAll4Id().getData();
		JFiletype entity = data.get(id);
		return entity;
	}

	@NativeCacheable
	@Override
	public CacheResponse getAll4Id() {
		List<JFiletype> all = filetypeMapper.getAll();
		Map<Long, JFiletype> map = new HashMap<>();
		for (JFiletype entity : all) {
			map.put(entity.getId(), entity);
		}
		CacheResponse response = new CacheResponse();
		response.setCode(0);
		response.setData(map);
		return response;
	}

}
