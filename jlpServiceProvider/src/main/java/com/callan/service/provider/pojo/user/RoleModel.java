package com.callan.service.provider.pojo.user;

import java.util.List;

public class RoleModel {
	public Long id;
	public String name;
	public List<Long> rights;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Long> getRights() {
		return rights;
	}

	public void setRights(List<Long> rights) {
		this.rights = rights;
	}

}
