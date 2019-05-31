package com.callan.service.provider.pojo.advanceQueryBase;

import javax.validation.constraints.Size;

public class AdvancedQueryRecordModel {
	/*
    * 纳排条件ID(新建时为0)
    */
	private long id;
    /*
    * 课题ID
    */
    private long projectId;
    /*
    * 纳排条件名称
    */
    @Size(max = 50, min=1, message="长度应该在1-50之间")
    private String name;

    /*
    * 纳排条件
    */
    private Queries queries;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getProjectId() {
		return projectId;
	}

	public void setProjectId(long projectId) {
		this.projectId = projectId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Queries getQueries() {
		return queries;
	}

	public void setQueries(Queries queries) {
		this.queries = queries;
	}

	
}
