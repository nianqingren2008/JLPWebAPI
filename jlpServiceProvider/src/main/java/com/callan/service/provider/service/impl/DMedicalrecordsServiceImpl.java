package com.callan.service.provider.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callan.service.provider.config.JLPConts;
import com.callan.service.provider.dao.mapper.DMedicalrecordsMapper;
import com.callan.service.provider.dao.mapper.JPageviewMapper;
import com.callan.service.provider.dao.mapper.JRightMapper;
import com.callan.service.provider.pojo.base.CacheResponse;
import com.callan.service.provider.pojo.cache.LocalCacheable;
import com.callan.service.provider.pojo.db.DMedicalmainrecords;
import com.callan.service.provider.pojo.db.DMedicalrecords;
import com.callan.service.provider.pojo.db.JPageview;
import com.callan.service.provider.pojo.db.JRight;
import com.callan.service.provider.service.IDMedicalrecordsService;
import com.callan.service.provider.service.IJPageViewService;
import com.callan.service.provider.service.IJRightService;
import com.callan.service.provider.service.IJShowDetailViewService;

@Service
public class DMedicalrecordsServiceImpl implements IDMedicalrecordsService {
	@Autowired
	private DMedicalrecordsMapper dMedicalrecordsMapper;
	
	@Override
	public DMedicalrecords getOne(long id) {
		return dMedicalrecordsMapper.selectByPrimaryKey(id);
	}


	@Override
	public List<DMedicalrecords> getByPatientId(String patientId,String visitId) {
		return dMedicalrecordsMapper.getByPatientId(patientId,visitId);
	}
	

}
