package com.callan.service.provider.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callan.service.provider.dao.mapper.JProjectdefaultstatusMapper;
import com.callan.service.provider.pojo.db.JProjectdefaultstatus;
import com.callan.service.provider.service.IJProjectdefaultstatusService;

@Service
public class JProjectdefaultstatusServiceImpl implements IJProjectdefaultstatusService {
	
	@Autowired JProjectdefaultstatusMapper projectdefaultstatusMapper;

	@Override
	public List<JProjectdefaultstatus> getByDatastatusid(Long datastatusid) {
		return projectdefaultstatusMapper.getByDatastatusid(datastatusid);
	}

	@Override
	public JProjectdefaultstatus getByProjectId(Long projectId) {
		List<JProjectdefaultstatus> list = projectdefaultstatusMapper.getByProjectId(projectId);
		if(list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public void update(JProjectdefaultstatus record) {
		projectdefaultstatusMapper.updateByPrimaryKey(record);
	}

	@Override
	public void save(JProjectdefaultstatus record) {
		projectdefaultstatusMapper.insert(record);
	}


	
	
}
