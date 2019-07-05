package com.callan.service.provider.controller;

import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.callan.service.provider.config.JLPLog;
import com.callan.service.provider.config.ObjectUtil;
import com.callan.service.provider.config.ThreadPoolConfig;
import com.callan.service.provider.pojo.advanceQueryBase.AdvancedQueryRecordModel;
import com.callan.service.provider.pojo.advanceQueryBase.Sorted;
import com.callan.service.provider.pojo.base.BaseResponse;
import com.callan.service.provider.pojo.db.JStatisconf;
import com.callan.service.provider.pojo.db.JStatisconfdetail;
import com.callan.service.provider.pojo.db.JTableDict;
import com.callan.service.provider.pojo.db.JTableFieldDict;
import com.callan.service.provider.pojo.db.JTagdicts;
import com.callan.service.provider.pojo.db.JUser;
import com.callan.service.provider.pojo.statistic.StatisticFieldModel;
import com.callan.service.provider.pojo.statistic.StatisticModel;
import com.callan.service.provider.service.IJLpService;
import com.callan.service.provider.service.IJProjectService;
import com.callan.service.provider.service.IJStatisconfService;
import com.callan.service.provider.service.IJStatisconfdetailService;
import com.callan.service.provider.service.IJTableDictService;
import com.callan.service.provider.service.IJTableFieldDictService;
import com.callan.service.provider.service.IJTagdictService;
import com.callan.service.provider.service.IJUserService;
import com.callan.service.provider.service.impl.JLPServiceImpl;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(description = "数据统计")
public class StatisticsController {
	@Autowired
	private IJUserService userService;
	@Autowired
	private IJLpService jlpService;
	@Autowired
	private IJStatisconfService statisconfService;
	@Autowired
	private IJProjectService projectService;
	@Autowired
	private IJTagdictService tagdictService;
	@Autowired
	private IJStatisconfdetailService statisconfdetailService;
	@Autowired
	private IJTableFieldDictService tableFieldDictService;
	@Autowired
	private IJTableDictService tableDictService;

	@ApiOperation(value = "获取统计项列表")
	@RequestMapping(value = "/api/Statistics/pageCode", method = { RequestMethod.GET })
	public String geStatistics(String pageCode, HttpServletRequest request) {
		JLPLog log = ThreadPoolConfig.getBaseContext();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		pageCode = StringUtils.isBlank(pageCode) ? "main" : pageCode;
		String sql = " select distinct k1.code,k1.title,k1.id  from ( " + " select n1.id, " + "    n1.code,  "
				+ "    n1.title,   " + "    n1.createdate, " + "       n1.pagecode, " + "       n1.pagetitle from (  "
				+ " select w1.id,  " + "       w1.code,  " + "       w1.title, " + "       w1.createdate, "
				+ "       w1.pagecode, " + "       w1.pagetitle, " + "       w1.fieldid, " + "       w2.name,    "
				+ "       w2.description " + "  from (select t.id, " + "               t.code, "
				+ "               t.title, " + "               t.createdate, " + "               t.pagecode, "
				+ "               t.pagetitle, " + "               m.fieldid " + "          from j_statisconf t "
				+ "          left join j_statisconfdetail m " + "            on t.id = m.statisconfid "
				+ "         where t.activeflag = '1' and t.pagecode = '" + pageCode + "') w1 "
				+ "  left join j_tablefielddict w2 " + "    on w1.fieldid = w2.id) n1  group  by   n1.id, "
				+ "       n1.code, " + "       n1.title, " + "       n1.createdate, " + "       n1.pagecode, "
				+ "       n1.pagetitle " + " ) k1  order by k1.id ";
		log.info("geStatistics-->>sql : " + sql);
		int pageNum = 1, pageSize = 20;
		List<Map<String, Object>> resultList = jlpService.queryForSQLStreaming(sql, pageNum, pageSize);
		List<JStatisconf> jStatisconfList = new ArrayList<JStatisconf>();
		List<JStatisconfdetail> jStatisconfdetails = null;
		Map<String, List<JStatisconfdetail>> detailsMap = new HashMap<String, List<JStatisconfdetail>>();
		if (resultList != null && resultList.size() > 0) {
			for (Map<String, Object> map : resultList) {
				JStatisconf jStatisconf = new JStatisconf();
				jStatisconf.setId((Long.valueOf(map.get("id") + "")));
				jStatisconf.setCode(map.get("code") + "");
				jStatisconf.setTitle(map.get("title") + "");
				jStatisconfList.add(jStatisconf);

				jStatisconfdetails = statisconfdetailService.getByStatisconfid(Long.valueOf(map.get("id") + ""));
				detailsMap.put(map.get("code") + "", jStatisconfdetails);
			}
		} else {
			BaseResponse baseResponse = new BaseResponse();
			baseResponse.setCode("0000");
			baseResponse.setText("错误的页面编号");
			resultMap.put("response", baseResponse);
			String json = JSONObject.toJSONString(resultMap);
			log.error(json);
			return json;
		}

		resultMap.put("response", new BaseResponse());
		resultMap.put("statistics", jStatisconfList);
		resultMap.put("statisticDetails", detailsMap);
		String json = JSONObject.toJSONString(resultMap);
		log.info("response : " + json);
		return json;
	}

	@ApiOperation(value = "课题中各分类数据统计")
	@RequestMapping(value = "/api/Statistics/Project/{Id}", method = { RequestMethod.POST })
	public String getProject(@PathVariable Long Id, HttpServletRequest request, HttpServletResponse response) {
		JLPLog log = ThreadPoolConfig.getBaseContext();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		// 从前台header中获取token参数
		String authorization = request.getHeader("Authorization") == null ? "6c52445e47389d707807022cbba731cd"
				: request.getHeader("Authorization");
		Long userId = userService.getIdByToken(authorization);
		if (userId == null || userId.longValue() == 0) {
			BaseResponse baseResponse = new BaseResponse();
			baseResponse.setCode("0000");
			baseResponse.setText("用户信息获取失败，请检查请求头");
			resultMap.put("response", baseResponse);
			String json = JSONObject.toJSONString(resultMap);
			log.error(json);
			return json;
		}
		log.info("userId : " + userId);
		try {
			AdvancedQueryRecordModel advancedQueryRecord = projectService.getQueryRecord(Id, userId);
			BaseResponse baseResponse = new BaseResponse();
			resultMap.put("response", baseResponse);
			resultMap.put("queryRecord", advancedQueryRecord);
			String json = JSONObject.toJSONString(resultMap);
			log.info(json);
		} catch (Exception e) {
			BaseResponse baseResponse = new BaseResponse();
			response.setStatus(404);
			baseResponse.setCode("404");
			baseResponse.setText(e.getMessage());
			resultMap.put("response", baseResponse);
			String json = JSONObject.toJSONString(resultMap);
			log.error(json);
		}

		List<JTagdicts> list = tagdictService.getByProjectId(Id);
		for (JTagdicts tagdicts : list) {
			projectService.staticAsync(Id, ObjectUtil.objToLong(tagdicts.getTagattached()), request, response);
		}

		resultMap.put("response", new BaseResponse());
		String json = JSONObject.toJSONString(resultMap);
		log.info("response : " + json);
		return json;
	}

	@ApiOperation(value = "获取统计项详细数据")
	@RequestMapping(value = "/api/Statistics", method = { RequestMethod.POST })
	public String getProjectDetail(StatisticModel statisticModel, HttpServletRequest request,
			HttpServletResponse response) {
		JLPLog log = ThreadPoolConfig.getBaseContext();
		Map<String, Object> resultMap = new HashMap<String, Object>();

		JStatisconf statistics = statisconfService.getOne(statisticModel.getId());
		List<JStatisconfdetail> statisticDetails = statisconfdetailService.getByStatisconfid(statistics.getId());

		if (statisticDetails.size() == 0) {
			BaseResponse baseResponse = new BaseResponse();
			response.setStatus(404);
			baseResponse.setCode("404");
			baseResponse.setText("统计ID错误");
			resultMap.put("response", baseResponse);
			String json = JSONObject.toJSONString(resultMap);
			log.error(json);
			return json;
		}
		List<Long> fieldIds = new ArrayList<Long>();
		List<JTableFieldDict> tableFieldInfos = new ArrayList<JTableFieldDict>();
		for (JStatisconfdetail detail : statisticDetails) {
			fieldIds.add(detail.getFieldid());
			tableFieldInfos.add(tableFieldDictService.getOne(detail.getFieldid()));
		}

//        var tableFieldInfos = (from table in orclJlpContext.JTabledicts
//                               join field in orclJlpContext.JTablefielddicts on table.Code equals field.Tablecode into fields
//                               from field in fields
//                               where table.Activeflag == "1" && fieldIds.Contains(field.Id)
//                               select new { table, field }).ToList();
		if (tableFieldInfos.size() == 0) {
			BaseResponse baseResponse = new BaseResponse();
			response.setStatus(404);
			baseResponse.setCode("404");
			baseResponse.setText("未找到可用的统计字段，请联系技术人员");
			resultMap.put("response", baseResponse);
			String json = JSONObject.toJSONString(resultMap);
			log.error(json);
			return json;
		}

//        var fieldAllInfos = (from statisticDetail in statisticDetails
//                             join tablefield in tableFieldInfos on statisticDetail.Fieldid
//                             	equals tablefield.field.Id into tablefields
//                             from tablefield in tablefields
//                             select new { statisticDetail, tablefield.field, tablefield.table }).ToList();
		Set<String> keys = new HashSet<>();
		SortedSet<String> tableNames = new TreeSet<>();

		for (JTableFieldDict tableFieldDict : tableFieldInfos) {
			keys.add(tableFieldDict.getName().toLowerCase());

		}

		if (statisticModel.getKey() != null && statisticModel.getKey().size() > 0) {
			keys.addAll(statisticModel.getKey());
		}
		boolean IsCount = false;
		List<StatisticFieldModel> selectFields = new ArrayList<StatisticFieldModel>();
		List<StatisticFieldModel> groupFields = new ArrayList<StatisticFieldModel>();
		List<StatisticFieldModel> countFields = new ArrayList<StatisticFieldModel>();

		for (JStatisconfdetail detail : statisticDetails) {
			for (JTableFieldDict tableFieldDict : tableFieldInfos) {
				if (detail.getFieldid().longValue() == tableFieldDict.getId().longValue()) {
					JTableDict tableDict = tableDictService.getByCode(tableFieldDict.getTablecode());
					tableNames.add(tableDict.getName());
				}
			}

			if ("1".equals(detail.getDetailtype())) {
				for (JTableFieldDict tableFieldDict : tableFieldInfos) {
					if (detail.getFieldid().longValue() == tableFieldDict.getId().longValue()) {
						JTableDict tableDict = tableDictService.getByCode(tableFieldDict.getTablecode());
						StatisticFieldModel model = new StatisticFieldModel();
						model.setFieldTrans(detail.getFieldtypetrans());
						model.setFieldName(tableFieldDict.getName());
						model.setFieldTitle(tableFieldDict.getDescription());
						model.setTableName(tableDict.getName());
						selectFields.add(model);
					}
				}
			}
			if ("2".equals(detail.getDetailtype())) {
				for (JTableFieldDict tableFieldDict : tableFieldInfos) {
					if (detail.getFieldid().longValue() == tableFieldDict.getId().longValue()) {
						StatisticFieldModel model = new StatisticFieldModel();
						model.setFieldTrans(detail.getFieldtypetrans());
						model.setFieldName(tableFieldDict.getName());
						model.setFieldTitle(tableFieldDict.getDescription());
						JTableDict tableDict = tableDictService.getByCode(tableFieldDict.getTablecode());
						model.setTableName(tableDict.getName());
						selectFields.add(model);
					}
				}
			}
			if ("3".equals(detail.getDetailtype())) {
				for (JTableFieldDict tableFieldDict : tableFieldInfos) {
					if (detail.getFieldid().longValue() == tableFieldDict.getId().longValue()) {
						StatisticFieldModel model = new StatisticFieldModel();
						model.setFieldTrans(detail.getFieldtypetrans());
						model.setFieldName(tableFieldDict.getName());
						model.setFieldTitle(tableFieldDict.getDescription());
						JTableDict tableDict = tableDictService.getByCode(tableFieldDict.getTablecode());
						model.setTableName(tableDict.getName());
						selectFields.add(model);
					}
				}
			}

		}

		if (selectFields.size() == 0 && groupFields.size() == 0 && countFields.size() == 0) {
			BaseResponse baseResponse = new BaseResponse();
			response.setStatus(404);
			baseResponse.setCode("404");
			baseResponse.setText("统计字段设计错误，请联系技术人员");
			resultMap.put("response", baseResponse);
			String json = JSONObject.toJSONString(resultMap);
			log.error(json);
			return json;
		}
		IsCount = countFields.size() > 0;

		List<Map<String, Object>> columns = new ArrayList<Map<String, Object>>();
		for (StatisticFieldModel model : selectFields) {
			columns.add(new HashMap<String, Object>() {
				{
					put("dataIndex", model.getFieldName().toLowerCase());
					put("key", model.getFieldName().toLowerCase());
					put("title", model.getFieldTitle());
				}
			});

		}
		columns.add(new HashMap<String, Object>() {
			{
				put("dataIndex", "num");
				put("key", "num");
				put("title", "数量");
			}
		});

		String selctFieldstr = "";// selectFields.Select(x => x.ToString(false)).ToArray().ToStringEx();
		String groupFieldStr = "";// groupFields.Select(x => x.ToString(true)).ToArray().ToStringEx();
		for (StatisticFieldModel model : selectFields) {
			selctFieldstr += model.toString(false) + ",";
		}
		for (StatisticFieldModel model : groupFields) {
			groupFieldStr += model.toString(true) + ",";
		}
		if (selctFieldstr.length() > 0) {
			selctFieldstr = selctFieldstr.substring(0, selctFieldstr.length() - 1);
		}
		if (groupFieldStr.length() > 0) {
			groupFieldStr = groupFieldStr.substring(0, groupFieldStr.length() - 1);
		}

		String tempSqlWhere = " where D_PATIENTGLOBAL.jlactiveflag='1' ";
		String Sql = "";
		if (IsCount) {
			Sql = "select count(*) AS NUM from " + JLPServiceImpl.toTableString(tableNames, "") + " " + tempSqlWhere;
		} else {
			Sql = "select " + selctFieldstr + ",count(*) AS NUM from " + JLPServiceImpl.toTableString(tableNames, "")
					+ " " + tempSqlWhere + " group by " + groupFieldStr + " order by" + groupFieldStr;
		}
		List<Map<String, Object>> contents = jlpService.queryForSQL(Sql, new Object[] {});
		resultMap.put("response", response);
		resultMap.put("columns", columns);
		resultMap.put("content", contents);

		Map<String, List<String>> keyDicts = new HashMap<String, List<String>>();
		if (!IsCount && contents.size() > 0) {
//            Type type = contents[0].GetType();
//            var properties = type.GetProperties().ToList();
//            keyDicts.Clear();
//            properties.ForEach(x =>
//            {
//                if (x.Name !=  && !keyDicts.ContainsKey(x.Name))
//                {
//                    keyDicts.Add(x.Name, new List<string>());
//                }
//            });
			
			for (Map<String, Object> item : contents) {
				
				for (String mapKey : item.keySet()) {
					if(!"num".equals(mapKey) && !keyDicts.containsKey(mapKey)) {
						keyDicts.put(mapKey, new ArrayList<String>());
					}
						
					if (keyDicts.containsKey(mapKey)) {
						if (item.get(mapKey) == null || item.get(mapKey).toString().length() == 0) {
//                            pro.SetValue(item, "other");
						}
						if (!keyDicts.get(mapKey).contains(item.get(mapKey))) {
							keyDicts.get(mapKey).add(item.get(mapKey).toString());
						}
					}
				}
			}

			for (String item : keyDicts.keySet()) {
				if (keyDicts.get(item).contains("other")) {
					keyDicts.get(item).remove("other");
					keyDicts.get(item).add("other");
				}
			}
			resultMap.put("keyDict", keyDicts);
		}

		resultMap.put("response", new BaseResponse());
		String json = JSONObject.toJSONString(resultMap);
		log.info("response : " + json);
		return json;

	}

}
