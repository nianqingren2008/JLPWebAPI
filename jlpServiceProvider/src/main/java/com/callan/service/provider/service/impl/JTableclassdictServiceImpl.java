package com.callan.service.provider.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.callan.service.provider.dao.mapper.JTableclassdictMapper;
import com.callan.service.provider.pojo.base.CacheResponse;
import com.callan.service.provider.pojo.cache.LocalCacheable;
import com.callan.service.provider.pojo.db.JSystemconfig;
import com.callan.service.provider.pojo.tableclassdict.JTableclassdict;
import com.callan.service.provider.service.IJSystemConfigService;
import com.callan.service.provider.service.IJTableclassdictService;

@Service
public class JTableclassdictServiceImpl implements IJTableclassdictService{

	@Autowired
	private JTableclassdictMapper  jTableclassdictMapper;

	@LocalCacheable
	@Override
	public List<JTableclassdict> getAll() {
		return jTableclassdictMapper.getAll();
	}

	@LocalCacheable
	@Override
	public CacheResponse getAll4Id() {
		List<JTableclassdict> all = jTableclassdictMapper.getAll();
		Map<Long, JTableclassdict> map = new HashMap<>();
		for (JTableclassdict entity : all) {
			map.put(entity.getId(), entity);
		}
		CacheResponse response = new CacheResponse();
		response.setCode(0);
		response.setData(map);
		return response;
	}
	
	@Override
	public JTableclassdict getOne(Long id) {
		IJTableclassdictService base = (IJTableclassdictService) AopContext.currentProxy();
		Map<Long, JTableclassdict> data = (Map<Long, JTableclassdict>) base.getAll4Id().getData();
		JTableclassdict entity = data.get(id);
		return entity;
	}

}
