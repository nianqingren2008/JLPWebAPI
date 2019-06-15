package com.callan.service.provider.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callan.service.provider.dao.mapper.JStatisconfdetailMapper;
import com.callan.service.provider.pojo.base.CacheResponse;
import com.callan.service.provider.pojo.cache.LocalCacheable;
import com.callan.service.provider.pojo.db.JStatisconfdetail;
import com.callan.service.provider.service.IJStatisconfdetailService;

@Service
public class JStatisconfdetailServiceImpl implements IJStatisconfdetailService {

	@Autowired
	private JStatisconfdetailMapper statisconfdetailMapper;
	@LocalCacheable
	@Override
	public CacheResponse getAll() {
		CacheResponse response = new CacheResponse();
		response.setCode(0);
		response.setData(statisconfdetailMapper.getAll());
		return response;
	}

	@LocalCacheable
	@Override
	public CacheResponse getAll4Id() {
		List<JStatisconfdetail> list = statisconfdetailMapper.getAll();
		Map<Long,JStatisconfdetail> map = new HashMap<Long,JStatisconfdetail>();
		for (JStatisconfdetail statisconfdetail : list) {
			map.put(statisconfdetail.getId(), statisconfdetail);
		}
		CacheResponse response = new CacheResponse();
		response.setCode(0);
		response.setData(map);
		return response;
	}

}
