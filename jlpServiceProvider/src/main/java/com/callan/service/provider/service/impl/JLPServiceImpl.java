package com.callan.service.provider.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callan.service.provider.dao.IJLPDao;
import com.callan.service.provider.service.IJLpService;

@Service
public class JLPServiceImpl implements IJLpService{
	Log log = LogFactory.getLog(JLPServiceImpl.class);
	@Autowired
	private IJLPDao dao;
	
	@Override
	public List<Map<String, Object>> queryForSQLStreaming(String sql,Object[] params){
		try {
			return dao.queryForSQLStreaming(sql, params);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return new ArrayList<Map<String,Object>>();
	}

	@Override
	public List<Map<String, Object>> queryForSQL(String sql, Object[] params) {
		try {
			return dao.queryForSQL(sql, params);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return new ArrayList<Map<String,Object>>();
	}
}
