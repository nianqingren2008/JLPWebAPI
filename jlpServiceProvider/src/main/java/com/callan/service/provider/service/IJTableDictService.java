package com.callan.service.provider.service;

import java.util.Map;

import com.callan.service.provider.pojo.base.CacheResponse;
import com.callan.service.provider.pojo.db.JTableDict;

public interface IJTableDictService {

	/**
	 * 
	 * @param id
	 * @return
	 */
	JTableDict getOne(Long id);

	
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
	JTableDict getOne(Long id, boolean showFlag);

	/**
	 * 根据code获取记录 
	 * @param tableName
	 * @param activeFlag
	 * @return
	 */
	JTableDict getByName(String tableName);
	
	/**
	 * 根据code获取记录  只取有效记录
	 * @param tableName
	 * @param activeFlag
	 * @return
	 */
	JTableDict getByName(String tableName, boolean activeFlag);


	CacheResponse getAll4Name();
	
	
	
	/**
	 * 根据code获取记录 
	 * @param tableCode
	 * @param activeFlag
	 * @return
	 */
	JTableDict getByCode(String tableCode);
	
	/**
	 * 根据code获取记录  只取有效记录
	 * @param tableCode
	 * @param activeFlag
	 * @return
	 */
	JTableDict getByCode(String tableCode, boolean activeFlag);


	CacheResponse getAll4Code();
	

}
