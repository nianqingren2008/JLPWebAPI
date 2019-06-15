package com.callan.service.provider.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.callan.service.provider.config.JLPConts;
import com.callan.service.provider.config.JLPLog;
import com.callan.service.provider.config.ThreadPoolConfig;
import com.callan.service.provider.pojo.ControllerBaseResponse;
import com.callan.service.provider.pojo.base.BaseResponse;
import com.callan.service.provider.pojo.db.JDownloadfile;
import com.callan.service.provider.pojo.db.JQueryrecord;
import com.callan.service.provider.pojo.db.JQueryrecordDetails;
import com.callan.service.provider.pojo.db.JTableFieldDict;
import com.callan.service.provider.pojo.db.JUser;
import com.callan.service.provider.pojo.task.JTask;
import com.callan.service.provider.pojo.task.JTaskProjectModel;
import com.callan.service.provider.pojo.task.JTaskdownload;
import com.callan.service.provider.pojo.task.TaskModel;
import com.callan.service.provider.service.IJQueryrecordDetailService;
import com.callan.service.provider.service.IJTableFieldDictService;
import com.callan.service.provider.service.IJTaskService;
import com.callan.service.provider.service.IJTaskdownloadService;
import com.callan.service.provider.service.IJUserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(description = "下载管理")
public class TaskController {

//	Log log = LogFactory.getLog(TaskController.class);
	@Autowired
	private IJTaskService jTaskService;
	@Autowired 
	private IJTaskdownloadService jTaskdownloadService;
	@Autowired
	private IJQueryrecordDetailService queryrecordDetailService;
	@Autowired
	private IJUserService userService;
	
	@ApiOperation(value = "下载任务提交")
	@RequestMapping(value = "/api/Task/Project/DownLoad", method = { RequestMethod.POST })
	public String taskDownLoad(JTaskProjectModel taskProject,HttpSession session) {
		JLPLog log = ThreadPoolConfig.getBaseContext();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		ControllerBaseResponse response = new ControllerBaseResponse();
		JUser user = (JUser) session.getAttribute("user");
		JTask task = new JTask();
		task.setActiveflag(JLPConts.ActiveFlag);  
		task.setCreatedate(new Date());
		task.setName(taskProject.getName());
		task.setProgress("0.00");
		task.setStatus("1");
		task.setTasktype(taskProject.getExportType()+"");
		task.setUserid(user.getId());
		
		JTaskdownload taskdownload = new JTaskdownload();
		taskdownload.setActiveflag("");
		taskdownload.setCreatedate(new Date());
//	                Exportfields = taskProject.queryShowFields.Where(x => int.TryParse(x, out int result)).ToArray().ToStringEx(),
		taskdownload.setProjectid(taskProject.getProjectId()); 
		taskdownload.setProjectstatuses(taskProject.getProjectstatusIDs().toString());
		taskdownload.setFiletypeid(taskProject.getFileTypeId());
		taskdownload.setDataexportclass((short)taskProject.getDataExportClass());
		taskdownload.setImageclass(taskProject.getModalities().toString());
		taskdownload.setImageexportclass((short)taskProject.getImageExportClass());
		taskdownload.setTagtranspose(taskProject.getTagTransposes().toString());
		if(taskProject.getQueryConds()!=null&&taskProject.getQueryConds().size()>0) {
			
			 JQueryrecord queryrecord = new JQueryrecord();
			 queryrecord.setActiveflag(JLPConts.ActiveFlag);   
			 queryrecord.setCount(0L);
			 queryrecord.setCreatedate(new Date());
			 queryrecord.setQueryname("");
			 queryrecord.setUserid(user.getId());
			 queryrecord.setSortno(1);
			 for(int i=0;i< taskProject.getQueryConds().size();i++)
			 {
				 JQueryrecordDetails  queryrecorddetails = new JQueryrecordDetails();
				 queryrecorddetails.setActiveflag(JLPConts.ActiveFlag);
				 queryrecorddetails.setCreatedate(new Date());
				 queryrecorddetails.setId(i+1L);
				 queryrecorddetails.setFieldname(taskProject.getQueryConds().get(i).getCondition());
				 queryrecorddetails.setFieldvalue(taskProject.getQueryConds().get(i).getCondValue());
				 queryrecorddetails.setFieldvaluetype(taskProject.getQueryConds().get(i).getFieldType());
				 queryrecorddetails.setLeftbrackets(taskProject.getQueryConds().get(i).getLeftqueto());
				 queryrecorddetails.setLogicaltype(taskProject.getQueryConds().get(i).getCombinator());
				 queryrecorddetails.setQueryid(queryrecord.getId());
				 queryrecorddetails.setRelationtype(taskProject.getQueryConds().get(i).getRelation());
				 queryrecorddetails.setRightbrackets(taskProject.getQueryConds().get(i).getRightqueto());
				 queryrecorddetails.setUpdatedate(new Date());
				 try {
//						jTaskService.addQueryrecorddetails(queryrecorddetails);
					 queryrecordDetailService.save(queryrecorddetails);
					}catch(Exception e) {
						log.error("添加查询记录详细信息失败",e);
						response.getResponse().setCode("400");
						response.getResponse().setText(e.getMessage());
						return response.toJsonString();
					}
			 }
			 taskdownload.setQueryid(queryrecord.getId());  
			
		}
		
		taskdownload.setTaskid(task.getId());  
//		 orclJlpContext.JTasks.Add(task);
//         orclJlpContext.JTaskdownloads.Add(taskdownload);
//         orclJlpContext.SaveChanges();
		 try {
				jTaskService.addTask(task);
			}catch(Exception e) {
				log.error("添加任务失败",e);
				response.getResponse().setCode("400");
				response.getResponse().setText(e.getMessage());
				return response.toJsonString();
			}
		 try {
				jTaskService.addTaskdownloads(taskdownload);
			}catch(Exception e) {
				log.error("添加下载任务失败",e);
				response.getResponse().setCode("400");
				response.getResponse().setText(e.getMessage());
				return response.toJsonString();
			}

		 TaskModel taskRet = new  TaskModel();
		 taskRet.setCreateDate(new Date());
		 taskRet.setId(task.getId());
		 taskRet.setName(task.getName());
		 taskRet.setProgress(task.getProgress()+"%");
		 taskRet.setStatus(task.getStatus());
		 taskRet.setTaskType(task.getTasktype());
		 resultMap.put("task", taskRet);
		 String json = JSONObject.toJSONString(resultMap);
		 return json;
		
	}
	
	@ApiOperation(value = "删除任务")
	@RequestMapping(value = "/api/Task/", method = { RequestMethod.DELETE })
	public String  deleteTask(Long id){
		JLPLog log = ThreadPoolConfig.getBaseContext();
		ControllerBaseResponse response = new ControllerBaseResponse();
		IJTaskService base = (IJTaskService) AopContext.currentProxy();
		Map<Long,JTask> data = (Map<Long,JTask>) base.getAll4Id().getData();
		JTask jtask = data.get(id);
		if(jtask!=null) {
			try {
				jTaskService.delete(id);
			}catch(Exception e) {
//				log.error("添加下载任务失败",e);
				response.getResponse().setCode("400");
				response.getResponse().setText(e.getMessage());
				return response.toJsonString();
			}
		}
		else{
			response.getResponse().setCode("400");
			response.getResponse().setText("未找到id="+id+"的任务!");
			return response.toJsonString();
		}
		return response.toJsonString();
	}
	
	@ApiOperation(value = "获取后台任务列表")
	@RequestMapping(value = "/api/Task", method = { RequestMethod.GET })
	public String  getTaskList(String pageNum, String pageSize, HttpServletRequest request){
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
		List<JTask> list = jTaskService.getByUserId(userId);
		String urlHost =  request.getRequestURL().toString();
		urlHost = urlHost.substring(0, urlHost.indexOf("/api/")) + "/api/DownLoad/";
	    List<JTask> jtaskList = new ArrayList<JTask>();
	    if(list!=null&&list.size()>0) {
	    	for(JTask jtask : list) {
	    		if("3".equals(jtask.getStatus()))
	    		{
	    		    List<JTaskdownload> downLoadList = jTaskService.getAllDowndLoad();
	    			if(downLoadList!=null&&downLoadList.size()>0) {
	    				for(JTaskdownload jTaskdownload : downLoadList) {
	    					if(jtask.getId()==jTaskdownload.getTaskid()) {
		    					JDownloadfile downloadfile = jTaskService.getDownloadfileById(jTaskdownload.getFileid());
		    					jTaskdownload.setUrl(urlHost+downloadfile.getId()+ "?fileCode="+downloadfile.getFilemd5());
//		    					useDownLoadList.add(jTaskdownload);
		    					jtask.setFileurl(jTaskdownload.getUrl());
	    					}
	    				}
	    			}
	    		}
	    	}
	    }
	    
	    List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		for (JTask task : jtaskList) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", task.getId());
			map.put("name", task.getName());
			map.put("progress",  Double.valueOf(task.getProgress()));
			map.put("taskType", task.getTasktype());
			map.put("endDate", task.getEnddate());
			map.put("startDate", task.getStartdate());
			map.put("fileUrl", task.getFileurl());
			resultList.add(map);
		}
		 
		resultMap.put("response", baseResponse);
		resultMap.put("tasks", resultList);
		String json = JSONObject.toJSONString(resultMap);
		log.info("response : " + json);
		return json;
	}
	
	@ApiOperation(value = "获取任务詳情")
	@RequestMapping(value = "/api/Task/detail", method = { RequestMethod.GET })
	public String  queryTask(Long id, HttpServletRequest request){
		JLPLog log = ThreadPoolConfig.getBaseContext();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		BaseResponse baseResponse = new BaseResponse();
		Map<String, Object> map = new HashMap<String, Object>();
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
		log.info("  id :"+ id+  "   userId : " + userId);
		JTask task = jTaskService.getByIdAndUserId(id,userId);
		if(task!=null) {
			
			if("3".equals(task.getStatus())) {
				IJTaskdownloadService base = (IJTaskdownloadService) AopContext.currentProxy();
				List<JTaskdownload> jtaskdownloadtempList = new ArrayList<JTaskdownload>();
				List<JTaskdownload> jtaskdownloadList = (List<JTaskdownload>)base.getAll().getData();
				 for(JTaskdownload jtaskdownlaod:jtaskdownloadList) {
					 if(jtaskdownlaod.getTaskid().equals(task.getId())) {
						 jtaskdownloadtempList.add(jtaskdownlaod);
						 
					 }
				 }
				 
				 map.put("fileUrl", jtaskdownloadtempList.get(0).getFileid().toString());
			}
			else {
				map.put("id", task.getId());
				map.put("createDate", task.getCreatedate());
				map.put("name",task.getName());
				map.put("progress",   task.getProgress()+"%");
				map.put("status", task.getStatus());
				map.put("taskType", task.getTasktype());
				map.put("startDate", task.getStartdate());
				map.put("endDate", task.getEnddate());
			}
		}
		else {
			baseResponse.setCode("0000");
			baseResponse.setText("获取任务詳情失败，请检查请求头");
			resultMap.put("response", baseResponse);
			return JSONObject.toJSONString(resultMap);
		}
		 
		resultMap.put("response", new BaseResponse());
		resultMap.put("task", map);
		String json = JSONObject.toJSONString(resultMap);
		log.info("response : " + json);
		return json;
	}
}
