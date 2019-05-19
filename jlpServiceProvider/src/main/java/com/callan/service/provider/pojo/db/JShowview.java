package com.callan.service.provider.pojo.db;


/**
 * 视图配置主表
 * @author callan
 *
 */
public class JShowview {
	
	/**
	 * 流水号
	 */
	private  Long id;
	/**
	 * 视图编码
	 */
	private String code;
	/**
	 * 视图名称
	 */
	private String name;
	/**
	 * 启用标志  默认为1
	 */
	private String activeflag="1";
	/**
	 * 访问代码
	 */
	private String accesscode;
	/**
	 * 视图主表
	 */
	private String maintablecode;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getActiveflag() {
		return activeflag;
	}
	public void setActiveflag(String activeflag) {
		this.activeflag = activeflag;
	}
	public String getAccesscode() {
		return accesscode;
	}
	public void setAccesscode(String accesscode) {
		this.accesscode = accesscode;
	}
	public String getMaintablecode() {
		return maintablecode;
	}
	public void setMaintablecode(String maintablecode) {
		this.maintablecode = maintablecode;
	}
	
	
}
