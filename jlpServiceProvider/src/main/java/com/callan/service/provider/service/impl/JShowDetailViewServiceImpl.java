package com.callan.service.provider.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callan.service.provider.dao.mapper.JShowDetailViewMapper;
import com.callan.service.provider.pojo.db.JShowDetailView;
import com.callan.service.provider.pojo.db.JTableDict;
import com.callan.service.provider.service.IJShowDetailViewService;

@Service
public class JShowDetailViewServiceImpl implements IJShowDetailViewService {
	@Autowired
	private JShowDetailViewMapper  jShowDetailViewMapper;
	
	@Override
	public JShowDetailView getOne(Long id) {
		return getAll4Id().get(id);
	}
	
	@Override
	public JShowDetailView getOne(Long id,boolean activeFlag) {
		JShowDetailView entity = getAll4Id().get(id);
		if (activeFlag) {
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
		return getAll4ViewId().get(viewId);
	}
	
	@Override
	public List<JShowDetailView> getByViewId(Long viewId,boolean activeFlag) {
		List<JShowDetailView> list = getAll4ViewId(true).get(viewId);
		return list;
	}
	
	@Override
	public Map<Long,List<JShowDetailView>> getAll4ViewId(boolean activeFlag) {
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
		return map;
	}
	
	@Override
	public Map<Long,List<JShowDetailView>> getAll4ViewId() {
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
		return map;
	}
	
	@Override
	public Map<Long,JShowDetailView> getAll4Id() {
		Map<Long,JShowDetailView> map = new HashMap<>();
		List<JShowDetailView> list = jShowDetailViewMapper.getAll();
		for(JShowDetailView jShowDetailView : list) {
				map.put(jShowDetailView.getId(), jShowDetailView);
		}
		return map;
	}
}
