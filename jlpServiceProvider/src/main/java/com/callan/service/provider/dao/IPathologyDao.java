package com.callan.service.provider.dao;

import java.util.List;
import java.util.Map;

public interface IPathologyDao {

	/**
     * 流式加载数据
     * @param sql
     * @param params
     * @return
     * @throws Exception 
     */
    public List<Map<String, Object>> queryForSQLStreaming(String sql, int pageNum,int pageSize) throws Exception;
    
    public List<Map<String, Object>> queryForSQL(String sql, Object[] params) throws Exception;

	public long getNexSeq(String sql) throws Exception;
}