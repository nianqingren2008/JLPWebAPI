package com.callan.service.provider.service;

import java.util.List;

import com.callan.service.provider.pojo.db.JProjectstatistics;

public interface IJProjectstatisticsService {

	List<JProjectstatistics> getByProjectIdAndStatisticstypedataid(Long id, Long statisticsTypedataId);

	void update(JProjectstatistics projectstatistics);

	void save(JProjectstatistics projectstatistics);
	
}
