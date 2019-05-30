package com.callan.service.provider.service;

import com.callan.service.provider.pojo.db.JQueryrecord;

public interface IJQueryrecordService {

	JQueryrecord getOne(Long id);

	void save(JQueryrecord queryrecord);
	
}
