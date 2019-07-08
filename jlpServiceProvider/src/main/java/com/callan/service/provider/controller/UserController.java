package com.callan.service.provider.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.callan.service.provider.config.JLPConts;
import com.callan.service.provider.config.JLPLog;
import com.callan.service.provider.config.MD5Util;
import com.callan.service.provider.config.ObjectUtil;
import com.callan.service.provider.config.ThreadPoolConfig;
import com.callan.service.provider.pojo.ControllerBaseResponse;
import com.callan.service.provider.pojo.advanceQueryBase.ColunmsModel;
import com.callan.service.provider.pojo.base.BaseResponse;
import com.callan.service.provider.pojo.cache.LocalData;
import com.callan.service.provider.pojo.db.JRole;
import com.callan.service.provider.pojo.db.JRoleRight;
import com.callan.service.provider.pojo.db.JSystemconfig;
import com.callan.service.provider.pojo.db.JUser;
import com.callan.service.provider.pojo.db.JUsertokens;
import com.callan.service.provider.pojo.user.UserChangePwd;
import com.callan.service.provider.pojo.user.UserModel;
import com.callan.service.provider.pojo.user.UserNewModel;
import com.callan.service.provider.pojo.user.UserRoleModel;
import com.callan.service.provider.service.IJLpService;
import com.callan.service.provider.service.IJRoleRightService;
import com.callan.service.provider.service.IJRoleService;
import com.callan.service.provider.service.IJSystemConfigService;
import com.callan.service.provider.service.IJUserService;
import com.callan.service.provider.service.IJUsertokensService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(description = "用户管理")
public class UserController {

	@Autowired
	private IJUserService jUserService;

	@Autowired
	private IJSystemConfigService systemConfigService;

	@Autowired
	private IJLpService jlpService;

	@Autowired
	private IJRoleRightService roleRightService;

	@Autowired
	private IJRoleService roleService;

	@Autowired
	private IJUsertokensService usertokensService;

	@ApiOperation(value = "用户登录")
	@RequestMapping(value = "/api/User", method = { RequestMethod.POST })
	public String login(@RequestBody UserModel user, HttpServletResponse resq, HttpServletRequest request) {
		JLPLog log = ThreadPoolConfig.getBaseContext();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		JUser users = jUserService.login(user.getUserCode(), user.getUserPwd());
		if (users == null || users.getId() == 0) {
			ControllerBaseResponse response = new ControllerBaseResponse();
			resq.setStatus(400);
			response.getResponse().setCode("400");
			response.getResponse().setText("用户名或密码错误");
			log.error(response.toJsonString());
			return response.toJsonString();
		}

		JSystemconfig systemconfig = (JSystemconfig) systemConfigService
				.getByClassTypeAndKeyName("system", "LoginActiveHours").getData();

		int activeHours = 24;
		if (systemconfig != null && systemconfig.getKeyvalue() != null) {
			activeHours = ObjectUtil.objToInt(systemconfig.getKeyvalue());
		}
		Date date = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.HOUR, activeHours);
		String ip = request.getLocalAddr();
		String secretKey = UUID.randomUUID().toString().replaceAll("-", "");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String token = MD5Util
				.md5Encrypt32Lower(sdf.format(cal.getTime()) + users.getId() + user.getUserPwd() + secretKey);
		JUsertokens usertokens = new JUsertokens();
		usertokens.setActivedate(cal.getTime());
		usertokens.setActiveflag(JLPConts.ActiveFlag);
		usertokens.setCreatedate(date);
		usertokens.setLoginip(ip);
		usertokens.setToken(token);
		long seqId = jlpService.getNextSeq("J_USERTOKENS");
		usertokens.setId(seqId);
		users.setToken(token);
		users.setSecretkey(secretKey);
		long userRole = users.getUserrole();

		JRole role = roleService.getOne(userRole);
		List<JRoleRight> roleRightList = roleRightService.getByRoleId(userRole);
		role.setList(roleRightList);
		Map<String, Object> roleRet = new HashMap<String, Object>();
		roleRet.put("roleId", role.getId());
		roleRet.put("roleName", role.getName());
		Set<Long> rightIdList = new HashSet<Long>();
		for (JRoleRight roleRight : roleRightList) {
			rightIdList.add(roleRight.getRightid());
		}
		roleRet.put("roleRights", rightIdList);

		usertokensService.save(usertokens);
		jUserService.update(users);
		Map<String, Object> userInfo = new HashMap<>();
		userInfo.put("userId", users.getId());
		userInfo.put("userName", users.getName());
		userInfo.put("AuthorizaToken", token);
		userInfo.put("role", roleRet);
		BaseResponse response = new BaseResponse();
		response.setText("登录成功");

		request.getSession().setAttribute("user", users);

		resultMap.put("userInfo", userInfo);
		resultMap.put("response", response);
		String json = JSONObject.toJSONString(resultMap);
		log.info("response--> " + json);
		return json;
	}

	@ApiOperation(value = "更改用户密码")
	@RequestMapping(value = "/api/changepwd", method = { RequestMethod.POST })
	public String ChangePwd(@RequestBody UserChangePwd userChangePwd, HttpServletRequest request,
			HttpServletResponse resq) {
		JLPLog log = ThreadPoolConfig.getBaseContext();
		ControllerBaseResponse response = new ControllerBaseResponse();
		// 从前台header中获取token参数
		String authorization = request.getHeader("Authorization") == null ? "6c52445e47389d707807022cbba731cd"
				: request.getHeader("Authorization");
		JUser user = jUserService.getUserByToken(authorization);
//		JUser user = (JUser) request.getSession().getAttribute("user"); 
		if (user == null) {
			resq.setStatus(400);
			response.getResponse().setCode("400");
			response.getResponse().setText("登录过期，请先登录");
			return response.toJsonString();
		}

		if (!user.getLoginpwd().equals(userChangePwd.getUserPwd())) {
			resq.setStatus(400);
			response.getResponse().setCode("400");
			response.getResponse().setText("用户名或密码错误");
			return response.toJsonString();
		}
		user.setLoginpwd(userChangePwd.getUserNewPwd());
		try {
			jUserService.updatePwd(user);
		} catch (Exception e) {
			log.error("修改密码失败", e);
			resq.setStatus(400);
			response.getResponse().setCode("400");
			response.getResponse().setText(e.getMessage());
			return response.toJsonString();
		}
		return response.toJsonString();
	}

	@ApiOperation(value = "获取用户列表")
	@RequestMapping(value = "/api/User", method = { RequestMethod.GET })
	public String Gets() {
		JLPLog log = ThreadPoolConfig.getBaseContext();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<JUser> users = jUserService.getAll();
		List<ColunmsModel> cols = new ArrayList<ColunmsModel>();
		ColunmsModel model1 = new ColunmsModel();
		model1.setDataIndex("usercode");
		model1.setIsLongStr(false);
		model1.setIsSearched(false);
		model1.setIsTag(false);
		model1.setKey("usercode");
		model1.setTitle("登录名");
		cols.add(model1);

		ColunmsModel model2 = new ColunmsModel();
		model2.setDataIndex("username");
		model2.setIsLongStr(false);
		model2.setIsSearched(false);
		model2.setIsTag(false);
		model2.setKey("username");
		model2.setTitle("姓名");
		cols.add(model2);

		ColunmsModel model3 = new ColunmsModel();
		model3.setDataIndex("rolename");
		model3.setIsLongStr(false);
		model3.setIsSearched(false);
		model3.setIsTag(false);
		model3.setKey("rolename");
		model3.setTitle("角色名");
		cols.add(model3);
		List<Map<String, Object>> userRet = new ArrayList<Map<String, Object>>();
		for (JUser user : users) {
			JRole role = roleService.getOne(user.getUserrole());
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("_userid", user.getId());
			map.put("usercode", user.getLogincode());
			map.put("username", user.getName());
			map.put("_roleid", role.getId());
			map.put("rolename", role.getName());
			userRet.add(map);
		}
		resultMap.put("columns", cols);
		resultMap.put("content", userRet);
		resultMap.put("response", new BaseResponse());
		String json = JSONObject.toJSONString(resultMap);
		log.info("response--> " + json);
		return json;
	}

	@ApiOperation(value = "添加新用户")
	@RequestMapping(value = "/api/User/new", method = { RequestMethod.POST })
	public String NewUser(@RequestBody UserNewModel userNewModel, HttpServletResponse response) {
		JLPLog log = ThreadPoolConfig.getBaseContext();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<JUser> users = jUserService.getByLogincode(userNewModel.getUserLoginCode());
		if (users != null && users.size() > 0) {
			BaseResponse baseResponse = new BaseResponse();
			response.setStatus(409);
			baseResponse.setCode("409");
			baseResponse.setText("已存在相同登录编码");
			resultMap.put("response", baseResponse);
			String json = JSONObject.toJSONString(resultMap);
			log.error("response --> " + json);
			return json;
		}

		JRole role = roleService.getOne(userNewModel.getUserRole());

		if (role == null) {
			BaseResponse baseResponse = new BaseResponse();
			response.setStatus(404);
			baseResponse.setCode("404");
			baseResponse.setText("角色编码不存在或角色无权限");
			resultMap.put("response", baseResponse);
			String json = JSONObject.toJSONString(resultMap);
			log.error("response --> " + json);
			return json;
		}

		List<JRoleRight> roleRightList = roleRightService.getByRoleId(userNewModel.getUserRole());
//		role.setList(roleRightList);
		Map<String, Object> roleRet = new HashMap<String, Object>();
		roleRet.put("roleId", role.getId());
		roleRet.put("roleName", role.getName());
		List<Long> rightIdList = new ArrayList<Long>();
		if (roleRightList != null) {
			for (JRoleRight roleRight : roleRightList) {
				rightIdList.add(roleRight.getRightid());
			}
		}
		roleRet.put("roleRights", rightIdList);

		JUser user = new JUser();
		user.setActiveflag(JLPConts.ActiveFlag);
		user.setLogincode(userNewModel.getUserLoginCode());
		user.setName(userNewModel.getUserName());
		user.setUserrole(userNewModel.getUserRole());
		user.setLoginpwd(MD5Util.md5Encrypt32Lower("123456"));
		long seqId = jlpService.getNextSeq("J_USERS");
		user.setId(seqId);
		jUserService.save(user);

		Map<String, Object> userInfo = new HashMap<>();
		userInfo.put("_userid", user.getId());
		userInfo.put("usercode", user.getLogincode());
		userInfo.put("userName", user.getName());
		userInfo.put("_roleid", role.getId());
		userInfo.put("rolename", role.getName());

		BaseResponse baseResponse = new BaseResponse();
		baseResponse.setText("登录成功");

		resultMap.put("userInfo", userInfo);
		resultMap.put("response", baseResponse);
		String json = JSONObject.toJSONString(resultMap);
		log.info("response--> " + json);
		
		//清理本地内存
		
		
		return json;
	}

	@ApiOperation(value = "用户角色修改")
	@RequestMapping(value = "/api/User/Role", method = { RequestMethod.POST })
	public String Post(@RequestBody UserRoleModel userRole, HttpServletResponse response) {
		JLPLog log = ThreadPoolConfig.getBaseContext();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		JUser users = jUserService.getOne(userRole.getUserId());

		if (users == null) {
			BaseResponse baseResponse = new BaseResponse();
			response.setStatus(404);
			baseResponse.setCode("404");
			baseResponse.setText("未找到用户信息");
			resultMap.put("response", baseResponse);
			String json = JSONObject.toJSONString(resultMap);
			log.error("response --> " + json);
			return json;
		}

		JRole jRole = roleService.getOne(userRole.getRoleId());

		if (jRole == null) {
			BaseResponse baseResponse = new BaseResponse();
			response.setStatus(404);
			baseResponse.setCode("404");
			baseResponse.setText("未找到角色信息");
			resultMap.put("response", baseResponse);
			String json = JSONObject.toJSONString(resultMap);
			log.error("response --> " + json);
			return json;
		}

		if (users.getUserrole().longValue() != jRole.getId().longValue()) {
			users.setUserrole(jRole.getId());
			jUserService.update(users);
		}
		BaseResponse baseResponse = new BaseResponse();
		resultMap.put("response", baseResponse);
		String json = JSONObject.toJSONString(resultMap);
		log.info("response--> " + json);
		return json;
	}

	@ApiOperation(value = "删除用户")
	@RequestMapping(value = "/api/User/{Id}", method = { RequestMethod.DELETE })
	public String Delete(@PathVariable Long Id, HttpServletResponse response) {
		JLPLog log = ThreadPoolConfig.getBaseContext();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		JUser users = jUserService.getOne(Id);
		if (users == null) {
			BaseResponse baseResponse = new BaseResponse();
			response.setStatus(404);
			baseResponse.setCode("404");
			baseResponse.setText("未找到用户信息");
			resultMap.put("response", baseResponse);
			String json = JSONObject.toJSONString(resultMap);
			log.error("response --> " + json);
			return json;
		}
		users.setActiveflag(JLPConts.Inactive);
		jUserService.update(users);
		BaseResponse baseResponse = new BaseResponse();
		resultMap.put("response", baseResponse);
		String json = JSONObject.toJSONString(resultMap);
		log.info("response--> " + json);
		return json;
	}

}
