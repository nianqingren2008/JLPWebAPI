package com.callan.service.provider.service;

import java.util.List;

import com.callan.service.provider.pojo.tableclassdict.JTableclassdict;

public interface IJTableclassdictService {

	/**
	 * 获取  标签所属分类列表
	 * @return
	 */
	List<JTableclassdict> getAll();

}
