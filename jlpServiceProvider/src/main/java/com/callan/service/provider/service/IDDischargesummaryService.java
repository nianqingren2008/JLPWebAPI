package com.callan.service.provider.service;

import java.util.List;

import com.callan.service.provider.pojo.db.DDischargesummary;

public interface IDDischargesummaryService {

	DDischargesummary getOne(long id);
	
	List<DDischargesummary> getByPatientId(String patientId, String visitId);
}
