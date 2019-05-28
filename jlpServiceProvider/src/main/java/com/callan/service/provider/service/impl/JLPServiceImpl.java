package com.callan.service.provider.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callan.service.provider.dao.IJLPDao;
import com.callan.service.provider.pojo.cache.CacheKey;
import com.callan.service.provider.pojo.cache.NativeSqlData;
import com.callan.service.provider.pojo.cache.SerializeUtil;
import com.callan.service.provider.pojo.cache.Sha1Util;
import com.callan.service.provider.service.IJLpService;

@Service
public class JLPServiceImpl implements IJLpService{
	Log log = LogFactory.getLog(JLPServiceImpl.class);
	
	@Autowired
	private IJLPDao dao;
	
	@Autowired
	private ExecutorService executorService;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> queryForSQLStreaming( String sql,Object[] params,int pageNum,int pageSize){
		try {
			String key = sql + SerializeUtil.getValueByType(params);
        	String serialKey = Sha1Util.getEncrypteWord(key);
        	if(NativeSqlData.getData(serialKey) == null) {
        		executorService.execute(new Runnable() {
    				@Override
    				public void run() {
    					try {
    						List<Map<String, Object>> allData = dao.queryForSQLStreaming(sql, params);
    						Map<String,Object> dataMap = new HashMap<String, Object>();
    						dataMap.put("lastActiveTime", new Date());
    						dataMap.put("data", allData);
    						NativeSqlData.setData(serialKey, dataMap);
    					} catch (Exception e) {
    						e.printStackTrace();
    					}
    				}
    			});
        		String  pageSql = getPageSql(sql,pageNum,pageSize);
    			log.info("queryForSQLStreaming -- > pageSql : " + pageSql );
    			return dao.queryForSQLStreaming(pageSql, params);
        	} else {
        		log.info("[GET SQLDATA SUCCESS]  pageNum : " + pageNum + "  pageSize : " + pageSize);
        		List<Map<String, Object>> allData = (List<Map<String, Object>>) NativeSqlData.getData(serialKey).get("data");
        		//获取缓存数据时，更新时间
        		NativeSqlData.getData(serialKey).put("lastActiveTime", new Date());
        		List<Map<String, Object>> pageList = new ArrayList<>(); 
				if(null != allData && allData.size()>0){
					for(int i=(pageNum-1)*pageSize;i<pageNum*pageSize;i++){
						if(allData.size()>i){
							pageList.add(allData.get(i));
						}
					}
				}
				return pageList;
        	}
        	
			
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
	
	private String getPageSql(String sql, int pageNum, int pageSize) {
		String pageSql = "SELECT *" + 
				"  FROM (SELECT tt.*, ROWNUM AS rowno" + 
				"          FROM (  " + sql + ") tt" + 
				"         WHERE ROWNUM <= " + pageNum * pageSize + ") table_alias" + 
				" WHERE table_alias.rowno >= " + (pageNum -1) * pageSize;
		return pageSql;
	}
	
}
