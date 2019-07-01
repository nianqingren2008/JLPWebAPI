package com.callan.service.provider.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.callan.service.provider.config.JLPConts;
import com.callan.service.provider.config.JLPLog;
import com.callan.service.provider.config.ThreadPoolConfig;
import com.callan.service.provider.pojo.base.BaseResponse;
import com.callan.service.provider.pojo.db.JProjecttags;
import com.callan.service.provider.pojo.db.JTagdicts;
import com.callan.service.provider.pojo.project.ProjectTagDataModel;
import com.callan.service.provider.service.IJLpService;
import com.callan.service.provider.service.IJProjectService;
import com.callan.service.provider.service.IJProjecttagsService;
import com.callan.service.provider.service.IJTagdictService;
import com.callan.service.provider.service.IJUserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(description = "课题标签值")
public class ProjectTagDataController {
	@Autowired
	private IJUserService userService;

	@Autowired
	private IJTagdictService tagdictService;

	@Autowired
	private IJProjectService projectService;

	@Autowired
	private IJProjecttagsService projecttagsService;

	@Autowired
	private IJLpService jlpService;

	@ApiOperation(value = "新建或更新课题标签值")
	@RequestMapping(value = "/api/ProjectTagData", method = { RequestMethod.GET })
	public String Post(@RequestBody ProjectTagDataModel projectTagData, HttpServletRequest request,
			HttpServletResponse response) {
		if (projectTagData.getTagValue() == null) {
			projectTagData.setTagValue("");
		}

		JLPLog log = ThreadPoolConfig.getBaseContext();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String authorization = request.getHeader("Authorization") == null ? "" : request.getHeader("Authorization");
		Long userId = userService.getIdByToken(authorization);
		if (userId == null) {
			userId = 0L;
		}
		JTagdicts tagdict = tagdictService.getOne(projectTagData.getTagId());
		
		if (tagdict == null) {
			BaseResponse baseResponse = new BaseResponse();
			response.setStatus(404);
			baseResponse.setCode("404");
			baseResponse.setText("标签编号错误！");
			resultMap.put("response", baseResponse);
			return JSONObject.toJSONString(resultMap);
		}
		if (tagdict.getCurrentnum() == null) {
			tagdict.setCurrentnum(0L);
		}
		JProjecttags jProjecttags = projecttagsService.getByProIdAndDataIdAndTagId(projectTagData.getProjectId(),
				projectTagData.getDataId(), projectTagData.getTagId());

		if (jProjecttags == null) {
			jProjecttags = new JProjecttags();
			jProjecttags.setActiveflag(JLPConts.ActiveFlag);
			jProjecttags.setCreatedate(new Date());
			jProjecttags.setDataid(projectTagData.getDataId());
			jProjecttags.setProjectid(projectTagData.getProjectId());
			jProjecttags.setTableid(projectTagData.getTableClassId());
			jProjecttags.setTagid(projectTagData.getTagId());
			jProjecttags.setTagvalue(projectTagData.getTagValue());
			jProjecttags.setPatientglobalid(projectTagData.getPatientGlobalId());
			jProjecttags.setUserid(userId);
			long seqId = jlpService.getNextSeq("J_PROJECTTAGS");
			jProjecttags.setId(seqId);
			projecttagsService.save(jProjecttags);
			tagdict.setCurrentnum(tagdict.getCurrentnum() + 1);
			tagdictService.update(tagdict);
		} else {
			// TODO 这里逻辑需要确认一下
			if (!StringUtils.isNotBlank(jProjecttags.getTagvalue())
					&& StringUtils.isNotBlank(jProjecttags.getTagvalue()) && tagdict.getCurrentnum() > 0) {
				tagdict.setCurrentnum(tagdict.getCurrentnum() - 1);
				tagdictService.update(tagdict);
			}

			jProjecttags.setTagvalue(projectTagData.getTagValue());
			jProjecttags.setPatientglobalid(projectTagData.getPatientGlobalId());
			projecttagsService.update(jProjecttags);
		}

		resultMap.put("response", new BaseResponse());
		resultMap.put("tagInfo", jProjecttags.getId());
		String json = JSONObject.toJSONString(resultMap);
		log.info("response : " + json);
		return json;
	}
}
