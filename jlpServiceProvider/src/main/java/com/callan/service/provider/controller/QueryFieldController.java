//package com.callan.service.provider.controller;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.callan.service.provider.config.JLPLog;
//import com.callan.service.provider.config.ThreadPoolConfig;
//import com.callan.service.provider.pojo.base.BaseResponse;
//import com.callan.service.provider.pojo.queryField.QueryFieldModel;
//import com.callan.service.provider.service.IJUserService;
//
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//
//@RestController
//@Api(description = "获取可查询字段")
//public class QueryFieldController {
//	
//	@Autowired
//	private IJUserService userService;
//	
//	@ApiOperation(value = "获取可查询字段")
//	@RequestMapping(value = "/api/QueryField/{Id}", method = { RequestMethod.GET })
//    public String Get(Long Id, HttpServletRequest request, HttpServletResponse response)
//    {
//		JLPLog log = ThreadPoolConfig.getBaseContext();
//		Map<String, Object> resultMap = new HashMap<String, Object>();
//		String authorization = request.getHeader("Authorization") == null ? "" : request.getHeader("Authorization");
//		Long userId = userService.getIdByToken(authorization);
//		if (userId == null) {
//			userId = 0L;
//		}
//
////        var fields = (from table in orclJlpContext.JTabledicts
////                      join field in orclJlpContext.JTablefielddicts on table.Code
////                      	equals field.Tablecode into fieldInfos
////                      from field in fieldInfos
////                      join controlDict in orclJlpContext.JControltypedicts on field.Controltypecode
////                      	equals controlDict.Code into ControlTypes
////                      from controlDict in ControlTypes
////                      where table.Activeflag == JLPStaticProperties.ActiveFlag 
////                      	&& field.Queryflag == JLPStaticProperties.QueryFieldFlag 
////                      	&& controlDict.Activeflag == JLPStaticProperties.ActiveFlag
////                      orderby table.Sortno, field.Sortno
////                      select new { table, field, controlDict }).ToList();
//        
//        List<QueryFieldModel> fieldBaseInfos = new ArrayList<>();
//        for(Object obj : fields) {
//        	QueryFieldModel model = new QueryFieldModel();
//        	model.setCode(code);
//        	model.getKeyWord();
//        	model.getLabel();
//        	model.setValue(value);
//        	model.setIsTag(false);
//        	fieldBaseInfos.add(model);
//        }
//            (from x in fields
//             select new QueryFieldModel()
//             {
//                 code = x.field.Code,
//                 keyWord = SpellCodeHelper.GetFirstPYLetter(x.field.Description),
//                 label = x.field.Description,
//                 value = x.table.Name + "." + x.field.Name,
//                 isTag = false
//             }).ToList();
//        DictionaryObj fieldRelations = new DictionaryObj();
//        var typeDict = (from typeD in orclJlpContext.JFieldtypedicts
//                        join typeToOper in orclJlpContext.JFieldtypewithoperators on typeD.Code equals typeToOper.Fieldttypecode into typeToOperInfos
//                        from typeToOper in typeToOperInfos
//                        join operdict in orclJlpContext.JOperatortypedicts on typeToOper.Operatortypecode equals operdict.Code into operdictInfo
//                        from operdict in operdictInfo
//                        where typeD.Jlactiveflag == JLPStaticProperties.ActiveFlag && typeToOper.Jlactiveflag == JLPStaticProperties.ActiveFlag && operdict.Jlactiveflag == JLPStaticProperties.ActiveFlag && operdict.Type == "2"
//                        group operdict by typeD.Name into typeWithOperInfos
//                        select typeWithOperInfos).ToList();
//
//        DictionaryObj fieldType = new DictionaryObj();
//        DictionaryObj fieldTittle = new DictionaryObj();
//        fields.Distinct().ToList().ForEach(x =>
//        {
//            fieldRelations.Add(x.table.Name + "." + x.field.Name, typeDict.FirstOrDefault(y => y.Key == x.field.Type).Select(y => new { label = y.Name, value = y.Value })?.ToArray());
//            fieldType.Add(x.table.Name + "." + x.field.Name, x.controlDict.Name);
//            fieldTittle.Add(x.table.Name + "." + x.field.Name, x.field.Description);
//        });
//        var operatorTypes = (from a in typeDict
//                             from b in a
//                             select new { b.Value, b.Name }).Distinct().ToList();
//        DictionaryObj operatorTypeDict = new DictionaryObj();
//        operatorTypes.ForEach(x =>
//        {
//            operatorTypeDict.Add(x.Value, x.Name);
//        });
//
//        if (Id > 0)
//        {
//            var tagProjectDicts = (from tagdict in orclJlpContext.JTagdicts
//                                   join tagvaluedict in orclJlpContext.JTagvaluedicts on tagdict.Id equals tagvaluedict.Tagid into taginfos
//                                   from tagvaluedict in taginfos
//                                   where tagdict.Activeflag == JLPStaticProperties.ActiveFlag && tagvaluedict.Activeflag == JLPStaticProperties.ActiveFlag && tagdict.Projectid == Id && tagdict.Userid == userId
//                                   group tagvaluedict by tagdict into taggroup
//                                   select taggroup).ToList();
//            fieldBaseInfos.AddRange(tagProjectDicts.Select(x => new QueryFieldModel()
//            {
//                code = "TAG_" + x.Key.Id.ToString(),
//                keyWord = SpellCodeHelper.GetFirstPYLetter(x.Key.Name),
//                label = x.Key.Name,
//                value = "PROJECTTAGS.TAG_" + x.Key.Id.ToString(),
//                isTag = true
//            }));
//            tagProjectDicts.ForEach(x =>
//            {
//                fieldRelations.Add("PROJECTTAGS.TAG_" + x.Key.Id.ToString(), typeDict.FirstOrDefault(y => y.Key == (x.Key.Valuetype == "1" ? "VARCHAR2" : "NUMBER")).Select(y => new { label = y.Name, value = y.Value })?.ToArray());
//                fieldType.Add("PROJECTTAGS.TAG_" + x.Key.Id.ToString(), (x.Key.Valuetype == "1" ? "string" : "number"));
//                fieldTittle.Add("PROJECTTAGS.TAG_" + x.Key.Id.ToString(), x.Key.Name);
//            });
//        }
//        BaseResponse baseResponse = new BaseResponse();
//        resultMap.put("response", baseResponse);
//        resultMap.put("fieldInfos", fieldBaseInfos);
//        resultMap.put("fieldRelations", fieldRelations);
//        resultMap.put("fieldTypes", fieldType);
//        resultMap.put("fieldTittles", fieldTittle);
//        resultMap.put("operatorTypes", operatorTypeDict);
//
//        return Ok(keyValues);
//    }
//}
