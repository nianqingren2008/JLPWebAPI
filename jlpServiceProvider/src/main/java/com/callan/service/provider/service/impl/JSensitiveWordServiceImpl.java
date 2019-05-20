package com.callan.service.provider.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callan.service.provider.dao.mapper.JSensitiveWordMapper;
import com.callan.service.provider.pojo.db.JSensitiveWord;
import com.callan.service.provider.service.IJSensitiveWordService;

@Service
public class JSensitiveWordServiceImpl implements IJSensitiveWordService {
	@Autowired
	private JSensitiveWordMapper  jSensitiveWordMapper;
	
	@Override
	public JSensitiveWord getOne(Long id) {
		return jSensitiveWordMapper.getOne(id);
	}

	@Override
	public List<JSensitiveWord> getAll(boolean activeflag) {
		List<JSensitiveWord> all = jSensitiveWordMapper.getAll();
		List<JSensitiveWord> result = new ArrayList<JSensitiveWord>();
		for(JSensitiveWord jSensitiveWord : all) {
			if("1".equals(jSensitiveWord.getActiveflag())){
					result.add(jSensitiveWord);
			}
		}
		return result;
	}
}
