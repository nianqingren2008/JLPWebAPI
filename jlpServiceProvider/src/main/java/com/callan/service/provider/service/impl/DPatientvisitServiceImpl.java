package com.callan.service.provider.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callan.service.provider.dao.mapper.DPatientvisitMapper;
import com.callan.service.provider.pojo.base.CacheResponse;
import com.callan.service.provider.pojo.db.DPatientglobal;
import com.callan.service.provider.pojo.db.DPatientvisit;
import com.callan.service.provider.service.IDPatientvisitService;

@Service
public class DPatientvisitServiceImpl implements IDPatientvisitService{
	
	@Autowired DPatientvisitMapper dpatientvisitMapper;
	
	@Override
	public DPatientvisit getOne(long id) {
		return dpatientvisitMapper.selectByPrimaryKey(id);
	}

	@Override
	public CacheResponse getAll4Id() {
		List<DPatientvisit> list = dpatientvisitMapper.getAll();
		Map<Long,DPatientvisit> map = new HashMap<Long,DPatientvisit>();
		for(DPatientvisit entity : list) {
			map.put(entity.getId(), entity);
		}
		CacheResponse response = new CacheResponse();
		response.setCode(0);
		response.setData(map);
		return response;
	}

	@Override
	public CacheResponse getAll() {
		List<DPatientvisit> list = dpatientvisitMapper.getAll();
		CacheResponse response = new CacheResponse();
		response.setCode(0);
		response.setData(list);
		return response;
	}

	@Override
	public List<DPatientvisit> getByPatientGlobalId(Long id) {
		
		return dpatientvisitMapper.getByPatientGlobalId(id);
	}
	
}
