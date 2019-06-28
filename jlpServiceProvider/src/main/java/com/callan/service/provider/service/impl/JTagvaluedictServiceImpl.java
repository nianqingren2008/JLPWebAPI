package com.callan.service.provider.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callan.service.provider.dao.mapper.JTagvaluedictsMapper;
import com.callan.service.provider.pojo.base.CacheResponse;
import com.callan.service.provider.pojo.cache.LocalCacheable;
import com.callan.service.provider.pojo.db.JTagvaluedicts;
import com.callan.service.provider.service.IJTableFieldDictService;
import com.callan.service.provider.service.IJTagvaluedictService;

@Service
public class JTagvaluedictServiceImpl implements IJTagvaluedictService {
	
	
	@Autowired
	private JTagvaluedictsMapper tagvaluedictsMapper;

	@Override
	public List<JTagvaluedicts> getAll() {
		List<JTagvaluedicts> list = tagvaluedictsMapper.getAll();
		return list;
	}

	@Override
	public JTagvaluedicts getOne(Long id) {
		IJTableFieldDictService base = (IJTableFieldDictService) AopContext.currentProxy();
		Map<Long, JTagvaluedicts> data = (Map<Long, JTagvaluedicts>) base.getAll4Id().getData();
		JTagvaluedicts entity = data.get(id);
		return entity;
	}

	@LocalCacheable
	@Override
	public CacheResponse getAll4Id() {
		Map<Long, JTagvaluedicts> map = new HashMap<>();
		List<JTagvaluedicts> list = tagvaluedictsMapper.getAll();
		for (JTagvaluedicts entity : list) {
			map.put(entity.getId(), entity);
		}
		CacheResponse response = new CacheResponse();
		response.setCode(0);
		response.setData(map);
		return response;
	}


}
