package com.callan.service.provider.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.callan.service.provider.config.JLPLog;
import com.callan.service.provider.config.ObjectUtil;
import com.callan.service.provider.config.ThreadPoolConfig;
import com.callan.service.provider.pojo.base.BaseResponse;
import com.callan.service.provider.pojo.db.DPatientglobal;
import com.callan.service.provider.pojo.db.DPatientgloballist;
import com.callan.service.provider.pojo.db.DPatientinfo;
import com.callan.service.provider.pojo.db.JPixconfig;
import com.callan.service.provider.pojo.db.JSystemconfig;
import com.callan.service.provider.pojo.db.JTableFieldDict;
import com.callan.service.provider.pojo.patient.PatientModel;
import com.callan.service.provider.service.IDPatientglobalService;
import com.callan.service.provider.service.IDPatientgloballistService;
import com.callan.service.provider.service.IDPatientinfoService;
import com.callan.service.provider.service.IJLpService;
import com.callan.service.provider.service.IJPixconfigService;
import com.callan.service.provider.service.IJSystemConfigService;
import com.callan.service.provider.service.IJTableFieldDictService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(description = "患者索引信息")
public class PatientController {

	@Autowired
	private IJTableFieldDictService tableFieldDictService;
	@Autowired
	private IJPixconfigService pixconfigService;
	@Autowired
	private IJSystemConfigService systemConfigService;
	@Autowired
	private IJLpService jLpService;
	@Autowired
	private IDPatientglobalService patientglobalService;

	@Autowired
	private IDPatientgloballistService patientgloballistServiceService;
	@Autowired
	private IDPatientinfoService patientinfoService;

	/**
	 * 主索引数据
	 * 
	 * @param patient
	 * @param response
	 * @return
	 */
	@ApiOperation(value = "主索引数据")
	@RequestMapping(value = "/api/Patiant", method = { RequestMethod.GET })
	public String patiant(@RequestBody PatientModel patient, HttpServletResponse response) {
		JLPLog log = ThreadPoolConfig.getBaseContext();
		Map<String, Object> resultMap = new HashMap<String, Object>();

		if (patient == null) {
			BaseResponse baseResponse = new BaseResponse();
			response.setStatus(400);
			baseResponse.setCode("400");
			resultMap.put("response", baseResponse);
			String json = JSONObject.toJSONString(resultMap);
			log.info("response : " + json);
			return json;
		}

		List<JPixconfig> pixconfigs = (List<JPixconfig>) pixconfigService.getAll().getData();
		for (JPixconfig pixconfig : pixconfigs) {
			String fieldName = pixconfig.getFieldname();
			JTableFieldDict tableFieldDict = tableFieldDictService.getByFieldCode(fieldName);
			pixconfig.setName(tableFieldDict.getName());
		}
		Collections.sort(pixconfigs, new Comparator<JPixconfig>() {
			@Override
			public int compare(JPixconfig o1, JPixconfig o2) {
				return o1.getWeightcoefficient().compareTo(o2.getWeightcoefficient());
			}
		});

		if (pixconfigs != null && pixconfigs.size() == 0) {
			BaseResponse baseResponse = new BaseResponse();
			response.setStatus(400);
			baseResponse.setCode("400");
			baseResponse.setText("pixconfigs is null");
			resultMap.put("response", baseResponse);
			String json = JSONObject.toJSONString(resultMap);
			log.info("response : " + json);
			return json;
		}

		JSONObject patientJsonObj = JSONObject.parseObject(JSONObject.toJSONString(patient));
		Set<String> patientJsonObjKeySet = patientJsonObj.keySet();
		Map<String, Object> keyValues = new HashMap<String, Object>();
		for (String key : patientJsonObjKeySet) {
			Object value = patientJsonObj.get(key);
			if (value != null) {
				keyValues.put(key.toUpperCase(), patientJsonObj.get(key));
			}
		}
		if (keyValues.size() == 0) {
			BaseResponse baseResponse = new BaseResponse();
			response.setStatus(400);
			baseResponse.setCode("400");
			baseResponse.setText("keyValues is null");
			resultMap.put("response", baseResponse);
			String json = JSONObject.toJSONString(resultMap);
			log.info("response : " + json);
			return json;
		}

		int sysPIXValue = 80;
		JSystemconfig systemconfig = (JSystemconfig) systemConfigService.getByClassTypeAndKeyName("system", "PIXValue")
				.getData();
		if (systemconfig != null && StringUtils.isNotBlank(systemconfig.getKeyvalue())) {
			sysPIXValue = ObjectUtil.objToInt(systemconfig.getKeyvalue());
		}
		int sum = 0;
		Set<JPixconfig> fieldInfos = new HashSet<JPixconfig>();
		Set<String> fieldInfosStr = new HashSet<String>();
		for (JPixconfig pixconfig : pixconfigs) {
			if (keyValues.containsKey(pixconfig.getName().toUpperCase())) {
				fieldInfos.add(pixconfig);
				sum += pixconfig.getWeightcoefficient();
			}
		}
		if (sum < sysPIXValue) {
			BaseResponse baseResponse = new BaseResponse();
			response.setStatus(400);
			baseResponse.setCode("400");
			baseResponse.setText("sum < sysPIXValue");
			resultMap.put("response", baseResponse);
			String json = JSONObject.toJSONString(resultMap);
			log.info("response : " + json);
			return json;
		}

		String strWhere = "";
		int status = 0;
		if (fieldInfosStr.contains("PATIENTID")) {
			strWhere = " PATIENTID='" + keyValues.get("PATIENTID") + "' ";
			status = 1;
		}

		if (fieldInfosStr.contains("IDCARD")) {
			if (strWhere.length() > 0) {
				strWhere += " OR ";
			}

			strWhere += " IDCARD='" + keyValues.get("IDCARD") + "' ";
			status = 1;
		}
		Date BirthDay = null;
		if (fieldInfosStr.contains("NAME") && fieldInfos.contains("BIRTHDAY")) {
			if (strWhere.length() > 0) {
				strWhere += " OR ";
			}
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			BirthDay = ObjectUtil.objToDate(keyValues.get("BIRTHDAY"));
			strWhere += " (NAME='" + keyValues.get("NAME") + "' AND BIRTHDAY=TO_DATE('" + df.format(BirthDay)
					+ "','YYYY-MM-DD HH24:MI:SS')) ";
			if (status == 0) {
				status = 2;
			}
		}
		DPatientinfo patientInfoExpre = new DPatientinfo();
		boolean IsNewExpre = false;
		for (String key : keyValues.keySet()) {
			switch (key) {
			case "PATIENTID":
				patientInfoExpre.setPatientid(ObjectUtil.objToString(keyValues.get(key)));
				IsNewExpre = true;
				break;
			case "NAME":
				patientInfoExpre.setName(ObjectUtil.objToString(keyValues.get(key)));
				IsNewExpre = true;
				break;
			case "BIRTHDAY":
				patientInfoExpre.setBirthday(ObjectUtil.objToDate(keyValues.get(key)));
				IsNewExpre = true;
				break;
			case "IDCARD":
				patientInfoExpre.setIdcard(ObjectUtil.objToString(keyValues.get(key)));
				IsNewExpre = true;
				break;
			default:
				break;
			}
		}
		if (!IsNewExpre) {
//            patientInfoExpre = patientInfoExpre.And(x => false);
		}
		String sql = "select * from D_PATIENTINFO where  " + strWhere;
		List<Map<String, Object>> DpatientList = jLpService.queryForSQL(sql, new Object[] {});
		DPatientglobal patientglobal = null;

		if (DpatientList != null && DpatientList.size() > 0) {
			long patientGlobalId = DpatientList.get(0).get("PATIENTGLOBALID") == null ? -1
					: Long.parseLong(DpatientList.get(0).get("PATIENTGLOBALID") + "");
			patientglobal = patientglobalService.getOne(patientGlobalId);
		}

		if (patientglobal == null) {
			patientglobal = new DPatientglobal();
//            patientglobal = StaticProperties.Patch(patientglobal, patient);
			patientglobal.setJlactiveflag("1");
			patientglobal.setJlcreatedate(new Date());
			long seqId = jLpService.getNextSeq("D_PATIENTGLOBAL");
			patientglobal.setId(seqId);

			if (DpatientList.size() > 0) {
				for (Map map : DpatientList) {
					patientinfoService.update(ObjectUtil.objToLong(map.get("id")), patientglobal.getId());
				}
			}
			patientglobalService.save(patientglobal);
		} else {
//            patientglobal = StaticProperties.Patch(patientglobal, patient);
			patientglobalService.update(patientglobal);
		}
		DPatientinfo patientinfo = patientinfoService.getEntity(patientInfoExpre);
		if (patientinfo == null) {
			patientinfo = new DPatientinfo();
//            patientinfo = StaticProperties.Patch(patientinfo, patient, true);
			patientglobal.setJlactiveflag("1");
			patientglobal.setJlcreatedate(new Date());
			long seqId = jLpService.getNextSeq("D_PATIENTGLOBAL");
			patientglobal.setId(seqId);

			DPatientgloballist patientgloballist = new DPatientgloballist();
//            patientgloballist = StaticProperties.Patch(patientgloballist, patient);
			patientgloballist.setJlactiveflag("1");
			patientgloballist.setJlcreatedate(new Date());
			long seqId1 = jLpService.getNextSeq("D_PATIENTGLOBALLIST");
			patientgloballist.setId(seqId1);
			patientgloballist.setPatientglobalid(patientglobal.getId());
			patientinfoService.save(patientinfo);
			patientgloballistServiceService.save(patientgloballist);
		} else {
//            patientinfo = StaticProperties.Patch(patientinfo, patient, true);
			patientglobalService.update(patientglobal);
		}

		resultMap.put("response", new BaseResponse());
		String json = JSONObject.toJSONString(resultMap);
		log.info("response : " + json);
		return json;
	}
}
