package com.callan.service.provider.service;

import java.util.List;

import com.callan.service.provider.pojo.base.CacheResponse;
import com.callan.service.provider.pojo.db.JTagvaluedicts;

public interface IJTagvaluedictService {

	/**
	 * 获取  标签所属分类列表
	 * @return
	 */
	List<JTagvaluedicts> getAll();

	JTagvaluedicts getOne(Long tableClassId);

	CacheResponse getAll4Id();


}
