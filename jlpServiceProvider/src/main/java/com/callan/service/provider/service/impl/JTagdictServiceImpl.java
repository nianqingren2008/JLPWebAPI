package com.callan.service.provider.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callan.service.provider.dao.mapper.JTagdictsMapper;
import com.callan.service.provider.pojo.base.CacheResponse;
import com.callan.service.provider.pojo.cache.LocalCacheable;
import com.callan.service.provider.pojo.db.JTagdicts;
import com.callan.service.provider.service.IJTagdictService;

@Service
public class JTagdictServiceImpl implements IJTagdictService {
	
	
	@Autowired
	private JTagdictsMapper tagdictsMapper;

	@Override
	public List<JTagdicts> getAll() {
		List<JTagdicts> list = tagdictsMapper.getAll();
		return list;
	}

	@Override
	public JTagdicts getOne(Long id) {
		IJTagdictService base = (IJTagdictService) AopContext.currentProxy();
		Map<Long, JTagdicts> data = (Map<Long, JTagdicts>) base.getAll4Id().getData();
		JTagdicts entity = data.get(id);
		return entity;
	}

	@LocalCacheable
	@Override
	public CacheResponse getAll4Id() {
		Map<Long, JTagdicts> map = new HashMap<>();
		List<JTagdicts> list = tagdictsMapper.getAll();
		for (JTagdicts entity : list) {
			map.put(entity.getId(), entity);
		}
		CacheResponse response = new CacheResponse();
		response.setCode(0);
		response.setData(map);
		return response;
	}

	@Override
	public List<JTagdicts> getByProjectId(Long projectId) {
		IJTagdictService base = (IJTagdictService) AopContext.currentProxy();
		Map<Long, List<JTagdicts>> data = (Map<Long, List<JTagdicts>>) base.getAll4ProjectId().getData();
		List<JTagdicts> entity = data.get(projectId);
		return entity;
	}

	@LocalCacheable
	@Override
	public CacheResponse getAll4ProjectId() {
		Map<Long, List<JTagdicts>> map = new HashMap<>();
		List<JTagdicts> list = tagdictsMapper.getAll();
		for (JTagdicts entity : list) {
			List<JTagdicts> projectIdList = map.get(entity.getProjectid());
			if(projectIdList == null) {
				projectIdList = new ArrayList<JTagdicts>();
			}
			projectIdList.add(entity);
			map.put(entity.getProjectid(), projectIdList);
		}
		CacheResponse response = new CacheResponse();
		response.setCode(0);
		response.setData(map);
		return response;
	}

	@Override
	public void save(JTagdicts tagdcit) {
		tagdictsMapper.insert(tagdcit)	;	
	}

	@Override
	public void update(JTagdicts tagdict) {
		tagdictsMapper.updateByPrimaryKey(tagdict);
	}
	
}
