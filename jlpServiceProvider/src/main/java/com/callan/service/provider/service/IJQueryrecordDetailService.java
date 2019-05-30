package com.callan.service.provider.service;

import com.callan.service.provider.pojo.db.JQueryrecordDetails;

public interface IJQueryrecordDetailService {
	
	JQueryrecordDetails getOne(Long id);

	void save(JQueryrecordDetails queryrecord);
}
