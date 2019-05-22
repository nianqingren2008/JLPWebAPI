package com.callan.service.provider.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callan.service.provider.dao.mapper.JShowDetailViewMapper;
import com.callan.service.provider.pojo.base.CacheResponse;
import com.callan.service.provider.pojo.cache.NativeCacheable;
import com.callan.service.provider.pojo.db.JShowDetailView;
import com.callan.service.provider.service.IJShowDetailViewService;

@Service
public class JShowDetailViewServiceImpl implements IJShowDetailViewService {
	@Autowired
	private JShowDetailViewMapper  jShowDetailViewMapper;
	
	@Override
	public JShowDetailView getOne(Long id) {
		IJShowDetailViewService base = (IJShowDetailViewService) AopContext.currentProxy();
		Map<Long,JShowDetailView> map = (Map<Long, JShowDetailView>) base.getAll4Id().getData();
		return map.get(id);
	}
	
	@Override
	public JShowDetailView getOne(Long id,boolean activeFlag) {
		IJShowDetailViewService base = (IJShowDetailViewService) AopContext.currentProxy();
		Map<Long,JShowDetailView> map = (Map<Long, JShowDetailView>) base.getAll4Id().getData();
		JShowDetailView entity = map.get(id);
		if (activeFlag && entity != null) {
			if ("1".equals(entity.getActiveflag())) {
				return entity;
			} else {
				return null;
			}
		} else {
			return entity;
		}
		
	}

	@Override
	public List<JShowDetailView> getByViewId(Long viewId) {
		IJShowDetailViewService base = (IJShowDetailViewService) AopContext.currentProxy();
		Map<Long,List<JShowDetailView>> map = (Map<Long,List<JShowDetailView>>) base.getAll4ViewId().getData();
		return map.get(viewId);
	}
	
	@Override
	public List<JShowDetailView> getByViewId(Long viewId,boolean activeFlag) {
		IJShowDetailViewService base = (IJShowDetailViewService) AopContext.currentProxy();
		Map<Long,List<JShowDetailView>> map = 
				(Map<Long,List<JShowDetailView>>) base.getAll4ViewId(activeFlag).getData();
		return map.get(viewId);
	}
	
	@NativeCacheable
	@Override
	public CacheResponse getAll4ViewId(boolean activeFlag) {
		Map<Long,List<JShowDetailView>> map = new HashMap<>();
		List<JShowDetailView> list = jShowDetailViewMapper.getAll();
		
		for(JShowDetailView jShowDetailView : list) {
			if(map.get(jShowDetailView.getViewid()) == null) {
				List<JShowDetailView> viewIdList = new ArrayList<JShowDetailView>();
				if("1".equals(jShowDetailView.getActiveflag())) {
					viewIdList.add(jShowDetailView);
				}
				map.put(jShowDetailView.getViewid(), viewIdList);
			}else {
				if("1".equals(jShowDetailView.getActiveflag())) {
					map.get(jShowDetailView.getViewid()).add(jShowDetailView);
				}
			}
			
		}
		CacheResponse response = new CacheResponse();
		response.setCode(0);
		response.setData(map);
		return response;
	}
	
	@NativeCacheable
	@Override
	public CacheResponse getAll4ViewId() {
		Map<Long,List<JShowDetailView>> map = new HashMap<>();
		List<JShowDetailView> list = jShowDetailViewMapper.getAll();
		
		for(JShowDetailView jShowDetailView : list) {
			if(map.get(jShowDetailView.getViewid()) == null) {
				List<JShowDetailView> viewIdList = new ArrayList<JShowDetailView>();
				viewIdList.add(jShowDetailView);
				map.put(jShowDetailView.getViewid(), viewIdList);
			}else {
				map.get(jShowDetailView.getViewid()).add(jShowDetailView);
			}
			
		}
		CacheResponse response = new CacheResponse();
		response.setCode(0);
		response.setData(map);
		return response;
	}
	
	@NativeCacheable
	@Override
	public CacheResponse getAll4Id() {
		Map<Long,JShowDetailView> map = new HashMap<>();
		List<JShowDetailView> list = jShowDetailViewMapper.getAll();
		for(JShowDetailView jShowDetailView : list) {
				map.put(jShowDetailView.getId(), jShowDetailView);
		}
		CacheResponse response = new CacheResponse();
		response.setCode(0);
		response.setData(map);
		return response;
	}
}
