package com.callan.service.provider.service;

import java.util.List;
import java.util.Map;

public interface IJLpService {
	
	/**
	 * 流式加载执行sql
	 * @param sql  sql语句
	 * @param params   参数数组
	 * @return
	 */
	public List<Map<String, Object>> queryForSQLStreaming(String sql,Object[] params);
	
	public List<Map<String, Object>> queryForSQL(String sql,Object[] params);
}
