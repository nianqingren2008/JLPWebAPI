package com.callan.service.provider.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.callan.service.provider.dao.mapper.JTableclassdictMapper;
import com.callan.service.provider.pojo.tableclassdict.JTableclassdict;
import com.callan.service.provider.service.IJTableclassdictService;

public class JTableclassdictServiceImpl implements IJTableclassdictService {

	@Autowired
	private JTableclassdictMapper  jTableclassdictMapper;
	
	@Override
	public List<JTableclassdict> getAll() {
		return jTableclassdictMapper.getAll();
	}

}