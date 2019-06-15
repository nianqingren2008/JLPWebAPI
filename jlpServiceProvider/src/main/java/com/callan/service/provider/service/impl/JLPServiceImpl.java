package com.callan.service.provider.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.concurrent.ExecutorService;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.callan.service.provider.config.JLPConts;
import com.callan.service.provider.config.JLPException;
import com.callan.service.provider.config.JLPLog;
import com.callan.service.provider.config.ObjectUtil;
import com.callan.service.provider.config.RedisUtil;
import com.callan.service.provider.config.ThreadPoolConfig;
import com.callan.service.provider.dao.IJLPDao;
import com.callan.service.provider.pojo.cache.SerializeUtil;
import com.callan.service.provider.pojo.cache.Sha1Util;
import com.callan.service.provider.pojo.db.JStatisconfdetail;
import com.callan.service.provider.service.IJLpService;

@Service
public class JLPServiceImpl implements IJLpService {

	@Autowired
	private IJLPDao dao;

	@Autowired
	private ExecutorService executorService;

	@Autowired
	RedisUtil redisUtil;

	/*
	 * 每页数据存活时长 单位为秒，3600s
	 */
	@Value("${pageDataAliveSeconds}")
	private int pageDataAliveSeconds;

	/*
	 * 向下向上查询的页数
	 */
	@Value("${pagePlusCount}")
	private int pagePlusCount;

	/*
	 * 加载中状态保存时长，防止意外中断导致key一直处于加载中状态，推荐300s
	 */
	@Value("${loadingSeconds}")
	private int loadingSeconds;
	
	/*
	 * 如果遇到loading状态的查询，连续执行的间隔
	 */
	@Value("${queryLoadingInterval}")
	private int queryLoadingInterval;
	/*
	 * 如果遇到loading状态的查询，连续执行的次数
	 */
	@Value("${queryLoadingCount}")
	private int queryLoadingCount;
	
	@Override
	public List<Map<String, Object>> queryForSQLStreaming(String sql, int pageNum, int pageSize) {
		JLPLog log = ThreadPoolConfig.getBaseContext();
		try {
			log.debug("queryForSQLStreaming -- > sql : " + sql);
			long start = System.currentTimeMillis();
			List<Map<String, Object>> pageData = dao.queryForSQLStreaming(sql, pageNum,pageSize);
			long end = System.currentTimeMillis();
			log.info("queryForSQLStreaming--" + (end - start) + "ms");
			return pageData;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return new ArrayList<Map<String, Object>>();
	}

	@Override
	public List<Map<String, Object>> queryForSQL(String sql, Object[] params) {
		JLPLog log = ThreadPoolConfig.getBaseContext();
		try {
			log.debug("queryForSQL -- > sql : " + sql);
			long start = System.currentTimeMillis();
			List<Map<String, Object>> pageData = dao.queryForSQL(sql, new Object[] {});
			long end = System.currentTimeMillis();
			log.info("queryForSQL--" + (end - start) + "ms");
			return pageData;
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
	public long getNextSeq(String seqName) {
		JLPLog log = ThreadPoolConfig.getBaseContext();
		String sql = "select seq_" + seqName + "_id.nextval seq from dual";
		try {
			return dao.getNexSeq(sql);
		} catch (Exception e) {
			log.error("获取序列出错 " + seqName, e);
		}
		return 0;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> queryForAdvanceQuery(SortedSet<String> tableNames, String tempSql,
			String patientTableWhere, Map<String, List<String>> tableWhere, String finalSelectFields,
			String tempSqlWhere, int pageNum, int pageSize) {
		JLPLog log = ThreadPoolConfig.getBaseContext();

		try {
			String key = SerializeUtil.getValueByType(new Object[] { tableNames, tempSql, patientTableWhere, tableWhere,
					finalSelectFields, tempSqlWhere });
			String baseKey = Sha1Util.getEncrypteWord(key);
			String serialKey = pageNum + "_" + baseKey;

			for (int i = 1; i <= pagePlusCount; i++) {
				int pageNumPlusDown = 0;
				String serialKeyPageUp = (pageNum + i) + "_" + baseKey;
				String serialKeyPageDown = null;
				if (pageNum - i > 0) {
					serialKeyPageDown = (pageNum - i) + "_" + baseKey;
					pageNumPlusDown = pageNum - i;
				}

				int pageNumPlusUp = pageNum + i;
				excutePagePlus(pageSize, log, serialKeyPageUp, pageNumPlusUp, tableNames, tempSql, patientTableWhere,
						tableWhere, finalSelectFields, tempSqlWhere);
				excutePagePlus(pageSize, log, serialKeyPageDown, pageNumPlusDown, tableNames, tempSql,
						patientTableWhere, tableWhere, finalSelectFields, tempSqlWhere);

			}

			if (serialKey != null && redisUtil.get(serialKey) == null) {
				Map<String, Object> dataMap = new HashMap<String, Object>();
				dataMap.put("lastActiveTime", new Date());
				dataMap.put("status", "loading");
				//加载中状态最多保存300s
				redisUtil.expire(serialKey, loadingSeconds);
				redisUtil.set(serialKey, dataMap);
				Map<String, String> sqlMap = getSqlMap(tableNames, tempSql, patientTableWhere, tableWhere,
						finalSelectFields, tempSqlWhere, pageNum, pageSize);
				long start = System.currentTimeMillis();
				String sql = sqlMap.get("sqlPageData10");
				List<Map<String, Object>> pageData = dao.queryForSQLStreaming(sql, pageNum, pageSize);
				if (pageData.size() != pageSize) {
					sql = sqlMap.get("sqlPageData1000");
					pageData = dao.queryForSQLStreaming(sql, pageNum, pageSize);
					if (pageData.size() != pageSize) {
						sql = sqlMap.get("sqlPageData100000");
						pageData = dao.queryForSQLStreaming(sql, pageNum, pageSize);
						if (pageData.size() != pageSize) {
							String finalTables4Count = toTableString(tableNames, tempSql, tableWhere);
							String sqlAllData = "select distinct " + finalSelectFields + " from " + finalTables4Count
									+ " " + tempSqlWhere + " order by " + JLPConts.PatientGlobalTable + ".Id";
							sql = sqlAllData;
							pageData = dao.queryForSQLStreaming(sql, pageNum, pageSize);
						}
					}
				}
				log.info("queryForSQLStreaming-----sql : " + sql);

				dataMap.put("lastActiveTime", new Date());
				dataMap.put("data", pageData);
				long end = System.currentTimeMillis();
				log.info("-------------------sqlData loaded-------------size : " + pageData.size() + ",pageNum : "
						+ pageNum + ",  耗时： " + (end - start) + "ms");
				dataMap.put("status", "finish");
				try {

					redisUtil.set(serialKey, dataMap);
					// 第一页和第二页存放30天，其他页存放1小时
					if (pageNum == 1 || pageNum == 2) {
						redisUtil.expire(serialKey, 3600 * 24 * 30);
					} else {
						redisUtil.expire(serialKey, pageDataAliveSeconds);
					}
				} catch (Exception e) {
					log.error("操作redis失败", e);
					redisUtil.del(serialKey);
				}
//				end = System.currentTimeMillis();
				return pageData;
			} else if(redisUtil.get(serialKey) != null && "loading".equals(ObjectUtil.objToString(((Map<String,Object>)redisUtil.get(serialKey)).get("status")))){
				int i = 0;
				while(i < queryLoadingCount) {
					log.info("[redis data is loading waiting 1s ], pageNum:" + pageNum);
					Thread.sleep(queryLoadingInterval);
					i++;
					if("finish".equals(ObjectUtil.objToString(((Map<String,Object>)redisUtil.get(serialKey)).get("status")))) {
						Map<String, List<Map<String, Object>>> map = (Map<String, List<Map<String, Object>>>) redisUtil
								.get(serialKey);
						return map.get("data");
					}
				}
				throw new JLPException("获取分页数据超时,请稍后重试");
				
			} else if(redisUtil.get(serialKey) != null && "finish".equals(ObjectUtil.objToString(((Map<String,Object>)redisUtil.get(serialKey)).get("status")))){
				log.info("[get data from redis success], pageNum:" + pageNum);
				Map<String, List<Map<String, Object>>> map = (Map<String, List<Map<String, Object>>>) redisUtil
						.get(serialKey);
				return map.get("data");
			}

		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new JLPException(e.getMessage());
		}
		return new ArrayList<Map<String, Object>>();
	}

	private Map<String, String> getSqlMap(SortedSet<String> tableNames, String tempSql, String patientTableWhere,
			Map<String, List<String>> tableWhere, String finalSelectFields, String tempSqlWhere, int pageNum,
			int pageSize) {
		Map<String, String> sqlMap = new HashMap<String, String>();
		String finalTables4PageData10 = toTableString(tableNames, tempSql, pageNum, pageSize, patientTableWhere,
				tableWhere, 10);
		String finalTables4PageData1000 = toTableString(tableNames, tempSql, pageNum, pageSize, patientTableWhere,
				tableWhere, 1000);
		String finalTables4PageData100000 = toTableString(tableNames, tempSql, pageNum, pageSize, patientTableWhere,
				tableWhere, 100000);

		String sqlPageData10 = "select distinct " + finalSelectFields + " from " + finalTables4PageData10 + " "
				+ tempSqlWhere + " order by " + JLPConts.PatientGlobalTable + ".Id";
		String sqlPageData1000 = "select distinct " + finalSelectFields + " from " + finalTables4PageData1000 + " "
				+ tempSqlWhere + " order by " + JLPConts.PatientGlobalTable + ".Id";
		String sqlPageData100000 = "select distinct " + finalSelectFields + " from " + finalTables4PageData100000 + " "
				+ tempSqlWhere + " order by " + JLPConts.PatientGlobalTable + ".Id";
		sqlMap.put("sqlPageData10", sqlPageData10);
		sqlMap.put("sqlPageData1000", sqlPageData1000);
		sqlMap.put("sqlPageData100000", sqlPageData100000);
		return sqlMap;
	}

	private void excutePagePlus(int pageSize, JLPLog log, String serialKeyPage, int pageNumPlus,
			SortedSet<String> tableNames, String tempSql, String patientTableWhere,
			Map<String, List<String>> tableWhere, String finalSelectFields, String tempSqlWhere) {
		if (serialKeyPage != null && redisUtil.get(serialKeyPage) == null) {

			Map<String, String> sqlMap = getSqlMap(tableNames, tempSql, patientTableWhere, tableWhere,
					finalSelectFields, tempSqlWhere, pageNumPlus, pageSize);

			log.info("-------------------开始加载第" + pageNumPlus + "页数据--------------");
			Map<String, Object> dataMap = new HashMap<String, Object>();
			dataMap.put("lastActiveTime", new Date());
			dataMap.put("status", "loading");
			//加载中状态最多保存300s
			redisUtil.expire(serialKeyPage, loadingSeconds);
			redisUtil.set(serialKeyPage, dataMap);
			executorService.execute(new Runnable() {
				@Override
				public void run() {
					try {
						log.info("-------------------start load sqlDataPlus data------------pageNumPlus: "
								+ pageNumPlus);

						long start = System.currentTimeMillis();
						String sql = sqlMap.get("sqlPageData10");
						List<Map<String, Object>> pageData = dao.queryForSQLStreaming(sql, pageNumPlus, pageSize);
						if (pageData.size() != pageSize) {
							sql = sqlMap.get("sqlPageData1000");
							pageData = dao.queryForSQLStreaming(sql, pageNumPlus, pageSize);
							if (pageData.size() != pageSize) {
								sql = sqlMap.get("sqlPageData100000");
								pageData = dao.queryForSQLStreaming(sql, pageNumPlus, pageSize);
								if (pageData.size() != pageSize) {
									String finalTables4Count = toTableString(tableNames, tempSql, tableWhere);
									String sqlAllData = "select distinct " + finalSelectFields + " from "
											+ finalTables4Count + " " + tempSqlWhere + " order by "
											+ JLPConts.PatientGlobalTable + ".Id";
									sql = sqlAllData;
									pageData = dao.queryForSQLStreaming(sqlAllData, pageNumPlus, pageSize);
								}
							}
						}

						dataMap.put("lastActiveTime", new Date());
						dataMap.put("data", pageData);
						long end = System.currentTimeMillis();
						log.info("-------------------sqlDataPlus loaded-------------size : " + pageData.size()
								+ ",pageNumPlus : " + pageNumPlus + ",  耗时： " + (end - start) + "ms");
						dataMap.put("status", "finish");
//						start = System.currentTimeMillis();
						try {
							redisUtil.set(serialKeyPage, dataMap);
							// 第一页和第二页存放30天，其他页存放1小时
							if (pageNumPlus == 1 || pageNumPlus == 2) {
								redisUtil.expire(serialKeyPage, 3600 * 24 * 30);
							} else {
								redisUtil.expire(serialKeyPage, pageDataAliveSeconds);
							}
						} catch (Exception e) {
							log.error("操作redis失败", e);
							redisUtil.del(serialKeyPage);
						}
//						end = System.currentTimeMillis();
//						log.info("---------redis set data---------" + pageData.size() + "----------pageNumPlus : "
//								+ pageNumPlus + (end - start) + "ms");
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
		} else {
			log.info("---------redis data is exist--------- -------pageNumPlus : " + pageNumPlus);
		}
	}

	private String toTableString(SortedSet<String> tableArray, String advancedQueryWhere, int pageNum, int pageSize,
			String patientTableWhere, Map<String, List<String>> tableWhere, int times) {
		String ret = "";
		if (tableArray != null) {
			String patientTable = JLPConts.PatientGlobalTable.toLowerCase();
			ret = "( select * from (select * from " + patientTable + " " + patientTableWhere + " order by "
					+ JLPConts.PatientGlobalTable + ".Id ) " + " where rownum<" + pageNum * pageSize * times
					+ ") d_patientglobal";

			if (StringUtils.isNotBlank(advancedQueryWhere)) {
				ret += " inner join (" + advancedQueryWhere + ") a on " + patientTable + ".Id=a.Id ";
			}

			boolean IsInner = tableArray.size() <= 1;
			if (!IsInner && tableArray.size() == 2) {
				if (tableArray.contains("D_LABMASTERINFO") && tableArray.contains("D_LABRESULTS")) {
					IsInner = true;
				}
			}
			boolean IsLabResult = false;
			boolean IsLabMaster = false;

			for (String item : tableArray) {
				if ("D_LABMASTERINFO".equals(item.toUpperCase())) {
					IsLabMaster = true;
				} else if ("D_LABRESULTS".equalsIgnoreCase(item)) {
					IsLabResult = true;
					continue;
				} else if (item.toLowerCase().equals(patientTable)) {
					continue;
				}
				ret += (IsInner ? " inner" : " left") + " join " + item + " on " + patientTable + ".Id = " + item
						+ ".patientglobalid ";
			}
			if (IsLabResult) {
				if (!IsLabMaster) {
					ret += (IsInner ? "inner" : "left") + " join D_LABMASTERINFO on " + patientTable
							+ ".Id = D_LABMASTERINFO.patientglobalid ";
				}
				ret += (IsInner ? " inner" : " left")
						+ " join D_LABRESULTS on D_LABRESULTS.APPLYNO = D_LABMASTERINFO.APPLYNO ";
			}
		}
		return ret;
	}

	private String toTableString(SortedSet<String> tableArray, String advancedQueryWhere,
			Map<String, List<String>> tableWhere) {
		String ret = "";
		if (tableArray != null) {

			String patientTable = JLPConts.PatientGlobalTable.toLowerCase();
			ret = patientTable;

			if (StringUtils.isNotBlank(advancedQueryWhere)) {
				ret += " inner join (" + advancedQueryWhere + ") a on " + patientTable + ".Id=a.Id ";
			}

			boolean IsInner = tableArray.size() <= 1;
			if (!IsInner && tableArray.size() == 2) {
				if (tableArray.contains("D_LABMASTERINFO") && tableArray.contains("D_LABRESULTS")) {
					IsInner = true;
				}
			}
			boolean IsLabResult = false;
			boolean IsLabMaster = false;

			for (String item : tableArray) {
				if ("D_LABMASTERINFO".equals(item.toUpperCase())) {
					IsLabMaster = true;
				} else if ("D_LABRESULTS".equalsIgnoreCase(item)) {
					IsLabResult = true;
					continue;
				} else if (item.toLowerCase().equals(patientTable)) {
					continue;
				}
				ret += (IsInner ? " inner" : " left") + " join " + item + " on " + patientTable + ".Id = " + item
						+ ".patientglobalid ";
			}
			if (IsLabResult) {
				if (!IsLabMaster) {
					ret += (IsInner ? "inner" : "left") + " join D_LABMASTERINFO on " + patientTable
							+ ".Id = D_LABMASTERINFO.patientglobalid ";
				}
				ret += (IsInner ? " inner" : " left")
						+ " join D_LABRESULTS on D_LABRESULTS.APPLYNO = D_LABMASTERINFO.APPLYNO ";
			}
		}
		return ret;
	}

	@Override
	public int queryForCount(String sql) {
		JLPLog log = ThreadPoolConfig.getBaseContext();
		try {
			String serialKey = "count_" + Sha1Util.getEncrypteWord(sql);
			if (redisUtil.get(serialKey) != null) {
				log.info("[GET sql for count SUCCESS]  ");
				return ObjectUtil.objToInt(redisUtil.get(serialKey));
			} else {
				log.info("sql for count -- > sql : " + sql);
				long start = System.currentTimeMillis();
				List<Map<String, Object>> countList = dao.queryForSQL(sql, new Object[] {});
				long end = System.currentTimeMillis();
				log.info("sql for count--" + (end - start) + "ms");
				if (countList != null && countList.size() > 0) {
					int count = ObjectUtil.objToInt(countList.get(0).get("count"));
					try {
						redisUtil.set(serialKey, count);
						// 存放30天
						redisUtil.expire(serialKey, 3600 * 24 * 30);
					} catch (Exception e) {
						log.error("操作redis失败", e);
						redisUtil.del(serialKey);
					}
					return count;
				} else {
					return 0;
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return 0;
	}
 
	 
	 
}
