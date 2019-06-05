package com.callan.service.provider.service;

import java.util.List;

import com.callan.service.provider.pojo.base.CacheResponse;
import com.callan.service.provider.pojo.db.DPatientvisit;

public interface IDPatientvisitService {

	DPatientvisit getOne(long id);

	CacheResponse getAll4Id();
	
	CacheResponse getAll();

	List<DPatientvisit> getByPatientGlobalId(Long id);
}
