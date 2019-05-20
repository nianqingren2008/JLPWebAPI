package com.callan.service.provider.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callan.service.provider.dao.mapper.JTableDictMapper;
import com.callan.service.provider.pojo.db.JTableDict;
import com.callan.service.provider.pojo.db.JTableFieldDict;
import com.callan.service.provider.service.IJTableDictService;

@Service
public class JTableDictServiceImpl implements IJTableDictService {
	@Autowired
	private JTableDictMapper jTableDictMapper;

	@Override
	public JTableDict getOne(Long id) {
		return getAll4Id().get(id);
	}

	@Override
	public JTableDict getOne(Long id, boolean activeFlag) {
		JTableDict jTableDict = getAll4Id().get(id);
		if (activeFlag) {
			if ("1".equals(jTableDict.getActiveflag())) {
				return jTableDict;
			} else {
				return null;
			}
		} else {
			return jTableDict;
		}
	}

	@Override
	public Map<Long, JTableDict> getAll4Id() {
		Map<Long, JTableDict> map = new HashMap<>();
		List<JTableDict> list = jTableDictMapper.getAll();
		for (JTableDict jTableDict : list) {
			map.put(jTableDict.getId(), jTableDict);
		}
		return map;
	}

	@Override
	public JTableDict getByCode(String tablecode) {
		
		return getAll4Code().get(tablecode);
	}

	@Override
	public JTableDict getByCode(String tablecode, boolean activeFlag) {
		JTableDict entity = getAll4Code().get(tablecode);
		if(activeFlag) {
			if("1".equals(entity.getActiveflag())) {
				return entity;
			}else {
				return null;
			}
		}else {
			return entity;
		}
	}
	
	@Override
	public Map<String, JTableDict> getAll4Code() {
		Map<String, JTableDict> map = new HashMap<>();
		List<JTableDict> list = jTableDictMapper.getAll();
		for (JTableDict jTableDict : list) {
			map.put(jTableDict.getCode(), jTableDict);
		}
		return map;
	}
}
