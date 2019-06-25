package com.callan.service.provider.service;

import java.util.List;

import com.callan.service.provider.pojo.advanceQueryBase.AdvancedQueryRecordModel;
import com.callan.service.provider.pojo.db.JProject;

public interface IJProjectService {

	JProject getOne(Long id);

	List<JProject> getByUserId(long userId);

	void save(JProject jProject);

	void update(JProject jProject);

	AdvancedQueryRecordModel getQueryRecord(Long Id, Long userId);
	
}
