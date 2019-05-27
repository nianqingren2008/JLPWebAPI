package com.callan.service.provider.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
//import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
//import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.swing.JTable;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.callan.service.provider.dao.mapper.JRoleMapper;
import com.callan.service.provider.pojo.AdvanceQueryRequest;
import com.callan.service.provider.pojo.AdvanceQueryResponse;
import com.callan.service.provider.pojo.advanceQueryBase.ColunmsModel;
import com.callan.service.provider.pojo.advanceQueryBase.Queries;
import com.callan.service.provider.pojo.advanceQueryBase.QueryConds;
import com.callan.service.provider.pojo.advanceQueryBase.QueryIncludesEX;
import com.callan.service.provider.pojo.advanceQueryBase.QueryIncludesEXCondition;
import com.callan.service.provider.pojo.advanceQueryBase.Sorted;
import com.callan.service.provider.pojo.base.FieldName;
import com.callan.service.provider.pojo.db.JRight;
import com.callan.service.provider.pojo.db.JRole;
import com.callan.service.provider.pojo.db.JSensitiveWord;
import com.callan.service.provider.pojo.db.JShowDetailView;
import com.callan.service.provider.pojo.db.JShowView;
import com.callan.service.provider.pojo.db.JTableDict;
import com.callan.service.provider.pojo.db.JTableFieldDict;
import com.callan.service.provider.pojo.db.JUser;
import com.callan.service.provider.service.IJLpService;
import com.callan.service.provider.service.IJRightService;
import com.callan.service.provider.service.IJSensitiveWordService;
import com.callan.service.provider.service.IJShowDetailViewService;
import com.callan.service.provider.service.IJShowViewService;
import com.callan.service.provider.service.IJTableDictService;
import com.callan.service.provider.service.IJTableFieldDictService;
import com.callan.service.provider.service.IJUserService;

import ch.qos.logback.core.joran.action.IADataForComplexProperty;
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

	@Autowired
	private IJUserService jUserService;
	
	@Autowired
	private JRoleMapper roleMapper;
	
//	@CrossOrigin(origins = "*",maxAge = 3600)  //跨域
	@ApiOperation(value = "病例检索", notes = "病例检索模糊查询")
	@RequestMapping(value = "/api/AdvanceQuery", method = { RequestMethod.POST  })
	public String query(@RequestBody String advanceQuery, String pageNum, String pageSize, HttpSession session) {
		long start = System.currentTimeMillis();
		log.info("");
		log.info("advanceQuery-->" + advanceQuery);
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

		JShowView jShowView = jShowviewService.getOne(advanceQueryRequest.getViewId(),true);
		if (jShowView == null ) {
			response.getResponse().setCode("0000");
			response.getResponse().setText("视图编号错误");
			return JSONObject.toJSONString(response);
		}
		
		JTableDict mainTable = null;

		// 获取显示视图信息
		List<JShowDetailView> jShowDetailViewList = jShowDetailViewService.getByViewId(jShowView.getId(), true);
//        
		List<JTableFieldDict> jTableFieldDictList = new ArrayList<JTableFieldDict>();
		for (JShowDetailView jShowDetailView : jShowDetailViewList) {
			long fieldId = jShowDetailView.getFieldid();
			JTableFieldDict jTableFieldDict = jTableFieldDictService.getOne(fieldId, true);
			if(jTableFieldDict == null) {
				log.warn("fieldId 为 " + fieldId + " 未找到j_tablefielddict 记录 ");
				continue;
			}
			jTableFieldDictList.add(jTableFieldDict);
			jTableFieldDict.setjShowDetailView(jShowDetailView);
			jShowDetailView.setjTableFieldDict(jTableFieldDict);
        	JTableDict jTableDict = jTableDictService.getByCode(jTableFieldDict.getTablecode(),true);
        	jShowDetailView.setjTableDict(jTableDict);
        	if(mainTable == null) {
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
			return response.toJsonString();
		}

		List<ColunmsModel> columns = new ArrayList<ColunmsModel>();
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
		SortedSet<String> tableNameMainWheres = new TreeSet<String>();
		SortedSet<String> tableNameWhereMainValues = new TreeSet<String>();
		//		List<String> whereFieldsMain = new ArrayList<String>();
		//		List<String> whereFieldTypesMain = new ArrayList<String>();
				String sqlWhereMain = "";
		if(queryCondsList != null) {
			
	
			for (QueryConds queryConds : queryCondsList) {
				String tableName = queryConds.getCondition().split("\\.")[0].toUpperCase();
				tableNameMainWheres.add(tableName);
				String fieldName = queryConds.getCondition().split("\\.")[1].toUpperCase();
	//			
				if (queryConds.getFieldType() == 1) {
					tableNameWhereMainValues.add(queryConds.getCondValue().split("\\.")[0].toUpperCase());
				}
				String condition = queryConds.getCondition().toUpperCase();
				JTableDict jTableDict = jTableDictService.getByName(tableName, true);
				JTableFieldDict jTableFieldDict = jTableFieldDictService.getByTableCodeAndName (jTableDict.getCode(),fieldName);
	//			whereFieldTypesMain.add(jTableFieldDict.getType());
				
				String relation = queryConds.getRelation();
				//如果是is not null 则不拼装value
				if(!relation.contains("null")) {
					String condValue = queryConds.getCondValue() == null ? "" : queryConds.getCondValue();
					if("like".equalsIgnoreCase(relation)) {
						condValue = "%" + condValue + "%";
					}
					if("VARCHAR2".equalsIgnoreCase(jTableFieldDict.getType()) || "CHAR".equalsIgnoreCase(jTableFieldDict.getType()) ) {
						condValue = "'" + condValue + "'";
					}
					if("DATE".equalsIgnoreCase(jTableFieldDict.getType())) {
						condValue = "to_date('" + condValue + "','yyyy/mm/dd')";
					}
					sqlWhereMain += condition + " " 
							+ queryConds.getRelation() + " " 
							+ condValue
							+ " and ";
				}else {
					sqlWhereMain += condition + " " 
							+ queryConds.getRelation() + " " 
							+ " and ";
				}
				
				
			}
		}
		//去掉最后的and
		if(sqlWhereMain.length() > 4) {
			sqlWhereMain = sqlWhereMain.substring(0,sqlWhereMain.length()-4);
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
		fieldNames.add(new FieldName(PatientGlobalTable + ".Id as hide_key"));
		SortedSet<String> fieldShowNames = new TreeSet<String>();
		for(JShowDetailView JShowDetailView : jShowDetailViewListShow) {
			tableNames.add(JShowDetailView.getjTableDict().getName());
			fieldNames.add(new FieldName((JShowDetailView.getjTableDict().getName() + "." 
					+ JShowDetailView.getjTableFieldDict().getName()).toLowerCase()));
			fieldShowNames.add(JShowDetailView.getjTableFieldDict().getName().toLowerCase());
		}
		tableNames.addAll(tableNameMainWheres);
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
		//排序字段
		String sorts = "";
		for(String showField : fieldShowNames) {
			for (Sorted sorted : sortedList) {
				if(showField.equalsIgnoreCase(sorted.getId())) {
					sorts +=  showField + ",";
				}
			}
		}
		if(sorts.length() > 0) {
			sorts = sorts.substring(0, sorts.length()-1);
		}

//		String includeSqlList = "";
//		String tempSql = "";
//		String tempadvancedQueryType = "";
		
//		List<QueryIncludesEXCondition> includes = new ArrayList<QueryIncludesEXCondition>();
//		List<QueryIncludesEXCondition> excludes = new ArrayList<QueryIncludesEXCondition>();
		QueryIncludesEX  queryIncludesEX = advanceQueryRequest.getQueries().getQueryIncludesEX();
//		if(queryIncludesEX != null) {
//			includes = queryIncludesEX.getIncludes();
//			excludes = queryIncludesEX.getExcludes();
//		}
		//处理纳入
//		for (QueryIncludesEXCondition queryIncludesEXCondition : includes) {
		String tempSql = queryEx(queryIncludesEX);
//		}
        //处理排除
		
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
		
		
		
		for (int i = 0;i<fieldNames.size() ;i++) {
			FieldName fieldName = fieldNames.get(i);
			if (fieldName.getFieldName().toUpperCase().contains("BIRTHDAY") 
					&& !fieldName.getFieldName().contains("to_char")) {
				fieldName.setFieldName("to_char("+fieldName.getFieldName()+",'yyyy/mm/dd') as birthday");
			}
		}
		String finalSelectFields = fieldNames.toString().substring(1,fieldNames.toString().length()-1);
		String finalTables =  toTableString(tableNames, tempSql);
		String tableKeys = toTableKeys(tableNames);

		String tempSqlWhere = " where 1=1 ";
		tempSqlWhere += " and D_PATIENTGLOBAL.jlactiveflag='1'";
		if (advanceQueryRequest.getPatientGlobalId() != null && advanceQueryRequest.getPatientGlobalId().longValue() > 0) {
			tempSqlWhere += " and D_PATIENTGLOBAL.Id = " + advanceQueryRequest.getPatientGlobalId();
		}
		if (sqlWhereMain.length() > 0) {
			tempSqlWhere += " and  " + sqlWhereMain;
		}
		tempSqlWhere += " and " + mainTable.getName() + ".Id is not null";
//        #region 组装返回值
		String sortStr = PatientGlobalTable + "__Id";
//
		String sql = "select distinct " + tableKeys + " from " + finalTables + " " + tempSqlWhere;
		String sqlCount = " select count(1) count from (" + sql + ") countsql";
//		log.info("sql : " + sql);
		log.info("sqlCount : " + sqlCount);
//		if (isTotals) {
			List<Map<String,Object>> countList = jlpService.queryForSQL(sqlCount, new Object[] {});
			if(countList != null && countList.size() > 0) {
				response.setTotals(Integer.parseInt(countList.get(0).get("count")+""));
			}else {
				response.setTotals(0);
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
			
			String SqlData = "select distinct " + finalSelectFields + " from " + finalTables + " " + tempSqlWhere
					+ " order by " + PatientGlobalTable + ".Id";
			SqlData = getPageSql(SqlData,pageNumInt,pageSizeInt);
			log.info("SqlData : " + SqlData);
			retData = jlpService.queryForSQLStreaming(SqlData, new Object[] {});
			
			
			JUser user = jUserService.getOne(6L);
			
			JRole role = roleMapper.getOne(1L);
			
			if(user != null) {
				if(user.getjRole() != null) {
					if(user.getjRole().getRoleRightList() != null && user.getjRole().getRoleRightList().size() > 0 ) {
						JRight jRight = user.getjRole().getRoleRightList().get(0).getjRight();
							if (jRight == null || jRight.getId() != 4L) {
								// 获取敏感字段配置
								List<JSensitiveWord> jSensitiveWordList = 
										(List<JSensitiveWord>) jSensitiveWordService.getAll(true).getData();

								if (jSensitiveWordList.size() > 0) {
									// 将敏感字段设置为 ***
									// TODO
//				                        retData = retData.sensitiveWord(jSensitiveWordList);
								}
						}
					}
						
				}
			}
			
//			}
			response.setColumns(columns);
			response.setContent(retData);
//		}
		long end = System.currentTimeMillis();
		log.info("serviceTime " + (end - start) + "ms");
		log.info(" ");
		return response.toJsonString();
	}

	
	
	private String getPageSql(String sql, int pageNum, int pageSize) {
		String pageSql = "SELECT *" + 
				"  FROM (SELECT tt.*, ROWNUM AS rowno" + 
				"          FROM (  " + sql + ") tt" + 
				"         WHERE ROWNUM <= " + pageNum * pageSize + ") table_alias" + 
				" WHERE table_alias.rowno >= " + (pageNum -1) * pageSize;
		return pageSql;
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

	private String toTableString(SortedSet<String> tableArray, String advancedQueryWhere) {
	    String ret = "";
	    if (tableArray != null) {
	        String patientTable = PatientGlobalTable.toLowerCase();
	        ret = patientTable;
	
	        if (StringUtils.isNotBlank(advancedQueryWhere)) {
	            ret += " inner join ("+advancedQueryWhere+") a on "+patientTable+".Id=a.Id ";
	        }
	
	        boolean IsInner = tableArray.size() <= 1;
	        if (!IsInner && tableArray.size() == 2) {
	            if (tableArray.contains("D_LABMASTERINFO") && tableArray.contains("D_LABRESULTS")) {
	                IsInner = true;
	            }
	        }
	        boolean IsLabResult = false;
	        boolean IsLabMaster = false;
	
	        for (String item :tableArray) {
	            if ("D_LABMASTERINFO".equals(item.toUpperCase())) {
	                IsLabMaster = true;
	            }  else if ("D_LABRESULTS".equalsIgnoreCase(item)) {
	                IsLabResult = true;
	                continue;
	            } else if (item.toLowerCase().equals(patientTable)) {
	                continue;
	            }
	            ret += (IsInner ? " inner" : " left") +" join " + item + " on " + patientTable + ".Id = " + item + ".patientglobalid ";
	        }
	        if (IsLabResult) {
	            if (!IsLabMaster) {
	                ret += (IsInner ? "inner" : "left") + " join D_LABMASTERINFO on " + patientTable + ".Id = D_LABMASTERINFO.patientglobalid ";
	            }
	            ret += (IsInner ? " inner" : " left") + " join D_LABRESULTS on D_LABRESULTS.APPLYNO = D_LABMASTERINFO.APPLYNO ";
	        }
	    }
	    return ret;
	}

	/**
	 * 解析incule和exclude，拼装sql
	 * @param queryIncludesEX
	 * @return
	 */
	private String queryEx(QueryIncludesEX queryIncludesEX) {
		SortedSet<String> tableNameWheres = new TreeSet<String>();
		if (queryIncludesEX == null) {
			return "";
		}
		String includeSql = cludeSqlExcutor(queryIncludesEX.getIncludes(), tableNameWheres);
		String excludeSql = cludeSqlExcutor(queryIncludesEX.getExcludes(), tableNameWheres);

		String tempSql = "";
		if (includeSql.length() > 0) {
			tempSql += "select Id from (" + includeSql.toString() + ")";
			if (excludeSql.length() > 0) {
				tempSql += " minus (" + excludeSql.toString() + ")";
			}
		}
		return tempSql;
	}

	private String cludeSqlExcutor(List<QueryIncludesEXCondition> queryIncludesEX, SortedSet<String> tableNameWheres) {
		String includeSql = " select distinct " + PatientGlobalTable + ".Id   from ";
		String allSqlWhere = "";
		for (QueryIncludesEXCondition queryIncludesEXCondition : queryIncludesEX) {
			if (queryIncludesEXCondition.getConds().isEmpty()) {
				continue;
			}

			String SqlWhere = "";
			for (QueryConds queryConds : queryIncludesEXCondition.getConds()) {
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
		if(allSqlWhere.length() == 0) {
			return "";
		}
//	     #region 获取查询结果和数量
		String tableWhere = toTableString(tableNameWheres, "");
		includeSql += tableWhere + " where 1=1 " + allSqlWhere;
		return includeSql;
	}

	private String toTableKeys(SortedSet<String> tables) {
		String ret = "";
		if (tables == null || tables.size() == 0) {
			return PatientGlobalTable + ".id";
		}
		List<String> tempTables = new ArrayList<String>();
		tempTables.add(PatientGlobalTable);
		tables.remove(PatientGlobalTable);
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
