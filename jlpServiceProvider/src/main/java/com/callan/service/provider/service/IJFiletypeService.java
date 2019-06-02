package com.callan.service.provider.service;

import java.util.List;

import com.callan.service.provider.pojo.base.CacheResponse;
import com.callan.service.provider.pojo.db.JFiletype;

public interface IJFiletypeService {

	CacheResponse getAll();
	
	JFiletype getOne(long id);

	CacheResponse getAll4Id();
}
