package com.callan.service.provider.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callan.service.provider.config.JLPConts;
import com.callan.service.provider.dao.mapper.JPageviewMapper;
import com.callan.service.provider.dao.mapper.JPageviewdetailMapper;
import com.callan.service.provider.dao.mapper.JRightMapper;
import com.callan.service.provider.pojo.base.CacheResponse;
import com.callan.service.provider.pojo.cache.LocalCacheable;
import com.callan.service.provider.pojo.db.JPageview;
import com.callan.service.provider.pojo.db.JPageviewdetail;
import com.callan.service.provider.pojo.db.JRight;
import com.callan.service.provider.service.IJPageViewDetailService;
import com.callan.service.provider.service.IJPageViewService;
import com.callan.service.provider.service.IJRightService;
import com.callan.service.provider.service.IJShowDetailViewService;

@Service
public class JPageViewDetailServiceImpl implements IJPageViewDetailService {
	@Autowired
	private JPageviewdetailMapper  pageviewDetailMapper;
	
	@Override
	public JPageviewdetail getOne(int id) {
		return pageviewDetailMapper.selectByPrimaryKey(id);
	}
	
	@LocalCacheable
	@Override
	public CacheResponse getAll4PageCode() {
		List<JPageviewdetail> list = pageviewDetailMapper.getAll();
		Map<String, List<JPageviewdetail>> map = new HashMap<String, List<JPageviewdetail>>();
		for (JPageviewdetail entity : list) {
			List<JPageviewdetail> data = map.get(entity.getPagecode());
			if(data == null) {
				data = new ArrayList<JPageviewdetail>();
			}
			data.add(entity);
			map.put(entity.getPagecode(), data);
		}
		CacheResponse response = new CacheResponse();
		response.setCode(0);
		response.setData(map);
		return response;
	}

	@Override
	public List<JPageviewdetail> getByPageCode(String pageCode) {
		IJPageViewService base = (IJPageViewService) AopContext.currentProxy();
		Map<Long, List<JPageviewdetail>> data = (Map<Long, List<JPageviewdetail>>) base.getAll4Code().getData();
		List<JPageviewdetail> entity = data.get(pageCode);
		return entity;
	}

}
