package com.callan.service.provider.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.callan.service.provider.service.IJLpService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(description = "测试类")
public class MyFirstProviderController {
	
	@Autowired
	private IJLpService service;
	
	@ApiOperation(value = "测试hello" ,  notes="测试hello")
    @RequestMapping(value = "/hello1",method = RequestMethod.GET)
    public String hello1(@RequestParam String name){
        return "hello1," + name;
    }
    
	@ApiOperation(value = "测试oracle加载数据-流式加载" ,  notes="流式加载")
    @RequestMapping(value = "/getOracleDataTest",method = RequestMethod.GET)
    @ResponseBody
    public int getOracleDataTest(){
    	long start = System.currentTimeMillis();
    	String sql = "select * from D_MEDICATIONINFO";
    	List<Map<String,Object>> list = service.queryForSQLStreaming(sql, new Object[] {});
        long end = System.currentTimeMillis();
        System.out.println(end-start + "ms");
    	return list.size();
    }
    
	@ApiOperation(value = "测试oracle加载数据-常规加载" ,  notes="常规加载")
    @RequestMapping(value = "/getOracleDataTest1",method = RequestMethod.GET)
    @ResponseBody
    public int getOracleDataTest1(){
    	long start = System.currentTimeMillis();
    	String sql = "select * from D_MEDICATIONINFO";
    	List<Map<String,Object>> list = service.queryForSQL(sql, new Object[] {});
        long end = System.currentTimeMillis();
        System.out.println(end-start + "ms1");
    	return list.size();
    }
}
