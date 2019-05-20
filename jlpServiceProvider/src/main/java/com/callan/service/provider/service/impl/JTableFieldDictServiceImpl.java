package com.callan.service.provider.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callan.service.provider.dao.mapper.JTableFiledDictMapper;
import com.callan.service.provider.pojo.db.JTableFieldDict;
import com.callan.service.provider.service.IJTableFieldDictService;

@Service
public class JTableFieldDictServiceImpl implements IJTableFieldDictService {
	@Autowired
	private JTableFiledDictMapper jTableFiledDictMapper;

	@Override
	public JTableFieldDict getOne(Long id) {
		return getAll4Id().get(id);
	}

	@Override
	public JTableFieldDict getOne(Long id, boolean showFlag) {
		JTableFieldDict jTableFieldDict = getAll4Id().get(id);
		if (showFlag) {
			if ("1".equals(jTableFieldDict.getShowflag())) {
				return jTableFieldDict;
			} else {
				return null;
			}
		} else {
			return jTableFieldDict;
		}
	}

	@Override
	public Map<Long, JTableFieldDict> getAll4Id() {
		Map<Long, JTableFieldDict> map = new HashMap<>();
		List<JTableFieldDict> list = jTableFiledDictMapper.getAll();
		for (JTableFieldDict jShowDetailView : list) {
			map.put(jShowDetailView.getId(), jShowDetailView);
		}
		return map;
	}
	
	@Override
	public Map<String, List<JTableFieldDict>> getAll4TableCode() {
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
		return map;
	}

	@Override
	public List<JTableFieldDict> getByTableCode(String tableName) {
		return getAll4TableCode().get(tableName);
	}

	@Override
	public List<JTableFieldDict> getByTableCode(String tableName, boolean showFlag) {
		return getAll4TableCode(showFlag).get(tableName);
	}

	@Override
	public Map<String, List<JTableFieldDict>> getAll4TableCode(boolean showFlag) {
		Map<String, List<JTableFieldDict>> map = new HashMap<>();
		List<JTableFieldDict> list = jTableFiledDictMapper.getAll();
		for (JTableFieldDict entity : list) {
			if(map.get(entity.getTablecode()) == null){
				List<JTableFieldDict> fieldDictList = new ArrayList<JTableFieldDict>();
				if(showFlag) {
					if("1".equals(entity.getShowflag())) {
						fieldDictList.add(entity);
					}
				}else {
					fieldDictList.add(entity);
				}
				map.put(entity.getTablecode(), fieldDictList);
			}else {
				if(showFlag) {
					if("1".equals(entity.getShowflag())) {
						map.get(entity.getTablecode()).add(entity);
					}
				}else {
					map.get(entity.getTablecode()).add(entity);
				}
			}
		}
		return map;
	}
}
