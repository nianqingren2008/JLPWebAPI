package com.callan.service.provider.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callan.service.provider.dao.mapper.JProjectdatastatusdictMapper;
import com.callan.service.provider.pojo.db.JProjectdatastatusdict;
import com.callan.service.provider.service.IJProjectDataStatusService;
import com.callan.service.provider.service.IJProjectDataStatusdictService;

@Service
public class JProjectDataStatusdictServiceImpl implements IJProjectDataStatusdictService {
	
	@Autowired JProjectdatastatusdictMapper projectdatastatusdictMapper;

	@Override
	public void save(JProjectdatastatusdict projectDataStatus) {
		projectdatastatusdictMapper.insert(projectDataStatus);
	}

	@Override
	public List<JProjectdatastatusdict> getByProjectIdAndProjectstatusIDs(Long projectId, Long[] projectstatusIDs) {
		List<JProjectdatastatusdict> list = new ArrayList<JProjectdatastatusdict>();
		for(Long id : projectstatusIDs) {
			JProjectdatastatusdict dict = projectdatastatusdictMapper.selectByPrimaryKey(id);
			if(projectId.longValue() == dict.getProjectid().longValue()) {
				list.add(dict);
			}
		}
		return list;
	}

	@Override
	public List<JProjectdatastatusdict> getByProjectId(Long id) {
		return projectdatastatusdictMapper.getByProjectId(id);
	}

	@Override
	public JProjectdatastatusdict getOne(Integer id) {
		return projectdatastatusdictMapper.selectByPrimaryKey(id.longValue());
	}

	@Override
	public void update(JProjectdatastatusdict record) {
		projectdatastatusdictMapper.updateByPrimaryKey(record);
	}
	
	
}
