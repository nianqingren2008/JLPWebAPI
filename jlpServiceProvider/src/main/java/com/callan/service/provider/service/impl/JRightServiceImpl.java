package com.callan.service.provider.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callan.service.provider.dao.mapper.JRightMapper;
import com.callan.service.provider.pojo.db.JRight;
import com.callan.service.provider.service.IJRightService;

@Service
public class JRightServiceImpl implements IJRightService {
	@Autowired
	private JRightMapper  jRightMapper;
	
	@Override
	public JRight getOne(Long id) {
		return jRightMapper.getOne(id);
	}
}
