package com.callan.service.provider.service;

import java.util.List;

import com.callan.service.provider.pojo.base.CacheResponse;
import com.callan.service.provider.pojo.task.JTaskdownload;

public interface IJTaskdownloadService {

	CacheResponse getAll();

	CacheResponse getAll4Id();

}
