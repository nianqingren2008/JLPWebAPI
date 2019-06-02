package com.callan.service.provider.service;

import com.callan.service.provider.pojo.db.JDownloadfile;

public interface IJDownloadFileService {

	JDownloadfile getOne(long id);
	
	JDownloadfile getByIdAndFileCode(long id,String fileCode);
}
