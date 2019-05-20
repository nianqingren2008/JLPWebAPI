package com.callan.service.provider.service;

import java.util.List;

import com.callan.service.provider.pojo.db.JSensitiveWord;

public interface IJSensitiveWordService {

	JSensitiveWord getOne(Long id);

	List<JSensitiveWord> getAll(boolean activeflag);

}
