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
import com.callan.service.provider.dao.mapper.JSensitiveWordMapper;
import com.callan.service.provider.pojo.base.CacheResponse;
import com.callan.service.provider.pojo.cache.NativeCacheable;
import com.callan.service.provider.pojo.db.JSensitiveWord;
import com.callan.service.provider.pojo.db.JShowDetailView;
import com.callan.service.provider.service.IJRightService;
import com.callan.service.provider.service.IJSensitiveWordService;
import com.callan.service.provider.service.IJShowDetailViewService;

@Service
public class JSensitiveWordServiceImpl implements IJSensitiveWordService {
	@Autowired
	private JSensitiveWordMapper  jSensitiveWordMapper;
	
	@Override
	public JSensitiveWord getOne(Long id) {
		
		IJSensitiveWordService base = (IJSensitiveWordService) AopContext.currentProxy();
		Map<Long,JSensitiveWord> map = (Map<Long, JSensitiveWord>) base.getAll().getData();
		return map.get(id);
	}

	@NativeCacheable
	@Override
	public CacheResponse getAll() {
		List<JSensitiveWord> all = jSensitiveWordMapper.getAll();
		List<JSensitiveWord> result = new ArrayList<JSensitiveWord>();
		for(JSensitiveWord jSensitiveWord : all) {
			if(JLPConts.ActiveFlag.equals(jSensitiveWord.getActiveflag())){
					result.add(jSensitiveWord);
			}
		}
		CacheResponse response = new CacheResponse();
		response.setCode(0);
		response.setData(result);
		return response;
	}

	@NativeCacheable
	@Override
	public CacheResponse getAll4Name() {
		List<JSensitiveWord> all = jSensitiveWordMapper.getAll();
		Map<String,JSensitiveWord> map = new HashMap<String,JSensitiveWord>();
		for(JSensitiveWord jSensitiveWord : all) {
			if(JLPConts.ActiveFlag.equals(jSensitiveWord.getActiveflag())){
				map.put(jSensitiveWord.getName(),jSensitiveWord);
			}
		}
		
		CacheResponse response = new CacheResponse();
		response.setCode(0);
		response.setData(map);
		return response;
	}
}
