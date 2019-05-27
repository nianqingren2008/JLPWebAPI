package com.callan.service.provider.service;

import com.callan.service.provider.pojo.base.CacheResponse;
import com.callan.service.provider.pojo.db.JRight;

public interface IJRoleService {

	JRight getOne(Long id);
	
	CacheResponse getAll4Id();

	JRight getOne(Long id, boolean activityFlag);
}
