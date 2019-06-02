package com.callan.service.provider.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callan.service.provider.dao.mapper.JTaskMapper;
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

}
