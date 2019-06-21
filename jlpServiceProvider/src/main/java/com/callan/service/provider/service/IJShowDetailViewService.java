package com.callan.service.provider.service;

import java.util.List;
import java.util.Map;

import com.callan.service.provider.pojo.base.CacheResponse;
import com.callan.service.provider.pojo.db.JShowDetailView;

public interface IJShowDetailViewService {

	/**
	 * 获取记录
	 * @param id
	 * @return
	 */
	JShowDetailView getOne(Long id);
	
	/**
	 * 获取有效记录
	 * @param id
	 * @param activeFlag
	 * @return
	 */
	JShowDetailView getOne(Long id, boolean activeFlag);
	
	/**
	 * 根据viewId取Detail集合
	 * @param id
	 * @return
	 */
	List<JShowDetailView> getByViewId(Long id);

	
	/**
	 * 获取所有值   key为viewId
	 * @param id
	 * @return
	 */
	CacheResponse getAll4Id();

	/**
	 * 获取所有值  并按viewId归类 只取 有效记录
	 * @param activeFlag
	 * @return
	 */
	CacheResponse getAll4ViewId();

	JShowDetailView getByFieldId(long code);

	CacheResponse getAll4FieldId();

}
