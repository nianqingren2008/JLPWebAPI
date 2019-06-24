package com.callan.service.provider.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.callan.service.provider.config.ObjectUtil;
import com.callan.service.provider.config.ThreadPoolConfig;
import com.callan.service.provider.pojo.base.BaseResponse;
import com.callan.service.provider.pojo.db.JProject;
import com.callan.service.provider.pojo.project.JProjectdatastatusdict;
import com.callan.service.provider.pojo.project.ProjectModel;
import com.callan.service.provider.service.IJLpService;
import com.callan.service.provider.service.IJProjectService;
import com.callan.service.provider.service.IJUserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(description = "课题")
public class ProjectController {

	@Autowired
	private IJProjectService projectService;
	@Autowired
	private IJLpService jLpService;

	@Autowired
	private IJUserService userService;

//	@Autowired
//	private IJProjectDataStatusService projectDataStatusService;
	
	/**
	 * 获取课题列表
	 */
	@ApiOperation(value = "获取课题列表 / 新建或修改课题")
	@RequestMapping(value = "/api/Project", method = { RequestMethod.GET })
	public String getProjects(Boolean simple, HttpServletRequest request,@RequestBody ProjectModel project) {
		JLPLog log = ThreadPoolConfig.getBaseContext();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		if(project == null) {
		
		String authorization = request.getHeader("Authorization") == null ? "" : request.getHeader("Authorization");
		Long userId = userService.getIdByToken(authorization);
		List<JProject> list = projectService.getByUserId(userId);
		Collections.sort(list, new Comparator<JProject>() {
			@Override
			public int compare(JProject o1, JProject o2) {
				return o1.getCreatedate().compareTo(o2.getCreatedate());
			}
		});
		if (simple != null && simple) {
			List<Map<String, Object>> isdustries = new ArrayList<Map<String, Object>>();
			for (JProject pro : list) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("courseID", pro.getId());
				map.put("cateroy", pro.getProjecttype());
				map.put("courseName", pro.getProjectname());
				map.put("englishName", pro.getProjectname());
				isdustries.add(map);
			}
			resultMap.put("industries", isdustries);
		} else {
			List<ProjectModel> isdustries = new ArrayList<ProjectModel>();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			for (JProject pro1 : list) {
				ProjectModel proNew = new ProjectModel();
				proNew.setCourseID(pro1.getId());
				proNew.setCateroy(pro1.getProjecttype());
				proNew.setCourseName(pro1.getProjectenname());
				proNew.setDescription(pro1.getProjectdescribe());
				proNew.setEnglishName(pro1.getProjectenname());
				proNew.setFounders(pro1.getSponsor());
				proNew.setIsShared(ObjectUtil.objToBool(pro1.getSharetype(), false));
				proNew.setRegisterid(pro1.getProjectRegistNo());
				proNew.setImageUrl(pro1.getImageurl());
				if (pro1.getStartdate() != null && pro1.getEnddate() != null) {
					proNew.setTimeproject(
							new String[] { sdf.format(pro1.getStartdate()), sdf.format(pro1.getEnddate()) });
				}
				isdustries.add(proNew);
			}
			resultMap.put("industries", isdustries);
		}
		BaseResponse baseResponse = new BaseResponse();
		resultMap.put("response", baseResponse);
		String json = JSONObject.toJSONString(resultMap);
		log.info("response--> " + json);
		return json;
		}else {
			
			boolean IsNew = (project.getCourseID() == 0L);
			String authorization = request.getHeader("Authorization") == null ? "" : request.getHeader("Authorization");
			Long userId = userService.getIdByToken(authorization);
			if(userId == null) {
				userId = 0L;
			}
	        JProject jProject = null;
	        if (!IsNew)
	        {
	        	jProject = projectService.getOne(project.getCourseID());

				if (!JLPConts.ActiveFlag.equals(jProject.getActiveflag())
						|| userId.longValue() != jProject.getUserid().longValue()) {
					jProject = null;
				}
	            IsNew = (jProject == null);
	        }
	        if (IsNew)
	        {
	            jProject = new JProject();
	            jProject.setActiveflag("1");
	            jProject.setCreatedate(new Date());
	            jProject.setUserid(userId);
	            jProject.setDatastatus("0");
	        }
	        if (project.getTimeproject() != null && project.getTimeproject().length == 2)
	        {
	        	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	            try {
					jProject.setEnddate(sdf.parse(project.getTimeproject()[1]));
					jProject.setStartdate(sdf.parse(project.getTimeproject()[0]));
				} catch (ParseException e) {
					log.error(e);
				}
	        }
	        jProject.setEthicalrecordno(project.getEthicalnumber());
	        jProject.setProjectdescribe(project.getDescription());
	        jProject.setProjectenname(project.getEnglishName());
	        jProject.setProjectname(project.getCourseName());
	        jProject.setProjectRegistNo(project.getRegisterid());
	        jProject.setProjecttype(project.getCateroy());
	        jProject.setSharetype(project.getIsShared()+"");
	        jProject.setSponsor(project.getFounders());
	        jProject.setImageurl(project.getImageUrl());
	        jProject.setStatus("1");
	        jProject.setUpdatedate(new Date());

	        if (IsNew)
	        {
	            long seqId = jLpService.getNextSeq("J_PROJECT");
	            jProject.setId(seqId);
	            projectService.save(jProject);
	            
	            String[] tempDataStatus = new String[] { "未开始", "进行中", "已完成" };
	            for (String item : tempDataStatus)
	            {
	                JProjectdatastatusdict projectDataStatus = new JProjectdatastatusdict();
	                	projectDataStatus.setActiveflag(JLPConts.ActiveFlag);
	                	projectDataStatus.setCreatedate(new Date());
	                	projectDataStatus.setName(item);
	                	projectDataStatus.setProjectid(jProject.getId());
	                	projectDataStatus.setStatustype("1");
	                long projectDataStatusSeqId = jLpService.getNextSeq("J_PROJECTDATASTATUSDICT");
	                projectDataStatus.setId(projectDataStatusSeqId);
//	                projectDataStatusService.save(projectDataStatus);
	            }
	        }
	        else
	        {
	        	projectService.update(jProject);
	        }
	        ProjectModel projectRet = new ProjectModel();
	        projectRet.setCourseID(jProject.getId());
	        projectRet.setCateroy(jProject.getProjecttype());
	        projectRet.setCourseName(jProject.getProjectenname());
	        projectRet.setDescription(jProject.getProjectdescribe());
	        projectRet.setEnglishName(jProject.getProjectenname());
	        projectRet.setFounders(jProject.getSponsor());
	        projectRet.setIsShared(ObjectUtil.objToBool(jProject.getSharetype(), false));
	        projectRet.setRegisterid(jProject.getProjectRegistNo());
	        projectRet.setImageUrl(jProject.getImageurl());
			if (jProject.getStartdate() != null && jProject.getEnddate() != null) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				projectRet.setTimeproject(new String[] { sdf.format(jProject.getStartdate())
						, sdf.format(jProject.getEnddate()) });
			}

			BaseResponse baseResponse = new BaseResponse();
			resultMap.put("response", baseResponse);
			resultMap.put("project", projectRet);
			String json = JSONObject.toJSONString(resultMap);
			log.info("response--> " + json);
			return json;
		}
		}

		/**
		 * 获取课题详细信息
		 */
		@ApiOperation(value = "获取课题详细信息")
		@RequestMapping(value = "/api/Project/{Id}", method = { RequestMethod.GET })
		public String GetProject(Long Id, HttpServletRequest request, HttpServletResponse response) {
			JLPLog log = ThreadPoolConfig.getBaseContext();
			Map<String, Object> resultMap = new HashMap<String, Object>();
			String authorization = request.getHeader("Authorization") == null ? "" : request.getHeader("Authorization");
			Long userId = userService.getIdByToken(authorization);
			if (userId == null) {
				userId = 0L;
			}

			JProject project = projectService.getOne(Id);

			if (!JLPConts.ActiveFlag.equals(project.getActiveflag())
					|| userId.longValue() != project.getUserid().longValue()) {
				project = null;
			}
			if (project == null) {
				BaseResponse baseResponse = new BaseResponse();
				response.setStatus(400);
				baseResponse.setCode("400");
				baseResponse.setText("错误的课题编号");
				resultMap.put("response", baseResponse);
				return JSONObject.toJSONString(resultMap);
			}
			ProjectModel pro = new ProjectModel();
			pro.setCourseID(project.getId());
			pro.setCateroy(project.getProjecttype());
			pro.setCourseName(project.getProjectenname());
			pro.setDescription(project.getProjectdescribe());
			pro.setEnglishName(project.getProjectenname());
			pro.setFounders(project.getSponsor());
			pro.setIsShared(ObjectUtil.objToBool(project.getSharetype(), false));
			pro.setRegisterid(project.getProjectRegistNo());
			pro.setImageUrl(project.getImageurl());
			if (project.getStartdate() != null && project.getEnddate() != null) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				pro.setTimeproject(new String[] { sdf.format(project.getStartdate()), sdf.format(project.getEnddate()) });
			}

			BaseResponse baseResponse = new BaseResponse();
			resultMap.put("response", baseResponse);
			resultMap.put("project", pro);
			String json = JSONObject.toJSONString(resultMap);
			log.info("response--> " + json);
			return json;
		}
		
		
		

     

//        /// <summary>
//        /// 删除课题
//        /// </summary>
//        /// <param name="Id">课题编号</param>
//        /// <returns></returns>
//        public IHttpActionResult Delete(long Id)
//        {
//            long userId = ActionContext.GetUserId();
//            JProject jProject = orclJlpContext.JProjects.FirstOrDefault(x => x.Id == Id && x.Userid == userId && x.Activeflag == JLPStaticProperties.ActiveFlag);
//            if (jProject is null)
//            {
//                return Content(HttpStatusCode.BadRequest, ResponseModel.Error(Text: "错误的课题编号").ToDictionaryObj());
//            }
//            jProject.Activeflag = JLPStaticProperties.Inactive;
//            orclJlpContext.Entry(jProject).State = System.Data.Entity.EntityState.Modified;
//            orclJlpContext.SaveChanges();
//
//            DictionaryObj ret = new DictionaryObj();
//            ret.Add("response", ResponseModel.Ok());
//            return Ok(ret);
//        }
//
//
//        /// <summary>
//        /// 课题清除更改状态
//        /// </summary>
//        /// <param name="projectChangeStatus"></param>
//        /// <returns></returns>
//        [Route("clearChangeStatus")]
//        public IHttpActionResult Post([FromBody]ProjectChangeStatusModel projectChangeStatus)
//        {
//            long userId = ActionContext.GetUserId();
//            orclJlpContext.Database.ExecuteSqlCommand($"update J_PROJECTDATASTATUS set CHANGESTATUS='0' where PROJECTID={projectChangeStatus.projectId} and USERID={userId} and CHANGESTATUS='1'");
//            return Ok(ResponseModel.Ok().ToDictionaryObj());
//        }
//
//        /// <summary>
//        /// 获取课题导出信息
//        /// </summary>
//        /// <param name="Id">课题ID</param>
//        /// <returns></returns>
//        [Route("ExportInfo/{Id}")]
//        public IHttpActionResult Get(long Id)
//        {
//            long userId = base.ActionContext.GetUserId();
//
//            #region 纳排条件处理
//            List<string> includeSql = new List<string>();
//            List<string> excludeSql = new List<string>();
//
//            Dictionary<string, List<QueryCollectionModel>> queryList = new Dictionary<string, List<QueryCollectionModel>>();
//            ProjectQueryController projectQuery = new ProjectQueryController()
//            {
//                Configuration = new HttpConfiguration(),
//                Request = new HttpRequestMessage()
//                {
//                    Method = HttpMethod.Get,
//                    RequestUri = new Uri("http://localhost/JLPWebAPI/AdvancedQuery")
//                }
//            };
//            dynamic dynamicData = JsonConvert.DeserializeObject<dynamic>(projectQuery.Get(Id).ExecuteAsync(CancellationToken.None).Result.Content.ReadAsStringAsync().Result);
//            //queryRecord
//            AdvancedQueryRecordModel projectQueryConds = null;
//
//            if (dynamicData.response.Code == "1000")
//            {
//                projectQueryConds = JsonConvert.DeserializeObject<AdvancedQueryRecordModel>(dynamicData.queryRecord.ToString());
//            }
//            else
//            {
//                return Content(HttpStatusCode.NotFound, ResponseModel.Error(Text: "未找到该项目信息").ToDictionaryObj());
//            }
//            if (projectQueryConds?.queries?.queryIncludesEX?.includes != null)
//            {
//                queryList.Add("include", projectQueryConds.queries.queryIncludesEX.includes.ToList());
//            }
//
//            if (projectQueryConds?.queries?.queryIncludesEX?.excludes != null)
//            {
//                queryList.Add("exclude", projectQueryConds.queries?.queryIncludesEX.excludes.ToList());
//            }
//
//            if (projectQueryConds?.queries?.queryConds != null)
//            {
//                QueryCollectionModel queryCollection = new QueryCollectionModel()
//                {
//                    Conds = projectQueryConds.queries.queryConds,
//                    leftqueto = "(",
//                    rightqueto = ")",
//                    setCombinator = AdvancedQueryType.Intersect,
//                    id = "",
//                    type = ""
//                };
//                if (!queryList.ContainsKey("include"))
//                {
//                    queryList.Add("include", new List<QueryCollectionModel>());
//                }
//                var relQCL = queryList["include"];
//                if (relQCL.Count > 0)
//                {
//                    relQCL.First().leftqueto += "(";
//                    relQCL.Last().rightqueto += ")";
//                }
//                relQCL.Add(queryCollection);
//            }
//
//            string tempadvancedQueryType = string.Empty;
//            foreach (var item in queryList)
//            {
//                foreach (var subItem in item.Value)
//                {
//                    List<string> tempTableNames = new List<string>();
//                    List<string> tempFieldNames = new List<string>();
//
//                    tempFieldNames.Add(JLPStaticProperties.PatientGlobalTable + ".Id");
//
//                    List<List<QueryDetailModel>> queryDetails = new List<List<QueryDetailModel>>();
//
//                    if (subItem.Conds is null)
//                    {
//                        continue;
//                    }
//
//                    queryDetails.Add(subItem.Conds.ToList());
//
//                    #region 获取查询所有表名
//                    var tableNameWheres = from a in queryDetails
//                                          from b in a
//                                          select b.condition.Split('.').FirstOrDefault().ToUpper();
//                    var tableNameWhereValues = from a in queryDetails
//                                               from b in a
//                                               where b.fieldType == FieldValueType.Field
//                                               select b.condValue.Split('.').FirstOrDefault().ToUpper();
//                    var whereFields = from a in queryDetails
//                                      from b in a
//                                      select b.condition.ToUpper();
//
//                    var whereFieldTypes = (from a in orclJlpContext.JTabledicts
//                                           join b in orclJlpContext.JTablefielddicts on a.Code equals b.Tablecode into tablefields
//                                           from b in tablefields
//                                           where a.Activeflag == "1" && whereFields.Contains(a.Name + "." + b.Name)
//                                           select new { field = a.Name + "." + b.Name, type = b.Type }).Distinct().ToList();
//                    queryDetails.ForEach(x =>
//                    {
//                        x.ForEach(y =>
//                        {
//                            if (y.fieldType != FieldValueType.Field)
//                            {
//                                y.fieldType = (whereFieldTypes.FirstOrDefault(m => m.field == y.condition.ToUpper())?.type.toFieldValueType()) ?? FieldValueType.String;
//                            }
//                        });
//                        if (x.LastOrDefault() != null)
//                        {
//                            x.LastOrDefault().combinator = "";
//                        }
//                    });
//
//                    var SqlWhere = queryDetails.Select(x => x.ToArray().ToStringEx(" ")).ToArray().ToStringEx(" and ");
//                    tempTableNames.AddRange(tableNameWheres);
//                    tempTableNames.AddRange(tableNameWhereValues);
//                    tempTableNames = tempTableNames.Distinct().ToList();
//                    tempTableNames.RemoveAll(x => x.ToUpper() == JLPStaticProperties.PatientGlobalTable);
//                    #endregion
//
//                    #region 获取查询结果和数量
//                    tempFieldNames = tempFieldNames.Distinct().ToList();
//                    string tableWhere = tempTableNames.ToArray().ToTableStringEx();
//
//                    string TempSql = $"{subItem.leftqueto}select distinct {tempFieldNames.ToArray().ToStringEx(",")} from {tableWhere} {(string.IsNullOrEmpty(SqlWhere) ? "" : " where " + SqlWhere)} {subItem.rightqueto}";
//
//                    if (item.Key == "include")
//                    {
//                        if (includeSql.Count != 0)
//                        {
//                            includeSql.Add(tempadvancedQueryType);
//                        }
//                        includeSql.Add(TempSql);
//                    }
//                    else if (item.Key == "exclude")
//                    {
//                        if (excludeSql.Count != 0)
//                        {
//                            excludeSql.Add(tempadvancedQueryType);
//                        }
//                        excludeSql.Add(TempSql);
//                    }
//
//                    tempadvancedQueryType = subItem.setCombinator.ToString();
//                }
//                #endregion
//            }
//            #endregion
//
//            #region 组装SQL
//            StringBuilder includeStrBuilder = new StringBuilder();
//            includeSql.ForEach(x =>
//            {
//                includeStrBuilder.AppendLine(x + " ");
//            });
//
//            StringBuilder excludeStrBuilder = new StringBuilder();
//            excludeSql.ForEach(x =>
//            {
//                excludeStrBuilder.AppendLine(x + " ");
//            });
//            string tempSql = string.Empty;
//
//            if (includeStrBuilder.Length > 0)
//            {
//                tempSql = $"select Id from ({includeStrBuilder.ToString()})";
//                if (excludeStrBuilder.Length > 0)
//                {
//                    tempSql += $" minus ({excludeStrBuilder.ToString()})";
//                }
//            }
//            List<string> tableNames = new List<string>() { "D_EXAMINFO" };
//            string Sql = $"select distinct EXAMCLASS from {tableNames.ToArray().ToTableStringEx(advancedQueryWhere: tempSql)}";
//
//            string tempSqlWhere = string.Empty;
//            if (tempSqlWhere.Length == 0)
//            {
//                tempSqlWhere = $" where D_PATIENTGLOBAL.jlactiveflag='1'";
//            }
//            else
//            {
//                tempSqlWhere += $" and D_PATIENTGLOBAL.jlactiveflag='1'";
//            }
//
//            Sql += tempSqlWhere;
//            #endregion
//
//            var ImageClasses = orclJlpContext.Database.SqlQuery<string>(Sql).ToList();
//            var AllExportClasses = orclJlpContext.JExportdataclasses.Where(x => x.Activeglag == JLPStaticProperties.ActiveFlag).OrderBy(x => x.Type).ThenBy(x => x.Id)
//                                   .ToList().Select(x => new
//                                   {
//                                       id = x.Id,
//                                       title = x.Title,
//                                       info = x.Info,
//                                       type = x.Type
//                                   });
//            var dataExportClasses = AllExportClasses.Where(x => x.type == "1").Select(x => new { x.id, x.title, x.info });
//            var imageExportClasses = AllExportClasses.Where(x => x.type == "2").Select(x => new { x.id, x.title, x.info });
//            var fileTypes = orclJlpContext.JFiletypes.Where(x => x.Activeflag == JLPStaticProperties.ActiveFlag).ToList()
//                .Select(x => new { id = x.Id, name = x.Name });
//            DictionaryObj image = new DictionaryObj();
//            image.Add("imageExportClasses", imageExportClasses);
//            image.Add("imageClasses", ImageClasses);
//
//            DictionaryObj ret = ResponseModel.Ok().ToDictionaryObj();
//            ret.Add("filetypes", fileTypes);
//            ret.Add("exportTypes", new[]
//            {
//                new { id=1,title="导出到文件",info="生成可下载文件名" },
//                new { id=2,title="导出到统计分析",info="生成在线统计分析数据库" }
//            });
//            ret.Add("dataExportClasses", dataExportClasses);
//            ret.Add("image", image);
//            return Ok(ret);
//        }
//    }
//	}
}
