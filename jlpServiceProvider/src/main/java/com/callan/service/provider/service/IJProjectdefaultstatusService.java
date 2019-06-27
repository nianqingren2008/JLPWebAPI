package com.callan.service.provider.service;

import java.util.List;

import com.callan.service.provider.pojo.db.JProjectdefaultstatus;

public interface IJProjectdefaultstatusService {

	List<JProjectdefaultstatus> getByDatastatusid(Long datastatusid);

	JProjectdefaultstatus getByProjectId(Long projectId);

	void update(JProjectdefaultstatus projectDataDefault);

	void save(JProjectdefaultstatus projectdefaultstatus);
	
}
