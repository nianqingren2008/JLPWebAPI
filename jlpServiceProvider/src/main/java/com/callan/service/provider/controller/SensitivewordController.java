package com.callan.service.provider.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.callan.service.provider.config.JLPLog;
import com.callan.service.provider.config.ThreadPoolConfig;
import com.callan.service.provider.pojo.base.BaseResponse;
import com.callan.service.provider.pojo.db.JSensitiveWord;
import com.callan.service.provider.service.IJSensitiveWordService;
import com.callan.service.provider.service.IJUserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(description = "敏感词管理")
public class SensitivewordController {
	@Autowired
	private IJUserService userService;
	@Autowired
	private IJSensitiveWordService jSensitiveWordService;
	
	@ApiOperation(value = "获取敏感词列表")
	@RequestMapping(value = "/api/Sensitiveword", method = { RequestMethod.GET })
	public String getSensitivewordList(HttpServletRequest request) {
		JLPLog log = ThreadPoolConfig.getBaseContext();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		BaseResponse baseResponse = new BaseResponse();
		// 从前台header中获取token参数
		String authorization = request.getHeader("Authorization") == null ? "6c52445e47389d707807022cbba731cd"
				: request.getHeader("Authorization");
		Long userId = userService.getIdByToken(authorization);
		if (userId == null || userId == 0) {
			baseResponse.setCode("0000");
			baseResponse.setText("用户信息获取失败，请检查请求头");
			resultMap.put("response", baseResponse);
			return JSONObject.toJSONString(resultMap);
		}
		log.info("userId : " + userId);
		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		IJSensitiveWordService base = (IJSensitiveWordService) AopContext.currentProxy();
		Map<Long, JSensitiveWord> allData = (Map<Long, JSensitiveWord>) base.getAll().getData();
		Iterator<Entry<Long, JSensitiveWord>> entries = allData.entrySet().iterator(); 
		while (entries.hasNext()) { 
			Map<String, Object> map = new HashMap<String, Object>();
			Entry<Long, JSensitiveWord> entry = entries.next(); 
			map.put("id", entry.getValue().getId());
			map.put("name", entry.getValue().getName());
			resultList.add(map);
		}
		
		resultMap.put("response", new BaseResponse());
		resultMap.put("sensitivewords", resultList);
		String json = JSONObject.toJSONString(resultMap);
		log.info("response : " + json);
		return json;
	}
	
	
	@ApiOperation(value = "获取所有可设置字段")
	@RequestMapping(value = "/api/Sensitiveword/AllFieldName", method = { RequestMethod.GET })
	public String getSensitivAllFieldName(HttpServletRequest request) {
		JLPLog log = ThreadPoolConfig.getBaseContext();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		BaseResponse baseResponse = new BaseResponse();
		// 从前台header中获取token参数
		String authorization = request.getHeader("Authorization") == null ? "6c52445e47389d707807022cbba731cd"
				: request.getHeader("Authorization");
		Long userId = userService.getIdByToken(authorization);
		if (userId == null || userId == 0) {
			baseResponse.setCode("0000");
			baseResponse.setText("用户信息获取失败，请检查请求头");
			resultMap.put("response", baseResponse);
			return JSONObject.toJSONString(resultMap);
		}
		log.info("userId : " + userId);
		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		
		
		
		resultMap.put("response", new BaseResponse());
		resultMap.put("allFields", resultList);
		String json = JSONObject.toJSONString(resultMap);
		log.info("response : " + json);
		return json;
	}
	
}
