package com.callan.service.provider.service;


import java.util.List;

import com.callan.service.provider.pojo.base.CacheResponse;
import com.callan.service.provider.pojo.db.JPixconfig;

public interface IJPixconfigService {

	JPixconfig getOne(Long id);

	CacheResponse getAll4Id();
	
	CacheResponse getAll4FiledName();

	CacheResponse getAll();

}
