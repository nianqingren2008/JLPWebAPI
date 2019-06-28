package com.callan.service.provider.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
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
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.alibaba.fastjson.JSONObject;
import com.callan.service.provider.config.JLPConts;
import com.callan.service.provider.config.JLPException;
import com.callan.service.provider.config.JLPLog;
import com.callan.service.provider.config.ThreadPoolConfig;
import com.callan.service.provider.dao.mapper.JProjectMapper;
import com.callan.service.provider.dao.mapper.JProjecttagsMapper;
import com.callan.service.provider.pojo.advanceQueryBase.AdvancedQueryRecordModel;
import com.callan.service.provider.pojo.advanceQueryBase.Queries;
import com.callan.service.provider.pojo.advanceQueryBase.QueryDetailModel;
import com.callan.service.provider.pojo.advanceQueryBase.QueryIncludesEX;
import com.callan.service.provider.pojo.advanceQueryBase.QueryCollectionModel;
import com.callan.service.provider.pojo.base.BaseResponse;
import com.callan.service.provider.pojo.db.JAdvancedqr;
import com.callan.service.provider.pojo.db.JAdvancedqrItem;
import com.callan.service.provider.pojo.db.JProject;
import com.callan.service.provider.pojo.db.JProjectstatistics;
import com.callan.service.provider.pojo.db.JProjecttags;
import com.callan.service.provider.pojo.db.JQueryrecordDetails;
import com.callan.service.provider.pojo.db.JShowDetailView;
import com.callan.service.provider.pojo.db.JShowView;
import com.callan.service.provider.pojo.db.JTableDict;
import com.callan.service.provider.pojo.db.JTableFieldDict;
import com.callan.service.provider.pojo.tableclassdict.JTableclassdict;
import com.callan.service.provider.service.IJAdvancedqrItemService;
import com.callan.service.provider.service.IJAdvancedqrService;
import com.callan.service.provider.service.IJLpService;
import com.callan.service.provider.service.IJProjectDataStatusdictService;
import com.callan.service.provider.service.IJProjectService;
import com.callan.service.provider.service.IJProjectstatisticsService;
import com.callan.service.provider.service.IJProjecttagsService;
import com.callan.service.provider.service.IJQueryrecordDetailService;
import com.callan.service.provider.service.IJQueryrecordService;
import com.callan.service.provider.service.IJRoleRightService;
import com.callan.service.provider.service.IJSensitiveWordService;
import com.callan.service.provider.service.IJShowDetailViewService;
import com.callan.service.provider.service.IJShowViewService;
import com.callan.service.provider.service.IJTableDictService;
import com.callan.service.provider.service.IJTableFieldDictService;
import com.callan.service.provider.service.IJTableclassdictService;
import com.callan.service.provider.service.IJTagdictService;
import com.callan.service.provider.service.IJUserService;

@Service
public class JProjecttagsServiceImpl implements IJProjecttagsService {
	@Autowired
	private JProjecttagsMapper  projecttagsMapper;

	@Override
	public List<JProjecttags> getByTagId(Long tagId) {
		return projecttagsMapper.getByTagId(tagId);
	}

	@Override
	public List<JProjecttags> getByProjectId(Long projectId) {
		return  projecttagsMapper.getByProjectId(projectId);
	}

	
}
