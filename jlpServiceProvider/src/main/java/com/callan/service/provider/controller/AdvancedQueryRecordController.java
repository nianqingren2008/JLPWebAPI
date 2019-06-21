package com.callan.service.provider.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.callan.service.provider.pojo.AdvanceQueryRequest;
import com.callan.service.provider.pojo.advanceQueryBase.AdvancedQueryRecordModel;
import com.callan.service.provider.pojo.advanceQueryBase.Queries;
import com.callan.service.provider.pojo.advanceQueryBase.QueryConds;
import com.callan.service.provider.pojo.advanceQueryBase.QueryIncludesEX;
import com.callan.service.provider.pojo.advanceQueryBase.QueryIncludesEXCondition;
import com.callan.service.provider.pojo.base.BaseResponse;
import com.callan.service.provider.pojo.db.JAdvancedqr;
import com.callan.service.provider.pojo.db.JAdvancedqrItem;
import com.callan.service.provider.pojo.db.JQueryrecord;
import com.callan.service.provider.pojo.db.JQueryrecordDetails;
import com.callan.service.provider.service.IJAdvancedqrItemService;
import com.callan.service.provider.service.IJAdvancedqrService;
import com.callan.service.provider.service.IJLpService;
import com.callan.service.provider.service.IJProjectService;
import com.callan.service.provider.service.IJQueryrecordDetailService;
import com.callan.service.provider.service.IJQueryrecordService;
import com.callan.service.provider.service.IJUserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(description = "纳排记录查询")
public class AdvancedQueryRecordController {
	@Autowired
	private IJUserService userService;

	@Autowired
	private IJAdvancedqrService advancedqrService;

	@Autowired
	private IJQueryrecordService queryrecordService;

	@Autowired
	private IJProjectService projectService;

	@Autowired
	private IJLpService jlpService;

	@Autowired
	private IJAdvancedqrItemService advancedqrItemService;

	@Autowired
	private IJQueryrecordDetailService queryrecordDetailService;

	@ApiOperation(value = "获取纳排条件列表")
	@RequestMapping(value = "/api/AdvancedQueryRecord/list", method = { RequestMethod.GET })
	public String getRecords(HttpServletRequest request) {
		JLPLog log = ThreadPoolConfig.getBaseContext();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		// 从前台header中获取token参数
		String authorization = request.getHeader("Authorization") == null ? "6995033dec41df257013eef145dded3f"
				: request.getHeader("Authorization");
		Long userId = userService.getIdByToken(authorization);
		if (userId == null || userId == 0) {
			BaseResponse baseResponse = new BaseResponse();
			baseResponse.setCode("0000");
			baseResponse.setText("用户信息获取失败，请检查请求头");
			resultMap.put("response", baseResponse);
			return JSONObject.toJSONString(resultMap);
		}
		log.info("userId : " + userId);
		List<JAdvancedqr> list = advancedqrService.getByUserId(userId);

		Collections.sort(list, new Comparator<JAdvancedqr>() {
			@Override
			public int compare(JAdvancedqr o1, JAdvancedqr o2) {
				return o1.getCreatedate().compareTo(o2.getCreatedate());
			}
		});
		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		for (JAdvancedqr advancedqr : list) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", advancedqr.getId());
			map.put("name", advancedqr.getAqname() == null ? "" : advancedqr.getAqname());
			map.put("createdate", sdf.format(advancedqr.getCreatedate()));
			resultList.add(map);
		}

		resultMap.put("response", new BaseResponse());

		resultMap.put("queryRecords", resultList);
		String json = JSONObject.toJSONString(resultMap);
		log.info("response : " + json);
		return json;
	}

	@ApiOperation(value = "获取纳排详细条件")
	@RequestMapping(value = "/api/AdvancedQueryRecord/detail/{id}", method = { RequestMethod.GET })
	public String getRecordDetail(HttpServletRequest request, @RequestParam Long id) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		// 从前台header中获取token参数
		JLPLog log = ThreadPoolConfig.getBaseContext();
		String authorization = request.getHeader("Authorization") == null ? "f5d3689f6aa282f23d496907593b5176"
				: request.getHeader("Authorization");
		Long userId = userService.getIdByToken(authorization);
		if (userId == null || userId == 0) {
			BaseResponse baseResponse = new BaseResponse();
			baseResponse.setCode("0000");
			baseResponse.setText("用户信息获取失败，请检查请求头");
			resultMap.put("response", baseResponse);
			String json = JSONObject.toJSONString(resultMap);
			log.info("response : " + json);
			return json;
		}
		JAdvancedqr advancedqr = advancedqrService.getOne(id);

		if (advancedqr == null || advancedqr.getUserid() != userId) {
			BaseResponse baseResponse = new BaseResponse();
			baseResponse.setCode("0000");
			baseResponse.setText("不存在此纳排记录");
			resultMap.put("response", baseResponse);
			String json = JSONObject.toJSONString(resultMap);
			log.info("response : " + json);
			return json;
		}
		List<JAdvancedqrItem> itemList = advancedqrItemService.getByQrId(id);
		
		List<JQueryrecordDetails> condsDetailList = new ArrayList<JQueryrecordDetails>();
		List<JAdvancedqrItem> includeDetailList = new ArrayList<JAdvancedqrItem>();
		List<JAdvancedqrItem> excludeDetailList = new ArrayList<JAdvancedqrItem>();
		if(itemList != null) {
			for (JAdvancedqrItem item : itemList) {
				JQueryrecord record = queryrecordService.getOne(item.getQueryid());
				List<JQueryrecordDetails> detailList = queryrecordDetailService.getByQueryId(record.getId());
				record.setDetailList(detailList);
	//        	record.setAdvancedqrItem(item);
				item.setQueryrecord(record);
//				for (JQueryrecordDetails detail : record.getDetailList()) {
//					detail.setQueryrecord(record);
//				}
	
				if ("1".equals(item.getItemtype())) {
					condsDetailList.addAll(detailList);
				}
				if ("2".equals(item.getItemtype())) {
					includeDetailList.add(item);
				}
				if ("3".equals(item.getItemtype())) {
					excludeDetailList.add(item);
				}
	
			}
		}
		AdvancedQueryRecordModel advancedQueryRecord = new AdvancedQueryRecordModel();
		advancedQueryRecord.setId(advancedqr.getId());
		advancedQueryRecord.setName(advancedqr.getAqname());
		advancedQueryRecord.setProjectId(ObjectUtil.objToLong(advancedqr.getProjectid()));
		Queries queries = new Queries();
		advancedQueryRecord.setQueries(queries);
		List<QueryConds> queryConds = new ArrayList<QueryConds>();
		queries.setQueryConds(queryConds);
		for (JQueryrecordDetails detail : condsDetailList) {
			QueryConds conds = new QueryConds();
			conds.setCombinator(detail.getLogicaltype());
			conds.setCondition(detail.getFieldname());
			conds.setCondValue(detail.getFieldvalue());
			conds.setFieldType(detail.getFieldvaluetype());
			conds.setLeftqueto(detail.getLeftbrackets());
			conds.setRelation(detail.getRelationtype());
			conds.setRightqueto(detail.getRightbrackets());
			queryConds.add(conds);
		}

		QueryIncludesEX queryIncludesEX = new QueryIncludesEX();
		queries.setQueryIncludesEX(queryIncludesEX);

		Collections.sort(includeDetailList, new Comparator<JAdvancedqrItem>() {
			@Override
			public int compare(JAdvancedqrItem o1, JAdvancedqrItem o2) {
				return o1.getItemno().compareTo(o2.getItemno());
			}
		});
		List<QueryIncludesEXCondition> includes = new ArrayList<QueryIncludesEXCondition>();
		for (JAdvancedqrItem item : includeDetailList) {
			QueryIncludesEXCondition queryIncludesEXCondition = new QueryIncludesEXCondition();
			queryIncludesEXCondition.setId(item.getModelid());
			queryIncludesEXCondition.setLeftqueto(item.getLeftqueto());
			queryIncludesEXCondition.setSetCombinator(item.getSetcombinatortype());
			queryIncludesEXCondition.setType(item.getModeltype());

			List<QueryConds> condsList = new ArrayList<QueryConds>();

			JQueryrecord record = item.getQueryrecord();
			for (JQueryrecordDetails detail : record.getDetailList()) {
				QueryConds conds = new QueryConds();
				conds.setCombinator(detail.getLogicaltype());
				conds.setCondition(detail.getFieldname());
				conds.setCondValue(detail.getFieldvalue());
				conds.setFieldType(detail.getFieldvaluetype());
				conds.setRelation(detail.getRelationtype());
				conds.setRightqueto(detail.getRightbrackets());
				condsList.add(conds);
			}
			queryIncludesEXCondition.setConds(condsList);
			;
			includes.add(queryIncludesEXCondition);
		}
		List<QueryIncludesEXCondition> excludes = new ArrayList<QueryIncludesEXCondition>();
		for (JAdvancedqrItem item : excludeDetailList) {
			QueryIncludesEXCondition queryIncludesEXCondition = new QueryIncludesEXCondition();
			queryIncludesEXCondition.setId(item.getModelid());
			queryIncludesEXCondition.setLeftqueto(item.getLeftqueto());
			queryIncludesEXCondition.setSetCombinator(item.getSetcombinatortype());
			queryIncludesEXCondition.setType(item.getModeltype());

			List<QueryConds> condsList = new ArrayList<QueryConds>();

			JQueryrecord record = item.getQueryrecord();
			for (JQueryrecordDetails detail : record.getDetailList()) {
				QueryConds conds = new QueryConds();
				conds.setCombinator(detail.getLogicaltype());
				conds.setCondition(detail.getFieldname());
				conds.setCondValue(detail.getFieldvalue());
				conds.setFieldType(detail.getFieldvaluetype());
				conds.setRelation(detail.getRelationtype());
				conds.setRightqueto(detail.getRightbrackets());
				condsList.add(conds);
			}
			queryIncludesEXCondition.setConds(condsList);
			;
			excludes.add(queryIncludesEXCondition);
		}
		queryIncludesEX.setIncludes(includes);
		queryIncludesEX.setExcludes(excludes);

		BaseResponse baseResponse = new BaseResponse();
		resultMap.put("response", baseResponse);
		resultMap.put("queryRecord", advancedQueryRecord);
		String json = JSONObject.toJSONString(resultMap);
		log.info("response : " + json);
		return json;
	}

	/**
	 * 添加纳排条件记录
	 * 
	 * @return
	 */
	@ApiOperation(value = "添加纳排记录")
	@RequestMapping(value = "/api/AdvancedQueryRecord/add", method = { RequestMethod.POST })
	public String addRecord(@RequestBody String advancedQueryRecord, HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		JLPLog log = ThreadPoolConfig.getBaseContext();
		log.info("request --> " + advancedQueryRecord);
		AdvancedQueryRecordModel advancedQueryRecordModel = null;
		try {
			advancedQueryRecordModel = JSONObject.toJavaObject(JSONObject.parseObject(advancedQueryRecord),
					AdvancedQueryRecordModel.class);
		} catch (Exception e) {
			log.error(e);
		}

		// 判断传入条件是否为空
		if (advancedQueryRecordModel == null) {
			BaseResponse baseResponse = new BaseResponse();
			baseResponse.setCode("0000");
			baseResponse.setText("无有效的条件");
			resultMap.put("response", baseResponse);
			String json = JSONObject.toJSONString(resultMap);
			log.info("response --> " + json);
			return json;
		}

		// 纳排记录主表信息
		// 从前台header中获取token参数
		String authorization = request.getHeader("Authorization") == null ? "6c52445e47389d707807022cbba731cd"
				: request.getHeader("Authorization");
		Long userId = userService.getIdByToken(authorization);
		if (userId == null || userId == 0) {
			BaseResponse baseResponse = new BaseResponse();
			baseResponse.setCode("0000");
			baseResponse.setText("用户信息获取失败，请检查请求头");
			resultMap.put("response", baseResponse);
			String json = JSONObject.toJSONString(resultMap);
			log.info("response --> " + json);
			return json;
		}
		JAdvancedqr jAdvancedqr = advancedqrService.getOne(advancedQueryRecordModel.getId());

		boolean IsNew = false;
		if (jAdvancedqr == null || jAdvancedqr.getUserid() != userId) {
			if (advancedQueryRecordModel.getId() != 0) {
				BaseResponse baseResponse = new BaseResponse();
				baseResponse.setCode("0000");
				baseResponse.setText("无有效的条件");
				resultMap.put("response", baseResponse);
				String json = JSONObject.toJSONString(resultMap);
				log.info("response --> " + json);
				return json;
			} else {
				jAdvancedqr = new JAdvancedqr();
				IsNew = true;
			}
		}
//		else {
//			List<JAdvancedqrItem> itemList = jAdvancedqr.getItemList();
//			for (JAdvancedqrItem item : itemList) {
//
//			}
////            advancedQRItems.ForEach(x => { x.  orclJlpContext.Entry(x).State = System.Data.Entity.EntityState.Modified; });
//		}

		jAdvancedqr.setSortno(1);
		jAdvancedqr.setAqname(advancedQueryRecordModel.getName());
		if (advancedQueryRecordModel.getProjectId() > 0
				&& projectService.getOne(advancedQueryRecordModel.getProjectId()) != null) {
//			List<JAdvancedqr> projectList = advancedqrService.getByProjectId(advancedQueryRecordModel.getProjectId());

//			for (JAdvancedqr advancedqr : projectList) {
//				advancedqr.setProjectid(0L);
//			}
			jAdvancedqr.setProjectid(advancedQueryRecordModel.getProjectId());
		}

		jAdvancedqr.setCreatedate(new Date());

		if (IsNew) {
			jAdvancedqr.setActiveflag(JLPConts.ActiveFlag);
			jAdvancedqr.setUserid(userId);
			long seqId = jlpService.getNextSeq("J_ADVANCEDQR");
			jAdvancedqr.setId(seqId);
			if (jAdvancedqr.getProjectid() == null) {
				jAdvancedqr.setProjectid(0L);
			}
			advancedqrService.save(jAdvancedqr);
		} else {
//             orclJlpContext.Entry(jAdvancedqr).State = System.Data.Entity.EntityState.Modified;
		}

//        #region 查询条件保存
		int itemNo = 0;

//        #region 普通查询条件
		if (advancedQueryRecordModel.getQueries().getQueryConds() != null
				&& advancedQueryRecordModel.getQueries().getQueryConds().size() > 0) {
			JAdvancedqrItem advancedqrItem = new JAdvancedqrItem();
			advancedqrItem.setActiveflag(JLPConts.ActiveFlag);
			advancedqrItem.setCreatedate(new Date());
			advancedqrItem.setItemno(itemNo++);
			advancedqrItem.setItemtype("1");
			advancedqrItem.setLeftqueto("");
			advancedqrItem.setModelid("");
			advancedqrItem.setModeltype("");
			advancedqrItem.setQrid(jAdvancedqr.getId());
			advancedqrItem.setRightqueto("");
			advancedqrItem.setSetcombinatortype("");

			JQueryrecord queryrecord = new JQueryrecord();
			queryrecord.setActiveflag(JLPConts.ActiveFlag);
			queryrecord.setCreatedate(new Date());
			queryrecord.setSortno(1);
			queryrecord.setUpdatedate(new Date());
			queryrecord.setUserid(userId);
			long queryId = jlpService.getNextSeq("J_QUERYRECORD");
			queryrecord.setId(queryId);
			advancedqrItem.setQueryid(queryId);
			long queryItemId = jlpService.getNextSeq("J_ADVANCEDQRITEM");
			advancedqrItem.setId(queryItemId);
			queryrecord.setQueryname("");
			queryrecord.setCount(0L);

			List<QueryConds> condsList = advancedQueryRecordModel.getQueries().getQueryConds();
			List<JQueryrecordDetails> detailList = toJqueryrecorddetailList(condsList, queryId);
			queryrecordService.save(queryrecord);
			advancedqrItemService.save(advancedqrItem);
			for (JQueryrecordDetails detail : detailList) {
				queryrecordDetailService.save(detail);
			}
		}

//        #region 纳入条件
		if (advancedQueryRecordModel.getQueries().getQueryIncludesEX() != null) {

			List<QueryIncludesEXCondition> includes = advancedQueryRecordModel.getQueries().getQueryIncludesEX()
					.getIncludes();
			if (includes != null) {
				for (QueryIncludesEXCondition item : includes) {
					try {
						saveEx(advancedQueryRecordModel, userId, jAdvancedqr, itemNo, item);
					} catch (Exception e) {
						e.printStackTrace();
					}

				}
			}

//        #region 排除条件
			List<QueryIncludesEXCondition> excludes = advancedQueryRecordModel.getQueries().getQueryIncludesEX()
					.getExcludes();
			if (includes != null) {
				for (QueryIncludesEXCondition item : excludes) {
					try {
						saveEx(advancedQueryRecordModel, userId, jAdvancedqr, itemNo, item);
					} catch (Exception e) {
						e.printStackTrace();
					}

				}
			}
		}
//        #region 返回值
		BaseResponse baseResponse = new BaseResponse();
		resultMap.put("response", baseResponse);
		Map<String, Object> queryRecord = new HashMap<String, Object>();
		queryRecord.put("id", jAdvancedqr.getId());
		queryRecord.put("name", jAdvancedqr.getAqname());
		queryRecord.put("projectId", jAdvancedqr.getProjectid());
		queryRecord.put("createDate", jAdvancedqr.getCreatedate());
		resultMap.put("queryRecord", queryRecord);
		String json = JSONObject.toJSONString(resultMap);
		log.info("response --> " + json);
		return json;
	}

	private void saveEx(AdvancedQueryRecordModel advancedQueryRecord, Long userId, JAdvancedqr jAdvancedqr, int itemNo,
			QueryIncludesEXCondition item) throws Exception {
		JAdvancedqrItem advancedqrItem = new JAdvancedqrItem();
		advancedqrItem.setActiveflag(JLPConts.ActiveFlag);
		advancedqrItem.setCreatedate(new Date());
		advancedqrItem.setItemno(itemNo++);
		advancedqrItem.setItemtype("2");
		advancedqrItem.setLeftqueto(item.getLeftqueto());
		advancedqrItem.setModelid(item.getId());
		advancedqrItem.setModeltype(item.getType());
		advancedqrItem.setQrid(jAdvancedqr.getId());
		advancedqrItem.setRightqueto(item.getRightqueto());
		advancedqrItem.setSetcombinatortype(item.getSetCombinator());

		JQueryrecord queryrecord = new JQueryrecord();
		queryrecord.setActiveflag(JLPConts.ActiveFlag);
		queryrecord.setCreatedate(new Date());
		queryrecord.setSortno(1);
		queryrecord.setUpdatedate(new Date());
		queryrecord.setUserid(userId);
		long queryId = jlpService.getNextSeq("J_QUERYRECORD");
		queryrecord.setId(queryId);
		advancedqrItem.setQueryid(queryId);

		List<QueryConds> condsList = advancedQueryRecord.getQueries().getQueryConds();
		List<JQueryrecordDetails> detailList = toJqueryrecorddetailList(condsList, queryId);
		queryrecordService.save(queryrecord);
		advancedqrItemService.save(advancedqrItem);
		for (JQueryrecordDetails detail : detailList) {
			queryrecordDetailService.save(detail);
		}
	}

	private List<JQueryrecordDetails> toJqueryrecorddetailList(List<QueryConds> queryDetails, long queryId) {
		List<JQueryrecordDetails> ret = new ArrayList<JQueryrecordDetails>();
		if (queryDetails != null) {
			int i = 0;
			for (QueryConds conds : queryDetails) {
				JQueryrecordDetails detail = new JQueryrecordDetails();
				detail.setActiveflag(JLPConts.ActiveFlag);
				detail.setCreatedate(new Date());
				detail.setDetailid((short) i++);
				detail.setFieldname(conds.getCondition());
				detail.setFieldvalue(conds.getCondValue());
				detail.setFieldvaluetype(conds.getFieldType());
				detail.setLeftbrackets(conds.getLeftqueto());
				detail.setLogicaltype(conds.getCombinator());
				detail.setRelationtype(ObjectUtil.objToString(conds.getRelation()));
				detail.setRightbrackets(conds.getRightqueto());
				detail.setUpdatedate(new Date());
				detail.setQueryid(queryId);
				long detailId = 0L;
				try {
					detailId = jlpService.getNextSeq("J_QUERYRECORDDETAILS");
				} catch (Exception e) {
					e.printStackTrace();
				}
				detail.setId(detailId);
				ret.add(detail);
			}
		}
		return ret;
	}

	/**
	 * 删除纳排条件记录
	 * 
	 * @param Id
	 * @return
	 */
	@ApiOperation(value = "删除纳排记录")
	@RequestMapping(value = "/api/AdvancedQueryRecord/delete/{id}", method = { RequestMethod.GET })
	public String delete(@RequestParam long id, HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String authorization = request.getHeader("Authorization") == null ? "6c52445e47389d707807022cbba731cd"
				: request.getHeader("Authorization");
		Long userId = userService.getIdByToken(authorization);
		if (userId == null || userId == 0) {
			BaseResponse baseResponse = new BaseResponse();
			baseResponse.setCode("0000");
			baseResponse.setText("用户信息获取失败，请检查请求头");
			resultMap.put("response", baseResponse);
			return JSONObject.toJSONString(resultMap);
		}
		JAdvancedqr advancedqr = advancedqrService.getOne(id);

		if (advancedqr == null || advancedqr.getUserid() != userId) {
			BaseResponse baseResponse = new BaseResponse();
			baseResponse.setCode("0000");
			baseResponse.setText("不存在此纳排记录");
			resultMap.put("response", baseResponse);
			return JSONObject.toJSONString(resultMap);
		}
		advancedqr.setActiveflag(JLPConts.Inactive);
		advancedqrService.save(advancedqr);
		BaseResponse baseResponse = new BaseResponse();
		resultMap.put("response", baseResponse);
		return JSONObject.toJSONString(resultMap);
	}

}
