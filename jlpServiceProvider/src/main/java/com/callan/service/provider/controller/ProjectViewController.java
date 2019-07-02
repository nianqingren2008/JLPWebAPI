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
import com.callan.service.provider.config.JLPLog;
import com.callan.service.provider.config.ObjectUtil;
import com.callan.service.provider.config.ThreadPoolConfig;
import com.callan.service.provider.pojo.base.BaseResponse;
import com.callan.service.provider.pojo.db.JShowDetailView;
import com.callan.service.provider.service.IJLpService;
import com.callan.service.provider.service.IJShowDetailViewService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(description = " 获取课题视图列表信息")
public class ProjectViewController {
	
	@Autowired
	private IJLpService jlpService;
	
	@Autowired
	private IJShowDetailViewService showDetailViewService;
	
	@ApiOperation(value = " 获取课题视图列表信息")
	@RequestMapping(value = "/api/ProjectView/{Id}", method = { RequestMethod.GET })
        public String Gets(@PathVariable Long Id, HttpServletRequest request, HttpServletResponse response)
        {
		JLPLog log = ThreadPoolConfig.getBaseContext();
		Map<String, Object> resultMap = new HashMap<String, Object>();
            if (Id == null || Id < 1)
            {
            	BaseResponse baseResponse = new BaseResponse();
    			response.setStatus(404);
    			baseResponse.setCode("404");
    			baseResponse.setText("课题编号错误");
    			resultMap.put("response", baseResponse);
    			return JSONObject.toJSONString(resultMap);
            }

            StringBuffer fieldsSql  = new StringBuffer("SELECT UnionAll1.ACTIVEFLAG AS C1," + 
            		"       UnionAll1.CREATEDATE AS C2," + 
            		"       UnionAll1.VIEWID     AS C3," + 
            		"       UnionAll1.ID         AS C4," + 
            		"       UnionAll1.ID1        AS C5," + 
            		"       UnionAll1.ID2        AS C6," + 
            		"       UnionAll1.NAME       AS C7," + 
            		"       UnionAll1.CODE       AS C8," + 
            		"       UnionAll1.C1         AS C9," + 
            		"       UnionAll1.ID3        AS C10," + 
            		"       UnionAll1.ID4        AS C11," + 
            		"       UnionAll1.FIELDTITLE AS C12," + 
            		"       UnionAll1.ID5        AS C13," + 
            		"       UnionAll1.C2         AS C14," + 
            		"       UnionAll1.C3         AS C15," + 
            		"       UnionAll1.C4         AS C16" + 
            		"  FROM (SELECT CASE" + 
            		"                 WHEN (Filter3.ID1 IS NULL) THEN" + 
            		"                  NULL" + 
            		"                 ELSE" + 
            		"                  1" + 
            		"               END AS C1," + 
            		"               Distinct1.ACTIVEFLAG AS ACTIVEFLAG," + 
            		"               Distinct1.CREATEDATE AS CREATEDATE," + 
            		"               Distinct1.VIEWID AS VIEWID," + 
            		"               Distinct1.ID AS ID," + 
            		"               Distinct1.ID AS ID1," + 
            		"               Distinct1.ID AS ID2," + 
            		"               Distinct1.NAME AS NAME," + 
            		"               Distinct1.CODE AS CODE," + 
            		"               Filter3.ID1 AS ID3," + 
            		"               Filter3.ID2 AS ID4," + 
            		"               Filter3.FIELDTITLE AS FIELDTITLE," + 
            		"               Filter3.ID2 AS ID5," + 
            		"               NULL AS C2," + 
            		"               NULL AS C3," + 
            		"               NULL AS C4" + 
            		"          FROM (SELECT DISTINCT Filter1.ID3         AS ID," + 
            		"                                Filter1.CODE1       AS CODE," + 
            		"                                Filter1.NAME1       AS NAME," + 
            		"                                Filter1.ACTIVEFLAG1 AS ACTIVEFLAG," + 
            		"                                Filter1.CREATEDATE  AS CREATEDATE," + 
            		"                                Filter1.VIEWID1     AS VIEWID" + 
            		"                  FROM (SELECT Extent1.ID              AS ID3," + 
            		"                               Extent1.CODE            AS CODE1," + 
            		"                               Extent1.NAME            AS NAME1," + 
            		"                               Extent1.ACTIVEFLAG      AS ACTIVEFLAG1," + 
            		"                               Extent1.CREATEDATE      AS CREATEDATE," + 
            		"                               Extent1.VIEWID          AS VIEWID1," + 
            		"                               Extent2.ID              AS ID4," + 
            		"                               Extent2.CODE            AS CODE2," + 
            		"                               Extent2.NAME            AS NAME2," + 
            		"                               Extent2.ACTIVEFLAG      AS ACTIVEFLAG2," + 
            		"                               Extent2.ACCESSCODE      AS ACCESSCODE," + 
            		"                               Extent2.MAINTABLECODE   AS MAINTABLECODE," + 
            		"                               Extent3.ID              AS ID5," + 
            		"                               Extent3.VIEWID          AS VIEWID2," + 
            		"                               Extent3.FIELDID         AS FIELDID," + 
            		"                               Extent3.FIELDTITLE      AS FIELDTITLE," + 
            		"                               Extent3.ACTIVEFLAG      AS ACTIVEFLAG3," + 
            		"                               Extent3.SORTNO          AS SORTNO1," + 
            		"                               Extent3.DEFAULTFLAG     AS DEFAULTFLAG," + 
            		"                               Extent3.FIELDNAME       AS FIELDNAME," + 
            		"                               Extent4.ID              AS ID6," + 
            		"                               Extent4.TABLECODE       AS TABLECODE," + 
            		"                               Extent4.CODE            AS CODE3," + 
            		"                               Extent4.NAME            AS NAME3," + 
            		"                               Extent4.TYPE            AS TYPE," + 
            		"                               Extent4.DESCRIPTION     AS DESCRIPTION," + 
            		"                               Extent4.SORTNO          AS SORTNO2," + 
            		"                               Extent4.CONTROLTYPECODE AS CONTROLTYPECODE," + 
            		"                               Extent4.NULLABLE        AS NULLABLE," + 
            		"                               Extent4.DICTNAME        AS DICTNAME," + 
            		"                               Extent4.QUERYFLAG       AS QUERYFLAG," + 
            		"                               Extent4.SHOWFLAG        AS SHOWFLAG," + 
            		"                               Extent4.SORTFLAG        AS SORTFLAG" + 
            		"                          FROM JLP.J_TABLECLASSDICT Extent1" + 
            		"                         INNER JOIN JLP.J_SHOWVIEW Extent2" + 
            		"                            ON (CAST(Extent1.VIEWID AS number(19, 0))) =" + 
            		"                               Extent2.ID" + 
            		"                         INNER JOIN JLP.J_SHOWDETAILVIEW Extent3" + 
            		"                            ON Extent2.ID = Extent3.VIEWID" + 
            		"                         INNER JOIN JLP.J_TABLEFIELDDICT Extent4" + 
            		"                            ON Extent3.FIELDID = Extent4.ID" + 
            		"                         WHERE (('1' = Extent1.ACTIVEFLAG) AND" + 
            		"                               ('1' = Extent2.ACTIVEFLAG) AND" + 
            		"                               ('1' = Extent3.ACTIVEFLAG) AND" + 
            		"                               ('1' = Extent4.SHOWFLAG))) Filter1" + 
            		"                 INNER JOIN JLP.J_TABLEDICT Extent5" + 
            		"                    ON Filter1.TABLECODE = Extent5.CODE" + 
            		"                 WHERE ('1' = Extent5.ACTIVEFLAG)) Distinct1" + 
            		"          LEFT OUTER JOIN (SELECT Extent6.ID              AS ID1," + 
            		"                                 Extent6.CODE            AS CODE4," + 
            		"                                 Extent6.NAME            AS NAME4," + 
            		"                                 Extent6.ACTIVEFLAG      AS ACTIVEFLAG4," + 
            		"                                 Extent6.CREATEDATE      AS CREATEDATE," + 
            		"                                 Extent6.VIEWID          AS VIEWID3," + 
            		"                                 Extent7.ID              AS ID7," + 
            		"                                 Extent7.CODE            AS CODE5," + 
            		"                                 Extent7.NAME            AS NAME5," + 
            		"                                 Extent7.ACTIVEFLAG      AS ACTIVEFLAG5," + 
            		"                                 Extent7.ACCESSCODE      AS ACCESSCODE," + 
            		"                                 Extent7.MAINTABLECODE   AS MAINTABLECODE," + 
            		"                                 Extent8.ID              AS ID2," + 
            		"                                 Extent8.VIEWID          AS VIEWID4," + 
            		"                                 Extent8.FIELDID         AS FIELDID," + 
            		"                                 Extent8.FIELDTITLE      AS FIELDTITLE," + 
            		"                                 Extent8.ACTIVEFLAG      AS ACTIVEFLAG6," + 
            		"                                 Extent8.SORTNO          AS SORTNO3," + 
            		"                                 Extent8.DEFAULTFLAG     AS DEFAULTFLAG," + 
            		"                                 Extent8.FIELDNAME       AS FIELDNAME," + 
            		"                                 Extent9.ID              AS ID8," + 
            		"                                 Extent9.TABLECODE       AS TABLECODE," + 
            		"                                 Extent9.CODE            AS CODE6," + 
            		"                                 Extent9.NAME            AS NAME6," + 
            		"                                 Extent9.TYPE            AS TYPE," + 
            		"                                 Extent9.DESCRIPTION     AS DESCRIPTION1," + 
            		"                                 Extent9.SORTNO          AS SORTNO4," + 
            		"                                 Extent9.CONTROLTYPECODE AS CONTROLTYPECODE," + 
            		"                                 Extent9.NULLABLE        AS NULLABLE," + 
            		"                                 Extent9.DICTNAME        AS DICTNAME," + 
            		"                                 Extent9.QUERYFLAG       AS QUERYFLAG," + 
            		"                                 Extent9.SHOWFLAG        AS SHOWFLAG," + 
            		"                                 Extent9.SORTFLAG        AS SORTFLAG," + 
            		"                                 Extent10.ID             AS ID9," + 
            		"                                 Extent10.CODE           AS CODE7," + 
            		"                                 Extent10.NAME           AS NAME7," + 
            		"                                 Extent10.DESCRIPTION    AS DESCRIPTION2," + 
            		"                                 Extent10.SORTNO         AS SORTNO5," + 
            		"                                 Extent10.ACTIVEFLAG     AS ACTIVEFLAG7" + 
            		"                            FROM JLP.J_TABLECLASSDICT Extent6" + 
            		"                           INNER JOIN JLP.J_SHOWVIEW Extent7" + 
            		"                              ON (CAST(Extent6.VIEWID AS number(19, 0))) =" + 
            		"                                 Extent7.ID" + 
            		"                           INNER JOIN JLP.J_SHOWDETAILVIEW Extent8" + 
            		"                              ON Extent7.ID = Extent8.VIEWID" + 
            		"                           INNER JOIN JLP.J_TABLEFIELDDICT Extent9" + 
            		"                              ON Extent8.FIELDID = Extent9.ID" + 
            		"                           INNER JOIN JLP.J_TABLEDICT Extent10" + 
            		"                              ON Extent9.TABLECODE = Extent10.CODE" + 
            		"                           WHERE (('1' = Extent6.ACTIVEFLAG) AND" + 
            		"                                 ('1' = Extent7.ACTIVEFLAG) AND" + 
            		"                                 ('1' = Extent8.ACTIVEFLAG) AND" + 
            		"                                 ('1' = Extent9.SHOWFLAG))) Filter3" + 
            		"            ON ('1' = Filter3.ACTIVEFLAG7)" + 
            		"           AND (Distinct1.ID = Filter3.ID1)" + 
            		"        UNION ALL" + 
            		"        SELECT 2 AS C1," + 
            		"               Distinct2.ACTIVEFLAG AS ACTIVEFLAG," + 
            		"               Distinct2.CREATEDATE AS CREATEDATE," + 
            		"               Distinct2.VIEWID AS VIEWID," + 
            		"               Distinct2.ID AS ID," + 
            		"               Distinct2.ID AS ID1," + 
            		"               Distinct2.ID AS ID2," + 
            		"               Distinct2.NAME AS NAME," + 
            		"               Distinct2.CODE AS CODE," + 
            		"               NULL AS C2," + 
            		"               NULL AS C3," + 
            		"               NULL AS C4," + 
            		"               NULL AS C5," + 
            		"               Join17.ID10 AS ID3," + 
            		"               Join17.ID11 AS ID4," + 
            		"               CASE" + 
            		"                 WHEN ('1' = Join17.DEFAULTFLAG) THEN" + 
            		"                  1" + 
            		"                 WHEN ('1' <> Join17.DEFAULTFLAG) THEN" + 
            		"                  0" + 
            		"               END AS C6" + 
            		"          FROM (SELECT DISTINCT Filter4.ID12        AS ID," + 
            		"                                Filter4.CODE8       AS CODE," + 
            		"                                Filter4.NAME8       AS NAME," + 
            		"                                Filter4.ACTIVEFLAG8 AS ACTIVEFLAG," + 
            		"                                Filter4.CREATEDATE  AS CREATEDATE," + 
            		"                                Filter4.VIEWID5     AS VIEWID" + 
            		"                  FROM (SELECT Extent11.ID              AS ID12," + 
            		"                               Extent11.CODE            AS CODE8," + 
            		"                               Extent11.NAME            AS NAME8," + 
            		"                               Extent11.ACTIVEFLAG      AS ACTIVEFLAG8," + 
            		"                               Extent11.CREATEDATE      AS CREATEDATE," + 
            		"                               Extent11.VIEWID          AS VIEWID5," + 
            		"                               Extent12.ID              AS ID13," + 
            		"                               Extent12.CODE            AS CODE9," + 
            		"                               Extent12.NAME            AS NAME9," + 
            		"                               Extent12.ACTIVEFLAG      AS ACTIVEFLAG9," + 
            		"                               Extent12.ACCESSCODE      AS ACCESSCODE," + 
            		"                               Extent12.MAINTABLECODE   AS MAINTABLECODE," + 
            		"                               Extent13.ID              AS ID14," + 
            		"                               Extent13.VIEWID          AS VIEWID6," + 
            		"                               Extent13.FIELDID         AS FIELDID," + 
            		"                               Extent13.FIELDTITLE      AS FIELDTITLE," + 
            		"                               Extent13.ACTIVEFLAG      AS ACTIVEFLAG10," + 
            		"                               Extent13.SORTNO          AS SORTNO6," + 
            		"                               Extent13.DEFAULTFLAG     AS DEFAULTFLAG," + 
            		"                               Extent13.FIELDNAME       AS FIELDNAME," + 
            		"                               Extent14.ID              AS ID15," + 
            		"                               Extent14.TABLECODE       AS TABLECODE," + 
            		"                               Extent14.CODE            AS CODE10," + 
            		"                               Extent14.NAME            AS NAME10," + 
            		"                               Extent14.TYPE            AS TYPE," + 
            		"                               Extent14.DESCRIPTION     AS DESCRIPTION," + 
            		"                               Extent14.SORTNO          AS SORTNO7," + 
            		"                               Extent14.CONTROLTYPECODE AS CONTROLTYPECODE," + 
            		"                               Extent14.NULLABLE        AS NULLABLE," + 
            		"                               Extent14.DICTNAME        AS DICTNAME," + 
            		"                               Extent14.QUERYFLAG       AS QUERYFLAG," + 
            		"                               Extent14.SHOWFLAG        AS SHOWFLAG," + 
            		"                               Extent14.SORTFLAG        AS SORTFLAG" + 
            		"                          FROM JLP.J_TABLECLASSDICT Extent11" + 
            		"                         INNER JOIN JLP.J_SHOWVIEW Extent12" + 
            		"                            ON (CAST(Extent11.VIEWID AS number(19, 0))) =" + 
            		"                               Extent12.ID" + 
            		"                         INNER JOIN JLP.J_SHOWDETAILVIEW Extent13" + 
            		"                            ON Extent12.ID = Extent13.VIEWID" + 
            		"                         INNER JOIN JLP.J_TABLEFIELDDICT Extent14" + 
            		"                            ON Extent13.FIELDID = Extent14.ID" + 
            		"                         WHERE (('1' = Extent11.ACTIVEFLAG) AND" + 
            		"                               ('1' = Extent12.ACTIVEFLAG) AND" + 
            		"                               ('1' = Extent13.ACTIVEFLAG) AND" + 
            		"                               ('1' = Extent14.SHOWFLAG))) Filter4" + 
            		"                 INNER JOIN JLP.J_TABLEDICT Extent15" + 
            		"                    ON Filter4.TABLECODE = Extent15.CODE" + 
            		"                 WHERE ('1' = Extent15.ACTIVEFLAG)) Distinct2" + 
            		"         INNER JOIN (SELECT Extent16.ID              AS ID10," + 
            		"                           Extent16.CODE            AS CODE11," + 
            		"                           Extent16.NAME            AS NAME11," + 
            		"                           Extent16.ACTIVEFLAG      AS ACTIVEFLAG11," + 
            		"                           Extent16.CREATEDATE      AS CREATEDATE," + 
            		"                           Extent16.VIEWID          AS VIEWID7," + 
            		"                           Extent17.ID              AS ID16," + 
            		"                           Extent17.CODE            AS CODE12," + 
            		"                           Extent17.NAME            AS NAME12," + 
            		"                           Extent17.ACTIVEFLAG      AS ACTIVEFLAG12," + 
            		"                           Extent17.ACCESSCODE      AS ACCESSCODE," + 
            		"                           Extent17.MAINTABLECODE   AS MAINTABLECODE," + 
            		"                           Extent18.ID              AS ID11," + 
            		"                           Extent18.VIEWID          AS VIEWID8," + 
            		"                           Extent18.FIELDID         AS FIELDID," + 
            		"                           Extent18.FIELDTITLE      AS FIELDTITLE," + 
            		"                           Extent18.ACTIVEFLAG      AS ACTIVEFLAG13," + 
            		"                           Extent18.SORTNO          AS SORTNO8," + 
            		"                           Extent18.DEFAULTFLAG     AS DEFAULTFLAG," + 
            		"                           Extent18.FIELDNAME       AS FIELDNAME," + 
            		"                           Extent19.ID              AS ID17," + 
            		"                           Extent19.TABLECODE       AS TABLECODE," + 
            		"                           Extent19.CODE            AS CODE13," + 
            		"                           Extent19.NAME            AS NAME13," + 
            		"                           Extent19.TYPE            AS TYPE," + 
            		"                           Extent19.DESCRIPTION     AS DESCRIPTION3," + 
            		"                           Extent19.SORTNO          AS SORTNO9," + 
            		"                           Extent19.CONTROLTYPECODE AS CONTROLTYPECODE," + 
            		"                           Extent19.NULLABLE        AS NULLABLE," + 
            		"                           Extent19.DICTNAME        AS DICTNAME," + 
            		"                           Extent19.QUERYFLAG       AS QUERYFLAG," + 
            		"                           Extent19.SHOWFLAG        AS SHOWFLAG," + 
            		"                           Extent19.SORTFLAG        AS SORTFLAG," + 
            		"                           Extent20.ID              AS ID18," + 
            		"                           Extent20.CODE            AS CODE14," + 
            		"                           Extent20.NAME            AS NAME14," + 
            		"                           Extent20.DESCRIPTION     AS DESCRIPTION4," + 
            		"                           Extent20.SORTNO          AS SORTNO10," + 
            		"                           Extent20.ACTIVEFLAG      AS ACTIVEFLAG14" + 
            		"                      FROM JLP.J_TABLECLASSDICT Extent16" + 
            		"                     INNER JOIN JLP.J_SHOWVIEW Extent17" + 
            		"                        ON (CAST(Extent16.VIEWID AS number(19, 0))) =" + 
            		"                           Extent17.ID" + 
            		"                     INNER JOIN JLP.J_SHOWDETAILVIEW Extent18" + 
            		"                        ON Extent17.ID = Extent18.VIEWID" + 
            		"                     INNER JOIN JLP.J_TABLEFIELDDICT Extent19" + 
            		"                        ON Extent18.FIELDID = Extent19.ID" + 
            		"                     INNER JOIN JLP.J_TABLEDICT Extent20" + 
            		"                        ON Extent19.TABLECODE = Extent20.CODE) Join17" + 
            		"            ON ('1' = Join17.ACTIVEFLAG14)" + 
            		"           AND (Distinct2.ID = Join17.ID10)" + 
            		"         WHERE (('1' = Join17.ACTIVEFLAG11) AND ('1' = Join17.ACTIVEFLAG12) AND" + 
            		"               ('1' = Join17.ACTIVEFLAG13) AND ('1' = Join17.SHOWFLAG))) UnionAll1" + 
            		" ORDER BY UnionAll1.ACTIVEFLAG ASC," + 
            		"          UnionAll1.CREATEDATE ASC," + 
            		"          UnionAll1.VIEWID     ASC," + 
            		"          UnionAll1.ID2        ASC," + 
            		"          UnionAll1.NAME       ASC," + 
            		"          UnionAll1.CODE       ASC," + 
            		"          UnionAll1.C1         ASC") ;
            
            log.info("fieldsSql --> " + fieldsSql.toString());
            List<Map<String,Object>> fields = jlpService.queryForSQL(fieldsSql.toString(),new Object[] {});
            Set<Map<String,Object>> showFields = new HashSet<Map<String,Object>>();
            Set<Map<String,Object>> FieldsDefalut = new HashSet<>();
            for(Map<String,Object> map : fields) {
            	Map<String,Object> newMap = new HashMap<String, Object>();
            	newMap.put("viewId", ObjectUtil.objToString(map.get("C4")));
            	newMap.put("title", ObjectUtil.objToString(map.get("C7")));
            	newMap.put("key", ObjectUtil.objToString(map.get("C8")));
            	
            	List<JShowDetailView> detailViewList = showDetailViewService.getByViewId(ObjectUtil.objToLong(map.get("C3")));
            	List<Map<String,Object>> detailList = new ArrayList<>();
            	
            	for(JShowDetailView showDetailView : detailViewList) {
            		Map<String,Object> detailMap = new HashMap<>();
            		detailMap.put("Id", showDetailView.getId());
            		detailMap.put("title", showDetailView.getFieldtitle());
            		detailMap.put("key", showDetailView.getId());
            		detailList.add(detailMap);
            		
            		Map<String,Object> defalutMap = new HashMap<>();
            		defalutMap.put("value", "1".equals(showDetailView.getDefaultflag()));
            		defalutMap.put("key", showDetailView.getId());
            		FieldsDefalut.add(defalutMap);
            	}
            	newMap.put("children", detailList);
            	showFields.add(newMap);
            }
            
            Set<Object> defaultFields = new HashSet<>();
            for(Map<String,Object> map : FieldsDefalut) {
            	if(ObjectUtil.objToBool(map.get("value"),false)) {
            		defaultFields.add(map.get("key"));
            	}
            }
            
            resultMap.put("response", new BaseResponse());
    		resultMap.put("showFields", showFields);
    		resultMap.put("defaultFields", defaultFields);
    		String json = JSONObject.toJSONString(resultMap);
    		log.info("response : " + json);
    		return json;

        }
}
