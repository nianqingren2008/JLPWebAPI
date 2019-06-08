package com.callan.service.provider.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callan.service.provider.dao.mapper.JRoleMapper;
import com.callan.service.provider.pojo.db.JRole;
import com.callan.service.provider.service.IJRoleService;

@Service
public class JRoleServiceImpl implements IJRoleService {
	@Autowired
	private JRoleMapper  roleMapper;
	
	@Override
	public JRole getOne(Long id) {
		return roleMapper.getOne(id);
	}

	@Override
	public List<JRole> getAll() {
		return roleMapper.getAll();
	}
	
	 
}
