package com.callan.service.provider.pojo.db;

/**
 * 数据表字典
 * 
 * @author callan
 *
 */
public class JTableDict {
	/**
	 * 流水号
	 */
	private Long id;
	/**
	 * 编码
	 */
	private String code;
	/**
	 * 表名
	 */
	private String name;
	/**
	 * 描述
	 */
	private String description;
	/**
	 * 排序大小
	 */
	private Integer sortno;
	/**
	 * 启用标志
	 */
	private String activeflag;

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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getSortno() {
		return sortno;
	}

	public void setSortno(Integer sortno) {
		this.sortno = sortno;
	}

	public String getActiveflag() {
		return activeflag;
	}

	public void setActiveflag(String activeflag) {
		this.activeflag = activeflag;
	}

}
