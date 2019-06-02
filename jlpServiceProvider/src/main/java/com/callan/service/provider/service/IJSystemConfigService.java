package com.callan.service.provider.service;

import java.util.List;

import com.callan.service.provider.pojo.base.CacheResponse;
import com.callan.service.provider.pojo.db.JSystemconfig;

public interface IJSystemConfigService {

	JSystemconfig getOne(long id);
	
	CacheResponse getByClassTypeAndKeyName(String classType,String keyName);

	List<JSystemconfig> getAll();

	CacheResponse getAll4Id();
}
