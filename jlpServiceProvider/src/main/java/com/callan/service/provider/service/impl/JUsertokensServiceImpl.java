package com.callan.service.provider.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callan.service.provider.dao.mapper.JRoleMapper;
import com.callan.service.provider.dao.mapper.JUsertokensMapper;
import com.callan.service.provider.pojo.db.JRole;
import com.callan.service.provider.pojo.db.JUsertokens;
import com.callan.service.provider.service.IJRoleService;
import com.callan.service.provider.service.IJUsertokensService;

@Service
public class JUsertokensServiceImpl implements IJUsertokensService {
	@Autowired
	private JUsertokensMapper  usertokensMapper;

	@Override
	public JUsertokens getOne(Long id) {
		return usertokensMapper.selectByPrimaryKey(id);
	}

	@Override
	public List<JUsertokens> getAll() {
		return usertokensMapper.getAll();
	}

	@Override
	public void save(JUsertokens record) {
		usertokensMapper.insert(record);
	}
	

	 
}
