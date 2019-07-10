package com.callan.service.provider.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
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
import com.callan.service.provider.service.IJLpService;
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

	@Autowired
	private IJLpService jlpService;

	@Transactional
	@ApiOperation(value = "下载任务提交")
	@RequestMapping(value = "/api/Task/Project/Download", method = { RequestMethod.POST })
	public String taskDownLoad(@RequestBody JTaskProjectModel taskProject, HttpServletRequest request,
			HttpServletResponse response) {
		JLPLog log = ThreadPoolConfig.getBaseContext();
		Map<String, Object> resultMap = new HashMap<String, Object>();

		String authorization = request.getHeader("Authorization") == null ? "bc6ef9c43a0e5b25da87ca2ba948d3eb"
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
			log.error("response --> " + json);
			return json;
		}

		ControllerBaseResponse baseResponse = new ControllerBaseResponse();
		JTask task = new JTask();
		task.setActiveflag(JLPConts.ActiveFlag);
		task.setCreatedate(new Date());
		task.setName(taskProject.getName());
		task.setProgress("0.00");
		task.setStatus("1");
		task.setTasktype(taskProject.getExportType() + "");
		task.setUserid(userId);

		JTaskdownload taskdownload = new JTaskdownload();
		taskdownload.setActiveflag(JLPConts.ActiveFlag);
		taskdownload.setCreatedate(new Date());
		taskdownload.setExportfields(Arrays.toString(taskProject.getQueryShowFields()).substring(1,
				Arrays.toString(taskProject.getQueryShowFields()).length() - 1));
		taskdownload.setProjectid(taskProject.getProjectId());
		taskdownload.setProjectstatuses(taskProject.getProjectstatusIDs().toString());
		taskdownload.setFiletypeid(taskProject.getFileTypeId());
		taskdownload.setDataexportclass((short) taskProject.getDataExportClass());
		taskdownload.setImageclass(taskProject.getModalities().toString());
		taskdownload.setImageexportclass((short) taskProject.getImageExportClass());
		taskdownload.setTagtranspose(taskProject.getTagTransposes().toString());
		if (taskProject.getQueryConds() != null && taskProject.getQueryConds().size() > 0) {

			JQueryrecord queryrecord = new JQueryrecord();
			queryrecord.setActiveflag(JLPConts.ActiveFlag);
			queryrecord.setCount(0L);
			queryrecord.setCreatedate(new Date());
			queryrecord.setQueryname("");
			queryrecord.setUserid(userId);
			queryrecord.setSortno(1);
			for (int i = 0; i < taskProject.getQueryConds().size(); i++) {
				JQueryrecordDetails queryrecorddetails = new JQueryrecordDetails();
				queryrecorddetails.setActiveflag(JLPConts.ActiveFlag);
				queryrecorddetails.setCreatedate(new Date());
				queryrecorddetails.setId(i + 1L);
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
				} catch (Exception e) {
					log.error("添加查询记录详细信息失败", e);
					baseResponse.getResponse().setCode("400");
					baseResponse.getResponse().setText(e.getMessage());

					String json = baseResponse.toJsonString();
					log.error("response --> " + json);
					return json;

				}
			}
			taskdownload.setQueryid(queryrecord.getId());

		}
		long taskId = jlpService.getNextSeq("J_TASK");
		task.setId(taskId);
		long taskDownloadId = jlpService.getNextSeq("J_TASKDOWNLOAD");
		taskdownload.setId(taskDownloadId);

		taskdownload.setTaskid(task.getId());
		try {
			jTaskService.addTask(task);
		} catch (Exception e) {
			log.error("添加任务失败", e);
			baseResponse.getResponse().setCode("400");
			baseResponse.getResponse().setText(e.getMessage());
			String json = baseResponse.toJsonString();
			log.error("response --> " + json);
			return json;
		}
		try {
			jTaskService.addTaskdownloads(taskdownload);
		} catch (Exception e) {
			log.error("添加下载任务失败", e);
			baseResponse.getResponse().setCode("400");
			baseResponse.getResponse().setText(e.getMessage());
			String json = baseResponse.toJsonString();
			log.error("response --> " + json);
			return json;
		}

		TaskModel taskRet = new TaskModel();
		taskRet.setCreateDate(new Date());
		taskRet.setId(task.getId());
		taskRet.setName(task.getName());
		taskRet.setProgress(task.getProgress() + "%");
		taskRet.setStatus(task.getStatus());
		taskRet.setTaskType(task.getTasktype());
		resultMap.put("task", taskRet);
		resultMap.put("response", new BaseResponse());
		String json = JSONObject.toJSONStringWithDateFormat(resultMap, "yyyy-MM-dd HH:mm:ss",
				SerializerFeature.WriteDateUseDateFormat);
		log.info("response --> " + json);
		return json;

	}

	@Transactional
	@ApiOperation(value = "删除任务")
	@RequestMapping(value = "/api/Task/{Id}", method = { RequestMethod.DELETE })
	public String deleteTask(@PathVariable Long Id,HttpServletResponse resp) {
		JLPLog log = ThreadPoolConfig.getBaseContext();
		ControllerBaseResponse response = new ControllerBaseResponse();
		JTask jtask = jTaskService.getOne(Id);;
		if (jtask != null) {
			try {
				jTaskService.delete(Id);
			} catch (Exception e) {
//				log.error("添加下载任务失败",e);
				resp.setStatus(400);
				response.getResponse().setCode("400");
				response.getResponse().setText(e.getMessage());
				String json = response.toJsonString();
				log.error("response --> " + json);
				return json;
			}
		} else {
			resp.setStatus(400);
			response.getResponse().setCode("400");
			response.getResponse().setText("未找到id=" + Id + "的任务!");
			String json = response.toJsonString();
			log.error("response --> " + json);
			return json;
		}
		return response.toJsonString();
	}

	@ApiOperation(value = "获取后台任务列表")
	@RequestMapping(value = "/api/Task", method = { RequestMethod.GET })
	public String getTaskList(Integer pageNum, Integer pageSize, HttpServletRequest request) {
		JLPLog log = ThreadPoolConfig.getBaseContext();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		BaseResponse baseResponse = new BaseResponse();
		// 从前台header中获取token参数
		String authorization = request.getHeader("Authorization") == null ? "" : request.getHeader("Authorization");
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
		List<JTask> list = jTaskService.getByUserId(userId);
		List<JTask> pageList = new ArrayList<JTask>();
		if (pageNum == null || pageNum < 1) {
			pageNum = 1;
		}
		if (pageSize == null || pageSize < 1) {
			pageSize = 20;
		}
		if (list == null) {
			pageList = new ArrayList<JTask>();
		} else {
			for (int i = 0; i < list.size(); i++) {
				if (pageSize * (pageNum - 1) <= i && i < pageSize * pageNum) {
					pageList.add(list.get(i));
				}
			}
		}

		String urlHost = request.getRequestURL().toString();
		urlHost = urlHost.substring(0, urlHost.indexOf("/api/")) + "/api/DownLoad/";

		List<Long> taskIds = new ArrayList<Long>();
		for (JTask jtask : pageList) {
			if ("3".equals(jtask.getStatus())) {
				taskIds.add(jtask.getId());
			}
		}
		List<Map<String, Object>> taskRet = new ArrayList<>();

		for (JTask jtask : pageList) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", jtask.getId());
			map.put("name", jtask.getName());
			map.put("progress", jtask.getProgress());
			map.put("status", jtask.getStatus());
			map.put("taskType", jtask.getTasktype());
			map.put("endDate", jtask.getEnddate());
			map.put("startDate", jtask.getStartdate());

			List<JTaskdownload> downLoadList = jTaskService.getTaskDownloadByTaskId(jtask.getId());
			if (downLoadList != null && downLoadList.size() > 0) {
				for (JTaskdownload jTaskdownload : downLoadList) {
					if(jTaskdownload.getFileid() != null) {
						JDownloadfile downloadfile = jTaskService.getDownloadfileById(jTaskdownload.getFileid());
						if (downloadfile != null) {
							jtask.setFileurl(urlHost + downloadfile.getId() + "?fileCode=" + downloadfile.getFilemd5());
						}
					}
				}
			}
			map.put("fileUrl", jtask.getFileurl());
			taskRet.add(map);
		}

		resultMap.put("response", baseResponse);
		resultMap.put("tasks", taskRet);
		String json = JSONObject.toJSONString(resultMap);
		log.info("response : " + json);
		return json;
	}

	@ApiOperation(value = "获取任务詳情")
	@RequestMapping(value = "/api/Task/detail", method = { RequestMethod.GET })
	public String queryTask(Long id, HttpServletRequest request) {
		JLPLog log = ThreadPoolConfig.getBaseContext();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		BaseResponse baseResponse = new BaseResponse();
		Map<String, Object> map = new HashMap<String, Object>();
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
		log.info("  id :" + id + "   userId : " + userId);
		JTask task = jTaskService.getByIdAndUserId(id, userId);
		if (task != null) {

			if ("3".equals(task.getStatus())) {
				List<JTaskdownload> jtaskdownloadtempList = new ArrayList<JTaskdownload>();
				List<JTaskdownload> jtaskdownloadList = (List<JTaskdownload>) jTaskdownloadService.getAll().getData();
				for (JTaskdownload jtaskdownlaod : jtaskdownloadList) {
					if (jtaskdownlaod.getTaskid().equals(task.getId())) {
						jtaskdownloadtempList.add(jtaskdownlaod);

					}
				}

				map.put("fileUrl", jtaskdownloadtempList.get(0).getFileid().toString());
			} else {
				map.put("id", task.getId());
				map.put("createDate", task.getCreatedate());
				map.put("name", task.getName());
				map.put("progress", task.getProgress() + "%");
				map.put("status", task.getStatus());
				map.put("taskType", task.getTasktype());
				map.put("startDate", task.getStartdate());
				map.put("endDate", task.getEnddate());
			}
		} else {
			baseResponse.setCode("0000");
			baseResponse.setText("获取任务詳情失败，请检查请求头");
			resultMap.put("response", baseResponse);
			String json = JSONObject.toJSONString(resultMap);
			log.error("response --> " + json);
			return json;
		}

		resultMap.put("response", new BaseResponse());
		resultMap.put("task", map);
		String json = JSONObject.toJSONString(resultMap);
		log.info("response : " + json);
		return json;
	}
}
