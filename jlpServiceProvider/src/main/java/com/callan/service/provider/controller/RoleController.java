package com.callan.service.provider.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.callan.service.provider.config.JLPLog;
import com.callan.service.provider.config.ThreadPoolConfig;
import com.callan.service.provider.pojo.base.BaseResponse;
import com.callan.service.provider.pojo.db.JAdvancedqr;
import com.callan.service.provider.pojo.db.JRole;
import com.callan.service.provider.pojo.db.JUser;
import com.callan.service.provider.pojo.task.JTask;
import com.callan.service.provider.service.IJRoleService;
import com.callan.service.provider.service.IJUserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(description = "角色管理")
public class RoleController {
	@Autowired
	private IJUserService userService;
	@Autowired
	private IJRoleService jRoleService;
	
	@ApiOperation(value = "获取角色列表")
	@RequestMapping(value = "/api/Role", method = { RequestMethod.GET })
	public String getRoleList(HttpServletRequest request) {
		JLPLog log = ThreadPoolConfig.getBaseContext();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		BaseResponse baseResponse = new BaseResponse();
		// 从前台header中获取token参数
//		String authorization = request.getHeader("Authorization") == null ? "6c52445e47389d707807022cbba731cd"
//				: request.getHeader("Authorization");
//		Long userId = userService.getIdByToken(authorization);
		JUser user = (JUser) request.getSession().getAttribute("user");
		if (user == null || user.getId() == 0) {
			baseResponse.setCode("0000");
			baseResponse.setText("用户信息获取失败，请检查请求头");
			resultMap.put("response", baseResponse);
			return JSONObject.toJSONString(resultMap);
		}
		log.info("userId : " + user.getId());
		List<JRole> list = jRoleService.getAll();
		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		for (JRole jRole : list) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", jRole.getId()+"");
			map.put("name", jRole.getName() == null ? "" : jRole.getName());
			resultList.add(map);
		}
		
		resultMap.put("response", new BaseResponse());
		resultMap.put("roles", resultList);
		String json = JSONObject.toJSONString(resultMap);
		log.info("response : " + json);
		return json;
	}

}
