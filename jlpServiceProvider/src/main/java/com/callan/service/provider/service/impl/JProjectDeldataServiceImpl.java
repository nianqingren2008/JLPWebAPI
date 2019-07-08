package com.callan.service.provider.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callan.service.provider.dao.mapper.JProjectdatastatusMapper;
import com.callan.service.provider.dao.mapper.JProjectdeldataMapper;
import com.callan.service.provider.pojo.db.JProjectdatastatus;
import com.callan.service.provider.pojo.db.JProjectdeldata;
import com.callan.service.provider.service.IJProjectDataStatusService;
import com.callan.service.provider.service.IJProjectDeldataService;

@Service
public class JProjectDeldataServiceImpl implements IJProjectDeldataService {
	
	@Autowired JProjectdeldataMapper projectdeldataMapper;

	@Override
	public List<JProjectdeldata> getByProjectIdAndTableId(Long projectId, Long tableId, Long dataId) {
		return projectdeldataMapper.getByProjectIdAndTableId(projectId,tableId,dataId);
	}

	@Override
	public void save(JProjectdeldata record) {
		projectdeldataMapper.insert(record);
	}

	@Override
	public void update(JProjectdeldata record) {
		projectdeldataMapper.updateByPrimaryKey(record);
	}

	
}
