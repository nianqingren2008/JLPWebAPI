package com.callan.service.provider.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.callan.service.provider.pojo.db.JProject;
import com.callan.service.provider.pojo.db.JProjectdeldata;
import com.callan.service.provider.pojo.project.ProjectDataItemModel;
import com.callan.service.provider.pojo.project.ProjectDataModel;
import com.callan.service.provider.pojo.tableclassdict.JTableclassdict;
import com.callan.service.provider.service.IJLpService;
import com.callan.service.provider.service.IJProjectDeldataService;
import com.callan.service.provider.service.IJProjectService;
import com.callan.service.provider.service.IJTableclassdictService;
import com.callan.service.provider.service.IJUserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(description = "课题数据管理")
public class ProjectDataController {
	
	@Autowired
	private IJUserService userService;
	
	@Autowired
	private IJProjectService projectService;
	
	@Autowired
	private IJLpService jlpService;
	
	@Autowired
	private IJTableclassdictService tableclassdictService;
	
	@Autowired
	private IJProjectDeldataService projectDeldataService;
	
	@ApiOperation(value = "删除课题数据")
	@RequestMapping(value = "/api/ProjectData", method = { RequestMethod.POST })
	 /// <summary>
    public String Delete(@RequestBody ProjectDataModel projectData
    		,HttpServletRequest request
    		,HttpServletResponse response) {
		
		JLPLog log = ThreadPoolConfig.getBaseContext();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String authorization = request.getHeader("Authorization") == null ? "" : request.getHeader("Authorization");
		
        if (projectData.getItems() == null || projectData.getItems().size()==0)  {
        	BaseResponse baseResponse = new BaseResponse();
			response.setStatus(404);
			baseResponse.setCode("404");
			baseResponse.setText("无有效数据");
			resultMap.put("response", baseResponse);
			return JSONObject.toJSONString(resultMap);
        }
        Long userId = userService.getIdByToken(authorization);
        if (userId == null || userId.longValue() == 0) {
			BaseResponse baseResponse = new BaseResponse();
			response.setStatus(400);
			baseResponse.setCode("400");
			baseResponse.setText("用户信息获取失败，请检查请求头");
			resultMap.put("response", baseResponse);
			return JSONObject.toJSONString(resultMap);
		}
        
        JProject project = projectService.getOne(projectData.getProjectId());
        if(project == null || project.getUserid().longValue() != userId) {
        	BaseResponse baseResponse = new BaseResponse();
			response.setStatus(404);
			baseResponse.setCode("404");
			baseResponse.setText("未发现此课题项目");
			resultMap.put("response", baseResponse);
			return JSONObject.toJSONString(resultMap);
        }
        
        JTableclassdict tableclassdict = tableclassdictService.getOne(projectData.getTableClassId());

        if (tableclassdict == null) {
        	BaseResponse baseResponse = new BaseResponse();
			response.setStatus(404);
			baseResponse.setCode("404");
			baseResponse.setText("未发现此业务分类ID");
			resultMap.put("response", baseResponse);
			return JSONObject.toJSONString(resultMap);
        }
        List<ProjectDataItemModel> itemList = projectData.getItems();
        List<Long> dataids = new ArrayList<Long>();
        for(ProjectDataItemModel model : itemList) {
        	dataids.add(model.getDataId());
        }
        
        List<JProjectdeldata> projectdeldatas = new ArrayList<JProjectdeldata>();
        for(Long dataId : dataids) {
        	List<JProjectdeldata> list = projectDeldataService.getByProjectIdAndTableId(projectData.getProjectId(),projectData.getTableClassId(),dataId);
        	if(list != null) {
        		projectdeldatas.addAll(list);
        	}
        }
        Set<JProjectdeldata> activeData = new HashSet<JProjectdeldata>();
        Set<JProjectdeldata> inactiveData = new HashSet<JProjectdeldata>();
        
        Set<Long> newDataId = new HashSet<Long>();
        
        for(JProjectdeldata projectdeldata : projectdeldatas) {
        	if(JLPConts.ActiveFlag.equals(projectdeldata.getActiveflag())) {
        		activeData.add(projectdeldata);
        	}
        	if(JLPConts.Inactive.equals(projectdeldata.getActiveflag())) {
        		inactiveData.add(projectdeldata);
        	}
        	if(!dataids.contains(projectdeldata.getDataid())) {
        		newDataId.add(projectdeldata.getDataid());
        	}
        }

        JProjectdeldata projectdeldata = null;

        for (Long item : newDataId)
        {
        	ProjectDataItemModel tempData = null;
        	for(ProjectDataItemModel model : projectData.getItems()) {
        		if(model.getDataId().longValue() == item.longValue()) {
        			tempData = model;
        		}
        	}
            if (tempData == null) {
                continue;
            }

            projectdeldata = new JProjectdeldata();
            projectdeldata.setPatientglobalid(tempData.getPatientGlobalId());
            projectdeldata.setProjectid(projectData.getProjectId());
            projectdeldata.setTableid(projectData.getTableClassId());
            projectdeldata.setDataid(tempData.getDataId());
            projectdeldata.setUserid(userId);
            projectdeldata.setCreatedate(new Date());
            projectdeldata.setActiveflag(JLPConts.ActiveFlag);
            long seqId = jlpService.getNextSeq("");
            projectdeldata.setId(seqId);
            projectDeldataService.save(projectdeldata);
        }
        
        for(JProjectdeldata projectdeldata1 : inactiveData) {
        	if(JLPConts.Inactive.equals(projectdeldata1.getActiveflag())) {
        		projectdeldata1.setActiveflag(JLPConts.ActiveFlag);
        		projectDeldataService.update(projectdeldata1);
        	}
        }

        BaseResponse baseResponse = new BaseResponse();
		resultMap.put("response", baseResponse);
		String json = JSONObject.toJSONString(resultMap);
		log.info("response --> " + json);
		return json;
    }
}
