package com.callan.service.provider.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callan.service.provider.dao.mapper.JAdvancedqrItemMapper;
import com.callan.service.provider.pojo.db.JAdvancedqrItem;
import com.callan.service.provider.service.IJAdvancedqrItemService;

@Service
public class JAdvancedqrItemServiceImpl implements IJAdvancedqrItemService {
	@Autowired
	private JAdvancedqrItemMapper advancedqrItemMapper;


	@Override
	public JAdvancedqrItem getOne(Long id) {
		return advancedqrItemMapper.getOne(id);
	}


	@Override
	public void save(JAdvancedqrItem item) {
		advancedqrItemMapper.save(item);
	}
	

}
