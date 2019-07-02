package com.callan.service.provider.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.callan.service.provider.config.HanyuUtil;
import com.callan.service.provider.config.JLPConts;
import com.callan.service.provider.config.JLPLog;
import com.callan.service.provider.config.ObjectUtil;
import com.callan.service.provider.config.ThreadPoolConfig;
import com.callan.service.provider.pojo.base.BaseResponse;
import com.callan.service.provider.pojo.queryField.QueryFieldModel;
import com.callan.service.provider.service.IJLpService;
import com.callan.service.provider.service.IJUserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.sourceforge.pinyin4j.PinyinHelper;

@RestController
@Api(description = "获取可查询字段")
public class QueryFieldController {
	
	@Autowired
	private IJUserService userService;
	
	@Autowired
	private IJLpService jlpService;
	
	@ApiOperation(value = "获取可查询字段")
	@RequestMapping(value = "/api/QueryField/{Id}", method = { RequestMethod.GET })
    public String Get(@PathVariable Long Id, HttpServletRequest request, HttpServletResponse response)
    {
		JLPLog log = ThreadPoolConfig.getBaseContext();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String authorization = request.getHeader("Authorization") == null ? "" : request.getHeader("Authorization");
		Long userId = userService.getIdByToken(authorization);
		if (userId == null) {
			userId = 0L;
		}

		String sql = "SELECT Filter1.SORTNO1         AS SORTNO," + 
				"       Filter1.ID1             AS ID," + 
				"       Filter1.CODE1           AS CODE," + 
				"       Filter1.NAME1           AS NAME," + 
				"       Filter1.DESCRIPTION1    AS DESCRIPTION," + 
				"       Filter1.ACTIVEFLAG      AS ACTIVEFLAG," + 
				"       Filter1.ID2             AS ID1," + 
				"       Filter1.TABLECODE       AS TABLECODE," + 
				"       Filter1.CODE2           AS CODE1," + 
				"       Filter1.NAME2           AS NAME1," + 
				"       Filter1.TYPE            AS TYPE," + 
				"       Filter1.DESCRIPTION2    AS DESCRIPTION1," + 
				"       Filter1.SORTNO2         AS SORTNO1," + 
				"       Filter1.CONTROLTYPECODE AS CONTROLTYPECODE," + 
				"       Filter1.NULLABLE        AS NULLABLE," + 
				"       Filter1.DICTNAME        AS DICTNAME," + 
				"       Filter1.QUERYFLAG       AS QUERYFLAG," + 
				"       Filter1.SHOWFLAG        AS SHOWFLAG," + 
				"       Filter1.SORTFLAG        AS SORTFLAG," + 
				"       Extent3.CODE            AS CODE2," + 
				"       Extent3.NAME            AS NAME2," + 
				"       Extent3.ACTIVEFLAG      AS ACTIVEFLAG1" + 
				"  FROM (SELECT Extent1.ID              AS ID1," + 
				"               Extent1.CODE            AS CODE1," + 
				"               Extent1.NAME            AS NAME1," + 
				"               Extent1.DESCRIPTION     AS DESCRIPTION1," + 
				"               Extent1.SORTNO          AS SORTNO1," + 
				"               Extent1.ACTIVEFLAG      AS ACTIVEFLAG," + 
				"               Extent2.ID              AS ID2," + 
				"               Extent2.TABLECODE       AS TABLECODE," + 
				"               Extent2.CODE            AS CODE2," + 
				"               Extent2.NAME            AS NAME2," + 
				"               Extent2.TYPE            AS TYPE," + 
				"               Extent2.DESCRIPTION     AS DESCRIPTION2," + 
				"               Extent2.SORTNO          AS SORTNO2," + 
				"               Extent2.CONTROLTYPECODE AS CONTROLTYPECODE," + 
				"               Extent2.NULLABLE        AS NULLABLE," + 
				"               Extent2.DICTNAME        AS DICTNAME," + 
				"               Extent2.QUERYFLAG       AS QUERYFLAG," + 
				"               Extent2.SHOWFLAG        AS SHOWFLAG," + 
				"               Extent2.SORTFLAG        AS SORTFLAG" + 
				"          FROM JLP.J_TABLEDICT Extent1" + 
				"         INNER JOIN JLP.J_TABLEFIELDDICT Extent2" + 
				"            ON Extent1.CODE = Extent2.TABLECODE" + 
				"         WHERE (('"+JLPConts.ActiveFlag+"' = Extent1.ACTIVEFLAG)"
						+ " AND ('"+JLPConts.QueryFieldFlag+"' = Extent2.QUERYFLAG))) Filter1" + 
				" INNER JOIN JLP.J_CONTROLTYPEDICT Extent3" + 
				"    ON Filter1.CONTROLTYPECODE =" + 
				"       Extent3.CODE WHERE('1' = Extent3.ACTIVEFLAG)" + 
				" ORDER BY Filter1.SORTNO1 ASC, Filter1.SORTNO2 ASC " ;
		
		List<Map<String,Object>> fields = jlpService.queryForSQL(sql, new Object[] {});
		
        List<QueryFieldModel> fieldBaseInfos = new ArrayList<>();
        for(Map<String,Object> obj : fields) {
        	QueryFieldModel model = new QueryFieldModel();
        	model.setCode(ObjectUtil.objToString(obj.get("CODE1")));
        	HanyuUtil hanyu = new HanyuUtil();
        	String strPinyin = hanyu.getStringPinYin(ObjectUtil.objToString(obj.get("DESCRIPTION1")));
        	model.setKeyWord(strPinyin);
        	model.setLabel(ObjectUtil.objToString(obj.get("DESCRIPTION1")));
        	model.setValue(ObjectUtil.objToString(obj.get("NAME"))+ "." + ObjectUtil.objToString(obj.get("NAME1")));
        	model.setIsTag(false);
        	fieldBaseInfos.add(model);
        }
        
        
        String typeDictSql = "SELECT Project2.C1           AS C1," + 
        		"       Project2.NAME         AS NAME," + 
        		"       Project2.C2           AS C2," + 
        		"       Project2.CODE         AS CODE," + 
        		"       Project2.NAME1        AS NAME1," + 
        		"       Project2.VALUE        AS VALUE," + 
        		"       Project2.TYPE         AS TYPE," + 
        		"       Project2.JLACTIVEFLAG AS JLACTIVEFLAG" + 
        		"  FROM (SELECT Distinct1.NAME AS NAME," + 
        		"               1 AS C1," + 
        		"               Filter3.CODE1 AS CODE," + 
        		"               Filter3.NAME1 AS NAME1," + 
        		"               Filter3.VALUE AS VALUE," + 
        		"               Filter3.TYPE AS TYPE," + 
        		"               Filter3.JLACTIVEFLAG1 AS JLACTIVEFLAG," + 
        		"               CASE" + 
        		"                 WHEN (Filter3.CODE2 IS NULL) THEN" + 
        		"                  NULL" + 
        		"                 ELSE" + 
        		"                  1" + 
        		"               END AS C2" + 
        		"          FROM (SELECT DISTINCT Filter1.NAME AS NAME" + 
        		"                  FROM (SELECT Extent1.CODE             AS CODE," + 
        		"                               Extent1.NAME             AS NAME," + 
        		"                               Extent1.JLACTIVEFLAG     AS JLACTIVEFLAG2," + 
        		"                               Extent2.ID               AS ID," + 
        		"                               Extent2.FIELDTTYPECODE   AS FIELDTTYPECODE," + 
        		"                               Extent2.OPERATORTYPECODE AS OPERATORTYPECODE," + 
        		"                               Extent2.JLACTIVEFLAG     AS JLACTIVEFLAG3" + 
        		"                          FROM JLP.J_FIELDTYPEDICT Extent1" + 
        		"                         INNER JOIN JLP.J_FIELDTYPEWITHOPERATORS Extent2" + 
        		"                            ON Extent1.CODE = Extent2.FIELDTTYPECODE" + 
        		"                         WHERE (('1' = Extent1.JLACTIVEFLAG) AND" + 
        		"                               ('1' = Extent2.JLACTIVEFLAG))) Filter1" + 
        		"                 INNER JOIN JLP.J_OPERATORTYPEDICT Extent3" + 
        		"                    ON Filter1.OPERATORTYPECODE = Extent3.CODE" + 
        		"                 WHERE (('1' = Extent3.JLACTIVEFLAG) AND ('2' = Extent3.TYPE))) Distinct1" + 
        		"          LEFT OUTER JOIN (SELECT Extent4.CODE             AS CODE2," + 
        		"                                 Extent4.NAME             AS NAME2," + 
        		"                                 Extent4.JLACTIVEFLAG     AS JLACTIVEFLAG4," + 
        		"                                 Extent5.ID               AS ID," + 
        		"                                 Extent5.FIELDTTYPECODE   AS FIELDTTYPECODE," + 
        		"                                 Extent5.OPERATORTYPECODE AS OPERATORTYPECODE," + 
        		"                                 Extent5.JLACTIVEFLAG     AS JLACTIVEFLAG5," + 
        		"                                 Extent6.CODE             AS CODE1," + 
        		"                                 Extent6.NAME             AS NAME1," + 
        		"                                 Extent6.VALUE            AS VALUE," + 
        		"                                 Extent6.TYPE             AS TYPE," + 
        		"                                 Extent6.JLACTIVEFLAG     AS JLACTIVEFLAG1" + 
        		"                            FROM JLP.J_FIELDTYPEDICT Extent4" + 
        		"                           INNER JOIN JLP.J_FIELDTYPEWITHOPERATORS Extent5" + 
        		"                              ON Extent4.CODE = Extent5.FIELDTTYPECODE" + 
        		"                           INNER JOIN JLP.J_OPERATORTYPEDICT Extent6" + 
        		"                              ON Extent5.OPERATORTYPECODE = Extent6.CODE" + 
        		"                           WHERE (('1' = Extent4.JLACTIVEFLAG) AND" + 
        		"                                 ('1' = Extent5.JLACTIVEFLAG))) Filter3" + 
        		"            ON ('1' = Filter3.JLACTIVEFLAG1)" + 
        		"           AND ('2' = Filter3.TYPE)" + 
        		"           AND (Distinct1.NAME = Filter3.NAME2)) Project2" + 
        		" ORDER BY Project2.NAME                                 ASC," + 
        		"          Project2.C2                                   ASC  " + 
        		"" ;
        Map<String,Object> fieldRelations = new HashMap<>();
        Map<String,String> fieldType = new HashMap<>();
        Map<String,String> fieldTittle = new HashMap<>();
        
        List<Map<String,Object>> typeDict = jlpService.queryForSQL(typeDictSql, new Object[] {});
        
        
        for(Map<String,Object> obj : fields) {
        	
        	List<Map<String,String>> dictList = new ArrayList<Map<String,String>>();
            for(Map<String,Object> map : typeDict) {
            	if(ObjectUtil.objToString(obj.get("TYPE")).equals(ObjectUtil.objToString(map.get("NAME")))) {
            		HashMap<String,String> newMap = new HashMap<String,String>() 
	            		{
	            			{put("value",ObjectUtil.objToString(map.get("VALUE")));}
		            		{put("label",ObjectUtil.objToString(map.get("NAME1")));}
	            		};
	            	dictList.add(newMap);
            	}
            }
        	fieldRelations.put(ObjectUtil.objToString(obj.get("NAME"))+ "." + ObjectUtil.objToString(obj.get("NAME1")), dictList);
        	
            fieldType.put(ObjectUtil.objToString(obj.get("NAME"))+ "." + ObjectUtil.objToString(obj.get("NAME1")),
            		ObjectUtil.objToString(obj.get("NAME2")));
            fieldTittle.put(ObjectUtil.objToString(obj.get("NAME"))+ "." + ObjectUtil.objToString(obj.get("NAME1")), 
            		ObjectUtil.objToString(obj.get("DESCRIPTION1")));
        }
        Map<String,String> operatorTypeDict = new HashMap<>();
        for(Map<String,Object> map : typeDict) {
        	operatorTypeDict.put(ObjectUtil.objToString(map.get("VALUE")), ObjectUtil.objToString(map.get("NAME1")));
        }

        if (Id.longValue() > 0)
        {
            
            String tagProjectDictsSql = "SELECT Project2.C1          AS C1," + 
            		"       Project2.ID          AS ID," + 
            		"       Project2.NAME        AS NAME," + 
            		"       Project2.DESCRIPTION AS DESCRIPTION," + 
            		"       Project2.TAGTYPE     AS TAGTYPE," + 
            		"       Project2.VALUETYPE   AS VALUETYPE," + 
            		"       Project2.USERID      AS USERID," + 
            		"       Project2.PROJECTID   AS PROJECTID," + 
            		"       Project2.CREATEDATE  AS CREATEDATE," + 
            		"       Project2.ACTIVEFLAG  AS ACTIVEFLAG," + 
            		"       Project2.TAGATTACHED AS TAGATTACHED," + 
            		"       Project2.SHOWFLAG    AS SHOWFLAG," + 
            		"       Project2.TOTALNUM    AS TOTALNUM," + 
            		"       Project2.CURRENTNUM  AS CURRENTNUM," + 
            		"       Project2.C2          AS C2," + 
            		"       Project2.ID1         AS ID1," + 
            		"       Project2.TAGID       AS TAGID," + 
            		"       Project2.VALUETYPE1  AS VALUETYPE1," + 
            		"       Project2.VALUE       AS VALUE," + 
            		"       Project2.MINVALUE    AS MINVALUE," + 
            		"       Project2.MAXVALUE    AS MAXVALUE," + 
            		"       Project2.CREATEDATE1 AS CREATEDATE1," + 
            		"       Project2.ACTIVEFLAG1 AS ACTIVEFLAG1" + 
            		"  FROM (SELECT Distinct1.ID AS ID," + 
            		"               Distinct1.NAME AS NAME," + 
            		"               Distinct1.DESCRIPTION AS DESCRIPTION," + 
            		"               Distinct1.TAGTYPE AS TAGTYPE," + 
            		"               Distinct1.VALUETYPE AS VALUETYPE," + 
            		"               Distinct1.USERID AS USERID," + 
            		"               Distinct1.PROJECTID AS PROJECTID," + 
            		"               Distinct1.CREATEDATE AS CREATEDATE," + 
            		"               Distinct1.ACTIVEFLAG AS ACTIVEFLAG," + 
            		"               Distinct1.TAGATTACHED AS TAGATTACHED," + 
            		"               Distinct1.SHOWFLAG AS SHOWFLAG," + 
            		"               Distinct1.TOTALNUM AS TOTALNUM," + 
            		"               Distinct1.CURRENTNUM AS CURRENTNUM," + 
            		"               1 AS C1," + 
            		"               Join2.ID1 AS ID1," + 
            		"               Join2.TAGID AS TAGID," + 
            		"               Join2.VALUETYPE1 AS VALUETYPE1," + 
            		"               Join2.VALUE AS VALUE," + 
            		"               Join2.MINVALUE AS MINVALUE," + 
            		"               Join2.MAXVALUE AS MAXVALUE," + 
            		"               Join2.CREATEDATE1 AS CREATEDATE1," + 
            		"               Join2.ACTIVEFLAG1 AS ACTIVEFLAG1," + 
            		"               CASE" + 
            		"                 WHEN (Join2.ID2 IS NULL) THEN" + 
            		"                  NULL" + 
            		"                 ELSE" + 
            		"                  1" + 
            		"               END AS C2" + 
            		"          FROM (SELECT DISTINCT Extent1.ID          AS ID," + 
            		"                                Extent1.NAME        AS NAME," + 
            		"                                Extent1.DESCRIPTION AS DESCRIPTION," + 
            		"                                Extent1.TAGTYPE     AS TAGTYPE," + 
            		"                                Extent1.VALUETYPE   AS VALUETYPE," + 
            		"                                Extent1.USERID      AS USERID," + 
            		"                                Extent1.PROJECTID   AS PROJECTID," + 
            		"                                Extent1.CREATEDATE  AS CREATEDATE," + 
            		"                                Extent1.ACTIVEFLAG  AS ACTIVEFLAG," + 
            		"                                Extent1.TAGATTACHED AS TAGATTACHED," + 
            		"                                Extent1.SHOWFLAG    AS SHOWFLAG," + 
            		"                                Extent1.TOTALNUM    AS TOTALNUM," + 
            		"                                Extent1.CURRENTNUM  AS CURRENTNUM" + 
            		"                  FROM JLP.J_TAGDICT Extent1" + 
            		"                 INNER JOIN JLP.J_TAGVALUEDICT Extent2" + 
            		"                    ON Extent1.ID = Extent2.TAGID" + 
            		"                 WHERE (('1' = Extent1.ACTIVEFLAG) AND" + 
            		"                       ('1' = Extent2.ACTIVEFLAG) AND" + 
            		"                       (Extent1.PROJECTID = '"+Id+"') AND" + 
            		"                       (Extent1.USERID = '"+userId+"'))) Distinct1" + 
            		"          LEFT OUTER JOIN (SELECT Extent3.ID          AS ID2," + 
            		"                                 Extent3.NAME        AS NAME," + 
            		"                                 Extent3.DESCRIPTION AS DESCRIPTION," + 
            		"                                 Extent3.TAGTYPE     AS TAGTYPE," + 
            		"                                 Extent3.VALUETYPE   AS VALUETYPE2," + 
            		"                                 Extent3.USERID      AS USERID," + 
            		"                                 Extent3.PROJECTID   AS PROJECTID," + 
            		"                                 Extent3.CREATEDATE  AS CREATEDATE2," + 
            		"                                 Extent3.ACTIVEFLAG  AS ACTIVEFLAG2," + 
            		"                                 Extent3.TAGATTACHED AS TAGATTACHED," + 
            		"                                 Extent3.SHOWFLAG    AS SHOWFLAG," + 
            		"                                 Extent3.TOTALNUM    AS TOTALNUM," + 
            		"                                 Extent3.CURRENTNUM  AS CURRENTNUM," + 
            		"                                 Extent4.ID          AS ID1," + 
            		"                                 Extent4.TAGID       AS TAGID," + 
            		"                                 Extent4.VALUETYPE   AS VALUETYPE1," + 
            		"                                 Extent4.VALUE       AS VALUE," + 
            		"                                 Extent4.MINVALUE    AS MINVALUE," + 
            		"                                 Extent4.MAXVALUE    AS MAXVALUE," + 
            		"                                 Extent4.CREATEDATE  AS CREATEDATE1," + 
            		"                                 Extent4.ACTIVEFLAG  AS ACTIVEFLAG1" + 
            		"                            FROM JLP.J_TAGDICT Extent3" + 
            		"                           INNER JOIN JLP.J_TAGVALUEDICT Extent4" + 
            		"                              ON Extent3.ID = Extent4.TAGID) Join2" + 
            		"            ON ('1' = Join2.ACTIVEFLAG2)" + 
            		"           AND ('1' = Join2.ACTIVEFLAG1)" + 
            		"           AND (Join2.PROJECTID = '"+Id+"')" + 
            		"           AND (Join2.USERID = '"+userId+"' ) " + 
            		"           AND (Distinct1.ID = Join2.ID2)) Project2" + 
            		" ORDER BY Project2.ID          ASC," + 
            		"          Project2.NAME        ASC," + 
            		"          Project2.DESCRIPTION ASC," + 
            		"          Project2.TAGTYPE     ASC," + 
            		"          Project2.VALUETYPE   ASC," + 
            		"          Project2.USERID      ASC," + 
            		"          Project2.PROJECTID   ASC," + 
            		"          Project2.CREATEDATE  ASC," + 
            		"          Project2.ACTIVEFLAG  ASC," + 
            		"          Project2.TAGATTACHED ASC," + 
            		"          Project2.SHOWFLAG    ASC," + 
            		"          Project2.TOTALNUM    ASC," + 
            		"          Project2.CURRENTNUM  ASC," + 
            		"          Project2.C2          ASC" ;
            List<Map<String,Object>> tagProjectDicts = jlpService.queryForSQL(tagProjectDictsSql, new Object[] {});
            
            for(Map<String,Object> map : tagProjectDicts) {
            	QueryFieldModel model = new QueryFieldModel();
            	model.setCode("TAG_" + map.get("ID"));
            	HanyuUtil hanyu = new HanyuUtil();
            	String strPinyin = hanyu.getStringPinYin(ObjectUtil.objToString(map.get("NAME")));
            	model.setKeyWord(strPinyin);
            	model.setLabel(ObjectUtil.objToString(map.get("NAME")));
            	model.setValue("PROJECTTAGS.TAG_" + map.get("ID"));
            	model.setIsTag(true);
            	fieldBaseInfos.add(model);
            	
            	List<String[]> dictList = new ArrayList<String[]>();
                for(Map<String,Object> map1 : typeDict) {
                	String name = "1".equals(ObjectUtil.objToString(map.get("VALUETYPE"))) ? "VARCHAR2" : "NUMBER";
                	if(ObjectUtil.objToString(map.get("TYPE")).equals( name )) {
    	            	String[] strArr = new String[] {ObjectUtil.objToString(map1.get("NAME")),ObjectUtil.objToString(map1.get("VALUE"))};
    	            	dictList.add(strArr);
                	}
                }
            	fieldRelations.put("PROJECTTAGS.TAG_" + map.get("ID"), dictList);
            	fieldType.put("PROJECTTAGS.TAG_" + map.get("ID"), "1".equals(ObjectUtil.objToString(map.get("VALUETYPE"))) ? "string" : "number");
                fieldTittle.put("PROJECTTAGS.TAG_" + map.get("ID"), ObjectUtil.objToString(map.get("NAME")));
            }
        }
        BaseResponse baseResponse = new BaseResponse();
        resultMap.put("response", baseResponse);
        resultMap.put("fieldInfos", fieldBaseInfos);
        resultMap.put("fieldRelations", fieldRelations);
        resultMap.put("fieldTypes", fieldType);
        resultMap.put("fieldTittles", fieldTittle);
        resultMap.put("operatorTypes", operatorTypeDict);

        String json = JSONObject.toJSONString(resultMap);
		log.info("response--> " + json);
		return json;
    }
}
