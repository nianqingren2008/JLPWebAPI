package com.callan.service.provider.service;

import java.util.List;

import com.callan.service.provider.pojo.db.JDownloadfile;
import com.callan.service.provider.pojo.task.JTask;
import com.callan.service.provider.pojo.task.JTaskdownload;

public interface IJTaskService {
	 

	/**
	 * 添加 任务
	 * @param queryrecorddetails
	 */
	void addTask(JTask task);

	/**
	 * 添加  下载任务
	 * @param taskdownload
	 */
	void addTaskdownloads(JTaskdownload taskdownload);

	/**
	 * 刪除  任务
	 * @param id
	 */
	void delete(Long id);

	/**
	 * 获取用户 任务列表
	 * @param userId
	 * @return
	 */
	List<JTask> getByUserId(Long userId);

	/**
	 * 
	 * @return
	 */
	List<JTaskdownload> getAllDowndLoad();

	/**
	 * 
	 * @return
	 */
	List<JDownloadfile> getDownloadfile();

	/**
	 * 
	 * @param id
	 * @return
	 */
	JDownloadfile getDownloadfileById(long id);

	/**
	 * 
	 * @param id
	 * @param userId
	 * @return
	 */
	JTask getByIdAndUserId(Long id, Long userId);
}
