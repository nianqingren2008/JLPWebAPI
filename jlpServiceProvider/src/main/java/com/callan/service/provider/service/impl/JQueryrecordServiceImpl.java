package com.callan.service.provider.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callan.service.provider.dao.mapper.JAdvancedqrMapper;
import com.callan.service.provider.dao.mapper.JQueryrecordMapper;
import com.callan.service.provider.pojo.db.JAdvancedqr;
import com.callan.service.provider.pojo.db.JQueryrecord;
import com.callan.service.provider.service.IJAdvancedqrService;
import com.callan.service.provider.service.IJQueryrecordService;

@Service
public class JQueryrecordServiceImpl implements IJQueryrecordService {
	@Autowired
	private JQueryrecordMapper  queryrecordMapper;


	@Override
	public JQueryrecord getOne(Long id) {
		return queryrecordMapper.getOne(id);
	}


	@Override
	public void save(JQueryrecord queryrecord) {
		queryrecordMapper.save(queryrecord);
	}
	

}
