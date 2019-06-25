package com.callan.service.provider.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.callan.service.provider.config.JLPException;
import com.callan.service.provider.config.JLPLog;
import com.callan.service.provider.config.ThreadPoolConfig;
import com.callan.service.provider.pojo.advanceQueryBase.AdvancedQueryRecordModel;
import com.callan.service.provider.pojo.base.BaseResponse;
import com.callan.service.provider.service.IJProjectService;
import com.callan.service.provider.service.IJUserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 课题内查询
 *
 */
@RestController
@Api(description = "课题内查询")
public class ProjectQueryController {

	@Autowired
	private IJUserService userService;

@Autowired
private IJProjectService  projectService;

	@ApiOperation(value = "获取课题查询条件")
	@RequestMapping(value = "/api/ProjectQuery/{Id}", method = { RequestMethod.GET })
	public String Get(Long Id, HttpServletRequest request, HttpServletResponse response) {
		JLPLog log = ThreadPoolConfig.getBaseContext();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String authorization = request.getHeader("Authorization") == null ? "" : request.getHeader("Authorization");
		Long userId = userService.getIdByToken(authorization);
		if (userId == null) {
			userId = 0L;
		}
		try {
			AdvancedQueryRecordModel advancedQueryRecord = projectService.getQueryRecord(Id, userId);
			BaseResponse baseResponse = new BaseResponse();
			resultMap.put("response", baseResponse);
			resultMap.put("queryRecord", advancedQueryRecord);
			return JSONObject.toJSONString(resultMap);
		}catch( Exception e) {
			BaseResponse baseResponse = new BaseResponse();
			response.setStatus(404);
			baseResponse.setCode("404");
			baseResponse.setText(e.getMessage());
			resultMap.put("response", baseResponse);
			return JSONObject.toJSONString(resultMap);
		}
	}

	

//    /// <summary>
//    /// 课题数据查询
//    /// </summary>
//    /// <param name="urlPageParameter"></param>
//    /// <param name="projectQuery"></param>
//    /// <returns></returns>
//    public IHttpActionResult Post([FromUri]UrlPageParameterModel urlPageParameter, [FromBody]ProjectQueryModel projectQuery)
//    {
//        int pageNum = urlPageParameter.pageNum;
//        int pageSize = urlPageParameter.pageSize;
//
//        long userId = ActionContext.GetUserId();
//        if (pageNum == 0) { pageNum = 1; }
//        if (pageSize == 0) { pageSize = 20; }
//
//        DictionaryObj hashtable = new DictionaryObj();
//        ResponseModel responseModel = ResponseModel.Ok();
//
//        #region 传入参数空值验证
//        if (projectQuery is null)
//        {
//            responseModel = ResponseModel.Error(Text: "传入参数为空");
//            hashtable.Add("response", responseModel);
//            return Ok(hashtable);
//        }
//        else
//        {
//            if (projectQuery.projectId == 0)
//            {
//                responseModel = ResponseModel.Error(Text: "课题ID错误");
//                hashtable.Add("response", responseModel);
//                return Ok(hashtable);
//            }
//        }
//        #endregion
//
//        #region 验证视图编号
//        JTableclassdict tableclassdict = orclJlpContext.JTableclassdicts.FirstOrDefault(x => x.Id == projectQuery.tableClassId && x.Activeflag == JLPStaticProperties.ActiveFlag);
//        if (tableclassdict is null)
//        {
//            return Content(HttpStatusCode.BadRequest, ResponseModel.Error(Text: "数据分类信息错误"));
//        }
//
//        JShowview showview = orclJlpContext.JShowviews.FirstOrDefault(x => x.Id == tableclassdict.Viewid && x.Activeflag == "1");
//        if (showview is null)
//        {
//            responseModel = ResponseModel.Error(Text: "视图编号错误");
//            hashtable.Add("response", responseModel);
//            return Ok(hashtable);
//        }
//        #endregion
//
//        #region 获取显示视图信息
//        string viewTableName = string.Empty;
//        var showViewDetails = from detail in orclJlpContext.JShowdetailviews
//                              where detail.Activeflag == "1" && detail.Viewid == showview.Id
//                              join field in orclJlpContext.JTablefielddicts on detail.Fieldid equals field.Id into tablefields
//                              from field in tablefields.DefaultIfEmpty()
//                              where field.Showflag == "1"
//                              join table in orclJlpContext.JTabledicts on field.Tablecode equals table.Code into tables
//                              from table in tables.DefaultIfEmpty()
//                              where table.Activeflag == "1"
//                              orderby detail.Sortno
//                              select new { detail.Id, detail.Fieldid, detail.Sortno, detail.Fieldtitle, fieldInfos = field, tables = table };
//        if (projectQuery.queryShowFields != null)
//        {
//            List<long> temoFieldList = new List<long>();
//            projectQuery.queryShowFields.ToList().ForEach(x =>
//            {
//                if (long.TryParse(x, out long y))
//                {
//                    temoFieldList.Add(y);
//                }
//            });
//            showViewDetails = showViewDetails.Where(x => temoFieldList.Contains(x.Id));
//        }
//        var showFieldDetails = showViewDetails.Distinct().ToList().OrderBy(x => x.Sortno).ToList();
//        #endregion
//
//        #region 验证显示字段
//        if (showFieldDetails.Count() == 0)
//        {
//            responseModel.Code = "1001";
//            responseModel.Text = "显示字段未配置";
//            hashtable.Add("response", responseModel);
//            return Ok(hashtable);
//        }
//        viewTableName = (orclJlpContext.JTabledicts.FirstOrDefault(x => x.Code == showview.Maintablecode)?.Name.ToUpper()) ?? showFieldDetails.FirstOrDefault().tables.Name.ToUpper();
//        #endregion
//
//        #region 获取标签信息
//        var tagProjectDicts = (from tagdict in orclJlpContext.JTagdicts
//                               join tagvaluedict in orclJlpContext.JTagvaluedicts on tagdict.Id
//                                equals tagvaluedict.Tagid into taginfos
//                               from tagvaluedict in taginfos
//                               where tagdict.Activeflag == JLPStaticProperties.ActiveFlag
//                                  && tagdict.Showflag == JLPStaticProperties.ActiveFlag
//                                  && tagvaluedict.Activeflag == JLPStaticProperties.ActiveFlag
//                                  && tagdict.Projectid == projectQuery.projectId
//                                  && tagdict.Userid == userId
//                               group tagvaluedict by tagdict into taggroup
//                               select taggroup).ToList();
//
//        var tagShowDicts = tagProjectDicts.Where(x => x.Key.Tagattached == projectQuery.tableClassId.ToString()).ToList();
//        DictionaryObj tagShowRets = new DictionaryObj();
//        tagShowDicts.ForEach(x =>
//        {
//            tagShowRets.Add("tag_" + x.Key.Id.ToString(), new ProjectTagModel
//            {
//                id = x.Key.Id,
//                name = x.Key.Name,
//                fieldType = x.Key.Valuetype,
//                fieldValue = x.OrderBy(y => y.Id).Select(y =>
//               {
//                   if (!string.IsNullOrEmpty(y.Value))
//                   { return y.Value; }
//                   else
//                   { return y.Minvalue + "" + y.Maxvalue; }
//               }).ToArray().ToStringEx("/")
//            });
//        });
//        #endregion
//
//        List<List<QueryDetailModel>> queryMainDetails = new List<List<QueryDetailModel>>();
//        List<List<QueryDetailModel>> queryOtherTableClassDetails = new List<List<QueryDetailModel>>();
//
//
//        dynamic dynamicData = JsonConvert.DeserializeObject<dynamic>(Get(projectQuery.projectId).ExecuteAsync(CancellationToken.None).Result.Content.ReadAsStringAsync().Result);
//        //queryRecord
//        AdvancedQueryRecordModel projectQueryConds = null;
//
//        if (dynamicData.response.Code == "1000")
//        {
//            projectQueryConds = JsonConvert.DeserializeObject<AdvancedQueryRecordModel>(dynamicData.queryRecord.ToString());
//        }
//        else
//        {
//            responseModel.Code = "1001";
//            responseModel.Text = "无法获取课题查询数据";
//
//            hashtable.Add("response", responseModel);
//            hashtable.Add("initialization", true);
//            return Ok(hashtable);
//        }
//
//        List<QueryDetailModel> tempmainQueryModels = new List<QueryDetailModel>();
//        if (projectQueryConds.queries?.queryConds != null)
//        {
//            tempmainQueryModels.AddRange(projectQueryConds.queries?.queryConds);
//        }
//        if (projectQuery.queries != null && projectQuery.queries.queryConds != null && projectQuery.queries.queryConds.Length > 0)
//        {
//            if (tempmainQueryModels.Count > 0)
//            {
//                tempmainQueryModels.First().leftqueto = tempmainQueryModels.First().leftqueto + "(";
//                tempmainQueryModels.Last().rightqueto = tempmainQueryModels.First().rightqueto + ")";
//                tempmainQueryModels.Last().combinator = "AND";
//            }
//            tempmainQueryModels.AddRange(projectQuery.queries.queryConds);
//        }
//        if (tempmainQueryModels.Count > 0)
//        {
//            var tagShowCondition = tagShowDicts.Select(x => ("projecttags.tag_" + x.Key.Id.ToString()).ToUpper());
//            queryMainDetails.Add(tempmainQueryModels.Where(x =>
//            {
//                if (x.condition.ToUpper().Contains("TAG_"))
//                {
//                    return tagShowCondition.Contains(x.condition.ToUpper());
//                }
//                else
//                {
//                    return true;
//                }
//            }).ToList());
//            queryOtherTableClassDetails.Add(tempmainQueryModels.Where(x =>
//            {
//                if (x.condition.ToUpper().Contains("TAG_"))
//                {
//                    return !tagShowCondition.Contains(x.condition.ToUpper());
//                }
//                else
//                {
//                    return false;
//                }
//            }).ToList());
//        }
//
//
//        #region 获取查询所有表名
//        var tableNameMainWheres = from a in queryMainDetails
//                                  from b in a
//                                  select b.condition.Split('.').FirstOrDefault().ToUpper();
//        var tableNameWhereMainValues = from a in queryMainDetails
//                                       from b in a
//                                       where b.fieldType == FieldValueType.Field
//                                       select b.condValue.Split('.').FirstOrDefault().ToUpper();
//        var whereFieldsMain = from a in queryMainDetails
//                              from b in a
//                              select b.condition.ToUpper();
//
//        var whereFieldTypesMain = (from a in orclJlpContext.JTabledicts
//                                   join b in orclJlpContext.JTablefielddicts on a.Code equals b.Tablecode into tablefields
//                                   from b in tablefields
//                                   where a.Activeflag == "1" && whereFieldsMain.Contains(a.Name + "." + b.Name)
//                                   select new { field = a.Name + "." + b.Name, type = b.Type }).Distinct().ToList();
//
//        queryMainDetails.ForEach(x =>
//        {
//            x.ForEach(y =>
//            {
//                if (!y.condition.ToUpper().Contains("TAG_"))
//                {
//                    if (y.fieldType != FieldValueType.Field)
//                    {
//                        y.fieldType = (whereFieldTypesMain.FirstOrDefault(m => m.field == y.condition.ToUpper())?.type.toFieldValueType()) ?? FieldValueType.String;
//                    }
//                }
//                else
//                {
//                    y.fieldType = (tagShowDicts.FirstOrDefault(m => ("projecttags.tag_" + m.Key.Id.ToString()).ToUpper() == y.condition.ToUpper())?.Key.Valuetype == "2") ? FieldValueType.Number : FieldValueType.String;
//                }
//            });
//            if (x.LastOrDefault() != null)
//            {
//                x.LastOrDefault().combinator = "";
//            }
//        });
//
//        #endregion
//
//        #region 获取当前查询条件
//        var tableNames = showFieldDetails.Select(x => x.tables.Name).Distinct().ToList();
//        var fieldNames = showFieldDetails.Select(x => (x.tables.Name + "." + x.fieldInfos.Name).ToLower()).Distinct().ToList();
//        var fieldShowNames = showFieldDetails.Select(x => x.fieldInfos.Name.ToLower()).Distinct().ToList();
//
//        var columns = showFieldDetails.Select(
//            x => new ColunmsModel()
//            {
//                dataIndex = x.fieldInfos.Name.ToLower(),
//                key = x.fieldInfos.Name.ToLower(),
//                title = x.Fieldtitle,
//                isLongStr = x.fieldInfos.Type == "clob",
//                isSearched = x.fieldInfos.Queryflag == "1",
//                isTag = false
//            }).Distinct().ToList();
//
//        #region 标签显示项
//        if (tagShowDicts.Count > 0)
//        {
//            fieldShowNames.AddRange(tagShowDicts.Select(x => "tag_" + x.Key.Id.ToString()));
//            fieldShowNames.Add("tag_complete");
//            columns.Add(new ColunmsModel()
//            {
//                dataIndex = "tag_complete",
//                key = "tag_complete",
//                title = "标签进度",
//                isLongStr = false,
//                isSearched = false,
//                isTag = false
//            });
//            columns.AddRange(tagShowDicts.Select(x => new ColunmsModel()
//            {
//                dataIndex = ("TAG_" + x.Key.Id.ToString()).ToLower(),
//                key = ("TAG_" + x.Key.Id.ToString()).ToLower(),
//                title = x.Key.Name,
//                isLongStr = false,
//                isSearched = true,
//                isTag = true
//            }));
//            fieldNames.Add("(case when tag_complete is null then '未开始' else tag_complete end) as tag_complete");
//            fieldNames.Add("hide_tag_changestatus");
//            fieldNames.AddRange(tagShowDicts.Select(x => "PROJECTTAGS.TAG_" + x.Key.Id.ToString()));
//        }
//        #endregion
//        #endregion
//
//        var SqlWhereMain = queryMainDetails.Select(x => x.ToArray().ToStringEx(" ")).ToArray().ToStringEx(" and ");
//        tableNames.AddRange(tableNameMainWheres);
//        tableNames.AddRange(tableNameWhereMainValues);
//        tableNames = tableNames.Distinct().ToList();
//        tableNames.RemoveAll(x => x.ToUpper() == JLPStaticProperties.PatientGlobalTable);
//
//        #region 排序字段处理
//        string sortStr = string.Empty;
//        if (projectQuery.sorted is null) projectQuery.sorted = new SortFieldModel[] { };
//        var sorts = projectQuery.sorted.Where(x => fieldShowNames.Contains(x.id.ToLower())).Select(x => x.ToString()).ToArray();
//        sortStr = sorts.ToStringEx(",");
//        if (sortStr.Length == 0)
//        {
//            sortStr = "hide_key";
//        }
//        //fieldShowNames
//        #endregion
//
//        #region 纳排条件处理
//        List<string> includeSql = new List<string>();
//        List<string> excludeSql = new List<string>();
//
//        Dictionary<string, List<QueryCollectionModel>> queryList = new Dictionary<string, List<QueryCollectionModel>>();
//
//        if (projectQueryConds?.queries?.queryIncludesEX?.includes != null)
//        {
//            queryList.Add("include", projectQueryConds.queries.queryIncludesEX.includes.ToList());
//        }
//
//        if (projectQueryConds?.queries?.queryIncludesEX?.excludes != null)
//        {
//            queryList.Add("exclude", projectQueryConds.queries?.queryIncludesEX.excludes.ToList());
//        }
//
//        string tempadvancedQueryType = string.Empty;
//        foreach (var item in queryList)
//        {
//            foreach (var subItem in item.Value)
//            {
//                List<string> tempTableNames = new List<string>();
//                List<string> tempFieldNames = new List<string>();
//
//                tempFieldNames.Add(JLPStaticProperties.PatientGlobalTable + ".Id");
//
//                List<List<QueryDetailModel>> queryDetails = new List<List<QueryDetailModel>>();
//
//                if (subItem.Conds is null)
//                {
//                    continue;
//                }
//
//                queryDetails.Add(subItem.Conds.ToList());
//
//                #region 获取查询所有表名
//                var tableNameWheres = from a in queryDetails
//                                      from b in a
//                                      select b.condition.Split('.').FirstOrDefault().ToUpper();
//                var tableNameWhereValues = from a in queryDetails
//                                           from b in a
//                                           where b.fieldType == FieldValueType.Field
//                                           select b.condValue.Split('.').FirstOrDefault().ToUpper();
//                var whereFields = from a in queryDetails
//                                  from b in a
//                                  select b.condition.ToUpper();
//
//                var whereFieldTypes = (from a in orclJlpContext.JTabledicts
//                                       join b in orclJlpContext.JTablefielddicts on a.Code equals b.Tablecode into tablefields
//                                       from b in tablefields
//                                       where a.Activeflag == "1" && whereFields.Contains(a.Name + "." + b.Name)
//                                       select new { field = a.Name + "." + b.Name, type = b.Type }).Distinct().ToList();
//                queryDetails.ForEach(x =>
//                {
//                    x.ForEach(y =>
//                    {
//                        if (y.fieldType != FieldValueType.Field)
//                        {
//                            y.fieldType = (whereFieldTypes.FirstOrDefault(m => m.field == y.condition.ToUpper())?.type.toFieldValueType()) ?? FieldValueType.String;
//                        }
//                    });
//                    if (x.LastOrDefault() != null)
//                    {
//                        x.LastOrDefault().combinator = "";
//                    }
//                });
//
//                var SqlWhere = queryDetails.Select(x => x.ToArray().ToStringEx(" ")).ToArray().ToStringEx(" and ");
//                tempTableNames.AddRange(tableNameWheres);
//                tempTableNames.AddRange(tableNameWhereValues);
//                tempTableNames = tempTableNames.Distinct().ToList();
//                tempTableNames.RemoveAll(x => x.ToUpper() == JLPStaticProperties.PatientGlobalTable);
//                #endregion
//
//                #region 获取查询结果和数量
//                tempFieldNames = tempFieldNames.Distinct().ToList();
//                string tableWhere = tempTableNames.ToArray().ToTableStringEx();
//
//                string TempSql = $"{subItem.leftqueto}select distinct {tempFieldNames.ToArray().ToStringEx(",")} from {tableWhere} {(string.IsNullOrEmpty(SqlWhere) ? "" : " where " + SqlWhere)} {subItem.rightqueto}";
//
//                if (item.Key == "include")
//                {
//                    if (includeSql.Count != 0)
//                    {
//                        includeSql.Add(tempadvancedQueryType);
//                    }
//                    includeSql.Add(TempSql);
//                }
//                else if (item.Key == "exclude")
//                {
//                    if (excludeSql.Count != 0)
//                    {
//                        excludeSql.Add(tempadvancedQueryType);
//                    }
//                    excludeSql.Add(TempSql);
//                }
//
//                tempadvancedQueryType = subItem.setCombinator.ToString();
//            }
//            #endregion
//        }
//        #endregion
//
//        //增加隐藏主键
//        fieldNames.Add(JLPStaticProperties.PatientGlobalTable + ".Id as hide_key");
//        fieldNames.Add(viewTableName + ".Id as hide_dataid");
//
//        #region 组装SQL
//        StringBuilder includeStrBuilder = new StringBuilder();
//        includeSql.ForEach(x =>
//        {
//            includeStrBuilder.AppendLine(x + " ");
//        });
//
//        StringBuilder excludeStrBuilder = new StringBuilder();
//        excludeSql.ForEach(x =>
//        {
//            excludeStrBuilder.AppendLine(x + " ");
//        });
//        string tempSql = string.Empty;
//
//        LoggerHelper.Info("includeStrBuilder:" + includeStrBuilder.ToString());
//        LoggerHelper.Info("excludeStrBuilder:" + includeStrBuilder.ToString());
//
//        if (includeStrBuilder.Length > 0)
//        {
//            tempSql = $"select Id from ({includeStrBuilder.ToString()})";
//            if (excludeStrBuilder.Length > 0)
//            {
//                tempSql += $" minus ({excludeStrBuilder.ToString()})";
//            }
//
//            if (queryOtherTableClassDetails.Count > 0)
//            {
//                queryOtherTableClassDetails.ForEach(x =>
//                {
//                    x.ForEach(y =>
//                    {
//                        if (y.condition.ToUpper().Contains("TAG_"))
//                        {
//                            y.condition = y.condition.ToUpper().Replace("PROJECTTAGS.TAG_", "PROJECTTAGSEX.TAG_");
//                            y.fieldType = (tagShowDicts.FirstOrDefault(m => ("PROJECTTAGSEX.TAG_" + m.Key.Id.ToString()).ToUpper() == y.condition.ToUpper())?.Key.Valuetype == "2") ? FieldValueType.Number : FieldValueType.String;
//                        }
//                    });
//                    if (x.LastOrDefault() != null)
//                    {
//                        x.LastOrDefault().combinator = "";
//                    }
//                });
//                string OtherTagWhere = (from a in queryOtherTableClassDetails
//                                        from b in a
//                                        select b).ToArray().ToStringEx(" ");
//                var OtherTags = (from a in queryOtherTableClassDetails
//                                 from b in a
//                                 select b.condition.Split('.')[1]).ToArray();
//                if (OtherTags != null && OtherTags.Length > 0 && OtherTagWhere.Length > 0)
//                {
//                    string SqlTagsStr = $"select PatientGlobalId as Id from (select PatientGlobalId,('TAG_'||tagid) as tagName,tagValue from j_projecttags where where t.userid = {userId} and t.projectid = {projectQuery.projectId} and t.tableid <> {projectQuery.tableClassId}) pivot (max(tagvalue) for name in ({OtherTags.ToTagStringEx()})) projecttagsex where {OtherTagWhere}";
//                    tempSql += $" minus ({SqlTagsStr})";
//                }
//            }
//        }
//        string projectDataStatusWhere = string.Empty;
//        if (projectQuery.projectstatusIDs != null && projectQuery.projectstatusIDs.Length > 0)
//        {
//            var jprojectDataStatusDicts = orclJlpContext.JProjectdatastatusdicts.Where(x => x.Activeflag == JLPStaticProperties.ActiveFlag && x.Projectid == projectQuery.projectId && projectQuery.projectstatusIDs.Contains(x.Id)).Select(x => x.Name).ToArray();
//
//            projectDataStatusWhere = $" projectdatastatus.tag_complete in ('{jprojectDataStatusDicts.ToStringEx("','")}')";
//            if (jprojectDataStatusDicts.Contains("未开始"))
//            {
//                projectDataStatusWhere = $"({projectDataStatusWhere} or projectdatastatus.tag_complete is null)";
//            }
//        }
//        string projectDelData = $"select dataid as datadel_id from j_projectdeldata t where t.activeflag='1' and t.userid={userId} and t.projectid={projectQuery.projectId} and t.tableid={projectQuery.tableClassId} ";
//        string projectPatientDelData = string.Empty;
//        if (string.Compare(JLPStaticProperties.PatientGlobalTable, viewTableName, true) != 0)
//        {
//            projectPatientDelData = $"select dataid as datadel_id from j_projectdeldata t where t.activeflag='1' and t.userid={userId} and t.projectid={projectQuery.projectId} and t.tableid=1 ";
//        }
//
//        string dataStatusSql = $"select distinct j_projectdatastatus.patientglobalid,j_projectdatastatusdict.name as tag_complete,j_projectdatastatus.changestatus as hide_tag_changestatus from j_projectdatastatus left join j_projectdatastatusdict on j_projectdatastatus.status=j_projectdatastatusdict.id where j_projectdatastatus.activeflag='1' and j_projectdatastatus.userid={userId} and j_projectdatastatus.projectid={projectQuery.projectId}";
//        string showTagSql = $"select * from (select dataid,('TAG_'||tagid) as tagName,tagValue from j_projecttags where activeflag=1 and userid = {userId} and projectid = {projectQuery.projectId} and tableid = {projectQuery.tableClassId}) pivot (max(tagvalue) for tagName in ({tagShowDicts.Select(x => "TAG_" + x.Key.Id.ToString()).ToArray().ToTagStringEx()}))";
//        string Sql = $"select distinct {fieldNames.ToArray().ToStringEx()} from {tableNames.ToArray().ToTableStringTagEx(advancedQueryWhere: tempSql, tagSql: showTagSql, TagtableName: viewTableName, dataStatusSql: dataStatusSql, projectDelDataSql: projectDelData, projectDelPatSql: projectPatientDelData)}";
//
//        string tempSqlWhere = string.Empty;
//        if (tempSqlWhere.Length == 0)
//        {
//            tempSqlWhere = $" where D_PATIENTGLOBAL.jlactiveflag='1'";
//        }
//        else
//        {
//            tempSqlWhere += $" and D_PATIENTGLOBAL.jlactiveflag='1'";
//        }
//
//        if (projectQuery.patientGlobalId > 0)
//        {
//            tempSqlWhere += $" and D_PATIENTGLOBAL.Id={projectQuery.patientGlobalId}";
//        }
//
//        if (SqlWhereMain.Length > 0)
//        {
//            tempSqlWhere += $" and {SqlWhereMain}";
//        }
//
//        if (projectDataStatusWhere.Length > 0)
//        {
//            tempSqlWhere += $" and {projectDataStatusWhere}";
//        }
//
//        if (projectDelData.Length > 0)
//        {
//            tempSqlWhere += $" and projectdeldata.datadel_id is null";
//        }
//
//        if (projectPatientDelData.Length > 0)
//        {
//            tempSqlWhere += $" and projectdelpat.datadel_id is null";
//        }
//
//        Sql += tempSqlWhere;
//        #endregion
//
//        #region 组装返回值
//        string SqlCount = DBHelperBase.getCountSql(BarryCommon.DataBaseType.Oracle, Sql);
//        LoggerHelper.Info(Sql);
//        dynamic count = orclJlpContext.Database.DynamicSqlQueryMethod(SqlCount, new object[] { }).ToListAsync().Result.FirstOrDefault();
//        hashtable.Add("response", responseModel);
//        hashtable.Add("totals", count.num);
//        hashtable.Add("tagTypes", tagShowRets);
//        Sql = DBHelperBase.getPageSql(BarryCommon.DataBaseType.Oracle, Sql, pageSize, pageNum, sortStr);
//        LoggerHelper.Info(Sql);
//        object[] retData = orclJlpContext.Database.DynamicSqlQueryMethod(Sql, new object[] { }).ToListAsync().Result.ToArray();
//
//        var rights = ActionContext.GetUserRight();
//        if (rights is null || !rights.Any(x => x.Id == 4))
//        {
//            var sensitivewords = orclJlpContext.JSensitivewords.Where(x => x.Activeflag == JLPStaticProperties.ActiveFlag).Select(x => x.Name).ToArray().Select(x => x.ToLower()).ToArray();
//            if (sensitivewords.Count() > 0)
//            {
//                retData = retData.Sensitiveword(sensitivewords);
//            }
//        }
//
//
//        hashtable.Add("columns", columns);
//        hashtable.Add("content", retData);
//        hashtable.Add("initialization", false);
//        #endregion
//
//        return Ok(hashtable);
//    }
//
//
//    /// <summary>
//    /// 课题数据查询
//    /// </summary>
//    /// <param name="Id"></param>
//    /// <param name="TableClassId"></param>
//    /// <returns></returns>
//    [Route("Statics/{Id:long}/{TableClassId:long}")]
//    [HttpGet]
//    public async System.Threading.Tasks.Task<IHttpActionResult> StaticsAsync(long Id, long TableClassId)
//    {
//        long userId = ActionContext.GetUserId();
//
//        DictionaryObj hashtable = new DictionaryObj();
//        ResponseModel responseModel = ResponseModel.Ok();
//
//        #region 验证视图编号
//        JTableclassdict tableclassdict = orclJlpContext.JTableclassdicts.FirstOrDefault(x => x.Id == TableClassId && x.Activeflag == JLPStaticProperties.ActiveFlag);
//        if (tableclassdict is null)
//        {
//            return Content(HttpStatusCode.BadRequest, ResponseModel.Error(Text: "数据分类信息错误"));
//        }
//
//        JShowview showview = orclJlpContext.JShowviews.FirstOrDefault(x => x.Id == tableclassdict.Viewid && x.Activeflag == JLPStaticProperties.ActiveFlag);
//        if (showview is null)
//        {
//            responseModel = ResponseModel.Error(Text: "视图编号错误");
//            hashtable.Add("response", responseModel);
//            return Ok(hashtable);
//        }
//        #endregion
//
//        #region 获取显示视图信息
//        string viewTableName = string.Empty;
//        var showViewDetails = from detail in orclJlpContext.JShowdetailviews
//                              where detail.Activeflag == "1" && detail.Viewid == showview.Id
//                              join field in orclJlpContext.JTablefielddicts on detail.Fieldid equals field.Id into tablefields
//                              from field in tablefields.DefaultIfEmpty()
//                              where field.Showflag == "1"
//                              join table in orclJlpContext.JTabledicts on field.Tablecode equals table.Code into tables
//                              from table in tables.DefaultIfEmpty()
//                              where table.Activeflag == "1"
//                              orderby detail.Sortno
//                              select new { detail.Id, detail.Fieldid, detail.Sortno, detail.Fieldtitle, fieldInfos = field, tables = table };
//
//        var showFieldDetails = showViewDetails.Distinct().ToList().OrderBy(x => x.Sortno).ToList();
//        #endregion
//
//        #region 验证显示字段
//        if (showFieldDetails.Count() == 0)
//        {
//            responseModel.Code = "1001";
//            responseModel.Text = "显示字段未配置";
//            hashtable.Add("response", responseModel);
//            return Ok(hashtable);
//        }
//        viewTableName = showFieldDetails.FirstOrDefault().tables.Name.ToUpper();
//        #endregion
//
//        #region 获取当前查询条件
//        var tableNames = showFieldDetails.Select(x => x.tables.Name).Distinct().ToList();
//        var fieldNames = showFieldDetails.Select(x => (x.tables.Name + "." + x.fieldInfos.Name).ToLower()).Distinct().ToList();
//        var fieldShowNames = showFieldDetails.Select(x => x.fieldInfos.Name.ToLower()).Distinct().ToList();
//
//        #endregion
//
//
//        tableNames.RemoveAll(x => x.ToUpper() == JLPStaticProperties.PatientGlobalTable);
//
//
//        #region 纳排条件处理
//        List<string> includeSql = new List<string>();
//        List<string> excludeSql = new List<string>();
//
//        Dictionary<string, List<QueryCollectionModel>> queryList = new Dictionary<string, List<QueryCollectionModel>>();
//        dynamic dynamicData = JsonConvert.DeserializeObject<dynamic>(Get(Id).ExecuteAsync(CancellationToken.None).Result.Content.ReadAsStringAsync().Result);
//        //queryRecord
//
//        AdvancedQueryRecordModel projectQueryConds = JsonConvert.DeserializeObject<AdvancedQueryRecordModel>(dynamicData.queryRecord.ToString());
//
//        if (projectQueryConds?.queries?.queryIncludesEX?.includes != null)
//        {
//            queryList.Add("include", projectQueryConds.queries.queryIncludesEX.includes.ToList());
//        }
//
//        if (projectQueryConds?.queries?.queryIncludesEX?.excludes != null)
//        {
//            queryList.Add("exclude", projectQueryConds.queries?.queryIncludesEX.excludes.ToList());
//        }
//
//        if (projectQueryConds?.queries?.queryConds != null)
//        {
//            QueryCollectionModel queryCollection = new QueryCollectionModel()
//            {
//                Conds = projectQueryConds.queries.queryConds,
//                leftqueto = "(",
//                rightqueto = ")",
//                setCombinator = AdvancedQueryType.Intersect,
//                id = "",
//                type = ""
//            };
//            if (!queryList.ContainsKey("include"))
//            {
//                queryList.Add("include", new List<QueryCollectionModel>());
//            }
//            var relQCL = queryList["include"];
//            if (relQCL.Count > 0)
//            {
//                relQCL.First().leftqueto += "(";
//                relQCL.Last().rightqueto += ")";
//            }
//            relQCL.Add(queryCollection);
//        }
//
//        string tempadvancedQueryType = string.Empty;
//        foreach (var item in queryList)
//        {
//            foreach (var subItem in item.Value)
//            {
//                List<string> tempTableNames = new List<string>();
//                List<string> tempFieldNames = new List<string>();
//
//                tempFieldNames.Add(JLPStaticProperties.PatientGlobalTable + ".Id");
//
//                List<List<QueryDetailModel>> queryDetails = new List<List<QueryDetailModel>>();
//
//                if (subItem.Conds is null)
//                {
//                    continue;
//                }
//
//                queryDetails.Add(subItem.Conds.ToList());
//
//                #region 获取查询所有表名
//                var tableNameWheres = from a in queryDetails
//                                      from b in a
//                                      select b.condition.Split('.').FirstOrDefault().ToUpper();
//                var tableNameWhereValues = from a in queryDetails
//                                           from b in a
//                                           where b.fieldType == FieldValueType.Field
//                                           select b.condValue.Split('.').FirstOrDefault().ToUpper();
//                var whereFields = from a in queryDetails
//                                  from b in a
//                                  select b.condition.ToUpper();
//
//                var whereFieldTypes = (from a in orclJlpContext.JTabledicts
//                                       join b in orclJlpContext.JTablefielddicts on a.Code equals b.Tablecode into tablefields
//                                       from b in tablefields
//                                       where a.Activeflag == "1" && whereFields.Contains(a.Name + "." + b.Name)
//                                       select new { field = a.Name + "." + b.Name, type = b.Type }).Distinct().ToList();
//                queryDetails.ForEach(x =>
//                {
//                    x.ForEach(y =>
//                    {
//                        if (y.fieldType != FieldValueType.Field)
//                        {
//                            y.fieldType = (whereFieldTypes.FirstOrDefault(m => m.field == y.condition.ToUpper())?.type.toFieldValueType()) ?? FieldValueType.String;
//                        }
//                    });
//                    if (x.LastOrDefault() != null)
//                    {
//                        x.LastOrDefault().combinator = "";
//                    }
//                });
//
//                var SqlWhere = queryDetails.Select(x => x.ToArray().ToStringEx(" ")).ToArray().ToStringEx(" and ");
//                tempTableNames.AddRange(tableNameWheres);
//                tempTableNames.AddRange(tableNameWhereValues);
//                tempTableNames = tempTableNames.Distinct().ToList();
//                tempTableNames.RemoveAll(x => x.ToUpper() == JLPStaticProperties.PatientGlobalTable);
//                #endregion
//
//                #region 获取查询结果和数量
//                tempFieldNames = tempFieldNames.Distinct().ToList();
//                string tableWhere = tempTableNames.ToArray().ToTableStringEx();
//
//                string TempSql = $"{subItem.leftqueto}select distinct {tempFieldNames.ToArray().ToStringEx(",")} from {tableWhere} {(string.IsNullOrEmpty(SqlWhere) ? "" : " where " + SqlWhere)} {subItem.rightqueto}";
//
//                if (item.Key == "include")
//                {
//                    if (includeSql.Count != 0)
//                    {
//                        includeSql.Add(tempadvancedQueryType);
//                    }
//                    includeSql.Add(TempSql);
//                }
//                else if (item.Key == "exclude")
//                {
//                    if (excludeSql.Count != 0)
//                    {
//                        excludeSql.Add(tempadvancedQueryType);
//                    }
//                    excludeSql.Add(TempSql);
//                }
//
//                tempadvancedQueryType = subItem.setCombinator.ToString();
//            }
//            #endregion
//        }
//        #endregion
//
//        //增加隐藏主键
//        fieldNames.Add(JLPStaticProperties.PatientGlobalTable + ".Id as hide_key");
//        fieldNames.Add(viewTableName + ".Id as hide_dataid");
//
//        #region 组装SQL
//        StringBuilder includeStrBuilder = new StringBuilder();
//        includeSql.ForEach(x =>
//        {
//            includeStrBuilder.AppendLine(x + " ");
//        });
//
//        StringBuilder excludeStrBuilder = new StringBuilder();
//        excludeSql.ForEach(x =>
//        {
//            excludeStrBuilder.AppendLine(x + " ");
//        });
//        string tempSql = string.Empty;
//
//        LoggerHelper.Info("includeStrBuilder:" + includeStrBuilder.ToString());
//        LoggerHelper.Info("excludeStrBuilder:" + includeStrBuilder.ToString());
//
//        if (includeStrBuilder.Length > 0)
//        {
//            tempSql = $"select Id from ({includeStrBuilder.ToString()})";
//            if (excludeStrBuilder.Length > 0)
//            {
//                tempSql += $" minus ({excludeStrBuilder.ToString()})";
//            }
//        }
//
//        string Sql = $"select distinct {fieldNames.ToArray().ToStringEx()} from {tableNames.ToArray().ToTableStringEx(advancedQueryWhere: tempSql)}";
//
//        string tempSqlWhere = string.Empty;
//        if (tempSqlWhere.Length == 0)
//        {
//            tempSqlWhere = $" where D_PATIENTGLOBAL.jlactiveflag='1'";
//        }
//        else
//        {
//            tempSqlWhere += $" and D_PATIENTGLOBAL.jlactiveflag='1'";
//        }
//        Sql += tempSqlWhere;
//        #endregion
//
//        #region 组装返回值
//        string SqlCount = DBHelperBase.getCountSql(BarryCommon.DataBaseType.Oracle, Sql);
//        LoggerHelper.Info(Sql);
//        dynamic count = orclJlpContext.Database.DynamicSqlQueryMethod(SqlCount, new object[] { }).ToListAsync().Result.FirstOrDefault();
//
//        hashtable.Add("response", responseModel);
//        hashtable.Add("totals", count.num);
//        #endregion
//
//        string tagClass = TableClassId.ToString();
//        long tempCount = (long)count.num;
//
//        var projectStatistics = orclJlpContext.JProjectstatistics.Where(x => x.Activeflag == JLPStaticProperties.ActiveFlag && x.Projectid == Id && x.Statisticstype == "1" && x.Statisticstypedataid == TableClassId).ToList();
//        if (projectStatistics.Count() > 0)
//        {
//            projectStatistics.ForEach(x => { x.Count = tempCount; orclJlpContext.Entry(x).State = System.Data.Entity.EntityState.Modified; });
//        }
//        else
//        {
//            JProjectstatistics projectstatistics = new JProjectstatistics()
//            {
//                Activeflag = JLPStaticProperties.ActiveFlag,
//                Count = tempCount,
//                Createdate = DateTime.Now,
//                Projectid = Id,
//                Statisticstype = "1",
//                Statisticstypedataid = TableClassId
//            };
//            projectstatistics.NewId(orclJlpContext);
//            orclJlpContext.JProjectstatistics.Add(projectstatistics);
//        }
//        await orclJlpContext.SaveChangesAsync();
//
//        return Ok(hashtable);
//    }
}
