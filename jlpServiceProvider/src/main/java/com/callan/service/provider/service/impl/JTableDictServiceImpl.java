package com.callan.service.provider.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callan.service.provider.config.JLPConts;
import com.callan.service.provider.config.JLPLog;
import com.callan.service.provider.config.ThreadPoolConfig;
import com.callan.service.provider.dao.mapper.JTabledictMapper;
import com.callan.service.provider.pojo.base.CacheResponse;
import com.callan.service.provider.pojo.cache.LocalCacheable;
import com.callan.service.provider.pojo.db.JShowDetailView;
import com.callan.service.provider.pojo.db.JTableDict;
import com.callan.service.provider.service.IJShowDetailViewService;
import com.callan.service.provider.service.IJShowViewService;
import com.callan.service.provider.service.IJTableDictService;

@Service
public class JTableDictServiceImpl implements IJTableDictService {
	
	@Autowired
	private JTabledictMapper jTableDictMapper;

	@Override
	public JTableDict getOne(Long id) {
		IJTableDictService base = (IJTableDictService) AopContext.currentProxy();
		Map<Long, JTableDict> map = (Map<Long, JTableDict>) base.getAll4Id().getData();
		return map.get(id);
	}

	@Override
	public JTableDict getOne(Long id, boolean activeFlag) {
		IJTableDictService base = (IJTableDictService) AopContext.currentProxy();
		Map<Long, JTableDict> map = (Map<Long, JTableDict>) base.getAll4Id().getData();
		JTableDict jTableDict = map.get(id);
		if (activeFlag) {
			if (JLPConts.ActiveFlag.equals(jTableDict.getActiveflag())) {
				return jTableDict;
			} else {
				return null;
			}
		} else {
			return jTableDict;
		}
	}
	@LocalCacheable
	@Override
	public CacheResponse getAll4Id() {
		Map<Long, JTableDict> map = new HashMap<>();
		List<JTableDict> list = jTableDictMapper.getAll();
		for (JTableDict jTableDict : list) {
			map.put(jTableDict.getId(), jTableDict);
		}
		CacheResponse response = new CacheResponse();
		response.setCode(0);
		response.setData(map);
		return response;
	}

	@Override
	public JTableDict getByName(String tableName) {
		IJTableDictService base = (IJTableDictService) AopContext.currentProxy();
		Map<Long, JTableDict> map = (Map<Long, JTableDict>) base.getAll4Name().getData();
		return map.get(tableName);
	}

	@Override
	public JTableDict getByName(String tableName, boolean activeFlag) {
		JLPLog log = ThreadPoolConfig.getBaseContext();
		IJTableDictService base = (IJTableDictService) AopContext.currentProxy();
		Map<Long, JTableDict> map = (Map<Long, JTableDict>) base.getAll4Name().getData();
		JTableDict entity = map.get(tableName);
		if(entity == null) {
			log.error("没有查到对象，name: " + tableName);
			return null;
		}
		if(activeFlag) {
			if(JLPConts.ActiveFlag.equals(entity.getActiveflag())) {
				return entity;
			}else {
				return null;
			}
		}else {
			return entity;
		}
	}
	@LocalCacheable
	@Override
	public CacheResponse getAll4Name() {
		Map<String, JTableDict> map = new HashMap<>();
		List<JTableDict> list = jTableDictMapper.getAll();
		for (JTableDict jTableDict : list) {
			map.put(jTableDict.getName(), jTableDict);
		}
		CacheResponse response = new CacheResponse();
		response.setCode(0);
		response.setData(map);
		return response;
	}
	
	
	
	
	

	@Override
	public JTableDict getByCode(String tableCode) {
		IJTableDictService base = (IJTableDictService) AopContext.currentProxy();
		Map<Long, JTableDict> map = (Map<Long, JTableDict>) base.getAll4Code().getData();
		return map.get(tableCode);
	}

	@SuppressWarnings({ "unchecked", "unlikely-arg-type" })
	@Override
	public JTableDict getByCode(String tableCode, boolean activeFlag) {
		JLPLog log = ThreadPoolConfig.getBaseContext();
		IJTableDictService base = (IJTableDictService) AopContext.currentProxy();
		Map<Long, JTableDict> map = (Map<Long, JTableDict>) base.getAll4Code().getData();
		JTableDict entity = map.get(tableCode);
		if(entity == null) {
			log.error("没有查到对象，code: " + tableCode);
			return null;
		}
		if(activeFlag) {
			if(JLPConts.ActiveFlag.equals(entity.getActiveflag())) {
				return entity;
			}else {
				return null;
			}
		}else {
			return entity;
		}
	}
	
	@LocalCacheable
	@Override
	public CacheResponse getAll4Code() {
		Map<String, JTableDict> map = new HashMap<>();
		List<JTableDict> list = jTableDictMapper.getAll();
		for (JTableDict jTableDict : list) {
			map.put(jTableDict.getCode(), jTableDict);
		}
		CacheResponse response = new CacheResponse();
		response.setCode(0);
		response.setData(map);
		return response;
	}
	
}
