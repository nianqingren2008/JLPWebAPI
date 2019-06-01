package com.callan.service.provider.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callan.service.provider.dao.mapper.JDownloadFileMapper;
import com.callan.service.provider.pojo.db.JDownloadfile;
import com.callan.service.provider.service.IJDownloadFileService;

@Service
public class JDownloadFileServiceImpl implements IJDownloadFileService {
	@Autowired
	private JDownloadFileMapper  downloadFileMapper;

	@Override
	public JDownloadfile getOne(long id) {
		return downloadFileMapper.getOne(id);
	}

	@Override
	public JDownloadfile getByIdAndFileCode(long id, String fileCode) {
		return downloadFileMapper.getByIdAndFileCode(id, fileCode);
	}

}
