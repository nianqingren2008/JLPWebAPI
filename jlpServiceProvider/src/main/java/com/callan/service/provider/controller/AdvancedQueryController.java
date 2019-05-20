package com.callan.service.provider.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.callan.service.provider.pojo.AdvanceQueryRequest;
import com.callan.service.provider.pojo.AdvanceQueryResponse;
import com.callan.service.provider.pojo.advanceQueryBase.ColunmsModel;
import com.callan.service.provider.pojo.advanceQueryBase.Queries;
import com.callan.service.provider.pojo.advanceQueryBase.QueryConds;
import com.callan.service.provider.pojo.advanceQueryBase.QueryIncludesEXCondition;
import com.callan.service.provider.pojo.advanceQueryBase.Sorted;
import com.callan.service.provider.pojo.db.JRight;
import com.callan.service.provider.pojo.db.JSensitiveWord;
import com.callan.service.provider.pojo.db.JShowDetailView;
import com.callan.service.provider.pojo.db.JShowView;
import com.callan.service.provider.pojo.db.JTableDict;
import com.callan.service.provider.pojo.db.JTableFieldDict;
import com.callan.service.provider.service.IJLpService;
import com.callan.service.provider.service.IJRightService;
import com.callan.service.provider.service.IJSensitiveWordService;
import com.callan.service.provider.service.IJShowDetailViewService;
import com.callan.service.provider.service.IJShowViewService;
import com.callan.service.provider.service.IJTableDictService;
import com.callan.service.provider.service.IJTableFieldDictService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(description = "病例检索查询")
public class AdvancedQueryController {
	Log log = LogFactory.getLog(AdvancedQueryController.class);

	public final String PatientGlobalTable = "D_PATIENTGLOBAL";

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
	private IJSensitiveWordService jSensitiveWordService;

	@ApiOperation(value = "病例检索", notes = "111")
	@RequestMapping(value = "/api/AdvanceQuery", method = { RequestMethod.GET, RequestMethod.POST })
	public String query(@RequestBody String advanceQuery, String pageNum, String pageSize, HttpSession session) {
		AdvanceQueryRequest advanceQueryRequest = null;
		try {
			advanceQueryRequest = JSONObject.toJavaObject(JSONObject.parseObject(advanceQuery),
					AdvanceQueryRequest.class);
		} catch (Exception e) {
			log.error(e);
		}
		boolean isTotals = false;
		int pageNumInt = StringUtils.isBlank(pageNum) ? 1 : Integer.parseInt(pageNum);
		int pageSizeInt = StringUtils.isBlank(pageSize) ? 20 : Integer.parseInt(pageSize);
		AdvanceQueryResponse response = new AdvanceQueryResponse();
		if (advanceQueryRequest == null) {
			response.getResponse().setCode("0000");
			response.getResponse().setText("传入参数为空");
			return response.toJsonString();
		}
		if (advanceQueryRequest.getViewId() == 0) {
			response.getResponse().setCode("0000");
			response.getResponse().setText("视图编号错误");
			return response.toJsonString();
		}

		JShowView jShowView = jShowviewService.getOne(advanceQueryRequest.getViewId());
		if (jShowView == null || !jShowView.getActiveflag().equals("1")) {
			response.getResponse().setCode("0000");
			response.getResponse().setText("视图编号错误");
			return JSONObject.toJSONString(response);
		}
		String mainTable = jShowView.getMaintablecode();

		// 获取显示视图信息
		List<JShowDetailView> jShowDetailViewList = jShowDetailViewService.getByViewId(jShowView.getId(), true);
//        
		List<JTableFieldDict> jTableFieldDictList = new ArrayList<JTableFieldDict>();
		for (JShowDetailView jShowDetailView : jShowDetailViewList) {
			long fieldId = jShowDetailView.getFieldid();
			JTableFieldDict jTableFieldDict = jTableFieldDictService.getOne(fieldId, true);
			jTableFieldDictList.add(jTableFieldDict);
			jTableFieldDict.setjShowDetailView(jShowDetailView);
			jShowDetailView.setjTableFieldDict(jTableFieldDict);
        	JTableDict jTableDict = jTableDictService.getByCode(jTableFieldDict.getTablecode(),true);
        	jShowDetailView.setjTableDict(jTableDict);
        	

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
			return response.toJsonString();
		}

		Set<ColunmsModel> columns = new HashSet<ColunmsModel>();
		for (JTableFieldDict jTableFieldDict : jTableFieldDictList) {
			ColunmsModel colunmsModel = new ColunmsModel();
			colunmsModel.setDataIndex(jTableFieldDict.getName().toLowerCase());
			colunmsModel.setKey(jTableFieldDict.getName().toLowerCase());
			colunmsModel.setTitle(jTableFieldDict.getjShowDetailView().getFieldtitle());
			colunmsModel.setIsLongStr("clob".equals(jTableFieldDict.getType()));
			colunmsModel.setIsSearched("1".equals(jTableFieldDict.getQueryflag()));
			columns.add(colunmsModel);
		}

		// 获取查询所有表名
		List<QueryConds> queryCondsList = advanceQueryRequest.getQueries().getQueryConds();

		Set<String> tableNameMainWheres = new HashSet<String>();
		Set<String> tableNameWhereMainValues = new HashSet<String>();
		Set<String> whereFieldsMain = new HashSet<String>();
		Set<JTableFieldDict> whereFieldTypesMain = new HashSet<JTableFieldDict>();
		String sqlWhereMain = "";

		for (QueryConds queryConds : queryCondsList) {
			String tableName = queryConds.getCondition().split("\\.")[0].toUpperCase();
			tableNameMainWheres.add(tableName);
			if (queryConds.getFieldType() == 1) {
				tableNameWhereMainValues.add(queryConds.getCondValue().split("\\.")[0].toUpperCase());
			}
			whereFieldsMain.add(queryConds.getCondition().toUpperCase());
			JTableDict jTableDict = jTableDictService.getByCode(tableName, true);
			List<JTableFieldDict> jTableFieldDictListShow = jTableFieldDictService.getByTableCode(jTableDict.getCode(),
					true);
			for (JTableFieldDict jTableFieldDict : jTableFieldDictListShow) {
				whereFieldTypesMain.add(jTableFieldDict);
			}
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
		Set<String> tableNames = new HashSet<String>();
		Set<String> fieldNames = new HashSet<String>();
		Set<String> fieldShowNames = new HashSet<String>();
		for(JShowDetailView JShowDetailView : jShowDetailViewListShow) {
			tableNames.add(JShowDetailView.getjTableDict().getName());
			fieldNames.add((JShowDetailView.getjTableDict().getName() + "." 
					+ JShowDetailView.getjTableFieldDict().getName()).toUpperCase());
			fieldShowNames.add(JShowDetailView.getjTableFieldDict().getName().toLowerCase());
		}

//        String SqlWhereMain = queryMainDetails.Select(x => x.ToArray().ToStringEx(" ")).ToArray().ToStringEx(" and ");
//        tableNames.AddRange(tableNameMainWheres);
//        tableNames.AddRange(tableNameWhereMainValues);
//        tableNames = tableNames.Distinct().ToList();
//        tableNames.RemoveAll(x => x.ToUpper() == JLPStaticProperties.PatientGlobalTable);
//
		List<Sorted> sortedList = advanceQueryRequest.getSorted();
		String sorts = "";
		for (Sorted sorted : sortedList) {
			JTableFieldDict jTableFieldDict = jTableFieldDictService.getOne(Long.parseLong(sorted.getId()), true);
			String sortedFieldName = jTableFieldDict.getName();
			if (sorted.getDesc()) {
				sorts += sortedFieldName + " desc ,";
			} else {
				sorts += sortedFieldName + ",";
			}

		}

//
		List<String> includeSqlList = new ArrayList<String>();
		List<String> excludeSqlList = new ArrayList<String>();

		List<QueryIncludesEXCondition> includes = advanceQueryRequest.getQueries().getQueryIncludesEX().getIncludes();
		for (QueryIncludesEXCondition queryIncludesEXCondition : includes) {

		}
		List<QueryIncludesEXCondition> excludes = advanceQueryRequest.getQueries().getQueryIncludesEX().getExcludes();
		// 增加隐藏主键
		fieldNames.add(PatientGlobalTable + ".Id as hide_key");
		StringBuilder includeBuilder = new StringBuilder();
		for (String includeSql : includeSqlList) {
			includeBuilder.append(includeSql);
		}
		StringBuilder excludeBuilder = new StringBuilder();
		for (String excludeSql : excludeSqlList) {
			excludeBuilder.append(excludeSql);
		}
		String tempSql = "";
		if (includeBuilder.length() > 0) {
			tempSql += "select Id from (" + includeBuilder.toString() + ")";
			if (excludeBuilder.length() > 0) {
				tempSql += " minus (" + excludeBuilder.toString() + ")";
			}
		}
		for (String fieldName : fieldNames) {
			if (fieldName.toUpperCase().contains("BIRTHDAY")) {
				fieldName = "to_char({item},'yyyy/mm/dd') as birthday";
			}
		}
		String finalSelectFields = fieldNames.toString();
		String finalTables = tableNames.toString();
		String tableKeys = fieldShowNames.toString();

		String tempSqlWhere = " where 1=1 ";
		tempSqlWhere += " and D_PATIENTGLOBAL.jlactiveflag='1'";
		if (advanceQueryRequest.getPatientGlobalId() > 0) {
			tempSqlWhere += " and D_PATIENTGLOBAL.Id = " + advanceQueryRequest.getPatientGlobalId();
		}
		if (sqlWhereMain.length() > 0) {
			tempSqlWhere += " and  " + sqlWhereMain;
		}
		tempSqlWhere += " and " + mainTable + ".Id is not null";
//        #region 组装返回值
		String sortStr = PatientGlobalTable + "__Id";
//
		String sql = "select distinct " + tableKeys + " from " + finalTables + " " + tempSqlWhere;
		String sqlCount = "";// DBHelperBase.getCountSql(BarryCommon.DataBaseType.Oracle, Sql);
		log.info("sql : " + sql);
		log.info("sqlCount : " + sqlCount);
		if (isTotals) {
			int totals = jlpService.queryForSQL(sqlCount, new Object[] {}).size();
			response.setTotals(totals);
		} else {
			String SqlKeys = sql;
			log.info("SqlKeys : " + SqlKeys);
			List<Map<String, Object>> dataGrid = jlpService.queryForSQL(SqlKeys, new Object[] {});

			String keyIdWhere = "";// keysData.GetKeysWhere();
			List<Map<String, Object>> retData = new ArrayList<Map<String, Object>>();
			if (keyIdWhere.length() > 0) {
				String SqlData = "select distinct " + finalSelectFields + " from " + tableNames + " " + tempSqlWhere
						+ " and " + keyIdWhere + " order by " + PatientGlobalTable + ".Id";
				retData = jlpService.queryForSQLStreaming(SqlData, new Object[] {});

				JRight jRight = (JRight) session.getAttribute("jRight");
				if (jRight == null || jRight.getId() != 4L) {
					// 获取敏感字段配置
					List<JSensitiveWord> jSensitiveWordList = jSensitiveWordService.getAll(true);

					if (jSensitiveWordList.size() > 0) {
						// 将敏感字段设置为 ***
						// TODO
//                        retData = retData.sensitiveWord(jSensitiveWordList);
					}
				}
			}
			response.setTotals(retData.size());
			response.setColumns(columns);
			response.setContent(retData);
		}

		return response.toJsonString();
	}
}
