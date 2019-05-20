package com.callan.service.provider.service;

import java.util.List;
import java.util.Map;

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
	 * 根据viewId获取Detail集合  只获取有效记录
	 * @param viewId
	 * @param activeFlag
	 * @return
	 */
	List<JShowDetailView> getByViewId(Long viewId, boolean activeFlag);
	
	/**
	 * 获取所有值   key为viewId
	 * @param id
	 * @return
	 */
	Map<Long, JShowDetailView> getAll4Id();

	/**
	 * 获取所有值  并按viewId归类
	 * @param id
	 * @return
	 */
	Map<Long, List<JShowDetailView>> getAll4ViewId();
	/**
	 * 获取所有值  并按viewId归类 只取 有效记录
	 * @param activeFlag
	 * @return
	 */
	Map<Long, List<JShowDetailView>> getAll4ViewId(boolean activeFlag);

}
