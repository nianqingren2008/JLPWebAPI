package com.callan.service.provider.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callan.service.provider.dao.mapper.DMedicalmainrecordsMapper;
import com.callan.service.provider.pojo.db.DMedicalmainrecords;
import com.callan.service.provider.service.IDMedicalmainrecordsService;

@Service
public class DMedicalmainrecordsServiceImpl implements IDMedicalmainrecordsService {
	@Autowired
	private DMedicalmainrecordsMapper dMedicalmainrecordsMapper;
	
	@Override
	public DMedicalmainrecords getOne(long id) {
		return dMedicalmainrecordsMapper.selectByPrimaryKey(id);
	}


	@Override
	public List<DMedicalmainrecords> getByPatientId(String patientId) {
		return dMedicalmainrecordsMapper.getByPatientId(patientId);
	}
	

}
