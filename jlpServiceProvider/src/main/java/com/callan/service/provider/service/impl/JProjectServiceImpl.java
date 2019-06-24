package com.callan.service.provider.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callan.service.provider.dao.mapper.JProjectMapper;
import com.callan.service.provider.pojo.db.JProject;
import com.callan.service.provider.service.IJProjectService;

@Service
public class JProjectServiceImpl implements IJProjectService {
	@Autowired
	private JProjectMapper  projectMapper;


	@Override
	public JProject getOne(Long id) {
		return projectMapper.getOne(id);
	}


	@Override
	public List<JProject> getByUserId(long userId) {
		return projectMapper.getByUserId(userId);
	}


	@Override
	public void save(JProject project) {
		projectMapper.save(project);
	}


	@Override
	public void update(JProject project) {
		projectMapper.update(project);
	}
	

}
