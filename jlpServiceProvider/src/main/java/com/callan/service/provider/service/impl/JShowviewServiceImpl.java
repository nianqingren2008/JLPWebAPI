package com.callan.service.provider.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callan.service.provider.dao.mapper.JShowviewMapper;
import com.callan.service.provider.pojo.db.JShowview;
import com.callan.service.provider.service.IJShowviewService;

@Service
public class JShowviewServiceImpl implements IJShowviewService {
	@Autowired
	private JShowviewMapper  jshowviewMapper;
	
	@Override
	public JShowview getOne(Long id) {
		return jshowviewMapper.getOne(id);
	}
}
