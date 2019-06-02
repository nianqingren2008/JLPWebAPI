package com.callan.service.provider.service;

import java.util.List;

import com.callan.service.provider.pojo.db.JAdvancedqrItem;

public interface IJAdvancedqrItemService {


	JAdvancedqrItem getOne(long id);


	void save(JAdvancedqrItem advancedqrItem);


	List<JAdvancedqrItem> getByQrId(long qrId);
	
}
