package com.callan.service.provider.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callan.service.provider.dao.mapper.JQueryrecordDetailMapper;
import com.callan.service.provider.dao.mapper.JQueryrecordMapper;
import com.callan.service.provider.pojo.db.JQueryrecord;
import com.callan.service.provider.pojo.db.JQueryrecordDetails;
import com.callan.service.provider.service.IJQueryrecordDetailService;

@Service
public class JQueryrecordDetailServiceImpl implements IJQueryrecordDetailService {
	@Autowired
	private JQueryrecordDetailMapper  queryrecordDetailMapper;


	@Override
	public JQueryrecordDetails getOne(Long id) {
		return queryrecordDetailMapper.getOne(id);
	}


	@Override
	public void save(JQueryrecordDetails queryrecord) {
		queryrecordDetailMapper.save(queryrecord);
	}
	

}
