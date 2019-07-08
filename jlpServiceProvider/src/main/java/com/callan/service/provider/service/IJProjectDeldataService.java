package com.callan.service.provider.service;

import java.util.List;

import com.callan.service.provider.pojo.db.JProjectdeldata;

public interface IJProjectDeldataService {

	List<JProjectdeldata> getByProjectIdAndTableId(Long projectId, Long tableClassId, Long dataId);

	void save(JProjectdeldata projectdeldata);

	void update(JProjectdeldata projectdeldata1);
}
