package com.callan.service.provider.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callan.service.provider.config.JLPConts;
import com.callan.service.provider.dao.mapper.JShowDetailViewMapper;
import com.callan.service.provider.pojo.base.CacheResponse;
import com.callan.service.provider.pojo.cache.LocalCacheable;
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
			if (JLPConts.ActiveFlag.equals(entity.getActiveflag())) {
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
	
	
//	@LocalCacheable
//	@Override
//	public CacheResponse getAll4ViewId() {
//		Map<Long,List<JShowDetailView>> map = new HashMap<>();
//		List<JShowDetailView> list = jShowDetailViewMapper.getAll();
//		
//		for(JShowDetailView jShowDetailView : list) {
//			if(map.get(jShowDetailView.getViewid()) == null) {
//				List<JShowDetailView> viewIdList = new ArrayList<JShowDetailView>();
//				if(activeFlag) {
//					if(JLPConts.ActiveFlag.equals(jShowDetailView.getActiveflag())) {
//						viewIdList.add(jShowDetailView);
//					}
//				}else {
//					viewIdList.add(jShowDetailView);
//				}
//				map.put(jShowDetailView.getViewid(), viewIdList);
//			}else {
//				if(activeFlag) {
//					if(JLPConts.ActiveFlag.equals(jShowDetailView.getActiveflag())) {
//						map.get(jShowDetailView.getViewid()).add(jShowDetailView);
//					}
//				}else {
//					map.get(jShowDetailView.getViewid()).add(jShowDetailView);
//				}
//			}
//			
//		}
//		CacheResponse response = new CacheResponse();
//		response.setCode(0);
//		response.setData(map);
//		return response;
//	}
	
	@LocalCacheable
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
	
	@LocalCacheable
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

	@Override
	public JShowDetailView getByFieldId(long fieldId) {
		List<JShowDetailView> list = jShowDetailViewMapper.getAll();
		
		for(JShowDetailView jShowDetailView : list) {
			if(jShowDetailView.getFieldid() == fieldId ) {
				return jShowDetailView;
			}
		}
		return null;
	}
}
