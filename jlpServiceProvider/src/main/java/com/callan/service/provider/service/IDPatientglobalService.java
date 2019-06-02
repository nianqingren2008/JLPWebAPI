package com.callan.service.provider.service;

import com.callan.service.provider.pojo.db.DPatientglobal;

public interface IDPatientglobalService {

	DPatientglobal getOne(long id);

	DPatientglobal getAll4Id();
	
	DPatientglobal getAll();
}
