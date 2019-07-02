package com.callan.service.provider.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.callan.service.provider.config.JLPConts;
import com.callan.service.provider.config.JLPLog;
import com.callan.service.provider.config.ObjectUtil;
import com.callan.service.provider.config.ThreadPoolConfig;
import com.callan.service.provider.pojo.advanceQueryBase.AdvancedQueryRecordModel;
import com.callan.service.provider.pojo.advanceQueryBase.QueryDetailModel;
import com.callan.service.provider.pojo.advanceQueryBase.QueryCollectionModel;
import com.callan.service.provider.pojo.base.BaseResponse;
import com.callan.service.provider.pojo.db.JExportdataclass;
import com.callan.service.provider.pojo.db.JFiletype;
import com.callan.service.provider.pojo.db.JProject;
import com.callan.service.provider.pojo.db.JProjectdatastatusdict;
import com.callan.service.provider.pojo.project.ProjectChangeStatusModel;
import com.callan.service.provider.pojo.project.ProjectModel;
import com.callan.service.provider.service.IJExportdataclassService;
import com.callan.service.provider.service.IJFiletypeService;
import com.callan.service.provider.service.IJLpService;
import com.callan.service.provider.service.IJProjectDataStatusService;
import com.callan.service.provider.service.IJProjectDataStatusdictService;
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

	@Autowired
	private IJProjectDataStatusdictService projectDataStatusdictService;

	@Autowired
	private IJExportdataclassService exportdataclassService;

	@Autowired
	private IJFiletypeService filetypeService;

	/**
	 * 获取课题列表
	 */
	@ApiOperation(value = "获取课题列表")
	@RequestMapping(value = "/api/Project", method = { RequestMethod.GET })
	public String getProjects(HttpServletRequest request,String simple,HttpServletResponse response) {
		JLPLog log = ThreadPoolConfig.getBaseContext();
		Map<String, Object> resultMap = new HashMap<String, Object>();

		String authorization = request.getHeader("Authorization") == null ? "20a3b08fe9503604f6eabaa357ad72aa" : request.getHeader("Authorization");
		Long userId = userService.getIdByToken(authorization);
		if (userId == null) {
			BaseResponse baseResponse = new BaseResponse();
			response.setStatus(400);
			baseResponse.setCode("400");
			baseResponse.setText("用户信息获取失败，请检查请求头");
			resultMap.put("response", baseResponse);
			return JSONObject.toJSONString(resultMap);
		}
		List<JProject> list = projectService.getByUserId(userId);
		Collections.sort(list, new Comparator<JProject>() {
			@Override
			public int compare(JProject o1, JProject o2) {
				return o1.getCreatedate().compareTo(o2.getCreatedate());
			}
		});
		if (simple != null && ObjectUtil.objToBool(simple, false)) {
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
				proNew.setRegisterid(pro1.getProjectregistno());
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
	}

	/**
	 * 新建或修改课题
	 */
	@ApiOperation(value = "新建或修改课题")
	@RequestMapping(value = "/api/Project", method = { RequestMethod.POST })
	public String getProjectsModify(HttpServletRequest request, @RequestBody ProjectModel project,HttpServletResponse response) {
		JLPLog log = ThreadPoolConfig.getBaseContext();
		Map<String, Object> resultMap = new HashMap<String, Object>();

		boolean IsNew = (project.getCourseID() == 0L);
		String authorization = request.getHeader("Authorization") == null ? "bc6ef9c43a0e5b25da87ca2ba948d3eb" : request.getHeader("Authorization");
		Long userId = userService.getIdByToken(authorization);
		if (userId == null) {
			BaseResponse baseResponse = new BaseResponse();
			response.setStatus(400);
			baseResponse.setCode("400");
			baseResponse.setText("用户信息获取失败，请检查请求头");
			resultMap.put("response", baseResponse);
			return JSONObject.toJSONString(resultMap);
		}
		JProject jProject = null;
		if (!IsNew) {
			jProject = projectService.getOne(project.getCourseID());

			if (!JLPConts.ActiveFlag.equals(jProject.getActiveflag())
					|| userId.longValue() != jProject.getUserid().longValue()) {
				jProject = null;
			}
			IsNew = (jProject == null);
		}
		if (IsNew) {
			jProject = new JProject();
			jProject.setActiveflag("1");
			jProject.setCreatedate(new Date());
			jProject.setUserid(userId);
			jProject.setDatastatus("0");
		}
		if (project.getTimeproject() != null && project.getTimeproject().length == 2) {
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
		jProject.setProjectregistno(project.getRegisterid());
		jProject.setProjecttype(project.getCateroy());
		jProject.setSharetype(project.getIsShared() ? "1" : "0");
		jProject.setSponsor(project.getFounders());
		jProject.setImageurl(project.getImageUrl());
		jProject.setStatus("1");
		jProject.setUpdatedate(new Date());

		if (IsNew) {
			long seqId = jLpService.getNextSeq("J_PROJECT");
			jProject.setId(seqId);
			projectService.save(jProject);

			String[] tempDataStatus = new String[] { "未开始", "进行中", "已完成" };
			for (String item : tempDataStatus) {
				JProjectdatastatusdict projectDataStatus = new JProjectdatastatusdict();
				projectDataStatus.setActiveflag(JLPConts.ActiveFlag);
				projectDataStatus.setCreatedate(new Date());
				projectDataStatus.setName(item);
				projectDataStatus.setProjectid(jProject.getId());
				projectDataStatus.setStatustype("1");
				long projectDataStatusSeqId = jLpService.getNextSeq("J_PROJECTDATASTATUSDICT");
				projectDataStatus.setId(projectDataStatusSeqId);
				projectDataStatusdictService.save(projectDataStatus);
			}
		} else {
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
		projectRet.setRegisterid(jProject.getProjectregistno());
		projectRet.setImageUrl(jProject.getImageurl());
		if (jProject.getStartdate() != null && jProject.getEnddate() != null) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			projectRet.setTimeproject(
					new String[] { sdf.format(jProject.getStartdate()), sdf.format(jProject.getEnddate()) });
		}

		BaseResponse baseResponse = new BaseResponse();
		resultMap.put("response", baseResponse);
		resultMap.put("project", projectRet);
		String json = JSONObject.toJSONString(resultMap);
		log.info("response--> " + json);
		return json;
	}

	/**
	 * 获取课题详细信息
	 */
	@ApiOperation(value = "获取课题详细信息")
	@RequestMapping(value = "/api/Project/{Id}", method = { RequestMethod.GET })
	public String GetProjectDetail(@PathVariable Long Id, HttpServletRequest request, HttpServletResponse response) {
		JLPLog log = ThreadPoolConfig.getBaseContext();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String authorization = request.getHeader("Authorization") == null ? "bc6ef9c43a0e5b25da87ca2ba948d3eb" : request.getHeader("Authorization");
		Long userId = userService.getIdByToken(authorization);
		if (userId == null) {
			BaseResponse baseResponse = new BaseResponse();
			response.setStatus(400);
			baseResponse.setCode("400");
			baseResponse.setText("用户信息获取失败，请检查请求头");
			resultMap.put("response", baseResponse);
			return JSONObject.toJSONString(resultMap);
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
		pro.setCourseName(project.getProjectname());
		pro.setDescription(project.getProjectdescribe());
		pro.setEnglishName(project.getProjectenname());
		pro.setFounders(project.getSponsor());
		pro.setIsShared("1".equals(project.getSharetype()) ? true : false);
		pro.setRegisterid(project.getProjectregistno());
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

	@ApiOperation(value = "删除课题")
	@RequestMapping(value = "/api/Project/{Id}", method = { RequestMethod.DELETE })
	public String Delete(Long Id, HttpServletRequest request, HttpServletResponse response) {
		JLPLog log = ThreadPoolConfig.getBaseContext();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String authorization = request.getHeader("Authorization") == null ? "" : request.getHeader("Authorization");
		Long userId = userService.getIdByToken(authorization);
		if (userId == null) {
			BaseResponse baseResponse = new BaseResponse();
			response.setStatus(400);
			baseResponse.setCode("400");
			baseResponse.setText("用户信息获取失败，请检查请求头");
			resultMap.put("response", baseResponse);
			return JSONObject.toJSONString(resultMap);
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
		project.setActiveflag(JLPConts.Inactive);
		projectService.update(project);
		BaseResponse baseResponse = new BaseResponse();
		resultMap.put("response", baseResponse);
		String json = JSONObject.toJSONString(resultMap);
		log.info("response--> " + json);
		return json;
	}

	@ApiOperation(value = "课题清除更改状态")
	@RequestMapping(value = "/api/clearChangeStatus", method = { RequestMethod.POST})
	public String clearChangeStatus(@RequestBody ProjectChangeStatusModel projectChangeStatus,
			HttpServletRequest request,HttpServletResponse response) {
		JLPLog log = ThreadPoolConfig.getBaseContext();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String authorization = request.getHeader("Authorization") == null ? "" : request.getHeader("Authorization");
		Long userId = userService.getIdByToken(authorization);
		if (userId == null) {
			BaseResponse baseResponse = new BaseResponse();
			response.setStatus(400);
			baseResponse.setCode("400");
			baseResponse.setText("用户信息获取失败，请检查请求头");
			resultMap.put("response", baseResponse);
			return JSONObject.toJSONString(resultMap);
		}
		String sql = "update J_PROJECTDATASTATUS set CHANGESTATUS=? "
				+ " where PROJECTID= ? and USERID=? and CHANGESTATUS= ?";
		jLpService.excuteSql(sql, new Object[] { "0", projectChangeStatus.getProjectId(), userId, "1" });
		BaseResponse baseResponse = new BaseResponse();
		resultMap.put("response", baseResponse);
		String json = JSONObject.toJSONString(resultMap);
		log.info("response--> " + json);
		return json;
	}

	@ApiOperation(value = "获取课题导出信息")
	@RequestMapping(value = "/api/Project/ExportInfo/{Id}", method = { RequestMethod.GET })
	public String GetExportInfo(@PathVariable Long Id, HttpServletRequest request, HttpServletResponse response) {
		JLPLog log = ThreadPoolConfig.getBaseContext();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String authorization = request.getHeader("Authorization") == null ? "bc6ef9c43a0e5b25da87ca2ba948d3eb" : request.getHeader("Authorization");
		Long userId = userService.getIdByToken(authorization);
		if (userId == null) {
			BaseResponse baseResponse = new BaseResponse();
			response.setStatus(400);
			baseResponse.setCode("400");
			baseResponse.setText("用户信息获取失败，请检查请求头");
			resultMap.put("response", baseResponse);
			return JSONObject.toJSONString(resultMap);
		}
//            #region 纳排条件处理
		List<String> includeSql = new ArrayList<String>();
		List<String> excludeSql = new ArrayList<String>();

		Map<String, List<QueryCollectionModel>> queryList = new HashMap<String, List<QueryCollectionModel>>();

		AdvancedQueryRecordModel projectQueryConds = null;

		try {
			projectQueryConds = projectService.getQueryRecord(Id, userId);
		}catch( Exception e) {
			log.error(e);
		}
		
		if (projectQueryConds == null) {
			BaseResponse baseResponse = new BaseResponse();
			response.setStatus(404);
			baseResponse.setCode("404");
			baseResponse.setText("未找到该项目信息");
			resultMap.put("response", baseResponse);
			return JSONObject.toJSONString(resultMap);
		}
		if (projectQueryConds.getQueries() != null && projectQueryConds.getQueries().getQueryIncludesEX() != null
				&& projectQueryConds.getQueries().getQueryIncludesEX().getIncludes() != null) {
			queryList.put("include", projectQueryConds.getQueries().getQueryIncludesEX().getIncludes());
		}

		if (projectQueryConds.getQueries() != null && projectQueryConds.getQueries().getQueryIncludesEX() != null
				&& projectQueryConds.getQueries().getQueryIncludesEX().getExcludes() != null) {
			queryList.put("exclude", projectQueryConds.getQueries().getQueryIncludesEX().getExcludes());
		}

		if (projectQueryConds.getQueries() != null && projectQueryConds.getQueries().getQueryConds() != null) {
			QueryCollectionModel queryCollection = new QueryCollectionModel();
			queryCollection.setConds(projectQueryConds.getQueries().getQueryConds());
			queryCollection.setLeftqueto("(");
			queryCollection.setRightqueto(")");
			queryCollection.setSetCombinator("1");
			if (!queryList.containsKey("include")) {
				queryList.put("include", new ArrayList<QueryCollectionModel>());
			}
			List<QueryCollectionModel> relQCL = queryList.get("include");
			if (relQCL.size() > 0) {
				relQCL.get(0).setLeftqueto("(");
				relQCL.get(relQCL.size() - 1).setRightqueto(")");
			}
			relQCL.add(queryCollection);
		}

		String tempadvancedQueryType = "";
		for (String queryKey : queryList.keySet()) {
			List<QueryCollectionModel> list = queryList.get(queryKey);
			for (QueryCollectionModel subItem : list) {
				SortedSet<String> tempTableNames = new TreeSet<String>();
				List<String> tempFieldNames = new ArrayList<String>();

				tempFieldNames.add(JLPConts.PatientGlobalTable + ".Id");

				List<QueryDetailModel> queryDetails = new ArrayList<QueryDetailModel>();

				if (subItem.getConds() == null) {
					continue;
				}

				queryDetails.addAll(subItem.getConds());

//                    #region 获取查询所有表名
				Set<String> tableNameWheres = new HashSet<String>();
				Set<String> tableNameWhereValues = new HashSet<String>();

				Set<String> whereFields = new HashSet<String>();

				Set<String> whereFieldTypes = new HashSet<String>();
				String SqlWhere  = "where 1=1 and ";
				for (QueryDetailModel queryConds : queryDetails) {
					whereFields.add(queryConds.getCondition().toUpperCase());
					String[] conditionArray = queryConds.getCondition().split("\\.");
					if (conditionArray.length > 0) {
						tableNameWheres.add(conditionArray[0]);
					}
					String[] condValueArray = queryConds.getCondValue().split("\\.");
					if (condValueArray.length > 0) {
						if (queryConds.getFieldType() == 1) {
							tableNameWhereValues.add(condValueArray[0]);
						}
					}
					if (!queryConds.getRelation().contains("null")) {
						SqlWhere += queryConds.getCondition() + " " + queryConds.getRelation() + " '" + queryConds.getCondValue() + "' and ";
					}else {
						SqlWhere += queryConds.getCondition() + " " + queryConds.getRelation() + " " + " and ";
					}
				}
				// 去掉最后的and
				if (SqlWhere.length() > 4) {
					SqlWhere = SqlWhere.substring(0, SqlWhere.length() - 4);
				}
				tempTableNames.addAll(tableNameWheres);
				tempTableNames.addAll(tableNameWhereValues);
				tempTableNames.remove(JLPConts.PatientGlobalTable);

				// region 获取查询结果和数量
				String tableWhere = toTableString(tempTableNames, "");
				String TempSql = subItem.getLeftqueto() + " select distinct "
						+ tempFieldNames.toString().substring(1, tempFieldNames.toString().length() - 1) + " from "
						+ tableWhere + " " + SqlWhere + "   " + subItem.getRightqueto();

				if ("include".equals(queryKey)) {
					if (includeSql.size() != 0) {
						includeSql.add(tempadvancedQueryType);
					}
					includeSql.add(TempSql);
				} else if ("exclude".equals(queryKey)) {
					if (excludeSql.size() != 0) {
						excludeSql.add(tempadvancedQueryType);
					}
					excludeSql.add(TempSql);
				}

				tempadvancedQueryType = subItem.getSetCombinator();
			}
		}

//            #region 组装SQL
		StringBuilder includeStrBuilder = new StringBuilder();
		for (String sql : includeSql) {
			includeStrBuilder.append(sql + " ");
		}

		StringBuilder excludeStrBuilder = new StringBuilder();

		for (String sql : excludeSql) {
			excludeStrBuilder.append(sql + " ");
		}

		String tempSql = "";

		if (includeStrBuilder.length() > 0) {
			tempSql = " select Id from (" + includeStrBuilder.toString() + ")";
			if (excludeStrBuilder.length() > 0) {
				tempSql += " minus (" + excludeStrBuilder.toString() + ")";
			}
		}
		SortedSet<String> tableNames = new TreeSet<String>();
		tableNames.add("D_EXAMINFO");
		String Sql = "select distinct EXAMCLASS from " + toTableString(tableNames, tempSql);

		String tempSqlWhere = "";
		if (tempSqlWhere.length() == 0) {
			tempSqlWhere = " where D_PATIENTGLOBAL.jlactiveflag='1'";
		} else {
			tempSqlWhere += " and D_PATIENTGLOBAL.jlactiveflag='1'";
		}

		Sql += tempSqlWhere;
		log.info("Sql-->" + Sql);
		List<Map<String, Object>> ImageClassesList = jLpService.queryForSQL(Sql, new Object[] {});
		List<Object> ImageClasses = new ArrayList<Object>();
		for(Map<String,Object> map : ImageClassesList) {
			for(String key : map.keySet()) {
				ImageClasses.add(map.get(key));
			}
		}
		
		List<JExportdataclass> AllExportClasses = exportdataclassService.getAll();
		Collections.sort(AllExportClasses, new Comparator<JExportdataclass>() {
			@Override
			public int compare(JExportdataclass o1, JExportdataclass o2) {
				return o1.getType().compareTo(o2.getType());
			}
		});

		Collections.sort(AllExportClasses, new Comparator<JExportdataclass>() {
			@Override
			public int compare(JExportdataclass o1, JExportdataclass o2) {
				return o1.getId().compareTo(o2.getId());
			}
		});
		List<Map<String,Object>> dataExportClasses = new ArrayList<Map<String,Object>>();
		List<Map<String,Object>> imageExportClasses = new ArrayList<Map<String,Object>>();
		for (JExportdataclass exportdataclass : AllExportClasses) {
			if ("1".equals(exportdataclass.getType())) {
				dataExportClasses.add(new HashMap<String, Object>(){{
					put("id", exportdataclass.getId());
					put("title", exportdataclass.getTitle());
					put("info", exportdataclass.getInfo());
				}});
			}

			if ("2".equals(exportdataclass.getType())) {
				imageExportClasses.add(new HashMap<String, Object>(){{
					put("id", exportdataclass.getId());
					put("title", exportdataclass.getTitle());
					put("info", exportdataclass.getInfo());
				}});
			}
		}
		List<JFiletype> fileTypesList = (List<JFiletype>) filetypeService.getAll().getData();
		List<Map<String,Object>> fileTypes = new ArrayList<Map<String,Object>>();
		for(JFiletype filetype : fileTypesList) {
			fileTypes.add(new HashMap<String, Object>(){{
				put("id", filetype.getId());
				put("name", filetype.getName());
			}});
		}
		
		Map<String, Object> image = new HashMap<String, Object>();
		image.put("imageExportClasses", imageExportClasses);
		resultMap.put("dataExportClasses", dataExportClasses);
		
		image.put("imageClasses", ImageClasses);

		resultMap.put("filetypes", fileTypes);
		List<Map<String, Object>> exportTypes = new ArrayList<Map<String, Object>>();
		Map<String, Object> map1 = new HashMap<String, Object>();
		map1.put("id", 1);
		map1.put("title", "导出到文件");
		map1.put("info", "生成可下载文件名");
		exportTypes.add(map1);
		Map<String, Object> map2 = new HashMap<String, Object>();
		map2.put("id", 2);
		map2.put("title", "导出到统计分析");
		map2.put("info", "生成在线统计分析数据库");
		exportTypes.add(map2);

		resultMap.put("exportTypes", exportTypes);
		
		resultMap.put("image", image);
		BaseResponse baseResponse = new BaseResponse();
		resultMap.put("response", baseResponse);
		String json = JSONObject.toJSONString(resultMap);
		log.info("response--> " + json);
		return json;
	}

	private String toTableString(SortedSet<String> tableArray, String advancedQueryWhere) {
		String ret = "";
		tableArray.remove(JLPConts.PatientGlobalTable.toUpperCase());
		if (tableArray != null) {

			String patientTable = JLPConts.PatientGlobalTable.toLowerCase();
			ret = patientTable;

			if (StringUtils.isNotBlank(advancedQueryWhere)) {
				ret += " inner join (" + advancedQueryWhere + ") a on " + patientTable + ".Id=a.Id ";
			}

			boolean IsInner = tableArray.size() <= 1;
			if (!IsInner && tableArray.size() == 2) {
				if (tableArray.contains("D_LABMASTERINFO") && tableArray.contains("D_LABRESULTS")) {
					IsInner = true;
				}
			}
			boolean IsLabResult = false;
			boolean IsLabMaster = false;

			for (String item : tableArray) {
				if ("D_LABMASTERINFO".equals(item.toUpperCase())) {
					IsLabMaster = true;
				} else if ("D_LABRESULTS".equalsIgnoreCase(item)) {
					IsLabResult = true;
					continue;
				} else if (item.toLowerCase().equals(patientTable)) {
					continue;
				}
				ret += (IsInner ? " inner" : " left") + " join " + item + " on " + patientTable + ".Id = " + item
						+ ".patientglobalid ";
			}
			if (IsLabResult) {
				if (!IsLabMaster) {
					ret += (IsInner ? "inner" : "left") + " join D_LABMASTERINFO on " + patientTable
							+ ".Id = D_LABMASTERINFO.patientglobalid ";
				}
				ret += (IsInner ? " inner" : " left")
						+ " join D_LABRESULTS on D_LABRESULTS.APPLYNO = D_LABMASTERINFO.APPLYNO ";
			}
		}
		return ret;
	}
}
