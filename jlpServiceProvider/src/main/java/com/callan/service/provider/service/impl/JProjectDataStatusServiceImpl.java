package com.callan.service.provider.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callan.service.provider.dao.mapper.JProjectdatastatusMapper;
import com.callan.service.provider.pojo.db.JProjectdatastatus;
import com.callan.service.provider.service.IJProjectDataStatusService;

@Service
public class JProjectDataStatusServiceImpl implements IJProjectDataStatusService {
	
	@Autowired JProjectdatastatusMapper projectdatastatusMapper;

	@Override
	public List<JProjectdatastatus> getByProjectIdAndPatientglobalid(Long projectid, Long patientGlobalId) {
		return projectdatastatusMapper.getByProjectIdAndPatientglobalid(projectid,patientGlobalId);
	}

	@Override
	public void save(JProjectdatastatus record) {
		projectdatastatusMapper.insert(record);
	}

	@Override
	public void update(JProjectdatastatus record) {
		projectdatastatusMapper.updateByPrimaryKey(record);
	}

	
	
}
