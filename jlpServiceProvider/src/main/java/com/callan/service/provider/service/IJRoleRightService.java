package com.callan.service.provider.service;

import java.util.List;

import com.callan.service.provider.pojo.base.CacheResponse;
import com.callan.service.provider.pojo.db.JRoleRight;

public interface IJRoleRightService {

	List<JRoleRight> getByRoleId(Long roleId) ;
	
	CacheResponse getAll4RoleId();

	void update(JRoleRight roleRight);

	void save(JRoleRight roleright);
}
