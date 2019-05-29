//package com.callan.service.provider.controller;
//
//import javax.servlet.http.HttpServletRequest;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.callan.service.provider.service.IJUserService;
//
//import io.swagger.annotations.ApiOperation;
//
//@RestController
//public class AdvancedQueryRecordController {
//	@Autowired
//	private IJUserService jUserService;
//
//	@ApiOperation(value = "病例检索", notes = "获取纳排条件列表")
//	@RequestMapping(value = "/api/AdvancedQueryRecord", method = { RequestMethod.POST })
//	 public String gets( HttpServletRequest request) {
//		// 从前台header中获取token参数
//		String authorization = request.getHeader("Authorization") == null ? "" : request.getHeader("Authorization");
//		Long userId = jUserService.getIdByToken(authorization);
//		
//         var jadvanceQrs = orclJlpContext.JAdvancedqrs.Where(x => x.Activeflag == JLPStaticProperties.ActiveFlag && x.Userid == userId).OrderByDescending(x => x.Createdate).ToList();
//         var jadvanceQrList = jadvanceQrs.Select(x => new
//         {
//             id = x.Id,
//             name = x.Aqname,
//             createdate = x.Createdate.ToStringEx()
//         });
//         DictionaryObj ret = new DictionaryObj();
//         ret.Add("response", ResponseModel.Ok());
//         ret.Add("queryRecords", jadvanceQrList);
//         return Ok(ret);
//     }
//}
