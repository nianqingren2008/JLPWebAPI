package com.callan.service.provider.service;

import java.util.List;

import com.callan.service.provider.pojo.base.CacheResponse;
import com.callan.service.provider.pojo.db.JTagdicts;

public interface IJTagdictService {

	/**
	 * 获取  标签所属分类列表
	 * @return
	 */
	List<JTagdicts> getAll();

	JTagdicts getOne(Long tableClassId);

	CacheResponse getAll4Id();

	List<JTagdicts> getByProjectId(Long projectId);

	CacheResponse getAll4ProjectId();

}
