package com.callan.service.provider.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callan.service.provider.dao.mapper.DLabmasterinfoMapper;
import com.callan.service.provider.pojo.db.DLabmasterinfo;
import com.callan.service.provider.service.IDLabmasterinfoService;

@Service
public class DLabmasterinfoServiceImpl implements IDLabmasterinfoService {
	@Autowired
	private DLabmasterinfoMapper labmasterinfoMapper;
	
	@Override
	public DLabmasterinfo getOne(long id) {
		return labmasterinfoMapper.selectByPrimaryKey(id);
	}
}
