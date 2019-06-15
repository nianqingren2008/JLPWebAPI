package com.callan.service.provider.service;

import java.util.List;

import com.callan.service.provider.pojo.base.CacheResponse;
import com.callan.service.provider.pojo.db.JStatisconfdetail;

public interface IJStatisconfdetailService {

	CacheResponse getAll();

	CacheResponse getAll4Id();

//	List<JStatisconfdetail> getAllByConfId(Long confId);

}
