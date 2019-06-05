package com.callan.service.provider.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callan.service.provider.dao.mapper.DPatientglobalMapper;
import com.callan.service.provider.pojo.base.CacheResponse;
import com.callan.service.provider.pojo.cache.LocalCacheable;
import com.callan.service.provider.pojo.db.DPatientglobal;
import com.callan.service.provider.service.IDPatientglobalService;

@Service
public class DPatientglobalServiceImpl implements IDPatientglobalService{
	
	@Autowired DPatientglobalMapper patientglobalMapper;
	
	@Override
	public DPatientglobal getOne(long id) {
		return patientglobalMapper.selectByPrimaryKey(id);
	}

	@LocalCacheable
	@Override
	public CacheResponse getAll4Id() {
		List<DPatientglobal> list = patientglobalMapper.getAll();
		Map<Long,DPatientglobal> map = new HashMap<Long,DPatientglobal>();
		for(DPatientglobal patientglobal : list) {
			map.put(patientglobal.getId(), patientglobal);
		}
		CacheResponse response = new CacheResponse();
		response.setCode(0);
		response.setData(map);
		return response;
	}

	@LocalCacheable
	@Override
	public CacheResponse getAll() {
		List<DPatientglobal> list = patientglobalMapper.getAll();
		CacheResponse response = new CacheResponse();
		response.setCode(0);
		response.setData(list);
		return response;
	}
	
}
