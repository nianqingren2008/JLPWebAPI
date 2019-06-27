package com.callan.service.provider.service;

import java.util.List;

import com.callan.service.provider.pojo.db.JProjectdatastatusdict;

public interface IJProjectDataStatusdictService {

	void save(JProjectdatastatusdict projectDataStatus);

	List<JProjectdatastatusdict> getByProjectIdAndProjectstatusIDs(Long projectId, Long[] projectstatusIDs);

	List<JProjectdatastatusdict> getByProjectId(Long id);

	JProjectdatastatusdict getOne(Integer projectDataStatusId);

	void update(JProjectdatastatusdict jProjectdatastatusdict);
	
}
