package com.callan.service.provider.pojo.cache;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 存放静态数据
 * @author callan
 *
 */
public class NativeSqlData {
	public static ConcurrentHashMap<String,Map<String,Object>> sqlDataMap = new ConcurrentHashMap<>();

	public static Map<String,Object> getData(String key) {
		return sqlDataMap.get(key);
	}

	public static void setData(String key, Map<String,Object> data) {
		sqlDataMap.put(key, data);
	}
	public static ConcurrentHashMap<String,Map<String,Object>> getAllData() {
		return sqlDataMap;
	}
}	
