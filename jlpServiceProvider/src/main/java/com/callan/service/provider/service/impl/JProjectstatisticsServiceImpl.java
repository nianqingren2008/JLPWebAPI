package com.callan.service.provider.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callan.service.provider.dao.mapper.JProjectdatastatusdictMapper;
import com.callan.service.provider.dao.mapper.JProjectstatisticsMapper;
import com.callan.service.provider.pojo.db.JProjectdatastatusdict;
import com.callan.service.provider.pojo.db.JProjectstatistics;
import com.callan.service.provider.service.IJProjectDataStatusService;
import com.callan.service.provider.service.IJProjectstatisticsService;

@Service
public class JProjectstatisticsServiceImpl implements IJProjectstatisticsService {
	
	@Autowired JProjectstatisticsMapper projectstatisticsMapper;

	@Override
	public List<JProjectstatistics> getByProjectIdAndStatisticstypedataid(Long id, Long statisticsTypedataId) {
		return projectstatisticsMapper.getByProjectIdAndStatisticstypedataid(id,statisticsTypedataId);
	}

	@Override
	public void update(JProjectstatistics projectstatistics) {
		projectstatisticsMapper.updateByPrimaryKeySelective(projectstatistics);
	}

	@Override
	public void save(JProjectstatistics projectstatistics) {
		projectstatisticsMapper.insert(projectstatistics);
	}

	
	
}
