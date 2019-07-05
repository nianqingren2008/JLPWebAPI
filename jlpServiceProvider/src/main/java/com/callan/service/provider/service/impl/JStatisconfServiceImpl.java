package com.callan.service.provider.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callan.service.provider.dao.mapper.JStatisconfMapper;
import com.callan.service.provider.pojo.base.CacheResponse;
import com.callan.service.provider.pojo.cache.LocalCacheable;
import com.callan.service.provider.pojo.db.JStatisconf;
import com.callan.service.provider.service.IJStatisconfService;

@Service
public class JStatisconfServiceImpl implements IJStatisconfService{

	@Autowired
	private JStatisconfMapper statisconfMapper;
//	@Override
//	public List<JStatisconfdetail> queryDetailListById(Long statisconfigId) {
//		// TODO Auto-generated method stub
//		return new ArrayList<JStatisconfdetail>();
//	}
	
	@LocalCacheable
	@Override
	public CacheResponse getAll() {
		CacheResponse response = new CacheResponse();
		response.setCode(0);
		response.setData(statisconfMapper.getAll());
		return response;
	}

	@LocalCacheable
	@Override
	public CacheResponse getAll4Id() {
		List<JStatisconf> list = statisconfMapper.getAll();
		Map<Long,JStatisconf> map = new HashMap<Long,JStatisconf>();
		for (JStatisconf statisconf : list) {
			map.put(statisconf.getId(), statisconf);
		}
		CacheResponse response = new CacheResponse();
		response.setCode(0);
		response.setData(map);
		return response;
	}

	@Override
	public JStatisconf getOne(Long id) {
		return statisconfMapper.selectByPrimaryKey(id);
	}


	

}
