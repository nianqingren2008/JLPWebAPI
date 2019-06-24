package com.callan.service.provider.service;

import com.callan.service.provider.pojo.base.CacheResponse;
import com.callan.service.provider.pojo.db.DPatientinfo;

public interface IDPatientinfoService {

	DPatientinfo getOne(long id);

	CacheResponse getAll4Id();
	
	CacheResponse getAll();

	DPatientinfo getEntity(DPatientinfo patientInfoExpre);

	void update(long patientInfoId, Long patientGlobalId);

	void save(DPatientinfo patientinfo);
}
