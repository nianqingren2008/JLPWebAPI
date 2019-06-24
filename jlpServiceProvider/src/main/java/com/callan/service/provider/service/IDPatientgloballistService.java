package com.callan.service.provider.service;

import com.callan.service.provider.pojo.base.CacheResponse;
import com.callan.service.provider.pojo.db.DPatientglobal;
import com.callan.service.provider.pojo.db.DPatientgloballist;

public interface IDPatientgloballistService {

	DPatientgloballist getOne(long id);

	CacheResponse getAll4Id();
	
	CacheResponse getAll();

	void save(DPatientgloballist patientgloballist);
}
