package com.callan.service.provider.service;

import java.util.List;

import com.callan.service.provider.pojo.db.JUsershowviewfield;

public interface IJUsershowviewfieldService {

	JUsershowviewfield getOne(Long id);

	List<JUsershowviewfield> getByPagenameAndUserId(String pagename, Long id);

	void update(JUsershowviewfield usershowviewfield);

	void save(JUsershowviewfield jUsershowviewfield);

}
