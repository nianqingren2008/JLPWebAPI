package com.callan.service.provider.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callan.service.provider.dao.mapper.JProjectdatastatusdictMapper;
import com.callan.service.provider.pojo.db.JProjectdatastatusdict;
import com.callan.service.provider.service.IJProjectDataStatusService;

@Service
public class JProjectDataStatusServiceImpl implements IJProjectDataStatusService {
	
	@Autowired JProjectdatastatusdictMapper projectdatastatusdictMapper;

	@Override
	public void save(JProjectdatastatusdict projectDataStatus) {
		projectdatastatusdictMapper.insert(projectDataStatus);
	}
	
	
}
