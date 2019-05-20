package com.callan.service.provider.service;

import java.util.Map;

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
	Map<Long, JTableDict> getAll4Id();

	
	/**
	 * 查找有显示标识的记录
	 * @param id
	 * @param showFlag 是否校验showFlag字段 
	 * @return
	 */
	JTableDict getOne(Long id, boolean showFlag);

	/**
	 * 根据code获取记录 
	 * @param tablecode
	 * @param activeFlag
	 * @return
	 */
	JTableDict getByCode(String tablecode);
	
	/**
	 * 根据code获取记录  只取有效记录
	 * @param tablecode
	 * @param activeFlag
	 * @return
	 */
	JTableDict getByCode(String tablecode, boolean activeFlag);


	Map<String, JTableDict> getAll4Code();

}
