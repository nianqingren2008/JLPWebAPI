package com.callan.service.provider.service.impl;

import java.util.ArrayList;
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
import com.callan.service.provider.dao.mapper.JTableFiledDictMapper;
import com.callan.service.provider.pojo.base.CacheResponse;
import com.callan.service.provider.pojo.cache.LocalCacheable;
import com.callan.service.provider.pojo.db.JShowView;
import com.callan.service.provider.pojo.db.JTableFieldDict;
import com.callan.service.provider.service.IJShowViewService;
import com.callan.service.provider.service.IJTableDictService;
import com.callan.service.provider.service.IJTableFieldDictService;

@Service
public class JTableFieldDictServiceImpl implements IJTableFieldDictService {
	
	
	@Autowired
	private JTableFiledDictMapper jTableFiledDictMapper;

	@Override
	public JTableFieldDict getOne(Long id) {
		IJTableFieldDictService base = (IJTableFieldDictService) AopContext.currentProxy();
		Map<Long, JTableFieldDict> data = (Map<Long, JTableFieldDict>) base.getAll4Id().getData();
		JTableFieldDict jTableFieldDict = data.get(id);
		return jTableFieldDict;
	}

	@Override
	public JTableFieldDict getOne(Long id, boolean showFlag) {
		JLPLog log = ThreadPoolConfig.getBaseContext();
		IJTableFieldDictService base = (IJTableFieldDictService) AopContext.currentProxy();
		Map<Long, JTableFieldDict> data = (Map<Long, JTableFieldDict>) base.getAll4Id().getData();
		JTableFieldDict entity = data.get(id);
		if(entity == null) {
			log.error("没有查到对象，id: " + id);
			return null;
		}
		
		if (showFlag) {
			if (JLPConts.ActiveFlag.equals(entity.getShowflag())) {
				return entity;
			} else {
				return null;
			}
		} else {
			return entity;
		}
	}
	@LocalCacheable
	@PostConstruct
	@Override
	public CacheResponse getAll4Id() {
		Map<Long, JTableFieldDict> map = new HashMap<>();
		List<JTableFieldDict> list = jTableFiledDictMapper.getAll();
		for (JTableFieldDict jShowDetailView : list) {
			map.put(jShowDetailView.getId(), jShowDetailView);
		}
		CacheResponse response = new CacheResponse();
		response.setCode(0);
		response.setData(map);
		return response;
	}
	
	@LocalCacheable
	@Override
	public CacheResponse getAll4TableCode() {
		Map<String, List<JTableFieldDict>> map = new HashMap<>();
		List<JTableFieldDict> list = jTableFiledDictMapper.getAll();
		for (JTableFieldDict entity : list) {
			if(map.get(entity.getTablecode()) == null){
				List<JTableFieldDict> fieldDictList = new ArrayList<JTableFieldDict>();
				fieldDictList.add(entity);
				map.put(entity.getTablecode(), fieldDictList);
			}else {
				map.get(entity.getTablecode()).add(entity);
			}
			
		}
		CacheResponse response = new CacheResponse();
		response.setCode(0);
		response.setData(map);
		return response;
	}

	@Override
	public List<JTableFieldDict> getByTableCode(String tableCode) {
		IJTableFieldDictService base = (IJTableFieldDictService) AopContext.currentProxy();
		Map<Long, List<JTableFieldDict>> data = (Map<Long, List<JTableFieldDict>>) base.getAll4TableCode().getData();
		List<JTableFieldDict> entity = data.get(tableCode);
		return entity;
	}

	@Override
	public List<JTableFieldDict> getByTableCode(String tableCode, boolean showFlag) {
		IJTableFieldDictService base = (IJTableFieldDictService) AopContext.currentProxy();
		Map<Long, List<JTableFieldDict>> data = (Map<Long, List<JTableFieldDict>>) base.getAll4TableCode(showFlag).getData();
		List<JTableFieldDict> entity = data.get(tableCode);
		return entity;
	}
	
	@LocalCacheable
	@Override
	public CacheResponse getAll4TableCode(boolean showFlag) {
		Map<String, List<JTableFieldDict>> map = new HashMap<>();
		List<JTableFieldDict> list = jTableFiledDictMapper.getAll();
		for (JTableFieldDict entity : list) {
			if(map.get(entity.getTablecode()) == null){
				List<JTableFieldDict> fieldDictList = new ArrayList<JTableFieldDict>();
				if(showFlag) {
					if(JLPConts.ActiveFlag.equals(entity.getShowflag())) {
						fieldDictList.add(entity);
					}
				}else {
					fieldDictList.add(entity);
				}
				map.put(entity.getTablecode(), fieldDictList);
			}else {
				if(showFlag) {
					if(JLPConts.ActiveFlag.equals(entity.getShowflag())) {
						map.get(entity.getTablecode()).add(entity);
					}
				}else {
					map.get(entity.getTablecode()).add(entity);
				}
			}
		}
		CacheResponse response = new CacheResponse();
		response.setCode(0);
		response.setData(map);
		return response;
	}

	@Override
	public JTableFieldDict getByTableCodeAndName(String tableCode, String fieldName) {
		
		IJTableFieldDictService base = (IJTableFieldDictService) AopContext.currentProxy();
		Map<Long, List<JTableFieldDict>> data = (Map<Long, List<JTableFieldDict>>) base.getAll4TableCode().getData();
		List<JTableFieldDict> list = data.get(tableCode);
		
		for(JTableFieldDict jTableFieldDict : list) {
			if(fieldName.equalsIgnoreCase(jTableFieldDict.getName())) {
				return jTableFieldDict;
			}
		}
		return null;
	}
	
	@LocalCacheable
	@Override
	public CacheResponse getAll(boolean showFlag) {
		List<JTableFieldDict> tablefieldList = new ArrayList<JTableFieldDict>();
		List<JTableFieldDict> list = jTableFiledDictMapper.getAll();
		for (JTableFieldDict entity : list) {
			if(showFlag)
			{
					if(JLPConts.ActiveFlag.equals(entity.getShowflag())) {
						tablefieldList.add(entity);
					}
			}
			else {
				tablefieldList.add(entity);
			}
		}
		CacheResponse response = new CacheResponse();
		response.setCode(0);
		response.setData(tablefieldList);
		return response;
	}
	
}
