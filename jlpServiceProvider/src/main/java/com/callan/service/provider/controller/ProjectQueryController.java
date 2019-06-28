package com.callan.service.provider.controller;

import java.util.ArrayList;
import java.util.Arrays;
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
import javax.websocket.server.PathParam;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.weaver.ast.Var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.callan.service.provider.config.JLPConts;
import com.callan.service.provider.config.JLPException;
import com.callan.service.provider.config.JLPLog;
import com.callan.service.provider.config.ObjectUtil;
import com.callan.service.provider.config.ThreadPoolConfig;
import com.callan.service.provider.pojo.advanceQueryBase.AdvancedQueryRecordModel;
import com.callan.service.provider.pojo.advanceQueryBase.ColunmsModel;
import com.callan.service.provider.pojo.advanceQueryBase.QueryCollectionModel;
import com.callan.service.provider.pojo.advanceQueryBase.QueryDetailModel;
import com.callan.service.provider.pojo.advanceQueryBase.Sorted;
import com.callan.service.provider.pojo.base.BaseResponse;
import com.callan.service.provider.pojo.base.FieldName;
import com.callan.service.provider.pojo.db.JProjectdatastatusdict;
import com.callan.service.provider.pojo.db.JProjectstatistics;
import com.callan.service.provider.pojo.db.JRight;
import com.callan.service.provider.pojo.db.JRoleRight;
import com.callan.service.provider.pojo.db.JSensitiveWord;
import com.callan.service.provider.pojo.db.JShowDetailView;
import com.callan.service.provider.pojo.db.JShowView;
import com.callan.service.provider.pojo.db.JTableDict;
import com.callan.service.provider.pojo.db.JTableFieldDict;
import com.callan.service.provider.pojo.db.JTagdicts;
import com.callan.service.provider.pojo.project.ProjectQueryModel;
import com.callan.service.provider.pojo.project.ProjectTagModel;
import com.callan.service.provider.pojo.tableclassdict.JTableclassdict;
import com.callan.service.provider.service.IJLpService;
import com.callan.service.provider.service.IJProjectDataStatusService;
import com.callan.service.provider.service.IJProjectDataStatusdictService;
import com.callan.service.provider.service.IJProjectService;
import com.callan.service.provider.service.IJProjectstatisticsService;
import com.callan.service.provider.service.IJRoleRightService;
import com.callan.service.provider.service.IJSensitiveWordService;
import com.callan.service.provider.service.IJShowDetailViewService;
import com.callan.service.provider.service.IJShowViewService;
import com.callan.service.provider.service.IJTableDictService;
import com.callan.service.provider.service.IJTableFieldDictService;
import com.callan.service.provider.service.IJTableclassdictService;
import com.callan.service.provider.service.IJTagdictService;
import com.callan.service.provider.service.IJUserService;
import com.callan.service.provider.service.impl.JLPServiceImpl;
import com.callan.service.provider.service.impl.JTableclassdictServiceImpl;
import com.google.common.collect.Lists;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 课题内查询
 *
 */
@RestController
@Api(description = "课题内查询")
public class ProjectQueryController {

	@Autowired
	private IJUserService userService;

	@Autowired
	private IJProjectService projectService;

	@Autowired
	private IJTableclassdictService tableclassdictService;

	@Autowired
	private IJShowViewService showViewService;

	@Autowired
	private IJShowDetailViewService jShowDetailViewService;

	@Autowired
	private IJTableDictService jTableDictService;

	@Autowired
	private IJTableFieldDictService jTableFieldDictService;

	@Autowired
	private IJTagdictService tagdictService;

	@Autowired
	private IJProjectDataStatusdictService projectDataStatusdictService;

	@Autowired
	private IJLpService jlpService;

	@Autowired
	private IJRoleRightService roleRightService;

	@Autowired
	private IJSensitiveWordService sensitiveWordService;

	@Autowired
	private IJProjectstatisticsService projectstatisticsService;

	@ApiOperation(value = "获取课题查询条件")
	@RequestMapping(value = "/api/ProjectQuery/{Id}", method = { RequestMethod.GET })
	public String Get(Long Id, HttpServletRequest request, HttpServletResponse response) {
		JLPLog log = ThreadPoolConfig.getBaseContext();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String authorization = request.getHeader("Authorization") == null ? "" : request.getHeader("Authorization");
		Long userId = userService.getIdByToken(authorization);
		if (userId == null) {
			userId = 0L;
		}
		try {
			AdvancedQueryRecordModel advancedQueryRecord = projectService.getQueryRecord(Id, userId);
			BaseResponse baseResponse = new BaseResponse();
			resultMap.put("response", baseResponse);
			resultMap.put("queryRecord", advancedQueryRecord);
			return JSONObject.toJSONString(resultMap);
		} catch (Exception e) {
			BaseResponse baseResponse = new BaseResponse();
			response.setStatus(404);
			baseResponse.setCode("404");
			baseResponse.setText(e.getMessage());
			resultMap.put("response", baseResponse);
			return JSONObject.toJSONString(resultMap);
		}
	}

	@ApiOperation(value = "课题数据查询")
	@RequestMapping(value = "/api/ProjectQuery", method = { RequestMethod.GET })
	public String Post(Integer pageSize, Integer pageNum, @RequestBody ProjectQueryModel projectQuery,
			HttpServletRequest request, HttpServletResponse response) {

		JLPLog log = ThreadPoolConfig.getBaseContext();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String authorization = request.getHeader("Authorization") == null ? "" : request.getHeader("Authorization");
		Long userId = userService.getIdByToken(authorization);
		if (userId == null) {
			userId = 0L;
		}
		if (pageSize == null || pageSize == 0) {
			pageSize = 20;
		}
		if (pageNum == null || pageNum == 0) {
			pageNum = 1;
		}

//        #region 传入参数空值验证
		if (projectQuery == null) {
			BaseResponse baseResponse = new BaseResponse();
			baseResponse.setCode("0000");
			baseResponse.setText("传入参数为空");
			resultMap.put("response", baseResponse);
			return JSONObject.toJSONString(resultMap);
		} else {
			if (projectQuery.getProjectId().longValue() == 0L) {
				BaseResponse baseResponse = new BaseResponse();
				baseResponse.setCode("0000");
				baseResponse.setText("课题ID错误");
				resultMap.put("response", baseResponse);
				return JSONObject.toJSONString(resultMap);
			}
		}

		JTableclassdict tableclassdict = tableclassdictService.getOne(projectQuery.getTableClassId());

		if (tableclassdict == null) {
			BaseResponse baseResponse = new BaseResponse();
			baseResponse.setCode("404");
			response.setStatus(404);
			baseResponse.setText("数据分类信息错误");
			resultMap.put("response", baseResponse);
			return JSONObject.toJSONString(resultMap);
		}
		JShowView showview = showViewService.getOne(tableclassdict.getViewid().longValue());
		if (showview == null) {
			BaseResponse baseResponse = new BaseResponse();
			baseResponse.setCode("0000");
			baseResponse.setText("视图编号错误");
			resultMap.put("response", baseResponse);
			return JSONObject.toJSONString(resultMap);
		}

//        #region 获取显示视图信息
		String viewTableName = "";
		JTableDict mainTable = null;
		List<JShowDetailView> jShowDetailViewList = jShowDetailViewService.getByViewId(showview.getId());
//      
		List<JTableFieldDict> jTableFieldDictList = new ArrayList<JTableFieldDict>();
		for (JShowDetailView jShowDetailView : jShowDetailViewList) {
			long fieldId = jShowDetailView.getFieldid();
			JTableFieldDict jTableFieldDict = jTableFieldDictService.getOne(fieldId, true);
			if (jTableFieldDict == null) {
				log.warn("fieldId 为 " + fieldId + " 未找到j_tablefielddict 记录 ");
				continue;
			}
			jTableFieldDictList.add(jTableFieldDict);
			jTableFieldDict.setjShowDetailView(jShowDetailView);
			jShowDetailView.setjTableFieldDict(jTableFieldDict);
			JTableDict jTableDict = jTableDictService.getByCode(jTableFieldDict.getTablecode(), true);
			jShowDetailView.setjTableDict(jTableDict);
			if (mainTable == null) {
				mainTable = jTableDict;
			}
		}

		List<JShowDetailView> jShowDetailViewListShow = new ArrayList<>();
		if (projectQuery.getQueryShowFields() != null) {
			List<Long> tempFieldList = new ArrayList<Long>();
			for (String field : projectQuery.getQueryShowFields()) {
				tempFieldList.add(ObjectUtil.objToLong(field));
			}
			for (JShowDetailView jShowDetailView : jShowDetailViewList) {
				if (tempFieldList.contains(jShowDetailView.getId().longValue())) {
					if (!jShowDetailViewListShow.contains(jShowDetailView)) {
						jShowDetailViewListShow.add(jShowDetailView);
					}
				}
			}
		}
		Collections.sort(jShowDetailViewListShow, new Comparator<JShowDetailView>() {
			@Override
			public int compare(JShowDetailView o1, JShowDetailView o2) {
				return o1.getSortno().compareTo(o2.getSortno());
			}
		});

		if (jTableFieldDictList.size() == 0) {
			BaseResponse baseResponse = new BaseResponse();
			baseResponse.setCode("0000");
			baseResponse.setText("显示字段未配置");
			resultMap.put("response", baseResponse);
			String json = JSONObject.toJSONString(resultMap);
			log.info("response --> " + json);
			return json;
		}

		viewTableName = mainTable.getName();
		List<JTagdicts> list = tagdictService.getByProjectId(projectQuery.getProjectId());
		List<JTagdicts> tagProjectDicts = new ArrayList<JTagdicts>();
		for (JTagdicts tagdicts : list) {
			if (tagdicts.getId().longValue() == userId.longValue()
					&& JLPConts.ActiveFlag.equals(tagdicts.getShowflag())) {
				tagProjectDicts.add(tagdicts);
			}
		}
//        #region 获取标签信息
//        var tagProjectDicts = (from tagdict in orclJlpContext.JTagdicts
//                               join tagvaluedict in orclJlpContext.JTagvaluedicts on tagdict.Id
//                                equals tagvaluedict.Tagid into taginfos
//                               from tagvaluedict in taginfos
//                               where tagdict.Activeflag == JLPStaticProperties.ActiveFlag
//                                  && tagdict.Showflag == JLPStaticProperties.ActiveFlag
//                                  && tagvaluedict.Activeflag == JLPStaticProperties.ActiveFlag
//                                  && tagdict.Projectid == projectQuery.projectId
//                                  && tagdict.Userid == userId
//                               group tagvaluedict by tagdict into taggroup
//                               select taggroup).ToList();
		List<JTagdicts> tagShowDicts = new ArrayList<JTagdicts>();
		for (JTagdicts tagdicts : tagProjectDicts) {
			if (tagdicts.getTagattached().equals(projectQuery.getTableClassId())) {
				tagShowDicts.add(tagdicts);
			}
		}
		Map<String, ProjectTagModel> tagShowRets = new HashMap<>();
		for (JTagdicts tagdicts : tagShowDicts) {
			Long id = tagdicts.getId();
			ProjectTagModel projectTagModel = new ProjectTagModel();
			projectTagModel.setId(tagdicts.getId());
			projectTagModel.setName(tagdicts.getName());
			projectTagModel.setFieldType(tagdicts.getValuetype());
//			projectTagModel.setFieldValue(fieldValue);

			tagShowRets.put("tag_" + tagdicts.getId(), projectTagModel);
		}
//        tagShowDicts.ForEach(x =>
//        {
//            tagShowRets.Add("tag_" + x.Key.Id.ToString(), new ProjectTagModel
//            {
//                id = x.Key.Id,
//                name = x.Key.Name,
//                fieldType = x.Key.Valuetype,
//                fieldValue = x.OrderBy(y => y.Id).Select(y =>
//               {
//                   if (!string.IsNullOrEmpty(y.Value))
//                   { return y.Value; }
//                   else
//                   { return y.Minvalue + "" + y.Maxvalue; }
//               }).ToArray().ToStringEx("/")
//            });
//        });

		List<QueryDetailModel> queryMainDetails = new ArrayList<>();
		List<QueryDetailModel> queryOtherTableClassDetails = new ArrayList<>();

		// queryRecord
		AdvancedQueryRecordModel projectQueryConds = null;
		try {
			projectQueryConds = projectService.getQueryRecord(projectQuery.getProjectId(), userId);
		} catch (Exception e) {
			BaseResponse baseResponse = new BaseResponse();
			baseResponse.setCode("1001");
			baseResponse.setText("无法获取课题查询数据");
			resultMap.put("response", baseResponse);
			resultMap.put("initialization", true);
			String json = JSONObject.toJSONString(resultMap);
			log.info("response --> " + json);
			return json;
		}

		List<QueryDetailModel> tempmainQueryModels = new ArrayList<QueryDetailModel>();
		if (projectQueryConds.getQueries().getQueryConds() != null) {
			tempmainQueryModels.addAll(projectQueryConds.getQueries().getQueryConds());
		}
		if (projectQuery.getQueries() != null && projectQueryConds.getQueries().getQueryConds() != null
				&& projectQueryConds.getQueries().getQueryConds().size() > 0) {
			if (tempmainQueryModels.size() > 0) {
				tempmainQueryModels.get(0).setLeftqueto(tempmainQueryModels.get(0).getLeftqueto() + "(");
				tempmainQueryModels.get(tempmainQueryModels.size() - 1)
						.setRightqueto(tempmainQueryModels.get(tempmainQueryModels.size() - 1).getRightqueto() + ")");
				tempmainQueryModels.get(tempmainQueryModels.size() - 1).setCombinator("AND");
			}
			tempmainQueryModels.addAll(Arrays.asList(projectQuery.getQueries().getQueryConds()));
		}
		if (tempmainQueryModels.size() > 0) {
			Map<String, JTagdicts> tagShowCondition = new HashMap<>();
			for (JTagdicts tagdicts : tagShowDicts) {
				String newId = ("projecttags.tag_" + tagdicts.getId()).toUpperCase();
				tagShowCondition.put(newId, tagdicts);
			}
			for (QueryDetailModel queryDetailModel : tempmainQueryModels) {
				if (queryDetailModel.getCondition().toUpperCase().contains("TAG_")) {
					if (tagShowCondition.containsKey(queryDetailModel.getCondition().toUpperCase())) {
						queryMainDetails.add(queryDetailModel);
					} else {
						queryOtherTableClassDetails.add(queryDetailModel);
					}
				} else {
					queryMainDetails.add(queryDetailModel);
				}
			}
		}

//        #region 获取查询所有表名

		Set<String> tableNameMainWheres = new HashSet<String>();
		Set<String> tableNameWhereMainValues = new HashSet<String>();
		Set<String> whereFieldsMain = new HashSet<String>();
		Set<String> whereFieldTypesMain = new HashSet<String>();

		String SqlWhereMain = "";
		for (QueryDetailModel queryDetailModel : queryMainDetails) {
			tableNameMainWheres.add(queryDetailModel.getCondition().split(".")[0]);
			if (queryDetailModel.getFieldType() == 1) {
				tableNameWhereMainValues.add(queryDetailModel.getCondValue().split(".")[0]);
			}
			whereFieldsMain.add(queryDetailModel.getCondition().toUpperCase());

			if (!queryDetailModel.getRelation().contains("null")) {
				SqlWhereMain += queryDetailModel.getCondition() + " " + queryDetailModel.getRelation() + " "
						+ queryDetailModel.getCondValue() + " and ";
			} else {
				SqlWhereMain += queryDetailModel.getCondition() + " " + queryDetailModel.getRelation() + " " + " and ";
			}

		}
		// 去掉最后的and
		if (SqlWhereMain.length() > 4) {
			SqlWhereMain = SqlWhereMain.substring(0, SqlWhereMain.length() - 4);
		}
//        var whereFieldTypesMain = (from a in orclJlpContext.JTabledicts
//                                   join b in orclJlpContext.JTablefielddicts on a.Code equals b.Tablecode into tablefields
//                                   from b in tablefields
//                                   where a.Activeflag == "1" && whereFieldsMain.Contains(a.Name + "." + b.Name)
//                                   select new { field = a.Name + "." + b.Name, type = b.Type }).Distinct().ToList();

//        for(QueryDetailModel queryDetailModel : queryMainDetails) {
//        	if(queryDetailModel.getCondition().toUpperCase().contains("TAG_")) {
//        		if(queryDetailModel.getFieldType() != 1) {
//        			queryDetailModel.setFieldType(fieldType);
//        		}
//        	}else {
//        		queryDetailModel.setFieldType(fieldType);
//        	}
//        }
		queryMainDetails.get(queryMainDetails.size() - 1).setCombinator("");
		// TODO
//        queryMainDetails.ForEach(x =>
//        {
//            x.ForEach(y =>
//            {
//                if (!y.condition.ToUpper().Contains("TAG_"))
//                {
//                    if (y.fieldType != FieldValueType.Field)
//                    {
//                        y.fieldType = (whereFieldTypesMain.FirstOrDefault(m => m.field == y.condition.ToUpper())?.type.toFieldValueType()) ?? FieldValueType.String;
//                    }
//                }
//                else
//                {
//                    y.fieldType = (tagShowDicts.FirstOrDefault(m => ("projecttags.tag_" + m.Key.Id.ToString()).ToUpper() == y.condition.ToUpper())?.Key.Valuetype == "2") ? FieldValueType.Number : FieldValueType.String;
//                }
//            });
//            if (x.LastOrDefault() != null)
//            {
//                x.LastOrDefault().combinator = "";
//            }
//        });
		SortedSet<String> tableNames = new TreeSet<String>();
		List<FieldName> fieldNames = new ArrayList<FieldName>();
		SortedSet<String> fieldShowNames = new TreeSet<String>();
		SortedSet<String> showTableNames = new TreeSet<String>();

		List<ColunmsModel> columns = new ArrayList<ColunmsModel>();

		for (JShowDetailView showDetailView : jShowDetailViewListShow) {
			tableNames.add(showDetailView.getjTableDict().getName());
			showTableNames.add(showDetailView.getjTableDict().getName());
			fieldNames.add(new FieldName(
					(showDetailView.getjTableDict().getName() + "." + showDetailView.getjTableFieldDict().getName())
							.toLowerCase()));
			fieldShowNames.add(showDetailView.getjTableFieldDict().getName().toLowerCase());

			JTableFieldDict jTableFieldDict = showDetailView.getjTableFieldDict();
			ColunmsModel colunmsModel = new ColunmsModel();
			colunmsModel.setDataIndex(jTableFieldDict.getName().toLowerCase());
			colunmsModel.setKey(jTableFieldDict.getName().toLowerCase());
			colunmsModel.setTitle(jTableFieldDict.getjShowDetailView().getFieldtitle());
			colunmsModel.setIsLongStr("clob".equals(jTableFieldDict.getType()));
			colunmsModel.setIsSearched(JLPConts.ActiveFlag.equals(jTableFieldDict.getQueryflag()));
			colunmsModel.setIsTag(false);
			columns.add(colunmsModel);

		}
		tableNames.addAll(tableNameMainWheres);

//        #region 标签显示项
		if (tagShowDicts.size() > 0) {
			for (JTagdicts dicts : tagShowDicts) {
				fieldShowNames.add("tag_" + dicts.getId());
			}
			fieldShowNames.add("tag_complete");
			ColunmsModel colunmsModel = new ColunmsModel();
			colunmsModel.setDataIndex("tag_complete");
			colunmsModel.setKey("tag_complete");
			colunmsModel.setTitle("标签进度");
			colunmsModel.setIsLongStr(false);
			colunmsModel.setIsSearched(false);
			colunmsModel.setIsTag(false);
			columns.add(colunmsModel);

			for (JTagdicts dicts : tagShowDicts) {
				ColunmsModel colunmsModel1 = new ColunmsModel();
				colunmsModel1.setDataIndex(("TAG_" + dicts.getId()).toLowerCase());
				colunmsModel1.setKey(("TAG_" + dicts.getId()).toLowerCase());
				colunmsModel1.setTitle(dicts.getName());
				colunmsModel1.setIsLongStr(false);
				colunmsModel1.setIsSearched(true);
				colunmsModel1.setIsTag(true);
				columns.add(colunmsModel1);
			}

			fieldNames.add(
					new FieldName("(case when tag_complete is null then '未开始' else tag_complete end) as tag_complete"));
			fieldNames.add(new FieldName("\"_tag_changestatus\""));

			for (JTagdicts dicts : tagShowDicts) {
				fieldNames.add(new FieldName("PROJECTTAGS.TAG_" + dicts.getId()));
			}
		}

//        var SqlWhereMain = queryMainDetails.Select(x => x.ToArray().ToStringEx(" ")).ToArray().ToStringEx(" and ");
		tableNames.addAll(tableNameMainWheres);
		tableNames.addAll(tableNameWhereMainValues);
		tableNames.remove(JLPConts.PatientGlobalTable);

//        #region 排序字段处理
		String sortStr = "";
		if (projectQuery.getSorted() == null) {
			projectQuery.setSorted(new Sorted[] {});
		}
		Set<Sorted> sorts = new TreeSet<Sorted>();
		for (Sorted sorted : projectQuery.getSorted()) {
			if (fieldShowNames.contains(sorted.getId().toLowerCase())) {
				sorts.add(sorted);
			}
		}
		sortStr = sorts.toString().substring(1, sorts.toString().length() - 1);
		if (sortStr.length() == 0) {
			sortStr = "\"_key\"";
		}

//        #region 纳排条件处理
		List<String> includeSql = new ArrayList<String>();
		List<String> excludeSql = new ArrayList<String>();

		Map<String, List<QueryCollectionModel>> queryList = new HashMap<String, List<QueryCollectionModel>>();

		if (projectQueryConds.getQueries().getQueryIncludesEX().getIncludes() != null) {
			queryList.put("include", projectQueryConds.getQueries().getQueryIncludesEX().getIncludes());
		}

		if (projectQueryConds.getQueries().getQueryIncludesEX().getExcludes() != null) {
			queryList.put("exclude", projectQueryConds.getQueries().getQueryIncludesEX().getExcludes());
		}

		String tempadvancedQueryType = "";
		for (String item : queryList.keySet()) {
			for (QueryCollectionModel subItem : queryList.get(item)) {

				SortedSet<String> tempTableNames = new TreeSet<String>();
				List<String> tempFieldNames = new ArrayList<String>();

				tempFieldNames.add(JLPConts.PatientGlobalTable + ".Id");

				List<QueryDetailModel> queryDetails = new ArrayList<QueryDetailModel>();

				if (subItem.getConds() == null) {
					continue;
				}

				queryDetails.addAll(subItem.getConds());

//                #region 获取查询所有表名

				Set<String> tableNameWheres = new HashSet<String>();
				Set<String> tableNameWhereValues = new HashSet<String>();
//				Set<String> whereFields = new HashSet<String>();
//				Set<String> whereFieldTypes = new HashSet<String>();

				String SqlWhere = "";
				for (QueryDetailModel queryDetailModel : queryDetails) {
					tableNameWheres.add(queryDetailModel.getCondition().split(".")[0]);
					if (queryDetailModel.getFieldType() == 1) {
						tableNameWhereValues.add(queryDetailModel.getCondValue().split(".")[0]);
					}
//					whereFields.add(queryDetailModel.getCondition().toUpperCase());

					if (!queryDetailModel.getRelation().contains("null")) {
						SqlWhere += queryDetailModel.getCondition() + " " + queryDetailModel.getRelation() + " "
								+ queryDetailModel.getCondValue() + " and ";
					} else {
						SqlWhere += queryDetailModel.getCondition() + " " + queryDetailModel.getRelation() + " "
								+ " and ";
					}

				}
				// TODO
//                queryDetails.ForEach(x =>
//                {
//                    x.ForEach(y =>
//                    {
//                        if (y.fieldType != FieldValueType.Field)
//                        {
//                            y.fieldType = (whereFieldTypes.FirstOrDefault(m => m.field == y.condition.ToUpper())?.type.toFieldValueType()) ?? FieldValueType.String;
//                        }
//                    });
//                    if (x.LastOrDefault() != null)
//                    {
//                        x.LastOrDefault().combinator = "";
//                    }
//                });

				tempTableNames.addAll(tableNameWheres);
				tempTableNames.addAll(tableNameWhereValues);
				tempTableNames.remove(JLPConts.PatientGlobalTable);

//                #region 获取查询结果和数量
				String tableWhere = JLPServiceImpl.toTableString(tempTableNames, "");

				String TempSql = subItem.getLeftqueto() + " select distinct "
						+ tempFieldNames.toString().substring(1, tempFieldNames.size() - 1) + " from " + tableWhere
						+ " where 1=1 " + SqlWhere + "  " + subItem.getRightqueto();

				if ("include".equalsIgnoreCase(item)) {
					if (includeSql.size() != 0) {
						includeSql.add(tempadvancedQueryType);
					}
					includeSql.add(TempSql);
				} else if ("exclude".equalsIgnoreCase(item)) {
					if (excludeSql.size() != 0) {
						excludeSql.add(tempadvancedQueryType);
					}
					excludeSql.add(TempSql);
				}

				tempadvancedQueryType = subItem.getSetCombinator();

			}
		}

		// 增加隐藏主键
		fieldNames.add(new FieldName(JLPConts.PatientGlobalTable + ".Id as \"_key\""));
		fieldNames.add(new FieldName(viewTableName + ".Id as \"_dataid\""));

//        #region 组装SQL
		StringBuilder includeStrBuilder = new StringBuilder();
		for (String sql : includeSql) {
			includeStrBuilder.append(sql + " ");
		}
		StringBuilder excludeStrBuilder = new StringBuilder();
		for (String sql : excludeSql) {
			excludeStrBuilder.append(sql + " ");
		}

		String tempSql = "";
		log.info("includeStrBuilder" + includeStrBuilder.toString());
		log.info("excludeStrBuilder" + excludeStrBuilder.toString());

		if (includeStrBuilder.length() > 0) {
			tempSql = "select Id from (" + includeStrBuilder.toString() + ")";
			if (excludeStrBuilder.length() > 0) {
				tempSql += " minus (" + excludeStrBuilder.toString() + ")";
			}

			if (queryOtherTableClassDetails.size() > 0) {
				for (QueryDetailModel model : queryOtherTableClassDetails) {
					if (model.getCondition().toUpperCase().contains("TAG_")) {
						model.setCondition(model.getCondition().toUpperCase().replaceAll("PROJECTTAGS.TAG_",
								"PROJECTTAGSEX.TAG_"));
						Integer fieldType = null;
						for (JTagdicts dicts : tagShowDicts) {
							if (("PROJECTTAGSEX.TAG_" + dicts.getId()).equals(model.getCondition().toUpperCase())) {
								fieldType = model.getFieldType();
							} else {
								fieldType = 6;
							}
						}
						model.setFieldType(fieldType);
					}
				}
				queryOtherTableClassDetails.get(queryOtherTableClassDetails.size() - 1).setCombinator("");

				String OtherTagWhere = "";
				Set<String> OtherTags = new HashSet<String>();
				for (QueryDetailModel model : queryOtherTableClassDetails) {
					OtherTagWhere += model.getCondition() + " and";
					OtherTags.add(model.getCondition().split(".")[1]);
				}
				if (OtherTagWhere.length() > 0) {
					OtherTagWhere = OtherTagWhere.substring(0, OtherTagWhere.length() - 4);
				}
				if (OtherTags != null && OtherTags.size() > 0 && OtherTagWhere.length() > 0) {
					String SqlTagsStr = "select PatientGlobalId as Id from (select PatientGlobalId,('TAG_'||tagid) as tagName,"
							+ " tagValue from j_projecttags where where t.userid = " + userId + " and t.projectid = "
							+ projectQuery.getProjectId() + " and t.tableid <> " + projectQuery.getTableClassId()
							+ ") pivot (max(tagvalue) " + " for name in ("
							+ OtherTags.toString().substring(1, OtherTags.size() - 1) + ")) projecttagsex where "
							+ OtherTagWhere;
					tempSql += " minus (" + SqlTagsStr + ")";
				}
			}
		}
		String projectDataStatusWhere = "";
		if (projectQuery.getProjectstatusIDs() != null && projectQuery.getProjectstatusIDs().length > 0) {
			List<JProjectdatastatusdict> jprojectDataStatusDicts = projectDataStatusdictService
					.getByProjectIdAndProjectstatusIDs(projectQuery.getProjectId(), projectQuery.getProjectstatusIDs());
			Set<String> name = new HashSet<String>();
			for (JProjectdatastatusdict projectdatastatusdict : jprojectDataStatusDicts) {
				name.add(projectdatastatusdict.getName());
			}
			projectDataStatusWhere = " projectdatastatus.tag_complete in" + " ('"
					+ name.toString().substring(1, name.toString().length() - 1) + "')";
			if (jprojectDataStatusDicts.contains("未开始")) {
				projectDataStatusWhere = projectDataStatusWhere + "( or projectdatastatus.tag_complete is null)";
			}
		}
		String projectDelData = "select dataid as datadel_id from j_projectdeldata t"
				+ " where t.activeflag='1' and t.userid=" + userId + " and t.projectid=" + projectQuery.getProjectId()
				+ " and t.tableid=" + projectQuery.getTableClassId();
		String projectPatientDelData = "";
		if (!viewTableName.equalsIgnoreCase(JLPConts.PatientGlobalTable)) {
			projectPatientDelData = "select dataid as datadel_id from j_projectdeldata t where t.activeflag='1' and t.userid={userId} and t.projectid={projectQuery.projectId} and t.tableid=1 ";
		}

		String dataStatusSql = "select distinct j_projectdatastatus.patientglobalid"
				+ ",j_projectdatastatusdict.name as tag_complete"
				+ ",j_projectdatastatus.changestatus as \"_tag_changestatus\""
				+ "  from j_projectdatastatus left join j_projectdatastatusdict"
				+ " on j_projectdatastatus.status=j_projectdatastatusdict.id"
				+ " where j_projectdatastatus.activeflag='1' and j_projectdatastatus.userid= " + userId
				+ " and j_projectdatastatus.projectid= " + projectQuery.getProjectId();
		Set<String> dictsIds = new HashSet<String>();
		for (JTagdicts dicts : tagShowDicts) {
			dictsIds.add("TAG_" + dicts.getId());
		}
		String showTagSql = "select * from (select dataid,('TAG_'||tagid) as tagName,tagValue from j_projecttags"
				+ " where activeflag=1 and userid = " + userId + " and projectid = " + projectQuery.getProjectId()
				+ " and tableid = " + projectQuery.getTableClassId() + "pivot (max(tagvalue) for tagName " + "in ("
				+ dictsIds.toString().substring(1, dictsIds.toString().length() - 1) + ")";
		Set<String> fieldNamesSet = new HashSet<String>();
		for (FieldName name : fieldNames) {
			fieldNamesSet.add(name.getFieldName());
		}
		String where = ToTableStringTagEx(tableNames, tempSql, showTagSql, viewTableName, dataStatusSql, projectDelData,
				projectPatientDelData);
		String Sql = "select distinct " + fieldNamesSet.toString().substring(1, fieldNamesSet.toString().length() - 1)
				+ " from " + where;

		String tempSqlWhere = "";
		if (tempSqlWhere.length() == 0) {
			tempSqlWhere = " where D_PATIENTGLOBAL.jlactiveflag='1'";
		} else {
			tempSqlWhere += " and D_PATIENTGLOBAL.jlactiveflag='1'";
		}

		if (projectQuery.getPatientGlobalId() > 0) {
			tempSqlWhere += " and D_PATIENTGLOBAL.Id={projectQuery.patientGlobalId}";
		}

		if (SqlWhereMain.length() > 0) {
			tempSqlWhere += " and {SqlWhereMain}";
		}

		if (projectDataStatusWhere.length() > 0) {
			tempSqlWhere += " and {projectDataStatusWhere}";
		}

		if (projectDelData.length() > 0) {
			tempSqlWhere += " and projectdeldata.datadel_id is null";
		}

		if (projectPatientDelData.length() > 0) {
			tempSqlWhere += " and projectdelpat.datadel_id is null";
		}

		Sql += tempSqlWhere;

//        #region 组装返回值
		String sqlCount = " select count(1) count from (" + Sql + ") countsql";
		log.info("sqlCount: " + sqlCount);

		int count = jlpService.queryForCount(sqlCount);

		BaseResponse baseResponse = new BaseResponse();
		resultMap.put("response", baseResponse);
		resultMap.put("totals", count);
		resultMap.put("tagTypes", tagShowRets);
		List<Map<String, Object>> retData = jlpService.queryForSQLStreaming(Sql, pageNum, pageSize);
		Long userRole = userService.getUserRoleByToken(authorization);
		if (userRole != null && userRole != 0L) {
			List<JRoleRight> roleRightList = roleRightService.getByRoleId(userRole);
			if (roleRightList != null && roleRightList.size() > 0) {
				JRight jRight = roleRightList.get(0).getjRight();
				if (jRight == null || jRight.getId() != 4L) {
					// 获取敏感字段配置
					Map<String, JSensitiveWord> sensitiveWordMap = (Map<String, JSensitiveWord>) sensitiveWordService
							.getAll4Name().getData();
					if (!sensitiveWordMap.isEmpty()) {
						// 将敏感字段设置为 ***
						retData = JLPServiceImpl.sensitiveWord(retData, sensitiveWordMap, log);
					}
				}
			}

		}

		resultMap.put("columns", columns);
		resultMap.put("content", retData);
		resultMap.put("initialization", false);
		String json = JSONObject.toJSONString(resultMap);
		log.info("response --> " + json);
		return json;
	}

	private String getPageSql(String sql, int pageNum, int pageSize) {
		String pageSql = "SELECT *" + "  FROM (SELECT tt.*, ROWNUM AS rowno" + "          FROM (  " + sql + ") tt"
				+ "         WHERE ROWNUM <= " + pageNum * pageSize + ") table_alias" + " WHERE table_alias.rowno > "
				+ (pageNum - 1) * pageSize;
		return pageSql;
	}

	private String ToTableStringTagEx(SortedSet<String> tableArray, String advancedQueryWhere, String tagSql,
			String TagtableName, String dataStatusSql, String projectDelDataSql, String projectDelPatSql) {
		String ret = "";
		if (tableArray != null) {
			String patientTable = JLPConts.PatientGlobalTable.toLowerCase();
			ret = patientTable;

			if (StringUtils.isNotBlank(advancedQueryWhere)) {
				ret += " inner join (" + advancedQueryWhere + ") a on {patientTable}.Id=a.Id ";
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
				if (item.toUpperCase() == "D_LABMASTERINFO") {
					IsLabMaster = true;
				} else if (item.toUpperCase() == "D_LABRESULTS") {
					IsLabResult = true;
					continue;
				} else if (item.toLowerCase() == patientTable || item.toLowerCase() == "projecttags") {
					continue;
				}
				ret += (IsInner ? "inner" : "left") + "  join " + item + " on " + patientTable + ".Id = " + item
						+ ".patientglobalid ";
			}
			if (IsLabResult) {
				if (!IsLabMaster) {
					ret += (IsInner ? "inner" : "left") + "  join D_LABMASTERINFO on " + patientTable
							+ ".Id = D_LABMASTERINFO.patientglobalid ";
				}
				ret += (IsInner ? "inner" : "left") + " join D_LABRESULTS "
						+ " on D_LABRESULTS.APPLYNO = D_LABMASTERINFO.APPLYNO ";
			}
			if (StringUtils.isNotBlank(tagSql)) {
				ret += " left join (" + tagSql + ") projecttags on " + TagtableName + ".Id=projecttags.dataid ";
			}
			if (StringUtils.isNotBlank(dataStatusSql)) {
				ret += " left join (" + dataStatusSql + ") projectdatastatus on " + TagtableName
						+ ".Id=projectdatastatus.patientglobalid ";
			}
			if (StringUtils.isNotBlank(projectDelDataSql)) {
				ret += " left join (" + projectDelDataSql + ") projectdeldata on " + TagtableName
						+ ".Id=projectdeldata.datadel_id";
			}
			if (StringUtils.isNotBlank(projectDelPatSql)) {
				ret += " left join (" + projectDelPatSql + ") projectdelpat "
						+ " on D_PATIENTGLOBAL.Id=projectdelpat.datadel_id ";
			}
		}
		return ret;
	}

	public static String ToTagStringEx(String[] strArray) {
		String ret = "";
		if (strArray.length > 0) {
			for (String item : strArray) {
				if (ret.length() == 0) {
					ret = "'" + item + "' as " + item;
				} else {
					ret += ",'" + item + "' as " + item;
				}
			}
		}
		return ret;
	}

	// 课题数据查询
	@ApiOperation(value = "课题数据查询")
	@RequestMapping(value = "/api/ProjectQuery/Statics/{Id}/{TableClassId}", method = { RequestMethod.GET })
	public String StaticsAsync(@PathVariable Long Id, @PathVariable Long TableClassId, HttpServletRequest request,
			HttpServletResponse response) {
		JLPLog log = ThreadPoolConfig.getBaseContext();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String authorization = request.getHeader("Authorization") == null ? "" : request.getHeader("Authorization");
		Long userId = userService.getIdByToken(authorization);
		if (userId == null) {
			userId = 0L;
		}

//        #region 验证视图编号
		JTableclassdict tableclassdict = tableclassdictService.getOne(TableClassId);

		if (tableclassdict == null) {
			BaseResponse baseResponse = new BaseResponse();
			response.setStatus(404);
			baseResponse.setCode("404");
			baseResponse.setText("数据分类信息错误");
			resultMap.put("response", baseResponse);
			return JSONObject.toJSONString(resultMap);
		}

		JShowView showview = showViewService.getOne(tableclassdict.getViewid().longValue());
		if (showview == null) {
			BaseResponse baseResponse = new BaseResponse();
			baseResponse.setCode("0000");
			baseResponse.setText("视图编号错误");
			resultMap.put("response", baseResponse);
			return JSONObject.toJSONString(resultMap);
		}

//        #region 获取显示视图信息
		String viewTableName = "";
		JTableDict mainTable = null;
		List<JShowDetailView> jShowDetailViewList = jShowDetailViewService.getByViewId(showview.getId());
//      
		List<JTableFieldDict> jTableFieldDictList = new ArrayList<JTableFieldDict>();
		for (JShowDetailView jShowDetailView : jShowDetailViewList) {
			long fieldId = jShowDetailView.getFieldid();
			JTableFieldDict jTableFieldDict = jTableFieldDictService.getOne(fieldId, true);
			if (jTableFieldDict == null) {
				log.warn("fieldId 为 " + fieldId + " 未找到j_tablefielddict 记录 ");
				continue;
			}
			jTableFieldDictList.add(jTableFieldDict);
			jTableFieldDict.setjShowDetailView(jShowDetailView);
			jShowDetailView.setjTableFieldDict(jTableFieldDict);
			JTableDict jTableDict = jTableDictService.getByCode(jTableFieldDict.getTablecode(), true);
			jShowDetailView.setjTableDict(jTableDict);
			if (mainTable == null) {
				mainTable = jTableDict;
			}
		}

		if (jTableFieldDictList.size() == 0) {
			BaseResponse baseResponse = new BaseResponse();
			baseResponse.setCode("0000");
			baseResponse.setText("显示字段未配置");
			resultMap.put("response", baseResponse);
			String json = JSONObject.toJSONString(resultMap);
			log.info("response --> " + json);
			return json;
		}

		viewTableName = mainTable.getName();

//        #region 获取当前查询条件
		SortedSet<String> tableNames = new TreeSet<String>();
		Set<String> fieldNames = new HashSet<String>();
		Set<String> fieldShowNames = new HashSet<String>();
		for (JShowDetailView dict : jShowDetailViewList) {
			tableNames.add(dict.getjTableDict().getName());
			fieldNames.add((dict.getjTableDict().getName() + "." + dict.getFieldname()).toLowerCase());
			fieldShowNames.add(dict.getFieldname().toLowerCase());
		}
		tableNames.remove(JLPConts.PatientGlobalTable);

		List<String> includeSql = new ArrayList<String>();
		List<String> excludeSql = new ArrayList<String>();

		Map<String, List<QueryCollectionModel>> queryList = new HashMap<String, List<QueryCollectionModel>>();
		// queryRecord
		AdvancedQueryRecordModel projectQueryConds = null;

		try {
			projectQueryConds = projectService.getQueryRecord(Id, userId);
		} catch (Exception e) {
			log.error(e);
		}

		if (projectQueryConds.getQueries().getQueryIncludesEX().getIncludes() != null) {
			queryList.put("include", projectQueryConds.getQueries().getQueryIncludesEX().getIncludes());
		}

		if (projectQueryConds.getQueries().getQueryIncludesEX().getExcludes() != null) {
			queryList.put("exclude", projectQueryConds.getQueries().getQueryIncludesEX().getExcludes());
		}

		if (projectQueryConds.getQueries().getQueryConds() != null) {
			QueryCollectionModel queryCollection = new QueryCollectionModel();
			queryCollection.setConds(projectQueryConds.getQueries().getQueryConds());
			queryCollection.setLeftqueto("(");
			queryCollection.setRightqueto("");
			queryCollection.setSetCombinator("1");
			queryCollection.setId("");
			queryCollection.setType("");
			if (!queryList.containsKey("include")) {
				queryList.put("include", new ArrayList<QueryCollectionModel>());
			}
			List<QueryCollectionModel> relQCL = queryList.get("include");
			if (relQCL.size() > 0) {
				relQCL.get(0).setLeftqueto("(" + relQCL.get(0).getLeftqueto());
				relQCL.get(relQCL.size() - 1).setRightqueto(relQCL.get(relQCL.size() - 1).getRightqueto() + ")");
			}
			relQCL.add(queryCollection);
		}

		String tempadvancedQueryType = "";
		for (String item : queryList.keySet()) {
			for (QueryCollectionModel subItem : queryList.get(item)) {
				SortedSet<String> tempTableNames = new TreeSet<String>();
				List<String> tempFieldNames = new ArrayList<String>();

				tempFieldNames.add(JLPConts.PatientGlobalTable + ".Id");

				List<QueryDetailModel> queryDetails = new ArrayList<QueryDetailModel>();

				if (subItem.getConds() == null) {
					continue;
				}

				queryDetails.addAll(subItem.getConds());

//                #region 获取查询所有表名
//                var tableNameWheres = from a in queryDetails
//                                      from b in a
//                                      select b.condition.Split('.').FirstOrDefault().ToUpper();
//                var tableNameWhereValues = from a in queryDetails
//                                           from b in a
//                                           where b.fieldType == FieldValueType.Field
//                                           select b.condValue.Split('.').FirstOrDefault().ToUpper();
//                var whereFields = from a in queryDetails
//                                  from b in a
//                                  select b.condition.ToUpper();
//
//                var whereFieldTypes = (from a in orclJlpContext.JTabledicts
//                                       join b in orclJlpContext.JTablefielddicts on a.Code equals b.Tablecode into tablefields
//                                       from b in tablefields
//                                       where a.Activeflag == "1" && whereFields.Contains(a.Name + "." + b.Name)
//                                       select new { field = a.Name + "." + b.Name, type = b.Type }).Distinct().ToList();
//                queryDetails.ForEach(x =>
//                {
//                    x.ForEach(y =>
//                    {
//                        if (y.fieldType != FieldValueType.Field)
//                        {
//                            y.fieldType = (whereFieldTypes.FirstOrDefault(m => m.field == y.condition.ToUpper())?.type.toFieldValueType()) ?? FieldValueType.String;
//                        }
//                    });
//                    if (x.LastOrDefault() != null)
//                    {
//                        x.LastOrDefault().combinator = "";
//                    }
//                });

				Set<String> tableNameWheres = new HashSet<String>();
				Set<String> tableNameWhereValues = new HashSet<String>();
				Set<String> whereFields = new HashSet<String>();
				Set<String> whereFieldTypes = new HashSet<String>();

				String SqlWhere = " ";
				for (QueryDetailModel queryDetailModel : queryDetails) {
					tableNameWheres.add(queryDetailModel.getCondition().split(".")[0]);
					if (queryDetailModel.getFieldType() == 1) {
						tableNameWhereValues.add(queryDetailModel.getCondValue().split(".")[0]);
					}
					whereFields.add(queryDetailModel.getCondition().toUpperCase());

					if (!queryDetailModel.getRelation().contains("null")) {
						SqlWhere += queryDetailModel.getCondition() + " " + queryDetailModel.getRelation() + " "
								+ queryDetailModel.getCondValue() + " and ";
					} else {
						SqlWhere += queryDetailModel.getCondition() + " " + queryDetailModel.getRelation() + " "
								+ " and ";
					}

				}
				// 去掉最后的and
				if (SqlWhere.length() > 4) {
					SqlWhere = SqlWhere.substring(0, SqlWhere.length() - 4);
				}

//                var SqlWhere = queryDetails.Select(x => x.ToArray().ToStringEx(" ")).ToArray().ToStringEx(" and ");
				tempTableNames.addAll(tableNameWheres);
				tempTableNames.addAll(tableNameWhereValues);
				tempTableNames.remove(JLPConts.PatientGlobalTable);

//                #region 获取查询结果和数量
				String tableWhere = JLPServiceImpl.toTableString(tempTableNames, "");

				String TempSql = subItem.getLeftqueto() + " select distinct "
						+ tempFieldNames.toString().substring(1, tempFieldNames.toString().length() - 1) + " from "
						+ tableWhere + " where 1=1   " + (StringUtils.isBlank(SqlWhere) ? "" : " and " + SqlWhere) + " "
						+ subItem.getRightqueto();

				if ("include".equals(item)) {
					if (includeSql.size() != 0) {
						includeSql.add(tempadvancedQueryType);
					}
					includeSql.add(TempSql);
				} else if ("exclude".equals(item)) {
					if (excludeSql.size() != 0) {
						excludeSql.add(tempadvancedQueryType);
					}
					excludeSql.add(TempSql);
				}

				tempadvancedQueryType = subItem.getSetCombinator();
			}
		}

		// 增加隐藏主键
		fieldNames.add(JLPConts.PatientGlobalTable + ".Id as \"_key\"");
		fieldNames.add(viewTableName + ".Id as \"_dataid\"");

//        #region 组装SQL
		StringBuilder includeStrBuilder = new StringBuilder();

		for (String sql : includeSql) {
			includeStrBuilder.append(sql + " ");
		}
		StringBuilder excludeStrBuilder = new StringBuilder();
		for (String sql : excludeSql) {
			excludeStrBuilder.append(sql + " ");
		}

		String tempSql = "";
		log.info("includeStrBuilder" + includeStrBuilder.toString());
		log.info("excludeStrBuilder" + excludeStrBuilder.toString());

		if (includeStrBuilder.length() > 0) {
			tempSql = "select Id from (" + includeStrBuilder.toString() + ")";
			if (excludeStrBuilder.length() > 0) {
				tempSql += " minus (" + excludeStrBuilder.toString() + ")";
			}
		}
		String table = JLPServiceImpl.toTableString(tableNames, tempSql);
		String Sql = "select distinct  " + fieldNames.toString().substring(1, fieldNames.toString().length() - 1)
				+ " from " + table;

		String tempSqlWhere = "";
		if (tempSqlWhere.length() == 0) {
			tempSqlWhere = " where D_PATIENTGLOBAL.jlactiveflag='1'";
		} else {
			tempSqlWhere += " and D_PATIENTGLOBAL.jlactiveflag='1'";
		}
		Sql += tempSqlWhere;

//        #region 组装返回值
		String sqlCount = " select count(1) count from (" + Sql + ") countsql";
		log.info("sqlCount: " + sqlCount);
		int count = jlpService.queryForCount(sqlCount);

		BaseResponse baseResponse = new BaseResponse();
		resultMap.put("totals", count);

		String tagClass = String.valueOf(TableClassId);
		long tempCount = (long) count;

		List<JProjectstatistics> projectStatistics = projectstatisticsService.getByProjectIdAndStatisticstypedataid(Id,
				TableClassId);
		if (projectStatistics.size() > 0) {
			for (JProjectstatistics projectstatistics : projectStatistics) {
				projectstatistics.setCount(tempCount);
				projectstatisticsService.update(projectstatistics);
			}
		} else {
			JProjectstatistics projectstatistics = new JProjectstatistics();
			projectstatistics.setActiveflag(JLPConts.ActiveFlag);
			projectstatistics.setCount(tempCount);
			projectstatistics.setCreatedate(new Date());
			projectstatistics.setProjectid(Id);
			projectstatistics.setStatisticstype("1");
			projectstatistics.setStatisticstypedataid(TableClassId);
			long seqId = jlpService.getNextSeq("J_PROJECTSTATISTICS");
			projectstatistics.setId(seqId);
			projectstatisticsService.save(projectstatistics);
		}
		resultMap.put("response", baseResponse);
		String json = JSONObject.toJSONString(resultMap);
		log.info("response--> " + json);
		return json;
	}
}
