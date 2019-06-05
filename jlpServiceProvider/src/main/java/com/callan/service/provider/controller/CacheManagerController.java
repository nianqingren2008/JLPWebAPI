package com.callan.service.provider.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.callan.service.provider.config.JLPLog;
import com.callan.service.provider.config.ObjectUtil;
import com.callan.service.provider.config.ThreadPoolConfig;
import com.callan.service.provider.pojo.base.BaseResponse;
import com.callan.service.provider.pojo.cache.LocalData;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(description = "缓存管理")
public class CacheManagerController {
	@Autowired
	private RedisTemplate<String, Object> redisTemplate;

	@ApiOperation(value = "清理redis缓存,支持头部模糊搜索(后面加*)")
	@RequestMapping(value = "/api/cacheClear/redis", method = { RequestMethod.GET })
	public String clearRedis(String key) {
		JLPLog log = ThreadPoolConfig.getBaseContext();
		if(StringUtils.isBlank(key)) {
			key = "*";
		}
		Set<String> keySet = redisTemplate.keys(key);
//		ValueOperations<String, Object> valueOper = redisTemplate.opsForValue();
		for(String cacheKey : keySet) {
			try {
				//Map<String,Object> value = (Map<String, Object>) valueOper.get(cacheKey);
				redisTemplate.delete(cacheKey);
			}catch(Exception e) {
				continue;
			}
		}
		Map<String,Object> resultMap = new HashMap<String, Object>();
		resultMap.put("response", new BaseResponse());
		String json = JSONObject.toJSONString(resultMap);
		log.info("response --> " + json);
		return json;
	}

	@ApiOperation(value = "清理处于加载中状态的redis缓存")
	@RequestMapping(value = "/api/cacheClear/redisLoading", method = { RequestMethod.GET })
	public String clearRedisLoading() {
		JLPLog log = ThreadPoolConfig.getBaseContext();
		Set<String> keySet = redisTemplate.keys("*");
		ValueOperations<String, Object> valueOper = redisTemplate.opsForValue();
		for(String key : keySet) {
			try {
				Map<String,Object> value = (Map<String, Object>) valueOper.get(key);
				if("loading".equals(ObjectUtil.objToString(value.get("status")))){
					log.info("key : " + key +",处于loading状态，执行删除");
					redisTemplate.delete(key);
				}
			}catch(Exception e) {
				continue;
			}
		}
		Map<String,Object> resultMap = new HashMap<String, Object>();
		resultMap.put("response", new BaseResponse());
		String json = JSONObject.toJSONString(resultMap);
		log.info("response --> " + json);
		return json;
	}
	
	@ApiOperation(value = "清理存储在本地的缓存数据(全部清理)")
	@RequestMapping(value = "/api/cacheClear/local", method = { RequestMethod.GET })
	public String clearLocalCache() {
		JLPLog log = ThreadPoolConfig.getBaseContext();
		LocalData.dataMap.clear();;
		Map<String,Object> resultMap = new HashMap<String, Object>();
		resultMap.put("response", new BaseResponse());
		String json = JSONObject.toJSONString(resultMap);
		log.info("response --> " + json);
		return json;
	}
}
