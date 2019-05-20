package com.callan.service.provider.service;

import java.util.List;
import java.util.Map;

import com.callan.service.provider.pojo.db.JTableFieldDict;

public interface IJTableFieldDictService {

	/**
	 * 
	 * @param id
	 * @return
	 */
	JTableFieldDict getOne(Long id);

	
	/**
	 * 获取所有值   key为viewId
	 * @param id
	 * @return
	 */
	Map<Long, JTableFieldDict> getAll4Id();

	
	/**
	 * 查找有显示标识的记录
	 * @param id
	 * @param showFlag 是否校验showFlag字段 
	 * @return
	 */
	JTableFieldDict getOne(Long id, boolean showFlag);

	/**
	 * 根据表名获取字段定义列表
	 * @param tableName
	 * @return
	 */
	List<JTableFieldDict> getByTableCode(String tableName);
	
	/**
	 * 根据表名获取字段定义列表
	 * @param tableName
	 * @return
	 */
	List<JTableFieldDict> getByTableCode(String tableName, boolean showFlag);


	Map<String, List<JTableFieldDict>> getAll4TableCode();
	
	Map<String, List<JTableFieldDict>> getAll4TableCode(boolean showFlag);

}
