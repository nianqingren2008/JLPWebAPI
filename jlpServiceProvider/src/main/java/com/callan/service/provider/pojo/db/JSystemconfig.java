package com.callan.service.provider.pojo.db;

public class JSystemconfig {
	/*
	 * 流水号
	 */
	private Long id;
	/*
	 * 设置分类
	 */
	private String classtype;
	/*
	 * 名称
	 */
	private String keyname;
	/*
	 * 值
	 */
	private String keyvalue;
	/*
	 * 启用标志
	 */
	private String activeflag;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getClasstype() {
		return classtype;
	}

	public void setClasstype(String classtype) {
		this.classtype = classtype;
	}

	public String getKeyname() {
		return keyname;
	}

	public void setKeyname(String keyname) {
		this.keyname = keyname;
	}

	public String getKeyvalue() {
		return keyvalue;
	}

	public void setKeyvalue(String keyvalue) {
		this.keyvalue = keyvalue;
	}

	public String getActiveflag() {
		return activeflag;
	}

	public void setActiveflag(String activeflag) {
		this.activeflag = activeflag;
	}

}
