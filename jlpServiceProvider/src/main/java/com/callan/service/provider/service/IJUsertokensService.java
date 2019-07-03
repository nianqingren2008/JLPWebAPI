package com.callan.service.provider.service;

import java.util.List;

import com.callan.service.provider.pojo.db.JUsertokens;

public interface IJUsertokensService {

	JUsertokens getOne(Long id);

	List<JUsertokens> getAll();
		
	void save(JUsertokens record);
}
