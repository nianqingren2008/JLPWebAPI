package com.callan.service.provider.controller;

import java.util.Date;

import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.callan.service.provider.pojo.ControllerBaseResponse;
import com.callan.service.provider.pojo.db.JUser;
import com.callan.service.provider.pojo.task.JQueryrecord;
import com.callan.service.provider.pojo.task.JQueryrecorddetails;
import com.callan.service.provider.pojo.task.JTask;
import com.callan.service.provider.pojo.task.JTaskProjectModel;
import com.callan.service.provider.pojo.task.JTaskdownload;
import com.callan.service.provider.service.IJTaskService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(description = "下载管理")
public class TaskController {

	Log log = LogFactory.getLog(TaskController.class);
	@Autowired
	private IJTaskService jTaskService;
	@ApiOperation(value = "下载任务提交")
	@RequestMapping(value = "/api/Task/Project/DownLoad", method = { RequestMethod.POST })
	public String taskDownLoad(JTaskProjectModel taskProject,HttpSession session) {
		ControllerBaseResponse response = new ControllerBaseResponse();
		JUser user = (JUser) session.getAttribute("user");
		JTask task = new JTask();
		task.setActiveflag("1"); //TOD  从静态变量取值
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
			 queryrecord.setActiveflag("");  //TOD  从静态变量取值
			 queryrecord.setCount(0);
			 queryrecord.setCreatedate(new Date());
			 queryrecord.setQueryname("");
			 queryrecord.setUserid(user.getId());
			 queryrecord.setSortno(1);
			 for(int i=0;i< taskProject.getQueryConds().size();i++)
			 {
				 JQueryrecorddetails  queryrecorddetails = new JQueryrecorddetails();
				 queryrecorddetails.setActiveflag("1");//TOD  从静态变量取值
				 queryrecorddetails.setCreatedate(new Date());
				 queryrecorddetails.setId(i+1);
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
						jTaskService.AddQueryrecorddetails(queryrecorddetails);
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
				jTaskService.AddTask(task);
			}catch(Exception e) {
				log.error("添加任务失败",e);
				response.getResponse().setCode("400");
				response.getResponse().setText(e.getMessage());
				return response.toJsonString();
			}
		 try {
				jTaskService.AddTaskdownloads(taskdownload);
			}catch(Exception e) {
				log.error("添加下载任务失败",e);
				response.getResponse().setCode("400");
				response.getResponse().setText(e.getMessage());
				return response.toJsonString();
			}

		 
		 return response.toJsonString();
		
	}
	
	@ApiOperation(value = "删除任务")
	@RequestMapping(value = "/api/Task/", method = { RequestMethod.DELETE })
	public String  deleteTask(Long id){
		ControllerBaseResponse response = new ControllerBaseResponse();
		try {
			jTaskService.DeleteTask(id);
		}catch(Exception e) {
			log.error("添加下载任务失败",e);
			response.getResponse().setCode("400");
			response.getResponse().setText(e.getMessage());
			return response.toJsonString();
		}
		
		return response.toJsonString();
		
	}
	
	@ApiOperation(value = "获取任务")
	@RequestMapping(value = "/api/Task/", method = { RequestMethod.GET })
	public String  queryTask(Long id){
		ControllerBaseResponse response = new ControllerBaseResponse();
		
		
		
		return response.toJsonString();
	}
}
