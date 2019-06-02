package com.callan.service.provider.service.impl;

import java.util.List;

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
	public JAdvancedqrItem getOne(long id) {
		return advancedqrItemMapper.getOne(id);
	}


	@Override
	public void save(JAdvancedqrItem item) {
		advancedqrItemMapper.save(item);
	}


	@Override
	public List<JAdvancedqrItem> getByQrId(long qrId) {
		return advancedqrItemMapper.getByQrId(qrId);
	}
	

}
