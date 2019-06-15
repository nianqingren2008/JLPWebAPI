package com.callan.service.provider.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callan.service.provider.config.JLPConts;
import com.callan.service.provider.dao.mapper.JPageviewMapper;
import com.callan.service.provider.dao.mapper.JRightMapper;
import com.callan.service.provider.pojo.base.CacheResponse;
import com.callan.service.provider.pojo.cache.LocalCacheable;
import com.callan.service.provider.pojo.db.JPageview;
import com.callan.service.provider.pojo.db.JRight;
import com.callan.service.provider.service.IJPageViewService;
import com.callan.service.provider.service.IJRightService;
import com.callan.service.provider.service.IJShowDetailViewService;

@Service
public class JPageViewServiceImpl implements IJPageViewService {
	@Autowired
	private JPageviewMapper  pageviewMapper;
	
	@Override
	public JPageview getOne(Long id) {
		IJPageViewService base = (IJPageViewService) AopContext.currentProxy();
		Map<Long, JPageview> data = (Map<Long, JPageview>) base.getAll4Code().getData();
		JPageview entity = data.get(id);
		return entity;
	}
	
	@LocalCacheable
	@Override
	public CacheResponse getAll4Code() {
		List<JPageview> list = pageviewMapper.getAll();
		Map<String, JPageview> map = new HashMap<String, JPageview>();
		for (JPageview entity : list) {
			map.put(entity.getCode(), entity);
		}
		CacheResponse response = new CacheResponse();
		response.setCode(0);
		response.setData(map);
		return response;
	}

}
