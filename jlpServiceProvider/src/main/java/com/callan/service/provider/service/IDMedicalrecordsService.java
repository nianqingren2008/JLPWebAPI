package com.callan.service.provider.service;

import java.util.List;

import com.callan.service.provider.pojo.db.DMedicalrecords;

public interface IDMedicalrecordsService {

	DMedicalrecords getOne(long id);
	
	List<DMedicalrecords> getByPatientId(String patientId, String visitId);
}
