package com.callan.service.provider.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.callan.service.provider.config.JLPLog;
import com.callan.service.provider.config.ThreadPoolConfig;
import com.callan.service.provider.pojo.base.BaseResponse;
import com.callan.service.provider.pojo.db.JStatisconf;
import com.callan.service.provider.pojo.db.JStatisconfdetail;
import com.callan.service.provider.service.IJLpService;
import com.callan.service.provider.service.IJStatisconfService;
import com.callan.service.provider.service.IJUserService;

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
	private IJStatisconfService jStatisconfService;
	
	@ApiOperation(value = "获取统计项列表")
	@RequestMapping(value = "/api/Statistics/pageCode", method = { RequestMethod.GET })
	public String  geStatistics(String pageCode,HttpServletRequest request){
		JLPLog log = ThreadPoolConfig.getBaseContext();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		pageCode = StringUtils.isBlank(pageCode)?"main":pageCode;
		String sql = " select distinct k1.code,k1.title,k1.id  from ( "
				+ " select n1.id, "+
				"    n1.code,  "+
				"    n1.title,   "+
				"    n1.createdate, "+
				"       n1.pagecode, "+
				"       n1.pagetitle from (  "+
				" select w1.id,  "+
				"       w1.code,  "+
				"       w1.title, "+
				"       w1.createdate, "+
				"       w1.pagecode, "+
				"       w1.pagetitle, "+
				"       w1.fieldid, "+ 
				"       w2.name,    "+
				"       w2.description "+
				"  from (select t.id, "+
				"               t.code, "+
				"               t.title, "+
				"               t.createdate, "+
				"               t.pagecode, "+
				"               t.pagetitle, "+
				"               m.fieldid "+
				"          from j_statisconf t "+
				"          left join j_statisconfdetail m "+
				"            on t.id = m.statisconfid "+
				"         where t.activeflag = '1' and t.pagecode = '"+pageCode+"') w1 "+
				"  left join j_tablefielddict w2 "+
				"    on w1.fieldid = w2.id) n1  group  by   n1.id, "+
				"       n1.code, "+
				"       n1.title, "+
				"       n1.createdate, "+
				"       n1.pagecode, "+
				"       n1.pagetitle "
				+ " ) k1  order by k1.id ";
		log.info("geStatistics-->>sql : " + sql);
		int pageNum = 1, pageSize =20;
		List<Map<String, Object>> resultList = jlpService.queryForSQLStreaming(sql,  pageNum,  pageSize);
		List<JStatisconf> jStatisconfList = new ArrayList<JStatisconf>();
		List<JStatisconfdetail> jStatisconfdetails=null;
		Map<String,List<JStatisconfdetail>> detailsMap = new HashMap<String, List<JStatisconfdetail>>();
		if(resultList!=null&&resultList.size()>0) {
			for ( Map<String, Object>  map : resultList) {
				JStatisconf jStatisconf = new  JStatisconf();
				jStatisconf.setId((Long.valueOf(map.get("id")+"")) );
				jStatisconf.setCode(map.get("code")+"");
				jStatisconf.setTitle(map.get("title")+"");
				jStatisconfList.add(jStatisconf);
				
				jStatisconfdetails = jStatisconfService.queryDetailListById(Long.valueOf(map.get("id")+""));
				detailsMap.put(map.get("code")+"", jStatisconfdetails);
			}
			
		
			
		}
		else {
			BaseResponse baseResponse = new BaseResponse();
			baseResponse.setCode("0000");
			baseResponse.setText("错误的页面编号");
			resultMap.put("response", baseResponse);
			return JSONObject.toJSONString(resultMap);
		}
		
		resultMap.put("response", new BaseResponse());
		resultMap.put("statistics", jStatisconfList);
		resultMap.put("statisticDetails", detailsMap);
		String json = JSONObject.toJSONString(resultMap);
		log.info("response : " + json);
		return json;
 	}
	
	@ApiOperation(value = "课题中各分类数据统计")
	@RequestMapping(value = "/api/Statistics/Project/Id", method = { RequestMethod.POST})
	public String  getProject(long id,HttpServletRequest request){
		JLPLog log = ThreadPoolConfig.getBaseContext();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		// 从前台header中获取token参数
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
		log.info("userId : " + userId);
		
		
		
		return null;
	}
	
	@ApiOperation(value = "获取统计项详细数据")
	@RequestMapping(value = "/api/Statistics", method = { RequestMethod.POST})
	public String  getProjectDetail(long id,HttpServletRequest request){
		JLPLog log = ThreadPoolConfig.getBaseContext();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		// 从前台header中获取token参数
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
		log.info("userId : " + userId);
		
		
		
		return null;
	}

}
