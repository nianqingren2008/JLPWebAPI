package com.callan.service.provider.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.callan.service.provider.config.JLPConts;
import com.callan.service.provider.config.JLPLog;
import com.callan.service.provider.config.ThreadPoolConfig;
import com.callan.service.provider.pojo.base.BaseResponse;
import com.callan.service.provider.pojo.db.JAdvancedqr;
import com.callan.service.provider.pojo.db.JRight;
import com.callan.service.provider.pojo.db.JRole;
import com.callan.service.provider.pojo.db.JRoleRight;
import com.callan.service.provider.pojo.db.JUser;
import com.callan.service.provider.pojo.task.JTask;
import com.callan.service.provider.pojo.user.RoleModel;
import com.callan.service.provider.service.IJLpService;
import com.callan.service.provider.service.IJRightService;
import com.callan.service.provider.service.IJRoleRightService;
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
	private IJRoleRightService roleRightService;
	@Autowired
	private IJRoleService roleService;

	@Autowired
	private IJRightService rightService;

	@Autowired
	private IJLpService jlpService;

	@ApiOperation(value = "获取角色列表")
	@RequestMapping(value = "/api/Role", method = { RequestMethod.GET })
	public String getRoleList(HttpServletRequest request) {
		JLPLog log = ThreadPoolConfig.getBaseContext();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		BaseResponse baseResponse = new BaseResponse();
		// 从前台header中获取token参数
		String authorization = request.getHeader("Authorization") == null ? "6c52445e47389d707807022cbba731cd"
				: request.getHeader("Authorization");
		Long userId = userService.getIdByToken(authorization);
//		JUser user = (JUser) request.getSession().getAttribute("user");
		if (userId == null || userId.longValue() == 0) {
			baseResponse.setCode("0000");
			baseResponse.setText("用户信息获取失败，请检查请求头");
			resultMap.put("response", baseResponse);
			String json = JSONObject.toJSONString(resultMap);
			log.error("response --> " + json);
			return json;
		}
		log.info("userId : " + userId);
		List<JRole> list = roleService.getAll();
		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		for (JRole jRole : list) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", jRole.getId());
			map.put("name", jRole.getName() == null ? "" : jRole.getName());
			List<JRoleRight> roleRightList = roleRightService.getByRoleId(jRole.getId());
			if (roleRightList == null) {
				roleRightList = new ArrayList<JRoleRight>();
			}
			Set<Long> rights = new HashSet<Long>();
			for (JRoleRight roleRight : roleRightList) {
				rights.add(roleRight.getRightid());
			}
			map.put("rights", rights);
			resultList.add(map);
		}

		resultMap.put("response", new BaseResponse());
		resultMap.put("roles", resultList);
		String json = JSONObject.toJSONString(resultMap);
		log.info("response : " + json);
		return json;
	}

	@ApiOperation(value = "获取用户角色信息")
	@RequestMapping(value = "/api/Role/user", method = { RequestMethod.GET })
	public String GetUsers(Long userId, HttpServletResponse response) {
		JLPLog log = ThreadPoolConfig.getBaseContext();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		BaseResponse baseResponse = new BaseResponse();
		JUser user = userService.getOne(userId);
		if (user == null) {
			response.setStatus(404);
			baseResponse.setCode("404");
			baseResponse.setText("未发现此用户");
			resultMap.put("response", baseResponse);
			String json = JSONObject.toJSONString(resultMap);
			log.error("response --> " + json);
			return json;
		}
		JRole role = roleService.getOne(user.getUserrole());
		List<JRoleRight> roleRightList = roleRightService.getByRoleId(user.getUserrole());
		role.setList(roleRightList);
		Map<String, Object> userroles = new HashMap<String, Object>();
		userroles.put("roleId", role.getId());
		userroles.put("roleName", role.getName());
		List<Long> rightIdList = new ArrayList<Long>();
		for (JRoleRight roleRight : roleRightList) {
			rightIdList.add(roleRight.getRightid());
		}
		userroles.put("roleRights", rightIdList);

		resultMap.put("response", new BaseResponse());
		resultMap.put("roles", userroles);
		String json = JSONObject.toJSONString(resultMap);
		log.info("response : " + json);
		return json;

	}

	@SuppressWarnings("serial")
	@ApiOperation(value = "获取权限列表")
	@RequestMapping(value = "/api/role/rights", method = { RequestMethod.GET })
	public String GetRights() {
		JLPLog log = ThreadPoolConfig.getBaseContext();
		Map<String, Object> resultMap = new HashMap<String, Object>();

		List<JRight> list = rightService.getAll();
		List<Map<String, Object>> rights = new ArrayList<Map<String, Object>>();
		for (JRight right : list) {
			rights.add(new HashMap<String, Object>() {
				{
					put("key", right.getId());
					put("title", right.getName());
				}
			});
		}

		resultMap.put("response", new BaseResponse());
		resultMap.put("rights", rights);
		String json = JSONObject.toJSONString(resultMap);
		log.info("response : " + json);
		return json;
	}

	@Transactional
	@ApiOperation(value = "新增或者更改角色信息")
	@RequestMapping(value = "/api/Role", method = { RequestMethod.POST })
	public String Post(@RequestBody RoleModel roleModel, HttpServletResponse response) {
		JLPLog log = ThreadPoolConfig.getBaseContext();
		Map<String, Object> resultMap = new HashMap<String, Object>();

		JRole role = null;
		if (roleModel.getId() != null && roleModel.getId().longValue() > 0) {
			role = roleService.getOne(roleModel.getId());
		}

		List<JRight> rights = new ArrayList<JRight>();
		if (roleModel.rights != null) {
			for (Long rightId : roleModel.getRights()) {
				rights.add(rightService.getOne(rightId, true));
			}
		}
		List<Long> rightIdList = new ArrayList<Long>();
		for (JRight right : rights) {
			rightIdList.add(right.getId());
		}
		if (role == null) {
			role = new JRole();
			role.setActiveflag(JLPConts.ActiveFlag);
			role.setCreatedate(new Date());
			role.setName(roleModel.getName());
			long seqId = jlpService.getNextSeq("J_ROLE");
			role.setId(seqId);
			roleService.save(role);

			if (rights != null) {
				for (JRight item : rights) {
					JRoleRight roleright = new JRoleRight();
					roleright.setActiveflag(JLPConts.ActiveFlag);
					roleright.setCreatedate(new Date());
					roleright.setRightid(item.getId());
					roleright.setRoleid(role.getId());
					long rolerightId = jlpService.getNextSeq("J_ROLERIGHT");
					roleright.setId(rolerightId);
					roleRightService.save(roleright);
				}
			}
		} else {
			if (!StringUtils.equals(role.getName(), roleModel.getName())) {
				role.setName(roleModel.getName());
				roleService.update(role);
			}
			List<JRoleRight> rolerights = roleRightService.getByRoleId(role.getId());
			Set<Long> rolerightsIdList = new HashSet<Long>();
			List<Long> rolerightsDel = new ArrayList<Long>();
			for (JRoleRight roleRight : rolerights) {
				if (!rightIdList.contains(roleRight.getRightid())) {
					rolerightsDel.add(roleRight.getRightid());
					roleRight.setActiveflag(JLPConts.Inactive);
					roleRightService.update(roleRight);
				}
				rolerightsIdList.add(roleRight.getRightid());
			}
			List<Long> rolerightsNew = new ArrayList<Long>();
			for (JRight right : rights) {
				if (!rolerightsIdList.contains(right.getId())) {
					rolerightsNew.add(right.getId());
					JRoleRight roleright = new JRoleRight();
					roleright.setActiveflag(JLPConts.ActiveFlag);
					roleright.setCreatedate(new Date());
					roleright.setRightid(right.getId());
					roleright.setRoleid(role.getId());
					long roleRightId = jlpService.getNextSeq("J_ROLERIGHT");
					roleright.setId(roleRightId);
					roleRightService.save(roleright);
				}

			}
		}
		Map<String, Object> userRole = new HashMap<String, Object>();
		userRole.put("id", role.getId());
		userRole.put("name", role.getName());
		userRole.put("rights", rightIdList);

		resultMap.put("response", new BaseResponse());
		resultMap.put("roleinfo", userRole);
		String json = JSONObject.toJSONString(resultMap);
		log.info("response : " + json);
		return json;
	}

	@Transactional
	@ApiOperation(value = "角色删除")
	@RequestMapping(value = "/api/Role/{Id}", method = { RequestMethod.DELETE })
	public String Delete(@PathVariable Long Id, HttpServletResponse response) {
		BaseResponse baseResponse = new BaseResponse();
		JLPLog log = ThreadPoolConfig.getBaseContext();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		JRole role = roleService.getOne(Id);
		if (role == null) {
			response.setStatus(404);
			baseResponse.setCode("404");
			baseResponse.setText("未发现此角色");
			resultMap.put("response", baseResponse);
			String json = JSONObject.toJSONString(resultMap);
			log.error("response --> " + json);
			return json;
		}
		role.setActiveflag(JLPConts.Inactive);
		roleService.update(role);
		List<JRoleRight> rolerights = roleRightService.getByRoleId(role.getId());
		for (JRoleRight roleRight : rolerights) {
			roleRight.setActiveflag(JLPConts.Inactive);
			roleRightService.update(roleRight);
		}
		resultMap.put("response", new BaseResponse());
		String json = JSONObject.toJSONString(resultMap);
		log.info("response : " + json);
		return json;
	}

}
