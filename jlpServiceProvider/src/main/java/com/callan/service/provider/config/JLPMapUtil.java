package com.callan.service.provider.config;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
public  class JLPMapUtil {
	
	/**
     * 
     * 利用set不可重复特性进行判断
     *
     * @param map
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
	public static void deleteDuplicate2(Map<String, String> map) {
        if (map == null || map.size() == 0) {
            return;
        }
        Set<String> set = new HashSet<String>();
        for (Iterator<Entry<String, String>> iterator = map.entrySet().iterator(); iterator.hasNext();) {
            Entry<String, String> entry = iterator.next();
            if (set.contains(entry.getValue())) {
                iterator.remove();
                continue;
            } else {
                set.add(entry.getValue());
            }
        }
    }

    /**
     * * 通过containsValue去重后放入新定义map
     *
     * @param map
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
	public static Map<String, String> deleteDuplicate1(Map<String, String> map) {
        if (map == null || map.size() == 0) {
            return new HashMap<String, String>();
        }
        Map<String, String> map2 = new HashMap<String, String>();
        for (Iterator<Entry<String, String>> iterator = map.entrySet().iterator(); iterator.hasNext();) {
            Entry<String, String> entry = iterator.next();
            if (map2.containsValue(entry.getValue())) {
                continue;
            } else {
                map2.put(entry.getKey(), entry.getValue());
            }
        }
        return map2;
    }
    
    
}
