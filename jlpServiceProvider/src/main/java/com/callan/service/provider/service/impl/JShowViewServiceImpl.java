package com.callan.service.provider.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callan.service.provider.config.JLPConts;
import com.callan.service.provider.dao.mapper.JShowViewMapper;
import com.callan.service.provider.pojo.base.CacheResponse;
import com.callan.service.provider.pojo.cache.LocalCacheable;
import com.callan.service.provider.pojo.db.JShowView;
import com.callan.service.provider.service.IJSensitiveWordService;
import com.callan.service.provider.service.IJShowViewService;

@Service
public class JShowViewServiceImpl implements IJShowViewService {
	@Autowired
	private JShowViewMapper jshowviewMapper;

	@Override
	public JShowView getOne(Long id) {
		IJShowViewService base = (IJShowViewService) AopContext.currentProxy();
		Map<Long, JShowView> data = (Map<Long, JShowView>) base.getAll4Id().getData();
		JShowView jShowView = data.get(id);
		return jShowView;
	}
	
	@LocalCacheable
	@Override
	public CacheResponse getAll4Id() {
		List<JShowView> list = jshowviewMapper.getAll();
		Map<Long, JShowView> map = new HashMap<Long, JShowView>();
		for (JShowView jShowView : list) {
			map.put(jShowView.getId(), jShowView);
		}
		CacheResponse response = new CacheResponse();
		response.setCode(0);
		response.setData(map);
		return response;
	}
	
	@Override
	public JShowView getOne(Long id,boolean activityFlag) {
		IJShowViewService base = (IJShowViewService) AopContext.currentProxy();
		Map<Long, JShowView> data = (Map<Long, JShowView>) base.getAll4Id().getData();
		JShowView jShowView = data.get(id);
		if(activityFlag && jShowView != null) {
			if(JLPConts.ActiveFlag.equals(jShowView.getActiveflag())){
				return jShowView;
			}else {
				return null;
			}
		}
		return jShowView;
	}

	@Override
	public JShowView getByCode(String code) {
		IJShowViewService base = (IJShowViewService) AopContext.currentProxy();
		Map<Long, JShowView> data = (Map<Long, JShowView>) base.getAll4Code().getData();
		JShowView jShowView = data.get(code);
		return jShowView;
	}
	
	@LocalCacheable
	@Override
	public CacheResponse getAll4Code() {
		List<JShowView> list = jshowviewMapper.getAll();
		Map<String, JShowView> map = new HashMap<String, JShowView>();
		for (JShowView jShowView : list) {
			map.put(jShowView.getCode(), jShowView);
		}
		CacheResponse response = new CacheResponse();
		response.setCode(0);
		response.setData(map);
		return response;
	}
	
}
