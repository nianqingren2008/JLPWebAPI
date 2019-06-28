package com.callan.service.provider.service;

import java.util.List;

import com.callan.service.provider.pojo.db.JProjectdatastatus;

public interface IJProjectDataStatusService {

	List<JProjectdatastatus> getByProjectIdAndPatientglobalid(Long projectid, Long patientGlobalId);

	void save(JProjectdatastatus projectdatastatus);

	void update(JProjectdatastatus projectDataStatus);
}
