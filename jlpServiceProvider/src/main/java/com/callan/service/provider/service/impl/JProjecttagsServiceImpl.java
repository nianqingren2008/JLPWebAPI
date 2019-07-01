package com.callan.service.provider.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callan.service.provider.dao.mapper.JProjecttagsMapper;
import com.callan.service.provider.pojo.db.JProjecttags;
import com.callan.service.provider.service.IJProjecttagsService;

@Service
public class JProjecttagsServiceImpl implements IJProjecttagsService {
	@Autowired
	private JProjecttagsMapper  projecttagsMapper;

	@Override
	public List<JProjecttags> getByTagId(Long tagId) {
		return projecttagsMapper.getByTagId(tagId);
	}

	@Override
	public List<JProjecttags> getByProjectId(Long projectId) {
		return  projecttagsMapper.getByProjectId(projectId);
	}

	@Override
	public JProjecttags getByProIdAndDataIdAndTagId(Long projectId, Long dataId, Long tagId) {
		List<JProjecttags> list = projecttagsMapper.getByProIdAndDataIdAndTagId(projectId,dataId,tagId);
		if(list != null && list.size() > 0) {
			return list.get(0);
		}else {
			return null;
		}
	}

	@Override
	public void save(JProjecttags record) {
		projecttagsMapper.insert(record);
	}

	@Override
	public void update(JProjecttags record) {
		projecttagsMapper.updateByPrimaryKey(record);
	}
	
}
