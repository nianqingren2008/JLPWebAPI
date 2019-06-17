package com.callan.service.provider.service;

import java.util.List;
import java.util.Map;

import com.callan.service.provider.pojo.base.CacheResponse;
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
	CacheResponse getAll4Id();

	
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


	CacheResponse getAll4TableCode();
	
	CacheResponse getAll4TableCode(boolean showFlag);

	/**
	 * 根据表名和字段名查找字段定义
	 * @param code
	 * @param fieldName
	 * @param b
	 * @return
	 */
	JTableFieldDict getByTableCodeAndName(String code, String fieldName);


	CacheResponse getAll(boolean showFlag);

}
