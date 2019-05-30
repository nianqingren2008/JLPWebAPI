package com.callan.service.provider.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callan.service.provider.dao.mapper.JAdvancedqrMapper;
import com.callan.service.provider.pojo.db.JAdvancedqr;
import com.callan.service.provider.service.IJAdvancedqrService;

@Service
public class JAdvancedqrServiceImpl implements IJAdvancedqrService {
	@Autowired
	private JAdvancedqrMapper  advancedqrMapper;

	@Override
	public List<JAdvancedqr> getByUserId(Long userId) {
		return advancedqrMapper.getByUserId(userId);
	}
	

}
