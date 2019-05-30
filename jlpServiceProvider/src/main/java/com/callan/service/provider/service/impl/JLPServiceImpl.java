package com.callan.service.provider.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callan.service.provider.config.JLPLog;
import com.callan.service.provider.config.ThreadPoolConfig;
import com.callan.service.provider.dao.IJLPDao;
import com.callan.service.provider.pojo.cache.NativeSqlData;
import com.callan.service.provider.pojo.cache.Sha1Util;
import com.callan.service.provider.service.IJLpService;

@Service
public class JLPServiceImpl implements IJLpService {

	@Autowired
	private IJLPDao dao;

	@Autowired
	private ExecutorService executorService;

	@Override
	public List<Map<String, Object>> queryForSQLStreaming(String sqlPageData,String sqlAllData, int pageNum,
			int pageSize) {
		JLPLog log = ThreadPoolConfig.getBaseContext();
		try {
//			String key = sqlAllData;
//			String serialKey = Sha1Util.getEncrypteWord(key);
//			if (NativeSqlData.getData(serialKey) != null
//					&& "finish".equals(NativeSqlData.getData(serialKey).get("status"))) {
//
//				log.info("[GET SQLDATA SUCCESS]  pageNum : " + pageNum + "  pageSize : " + pageSize);
//				List<Map<String, Object>> allData = (List<Map<String, Object>>) NativeSqlData.getData(serialKey)
//						.get("data");
//				// 获取缓存数据时，更新时间
//				NativeSqlData.getData(serialKey).put("lastActiveTime", new Date());
//				if (pageSize == 0) {
//					return allData;
//				}
//				List<Map<String, Object>> pageList = new ArrayList<>();
//				pageList = allData.subList((pageNum - 1) * pageSize, pageNum * pageSize);
//				return pageList;
//			} else {
				//只有data为null时，才去加载全部数据
//				if (NativeSqlData.getData(serialKey) == null && needCache) {
//					
//					System.out.print("-------------------开始全量加载--------------"  + sqlAllData);
//					Map<String, Object> dataMap = new HashMap<String, Object>();
//					dataMap.put("lastActiveTime", new Date());
//					dataMap.put("status", "loading");
//					
//					executorService.execute(new Runnable() {
//						@Override
//						public void run() {
//							try {
//								
//								List<Map<String, Object>> allData = dao.queryForSQLStreaming(sqlAllData,1,1);
//								dataMap.put("lastActiveTime", new Date());
//								dataMap.put("data", allData);
//								System.out.print("-------------------全量数据加载完毕-------------size : "  + allData.size());
//								dataMap.put("status", "finish");
//								NativeSqlData.setData(serialKey, dataMap);
//							} catch (Exception e) {
//								e.printStackTrace();
//							}
//						}
//					});
//				}
//				String pageSql = null;
//				if (pageSize != 0) {
//					pageSql = getPageSql(sqlAllData, pageNum, pageSize);
//				} else {
//					pageSql = sqlAllData;
//				}
				log.info("queryForSQLStreaming -- > sqlPageData : " + sqlPageData);

				long start = System.currentTimeMillis();
				List<Map<String, Object>> pageData = dao.queryForSQLStreaming(sqlPageData,pageNum,pageSize);
				if(pageData.size() != pageSize) {
					log.info("queryForSQLStreaming -- > sqlAllData : " + sqlAllData);
					pageData = dao.queryForSQLStreaming(sqlAllData,pageNum,pageSize);
				}
				long end = System.currentTimeMillis();
				log.info("queryForSQLStreaming--" + (end - start));

				return pageData;

//			}

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return new ArrayList<Map<String, Object>>();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> queryForSQL(String sql, Object[] params) {
		JLPLog log = ThreadPoolConfig.getBaseContext();
		try {
			String key = sql;
			String serialKey = Sha1Util.getEncrypteWord(key);
			if (NativeSqlData.getData(serialKey) != null
					&& "finish".equals(NativeSqlData.getData(serialKey).get("status"))) {

				log.info("[GET sql for count SUCCESS]  ");
				List<Map<String, Object>> allData = (List<Map<String, Object>>) NativeSqlData.getData(serialKey)
						.get("data");
				// 获取缓存数据时，更新时间
				NativeSqlData.getData(serialKey).put("lastActiveTime", new Date());
				return allData;
			} else {
				//只有data为null时，才去加载全部数据
				if (NativeSqlData.getData(serialKey) == null) {
					
					Map<String, Object> dataMap = new HashMap<String, Object>();
					dataMap.put("lastActiveTime", new Date());
					dataMap.put("status", "loading");
					
					executorService.execute(new Runnable() {
						@Override
						public void run() {
							try {
								List<Map<String, Object>> allData = dao.queryForSQL(sql, new Object[] {});
								dataMap.put("lastActiveTime", new Date());
								dataMap.put("data", allData);
								log.info("-------------------全量数据加载完毕-------------size : "  + allData.size());
								dataMap.put("status", "finish");
								NativeSqlData.setData(serialKey, dataMap);
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					});
				}
//				String pageSql = null;
//				if (pageSize != 0) {
//					pageSql = getPageSql(sqlAllData, pageNum, pageSize);
//				} else {
//					pageSql = sqlAllData;
//				}
				log.debug("sql for count -- > sql : " + sql);
				long start = System.currentTimeMillis();
				List<Map<String, Object>> pageData = dao.queryForSQL(sql, new Object[] {});
				long end = System.currentTimeMillis();
				log.info("sql for count--" + (end - start) + "ms");

				return pageData;

			}

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return new ArrayList<Map<String, Object>>();
	}

	@SuppressWarnings("unused")
	private String getPageSql(String sql, int pageNum, int pageSize) {
		String pageSql = "SELECT *" + "  FROM (SELECT tt.*, ROWNUM AS rowno" + "          FROM (  " + sql + ") tt"
				+ "         WHERE ROWNUM <= " + pageNum * pageSize + ") table_alias" + " WHERE table_alias.rowno > "
				+ (pageNum - 1) * pageSize;
		return pageSql;
	}

	@Override
	public long getNextSeq(String seqName)  {
		JLPLog log = ThreadPoolConfig.getBaseContext();
		String sql = "select seq_"+seqName+"_id.nextval seq as Id from dual";
		try {
			return dao.getNexSeq(sql);
		} catch (Exception e) {
			log.error("获取序列出错 " + seqName,e);
		}
		return 0;
	}

}
