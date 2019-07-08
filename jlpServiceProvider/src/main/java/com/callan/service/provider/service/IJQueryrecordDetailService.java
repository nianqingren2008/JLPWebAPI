package com.callan.service.provider.service;

import java.util.List;

import com.callan.service.provider.pojo.db.JQueryrecordDetails;

public interface IJQueryrecordDetailService {
	
	JQueryrecordDetails getOne(long id);

	void save(JQueryrecordDetails queryrecord);

	List<JQueryrecordDetails> getByQueryId(long id);

	void update(JQueryrecordDetails queryrecordDetails);
}
