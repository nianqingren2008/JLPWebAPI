package com.callan.service.provider.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.callan.service.provider.config.JLPLog;
import com.callan.service.provider.config.ThreadPoolConfig;
import com.callan.service.provider.pojo.base.BaseResponse;
import com.callan.service.provider.pojo.db.JFiletype;
import com.callan.service.provider.service.IJFiletypeService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(description = "全景视图管理")
public class PanoramaController {
	@Autowired
	private IJFiletypeService filetypeService;
	
	
	@ApiOperation(value = "获取全景视图信息")
	@RequestMapping(value = "/api/Panorama/{PanoramaType}/{Id}", method = { RequestMethod.GET })
    public String getPanoramaDetail(long id,  String pageSize,String pageNum) {
//		bool hasPage = true;
//        if (urlParameter == null || urlParameter.pageNum <= 0 || urlParameter.pageSize <= 0)
//        {
//            hasPage = false;
//        }
//
//        DPatientglobal patientglobal = orclJlpContext.DPatientglobals.FirstOrDefault(x => x.Jlactiveflag == "1" && x.Id == Id);
//        if (patientglobal is null)
//        {
//            return Content(HttpStatusCode.BadRequest, ResponseModel.Error(Text: "未找到该患者信息！").ToDictionaryObj());
//        }
//        DictionaryObj ret = new DictionaryObj();
//        ResponseModel response = ResponseModel.Ok();
//        var patientVisitQuery = orclJlpContext.DPatientvisits.Where(x => x.Jlactiveflag == "1" && x.Patientglobalid == patientglobal.Id).OrderBy(x => x.Rydatetime).AsQueryable();
//        int patientVisitCount = patientVisitQuery.Count();
//        if (hasPage)
//        {
//            patientVisitQuery = patientVisitQuery.Skip((urlParameter.pageNum - 1) * urlParameter.pageSize).Take(urlParameter.pageSize);
//        }
//        var patientVisits = patientVisitQuery.ToList();
//        BasePatientInfoModel basePatientInfo = new BasePatientInfoModel()
//        {
//            patientID = patientglobal.Id.ToString(),
//            patientName = patientglobal.Name,
//            birthday = (patientglobal.Birthday?.ToString("yyyy-MM-dd")) ?? "暂无",
//            sex = patientglobal.Sex,
//            totalInpatients = patientVisitCount,
//            totalRecords = patientVisitCount
//        };
//        var patientRecords = patientVisits.Select(x => new RecordPatients
//        {
//            indeptNum = (int)x.Id,
//            type = "住院",
//            recordDate = (x.Rydatetime?.ToString("yyyy-MM-dd")) ?? "暂无",
//            recordTime = (x.Cydatetime?.ToString("yyyy-MM-dd")) ?? "暂无",
//            inDept = x.Rydept,
//            outDept = x.Cydept,
//            inDeptTotoalData = (x.Rydatetime.HasValue && x.Cydatetime.HasValue) ? (x.Cydatetime.Value - x.Rydatetime.Value).Days : 0,
//            dialognose = x.Diagnosecontent
//        });
//
//        var rights = ActionContext.GetUserRight();
//        if (rights is null || !rights.Any(x => x.Id == 4))
//        {
//            var sensitivewords = orclJlpContext.JSensitivewords.Where(x => x.Activeflag == JLPStaticProperties.ActiveFlag).Select(x => x.Name).ToArray().Select(x => x.ToLower()).ToArray();
//            if (sensitivewords.Count() > 0)
//            {
//                basePatientInfo = (new BasePatientInfoModel[] { basePatientInfo }).Sensitiveword(sensitivewords).First();
//            }
//        }
//
//        ret.Add("response", response);
//        ret.Add("baseInfo", basePatientInfo);
//        ret.Add("recordPatients", patientRecords);
//

		return null;
    }
}