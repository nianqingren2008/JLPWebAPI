package com.callan.service.provider.service;

import com.callan.service.provider.pojo.db.JAdvancedqrItem;

public interface IJAdvancedqrItemService {


	JAdvancedqrItem getOne(Long id);


	void save(JAdvancedqrItem advancedqrItem);
	
}
