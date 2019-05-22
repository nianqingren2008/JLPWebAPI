package com.callan.service.provider.service;

import java.util.Map;

import com.callan.service.provider.pojo.base.CacheResponse;
import com.callan.service.provider.pojo.db.JShowView;

public interface IJShowViewService {

	JShowView getOne(Long id);

	CacheResponse getAll4Id();

	JShowView getOne(Long id, boolean activityFlag);

}
