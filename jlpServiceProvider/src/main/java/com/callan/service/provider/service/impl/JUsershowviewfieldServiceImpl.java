package com.callan.service.provider.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callan.service.provider.dao.mapper.JUsershowviewfieldMapper;
import com.callan.service.provider.pojo.db.JUsershowviewfield;
import com.callan.service.provider.service.IJUsershowviewfieldService;

@Service
public class JUsershowviewfieldServiceImpl implements IJUsershowviewfieldService {
	@Autowired
	private JUsershowviewfieldMapper  usershowviewfieldMapper;

	@Override
	public JUsershowviewfield getOne(Long id) {
		return usershowviewfieldMapper.selectByPrimaryKey(id);
	}

	@Override
	public List<JUsershowviewfield> getByPagenameAndUserId(String pagename, Long userId) {
		return usershowviewfieldMapper.getByPagenameAndUserId(pagename,userId);
	}

	@Override
	public void update(JUsershowviewfield record) {
		usershowviewfieldMapper.updateByPrimaryKey(record);
	}

	@Override
	public void save(JUsershowviewfield record) {
		usershowviewfieldMapper.insert(record);
	}

	
	

	 
}
