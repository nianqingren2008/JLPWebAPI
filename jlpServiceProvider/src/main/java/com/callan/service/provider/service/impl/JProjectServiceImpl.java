package com.callan.service.provider.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.callan.service.provider.config.JLPException;
import com.callan.service.provider.dao.mapper.JProjectMapper;
import com.callan.service.provider.pojo.advanceQueryBase.AdvancedQueryRecordModel;
import com.callan.service.provider.pojo.advanceQueryBase.Queries;
import com.callan.service.provider.pojo.advanceQueryBase.QueryConds;
import com.callan.service.provider.pojo.advanceQueryBase.QueryIncludesEX;
import com.callan.service.provider.pojo.advanceQueryBase.QueryIncludesEXCondition;
import com.callan.service.provider.pojo.base.BaseResponse;
import com.callan.service.provider.pojo.db.JAdvancedqr;
import com.callan.service.provider.pojo.db.JAdvancedqrItem;
import com.callan.service.provider.pojo.db.JProject;
import com.callan.service.provider.pojo.db.JQueryrecordDetails;
import com.callan.service.provider.service.IJAdvancedqrItemService;
import com.callan.service.provider.service.IJAdvancedqrService;
import com.callan.service.provider.service.IJProjectService;
import com.callan.service.provider.service.IJQueryrecordDetailService;
import com.callan.service.provider.service.IJQueryrecordService;

@Service
public class JProjectServiceImpl implements IJProjectService {
	@Autowired
	private JProjectMapper  projectMapper;

	@Autowired
	private IJAdvancedqrService advancedqrService;

	@Autowired
	private IJAdvancedqrItemService advancedqrItemService;

	@Autowired
	private IJQueryrecordService queryrecordService;

	@Autowired
	private IJQueryrecordDetailService queryrecordDetailService;
	
	@Override
	public JProject getOne(Long id) {
		return projectMapper.selectByPrimaryKey(id);
	}


	@Override
	public List<JProject> getByUserId(long userId) {
		return projectMapper.getByUserId(userId);
	}


	@Override
	public void save(JProject project) {
		projectMapper.insert(project);
	}


	@Override
	public void update(JProject project) {
		projectMapper.updateByPrimaryKeySelective(project);
	}
	
	@Override
	public AdvancedQueryRecordModel getQueryRecord(Long Id,Long userId) {
		JAdvancedqr jAdvancedqr = null;
		List<JAdvancedqr> jAdvancedqrList = advancedqrService.getByProjectId(Id);
		if (jAdvancedqrList != null && jAdvancedqrList.size() > 0) {
			if (jAdvancedqrList.get(0).getUserid().longValue() == userId.longValue()) {
				jAdvancedqr = jAdvancedqrList.get(0);
			}
		}
		if (jAdvancedqr == null) {
			throw new JLPException("不存在此项目条件记录");
		}
		List<JAdvancedqrItem> dqrItemList = advancedqrItemService.getByQrId(jAdvancedqr.getId());


		AdvancedQueryRecordModel advancedQueryRecord = new AdvancedQueryRecordModel();
		advancedQueryRecord.setId(jAdvancedqr.getId());
		advancedQueryRecord.setName(jAdvancedqr.getAqname());
		advancedQueryRecord.setProjectId(jAdvancedqr.getProjectid() == null ? 0 : jAdvancedqr.getProjectid());
		advancedQueryRecord.setQueries(new Queries());
		List<JAdvancedqrItem> dqrItemListNew = new ArrayList<JAdvancedqrItem>();
		for (JAdvancedqrItem advancedqrItem : dqrItemList) {
			if ("1".equals(advancedqrItem.getItemtype())) {
				List<JQueryrecordDetails> detailList = queryrecordDetailService
						.getByQueryId(advancedqrItem.getQueryid());
				advancedqrItem.setDetailList(detailList);
				dqrItemListNew.add(advancedqrItem);
			}
		}
		
		List<QueryConds> queryConds = new ArrayList<QueryConds>();
		List<JAdvancedqrItem> dqrItemListInclude = new ArrayList<JAdvancedqrItem>();
		List<JAdvancedqrItem> dqrItemListExclude = new ArrayList<JAdvancedqrItem>();
		for (JAdvancedqrItem advancedqrItem : dqrItemListNew) {
			List<JQueryrecordDetails> detailList = advancedqrItem.getDetailList();
			Collections.sort(detailList, new Comparator<JQueryrecordDetails>() {
				@Override
				public int compare(JQueryrecordDetails o1, JQueryrecordDetails o2) {
					return o1.getQueryid().compareTo(o2.getQueryid());
				}
			});

			Collections.sort(detailList, new Comparator<JQueryrecordDetails>() {
				@Override
				public int compare(JQueryrecordDetails o1, JQueryrecordDetails o2) {
					return o1.getDetailid().compareTo(o2.getDetailid());
				}
			});
			for(JQueryrecordDetails detail : detailList) {
				QueryConds queryDetailModel = new QueryConds();
				queryDetailModel.setCombinator(detail.getLogicaltype());
				queryDetailModel.setCondition(detail.getFieldname());
				queryDetailModel.setCondValue(detail.getFieldvalue());
				queryDetailModel.setFieldType(detail.getFieldvaluetype());
				queryDetailModel.setLeftqueto(detail.getLeftbrackets());
				queryDetailModel.setRelation(detail.getRelationtype());
				queryDetailModel.setRightqueto(detail.getRightbrackets());
				queryConds.add(queryDetailModel);
			}

			if ("2".equals(advancedqrItem.getItemtype())) {
				dqrItemListInclude.add(advancedqrItem);
			}

			if ("3".equals(advancedqrItem.getItemtype())) {
				dqrItemListExclude.add(advancedqrItem);
			}
		}
		advancedQueryRecord.getQueries().setQueryConds(queryConds);
		advancedQueryRecord.getQueries().setQueryIncludesEX(new QueryIncludesEX());

		excueteEx(advancedQueryRecord, dqrItemListInclude);
		excueteEx(advancedQueryRecord, dqrItemListExclude);
		return advancedQueryRecord;
	}
	private void excueteEx(AdvancedQueryRecordModel advancedQueryRecord, List<JAdvancedqrItem> dqrItemListInclude) {
		List<QueryIncludesEXCondition> include = new ArrayList<QueryIncludesEXCondition>();
		advancedQueryRecord.getQueries().getQueryIncludesEX().setIncludes(include);

		Collections.sort(dqrItemListInclude, new Comparator<JAdvancedqrItem>() {
			@Override
			public int compare(JAdvancedqrItem o1, JAdvancedqrItem o2) {
				return o1.getItemno().compareTo(o2.getItemno());
			}
		});

		for (JAdvancedqrItem advancedqrItem : dqrItemListInclude) {
			QueryIncludesEXCondition queryCollectionModel = new QueryIncludesEXCondition();
			queryCollectionModel.setId(advancedqrItem.getModelid());
			queryCollectionModel.setLeftqueto(advancedqrItem.getLeftqueto());
			queryCollectionModel.setRightqueto(advancedqrItem.getRightqueto());
			queryCollectionModel.setSetCombinator(advancedqrItem.getSetcombinatortype());
			queryCollectionModel.setType(advancedqrItem.getModeltype());
			List<QueryConds> condsList = new ArrayList<QueryConds>();

			List<JQueryrecordDetails> detailList = advancedqrItem.getDetailList();
			Collections.sort(detailList, new Comparator<JQueryrecordDetails>() {
				@Override
				public int compare(JQueryrecordDetails o1, JQueryrecordDetails o2) {
					return o1.getQueryid().compareTo(o2.getQueryid());
				}
			});

			Collections.sort(detailList, new Comparator<JQueryrecordDetails>() {
				@Override
				public int compare(JQueryrecordDetails o1, JQueryrecordDetails o2) {
					return o1.getDetailid().compareTo(o2.getDetailid());
				}
			});
			for (JQueryrecordDetails detail : detailList) {
				QueryConds queryDetailModel = new QueryConds();
				queryDetailModel.setCombinator(detail.getLogicaltype());
				queryDetailModel.setCondition(detail.getFieldname());
				queryDetailModel.setCondValue(detail.getFieldvalue());
				queryDetailModel.setFieldType(detail.getFieldvaluetype());
				queryDetailModel.setLeftqueto(detail.getLeftbrackets());
				queryDetailModel.setRelation(detail.getRelationtype());
				queryDetailModel.setRightqueto(detail.getRightbrackets());
				condsList.add(queryDetailModel);
			}

			queryCollectionModel.setConds(condsList);
			include.add(queryCollectionModel);
		}

	}
}
