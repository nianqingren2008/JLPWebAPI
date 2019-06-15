package com.callan.service.provider.controller;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.callan.service.provider.config.JLPLog;
import com.callan.service.provider.config.ObjectUtil;
import com.callan.service.provider.config.ThreadPoolConfig;
import com.callan.service.provider.pojo.base.BaseResponse;
import com.callan.service.provider.pojo.db.DPatientglobal;
import com.callan.service.provider.pojo.db.DPatientvisit;
import com.callan.service.provider.pojo.db.JRight;
import com.callan.service.provider.pojo.db.JRoleRight;
import com.callan.service.provider.pojo.db.JSensitiveWord;
import com.callan.service.provider.pojo.panorama.BasePatientInfoModel;
import com.callan.service.provider.pojo.panorama.RecordPatients;
import com.callan.service.provider.service.IDPatientglobalService;
import com.callan.service.provider.service.IDPatientvisitService;
import com.callan.service.provider.service.IJFiletypeService;
import com.callan.service.provider.service.IJRoleRightService;
import com.callan.service.provider.service.IJSensitiveWordService;
import com.callan.service.provider.service.IJUserService;

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

	@ApiOperation(value = "获取全景视图患者住院列表")
	@RequestMapping(value = "/api/Panorama/{PanoramaType}/{Id}", method = { RequestMethod.GET })
	public String getPanoramaDetail(long id, int pageSize, int pageNum, HttpServletRequest request) {
		JLPLog log = ThreadPoolConfig.getBaseContext();
		Map<String, Object> resultMap = new HashMap<String, Object>();

		boolean hasPage = true;
		if (pageNum <= 0 || pageSize <= 0) {
			hasPage = false;
		}

		DPatientglobal patientglobal = patientglobalService.getOne(id);
		if (patientglobal == null) {
			BaseResponse baseResponse = new BaseResponse();
			baseResponse.setCode("0000");
			baseResponse.setText("未找到该患者信息！");
			resultMap.put("response", baseResponse);
			return JSONObject.toJSONString(resultMap);
		}
		List<DPatientvisit> vistList = patientvisitService.getByPatientGlobalId(patientglobal.getId());
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

		// 从前台header中获取token参数
		String authorization = request.getHeader("Authorization") == null ? "" : request.getHeader("Authorization");
		Long userRole = jUserService.getUserRoleByToken(authorization);
		if (userRole != null && userRole != 0L) {
			List<JRoleRight> roleRightList = roleRightService.getByRoleId(userRole);
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
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
//	
//	
//	
//	 #region 全景视图信息
//     /// <summary>
//     /// 全景视图信息
//     /// </summary>
//     /// <param name="PanoramaType">全景中数据类别</param>
//     /// <param name="Id">患者住院记录ID</param>
//     /// <param name="urlParameter"></param>
//     /// <returns></returns>
//     [Route("{panoramaType:length(1,20)}/{id:long:min(1)}")]
//     public IHttpActionResult Get(string PanoramaType, long Id, [FromUri]UrlPageParameterModel urlParameter)
//     {
//         var views = (from pageview in orclJlpContext.JPageviews
//                      join pageviewdetail in orclJlpContext.JPageviewdetails on pageview.Code equals pageviewdetail.Pagecode into pageviewdetails
//                      from pageviewdetail in pageviewdetails
//                      join showview in orclJlpContext.JShowviews on pageviewdetail.Viewcode equals showview.Code into showviews
//                      from showview in showviews
//                      where pageview.Activeflag == "1" && pageviewdetail.Activeflag == "1" && showview.Activeflag == "1" && pageview.Code == "1002"
//                      select showview).ToList();
//         JShowview panoramashowview = null;
//         if (views.Count() == 0 || (panoramashowview = views.FirstOrDefault(x => x.Accesscode.ToLower() == PanoramaType.ToLower())) is null)
//         {
//             return BadRequest("请求资源错误");
//         }
//
//         bool hasPage = true;
//         int pageNum = 0;
//         int pageSize = 0;
//         if (urlParameter == null || urlParameter.pageNum == 0)
//         {
//             hasPage = false;
//         }
//         else
//         {
//             pageNum = urlParameter.pageNum;
//             pageSize = urlParameter.pageSize;
//         }
//         DPatientvisit dPatientvisit = orclJlpContext.DPatientvisits.FirstOrDefault(x => x.Id == Id && x.Jlactiveflag == "1");
//         if (dPatientvisit is null)
//         {
//             return Content(HttpStatusCode.BadRequest, ResponseModel.Error(Text: "未找到该住院信息！").ToDictionaryObj());
//         }
//         if (string.IsNullOrEmpty(dPatientvisit.Patientid)
//             || string.IsNullOrEmpty(dPatientvisit.Visitid))
//         {
//             return Content(HttpStatusCode.BadRequest, ResponseModel.Error(Text: "该患者住院信息错误！").ToDictionaryObj());
//         }
//
//         var showFields = (from showview in orclJlpContext.JShowviews
//                           join showviewdetail in orclJlpContext.JShowdetailviews on showview.Id equals showviewdetail.Viewid into showviewDetails
//                           from showviewdetail in showviewDetails
//                           join field in orclJlpContext.JTablefielddicts on showviewdetail.Fieldid equals field.Id into fieldInfos
//                           from field in fieldInfos
//                           join fieldTable in orclJlpContext.JTabledicts on field.Tablecode equals fieldTable.Code into fieldtables
//                           from fieldtable in fieldtables
//                           where showview.Activeflag == "1" && showview.Id == panoramashowview.Id && showviewdetail.Activeflag == "1" && field.Showflag == "1" && fieldtable.Activeflag == "1"
//                           select new { showField = showviewdetail, field, fieldtable }).Distinct().ToList();
//         if (showFields.Count() == 0)
//         {
//             return Content(HttpStatusCode.BadRequest, ResponseModel.Error(Text: $"未配置全景信息视图{PanoramaType}，请联系技术人员！").ToDictionaryObj());
//         }
//
//         if (showFields.Select(x => x.fieldtable).Distinct().Count() > 1)
//         {
//             return Content(HttpStatusCode.BadRequest, ResponseModel.Error(Text: $"全景信息视图{PanoramaType}暂时不支持多表联合查看，请联系技术人员！").ToDictionaryObj());
//         }
//         showFields = showFields.OrderBy(x => x.showField.Sortno).ToList();
//         DictionaryObj ret = new DictionaryObj();
//         var columns = showFields.Select(x => new ColunmsModel
//         {
//             key = x.field.Name.ToLower(),
//             dataIndex = x.field.Name.ToLower(),
//             title = string.IsNullOrEmpty(x.showField.Fieldtitle) ? x.field.Description : x.showField.Fieldtitle,
//             isLongStr = x.field.Type == "CLOB",
//             isSearched = x.field.Queryflag == "1"
//         });
//
//         string patientId = dPatientvisit.Patientid;
//         string visitId = dPatientvisit.Visitid;
//
//         var fieldStrs = showFields.Select(x => x.field.Name).Distinct().ToList();
//         var tableName = showFields.FirstOrDefault().fieldtable.Name;
//         int totals = 0;
//
//         fieldStrs.Add("Id as hide_key");
//
//         string Sql = $"select distinct {fieldStrs.ToArray().ToStringEx()} from {tableName} where ";
//         if (PanoramaType == "basepatient")
//         {
//             Sql += $" Id={dPatientvisit.Patientglobalid} ";
//         }
//         else
//         {
//             Sql += $" patientId='{patientId}' and visitId='{visitId}'";
//         }
//         if (hasPage)
//         {
//             string sqlCount = DBHelperBase.getCountSql(BarryCommon.DataBaseType.Oracle, Sql);
//             totals = (int)orclJlpContext.Database.DynamicSqlQueryMethod(sqlCount, new object[] { }).ToListAsync().Result.FirstOrDefault();
//             Sql = DBHelperBase.getPageSql(BarryCommon.DataBaseType.Oracle, Sql, pageNum, pageNum);
//         }
//         var contentData = orclJlpContext.Database.DynamicSqlQueryMethod(Sql, new object[] { }).ToListAsync().Result.ToArray();
//         if (totals == 0)
//         {
//             totals = contentData.Length;
//         }
//
//         if (contentData.Length > 0)
//         {
//             var rights = ActionContext.GetUserRight();
//             if (rights is null || !rights.Any(x => x.Id == 4))
//             {
//                 var sensitivewords = orclJlpContext.JSensitivewords.Where(x => x.Activeflag == JLPStaticProperties.ActiveFlag).Select(x => x.Name).ToArray().Select(x => x.ToLower()).ToArray();
//                 if (sensitivewords.Count() > 0)
//                 {
//                     contentData = contentData.Sensitiveword(sensitivewords);
//                 }
//             }
//         }
//
//         ResponseModel response = ResponseModel.Ok();
//
//         ret.Add("response", response);
//         ret.Add("totals", totals);
//         ret.Add("columns", columns);
//         ret.Add("content", contentData);
//         return Ok(ret);
//     }
//     #endregion
//
//     #region 检验结果信息获取
//     /// <summary>
//     /// 检验信息获取
//     /// </summary>
//     /// <param name="Id">检验主表ID</param>
//     /// <param name="urlParameter"></param>
//     /// <returns></returns>
//     [HttpGet]
//     [Route("LabResult/{id:long:min(1)}")]
//     public IHttpActionResult LabResult(long Id, [FromUri]UrlPageParameterModel urlParameter)
//     {
//         var views = (from pageview in orclJlpContext.JPageviews
//                      join pageviewdetail in orclJlpContext.JPageviewdetails on pageview.Code equals pageviewdetail.Pagecode into pageviewdetails
//                      from pageviewdetail in pageviewdetails
//                      join showview in orclJlpContext.JShowviews on pageviewdetail.Viewcode equals showview.Code into showviews
//                      from showview in showviews
//                      where pageview.Activeflag == "1" && pageviewdetail.Activeflag == "1" && showview.Activeflag == "1" && pageview.Code == "1002"
//                      select showview).ToList();
//         JShowview panoramashowview = null;
//         if (views.Count() == 0 || (panoramashowview = views.FirstOrDefault(x => x.Accesscode.ToLower() == "labresult")) is null)
//         {
//             return BadRequest("请求资源错误");
//         }
//
//         bool hasPage = true;
//         int pageNum = 0;
//         int pageSize = 0;
//         if (urlParameter == null || urlParameter.pageNum == 0)
//         {
//             hasPage = false;
//         }
//         else
//         {
//             pageNum = urlParameter.pageNum;
//             pageSize = urlParameter.pageSize;
//         }
//         DLabmasterinfo labmasterinfo = orclJlpContext.DLabmasterinfos.FirstOrDefault(x => x.Id == Id && x.Jlactiveflag == "1");
//         if (labmasterinfo is null)
//         {
//             return Content(HttpStatusCode.BadRequest, ResponseModel.Error(Text: "未找到该检验信息！").ToDictionaryObj());
//         }
//         if (string.IsNullOrEmpty(labmasterinfo.Applyno))
//         {
//             return Content(HttpStatusCode.BadRequest, ResponseModel.Error(Text: "该检验信息数据缺失！").ToDictionaryObj());
//         }
//
//         var showFields = (from showview in orclJlpContext.JShowviews
//                           join showviewdetail in orclJlpContext.JShowdetailviews on showview.Id equals showviewdetail.Viewid into showviewDetails
//                           from showviewdetail in showviewDetails
//                           join field in orclJlpContext.JTablefielddicts on showviewdetail.Fieldid equals field.Id into fieldInfos
//                           from field in fieldInfos
//                           join fieldTable in orclJlpContext.JTabledicts on field.Tablecode equals fieldTable.Code into fieldtables
//                           from fieldtable in fieldtables
//                           where showview.Activeflag == "1" && showview.Id == panoramashowview.Id && showviewdetail.Activeflag == "1" && field.Showflag == "1" && fieldtable.Activeflag == "1"
//                           select new { showField = showviewdetail, field, fieldtable }).Distinct().ToList();
//         if (showFields.Count() == 0)
//         {
//             return Content(HttpStatusCode.BadRequest, ResponseModel.Error(Text: $"未配置全景信息视图labresult，请联系技术人员！").ToDictionaryObj());
//         }
//
//         if (showFields.Select(x => x.fieldtable).Distinct().Count() > 1)
//         {
//             return Content(HttpStatusCode.BadRequest, ResponseModel.Error(Text: $"全景信息视图labresult暂时不支持多表联合查看，请联系技术人员！").ToDictionaryObj());
//         }
//         showFields = showFields.OrderBy(x => x.showField.Sortno).ToList();
//         DictionaryObj ret = new DictionaryObj();
//         var columns = showFields.Select(x => new ColunmsModel
//         {
//             key = x.field.Name.ToLower(),
//             dataIndex = x.field.Name.ToLower(),
//             title = string.IsNullOrEmpty(x.showField.Fieldtitle) ? x.field.Description : x.showField.Fieldtitle,
//             isLongStr = x.field.Type == "CLOB",
//             isSearched = x.field.Queryflag == "1"
//         });
//
//         var fieldStrs = showFields.Select(x => x.field.Name).Distinct().ToList();
//         var tableName = showFields.FirstOrDefault().fieldtable.Name;
//         int totals = 0;
//
//         fieldStrs.Add("Id as hide_key");
//
//         string Sql = $"select distinct {fieldStrs.ToArray().ToStringEx()} from {tableName} where applyNo='{labmasterinfo.Applyno}'";
//         if (hasPage)
//         {
//             string sqlCount = DBHelperBase.getCountSql(BarryCommon.DataBaseType.Oracle, Sql);
//             totals = (int)orclJlpContext.Database.DynamicSqlQueryMethod(sqlCount, new object[] { }).ToListAsync().Result.FirstOrDefault();
//             Sql = DBHelperBase.getPageSql(BarryCommon.DataBaseType.Oracle, Sql, pageNum, pageNum);
//         }
//         var contentData = orclJlpContext.Database.DynamicSqlQueryMethod(Sql, new object[] { }).ToListAsync().Result;
//         if (totals == 0)
//         {
//             totals = contentData.Count;
//         }
//         ResponseModel response = ResponseModel.Ok();
//         ret.Add("response", response);
//         ret.Add("totals", totals);
//         ret.Add("columns", columns);
//         ret.Add("content", contentData);
//         return Ok(ret);
//     }
//     #endregion
//
//     #region 患者住院信息
//     /// <summary>
//     /// 患者住院信息
//     /// </summary>
//     /// <param name="Id">患者住院ID</param>
//     /// <returns></returns>
//     [Route("Inpat/{id:long:min(1)}")]
//     [HttpGet]
//     public IHttpActionResult Inpat(long Id)
//     {
//         DPatientvisit dPatientvisit = orclJlpContext.DPatientvisits.FirstOrDefault(x => x.Id == Id && x.Jlactiveflag == "1");
//         if (dPatientvisit is null)
//         {
//             return Content(HttpStatusCode.BadRequest, ResponseModel.Error(Text: "未找到该住院信息！").ToDictionaryObj());
//         }
//         if (string.IsNullOrEmpty(dPatientvisit.Patientid)
//             || string.IsNullOrEmpty(dPatientvisit.Visitid))
//         {
//             return Content(HttpStatusCode.BadRequest, ResponseModel.Error(Text: "该患者住院信息错误！").ToDictionaryObj());
//         }
//
//         DictionaryObj ret = new DictionaryObj();
//         DMedicalrecords medicalrecords = orclJlpContext.DMedicalrecords.FirstOrDefault(x => x.Patientid == dPatientvisit.Patientid && x.Visitid == dPatientvisit.Visitid);
//         DDischargesummary dDischargesummary = orclJlpContext.DDischargesummaries.FirstOrDefault(x => x.Patientid == dPatientvisit.Patientid && x.Visitid == dPatientvisit.Visitid);
//
//         ResponseModel response = ResponseModel.Ok();
//         ret.Add("response", response);
//
//         if (medicalrecords != null && medicalrecords.Medicalrecords.Length != 0)
//         {
//             ret.Add("caseHistory", JLPStaticProperties.MedicalRecordsHandle(medicalrecords.Medicalrecords));
//         }
//         if (dDischargesummary != null)
//         {
//             DictionaryObj tempObj = new DictionaryObj();
//             tempObj.Add("出院时间", dDischargesummary.Cydate?.ToString("yyyy年MM月dd日 HH时mm分ss秒"));
//             tempObj.Add("出院小结", dDischargesummary.Lscontent);
//             ret.Add("outDept", tempObj);
//         }
//         return Ok(ret);
//     }
//     #endregion
// }
	
	
}
