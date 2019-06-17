package com.callan.service.provider.service;

import com.callan.service.provider.pojo.base.CacheResponse;
import com.callan.service.provider.pojo.db.JSensitiveWord;

public interface IJSensitiveWordService {

	JSensitiveWord getOne(Long id);

	CacheResponse getAll();
	
	CacheResponse getAll4Name();

	int deleteByPrimaryKey(Long id);

	int insertSelective(JSensitiveWord jSensitiveWord);

	int updateByPrimaryKeySelective(JSensitiveWord jSensitiveWord);

}
