package com.callan.service.provider.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callan.service.provider.dao.mapper.DPatientglobalMapper;
import com.callan.service.provider.pojo.db.DPatientglobal;
import com.callan.service.provider.service.IDPatientglobalService;

@Service
public class DPatientglobalServiceImpl implements IDPatientglobalService{
	
	@Autowired DPatientglobalMapper patientglobalMapper;
	
	@Override
	public DPatientglobal getOne(long id) {
		return patientglobalMapper.selectByPrimaryKey(id);
	}

	@Override
	public DPatientglobal getAll4Id() {
		return null;
	}

	@Override
	public DPatientglobal getAll() {
		return null;
	}
	
}
