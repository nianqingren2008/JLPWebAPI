package com.callan.service.provider.pojo.cache;

import java.util.HashMap;
import java.util.Map;

/**
 * 存放静态数据
 * @author callan
 *
 */
public class LocalData {
	public static Map<String,Object> dataMap = new HashMap<String, Object>();

	public static Object getData(String key) {
		return dataMap.get(key);
	}

	public static void setData(String key,Object data) {
		dataMap.put(key, data);
	}
	
}	
