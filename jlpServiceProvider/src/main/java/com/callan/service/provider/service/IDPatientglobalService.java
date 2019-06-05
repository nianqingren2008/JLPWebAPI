package com.callan.service.provider.service;

import com.callan.service.provider.pojo.base.CacheResponse;
import com.callan.service.provider.pojo.db.DPatientglobal;

public interface IDPatientglobalService {

	DPatientglobal getOne(long id);

	CacheResponse getAll4Id();
	
	CacheResponse getAll();
}
