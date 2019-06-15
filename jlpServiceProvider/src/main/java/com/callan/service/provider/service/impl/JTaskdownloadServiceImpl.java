package com.callan.service.provider.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callan.service.provider.dao.mapper.JStatisconfMapper;
import com.callan.service.provider.dao.mapper.JTaskdownloadMapper;
import com.callan.service.provider.pojo.base.CacheResponse;
import com.callan.service.provider.pojo.task.JTask;
import com.callan.service.provider.pojo.task.JTaskdownload;
import com.callan.service.provider.service.IJTaskdownloadService;

@Service
public class JTaskdownloadServiceImpl implements IJTaskdownloadService{

	@Autowired
	private JTaskdownloadMapper taskdowenloadMapper;
	@Override
	public CacheResponse getAll() {
		 return taskdowenloadMapper.getAll();
	}

	@Override
	public CacheResponse getAll4Id() {
		 
		List<JTaskdownload> list = taskdowenloadMapper.getAll();
		Map<Long,JTaskdownload> map = new HashMap<Long,JTaskdownload>();
		for (JTaskdownload jtaskdownload : list) {
			map.put(jtaskdownload.getId(), jtaskdownload);
		}
		CacheResponse response = new CacheResponse();
		response.setCode(0);
		response.setData(map);
		return response;
	}

}
