package com.callan.service.provider.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callan.service.provider.dao.mapper.JQueryrecordDetailMapper;
import com.callan.service.provider.pojo.db.JQueryrecordDetails;
import com.callan.service.provider.service.IJQueryrecordDetailService;

@Service
public class JQueryrecordDetailServiceImpl implements IJQueryrecordDetailService {
	@Autowired
	private JQueryrecordDetailMapper  queryrecordDetailMapper;


	@Override
	public JQueryrecordDetails getOne(long id) {
		return queryrecordDetailMapper.getOne(id);
	}


	@Override
	public void save(JQueryrecordDetails queryrecord) {
		queryrecordDetailMapper.save(queryrecord);
	}


	@Override
	public List<JQueryrecordDetails> getByQueryId(long queryId) {
		return queryrecordDetailMapper.getByQueryId(queryId);
	}
	

}
