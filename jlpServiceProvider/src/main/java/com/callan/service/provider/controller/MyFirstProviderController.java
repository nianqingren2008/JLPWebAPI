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

@RestController
public class MyFirstProviderController {
	
	@Autowired
	private IJLpService service;
	
    @RequestMapping(value = "/hello",method = RequestMethod.GET)
    public String hello(@RequestParam String name){
        return "hello," + name;
    }
    
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
