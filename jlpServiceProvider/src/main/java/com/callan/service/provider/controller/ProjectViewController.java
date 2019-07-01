package com.callan.service.provider.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.callan.service.provider.config.JLPConts;
import com.callan.service.provider.config.JLPLog;
import com.callan.service.provider.config.ThreadPoolConfig;
import com.callan.service.provider.pojo.base.BaseResponse;
import com.callan.service.provider.pojo.db.JProjecttags;
import com.callan.service.provider.pojo.db.JTagdicts;
import com.callan.service.provider.pojo.project.ProjectTagDataModel;
import com.callan.service.provider.service.IJLpService;
import com.callan.service.provider.service.IJProjectService;
import com.callan.service.provider.service.IJProjecttagsService;
import com.callan.service.provider.service.IJTagdictService;
import com.callan.service.provider.service.IJUserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(description = " 获取课题视图列表信息")
public class ProjectViewController {
	@Autowired
	private IJUserService userService;
	
	@Autowired
	private IJTagdictService tagdictService;
	
	@Autowired
	private IJProjectService projectService;
	
	@Autowired
	private IJProjecttagsService  projecttagsService;
	
	@Autowired
	private IJLpService jlpService;
	
//	@ApiOperation(value = " 获取课题视图列表信息")
//	@RequestMapping(value = "/api/ProjectView/{Id}", method = { RequestMethod.POST })
//        public String Gets(Long Id, HttpServletRequest request, HttpServletResponse response)
//        {
//		JLPLog log = ThreadPoolConfig.getBaseContext();
//		Map<String, Object> resultMap = new HashMap<String, Object>();
//            if (Id == null || Id < 1)
//            {
//            	BaseResponse baseResponse = new BaseResponse();
//    			response.setStatus(404);
//    			baseResponse.setCode("404");
//    			baseResponse.setText("课题编号错误");
//    			resultMap.put("response", baseResponse);
//    			return JSONObject.toJSONString(resultMap);
//            }
//
//
//            var fields = (from tableclass in orclJlpContext.JTableclassdicts
//                          join view in orclJlpContext.JShowviews on tableclass.Viewid equals view.Id into showviews
//                          from view in showviews
//                          join viewDetaile in orclJlpContext.JShowdetailviews on view.Id equals viewDetaile.Viewid into showViewDetails
//                          from viewDetaile in showViewDetails
//                          
//                          join field in orclJlpContext.JTablefielddicts on viewDetaile.Fieldid equals field.Id into fieldInfos
//                          from field in fieldInfos
//                          join table in orclJlpContext.JTabledicts on field.Tablecode equals table.Code into tables
//                          from table in tables
//                          where tableclass.Activeflag == JLPStaticProperties.ActiveFlag 
//                          	&& view.Activeflag == JLPStaticProperties.ActiveFlag 
//                          	&& field.Showflag == JLPStaticProperties.ActiveFlag 
//                          	&& viewDetaile.Activeflag == JLPStaticProperties.ActiveFlag 
//                          	&& table.Activeflag == JLPStaticProperties.ActiveFlag
//                          orderby view.Id, viewDetaile.Sortno
//                          group new { field, viewDetaile, table } by tableclass into fieldGroups
//                          select new
//                          {
//                              showFields = new
//                              {
//                                  viewId = fieldGroups.Key.Id,
//                                  title = fieldGroups.Key.Name,
//                                  key = fieldGroups.Key.Code,
//                                  children = fieldGroups
//                                  .Select(x => new
//                                  {
//                                      x.viewDetaile.Id,
//                                      title = x.viewDetaile.Fieldtitle,
//                                      key = x.viewDetaile.Id
//                                  })
//                              },
//                              FieldsDefalut = fieldGroups
//                                  .Select(x => new
//                                  {
//                                      key = x.viewDetaile.Id,
//                                      value = x.viewDetaile.Defaultflag == "1"
//                                  })
//                          }).ToList();
//            var defaultFields = from a in fields
//                                from b in a.FieldsDefalut
//                                where b.value
//                                select b.key;
//            
//            resultMap.put("response", new BaseResponse());
//    		resultMap.put("showFields", fields.Select(x => x.showFields));
//    		resultMap.put("defaultFields", defaultFields);
//    		String json = JSONObject.toJSONString(resultMap);
//    		log.info("response : " + json);
//    		return json;
//
//        }
}
