package com.callan.service.provider.service;

import java.util.List;

import com.callan.service.provider.pojo.base.CacheResponse;
import com.callan.service.provider.pojo.db.JStatisconf;

public interface IJStatisconfService  {

	CacheResponse getAll4Id();
	
//	List<JStatisconfdetail> queryDetailListById(Long statisconfigId);

	CacheResponse getAll();

	JStatisconf getOne(Long id);

	List<JStatisconf> getByPageCode(String pageCode);

}
