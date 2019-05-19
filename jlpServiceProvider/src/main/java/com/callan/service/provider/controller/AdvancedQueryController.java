package com.callan.service.provider.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdvancedQueryController {
	
	@RequestMapping(value = "/api/AdvanceQuery",method = RequestMethod.GET)
	public String query(String requestJson) {
		
        int pageNum = urlParameter.pageNum;
        int pageSize = urlParameter.pageSize;
        bool isTotals = urlStatistic.totals;

        if (pageNum == 0)
        {
            pageNum = 1;
        }
        if (pageSize == 0)
        {
            pageSize = 20;
        }

        DictionaryObj hashtable = new DictionaryObj();
        ResponseModel responseModel = ResponseModel.Ok();

        #region 传入参数空值验证
        if (advancedQuery is null)
        {
            responseModel = ResponseModel.Error(Text: "传入参数为空");
            hashtable.Add("response", responseModel);
            return Ok(hashtable);
        }
        else
        {
            if (advancedQuery.viewId == 0)
            {
                responseModel = ResponseModel.Error(Text: "视图编号错误");
                hashtable.Add("response", responseModel);
                return Ok(hashtable);
            }
        }
        #endregion

        #region 验证视图编号
        JShowview showview = orclJlpContext.JShowviews.FirstOrDefault(x => x.Id == advancedQuery.viewId && x.Activeflag == "1");
        if (showview is null)
        {
            responseModel = ResponseModel.Error(Text: "视图编号错误");
            hashtable.Add("response", responseModel);
            return Ok(hashtable);
        }
        var mainTable = orclJlpContext.JTabledicts.FirstOrDefault(x => x.Code == showview.Maintablecode && x.Activeflag == JLPStaticProperties.ActiveFlag);

        #endregion

        #region 获取显示视图信息
        var showViewDetails = from detail in orclJlpContext.JShowdetailviews
                              where detail.Activeflag == "1" && detail.Viewid == showview.Id
                              join field in orclJlpContext.JTablefielddicts on detail.Fieldid equals field.Id into tablefields
                              from field in tablefields.DefaultIfEmpty()
                              where field.Showflag == "1"
                              join table in orclJlpContext.JTabledicts on field.Tablecode equals table.Code into tables
                              from table in tables.DefaultIfEmpty()
                              where table.Activeflag == "1"
                              orderby detail.Sortno
                              select new { detail.Id, detail.Fieldid, detail.Sortno, detail.Fieldtitle, fieldInfos = field, tables = table };

        if (advancedQuery.queryShowFields != null)
        {
            List<long> temoFieldList = new List<long>();
            advancedQuery.queryShowFields.ToList().ForEach(x =>
            {
                if (long.TryParse(x, out long y))
                {
                    temoFieldList.Add(y);
                }
            });
            showViewDetails = showViewDetails.Where(x => temoFieldList.Contains(x.Id));
        }
        var showFieldDetails = showViewDetails.Distinct().ToList().OrderBy(x => x.Sortno);
        #endregion

        #region 验证显示字段
        if (showFieldDetails.Count() == 0)
        {
            responseModel.Code = "1001";
            responseModel.Text = "显示字段未配置";
            hashtable.Add("response", responseModel);
            return Ok(hashtable);
        }
        #endregion

        List<List<QueryDetailModel>> queryMainDetails = new List<List<QueryDetailModel>>();
        if (advancedQuery.queries?.queryConds != null && advancedQuery.queries?.queryConds.Length > 0)
        {
            queryMainDetails.Add(advancedQuery.queries.queryConds.ToList());
        }

        #region 获取查询所有表名
        var tableNameMainWheres = from a in queryMainDetails
                                  from b in a
                                  select b.condition.Split('.').FirstOrDefault().ToUpper();
        var tableNameWhereMainValues = from a in queryMainDetails
                                       from b in a
                                       where b.fieldType == FieldValueType.Field
                                       select b.condValue.Split('.').FirstOrDefault().ToUpper();
        var whereFieldsMain = from a in queryMainDetails
                              from b in a
                              select b.condition.ToUpper();

        var whereFieldTypesMain = (from a in orclJlpContext.JTabledicts
                                   join b in orclJlpContext.JTablefielddicts on a.Code equals b.Tablecode into tablefields
                                   from b in tablefields
                                   where a.Activeflag == "1" && whereFieldsMain.Contains(a.Name + "." + b.Name)
                                   select new { field = a.Name + "." + b.Name, type = b.Type }).Distinct().ToList();
        queryMainDetails.ForEach(x =>
        {
            x.ForEach(y =>
            {
                if (y.fieldType != FieldValueType.Field)
                {
                    y.fieldType = (whereFieldTypesMain.FirstOrDefault(m => m.field == y.condition.ToUpper())?.type.toFieldValueType()) ?? FieldValueType.String;
                }
            });
            if (x.LastOrDefault() != null)
            {
                x.LastOrDefault().combinator = "";
            }
        });

        #endregion

        #region 获取当前查询条件
        var tableNames = showFieldDetails.Select(x => x.tables.Name).Distinct().ToList();
        var fieldNames = showFieldDetails.Select(x => (x.tables.Name + "." + x.fieldInfos.Name).ToLower()).Distinct().ToList();
        var fieldShowNames = showFieldDetails.Select(x => x.fieldInfos.Name.ToLower()).Distinct().ToArray();

        var columns = showFieldDetails.Select(
            x => new ColunmsModel()
            {
                dataIndex = x.fieldInfos.Name.ToLower(),
                key = x.fieldInfos.Name.ToLower(),
                title = x.Fieldtitle,
                isLongStr = x.fieldInfos.Type == "clob",
                isSearched = x.fieldInfos.Queryflag == "1"
            }).Distinct().ToArray();
        #endregion

        var SqlWhereMain = queryMainDetails.Select(x => x.ToArray().ToStringEx(" ")).ToArray().ToStringEx(" and ");
        tableNames.AddRange(tableNameMainWheres);
        tableNames.AddRange(tableNameWhereMainValues);
        tableNames = tableNames.Distinct().ToList();
        tableNames.RemoveAll(x => x.ToUpper() == JLPStaticProperties.PatientGlobalTable);

        #region 排序字段处理
        string sortStr = string.Empty;
        if (advancedQuery.sorted is null) advancedQuery.sorted = new SortFieldModel[] { };
        var sorts = advancedQuery.sorted.Where(x => fieldShowNames.Contains(x.id.ToLower())).Select(x => x.ToString()).ToArray();
        sortStr = sorts.ToStringEx(",");
        //fieldShowNames
        #endregion


        List<string> includeSql = new List<string>();
        List<string> excludeSql = new List<string>();

        Dictionary<string, List<QueryCollectionModel>> queryList = new Dictionary<string, List<QueryCollectionModel>>();

        if (advancedQuery.queries?.queryIncludesEX?.includes != null)
        {
            queryList.Add("include", advancedQuery.queries.queryIncludesEX.includes.ToList());
        }

        if (!urlStatistic.includesTotal && advancedQuery.queries?.queryIncludesEX?.excludes != null)
        {
            queryList.Add("exclude", advancedQuery.queries.queryIncludesEX.excludes.ToList());
        }

        string tempadvancedQueryType = string.Empty;
        foreach (var item in queryList)
        {
            foreach (var subItem in item.Value)
            {
                List<string> tempTableNames = new List<string>();
                List<string> tempFieldNames = new List<string>();

                tempFieldNames.Add(JLPStaticProperties.PatientGlobalTable + ".Id");

                List<List<QueryDetailModel>> queryDetails = new List<List<QueryDetailModel>>();

                if (subItem.Conds is null)
                {
                    continue;
                }

                queryDetails.Add(subItem.Conds.ToList());

                #region 获取查询所有表名
                var tableNameWheres = from a in queryDetails
                                      from b in a
                                      select b.condition.Split('.').FirstOrDefault().ToUpper();
                var tableNameWhereValues = from a in queryDetails
                                           from b in a
                                           where b.fieldType == FieldValueType.Field
                                           select b.condValue.Split('.').FirstOrDefault().ToUpper();
                var whereFields = from a in queryDetails
                                  from b in a
                                  select b.condition.ToUpper();

                var whereFieldTypes = (from a in orclJlpContext.JTabledicts
                                       join b in orclJlpContext.JTablefielddicts on a.Code equals b.Tablecode into tablefields
                                       from b in tablefields
                                       where a.Activeflag == "1" && whereFields.Contains(a.Name + "." + b.Name)
                                       select new { field = a.Name + "." + b.Name, type = b.Type }).Distinct().ToList();
                queryDetails.ForEach(x =>
                {
                    x.ForEach(y =>
                    {
                        if (y.fieldType != FieldValueType.Field)
                        {
                            y.fieldType = (whereFieldTypes.FirstOrDefault(m => m.field == y.condition.ToUpper())?.type.toFieldValueType()) ?? FieldValueType.String;
                        }
                    });
                    if (x.LastOrDefault() != null)
                    {
                        x.LastOrDefault().combinator = "";
                    }
                });

                var SqlWhere = queryDetails.Select(x => x.ToArray().ToStringEx(" ")).ToArray().ToStringEx(" and ");
                tempTableNames.AddRange(tableNameWheres);
                tempTableNames.AddRange(tableNameWhereValues);
                tempTableNames = tempTableNames.Distinct().ToList();
                tempTableNames.RemoveAll(x => x.ToUpper() == JLPStaticProperties.PatientGlobalTable);
                #endregion

                #region 获取查询结果和数量
                tempFieldNames = tempFieldNames.Distinct().ToList();
                string tableWhere = tempTableNames.ToArray().ToTableStringEx();

                string TempSql = $"{subItem.leftqueto}select distinct {tempFieldNames.ToArray().ToStringEx(",")} from {tableWhere} {(string.IsNullOrEmpty(SqlWhere) ? "" : " where " + SqlWhere)} {subItem.rightqueto}";

                if (item.Key == "include")
                {
                    if (includeSql.Count != 0)
                    {
                        includeSql.Add(tempadvancedQueryType);
                    }
                    includeSql.Add(TempSql);
                }
                else if (item.Key == "exclude")
                {
                    if (excludeSql.Count != 0)
                    {
                        excludeSql.Add(tempadvancedQueryType);
                    }
                    excludeSql.Add(TempSql);
                }

                tempadvancedQueryType = subItem.setCombinator.ToString();
            }
            #endregion
        }

        //增加隐藏主键
        fieldNames.Add(JLPStaticProperties.PatientGlobalTable + ".Id as hide_key");

        #region 组装SQL
        StringBuilder includeStrBuilder = new StringBuilder();
        includeSql.ForEach(x =>
        {
            includeStrBuilder.AppendLine(x + " ");
        });

        StringBuilder excludeStrBuilder = new StringBuilder();
        excludeSql.ForEach(x =>
        {
            excludeStrBuilder.AppendLine(x + " ");
        });
        string tempSql = string.Empty;

        if (includeStrBuilder.Length > 0)
        {
            tempSql = $"select Id from ({includeStrBuilder.ToString()})";
            if (excludeStrBuilder.Length > 0)
            {
                tempSql += $" minus ({excludeStrBuilder.ToString()})";
            }
        }
        if (fieldNames.Where(x => x.ToUpper().Contains(".BIRTHDAY")).Any())
        {
            var tempFields = fieldNames.Where(x => x.ToUpper().Contains(".BIRTHDAY")).ToList();
            foreach (var item in tempFields)
            {
                fieldNames[fieldNames.IndexOf(item)] = $"to_char({item},'yyyy/mm/dd') as birthday";
            }
        }
        string finalSelectFields = fieldNames.ToArray().ToStringEx();
        string finalTables = tableNames.ToArray().ToTableStringEx(advancedQueryWhere: tempSql);
        string tableKeys = showFieldDetails.Select(x => x.tables.Name).Distinct().ToList().ToTableKeys(out Type tableKeyType);

        string tempSqlWhere = string.Empty;
        if (tempSqlWhere.Length == 0)
        {
            tempSqlWhere = $" where D_PATIENTGLOBAL.jlactiveflag='1'";
        }
        else
        {
            tempSqlWhere += $" and D_PATIENTGLOBAL.jlactiveflag='1'";
        }

        if (advancedQuery.patientGlobalId > 0)
        {
            tempSqlWhere += $" and D_PATIENTGLOBAL.Id={advancedQuery.patientGlobalId}";
        }

        if (SqlWhereMain.Length > 0)
        {
            tempSqlWhere += $" and {SqlWhereMain}";
        }

        tempSqlWhere += $" and {mainTable.Name}.Id is not null";

        #endregion

        #region 组装返回值
        sortStr = $"{JLPStaticProperties.PatientGlobalTable}__Id";

        string Sql = $"select distinct {tableKeys} from {finalTables} {tempSqlWhere}";
        string SqlCount = DBHelperBase.getCountSql(BarryCommon.DataBaseType.Oracle, Sql);
        LoggerHelper.Info(Sql);
        LoggerHelper.Info(SqlCount);
        if (isTotals)
        {
            dynamic count = orclJlpContext.Database.DynamicSqlQueryMethod(SqlCount, new object[] { }).ToListAsync().Result.FirstOrDefault();
            hashtable.Add("response", responseModel);
            hashtable.Add("totals", count.num);
        }
        else
        {
            string SqlKeys = DBHelperBase.getPageSql(BarryCommon.DataBaseType.Oracle, Sql, pageSize, pageNum, sortStr);
            LoggerHelper.Info(SqlKeys);
            var dataGrid = orclJlpContext.Database.Connection.QueryMutiRecords(new string[] { SqlCount, SqlKeys }, new Type[] { null, tableKeyType });
            dynamic count = dataGrid[0].First().NUM;
            var keysData = dataGrid[1].ToArray();
            hashtable.Add("response", responseModel);
            hashtable.Add("totals", count);

            string keyIdWhere = keysData.GetKeysWhere();
            var retData = new object[] { };
            if (keyIdWhere.Length > 0)
            {
                string SqlData = $"select distinct {finalSelectFields} from {tableNames.ToArray().ToTableStringEx()} {tempSqlWhere} and {keyIdWhere} order by {JLPStaticProperties.PatientGlobalTable}.Id";
                retData = orclJlpContext.Database.DynamicSqlQueryMethod(SqlData, new object[] { }).ToListAsync().Result.ToArray();

                var rights = ActionContext.GetUserRight();
                if (rights is null || !rights.Any(x => x.Id == 4))
                {
                    var sensitivewords = orclJlpContext.JSensitivewords.Where(x => x.Activeflag == JLPStaticProperties.ActiveFlag).Select(x => x.Name).ToArray().Select(x => x.ToLower()).ToArray();
                    if (sensitivewords.Count() > 0)
                    {
                        retData = retData.Sensitiveword(sensitivewords);
                    }
                }
            }

            hashtable.Add("columns", columns);
            hashtable.Add("content", retData);
        }
        #endregion

		return "api/AdvanceQuery";
	}
}
