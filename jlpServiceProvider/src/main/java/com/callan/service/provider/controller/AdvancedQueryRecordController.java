package com.callan.service.provider.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.callan.service.provider.config.JLPLog;
import com.callan.service.provider.config.ThreadPoolConfig;
import com.callan.service.provider.pojo.base.BaseResponse;
import com.callan.service.provider.pojo.db.JAdvancedqr;
import com.callan.service.provider.service.IJAdvancedqrService;
import com.callan.service.provider.service.IJUserService;

import io.swagger.annotations.ApiOperation;

@RestController
public class AdvancedQueryRecordController {
	@Autowired
	private IJUserService jUserService;

	@Autowired
	private IJAdvancedqrService advancedqrService;
	
	@ApiOperation(value = "", notes = "获取纳排条件列表")
	@RequestMapping(value = "/api/AdvancedQueryRecord", method = { RequestMethod.GET })
	 public String gets( HttpServletRequest request) {
		JLPLog log = ThreadPoolConfig.getBaseContext();
		Map<String,Object> resultMap = new HashMap<String, Object>();
		// 从前台header中获取token参数
		String authorization = request.getHeader("Authorization") == null ? "" : request.getHeader("Authorization");
		Long userId = jUserService.getIdByToken(authorization);
		if(userId == null || userId == 0) {
			BaseResponse baseResponse = new BaseResponse();
			baseResponse.setCode("0000");
			baseResponse.setText("用户信息获取失败，请检查请求头");
			resultMap.put("response", baseResponse );
			return JSONObject.toJSONString(resultMap);
		}
		List<JAdvancedqr> list = advancedqrService.getByUserId(userId);
		
		Collections.sort(list, new Comparator<JAdvancedqr>() {
			@Override
			public int compare(JAdvancedqr o1, JAdvancedqr o2) {
				return o1.getCreatedate().compareTo(o2.getCreatedate());
			}
		});
		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		for(JAdvancedqr advancedqr : list) {
			Map<String,Object> map =  new HashMap<String, Object>();
			map.put("id", advancedqr.getId());
			map.put("name", advancedqr.getAqname() == null ? "" : advancedqr.getAqname());
			map.put("createdate", sdf.format(advancedqr.getCreatedate()));
			resultList.add(map);
		}
		
		resultMap.put("response", new BaseResponse());
		resultMap.put("queryRecords", resultList);
         return JSONObject.toJSONString(resultMap);
     }
}
