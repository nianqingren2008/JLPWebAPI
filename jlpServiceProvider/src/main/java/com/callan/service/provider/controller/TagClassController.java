//package com.callan.service.provider.controller;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import javax.servlet.http.HttpServletRequest;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.alibaba.fastjson.JSONObject;
//import com.callan.service.provider.config.JLPLog;
//import com.callan.service.provider.config.ThreadPoolConfig;
//import com.callan.service.provider.pojo.base.BaseResponse;
//import com.callan.service.provider.pojo.tableclassdict.JTableclassdict;
//import com.callan.service.provider.service.IJTableclassdictService;
//import com.callan.service.provider.service.IJUserService;
//
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
// 
//@RestController
//@Api(description = "标签列表")
// 
//public class TagClassController {
//
//@Autowired
//private IJUserService userService;
//@Autowired
//private IJTableclassdictService tableclassdictService;
//
//@ApiOperation(value = "获取标签所属分类列表")
//@RequestMapping(value = "/api/TagClass", method = { RequestMethod.GET })
//public String  getTagList(HttpServletRequest request){
//	JLPLog log = ThreadPoolConfig.getBaseContext();
//	Map<String, Object> resultMap = new HashMap<String, Object>();
//	// 从前台header中获取token参数
//	String authorization = request.getHeader("Authorization") == null ? "6c52445e47389d707807022cbba731cd"
//			: request.getHeader("Authorization");
//	Long userId = userService.getIdByToken(authorization);
//	if (userId == null || userId == 0) {
//		BaseResponse baseResponse = new BaseResponse();
//		baseResponse.setCode("0000");
//		baseResponse.setText("用户信息获取失败，请检查请求头");
//		resultMap.put("response", baseResponse);
//		return JSONObject.toJSONString(resultMap);
//	}
//	log.info("userId : " + userId);
//	 List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
//	 List<JTableclassdict> list = tableclassdictService.getAll();
//	 if(list!=null&&list.size()>0) {
//		for (JTableclassdict jtableclassdict : list) {
//			Map<String, Object> map = new HashMap<String, Object>();
//			map.put("id", jtableclassdict.getId()+"");
//			map.put("title", jtableclassdict.getName());
//			resultList.add(map);
//		}
//	 }
//		resultMap.put("response", new BaseResponse());
//		resultMap.put("TagClasses", resultList);
//		String json = JSONObject.toJSONString(resultMap);
//		log.info("response : " + json);
//		return json;
// 
//   }
//}
