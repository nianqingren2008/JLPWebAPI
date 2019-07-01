package com.callan.service.provider.service;

import java.util.List;

import com.callan.service.provider.pojo.db.JProjecttags;

public interface IJProjecttagsService {

	List<JProjecttags> getByTagId(Long long1);

	List<JProjecttags> getByProjectId(Long id);

	JProjecttags getByProIdAndDataIdAndTagId(Long projectId, Long dataId, Long tagId);

	void save(JProjecttags jProjecttags);

	void update(JProjecttags jProjecttags);

}
