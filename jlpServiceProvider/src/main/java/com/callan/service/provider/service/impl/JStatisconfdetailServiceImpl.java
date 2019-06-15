package com.callan.service.provider.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callan.service.provider.dao.mapper.JStatisconfdetailMapper;
import com.callan.service.provider.pojo.base.CacheResponse;
import com.callan.service.provider.pojo.cache.LocalCacheable;
import com.callan.service.provider.pojo.db.JStatisconfdetail;
import com.callan.service.provider.pojo.db.JTableFieldDict;
import com.callan.service.provider.pojo.db.JUser;
import com.callan.service.provider.service.IJStatisconfdetailService;
import com.callan.service.provider.service.IJTableFieldDictService;
import com.callan.service.provider.service.IJUserService;
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
	
	@Override
	public List<JStatisconfdetail> getAllByConfId(Long confId) {
		return statisconfdetailMapper.getAllByConfId(confId);
	}

}
