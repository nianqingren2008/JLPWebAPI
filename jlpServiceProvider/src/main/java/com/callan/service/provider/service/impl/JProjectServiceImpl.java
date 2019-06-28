package com.callan.service.provider.service.impl;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.alibaba.fastjson.JSONObject;
import com.callan.service.provider.config.JLPConts;
import com.callan.service.provider.config.JLPException;
import com.callan.service.provider.config.JLPLog;
import com.callan.service.provider.config.ThreadPoolConfig;
import com.callan.service.provider.dao.mapper.JProjectMapper;
import com.callan.service.provider.pojo.advanceQueryBase.AdvancedQueryRecordModel;
import com.callan.service.provider.pojo.advanceQueryBase.Queries;
import com.callan.service.provider.pojo.advanceQueryBase.QueryDetailModel;
import com.callan.service.provider.pojo.advanceQueryBase.QueryIncludesEX;
import com.callan.service.provider.pojo.advanceQueryBase.QueryCollectionModel;
import com.callan.service.provider.pojo.base.BaseResponse;
import com.callan.service.provider.pojo.db.JAdvancedqr;
import com.callan.service.provider.pojo.db.JAdvancedqrItem;
import com.callan.service.provider.pojo.db.JProject;
import com.callan.service.provider.pojo.db.JProjectstatistics;
import com.callan.service.provider.pojo.db.JQueryrecordDetails;
import com.callan.service.provider.pojo.db.JShowDetailView;
import com.callan.service.provider.pojo.db.JShowView;
import com.callan.service.provider.pojo.db.JTableDict;
import com.callan.service.provider.pojo.db.JTableFieldDict;
import com.callan.service.provider.pojo.tableclassdict.JTableclassdict;
import com.callan.service.provider.service.IJAdvancedqrItemService;
import com.callan.service.provider.service.IJAdvancedqrService;
import com.callan.service.provider.service.IJLpService;
import com.callan.service.provider.service.IJProjectDataStatusdictService;
import com.callan.service.provider.service.IJProjectService;
import com.callan.service.provider.service.IJProjectstatisticsService;
import com.callan.service.provider.service.IJQueryrecordDetailService;
import com.callan.service.provider.service.IJQueryrecordService;
import com.callan.service.provider.service.IJRoleRightService;
import com.callan.service.provider.service.IJSensitiveWordService;
import com.callan.service.provider.service.IJShowDetailViewService;
import com.callan.service.provider.service.IJShowViewService;
import com.callan.service.provider.service.IJTableDictService;
import com.callan.service.provider.service.IJTableFieldDictService;
import com.callan.service.provider.service.IJTableclassdictService;
import com.callan.service.provider.service.IJTagdictService;
import com.callan.service.provider.service.IJUserService;

@Service
public class JProjectServiceImpl implements IJProjectService {
	@Autowired
	private JProjectMapper  projectMapper;

	@Autowired
	private IJAdvancedqrService advancedqrService;

	@Autowired
	private IJAdvancedqrItemService advancedqrItemService;

	@Autowired
	private IJQueryrecordService queryrecordService;

	@Autowired
	private IJQueryrecordDetailService queryrecordDetailService;
	
	
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
	
	@Override
	public JProject getOne(Long id) {
		return projectMapper.selectByPrimaryKey(id);
	}


	@Override
	public List<JProject> getByUserId(long userId) {
		return projectMapper.getByUserId(userId);
	}


	@Override
	public void save(JProject project) {
		projectMapper.insert(project);
	}


	@Override
	public void update(JProject project) {
		projectMapper.updateByPrimaryKeySelective(project);
	}
	
	@Override
	public AdvancedQueryRecordModel getQueryRecord(Long Id,Long userId) {
		JAdvancedqr jAdvancedqr = null;
		List<JAdvancedqr> jAdvancedqrList = advancedqrService.getByProjectId(Id);
		if (jAdvancedqrList != null && jAdvancedqrList.size() > 0) {
			if (jAdvancedqrList.get(0).getUserid().longValue() == userId.longValue()) {
				jAdvancedqr = jAdvancedqrList.get(0);
			}
		}
		if (jAdvancedqr == null) {
			throw new JLPException("不存在此项目条件记录");
		}
		List<JAdvancedqrItem> dqrItemList = advancedqrItemService.getByQrId(jAdvancedqr.getId());


		AdvancedQueryRecordModel advancedQueryRecord = new AdvancedQueryRecordModel();
		advancedQueryRecord.setId(jAdvancedqr.getId());
		advancedQueryRecord.setName(jAdvancedqr.getAqname());
		advancedQueryRecord.setProjectId(jAdvancedqr.getProjectid() == null ? 0 : jAdvancedqr.getProjectid());
		advancedQueryRecord.setQueries(new Queries());
		List<JAdvancedqrItem> dqrItemListNew = new ArrayList<JAdvancedqrItem>();
		for (JAdvancedqrItem advancedqrItem : dqrItemList) {
			if ("1".equals(advancedqrItem.getItemtype())) {
				List<JQueryrecordDetails> detailList = queryrecordDetailService
						.getByQueryId(advancedqrItem.getQueryid());
				advancedqrItem.setDetailList(detailList);
				dqrItemListNew.add(advancedqrItem);
			}
		}
		
		List<QueryDetailModel> queryConds = new ArrayList<QueryDetailModel>();
		List<JAdvancedqrItem> dqrItemListInclude = new ArrayList<JAdvancedqrItem>();
		List<JAdvancedqrItem> dqrItemListExclude = new ArrayList<JAdvancedqrItem>();
		for (JAdvancedqrItem advancedqrItem : dqrItemListNew) {
			List<JQueryrecordDetails> detailList = advancedqrItem.getDetailList();
			Collections.sort(detailList, new Comparator<JQueryrecordDetails>() {
				@Override
				public int compare(JQueryrecordDetails o1, JQueryrecordDetails o2) {
					return o1.getQueryid().compareTo(o2.getQueryid());
				}
			});

			Collections.sort(detailList, new Comparator<JQueryrecordDetails>() {
				@Override
				public int compare(JQueryrecordDetails o1, JQueryrecordDetails o2) {
					return o1.getDetailid().compareTo(o2.getDetailid());
				}
			});
			for(JQueryrecordDetails detail : detailList) {
				QueryDetailModel queryDetailModel = new QueryDetailModel();
				queryDetailModel.setCombinator(detail.getLogicaltype());
				queryDetailModel.setCondition(detail.getFieldname());
				queryDetailModel.setCondValue(detail.getFieldvalue());
				queryDetailModel.setFieldType(detail.getFieldvaluetype());
				queryDetailModel.setLeftqueto(detail.getLeftbrackets());
				queryDetailModel.setRelation(detail.getRelationtype());
				queryDetailModel.setRightqueto(detail.getRightbrackets());
				queryConds.add(queryDetailModel);
			}

			if ("2".equals(advancedqrItem.getItemtype())) {
				dqrItemListInclude.add(advancedqrItem);
			}

			if ("3".equals(advancedqrItem.getItemtype())) {
				dqrItemListExclude.add(advancedqrItem);
			}
		}
		advancedQueryRecord.getQueries().setQueryConds(queryConds);
		advancedQueryRecord.getQueries().setQueryIncludesEX(new QueryIncludesEX());

		excueteEx(advancedQueryRecord, dqrItemListInclude);
		excueteEx(advancedQueryRecord, dqrItemListExclude);
		return advancedQueryRecord;
	}
	private void excueteEx(AdvancedQueryRecordModel advancedQueryRecord, List<JAdvancedqrItem> dqrItemListInclude) {
		List<QueryCollectionModel> include = new ArrayList<QueryCollectionModel>();
		advancedQueryRecord.getQueries().getQueryIncludesEX().setIncludes(include);

		Collections.sort(dqrItemListInclude, new Comparator<JAdvancedqrItem>() {
			@Override
			public int compare(JAdvancedqrItem o1, JAdvancedqrItem o2) {
				return o1.getItemno().compareTo(o2.getItemno());
			}
		});

		for (JAdvancedqrItem advancedqrItem : dqrItemListInclude) {
			QueryCollectionModel queryCollectionModel = new QueryCollectionModel();
			queryCollectionModel.setId(advancedqrItem.getModelid());
			queryCollectionModel.setLeftqueto(advancedqrItem.getLeftqueto());
			queryCollectionModel.setRightqueto(advancedqrItem.getRightqueto());
			queryCollectionModel.setSetCombinator(advancedqrItem.getSetcombinatortype());
			queryCollectionModel.setType(advancedqrItem.getModeltype());
			List<QueryDetailModel> condsList = new ArrayList<QueryDetailModel>();

			List<JQueryrecordDetails> detailList = advancedqrItem.getDetailList();
			Collections.sort(detailList, new Comparator<JQueryrecordDetails>() {
				@Override
				public int compare(JQueryrecordDetails o1, JQueryrecordDetails o2) {
					return o1.getQueryid().compareTo(o2.getQueryid());
				}
			});

			Collections.sort(detailList, new Comparator<JQueryrecordDetails>() {
				@Override
				public int compare(JQueryrecordDetails o1, JQueryrecordDetails o2) {
					return o1.getDetailid().compareTo(o2.getDetailid());
				}
			});
			for (JQueryrecordDetails detail : detailList) {
				QueryDetailModel queryDetailModel = new QueryDetailModel();
				queryDetailModel.setCombinator(detail.getLogicaltype());
				queryDetailModel.setCondition(detail.getFieldname());
				queryDetailModel.setCondValue(detail.getFieldvalue());
				queryDetailModel.setFieldType(detail.getFieldvaluetype());
				queryDetailModel.setLeftqueto(detail.getLeftbrackets());
				queryDetailModel.setRelation(detail.getRelationtype());
				queryDetailModel.setRightqueto(detail.getRightbrackets());
				condsList.add(queryDetailModel);
			}

			queryCollectionModel.setConds(condsList);
			include.add(queryCollectionModel);
		}

	}


	@Override
	public String staticAsync( Long Id,   Long TableClassId, HttpServletRequest request,
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
