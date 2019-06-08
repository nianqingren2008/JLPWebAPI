package com.callan.service.provider.service;

import java.util.List;

import com.callan.service.provider.pojo.db.JRole;

public interface IJRoleService {

	JRole getOne(Long id);

	List<JRole> getAll();
	
}
