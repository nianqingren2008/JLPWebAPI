package com.callan.service.provider.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.callan.service.provider.config.HanyuUtil;
import com.callan.service.provider.config.JLPConts;
import com.callan.service.provider.config.JLPLog;
import com.callan.service.provider.config.ObjectUtil;
import com.callan.service.provider.config.ThreadPoolConfig;
import com.callan.service.provider.pojo.advanceQueryBase.QueryDetailModel;
import com.callan.service.provider.pojo.base.BaseResponse;
import com.callan.service.provider.pojo.db.JQueryrecord;
import com.callan.service.provider.pojo.db.JQueryrecordDetails;
import com.callan.service.provider.pojo.db.JUser;
import com.callan.service.provider.pojo.queryField.QueryFieldModel;
import com.callan.service.provider.pojo.queryField.QueryRecordModel;
import com.callan.service.provider.service.IJLpService;
import com.callan.service.provider.service.IJQueryrecordDetailService;
import com.callan.service.provider.service.IJQueryrecordService;
import com.callan.service.provider.service.IJUserService;
import com.callan.service.provider.service.impl.JLPServiceImpl;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.sourceforge.pinyin4j.PinyinHelper;

@RestController
@Api(description = "获取可查询字段")
public class QueryRecordController {

	@Autowired
	private IJUserService userService;

	@Autowired
	private IJLpService jlpService;
	@Autowired
	private IJQueryrecordService queryrecordService;

	@Autowired
	private IJQueryrecordDetailService queryrecordDetailService;

	@ApiOperation(value = "获取查询条件列表(默认按照时间倒序)")
	@RequestMapping(value = "/api/QueryRecord", method = { RequestMethod.POST })
	public String GetQueryRecords(Integer pageNum, Integer pageSize, HttpServletRequest request,
			HttpServletResponse response) {
		JLPLog log = ThreadPoolConfig.getBaseContext();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String authorization = request.getHeader("Authorization") == null ? "" : request.getHeader("Authorization");
		JUser user = userService.getUserByToken(authorization);
		if (user == null || user.getId().longValue() == 0) {
			BaseResponse baseResponse = new BaseResponse();
			response.setStatus(404);
			baseResponse.setCode("404");
			baseResponse.setText("用户信息获取失败，请检查请求头");
			resultMap.put("response", baseResponse);
			String json = JSONObject.toJSONString(resultMap);
			log.error("response --> " + json);
			return json;
		}
		if (pageSize == null || pageSize <= 0) {
			pageSize = 20;
		}
		if (pageNum == null || pageNum <= 0) {
			pageNum = 1;
		}
		String sql = "SELECT ID,USERID,QUERYNAME,UPDATEDATE,CREATEDATE" + "ACTIVEFLAG,SORTNO,COUNT FROM J_QUERYRECORD"
				+ " WHERE USERID = " + user.getId() + " and activeflag='" + JLPConts.ActiveFlag
				+ "' order by createdate desc";

		String pageSql = JLPServiceImpl.getPageSql(sql, pageNum, pageSize);
		log.info("pageSql : " + pageSql);
		List<Map<String, Object>> queryrecordsList = jlpService.queryForSQL(pageSql, new Object[] {});
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List<Map<String, Object>> queryrecords = new ArrayList<>();
		if (queryrecordsList != null) {
			for (Map<String, Object> map : queryrecordsList) {
				Map<String, Object> newMap = new HashMap<>();
				newMap.put("id", map.get("ID"));
				newMap.put("name", map.get("QUERYNAME"));
				try {
					newMap.put("createdate", sdf.format(map.get("CREATEDATE")));
					newMap.put("updatedate", sdf.format(map.get("UPDATEDATE")));
				} catch (Exception e) {
					log.warn(e);
				}
				queryrecords.add(newMap);
			}
		}
		resultMap.put("response", new BaseResponse());
		resultMap.put("queryRecord", queryrecords);
		String json = JSONObject.toJSONString(resultMap);
		log.info("response : " + json);
		return json;
	}

	@ApiOperation(value = "查询条件提交")
	@RequestMapping(value = "/api/QueryRecord", method = { RequestMethod.POST })
	public String Post(@RequestBody QueryRecordModel queryRecord, HttpServletRequest request,
			HttpServletResponse response) {
		JLPLog log = ThreadPoolConfig.getBaseContext();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String authorization = request.getHeader("Authorization") == null ? "" : request.getHeader("Authorization");
		JUser user = userService.getUserByToken(authorization);
		if (user == null || user.getId().longValue() == 0) {
			BaseResponse baseResponse = new BaseResponse();
			response.setStatus(404);
			baseResponse.setCode("404");
			baseResponse.setText("用户信息获取失败，请检查请求头");
			resultMap.put("response", baseResponse);
			String json = JSONObject.toJSONString(resultMap);
			log.error("response --> " + json);
			return json;
		}

		JQueryrecord queryrecord = queryrecordService.getOne(queryRecord.getId());
		if (queryrecord == null) {
			queryrecord = new JQueryrecord();
			queryrecord.setQueryname(queryRecord.getQrName());
			queryrecord.setActiveflag(JLPConts.ActiveFlag);
			queryrecord.setCount(0L);
			queryrecord.setCreatedate(new Date());
			queryrecord.setSortno(1);
			queryrecord.setUpdatedate(new Date());
			queryrecord.setUserid(user.getId());
			long seqId = jlpService.getNextSeq("J_QUERYRECORD");
			queryrecord.setId(seqId);
			queryrecordService.save(queryrecord);
		} else {
			queryrecord.setUpdatedate(new Date());
			queryrecord.setQueryname(queryRecord.getQrName());
			queryrecordService.update(queryrecord);

			List<JQueryrecordDetails> detailList = queryrecordDetailService.getByQueryId(queryrecord.getId());
			if (detailList != null) {
				for (JQueryrecordDetails queryrecordDetails : detailList) {
					queryrecordDetails.setUpdatedate(new Date());
					queryrecordDetails.setActiveflag(JLPConts.ActiveFlag);
					queryrecordDetailService.update(queryrecordDetails);
				}
			}
		}

		if (queryRecord.getQueries() != null) {
			short index = 0;
			for (QueryDetailModel model : queryRecord.getQueries()) {
				JQueryrecordDetails queryrecorddetails = new JQueryrecordDetails();
				queryrecorddetails.setActiveflag(JLPConts.ActiveFlag);
				queryrecorddetails.setCreatedate(new Date());
				queryrecorddetails.setDetailid(++index);
				queryrecorddetails.setFieldname(model.getCondition());
				queryrecorddetails.setFieldvalue(model.getCondValue());
				queryrecorddetails.setFieldvaluetype(model.getFieldType());
				queryrecorddetails.setLeftbrackets(model.getLeftqueto());
				queryrecorddetails.setLogicaltype(model.getCombinator());
				queryrecorddetails.setQueryid(queryrecord.getId());
				queryrecorddetails.setRelationtype(model.getRelation());
				queryrecorddetails.setRightbrackets(model.getRightqueto());
				queryrecorddetails.setUpdatedate(new Date());
				long seqId = jlpService.getNextSeq("J_QUERYRECORDDETAILS");
				queryrecorddetails.setId(seqId);
				queryrecordDetailService.save(queryrecorddetails);
			}
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Map<String, Object> queryInfo = new HashMap<String, Object>();
		queryInfo.put("id", queryrecord.getId());
		queryInfo.put("name", queryrecord.getQueryname());
		try {
			queryInfo.put("createdate", sdf.format(queryrecord.getCreatedate()));
			queryInfo.put("updatedate", sdf.format(queryrecord.getUpdatedate()));
		} catch (Exception e) {
			log.warn(e);
		}
		resultMap.put("queryRecord", queryInfo);

		resultMap.put("response", new BaseResponse());
		String json = JSONObject.toJSONString(resultMap);
		log.info("response : " + json);
		return json;

	}

	@ApiOperation(value = "移除查询条件")
	@RequestMapping(value = "/api/QueryRecord", method = { RequestMethod.DELETE })
	public String Delete(@PathVariable Long Id, HttpServletRequest request, HttpServletResponse response) {
		JLPLog log = ThreadPoolConfig.getBaseContext();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String authorization = request.getHeader("Authorization") == null ? "" : request.getHeader("Authorization");
		JUser user = userService.getUserByToken(authorization);
		if (user == null || user.getId().longValue() == 0) {
			BaseResponse baseResponse = new BaseResponse();
			response.setStatus(404);
			baseResponse.setCode("404");
			baseResponse.setText("用户信息获取失败，请检查请求头");
			resultMap.put("response", baseResponse);
			String json = JSONObject.toJSONString(resultMap);
			log.error("response --> " + json);
			return json;
		}

		JQueryrecord queryrecord = queryrecordService.getOne(Id);
		if (queryrecord == null) {
			BaseResponse baseResponse = new BaseResponse();
			response.setStatus(400);
			baseResponse.setCode("400");
			baseResponse.setText("查询ID号不存在");
			resultMap.put("response", baseResponse);
			String json = JSONObject.toJSONString(resultMap);
			log.error("response --> " + json);
			return json;
		}
		if (queryrecord.getUserid().longValue() != user.getId().longValue()) {
			BaseResponse baseResponse = new BaseResponse();
			response.setStatus(400);
			baseResponse.setCode("400");
			baseResponse.setText("用户ID对比错误");
			resultMap.put("response", baseResponse);
			String json = JSONObject.toJSONString(resultMap);
			log.error("response --> " + json);
			return json;
		}
		queryrecord.setActiveflag(JLPConts.Inactive);
		queryrecordService.update(queryrecord);
		resultMap.put("response", new BaseResponse());
		String json = JSONObject.toJSONString(resultMap);
		log.info("response : " + json);
		return json;
	}
}
