package com.callan.service.provider.service;

import java.util.List;

import com.callan.service.provider.pojo.db.JAdvancedqr;

public interface IJAdvancedqrService {

	List<JAdvancedqr> getByUserId(Long userId);
	
}
