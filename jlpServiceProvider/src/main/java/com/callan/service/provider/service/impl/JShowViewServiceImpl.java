package com.callan.service.provider.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callan.service.provider.dao.mapper.JShowViewMapper;
import com.callan.service.provider.pojo.db.JShowView;
import com.callan.service.provider.service.IJShowViewService;

@Service
public class JShowViewServiceImpl implements IJShowViewService {
	@Autowired
	private JShowViewMapper  jshowviewMapper;
	
	@Override
	public JShowView getOne(Long id) {
		return jshowviewMapper.getOne(id);
	}
}
