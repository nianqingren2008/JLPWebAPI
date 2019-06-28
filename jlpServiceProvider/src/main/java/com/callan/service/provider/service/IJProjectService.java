package com.callan.service.provider.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.callan.service.provider.pojo.advanceQueryBase.AdvancedQueryRecordModel;
import com.callan.service.provider.pojo.db.JProject;

public interface IJProjectService {

	JProject getOne(Long id);

	List<JProject> getByUserId(long userId);

	void save(JProject jProject);

	void update(JProject jProject);

	AdvancedQueryRecordModel getQueryRecord(Long Id, Long userId);

	String staticAsync( Long Id,Long TableClassId, HttpServletRequest request,
			HttpServletResponse response);
	
}
