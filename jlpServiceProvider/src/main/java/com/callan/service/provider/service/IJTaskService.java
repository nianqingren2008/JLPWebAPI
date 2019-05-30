package com.callan.service.provider.service;

import com.callan.service.provider.pojo.db.JQueryrecordDetails;
import com.callan.service.provider.pojo.task.JTask;
import com.callan.service.provider.pojo.task.JTaskdownload;

public interface IJTaskService {
	/**
	 * 添加 查询记录 详细 信息
	 * @param queryrecorddetails
	 */
	void AddQueryrecorddetails(JQueryrecordDetails queryrecorddetails);

	/**
	 * 添加 任务
	 * @param queryrecorddetails
	 */
	void AddTask(JTask task);

	/**
	 * 添加  下载任务
	 * @param taskdownload
	 */
	void AddTaskdownloads(JTaskdownload taskdownload);

	/**
	 * 添加  任务
	 * @param id
	 */
	void DeleteTask(Long id);
}
