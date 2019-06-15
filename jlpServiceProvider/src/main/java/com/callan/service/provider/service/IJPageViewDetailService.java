package com.callan.service.provider.service;

import java.util.List;

import com.callan.service.provider.pojo.base.CacheResponse;
import com.callan.service.provider.pojo.db.JPageviewdetail;

public interface IJPageViewDetailService {

	JPageviewdetail getOne(int id);
	
	CacheResponse getAll4PageCode();
	
	List<JPageviewdetail> getByPageCode(String pageCode);
}
