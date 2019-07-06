package com.callan.service.provider.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.callan.service.provider.config.ObjectUtil;
import com.callan.service.provider.config.ThreadPoolConfig;
import com.callan.service.provider.pojo.advanceQueryBase.Sorted;
import com.callan.service.provider.pojo.base.BaseResponse;
import com.callan.service.provider.pojo.db.JProject;
import com.callan.service.provider.pojo.db.JProjectstatistics;
import com.callan.service.provider.pojo.db.JProjecttags;
import com.callan.service.provider.pojo.db.JTagdicts;
import com.callan.service.provider.pojo.db.JTagvaluedicts;
import com.callan.service.provider.pojo.db.JUser;
import com.callan.service.provider.pojo.project.ProjectTagCompleteModel;
import com.callan.service.provider.pojo.project.ProjectTagModel;
import com.callan.service.provider.pojo.project.ProjectTagShowModel;
import com.callan.service.provider.service.IJLpService;
import com.callan.service.provider.service.IJProjectDataStatusService;
import com.callan.service.provider.service.IJProjectDataStatusdictService;
import com.callan.service.provider.service.IJProjectService;
import com.callan.service.provider.service.IJProjectdefaultstatusService;
import com.callan.service.provider.service.IJProjectstatisticsService;
import com.callan.service.provider.service.IJProjecttagsService;
import com.callan.service.provider.service.IJTagdictService;
import com.callan.service.provider.service.IJTagvaluedictService;
import com.callan.service.provider.service.IJUserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(description = "课题标签")
public class ProjectTagController {
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

	@Autowired
	private IJTagdictService tagdictService;

	@Autowired
	private IJTagvaluedictService tagvaluedictService;

	@Autowired
	private IJProjectstatisticsService projectstatisticsService;

	@Autowired
	private IJProjectService projectService;

	@Autowired
	private IJProjecttagsService projecttagsService;

	@ApiOperation(value = "获取标签列表")
	@RequestMapping(value = "/api/ProjectTag/{Id}", method = { RequestMethod.GET })
	public String Gets(@PathVariable Long Id, HttpServletRequest request, HttpServletResponse response) {
		JLPLog log = ThreadPoolConfig.getBaseContext();
		Map<String, Object> resultMap = new HashMap<String, Object>();
//		String authorization = request.getHeader("Authorization") == null ? "bc6ef9c43a0e5b25da87ca2ba948d3eb" : request.getHeader("Authorization");
//		Long userId = userService.getIdByToken(authorization);
//		if (userId == null) {
//			BaseResponse baseResponse = new BaseResponse();
//			response.setStatus(400);
//			baseResponse.setCode("400");
//			baseResponse.setText("用户信息获取失败，请检查请求头");
//			resultMap.put("response", baseResponse);
//			return JSONObject.toJSONString(resultMap);
//		}
		List<Map<String, String>> columns = new ArrayList<>();
		Map<String, String> mapId = new HashMap<>();
		mapId.put("dataIndex", "_id");
		mapId.put("title", "编号");
		columns.add(mapId);
		Map<String, String> mapName = new HashMap<>();
		mapName.put("dataIndex", "name");
		mapName.put("title", "名称");
		columns.add(mapName);
		Map<String, String> mapFieldType = new HashMap<>();
		mapFieldType.put("dataIndex", "fieldType");
		mapFieldType.put("title", "类型");
		columns.add(mapFieldType);
		Map<String, String> mapFieldValue = new HashMap<>();
		mapFieldValue.put("dataIndex", "fieldValue");
		mapFieldValue.put("title", "字段值");
		columns.add(mapFieldValue);
		Map<String, String> mapTagLevel = new HashMap<>();
		mapTagLevel.put("dataIndex", "tagLevel");
		mapTagLevel.put("title", "标记层级");
		columns.add(mapTagLevel);
		Map<String, String> mapTagComplete = new HashMap<>();
		mapTagComplete.put("dataIndex", "tagComplete");
		mapTagComplete.put("title", "标记进度");
		columns.add(mapTagComplete);

		List<JTagdicts> projectTagDBs = tagdictService.getByProjectId(Id);
		for (JTagdicts tagdicts : projectTagDBs) {
			List<JTagvaluedicts> valueDictList = tagvaluedictService.getByTagId(tagdicts.getId());
			tagdicts.setValueDictsList(valueDictList);
		}

		List<ProjectTagCompleteModel> projectTags = new ArrayList<ProjectTagCompleteModel>();
		for (JTagdicts tagdicts : projectTagDBs) {
			ProjectTagCompleteModel model = new ProjectTagCompleteModel();
			model.set_id(tagdicts.getId());
			model.setName(tagdicts.getName());
			model.setTagLevel(tagdicts.getTagattached());
			model.setFieldType(tagdicts.getValuetype());
			model.setIsShow(true);
			List<JTagvaluedicts> valueDictList = tagdicts.getValueDictsList();
			List<String> fieldValue = new ArrayList<String>();
			if (valueDictList != null && valueDictList.size() > 0) {
				Collections.sort(valueDictList, new Comparator<JTagvaluedicts>() {
					@Override
					public int compare(JTagvaluedicts o1, JTagvaluedicts o2) {
						return o1.getId().compareTo(o2.getId());
					}
				});
				for (JTagvaluedicts tagvaluedicts : valueDictList) {
					if (tagvaluedicts.getValue() != null) {
						fieldValue.add(tagvaluedicts.getValue());
					} else {
						fieldValue.add(tagvaluedicts.getMinvalue() + "" + tagvaluedicts.getMaxvalue());
					}
				}
			}
			String fieldValueStr = "";
			for (String str : fieldValue) {
				fieldValueStr += str + "/";
			}
			if (fieldValueStr.length() > 0) {
				fieldValueStr = fieldValueStr.substring(0, fieldValueStr.length() - 1);
			}
			model.setFieldValue(fieldValueStr);
			model.setTagComplete("0%");
			projectTags.add(model);
		}
		List<JProjectstatistics> projectstatistics = projectstatisticsService.getByProjectIdAndStatisticstypedataid(Id,
				1L);
		if (projectstatistics == null || projectstatistics.size() == 0) {
			projectService.staticAsync(Id, 1L, request, response);
			projectstatistics = projectstatisticsService.getByProjectIdAndStatisticstypedataid(Id, 1L);
		}
		if (projectstatistics != null && projectstatistics.size() > 0) {
			for (ProjectTagCompleteModel model : projectTags) {
				List<JProjecttags> tagsList = projecttagsService.getByTagId(model.get_id());
				double complete = tagsList.size() * 100.00 / projectstatistics.get(0).getCount();
				double comp = new BigDecimal(complete).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
				model.setTagComplete(comp + "%");
			}
		}
		BaseResponse baseResponse = new BaseResponse();
		resultMap.put("response", baseResponse);
		resultMap.put("columns", columns);
		resultMap.put("content", projectTags);
		String json = JSONObject.toJSONString(resultMap);
		log.info("response --> " + json);
		return json;

	}

	@ApiOperation(value = "获取标签整体进度")
	@RequestMapping(value = "/api/ProjectTag/Statistic", method = { RequestMethod.GET })
	public String GetStatistic(Long Id, HttpServletRequest request, HttpServletResponse response) {
		List<JProjectstatistics> projectstatistics = projectstatisticsService.getByProjectIdAndStatisticstypedataid(Id,
				1L);
		if (projectstatistics == null || projectstatistics.size() == 0) {
			projectService.staticAsync(Id, 1L, request, response);
			projectstatistics = projectstatisticsService.getByProjectIdAndStatisticstypedataid(Id, 1L);
		}
		JLPLog log = ThreadPoolConfig.getBaseContext();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		if (projectstatistics != null && projectstatistics.size() > 0) {
			List<JProjecttags> tagsList = projecttagsService.getByProjectId(Id);
			if (tagsList.size() > 0) {
				double complete = tagsList.size() * 100.00 / projectstatistics.get(0).getCount();
				double comp = new BigDecimal(complete).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
				resultMap.put("totalComplete", comp + "%");
			} else {
				resultMap.put("totalComplete", "0.00%");
			}

		} else {
			resultMap.put("totalComplete", "0.00%");
		}
		BaseResponse baseResponse = new BaseResponse();
		resultMap.put("response", baseResponse);
		String json = JSONObject.toJSONString(resultMap);
		log.info("response --> " + json);
		return json;
	}

	@ApiOperation(value = "新增课题标签信息")
	@RequestMapping(value = "/api/ProjectTag", method = { RequestMethod.POST })
	public String Post(@RequestBody ProjectTagModel projectTagModel, HttpServletRequest request,
			HttpServletResponse response) {
		JLPLog log = ThreadPoolConfig.getBaseContext();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String authorization = request.getHeader("Authorization") == null ? "509c6c1834414deeddd6c485d5c22d0b"
				: request.getHeader("Authorization");
		Long userId = userService.getIdByToken(authorization);
//		JUser user = (JUser) request.getSession().getAttribute("user");
		if (userId == null || userId.longValue() == 0) {
			BaseResponse baseResponse = new BaseResponse();
			response.setStatus(400);
			baseResponse.setCode("400");
			baseResponse.setText("用户信息获取失败，请检查请求头");
			resultMap.put("response", baseResponse);
			String json = JSONObject.toJSONString(resultMap);
			log.error("response-->" + json);
			return json;
		}
		JProject project = projectService.getOne(projectTagModel.getProjectId());
		if (project == null || project.getUserid().longValue() != userId.longValue()) {
			BaseResponse baseResponse = new BaseResponse();
			response.setStatus(404);
			baseResponse.setCode("404");
			baseResponse.setText("课题信息不存在");
			resultMap.put("response", baseResponse);
			return JSONObject.toJSONString(resultMap);
		}

		JTagdicts tagdcit = new JTagdicts();
		tagdcit.setActiveflag(JLPConts.ActiveFlag);
		tagdcit.setCreatedate(new Date());
		tagdcit.setDescription("");
		tagdcit.setName(projectTagModel.getName());
		tagdcit.setProjectid(projectTagModel.getProjectId());
		boolean showFlag = projectTagModel.getIsShow() == null ? false : projectTagModel.getIsShow();
		tagdcit.setShowflag(showFlag ? "1" : "0");
		tagdcit.setTagattached(projectTagModel.getTagLevel());
		tagdcit.setTagtype("1");
		tagdcit.setUserid(userId);
		tagdcit.setValuetype(projectTagModel.getFieldType());
		long seqId = jlpService.getNextSeq("J_TAGDICT");
		tagdcit.setId(seqId);
		tagdictService.save(tagdcit);
		if ("1".equals(projectTagModel.getFieldType())) {
			String[] values = projectTagModel.getFieldValue().split("/");
			if (values == null || values.length == 0) {
				BaseResponse baseResponse = new BaseResponse();
				response.setStatus(404);
				baseResponse.setCode("404");
				baseResponse.setText("标签值信息错误");
				resultMap.put("response", baseResponse);
				return JSONObject.toJSONString(resultMap);
			}

			for (String item : values) {
				JTagvaluedicts tagvaluedcit = new JTagvaluedicts();
				tagvaluedcit.setActiveflag(JLPConts.ActiveFlag);
				tagvaluedcit.setCreatedate(new Date());
				tagvaluedcit.setValue(item);
				tagvaluedcit.setTagid(tagdcit.getId());
				tagvaluedcit.setValuetype(projectTagModel.getFieldType());
				long valueId = jlpService.getNextSeq("J_TAGVALUEDICT");
				tagvaluedcit.setId(valueId);
				tagvaluedictService.save(tagvaluedcit);
			}
		} else if ("2".equals(projectTagModel.getFieldType())) {

			JTagvaluedicts tagvaluedcit = new JTagvaluedicts();
			tagvaluedcit.setActiveflag(JLPConts.ActiveFlag);
			tagvaluedcit.setCreatedate(new Date());
			tagvaluedcit.setValue(projectTagModel.getFieldValue());
			tagvaluedcit.setTagid(tagdcit.getId());
			tagvaluedcit.setValuetype(projectTagModel.getFieldType());
			long valueId = jlpService.getNextSeq("J_TAGVALUEDICT");
			tagvaluedcit.setId(valueId);
			tagvaluedictService.save(tagvaluedcit);

		}
		Map<String, Long> tagInfo = new HashMap<>();
		tagInfo.put("id", tagdcit.getId());
		resultMap.put("projectTag", tagInfo);
		BaseResponse baseResponse = new BaseResponse();
		resultMap.put("response", baseResponse);
		String json = JSONObject.toJSONString(resultMap);
		log.info("response --> " + json);
		return json;
	}

	@Transactional
	@ApiOperation(value = "标签显示状态变化")
	@RequestMapping(value = "/api/ProjectTag/TagShow", method = { RequestMethod.POST })
	public String TagShow(@RequestBody ProjectTagShowModel[] projectTagShows, HttpServletRequest request,
			HttpServletResponse response) {
		JLPLog log = ThreadPoolConfig.getBaseContext();
		Map<String, Object> resultMap = new HashMap<String, Object>();

		if (projectTagShows == null || projectTagShows.length == 0) {
			BaseResponse baseResponse = new BaseResponse();
			resultMap.put("response", baseResponse);
			String json = JSONObject.toJSONString(resultMap);
			log.info("response --> " + json);
			return json;
		}
		Set<Long> Ids = new HashSet<Long>();
		long projectId = 0;
		boolean IsChange = false;
		for (ProjectTagShowModel model : projectTagShows) {
			Ids.add(model.getId());
			JTagdicts dicts = tagdictService.getOne(model.getId());
			if (dicts != null) {
				if (projectId == 0 && (dicts.getProjectid() != null)) {
					projectId = dicts.getProjectid();
				}
				if (ObjectUtil.objToBool(dicts.getShowflag(), false) != model.getIsShow()) {
					boolean showFlag = model.getIsShow() == null ? false : model.getIsShow();
					dicts.setShowflag(showFlag ? "1" : "0");
					IsChange = true;
					tagdictService.update(dicts);
				}
			}
		}

		if (IsChange) {
			String sql = "update j_projectdatastatus set changestatus='1' where" + " projectid=" + projectId;
			jlpService.excuteSql(sql, new Object[] {});
		}
		BaseResponse baseResponse = new BaseResponse();
		resultMap.put("response", baseResponse);
		String json = JSONObject.toJSONString(resultMap);
		log.info("response --> " + json);
		return json;

	}

	@ApiOperation(value = "移除标签值信息")
	@RequestMapping(value = "/api/ProjectTag/{Id}", method = { RequestMethod.DELETE })
	public String Delete(@PathVariable Long Id, HttpServletRequest request, HttpServletResponse response) {
		JLPLog log = ThreadPoolConfig.getBaseContext();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String authorization = request.getHeader("Authorization") == null ? "" : request.getHeader("Authorization");
		Long userId = userService.getIdByToken(authorization);
//		JUser user = (JUser) request.getSession().getAttribute("user");
		if (userId == null || userId.longValue() == 0) {
			BaseResponse baseResponse = new BaseResponse();
			response.setStatus(400);
			baseResponse.setCode("400");
			baseResponse.setText("用户信息获取失败，请检查请求头");
			resultMap.put("response", baseResponse);
			return JSONObject.toJSONString(resultMap);
		}
		JTagdicts tagdict = tagdictService.getOne(Id);
		if (tagdict == null || tagdict.getUserid().longValue() != userId.longValue()) {
			BaseResponse baseResponse = new BaseResponse();
			response.setStatus(404);
			baseResponse.setCode("404");
			baseResponse.setText("未找到有效的标签信息");
			resultMap.put("response", baseResponse);
			return JSONObject.toJSONString(resultMap);
		}
		tagdict.setActiveflag(JLPConts.Inactive);
		tagdictService.update(tagdict);
		BaseResponse baseResponse = new BaseResponse();
		resultMap.put("response", baseResponse);
		String json = JSONObject.toJSONString(resultMap);
		log.info("response --> " + json);
		return json;
	}

}
