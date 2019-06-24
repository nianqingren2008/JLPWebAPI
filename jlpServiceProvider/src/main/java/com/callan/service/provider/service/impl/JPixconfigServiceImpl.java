package com.callan.service.provider.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callan.service.provider.dao.mapper.JPixconfigMapper;
import com.callan.service.provider.pojo.base.CacheResponse;
import com.callan.service.provider.pojo.cache.LocalCacheable;
import com.callan.service.provider.pojo.db.JPixconfig;
import com.callan.service.provider.service.IJPixconfigService;

@Service
public class JPixconfigServiceImpl implements IJPixconfigService {
	@Autowired
	private JPixconfigMapper pixconfigMapper;

	@SuppressWarnings("unchecked")
	@Override
	public JPixconfig getOne(Long id) {
		IJPixconfigService base = (IJPixconfigService) AopContext.currentProxy();
		Map<Long, JPixconfig> data = (Map<Long, JPixconfig>) base.getAll4Id().getData();
		JPixconfig entity = data.get(id);
		return entity;
	}
	
	@LocalCacheable
	@Override
	public CacheResponse getAll4Id() {
		List<JPixconfig> list = pixconfigMapper.getAll();
		Map<Long, JPixconfig> map = new HashMap<Long, JPixconfig>();
		for (JPixconfig entity : list) {
			map.put(entity.getId(), entity);
		}
		CacheResponse response = new CacheResponse();
		response.setCode(0);
		response.setData(map);
		return response;
	}
	
	@LocalCacheable
	@Override
	public CacheResponse getAll4FiledName() {
		List<JPixconfig> list = pixconfigMapper.getAll();
		Map<String, JPixconfig> map = new HashMap<String, JPixconfig>();
		for (JPixconfig entity : list) {
			map.put(entity.getFieldname(), entity);
		}
		CacheResponse response = new CacheResponse();
		response.setCode(0);
		response.setData(map);
		return response;
	}

	@LocalCacheable
	@Override
	public CacheResponse getAll() {
		List<JPixconfig> list = pixconfigMapper.getAll();
		CacheResponse response = new CacheResponse();
		response.setCode(0);
		response.setData(list);
		return response;
	}
}
