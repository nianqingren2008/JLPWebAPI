package com.callan.service.provider.service;

import java.util.List;
import java.util.Map;
import java.util.SortedSet;

import com.callan.service.provider.config.JLPLog;
import com.callan.service.provider.pojo.db.JStatisconfdetail;

public interface IJLpService {
	
	/**
	 * 流式加载执行sql
	 * @param sql  sql语句
	 * @param params   参数数组
	 * @param pageSize 
	 * @param sqlData 
	 * @return
	 */
	public List<Map<String, Object>> queryForSQLStreaming(String sql, int pageNum, int pageSize);
	
	public List<Map<String, Object>> queryForSQL(String sql,Object[] params);
	
	/**
	 * 获取序列下一个值
	 * @param seqName
	 * @return
	 * @throws Exception 
	 */
	public long getNextSeq(String seqName);

	public List<Map<String, Object>> queryForAdvanceQuery(SortedSet<String> tableNames, String tempSql,
			String patientTableWhere, Map<String, List<String>> tableWhere, String finalSelectFields,
			String tempSqlWhere, int pageNumInt, int pageSizeInt, String sqlCount,String preUrl
			, boolean isImageUrl, String imageUrl, String imageField, String pathImageUrl
			, String pathImageField, boolean isPathImageUrl,Long userRole);

	public int queryForCount(String sqlCount);
}
