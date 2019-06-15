package com.callan.service.provider.service;

import com.callan.service.provider.pojo.base.CacheResponse;
import com.callan.service.provider.pojo.db.JPageview;

public interface IJPageViewService {

	JPageview getOne(Long id);
	
	CacheResponse getAll4Code();

}
