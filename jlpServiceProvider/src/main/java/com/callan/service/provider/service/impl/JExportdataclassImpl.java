package com.callan.service.provider.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callan.service.provider.dao.mapper.JExportdataclassMapper;
import com.callan.service.provider.pojo.db.JExportdataclass;
import com.callan.service.provider.service.IJExportdataclassService;

@Service
public class JExportdataclassImpl implements IJExportdataclassService {
	
	@Autowired JExportdataclassMapper exportdataclassMapper;

	@Override
	public List<JExportdataclass> getAll() {
		return exportdataclassMapper.getAll();
	}

	
	
	
}
