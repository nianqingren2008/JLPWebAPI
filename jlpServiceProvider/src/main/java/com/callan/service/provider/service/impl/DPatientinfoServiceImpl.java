package com.callan.service.provider.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callan.service.provider.dao.mapper.DPatientinfoMapper;
import com.callan.service.provider.pojo.base.CacheResponse;
import com.callan.service.provider.pojo.cache.LocalCacheable;
import com.callan.service.provider.pojo.db.DPatientglobal;
import com.callan.service.provider.pojo.db.DPatientinfo;
import com.callan.service.provider.service.IDPatientinfoService;

@Service
public class DPatientinfoServiceImpl implements IDPatientinfoService{
	
	@Autowired DPatientinfoMapper patientinfoMapper;
	
	@Override
	public DPatientinfo getOne(long id) {
		return patientinfoMapper.selectByPrimaryKey(id);
	}

	@LocalCacheable
	@Override
	public CacheResponse getAll4Id() {
		List<DPatientinfo> list = patientinfoMapper.getAll();
		Map<Long,DPatientinfo> map = new HashMap<Long,DPatientinfo>();
		for(DPatientinfo entity : list) {
			map.put(entity.getId(), entity);
		}
		CacheResponse response = new CacheResponse();
		response.setCode(0);
		response.setData(map);
		return response;
	}

	@LocalCacheable
	@Override
	public CacheResponse getAll() {
		List<DPatientinfo> list = patientinfoMapper.getAll();
		CacheResponse response = new CacheResponse();
		response.setCode(0);
		response.setData(list);
		return response;
	}

	@Override
	public DPatientinfo getEntity(DPatientinfo patientInfoExpre) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(long patientInfoId, Long patientGlobalId) {
//		patientinfoMapper.insert()
	}

	@Override
	public void save(DPatientinfo patientinfo) {
		patientinfoMapper.insert(patientinfo);
	}
	
}
