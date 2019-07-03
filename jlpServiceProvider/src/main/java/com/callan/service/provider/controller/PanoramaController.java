package com.callan.service.provider.controller;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.callan.service.provider.config.JLPConts;
import com.callan.service.provider.config.JLPLog;
import com.callan.service.provider.config.ObjectUtil;
import com.callan.service.provider.config.ThreadPoolConfig;
import com.callan.service.provider.config.XmlUtil;
import com.callan.service.provider.pojo.advanceQueryBase.ColunmsModel;
import com.callan.service.provider.pojo.base.BaseResponse;
import com.callan.service.provider.pojo.db.DDischargesummary;
import com.callan.service.provider.pojo.db.DLabmasterinfo;
import com.callan.service.provider.pojo.db.DMedicalrecords;
import com.callan.service.provider.pojo.db.DPatientglobal;
import com.callan.service.provider.pojo.db.DPatientvisit;
import com.callan.service.provider.pojo.db.JPageviewdetail;
import com.callan.service.provider.pojo.db.JRight;
import com.callan.service.provider.pojo.db.JRoleRight;
import com.callan.service.provider.pojo.db.JSensitiveWord;
import com.callan.service.provider.pojo.db.JShowDetailView;
import com.callan.service.provider.pojo.db.JShowView;
import com.callan.service.provider.pojo.db.JTableFieldDict;
import com.callan.service.provider.pojo.db.JUser;
import com.callan.service.provider.pojo.panorama.BasePatientInfoModel;
import com.callan.service.provider.pojo.panorama.RecordPatients;
import com.callan.service.provider.service.IDDischargesummaryService;
import com.callan.service.provider.service.IDLabmasterinfoService;
import com.callan.service.provider.service.IDMedicalrecordsService;
import com.callan.service.provider.service.IDPatientglobalService;
import com.callan.service.provider.service.IDPatientvisitService;
import com.callan.service.provider.service.IJFiletypeService;
import com.callan.service.provider.service.IJLpService;
import com.callan.service.provider.service.IJPageViewDetailService;
import com.callan.service.provider.service.IJPageViewService;
import com.callan.service.provider.service.IJRoleRightService;
import com.callan.service.provider.service.IJSensitiveWordService;
import com.callan.service.provider.service.IJShowDetailViewService;
import com.callan.service.provider.service.IJShowViewService;
import com.callan.service.provider.service.IJTableDictService;
import com.callan.service.provider.service.IJTableFieldDictService;
import com.callan.service.provider.service.IJUserService;

import ch.qos.logback.core.joran.spi.XMLUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(description = "全景视图管理")
public class PanoramaController {
	@Autowired
	private IJFiletypeService filetypeService;
	@Autowired
	private IDPatientglobalService patientglobalService;

	@Autowired
	private IDPatientvisitService patientvisitService;

	@Autowired
	private IJUserService jUserService;

	@Autowired
	private IJRoleRightService roleRightService;

	@Autowired
	private IJSensitiveWordService jSensitiveWordService;

	@Autowired
	private IJPageViewService pageViewService;

	@Autowired
	private IJPageViewDetailService pageViewDetailService;

	@Autowired
	private IJShowViewService showViewService;

	@Autowired
	private IJShowDetailViewService showDetailViewService;

	@Autowired
	private IJTableFieldDictService tableFieldDictService;
	
	@Autowired
	private IJTableDictService tableDictService;

	@Autowired
	private IJLpService JLpService;

	@Autowired
	private IDMedicalrecordsService medicalrecordsService;

	@Autowired
	private IDDischargesummaryService dischargesummaryService;

	@Autowired
	private IDLabmasterinfoService labmasterinfoService;

	@ApiOperation(value = "获取全景视图患者住院列表")
	@RequestMapping(value = "/api/Panorama/{Id}", method = { RequestMethod.GET })
	public String getPanoramaDetail(@PathVariable Long Id, Integer pageSize, Integer pageNum
			, HttpServletRequest request,HttpServletResponse response) {
		JLPLog log = ThreadPoolConfig.getBaseContext();
		Map<String, Object> resultMap = new HashMap<String, Object>();

		boolean hasPage = true;
		if (pageNum == null || pageSize == null || pageNum <= 0 || pageSize <= 0) {
			hasPage = false;
		}

		DPatientglobal patientglobal = patientglobalService.getOne(Id);
		if (patientglobal == null) {
			BaseResponse baseResponse = new BaseResponse();
			response.setStatus(400);
			baseResponse.setCode("400");
			baseResponse.setText("未找到该患者信息！");
			resultMap.put("response", baseResponse);
			return JSONObject.toJSONString(resultMap);
		}
		List<DPatientvisit> vistList = patientvisitService.getByPatientGlobalId(ObjectUtil.objToString(patientglobal.getId()));
		Collections.sort(vistList, new Comparator<DPatientvisit>() {

			@Override
			public int compare(DPatientvisit o1, DPatientvisit o2) {
				return o1.getRydatetime().compareTo(o2.getRydatetime());
			}
		});
		int patientVisitCount = vistList.size();
		List<DPatientvisit> patientvisitList = null;
		if (hasPage) {
			patientvisitList = vistList.subList((pageNum - 1) * pageSize, pageSize);
		}else {
			patientvisitList = vistList;
		}
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		BasePatientInfoModel basePatientInfo = new BasePatientInfoModel();
		basePatientInfo.setPatientID(patientglobal.getId() + "");
		basePatientInfo.setPatientName(patientglobal.getName());
		String bir = "";
		if (patientglobal.getBirthday() == null) {
			bir = "暂无";
		} else {
			bir = df.format(patientglobal.getBirthday());
		}
		basePatientInfo.setBirthday(bir);
		basePatientInfo.setSex(patientglobal.getSex());
		basePatientInfo.setTotalInpatients(patientVisitCount);
		basePatientInfo.setTotalRecords(patientVisitCount);

		List<RecordPatients> recordList = new ArrayList<RecordPatients>();
		if(patientvisitList != null) {
		for (DPatientvisit visit : patientvisitList) {
			RecordPatients recordPatients = new RecordPatients();
			recordPatients.setIndeptNum(visit.getId().intValue());
			recordPatients.setType("住院");
			String date = "";
			if (visit.getRydatetime() == null) {
				date = "暂无";
			} else {
				date = df.format(visit.getRydatetime());
			}
			recordPatients.setRecordDate(date);

			String time = "";
			if (visit.getCydatetime() == null) {
				time = "暂无";
			} else {
				time = df.format(visit.getCydatetime());
			}
			recordPatients.setRecordTime(time);
			recordPatients.setInDept(visit.getRydept());
			recordPatients.setOutDept(visit.getCydept());
			recordPatients.setInDeptTotoalData((visit.getRydatetime() != null && visit.getCydatetime() != null)
					? (int) (visit.getCydatetime().getTime() - visit.getRydatetime().getTime() / 3600000 * 24)
					: 0);
			recordPatients.setDialognose(visit.getDiagnosecontent());
			recordList.add(recordPatients);
		}
		}
		// 从前台header中获取token参数
//		String authorization = request.getHeader("Authorization") == null ? "" : request.getHeader("Authorization");
//		Long userRole = jUserService.getUserRoleByToken(authorization);
//		
		JUser user = (JUser) request.getSession().getAttribute("user"); //jUserService.getUserByToken(authorization);
		
		if (user != null && user.getUserrole() != 0L) {
			List<JRoleRight> roleRightList = roleRightService.getByRoleId(user.getUserrole());
			if (roleRightList != null && roleRightList.size() > 0) {
				JRight jRight = roleRightList.get(0).getjRight();
				if (jRight == null || jRight.getId() != 4L) {
					// 获取敏感字段配置
					Map<String, JSensitiveWord> sensitiveWordMap = (Map<String, JSensitiveWord>) jSensitiveWordService
							.getAll4Name().getData();
					if (!sensitiveWordMap.isEmpty()) {
						// 将敏感字段设置为 ***
						basePatientInfo = sensitiveWord(basePatientInfo, sensitiveWordMap);
					}
				}
			}

		}

		resultMap.put("response", new BaseResponse());
		resultMap.put("baseInfo", basePatientInfo);
		resultMap.put("recordPatients", recordList);

		String json = JSONObject.toJSONString(resultMap);
		log.info("response-->" + json);
		return json;
	}

	private BasePatientInfoModel sensitiveWord(BasePatientInfoModel retData,
			Map<String, JSensitiveWord> sensitiveWordMap) {
		if (retData == null) {
			return retData;
		}
		if (sensitiveWordMap == null || sensitiveWordMap.isEmpty()) {
			return retData;
		}
		try {
			Field[] fs = retData.getClass().getDeclaredFields();
			for (int i = 0; i < fs.length; i++) {
				Field f = fs[i];
				f.setAccessible(true); // 设置些属性是可以访问的
				Object value = f.get(retData);// 得到此属性的值
				String type = f.getType().toString();// 得到此属性的类型
				if (type.endsWith("String")) {
					f.set(retData, toSensitivewordEx(ObjectUtil.objToString(value))); // 给属性设值
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return retData;
	}

	private String toSensitivewordEx(String str) {
		if (StringUtils.isBlank(str)) {
			return str;
		}
		String ret = str;
		switch (str.length()) {
		case 1:
			ret = "*";
			break;
		case 2:
			ret = str.substring(0, 1) + "*";
			break;
		case 3:
			ret = str.substring(0, 1) + "**";
			break;
		default:
			int wordCount = str.length() / 4;
			int sensitivityCount = str.length() - (str.length() / 2);
			ret = padRight(str.substring(0, wordCount), sensitivityCount, '*')
					+ str.substring(str.length() - wordCount, str.length());
			break;
		}
		return ret;
	}

	private String padRight(String src, int len, char ch) {
		int diff = len - src.length();
		if (diff <= 0) {
			return src;
		}

		char[] charr = new char[len];
		System.arraycopy(src.toCharArray(), 0, charr, 0, src.length());
		for (int i = src.length(); i < len; i++) {
			charr[i] = ch;
		}
		return new String(charr);
	}

	@ApiOperation(value = "全景视图信息")
	@RequestMapping(value = "/api/Panorama/{PanoramaType}/{Id}", method = { RequestMethod.GET })
	public String panoramaTypeId(@PathVariable String PanoramaType,@PathVariable  Long Id
			, Integer pageSize, Integer pageNum, HttpServletRequest request,HttpServletResponse response) {

		JLPLog log = ThreadPoolConfig.getBaseContext();
		Map<String, Object> resultMap = new HashMap<String, Object>();

		List<JPageviewdetail> detailList = pageViewDetailService.getByPageCode("1002");
		JShowView panoramashowview = null;
		if (detailList != null && detailList.size() > 0) {
			for(JPageviewdetail JPageviewdetail : detailList) {
				JShowView showView = showViewService.getByCode(JPageviewdetail.getViewcode());
				if(showView.getAccesscode().equals(PanoramaType)) {
					panoramashowview = showView;
					break;
				}
			}
		}

		if (detailList.size() == 0 || (panoramashowview == null)) {
			BaseResponse baseResponse = new BaseResponse();
			response.setStatus(400);
			baseResponse.setCode("400");
			baseResponse.setText("请求资源错误");
			resultMap.put("response", baseResponse);
			return JSONObject.toJSONString(resultMap);
		}

		boolean hasPage = true;
		if (pageNum == null || pageSize == null || pageNum == 0 || pageSize == 0) {
			hasPage = false;
		}
		DPatientvisit dPatientvisit = patientvisitService.getOne(Id);
		if (dPatientvisit == null) {
			BaseResponse baseResponse = new BaseResponse();
			response.setStatus(400);
			baseResponse.setCode("400");
			baseResponse.setText("未找到住院信息");
			resultMap.put("response", baseResponse);
			return JSONObject.toJSONString(resultMap);
		}
		if (StringUtils.isBlank(dPatientvisit.getPatientid()) || StringUtils.isBlank(dPatientvisit.getVisitid())) {
			BaseResponse baseResponse = new BaseResponse();
			response.setStatus(400);
			baseResponse.setCode("400");
			baseResponse.setText("该患者住院信息错误");
			resultMap.put("response", baseResponse);
			return JSONObject.toJSONString(resultMap);
		}

		Long panoramashowviewId = panoramashowview.getId();
		JShowView showView = showViewService.getOne(panoramashowviewId);
		List<JShowDetailView> detailViewList = showDetailViewService.getByViewId(showView.getId());
		Set<String> tableNameSet = new HashSet<String>();

		TreeSet<JTableFieldDict> fieldDictSet = new TreeSet<JTableFieldDict>(new Comparator<JTableFieldDict>() {
			@Override
			public int compare(JTableFieldDict o1, JTableFieldDict o2) {
				return o1.getSortno().compareTo(o2.getSortno());
			}
		});

		for (JShowDetailView showDetailView : detailViewList) {
			JTableFieldDict tableFieldDict = tableFieldDictService.getOne(showDetailView.getFieldid());
			tableNameSet.add(tableDictService.getByCode(tableFieldDict.getTablecode(), true).getName());
			fieldDictSet.add(tableFieldDict);
		}
		if (fieldDictSet.size() == 0) {
			BaseResponse baseResponse = new BaseResponse();
			response.setStatus(400);
			baseResponse.setCode("400");
			baseResponse.setText("未配置全景信息视图" + PanoramaType + "，请联系技术人员！");
			resultMap.put("response", baseResponse);
			return JSONObject.toJSONString(resultMap);
		}

		if (tableNameSet.size() > 1) {
			BaseResponse baseResponse = new BaseResponse();
			response.setStatus(400);
			baseResponse.setCode("400");
			baseResponse.setText("全景信息视图" + PanoramaType + "}暂时不支持多表联合查看，请联系技术人员！");
			resultMap.put("response", baseResponse);
			return JSONObject.toJSONString(resultMap);
		}

		List<ColunmsModel> columns = new ArrayList<ColunmsModel>();
		Set<String> fieldSet = new HashSet<String>();
		for (JTableFieldDict jTableFieldDict : fieldDictSet) {
			ColunmsModel colunmsModel = new ColunmsModel();
			colunmsModel.setDataIndex(jTableFieldDict.getName().toLowerCase());
			colunmsModel.setKey(jTableFieldDict.getName().toLowerCase());
			JShowDetailView showDetailView = showDetailViewService.getByFieldId(jTableFieldDict.getId());
			colunmsModel.setTitle(showDetailView.getFieldtitle());
			colunmsModel.setIsLongStr("clob".equals(jTableFieldDict.getType()));
			colunmsModel.setIsSearched(JLPConts.ActiveFlag.equals(jTableFieldDict.getQueryflag()));
			columns.add(colunmsModel);
			fieldSet.add(jTableFieldDict.getName());
		}

		String patientId = dPatientvisit.getPatientid();
		String visitId = dPatientvisit.getVisitid();

		fieldSet.add("Id as \"_key\"");
		String fieldStrs = fieldSet.toString().substring(1, fieldSet.toString().length() - 1).toLowerCase();;
		String tableName = tableNameSet.iterator().next();
		int totals = 0;

		String Sql = "select distinct " + fieldStrs + " from " + tableName + " where ";
		if (PanoramaType.equals("basepatient")) {
			Sql += " Id=  '" + dPatientvisit.getPatientglobalid() + "'";
		} else {
			Sql += " patientId='" + patientId + "' and visitId='" + visitId + "'";
		}
		if (hasPage) {
			String sqlCount = " select count(1) count from (" + Sql + ") countsql";
			totals = JLpService.queryForCount(sqlCount);
			Sql = "SELECT *" + "  FROM (SELECT tt.*, ROWNUM AS rowno" + "          FROM (  " + Sql + ") tt"
					+ "         WHERE ROWNUM <= " + pageNum * pageSize + ") table_alias" + " WHERE table_alias.rowno > "
					+ (pageNum - 1) * pageSize;
		}
		log.info("Sql --> " + Sql);
		List<Map<String, Object>> contentData = JLpService.queryForSQL(Sql, new Object[] {});
		if (totals == 0) {
			totals = contentData.size();
		}

		// 从前台header中获取token参数
//		String authorization = request.getHeader("Authorization") == null ? "" : request.getHeader("Authorization");
//
//		Long userRole = jUserService.getUserRoleByToken(authorization);
		JUser user = (JUser) request.getSession().getAttribute("user"); //jUserService.getUserByToken(authorization);
		
		if (user != null && user.getUserrole() != 0L) {
			List<JRoleRight> roleRightList = roleRightService.getByRoleId(user.getUserrole());
			if (roleRightList != null && roleRightList.size() > 0) {
				JRight jRight = roleRightList.get(0).getjRight();
				if (jRight == null || jRight.getId() != 4L) {
					// 获取敏感字段配置
					Map<String, JSensitiveWord> sensitiveWordMap = (Map<String, JSensitiveWord>) jSensitiveWordService
							.getAll4Name().getData();
					if (!sensitiveWordMap.isEmpty()) {
						// 将敏感字段设置为 ***
						contentData = sensitiveWord(contentData, sensitiveWordMap);
					}
				}
			}
		}

		BaseResponse baseResponse = new BaseResponse();
		resultMap.put("response", baseResponse);
		resultMap.put("totals", totals);
		resultMap.put("columns", columns);
		resultMap.put("content", contentData);
		String json = JSONObject.toJSONString(resultMap);
		log.info("response--> " + json);
		return json;
	}

	private List<Map<String, Object>> sensitiveWord(List<Map<String, Object>> retData,
			Map<String, JSensitiveWord> sensitiveWordMap) {
		if (retData == null || retData.size() == 0) {
			return retData;
		}
		if (sensitiveWordMap == null || sensitiveWordMap.isEmpty()) {
			return retData;
		}

		for (Map<String, Object> data : retData) {
			Set<String> keySet = data.keySet();
			for (String key : keySet) {
				if (sensitiveWordMap.containsKey(key)) {
					String value = data.get(key) + "";
					data.put(key, toSensitivewordEx(value));
				}
			}
		}
		return retData;
	}

//
	@ApiOperation(value = "检验结果信息获取")
	@RequestMapping(value = "/api/Panorama/LabResult/{Id}", method = { RequestMethod.GET })
	public String LabResult(@PathVariable Long Id, Integer pageSize, Integer pageNum,HttpServletResponse response) {

		JLPLog log = ThreadPoolConfig.getBaseContext();
		Map<String, Object> resultMap = new HashMap<String, Object>();

		List<JPageviewdetail> detailList = pageViewDetailService.getByPageCode("1002");
		JShowView panoramashowview = null;
		if (detailList != null && detailList.size() > 0) {
			for(JPageviewdetail JPageviewdetail : detailList) {
				JShowView showView = showViewService.getByCode(JPageviewdetail.getViewcode());
				if(showView.getAccesscode().equals("labresult")) {
					panoramashowview = showView;
					break;
				}
			}
		}

		if (detailList.size() == 0 || (panoramashowview == null)
				|| !"labresult".equals(panoramashowview.getAccesscode())) {
			BaseResponse baseResponse = new BaseResponse();
			response.setStatus(400);
			baseResponse.setCode("400");
			baseResponse.setText("请求资源错误");
			resultMap.put("response", baseResponse);
			return JSONObject.toJSONString(resultMap);
		}

		boolean hasPage = true;
		if (pageNum == null || pageSize == null || pageNum == 0 || pageSize == 0) {
			hasPage = false;
		}

		DLabmasterinfo labmasterinfo = labmasterinfoService.getOne(Id);
		if (labmasterinfo == null) {
			BaseResponse baseResponse = new BaseResponse();
			response.setStatus(400);
			baseResponse.setCode("400");
			baseResponse.setText("未找到该检验信息！");
			resultMap.put("response", baseResponse);
			return JSONObject.toJSONString(resultMap);
		}
		if (StringUtils.isBlank(labmasterinfo.getApplyno())) {
			BaseResponse baseResponse = new BaseResponse();
			response.setStatus(400);
			baseResponse.setCode("400");
			baseResponse.setText("该检验信息数据缺失！");
			resultMap.put("response", baseResponse);
			return JSONObject.toJSONString(resultMap);
		}

		Long panoramashowviewId = panoramashowview.getId();
		JShowView showView = showViewService.getOne(panoramashowviewId);
		List<JShowDetailView> detailViewList = showDetailViewService.getByViewId(showView.getId());
		Set<String> tableNameSet = new HashSet<String>();

		TreeSet<JTableFieldDict> fieldDictSet = new TreeSet<JTableFieldDict>(new Comparator<JTableFieldDict>() {
			@Override
			public int compare(JTableFieldDict o1, JTableFieldDict o2) {
				return o1.getSortno().compareTo(o2.getSortno());
			}
		});

		for (JShowDetailView showDetailView : detailViewList) {
			JTableFieldDict tableFieldDict = tableFieldDictService.getOne(showDetailView.getFieldid());
			tableNameSet.add(tableDictService.getByCode(tableFieldDict.getTablecode(), true).getName());
			fieldDictSet.add(tableFieldDict);
		}
		if (fieldDictSet.size() == 0) {
			BaseResponse baseResponse = new BaseResponse();
			response.setStatus(400);
			baseResponse.setCode("400");
			baseResponse.setText("未配置全景信息视图labresult，请联系技术人员！");
			resultMap.put("response", baseResponse);
			return JSONObject.toJSONString(resultMap);
		}

		if (tableNameSet.size() > 1) {
			BaseResponse baseResponse = new BaseResponse();
			response.setStatus(400);
			baseResponse.setCode("400");
			baseResponse.setText("全景信息视图labresult暂时不支持多表联合查看，请联系技术人员！");
			resultMap.put("response", baseResponse);
			return JSONObject.toJSONString(resultMap);
		}

		List<ColunmsModel> columns = new ArrayList<ColunmsModel>();
		Set<String> fieldSet = new HashSet<String>();
		for (JTableFieldDict jTableFieldDict : fieldDictSet) {
			ColunmsModel colunmsModel = new ColunmsModel();
			colunmsModel.setDataIndex(jTableFieldDict.getName().toLowerCase());
			colunmsModel.setKey(jTableFieldDict.getName().toLowerCase());
			JShowDetailView showDetailView = showDetailViewService.getByFieldId(jTableFieldDict.getId());
			colunmsModel.setTitle(showDetailView.getFieldtitle());
			colunmsModel.setIsLongStr("clob".equals(jTableFieldDict.getType()));
			colunmsModel.setIsSearched(JLPConts.ActiveFlag.equals(jTableFieldDict.getQueryflag()));
			columns.add(colunmsModel);
			fieldSet.add(jTableFieldDict.getName());
		}

		fieldSet.add("Id as \"_key\"");
		String fieldStrs = fieldSet.toString().substring(1, fieldSet.toString().length() - 1).toLowerCase();
		String tableName = tableNameSet.iterator().next();
		int totals = 0;
		String Sql = "select distinct " + fieldStrs + " from " + tableName + " where applyNo='"
				+ labmasterinfo.getApplyno() + "'";
		if (hasPage) {
			String sqlCount = " select count(1) count from (" + Sql + ") countsql";
			totals = JLpService.queryForCount(sqlCount);
			Sql = "SELECT *" + "  FROM (SELECT tt.*, ROWNUM AS rowno" + "          FROM (  " + Sql + ") tt"
					+ "         WHERE ROWNUM <= " + pageNum * pageSize + ") table_alias" + " WHERE table_alias.rowno > "
					+ (pageNum - 1) * pageSize;
		}
		List<Map<String, Object>> contentData = JLpService.queryForSQL(Sql, new Object[] {});
		if (totals == 0) {
			totals = contentData.size();
		}
		BaseResponse baseResponse = new BaseResponse();
		resultMap.put("response", baseResponse);
		resultMap.put("totals", totals);
		resultMap.put("columns", columns);
		resultMap.put("content", contentData);
		String json = JSONObject.toJSONString(resultMap);
		log.info("response--> " + json);
		return json;
	}

	@ApiOperation(value = "患者住院信息")
	@RequestMapping(value = "/api/Panorama/Inpat/{Id}", method = { RequestMethod.GET })
	public String Inpat(@PathVariable Long Id,HttpServletResponse response) {

		JLPLog log = ThreadPoolConfig.getBaseContext();
		Map<String, Object> resultMap = new HashMap<String, Object>();

		DPatientvisit dPatientvisit = patientvisitService.getOne(Id);
		if (dPatientvisit == null) {
			BaseResponse baseResponse = new BaseResponse();
			response.setStatus(400);
			baseResponse.setCode("400");
			baseResponse.setText("未找到该住院信息！");
			resultMap.put("response", baseResponse);
			return JSONObject.toJSONString(resultMap);
		}
		if (StringUtils.isBlank(dPatientvisit.getPatientid()) || StringUtils.isBlank(dPatientvisit.getVisitid())) {
			BaseResponse baseResponse = new BaseResponse();
			response.setStatus(400);
			baseResponse.setCode("400");
			baseResponse.setText("该患者住院信息错误！");
			resultMap.put("response", baseResponse);
			return JSONObject.toJSONString(resultMap);
		}

		List<DMedicalrecords> recordList = medicalrecordsService.getByPatientId(dPatientvisit.getPatientid(),
				dPatientvisit.getVisitid());

		List<DDischargesummary> summaryList = dischargesummaryService.getByPatientId(dPatientvisit.getPatientid(),
				dPatientvisit.getVisitid());

		BaseResponse baseResponse = new BaseResponse();
		resultMap.put("response", baseResponse);

		if (recordList != null && recordList.size() > 0) {
			String xml = recordList.get(0).getMedicalrecords();
			
			resultMap.put("caseHistory", XmlUtil.coventXml(xml));
		}
		if (summaryList != null && summaryList.size() > 0) {
			Map tempObj = new HashMap();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");
			tempObj.put("出院时间", sdf.format(summaryList.get(0).getCydate()));
			tempObj.put("出院小结", summaryList.get(0).getLscontent());
			resultMap.put("outDept", tempObj);
		}
		String json = JSONObject.toJSONString(resultMap);
		log.info("response--> " + json);
		return json;
	}

}
