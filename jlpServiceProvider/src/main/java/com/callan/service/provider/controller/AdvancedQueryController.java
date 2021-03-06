package com.callan.service.provider.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
//import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.callan.service.provider.config.JLPConts;
import com.callan.service.provider.config.JLPLog;
import com.callan.service.provider.config.ThreadPoolConfig;
import com.callan.service.provider.pojo.AdvanceQueryRequest;
import com.callan.service.provider.pojo.AdvanceQueryResponse;
import com.callan.service.provider.pojo.advanceQueryBase.ColunmsModel;
import com.callan.service.provider.pojo.advanceQueryBase.QueryCollectionModel;
import com.callan.service.provider.pojo.advanceQueryBase.QueryDetailModel;
import com.callan.service.provider.pojo.advanceQueryBase.QueryIncludesEX;
import com.callan.service.provider.pojo.advanceQueryBase.Sorted;
import com.callan.service.provider.pojo.base.FieldName;
import com.callan.service.provider.pojo.db.JShowDetailView;
import com.callan.service.provider.pojo.db.JShowView;
import com.callan.service.provider.pojo.db.JSystemconfig;
import com.callan.service.provider.pojo.db.JTableDict;
import com.callan.service.provider.pojo.db.JTableFieldDict;
import com.callan.service.provider.pojo.db.JUser;
import com.callan.service.provider.service.IJLpService;
import com.callan.service.provider.service.IJShowDetailViewService;
import com.callan.service.provider.service.IJShowViewService;
import com.callan.service.provider.service.IJSystemConfigService;
import com.callan.service.provider.service.IJTableDictService;
import com.callan.service.provider.service.IJTableFieldDictService;
import com.callan.service.provider.service.IJUserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(description = "病例检索查询")
public class AdvancedQueryController {

	@Autowired
	private IJShowViewService jShowviewService;

	@Autowired
	private IJShowDetailViewService jShowDetailViewService;

	@Autowired
	private IJTableDictService jTableDictService;

	@Autowired
	private IJTableFieldDictService jTableFieldDictService;

	@Autowired
	private IJLpService jlpService;

//	@Autowired
//	private IJRightService jRightService;

	@Autowired
	private IJUserService jUserService;

	@Autowired
	private IJSystemConfigService systemConfigService;

	@ApiOperation(value = "病例检索模糊查询(仅列表)")
	@RequestMapping(value = "/api/AdvanceQuery", method = { RequestMethod.POST })
	public String query1(@RequestBody String advanceQuery, String pageNum, String pageSize,Boolean totals,Boolean includeTotals,
			HttpServletRequest request) {
		return query(advanceQuery, pageNum, pageSize,totals,includeTotals, request);
	}

	@ApiOperation(value = "病例检索模糊查询(仅列表)")
	@RequestMapping(value = "/api/AdvancedQuery", method = { RequestMethod.POST })
	public String query(@RequestBody String advanceQuery, String pageNum,String pageSize,Boolean totals,Boolean includeTotals, HttpServletRequest request) {
		JLPLog log = ThreadPoolConfig.getBaseContext();
		long start = System.currentTimeMillis();
		log.info("request --> " + advanceQuery);
		AdvanceQueryRequest advanceQueryRequest = null;
		try {
			advanceQueryRequest = JSONObject.toJavaObject(JSONObject.parseObject(advanceQuery),
					AdvanceQueryRequest.class);
		} catch (Exception e) {
			log.error(e);
		}
//		boolean isTotals = false;
		int pageNumInt = StringUtils.isBlank(pageNum) ? 1 : Integer.parseInt(pageNum);
		int pageSizeInt = StringUtils.isBlank(pageSize) ? 20 : Integer.parseInt(pageSize);
		AdvanceQueryResponse response = new AdvanceQueryResponse();
		if (advanceQueryRequest == null) {
			response.getResponse().setCode("0000");
			response.getResponse().setText("传入参数为空");
			String json = response.toJsonString();
			log.info("response --> " + json);
			return json;
		}
		if (advanceQueryRequest.getViewId() == 0) {
			response.getResponse().setCode("0000");
			response.getResponse().setText("视图编号错误");
			String json = response.toJsonString();
			log.info("response --> " + json);
			return json;
		}

		JShowView jShowView = jShowviewService.getOne(advanceQueryRequest.getViewId(), true);
		if (jShowView == null) {
			response.getResponse().setCode("0000");
			response.getResponse().setText("视图编号错误");
			String json = response.toJsonString();
			log.info("response --> " + json);
			return json;
		}

		JTableDict mainTable = null;

		// 获取显示视图信息
		List<JShowDetailView> jShowDetailViewList = jShowDetailViewService.getByViewId(jShowView.getId());
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
		if (advanceQueryRequest.getQueryShowFields() != null) {
			List<Long> tempFieldList = new ArrayList<Long>();
			for (Long field : advanceQueryRequest.getQueryShowFields()) {
				tempFieldList.add(field);
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
			response.getResponse().setCode("1001");
			response.getResponse().setText("显示字段未配置");
			String json = response.toJsonString();
			log.info("response --> " + json);
			return json;
		}

		// 获取查询所有表名
		List<QueryDetailModel> queryCondsList = advanceQueryRequest.getQueries().getQueryConds();
		SortedSet<String> tableNameMainWheres = new TreeSet<String>();
		SortedSet<String> tableNameWhereMainValues = new TreeSet<String>();
		// List<String> whereFieldsMain = new ArrayList<String>();
		// List<String> whereFieldTypesMain = new ArrayList<String>();
		String patientTableWhere = " where 1=1 ";
		String sqlWhereMain = "";
		Map<String, List<String>> tableWhere = new HashMap<String, List<String>>();
		if (queryCondsList != null) {

			for (QueryDetailModel queryConds : queryCondsList) {
				String tableName = queryConds.getCondition().split("\\.")[0].toUpperCase();
				tableNameMainWheres.add(tableName);
				String fieldName = queryConds.getCondition().split("\\.")[1].toUpperCase();
				//
				if (queryConds.getFieldType() == 1) {
					tableNameWhereMainValues.add(queryConds.getCondValue().split("\\.")[0].toUpperCase());
				}
				String condition = queryConds.getCondition().toUpperCase();
				JTableDict jTableDict = jTableDictService.getByName(tableName, true);
				JTableFieldDict jTableFieldDict = jTableFieldDictService.getByTableCodeAndName(jTableDict.getCode(),
						fieldName);
				// whereFieldTypesMain.add(jTableFieldDict.getType());

				String relation = queryConds.getRelation();
				// 如果是is not null 则不拼装value
				if (!relation.contains("null")) {
					String condValue = queryConds.getCondValue() == null ? "" : queryConds.getCondValue();
					if ("like".equalsIgnoreCase(relation)) {
						condValue = "%" + condValue + "%";
					}
					if ("VARCHAR2".equalsIgnoreCase(jTableFieldDict.getType())
							|| "CHAR".equalsIgnoreCase(jTableFieldDict.getType())) {
						condValue = "'" + condValue + "'";
					}
					if ("DATE".equalsIgnoreCase(jTableFieldDict.getType())) {
						condValue = "to_date('" + condValue + "','yyyy/mm/dd')";
					}
					sqlWhereMain += condition + " " + queryConds.getRelation() + " " + condValue + " and ";
					List<String> list = tableWhere.get(tableName);
					if (list == null) {
						list = new ArrayList<String>();
					}
					list.add(" and " + condition + " " + queryConds.getRelation() + " " + condValue);
					if (condition.contains(JLPConts.PatientGlobalTable)) {
						patientTableWhere += " and " + condition + " " + queryConds.getRelation() + " " + condValue;
					}
				} else {
					sqlWhereMain += condition + " " + queryConds.getRelation() + " " + " and ";
				}

			}
		}
		// 去掉最后的and
		if (sqlWhereMain.length() > 4) {
			sqlWhereMain = sqlWhereMain.substring(0, sqlWhereMain.length() - 4);
		}
//        queryMainDetails.ForEach(x =>
//        {
//            x.ForEach(y =>
//            {
//                if (y.fieldType != FieldValueType.Field)
//                {
//                    y.fieldType = (whereFieldTypesMain.FirstOrDefault(m => m.field == y.condition.ToUpper())
//                        ?.type.toFieldValueType()) ?? FieldValueType.String;
//                }
//            });
//            if (x.LastOrDefault() != null)
//            {
//                x.LastOrDefault().combinator = "";
//            }
//        });
		SortedSet<String> tableNames = new TreeSet<String>();
		List<FieldName> fieldNames = new ArrayList<FieldName>();
		// 增加隐藏主键
		fieldNames.add(new FieldName(JLPConts.PatientGlobalTable + ".Id as hide_key"));
		fieldNames.add(new FieldName(JLPConts.PatientGlobalTable + ".Id as \"_key\""));
		SortedSet<String> fieldShowNames = new TreeSet<String>();
		SortedSet<String> showTableNames = new TreeSet<String>();

		List<ColunmsModel> columns = new ArrayList<ColunmsModel>();

		for (JShowDetailView showDetailView : jShowDetailViewListShow) {
			if (showDetailView.getjTableDict() != null) {
				tableNames.add(showDetailView.getjTableDict().getName());
				showTableNames.add(showDetailView.getjTableDict().getName());
				fieldNames.add(new FieldName(
						(showDetailView.getjTableDict().getName() + "." + showDetailView.getjTableFieldDict().getName())
								.toLowerCase()));

			}
			if (showDetailView.getjTableFieldDict() != null) {
				fieldShowNames.add(showDetailView.getjTableFieldDict().getName().toLowerCase());
			}
			JTableFieldDict jTableFieldDict = showDetailView.getjTableFieldDict();
			if (jTableFieldDict != null) {
				ColunmsModel colunmsModel = new ColunmsModel();
				colunmsModel.setDataIndex(jTableFieldDict.getName().toLowerCase());
				colunmsModel.setKey(jTableFieldDict.getName().toLowerCase());
				colunmsModel.setTitle(jTableFieldDict.getjShowDetailView().getFieldtitle());
				colunmsModel.setIsLongStr("clob".equals(jTableFieldDict.getType()));
				colunmsModel.setIsSearched(JLPConts.ActiveFlag.equals(jTableFieldDict.getQueryflag()));
				columns.add(colunmsModel);
			}

		}
		tableNames.addAll(tableNameMainWheres);

		boolean IsImageUrl = false;
		boolean IsPathImageUrl = false;
		JSystemconfig systemconfig = (JSystemconfig) systemConfigService.getByClassTypeAndKeyName("image", "Html")
				.getData();
		String imageUrl = null;
		if (systemconfig != null) {
			imageUrl = systemconfig.getKeyvalue();
		}
		String imageKey = null;
		systemconfig = (JSystemconfig) systemConfigService.getByClassTypeAndKeyName("image", "fieldName").getData();
		if (systemconfig != null) {
			imageKey = systemconfig.getKeyvalue();
		}
		String imageField = "_examimage";
		String pathImageUrl = null;
		systemconfig = (JSystemconfig) systemConfigService.getByClassTypeAndKeyName("image", "pathHtml").getData();
		if (systemconfig != null) {
			pathImageUrl = systemconfig.getKeyvalue();
		}

		String pathImageKey = null;
		systemconfig = (JSystemconfig) systemConfigService.getByClassTypeAndKeyName("image", "pathFieldName").getData();
		if (systemconfig != null) {
			pathImageKey = systemconfig.getKeyvalue();
		}

		String pathImageField = "_pathimage";
		for (String str : tableNames) {
			if (imageKey.contains(str)) {
				IsImageUrl = true;
			}
			if (pathImageKey.contains(str)) {
				IsPathImageUrl = true;
			}
		}

		if (IsImageUrl) {
			fieldNames.add(new FieldName(imageKey + "  as \"" + imageField + "\""));
		} else {
			fieldNames.add(new FieldName("'' as \"" + imageField + "\""));
		}

		if (IsPathImageUrl) {
			fieldNames.add(new FieldName(pathImageKey + " as \"" + pathImageField + "\""));
		} else {
			fieldNames.add(new FieldName("'' as \"" + pathImageField + "\""));
		}

//		for(String tableName : tableNames) {
//			if(PatientGlobalTable.equals(tableName.toUpperCase())) {
//				tableNames.remove(tableName);
//			}
//		}

//        String SqlWhereMain = queryMainDetails.Select(x => x.ToArray().ToStringEx(" ")).ToArray().ToStringEx(" and ");
//        tableNames.AddRange(tableNameMainWheres);
//        tableNames.AddRange(tableNameWhereMainValues);
//        tableNames = tableNames.Distinct().ToList();
//        tableNames.RemoveAll(x => x.ToUpper() == JLPStaticProperties.PatientGlobalTable);
//
		List<Sorted> sortedList = advanceQueryRequest.getSorted();
		// 排序字段
		String sorts = "";
		if(sortedList != null) {
			for (String showField : fieldShowNames) {
				for (Sorted sorted : sortedList) {
					if (showField.equalsIgnoreCase(sorted.getId())) {
						sorts += showField + ",";
					}
				}
			}
		}
		if (sorts.length() > 0) {
			sorts = sorts.substring(0, sorts.length() - 1);
		}

//		String includeSqlList = "";
//		String tempSql = "";
//		String tempadvancedQueryType = "";

//		List<QueryIncludesEXCondition> includes = new ArrayList<QueryIncludesEXCondition>();
//		List<QueryIncludesEXCondition> excludes = new ArrayList<QueryIncludesEXCondition>();
		QueryIncludesEX queryIncludesEX = advanceQueryRequest.getQueries().getQueryIncludesEX();
//		if(queryIncludesEX != null) {
//			includes = queryIncludesEX.getIncludes();
//			excludes = queryIncludesEX.getExcludes();
//		}
		// 处理纳入
//		for (QueryIncludesEXCondition queryIncludesEXCondition : includes) {
		String tempSql = queryEx(queryIncludesEX, tableWhere);
//		}
		// 处理排除

//		for (QueryIncludesEXCondition queryIncludesEXCondition : excludes) {
//			excludeSqlList = queryEx(queryIncludesEX,tempadvancedQueryType);
//		}

//		String includeBuilder = "";
//		for (String includeSql : includeSqlList) {
//			if(includeBuilder.length() > 0) {
//				includeBuilder += " inner join " + includeSqlList ;
//			}else {
//				includeBuilder += "  " + includeSqlList ;
//			}
//		}
//		StringBuilder excludeBuilder = new StringBuilder();
//		for (String excludeSql : excludeSqlList) {
//			excludeBuilder.append(includeSqlList);
//		}

		for (int i = 0; i < fieldNames.size(); i++) {
			FieldName fieldName = fieldNames.get(i);
			if (fieldName.getFieldName().toUpperCase().contains("BIRTHDAY")
					&& !fieldName.getFieldName().contains("to_char")) {
				fieldName.setFieldName("to_char(" + fieldName.getFieldName() + ",'yyyy/mm/dd') as birthday");
			}
		}

		patientTableWhere += " and " + JLPConts.PatientGlobalTable + ".jlactiveflag='1'";

		String tempSqlWhere = " where 1=1 and " + JLPConts.PatientGlobalTable + ".jlactiveflag='1' ";
		;
		if (sqlWhereMain.length() > 0) {
			tempSqlWhere += " and  " + sqlWhereMain;
		}
		tempSqlWhere += " and " + mainTable.getName() + ".Id is not null";

		if (advanceQueryRequest.getPatientGlobalId() != null
				&& advanceQueryRequest.getPatientGlobalId().longValue() > 0) {
			tempSqlWhere += " and " + JLPConts.PatientGlobalTable + ".Id = " + advanceQueryRequest.getPatientGlobalId();
		}

//        #region 组装返回值
//		String sortStr = PatientGlobalTable + "__Id";
//
		String finalSelectFields = fieldNames.toString().substring(1, fieldNames.toString().length() - 1);
		String finalTables4Count = toTableString(tableNames, tempSql);

		String tableKeys = toTableKeys(showTableNames);

		String sql = "select distinct " + tableKeys + " from " + finalTables4Count + " " + tempSqlWhere;
		String sqlCount = " select count(1) count from (" + sql + ") countsql";
		log.info("sql : " + sql);
		log.info("sqlCount : " + sqlCount);
		int total = 0;
		if ((totals != null  && totals) || (includeTotals!= null && includeTotals)) {
			total = jlpService.queryForCount(sqlCount);
		}
	
//		} else {
//			String SqlKeys = getPageSql(sql,pageNumInt,pageSizeInt);
//			log.info("SqlKeys : " + SqlKeys);
//			List<Map<String, Object>> dataGrid = jlpService.queryForSQLStreaming(SqlKeys, new Object[] {});

//			String keyIdWhere = getKeysWhere(dataGrid);
		List<Map<String, Object>> retData = new ArrayList<Map<String, Object>>();
//			if (keyIdWhere.length() > 0) {
//			String countSql = "select count(1) count from " + tableNames.toString().substring(1,tableNames.toString().length()-1) + " " + tempSqlWhere
//					+ " order by " + PatientGlobalTable + ".Id";
//			List<Map<String, Object>> countData = jlpService.queryForSQL(countSql, new Object[] {});

//		String SqlAllData = "select distinct " + finalSelectFields + " from " + finalTables4Count + " " + tempSqlWhere
//				+ " order by " + JLPConts.PatientGlobalTable + ".Id";

//		if(StringUtils.isBlank(sqlWhereMain)) {
//			
//		}

//		String finalTables4PageData10 = toTableString(tableNames, tempSql,pageNumInt,pageSizeInt,patientTableWhere,tableWhere,10);
//		String finalTables4PageData1000 = toTableString(tableNames, tempSql,pageNumInt,pageSizeInt,patientTableWhere,tableWhere,1000);
//		String finalTables4PageData100000 = toTableString(tableNames, tempSql,pageNumInt,pageSizeInt,patientTableWhere,tableWhere,100000);
//		
//		String SqlPageData10 = "select distinct " + finalSelectFields + " from " + finalTables4PageData10 + " " + tempSqlWhere
//				+ " order by " + JLPConts.PatientGlobalTable + ".Id";
//		String SqlPageData1000 = "select distinct " + finalSelectFields + " from " + finalTables4PageData1000 + " " + tempSqlWhere
//				+ " order by " + JLPConts.PatientGlobalTable + ".Id";
//		String SqlPageData100000 = "select distinct " + finalSelectFields + " from " + finalTables4PageData100000 + " " + tempSqlWhere
//				+ " order by " + JLPConts.PatientGlobalTable + ".Id";
//		
//		retData = jlpService.queryForSQLStreaming(SqlPageData10,SqlPageData1000,SqlPageData100000,SqlAllData, pageNumInt, pageSizeInt);
		StringBuffer preUrl = request.getRequestURL();

		// 从前台header中获取token参数
		String authorization = request.getHeader("Authorization") == null ? "e1cb039e7adf7633a1de93e5f8da0dea"
				: request.getHeader("Authorization");

		Long userRole = jUserService.getUserRoleByToken(authorization);

//		JUser user = (JUser) request.getSession().getAttribute("user"); 
		// jUserService.getUserByToken(authorization);
		if (userRole == null || userRole.longValue() == 0L) {
			response.getResponse().setCode("0000");
			response.getResponse().setText("登录信息已过期，请重新登录");
			String json = response.toJsonString();
			log.info("response --> " + json);
			return json;
		}

		try {
			retData = jlpService.queryForAdvanceQuery(tableNames, tempSql, patientTableWhere, tableWhere,
					finalSelectFields, tempSqlWhere, pageNumInt, pageSizeInt, sqlCount, preUrl.toString(), IsImageUrl,
					imageUrl, imageField, pathImageUrl, pathImageField, IsPathImageUrl, userRole);

		} catch (Exception e) {
			response.getResponse().setCode("0000");
			response.getResponse().setText("获取数据错误，原因: " + e.getMessage());
			String json = response.toJsonString();
			log.info("response --> " + json);
			return json;
		}

//			}
		response.setColumns(columns);
		response.setContent(retData);
		if(total == 0) {
			total = retData.size();
		}
		response.setTotals(total);
//		}
		String json = response.toJsonString();
		log.info("response --> " + json);
		long end = System.currentTimeMillis();
		log.info("serviceTime " + (end - start) + "ms");
		// 清理上下文
		return json;
	}

	@ApiOperation(value = "病例检索模糊查询(仅数量)")
	@RequestMapping(value = "/api/AdvanceQuery/count", method = { RequestMethod.POST })
	public String query4Count(@RequestBody String advanceQuery, String pageNum, String pageSize) {
		JLPLog log = ThreadPoolConfig.getBaseContext();
		long start = System.currentTimeMillis();
		log.info("request --> " + advanceQuery);
		AdvanceQueryRequest advanceQueryRequest = null;
		try {
			advanceQueryRequest = JSONObject.toJavaObject(JSONObject.parseObject(advanceQuery),
					AdvanceQueryRequest.class);
		} catch (Exception e) {
			log.error(e);
		}
		AdvanceQueryResponse response = new AdvanceQueryResponse();
		if (advanceQueryRequest == null) {
			response.getResponse().setCode("0000");
			response.getResponse().setText("传入参数为空");
			String json = response.toJsonString();
			response.setTotals(0);
			log.info("response --> " + json);
			return json;
		}
		if (advanceQueryRequest.getViewId() == 0) {
			response.getResponse().setCode("0000");
			response.getResponse().setText("视图编号错误");
			String json = response.toJsonString();
			response.setTotals(0);
			log.info("response --> " + json);
			return json;
		}

		JShowView jShowView = jShowviewService.getOne(advanceQueryRequest.getViewId(), true);
		if (jShowView == null) {
			response.getResponse().setCode("0000");
			response.getResponse().setText("视图编号错误");
			String json = response.toJsonString();
			response.setTotals(0);
			log.info("response --> " + json);
			return json;
		}

		JTableDict mainTable = null;

		// 获取显示视图信息
		List<JShowDetailView> jShowDetailViewList = jShowDetailViewService.getByViewId(jShowView.getId());
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
		if (advanceQueryRequest.getQueryShowFields() != null) {
			List<Long> tempFieldList = new ArrayList<Long>();
			for (Long field : advanceQueryRequest.getQueryShowFields()) {
				tempFieldList.add(field);
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
			response.getResponse().setCode("1001");
			response.getResponse().setText("显示字段未配置");
			response.setTotals(0);
			String json = response.toJsonString();
			log.info("response --> " + json);
			return json;
		}

		// 获取查询所有表名
		List<QueryDetailModel> queryCondsList = advanceQueryRequest.getQueries().getQueryConds();
		SortedSet<String> tableNameMainWheres = new TreeSet<String>();
		SortedSet<String> tableNameWhereMainValues = new TreeSet<String>();
		String sqlWhereMain = "";
		Map<String, List<String>> tableWhere = new HashMap<String, List<String>>();
		if (queryCondsList != null) {

			for (QueryDetailModel queryConds : queryCondsList) {
				String tableName = queryConds.getCondition().split("\\.")[0].toUpperCase();
				tableNameMainWheres.add(tableName);
				String fieldName = queryConds.getCondition().split("\\.")[1].toUpperCase();
				//
				if (queryConds.getFieldType() == 1) {
					tableNameWhereMainValues.add(queryConds.getCondValue().split("\\.")[0].toUpperCase());
				}
				String condition = queryConds.getCondition().toUpperCase();
				JTableDict jTableDict = jTableDictService.getByName(tableName, true);
				JTableFieldDict jTableFieldDict = jTableFieldDictService.getByTableCodeAndName(jTableDict.getCode(),
						fieldName);
				// whereFieldTypesMain.add(jTableFieldDict.getType());

				String relation = queryConds.getRelation();
				// 如果是is not null 则不拼装value
				if (!relation.contains("null")) {
					String condValue = queryConds.getCondValue() == null ? "" : queryConds.getCondValue();
					if ("like".equalsIgnoreCase(relation)) {
						condValue = "%" + condValue + "%";
					}
					if ("VARCHAR2".equalsIgnoreCase(jTableFieldDict.getType())
							|| "CHAR".equalsIgnoreCase(jTableFieldDict.getType())) {
						condValue = "'" + condValue + "'";
					}
					if ("DATE".equalsIgnoreCase(jTableFieldDict.getType())) {
						condValue = "to_date('" + condValue + "','yyyy/mm/dd')";
					}
					sqlWhereMain += condition + " " + queryConds.getRelation() + " " + condValue + " and ";
					List<String> list = tableWhere.get(tableName);
					if (list == null) {
						list = new ArrayList<String>();
					}
					list.add(" and " + condition + " " + queryConds.getRelation() + " " + condValue);
				} else {
					sqlWhereMain += condition + " " + queryConds.getRelation() + " " + " and ";
				}

			}
		}
		// 去掉最后的and
		if (sqlWhereMain.length() > 4) {
			sqlWhereMain = sqlWhereMain.substring(0, sqlWhereMain.length() - 4);
		}
		SortedSet<String> tableNames = new TreeSet<String>();
		List<FieldName> fieldNames = new ArrayList<FieldName>();
		// 增加隐藏主键
		fieldNames.add(new FieldName(JLPConts.PatientGlobalTable + ".Id as hide_key"));
		SortedSet<String> fieldShowNames = new TreeSet<String>();
		SortedSet<String> showTableNames = new TreeSet<String>();
		for (JShowDetailView JShowDetailView : jShowDetailViewListShow) {
			if (JShowDetailView.getjTableDict() != null) {
				tableNames.add(JShowDetailView.getjTableDict().getName());
				showTableNames.add(JShowDetailView.getjTableDict().getName());
				fieldNames.add(new FieldName((JShowDetailView.getjTableDict().getName() + "."
						+ JShowDetailView.getjTableFieldDict().getName()).toLowerCase()));
			}
			if (JShowDetailView.getjTableFieldDict() != null) {
				fieldShowNames.add(JShowDetailView.getjTableFieldDict().getName().toLowerCase());
			}
		}
		tableNames.addAll(tableNameMainWheres);
		List<Sorted> sortedList = advanceQueryRequest.getSorted();
		// 排序字段
		String sorts = "";
		for (String showField : fieldShowNames) {
			for (Sorted sorted : sortedList) {
				if (showField.equalsIgnoreCase(sorted.getId())) {
					sorts += showField + ",";
				}
			}
		}
		if (sorts.length() > 0) {
			sorts = sorts.substring(0, sorts.length() - 1);
		}

		QueryIncludesEX queryIncludesEX = advanceQueryRequest.getQueries().getQueryIncludesEX();
		String tempSql = queryEx(queryIncludesEX, tableWhere);

		for (int i = 0; i < fieldNames.size(); i++) {
			FieldName fieldName = fieldNames.get(i);
			if (fieldName.getFieldName().toUpperCase().contains("BIRTHDAY")
					&& !fieldName.getFieldName().contains("to_char")) {
				fieldName.setFieldName("to_char(" + fieldName.getFieldName() + ",'yyyy/mm/dd') as birthday");
			}
		}

		String tempSqlWhere = " where 1=1 and " + JLPConts.PatientGlobalTable + ".jlactiveflag='1' ";
		;
		if (sqlWhereMain.length() > 0) {
			tempSqlWhere += " and  " + sqlWhereMain;
		}
		tempSqlWhere += " and " + mainTable.getName() + ".Id is not null";
		String finalTables4Count = toTableString(tableNames, tempSql);

		String tableKeys = toTableKeys(showTableNames);

		String sql = "select distinct " + tableKeys + " from " + finalTables4Count + " " + tempSqlWhere;
		String sqlCount = " select count(1) count from (" + sql + ") countsql";
		log.info("sqlCount : " + sqlCount);
//		if (isTotals) {
		int count = jlpService.queryForCount(sqlCount);
		response.setTotals(count);
		String json = response.toJsonString();
		log.info("response --> " + json);
		long end = System.currentTimeMillis();
		log.info("serviceTime " + (end - start) + "ms");
		// 清理上下文
		return json;
	}

//	private String getKeysWhere(List<Map<String, Object>> dataGrid) {
//		String ret = "";
//		if (dataGrid == null || dataGrid.size() == 0) {
//			return ret;
//		}
//		Map<String, String> dict = new HashMap<String, String>();
//		for (Map<String, Object> map : dataGrid) {
//			Set<String> set = map.keySet();
//			for (String item : set) {
//				Object tempValue = map.get(item);
//				if (item.contains("__") && map.get(item) != null) {
//					String tempKey = item.replace("__", ".");
//					if (!dict.containsKey(tempKey)) {
//						dict.put(tempKey, tempValue + "");
//					} else {
//						dict.put(tempKey, dict.get(item) + "," + tempValue);
//					}
//				}
//			}
//		}
//		for (String item : dict.keySet()) {
//			if (ret.length() == 0) {
//				ret = item + "  in (" + dict.get(item) + ")";
//			} else {
//				ret += " and " + item + " in (" + dict.get(item) + ")";
//			}
//		}
//		return ret;
//	}

//	/**
//	 * 
//	 * @param tableArray
//	 * @param advancedQueryWhere
//	 * @param pageNum
//	 * @param pageSize
//	 * @return
//	 */
//	private String toTableString(SortedSet<String> tableArray, String advancedQueryWhere,int pageNum,int pageSize
//			,String patientTableWhere,Map<String,List<String>> tableWhere,int times) {
//		String ret = "";
//		if (tableArray != null) {
//			String patientTable = JLPConts.PatientGlobalTable.toLowerCase();
//			ret = "( select * from (select * from " + patientTable + " " + patientTableWhere
//					+ " order by "+JLPConts.PatientGlobalTable+".Id ) "
//					+ " where rownum<" + pageNum * pageSize * times
//					+ ") d_patientglobal";
//
//			if (StringUtils.isNotBlank(advancedQueryWhere)) {
//				ret += " inner join (" + advancedQueryWhere + ") a on " + patientTable + ".Id=a.Id ";
//			}
//
//			boolean IsInner = tableArray.size() <= 1;
//			if (!IsInner && tableArray.size() == 2) {
//				if (tableArray.contains("D_LABMASTERINFO") && tableArray.contains("D_LABRESULTS")) {
//					IsInner = true;
//				}
//			}
//			boolean IsLabResult = false;
//			boolean IsLabMaster = false;
//
//			for (String item : tableArray) {
//				if ("D_LABMASTERINFO".equals(item.toUpperCase())) {
//					IsLabMaster = true;
//				} else if ("D_LABRESULTS".equalsIgnoreCase(item)) {
//					IsLabResult = true;
//					continue;
//				} else if (item.toLowerCase().equals(patientTable)) {
//					continue;
//				}
//				ret += (IsInner ? " inner" : " left") + " join " + item + " on " + patientTable + ".Id = " + item
//						+ ".patientglobalid ";
//			}
//			if (IsLabResult) {
//				if (!IsLabMaster) {
//					ret += (IsInner ? "inner" : "left") + " join D_LABMASTERINFO on " + patientTable
//							+ ".Id = D_LABMASTERINFO.patientglobalid ";
//				}
//				ret += (IsInner ? " inner" : " left")
//						+ " join D_LABRESULTS on D_LABRESULTS.APPLYNO = D_LABMASTERINFO.APPLYNO ";
//			}
//		}
//		return ret;
//	}

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

	/**
	 * 解析incule和exclude，拼装sql
	 * 
	 * @param queryIncludesEX
	 * @return
	 */
	private String queryEx(QueryIncludesEX queryIncludesEX, Map<String, List<String>> tableWhere) {
		SortedSet<String> tableNameWheres = new TreeSet<String>();
		if (queryIncludesEX == null) {
			return "";
		}
		String includeSql = cludeSqlExcutor(queryIncludesEX.getIncludes(), tableNameWheres, tableWhere);
		String excludeSql = cludeSqlExcutor(queryIncludesEX.getExcludes(), tableNameWheres, tableWhere);

		String tempSql = "";
		if (includeSql.length() > 0) {
			tempSql += "select Id from (" + includeSql.toString() + ")";
			if (excludeSql.length() > 0) {
				tempSql += " minus (" + excludeSql.toString() + ")";
			}
		}
		return tempSql;
	}

	private String cludeSqlExcutor(List<QueryCollectionModel> queryIncludesEX, SortedSet<String> tableNameWheres,
			Map<String, List<String>> tableWhere) {
		String includeSql = " select distinct " + JLPConts.PatientGlobalTable + ".Id   from ";
		String allSqlWhere = "";
		for (QueryCollectionModel queryIncludesEXCondition : queryIncludesEX) {
			if (queryIncludesEXCondition.getConds().isEmpty()) {
				continue;
			}

			String SqlWhere = "";
			for (QueryDetailModel queryConds : queryIncludesEXCondition.getConds()) {
				String tableName = queryConds.getCondition().split("\\.")[0].toUpperCase();
				tableNameWheres.add(tableName);
				String fieldName = queryConds.getCondition().split("\\.")[1].toUpperCase();
				String condition = queryConds.getCondition().toUpperCase();
				JTableDict jTableDict = jTableDictService.getByName(tableName, true);
				JTableFieldDict jTableFieldDict = jTableFieldDictService.getByTableCodeAndName(jTableDict.getCode(),
						fieldName);
//				whereFieldTypes.add(jTableFieldDict.getType());

				String relation = queryConds.getRelation();
				// 如果是is not null 则不拼装value
				if (!relation.contains("null")) {
					String condValue = queryConds.getCondValue() == null ? "" : queryConds.getCondValue();
					if ("like".equalsIgnoreCase(relation)) {
						condValue = "%" + condValue + "%";
					}
					if ("VARCHAR2".equalsIgnoreCase(jTableFieldDict.getType())
							|| "CHAR".equalsIgnoreCase(jTableFieldDict.getType())) {
						condValue = "'" + condValue + "'";
					}
					if ("DATE".equalsIgnoreCase(jTableFieldDict.getType())) {
						condValue = "to_date('" + condValue + "','yyyy/mm/dd')";
					}
					SqlWhere += condition + " " + queryConds.getRelation() + " " + condValue + " and ";
				} else {
					SqlWhere += condition + " " + queryConds.getRelation() + " " + " and ";
				}

			}
			// 去掉最后的and
			if (SqlWhere.length() > 4) {
				SqlWhere = SqlWhere.substring(0, SqlWhere.length() - 4);
			}
			allSqlWhere += " and " + queryIncludesEXCondition.getLeftqueto() + " " + SqlWhere + " "
					+ queryIncludesEXCondition.getRightqueto();
//	     var SqlWhere = queryDetails.Select(x => x.ToArray().ToStringEx(" ")).ToArray().ToStringEx(" and ");
		}
		if (allSqlWhere.length() == 0) {
			return "";
		}
//	     #region 获取查询结果和数量
		String tableWhere1 = toTableString(tableNameWheres, "");
		includeSql += tableWhere1 + " where 1=1 " + allSqlWhere;
		return includeSql;
	}

	private String toTableKeys(SortedSet<String> tables) {
		String ret = "";
		if (tables == null || tables.size() == 0) {
			return JLPConts.PatientGlobalTable + ".id";
		}
		List<String> tempTables = new ArrayList<String>();
		tempTables.add(JLPConts.PatientGlobalTable);
		tables.remove(JLPConts.PatientGlobalTable);
		tempTables.addAll(tables);
//        Dictionary<string, Type> keyValues = new Dictionary<string, Type>();
		for (String item : tempTables) {
//            keyValues.Add($"{item}__Id", typeof(long));
			if (ret.length() == 0) {
				ret = item + ".Id as " + item + "__Id";
			} else {
				ret += "," + item + ".Id as " + item + "__Id";
			}
		}
//        type = CreateType(keyValues);
		return ret;
	}

}
