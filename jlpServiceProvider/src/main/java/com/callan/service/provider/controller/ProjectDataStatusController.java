package com.callan.service.provider.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.callan.service.provider.config.ThreadPoolConfig;
import com.callan.service.provider.pojo.base.BaseResponse;
import com.callan.service.provider.pojo.db.JProjectdatastatus;
import com.callan.service.provider.pojo.db.JProjectdatastatusdict;
import com.callan.service.provider.pojo.db.JProjectdefaultstatus;
import com.callan.service.provider.pojo.project.ProjectDataDefaultStatusModel;
import com.callan.service.provider.pojo.project.ProjectDataStatusChangeModel;
import com.callan.service.provider.pojo.project.ProjectDataStatusDictModel;
import com.callan.service.provider.pojo.project.ProjectDataStatusModel;
import com.callan.service.provider.service.IJLpService;
import com.callan.service.provider.service.IJProjectDataStatusService;
import com.callan.service.provider.service.IJProjectDataStatusdictService;
import com.callan.service.provider.service.IJProjectdefaultstatusService;
import com.callan.service.provider.service.IJUserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(description = "课题数据操作")
public class ProjectDataStatusController {
	@Autowired
	private IJProjectdefaultstatusService projectdefaultstatusService;
	@Autowired
	private IJProjectDataStatusService projectDataStatusService;
	@Autowired
	private IJProjectDataStatusdictService projectDataStatusdictService;
	@Autowired
	private IJLpService jlpService;
	@Autowired
	private IJUserService userService;

	@ApiOperation(value = "课题数据状态字典查询")
	@RequestMapping(value = "/api/ProjectDataStatus/{Id}", method = { RequestMethod.GET })
	public String Gets(@PathVariable Long Id) {
		JLPLog log = ThreadPoolConfig.getBaseContext();
		Map<String, Object> resultMap = new HashMap<String, Object>();

		List<JProjectdatastatusdict> dictList = projectDataStatusdictService.getByProjectId(Id);
		List<Map<String, Object>> projectStatuDicts = new ArrayList<Map<String, Object>>();
		for (JProjectdatastatusdict projectdatastatusdict : dictList) {
			List<JProjectdefaultstatus> list = projectdefaultstatusService
					.getByDatastatusid(projectdatastatusdict.getId());
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", projectdatastatusdict.getId());
			map.put("name", projectdatastatusdict.getName());
			map.put("isDefault", list != null && list.size() > 0);
			projectStatuDicts.add(map);
		}
		List<JProjectdatastatusdict> projectdatastatusdicts = new ArrayList<JProjectdatastatusdict>();
		if (projectStatuDicts.size() == 0) {
			String[] tempDataStatus = new String[] { "未开始", "进行中", "已完成" };
			for (String item : tempDataStatus) {
				JProjectdatastatusdict projectDataStatus = new JProjectdatastatusdict();
				projectDataStatus.setActiveflag(JLPConts.ActiveFlag);
				projectDataStatus.setCreatedate(new Date());
				projectDataStatus.setName(item);
				projectDataStatus.setProjectid(Id);
				projectDataStatus.setStatustype("1");
				long seqId = jlpService.getNextSeq("J_PROJECTDATASTATUSDICT");
				projectDataStatus.setId(seqId);
				projectDataStatusdictService.save(projectDataStatus);
			}

			JProjectdefaultstatus projectdefaultstatus = new JProjectdefaultstatus();
			projectdefaultstatus.setActiveflag(JLPConts.ActiveFlag);
			projectdefaultstatus.setCreatedate(new Date());
			projectdefaultstatus.setDatastatusid(projectdatastatusdicts.get(0).getId());
			projectdefaultstatus.setProjectid(Id);
			long seqId = jlpService.getNextSeq("J_PROJECTDEFAULTSTATUS");
			projectdefaultstatus.setId(seqId);
			projectdefaultstatusService.save(projectdefaultstatus);

			for (JProjectdatastatusdict projectdatastatusdict : projectdatastatusdicts) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("id", projectdatastatusdict.getId());
				map.put("name", projectdatastatusdict.getName());
				map.put("isDefault", false);
				projectStatuDicts.add(map);
			}
		}
		List<Map<String, Object>> statuDicts = new ArrayList<>();

		for (Map<String, Object> map : projectStatuDicts) {
			Map<String, Object> newMap = new HashMap<>();
			newMap.put("id", map.get("id"));
			newMap.put("name", map.get("name"));
			statuDicts.add(newMap);
		}

		Map<String, Object> defaultStatus = projectStatuDicts.get(0);
		for (Map<String, Object> map : projectStatuDicts) {
			if ((boolean) map.get("isDefault")) {
				defaultStatus = map;
			}
		}
		if (defaultStatus == null) {
			defaultStatus = projectStatuDicts.get(0);
		}
		Map<String, Object> defaultStatusRet = new HashMap<String, Object>();
		defaultStatusRet.put("id", defaultStatus.get("id"));
		defaultStatusRet.put("name", defaultStatus.get("name"));

		resultMap.put("statuDicts", statuDicts);
		resultMap.put("defaultStatus", defaultStatusRet);
		BaseResponse baseResponse = new BaseResponse();
		resultMap.put("response", baseResponse);
		String json = JSONObject.toJSONString(resultMap);
		log.info("response-->" + json);
		return json;
	}

	@ApiOperation(value = "新增或修改课题数据字典")
	@RequestMapping(value = "/api/ProjectDataStatus", method = { RequestMethod.POST })
	public String Post(@RequestBody ProjectDataStatusDictModel projectDataStatusDict) {
		JLPLog log = ThreadPoolConfig.getBaseContext();
		Map<String, Object> resultMap = new HashMap<String, Object>();

		JProjectdatastatusdict jProjectdatastatusdict = null;
		if (projectDataStatusDict.getProjectDataStatusId() == 0) {
			jProjectdatastatusdict = new JProjectdatastatusdict();
			jProjectdatastatusdict.setActiveflag(JLPConts.ActiveFlag);
			jProjectdatastatusdict.setCreatedate(new Date());
			jProjectdatastatusdict.setName(projectDataStatusDict.getName());
			jProjectdatastatusdict.setStatustype(projectDataStatusDict.getStatusType());
			jProjectdatastatusdict.setProjectid(projectDataStatusDict.getProjectId());
			long seqId = jlpService.getNextSeq("J_PROJECTDATASTATUSDICT");
			jProjectdatastatusdict.setId(seqId);
			projectDataStatusdictService.save(jProjectdatastatusdict);
		} else {
			jProjectdatastatusdict = projectDataStatusdictService
					.getOne(projectDataStatusDict.getProjectDataStatusId());

			jProjectdatastatusdict.setStatustype(projectDataStatusDict.getStatusType());
			jProjectdatastatusdict.setName(projectDataStatusDict.getName());

			projectDataStatusdictService.update(jProjectdatastatusdict);
		}

		BaseResponse baseResponse = new BaseResponse();
		resultMap.put("response", baseResponse);
		resultMap.put("projectDataStatusId", jProjectdatastatusdict.getId());
		String json = JSONObject.toJSONString(resultMap);
		log.info("response-->" + json);
		return json;

	}

	@ApiOperation(value = "课题默认状态提交")
	@RequestMapping(value = "/api/ProjectDataStatus/default", method = { RequestMethod.POST })
	public String Post(@RequestBody ProjectDataDefaultStatusModel projectDataDefaultStatus) {
		JLPLog log = ThreadPoolConfig.getBaseContext();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		JProjectdefaultstatus projectDataDefault = projectdefaultstatusService
				.getByProjectId(projectDataDefaultStatus.getProjectId());
		if (projectDataDefault != null) {
			projectDataDefault.setDatastatusid(projectDataDefaultStatus.getProjectDataStatusId().longValue());
			projectdefaultstatusService.update(projectDataDefault);
		} else {
			projectDataDefault = new JProjectdefaultstatus();
			projectDataDefault.setActiveflag(JLPConts.ActiveFlag);
			projectDataDefault.setCreatedate(new Date());
			projectDataDefault.setDatastatusid(projectDataDefaultStatus.getProjectDataStatusId().longValue());
			projectDataDefault.setProjectid(projectDataDefaultStatus.getProjectId());
			long seqId = jlpService.getNextSeq("J_PROJECTDEFAULTSTATUS");
			projectDataDefault.setId(seqId);
			projectdefaultstatusService.save(projectDataDefault);
		}

		BaseResponse baseResponse = new BaseResponse();
		resultMap.put("response", baseResponse);
		Map<String, Object> projectDataDefault1 = new HashMap<>();
		projectDataDefault1.put("projectId", projectDataDefault.getProjectid());
		projectDataDefault1.put("datastatusid", projectDataDefault.getDatastatusid());
		resultMap.put("projectDataDefault", projectDataDefault1);
		String json = JSONObject.toJSONString(resultMap);
		log.info("response-->" + json);
		return json;

	}

	@ApiOperation(value = "批量变更项目状态(已存在的状态)")
	@RequestMapping(value = "/api/ProjectDataStatus/Change", method = { RequestMethod.POST })
	public String Post(@RequestBody ProjectDataStatusChangeModel projectDataStatusChange,
			HttpServletResponse response) {
		JLPLog log = ThreadPoolConfig.getBaseContext();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<JProjectdatastatusdict> list = projectDataStatusdictService
				.getByProjectId(projectDataStatusChange.getProjectId());
		List<JProjectdatastatusdict> projectDataStatusDictes = new ArrayList<>();
		for (JProjectdatastatusdict dict : list) {
			if (dict.getId().longValue() == projectDataStatusChange.getOldStatusId().longValue()
					|| dict.getId().longValue() == projectDataStatusChange.getNewStatusId().longValue())
				projectDataStatusDictes.add(dict);
		}
		if (projectDataStatusDictes.size() == 2) {
			String sql = "update J_PROJECTDATASTATUS set" + " STATUS='" + projectDataStatusChange.getNewStatusId()
					+ "' where" + " PROJECTID='" + projectDataStatusChange.getProjectId() + "'" + " and STATUS='"
					+ projectDataStatusChange.getOldStatusId() + "'";
			jlpService.excuteSql(sql, new Object[] {});
			BaseResponse baseResponse = new BaseResponse();
			resultMap.put("response", baseResponse);
			String json = JSONObject.toJSONString(resultMap);
			log.info("response-->" + json);
			return json;
		} else {
			BaseResponse baseResponse = new BaseResponse();
			response.setStatus(404);
			baseResponse.setCode("404");
			baseResponse.setText("状态代码错误或不存在！");
			resultMap.put("response", baseResponse);
			String json = JSONObject.toJSONString(resultMap);
			log.info("response-->" + json);
			return json;
		}
	}

	@ApiOperation(value = "删除课题状态字典")
	@RequestMapping(value = "/api/ProjectDataStatus/{Id}", method = { RequestMethod.DELETE })
	public String Delete(@PathVariable Long Id) {
		JLPLog log = ThreadPoolConfig.getBaseContext();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		JProjectdatastatusdict projectdatastatusdict = projectDataStatusdictService.getOne(Id.intValue());
		if (projectdatastatusdict != null) {
			projectdatastatusdict.setActiveflag(JLPConts.Inactive);
			projectDataStatusdictService.update(projectdatastatusdict);
		}
		BaseResponse baseResponse = new BaseResponse();
		resultMap.put("response", baseResponse);
		String json = JSONObject.toJSONString(resultMap);
		log.info("response-->" + json);
		return json;
	}

	@ApiOperation(value = "课题数据状态提交")
	@RequestMapping(value = "/api/ProjectDataStatus/Data/", method = { RequestMethod.POST })
	public String Post(@RequestBody ProjectDataStatusModel projectDataStatusModel, HttpServletRequest request,
			HttpServletResponse response) {
		JLPLog log = ThreadPoolConfig.getBaseContext();
		Map<String, Object> resultMap = new HashMap<String, Object>();
//		String authorization = request.getHeader("Authorization") == null ? "" : request.getHeader("Authorization");
//		Long userId = userService.getIdByToken(authorization);
//		JUser user = (JUser) request.getSession().getAttribute("user"); //jUserService.getUserByToken(authorization);
//		Long userId = null;
//		if (user == null || user.getId() == 0L) {
//			userId = 0L;
//		}
		List<JProjectdatastatus> projectDataStatuses = projectDataStatusService.getByProjectIdAndPatientglobalid(
				projectDataStatusModel.getProjectid(), projectDataStatusModel.getPatientGlobalId());

		if (projectDataStatuses.size() == 0) {
			JProjectdatastatus projectdatastatus = new JProjectdatastatus();
			projectdatastatus.setActiveflag(JLPConts.ActiveFlag);
			projectdatastatus.setCreatedate(new Date());
			projectdatastatus.setPatientglobalid(projectDataStatusModel.getPatientGlobalId());
			projectdatastatus.setProjectid(projectDataStatusModel.getProjectid());
			projectdatastatus.setStatus(projectDataStatusModel.getProjectDataStatusId().longValue());
			projectdatastatus.setChangestatus("0");
			long seqId = jlpService.getNextSeq("J_PROJECTDATASTATUS");
			projectdatastatus.setId(seqId);
			projectDataStatusService.save(projectdatastatus);
		} else {
			JProjectdatastatus projectDataStatus = projectDataStatuses.get(0);
			projectDataStatus.setStatus(projectDataStatusModel.getProjectDataStatusId().longValue());
			projectDataStatus.setChangestatus("0");
			projectDataStatusService.update(projectDataStatus);
		}
		BaseResponse baseResponse = new BaseResponse();
		resultMap.put("response", baseResponse);
		String json = JSONObject.toJSONString(resultMap);
		log.info("response-->" + json);
		return json;
	}

}
