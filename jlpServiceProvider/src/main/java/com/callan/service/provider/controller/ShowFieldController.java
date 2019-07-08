package com.callan.service.provider.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.callan.service.provider.config.JLPConts;
import com.callan.service.provider.config.JLPLog;
import com.callan.service.provider.config.ObjectUtil;
import com.callan.service.provider.config.ThreadPoolConfig;
import com.callan.service.provider.pojo.ControllerBaseResponse;
import com.callan.service.provider.pojo.base.BaseRequest;
import com.callan.service.provider.pojo.base.BaseResponse;
import com.callan.service.provider.pojo.db.JPageviewdetail;
import com.callan.service.provider.pojo.db.JShowDetailView;
import com.callan.service.provider.pojo.db.JShowView;
import com.callan.service.provider.pojo.db.JTableDict;
import com.callan.service.provider.pojo.db.JTableFieldDict;
import com.callan.service.provider.pojo.db.JUser;
import com.callan.service.provider.pojo.db.JUsershowviewfield;
import com.callan.service.provider.pojo.queryField.ShowFieldDetalModel;
import com.callan.service.provider.service.IJLpService;
import com.callan.service.provider.service.IJPageViewDetailService;
import com.callan.service.provider.service.IJPageViewService;
import com.callan.service.provider.service.IJShowDetailViewService;
import com.callan.service.provider.service.IJShowViewService;
import com.callan.service.provider.service.IJTableDictService;
import com.callan.service.provider.service.IJTableFieldDictService;
import com.callan.service.provider.service.IJUserService;
import com.callan.service.provider.service.IJUsershowviewfieldService;

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
	@Autowired
	private IJUsershowviewfieldService usershowviewfieldService;
	@Autowired
	private IJPageViewService pageViewService;
	@Autowired
	private IJPageViewDetailService pageViewDetailService;
	@Autowired
	private IJShowViewService showViewService;
	@Autowired
	private IJShowDetailViewService showDetailViewService;
	@Autowired
	private IJLpService jLpService;

	@ApiOperation(value = "获取所有显示字段")
	@RequestMapping(value = "/api/ShowField/{Id}", method = { RequestMethod.GET })
	public String Get(@PathVariable Long Id, String pagename, HttpServletRequest request, HttpServletResponse resq) {
		JLPLog log = ThreadPoolConfig.getBaseContext();
		Map<String, Object> resultMap = new HashMap<String, Object>();

		if (Id == 0) {
			ControllerBaseResponse response = new ControllerBaseResponse();
			resq.setStatus(404);
			response.getResponse().setCode("404");
			response.getResponse().setText("请输入参数");
			log.error(response.toJsonString());
			return response.toJsonString();
		}
		List<Long> fieldIds = new ArrayList<Long>();
		if (StringUtils.isNotBlank(pagename)) {
//			JUser user = (JUser) request.getSession().getAttribute("user");
			String authorization = request.getHeader("Authorization") == null ? "6c52445e47389d707807022cbba731cd"
					: request.getHeader("Authorization");
			Long userId = userService.getIdByToken(authorization);
			if (userId == null || userId.longValue() == 0) {
				ControllerBaseResponse response = new ControllerBaseResponse();
				resq.setStatus(409);
				response.getResponse().setCode("409");
				response.getResponse().setText("登录信息已过期");
				log.error(response.toJsonString());
				return response.toJsonString();
			}
			List<JUsershowviewfield> juserShowFields = usershowviewfieldService.getByPagenameAndUserId(pagename,
					userId);
			for (JUsershowviewfield usershowviewfield : juserShowFields) {
				fieldIds.add(usershowviewfield.getFieldid());
			}
		}

		List<Map<String, Object>> showFields = new ArrayList<>();
		List<Map<String, Object>> FieldsDefalut = new ArrayList<>();

		List<JPageviewdetail> detailList = pageViewDetailService.getByPageCode("1001");
		for (JPageviewdetail detail : detailList) {
			JShowView showView = showViewService.getByCode(detail.getViewcode());
			List<JShowDetailView> detailViewList = showDetailViewService.getByViewId(showView.getId());
			
			Map<String, Object> showField = new HashMap<>();
			showField.put("viewId", showView.getId());
			showField.put("title", showView.getName());
			showField.put("key", showView.getCode());

			List<Map<String, Object>> childrens = new ArrayList<>();
			for (JShowDetailView showDetailView : detailViewList) {
//        		JTableFieldDict tableFieldDict = jTablefielddictService.getOne(showDetailView.getFieldid());
//        		JTableDict tableDict = jTabledictService.getByCode(tableFieldDict.getTablecode());
				Map<String, Object> children = new HashMap<>();
				children.put("Id", showDetailView.getId());
				children.put("title", showDetailView.getFieldtitle());
				children.put("key", showDetailView.getId());
				childrens.add(children);

				Map<String, Object> defaultMap = new HashMap<>();
				defaultMap.put("key", showDetailView.getId());
				defaultMap.put("value", "1".equals(showDetailView.getDefaultflag()));
				FieldsDefalut.add(defaultMap);
			}
			showField.put("children", childrens);
			showFields.add(showField);
		}
		if (fieldIds.size() == 0) {
			for (Map<String, Object> map : FieldsDefalut) {
				if ((boolean) map.get("value")) {
					fieldIds.add((Long) map.get("key"));
				}
			}
		}
		Collections.sort(showFields,new Comparator<Map<String, Object>>() {

			@Override
			public int compare(Map<String, Object> o1, Map<String, Object> o2) {
				return ObjectUtil.objToLong(o1.get("viewId")).compareTo(ObjectUtil.objToLong(o2.get("viewId")));
			}
		});
		resultMap.put("response", new BaseResponse());
		resultMap.put("showFields", showFields);
		resultMap.put("defaultFields", fieldIds);
		String json = JSONObject.toJSONString(resultMap);
		log.info("response--> " + json);
		return json;
	}

	@ApiOperation(value = "用户默认字段")
	@RequestMapping(value = "/api/ShowField", method = { RequestMethod.POST })
    public String Post(@RequestBody ShowFieldDetalModel showFields, HttpServletRequest request, HttpServletResponse resq)
    {
		JLPLog log = ThreadPoolConfig.getBaseContext();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String authorization = request.getHeader("Authorization") == null ? "6c52445e47389d707807022cbba731cd"
				: request.getHeader("Authorization");
		Long userId = userService.getIdByToken(authorization);
//		JUser user = (JUser) request.getSession().getAttribute("user");
		if (userId == null || userId.longValue() == 0L) {
			ControllerBaseResponse response = new ControllerBaseResponse();
			resq.setStatus(409);
			response.getResponse().setCode("409");
			response.getResponse().setText("登录信息已过期");
			log.error(response.toJsonString());
			return response.toJsonString();
		}
		List<JUsershowviewfield> juserShowFields = usershowviewfieldService
				.getByPagenameAndUserId(showFields.getPageName(), userId);
		
        List<Long> newId = new ArrayList<Long>();
        if (juserShowFields.size() > 0)
        {
        	List<JUsershowviewfield> juserShowFieldsDeletes = new ArrayList<>();
        	for(JUsershowviewfield usershowviewfield : juserShowFields) {
        		if(!showFields.getFieldKeys().contains(usershowviewfield.getFieldid())) {
        			juserShowFieldsDeletes.add(usershowviewfield);
        		}
        	}
            for(JUsershowviewfield usershowviewfield : juserShowFieldsDeletes) {
            	usershowviewfield.setActiveflag(JLPConts.Inactive);
            	usershowviewfieldService.update(usershowviewfield);
            }
            for(Long fieldKey : showFields.getFieldKeys()) {
            	boolean has = false;
            	for(JUsershowviewfield usershowviewfield : juserShowFields) {
            		if(fieldKey.longValue() == usershowviewfield.getFieldid()) {
            			has = true;
            			continue;
            		}
            	}
            	if(!has) {
            		newId.add(fieldKey);
            	}
            }
        }
        else
        {
            newId.addAll(showFields.fieldKeys);
        }
        for (Long item : newId) {
            JUsershowviewfield jUsershowviewfield = new JUsershowviewfield();
            jUsershowviewfield.setActiveflag(JLPConts.ActiveFlag);
            jUsershowviewfield.setFieldid(item);
            jUsershowviewfield.setPagename(showFields.getPageName());
            jUsershowviewfield.setUserid(userId);
            long seqId = jLpService.getNextSeq("J_USERSHOWVIEWFIELD");
            jUsershowviewfield.setId(seqId);
            usershowviewfieldService.save(jUsershowviewfield);
        }
        resultMap.put("response", new BaseResponse());
		String json = JSONObject.toJSONString(resultMap);
		log.info("response--> " + json);
		return json;
    }

}
