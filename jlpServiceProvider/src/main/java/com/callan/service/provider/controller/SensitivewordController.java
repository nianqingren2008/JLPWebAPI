package com.callan.service.provider.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.callan.service.provider.config.JLPLog;
import com.callan.service.provider.config.JLPMapUtil;
import com.callan.service.provider.config.ThreadPoolConfig;
import com.callan.service.provider.pojo.ControllerBaseResponse;
import com.callan.service.provider.pojo.base.BaseResponse;
import com.callan.service.provider.pojo.db.JSensitiveWord;
import com.callan.service.provider.pojo.db.JTableFieldDict;
import com.callan.service.provider.service.IJSensitiveWordService;
import com.callan.service.provider.service.IJTableFieldDictService;
import com.callan.service.provider.service.IJUserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(description = "敏感词管理")
public class SensitivewordController {
	@Autowired
	private IJUserService userService;
	@Autowired
	private IJSensitiveWordService jSensitiveWordService;
	@Autowired
	private IJTableFieldDictService jTablefielddictService;
	
	@ApiOperation(value = "获取敏感词列表")
	@RequestMapping(value = "/api/Sensitiveword", method = { RequestMethod.GET })
	public String getSensitivewordList(HttpServletRequest request) {
		JLPLog log = ThreadPoolConfig.getBaseContext();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		BaseResponse baseResponse = new BaseResponse();
		/*// 从前台header中获取token参数
		String authorization = request.getHeader("Authorization") == null ? "6c52445e47389d707807022cbba731cd"
				: request.getHeader("Authorization");
		Long userId = userService.getIdByToken(authorization);
		if (userId == null || userId == 0) {
			baseResponse.setCode("0000");
			baseResponse.setText("用户信息获取失败，请检查请求头");
			resultMap.put("response", baseResponse);
			return JSONObject.toJSONString(resultMap);
		}
		log.info("userId : " + userId);*/
		
		List<Map<String, String>> resultList = new ArrayList<Map<String, String>>();
		List<JSensitiveWord> sensitivewordList = new ArrayList<JSensitiveWord>();
		sensitivewordList = (List<JSensitiveWord>)jSensitiveWordService.getAll().getData();
		if(sensitivewordList!=null&&sensitivewordList.size()>0) {
			for(JSensitiveWord sensitiveword:sensitivewordList) {
				Map<String,String> map = new HashMap<String,String>();
				map.put("id", sensitiveword.getId().toString());
				map.put("name", sensitiveword.getName());
				resultList.add(map);
			}
		}
		
		resultMap.put("response", new BaseResponse());
		resultMap.put("sensitivewords", resultList);
		String json = JSONObject.toJSONString(resultMap);
		log.info("response : " + json);
		return json;
	}
	
	
	@ApiOperation(value = "获取所有可设置字段")
	@RequestMapping(value = "/api/Sensitiveword/AllFieldName", method = { RequestMethod.GET })
	public String getSensitivAllFieldName(HttpServletRequest request) {
		JLPLog log = ThreadPoolConfig.getBaseContext();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		BaseResponse baseResponse = new BaseResponse();
		/*// 从前台header中获取token参数
		String authorization = request.getHeader("Authorization") == null ? "6c52445e47389d707807022cbba731cd"
				: request.getHeader("Authorization");
		Long userId = userService.getIdByToken(authorization);
		if (userId == null || userId == 0) {
			baseResponse.setCode("0000");
			baseResponse.setText("用户信息获取失败，请检查请求头");
			resultMap.put("response", baseResponse);
			return JSONObject.toJSONString(resultMap);
		}
		log.info("userId : " + userId);*/
		List<Map<String, String>> resultList = new ArrayList<Map<String, String>>();
		Map<String,String> fildsmap = new HashMap<>();
		List<JTableFieldDict> tablefieldList = (List<JTableFieldDict>)jTablefielddictService.getAll(true).getData();
		if(tablefieldList!=null&&tablefieldList.size()>0) {
			for(JTableFieldDict tablefile:tablefieldList) {
				if(!tablefile.getName().startsWith("JL")) {
					fildsmap.put("Name", tablefile.getName());
					fildsmap.put("title", tablefile.getDictname());
					fildsmap = JLPMapUtil.deleteDuplicate1(fildsmap); 	//去重 
					resultList.add(fildsmap);
				}
			}
			
		}
		
		resultMap.put("response", new BaseResponse());
		resultMap.put("allFields", resultList);
		String json = JSONObject.toJSONString(resultMap);
		log.info("response : " + json);
		return json;
	}
	
	
	@ApiOperation(value = "新增或者修改敏感词")
	@RequestMapping(value = "/api/Sensitiveword", method = { RequestMethod.POST })
	public String updateSensitiveword(Long id,String word,HttpServletRequest request) {
		JLPLog log = ThreadPoolConfig.getBaseContext();
		JSensitiveWord sensitiveword = jSensitiveWordService.getOne(id);
		if(sensitiveword!=null) {
			log.info("修改已经存在的 id : " + id);
			int updateRet = jSensitiveWordService.updateByPrimaryKeySelective(sensitiveword);
			
		}
		else {
			log.info("增加 id : " + id);
			int addRet = jSensitiveWordService.insertSelective(sensitiveword);
		}
		
		return null;
		
	}
	
	@ApiOperation(value = "移除敏感词")
	@RequestMapping(value = "/api/Sensitiveword/delete", method = { RequestMethod.DELETE })
	public String deleteSensitiveword(Long id,String word,HttpServletRequest request) {
		JLPLog log = ThreadPoolConfig.getBaseContext();
		
		JSensitiveWord sensitiveword = jSensitiveWordService.getOne(id);
		if(sensitiveword!=null) {
			int deleteRet = jSensitiveWordService.deleteByPrimaryKey(id);
		}
		else {
			ControllerBaseResponse response = new ControllerBaseResponse();
			response.getResponse().setCode("400");
			response.getResponse().setText("用户名或密码错误");
			return response.toJsonString();
		}
		 
		return null;
		
	}
	   
	
	
	
}
