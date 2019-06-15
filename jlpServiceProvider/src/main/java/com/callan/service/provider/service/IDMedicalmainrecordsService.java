package com.callan.service.provider.service;

import java.util.List;

import com.callan.service.provider.pojo.db.DMedicalmainrecords;

public interface IDMedicalmainrecordsService {

	DMedicalmainrecords getOne(long id);
	
	List<DMedicalmainrecords> getByPatientId(String patientId);
}
