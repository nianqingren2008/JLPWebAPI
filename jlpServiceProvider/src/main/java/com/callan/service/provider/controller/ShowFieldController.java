package com.callan.service.provider.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.callan.service.provider.config.JLPLog;
import com.callan.service.provider.config.ThreadPoolConfig;
import com.callan.service.provider.pojo.base.BaseResponse;
import com.callan.service.provider.service.IJTableDictService;
import com.callan.service.provider.service.IJTableFieldDictService;
import com.callan.service.provider.service.IJUserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(description = "显示字段")
public class ShowFieldController {
	@Autowired
	private IJUserService userService;
	@Autowired
	private IJTableDictService jTabledictService;
	@Autowired
	private IJTableFieldDictService jTablefielddictService;
	@ApiOperation(value = "获取所有显示字段")
	@RequestMapping(value = "/api/ShowField", method = { RequestMethod.GET })
	public String getAllShowFieldList(@RequestBody int id,HttpServletRequest request) {
		JLPLog log = ThreadPoolConfig.getBaseContext();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		BaseResponse baseResponse = new BaseResponse();
		
		
		
		
		return null;
	}

}
