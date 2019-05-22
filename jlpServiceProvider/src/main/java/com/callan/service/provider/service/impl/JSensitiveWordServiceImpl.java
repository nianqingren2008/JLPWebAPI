package com.callan.service.provider.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callan.service.provider.dao.mapper.JSensitiveWordMapper;
import com.callan.service.provider.pojo.base.CacheResponse;
import com.callan.service.provider.pojo.cache.NativeCacheable;
import com.callan.service.provider.pojo.db.JSensitiveWord;
import com.callan.service.provider.service.IJRightService;
import com.callan.service.provider.service.IJSensitiveWordService;

@Service
public class JSensitiveWordServiceImpl implements IJSensitiveWordService {
	@Autowired
	private JSensitiveWordMapper  jSensitiveWordMapper;
	
	@Override
	public JSensitiveWord getOne(Long id) {
		return jSensitiveWordMapper.getOne(id);
	}

	@NativeCacheable
	@Override
	public CacheResponse getAll(boolean activeflag) {
		List<JSensitiveWord> all = jSensitiveWordMapper.getAll();
		List<JSensitiveWord> result = new ArrayList<JSensitiveWord>();
		for(JSensitiveWord jSensitiveWord : all) {
			if("1".equals(jSensitiveWord.getActiveflag())){
					result.add(jSensitiveWord);
			}
		}
		CacheResponse response = new CacheResponse();
		response.setCode(0);
		response.setData(result);
		return response;
	}
}
