package com.callan.service.provider.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callan.service.provider.dao.mapper.DPatientgloballistMapper;
import com.callan.service.provider.pojo.base.CacheResponse;
import com.callan.service.provider.pojo.cache.LocalCacheable;
import com.callan.service.provider.pojo.db.DPatientgloballist;
import com.callan.service.provider.service.IDPatientgloballistService;

@Service
public class DPatientgloballistServiceImpl implements IDPatientgloballistService{
	
	@Autowired DPatientgloballistMapper patientgloballistMapper;
	
	@Override
	public DPatientgloballist getOne(long id) {
		return patientgloballistMapper.selectByPrimaryKey(id);
	}

	@LocalCacheable
	@Override
	public CacheResponse getAll4Id() {
		List<DPatientgloballist> list = patientgloballistMapper.getAll();
		Map<Long,DPatientgloballist> map = new HashMap<Long,DPatientgloballist>();
		for(DPatientgloballist patientglobal : list) {
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
		List<DPatientgloballist> list = patientgloballistMapper.getAll();
		CacheResponse response = new CacheResponse();
		response.setCode(0);
		response.setData(list);
		return response;
	}

	@Override
	public void save(DPatientgloballist patientgloballist) {
		patientgloballistMapper.insert(patientgloballist);
	}
	
}
