package com.callan.service.provider.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callan.service.provider.dao.mapper.JTaskMapper;
import com.callan.service.provider.pojo.base.CacheResponse;
import com.callan.service.provider.pojo.cache.LocalCacheable;
import com.callan.service.provider.pojo.db.JDownloadfile;
import com.callan.service.provider.pojo.task.JTask;
import com.callan.service.provider.pojo.task.JTaskdownload;
import com.callan.service.provider.service.IJTaskService;

@Service
public class JTaskServiceImpl implements IJTaskService {

	@Autowired
	private JTaskMapper  jTaskMapper;
	 

	@Override
	public void addTask(JTask task) {
		jTaskMapper.addTasktasktask(task);
	}

	@Override
	public void delete(Long id) {
		jTaskMapper.delete(id);
		
	}

	@Override
	public void addTaskdownloads(JTaskdownload taskdownload) {
		jTaskMapper.addTaskdownloads(taskdownload);
	}

	@Override
	public List<JTask> getByUserId(Long userId) {
		return jTaskMapper.getByUserId(userId);
	}

	@Override
	public List<JTaskdownload> getAllDowndLoad() {
		return jTaskMapper.getAllDowndLoad();
	}

	@Override
	public List<JDownloadfile> getDownloadfile() {
		return jTaskMapper.getDownloadfile();
	}

	@Override
	public JDownloadfile getDownloadfileById(long id) {
		return jTaskMapper.getDownloadfileById(id);
	}

	@Override
	public JTask getByIdAndUserId(Long id, Long userId) {
		return jTaskMapper.getByIdAndUserId(id,userId);
	}

	@LocalCacheable
	@Override
	public CacheResponse getAll() {
		CacheResponse response = new CacheResponse();
		response.setCode(0);
		response.setData(jTaskMapper.getAll());
		return response;
	}

	@LocalCacheable
	@Override
	public CacheResponse getAll4Id() {
		List<JTask> list = jTaskMapper.getAll();
		Map<Long,JTask> map = new HashMap<Long,JTask>();
		for (JTask jtask : list) {
			map.put(jtask.getId(), jtask);
		}
		CacheResponse response = new CacheResponse();
		response.setCode(0);
		response.setData(map);
		return response;
	}

}
