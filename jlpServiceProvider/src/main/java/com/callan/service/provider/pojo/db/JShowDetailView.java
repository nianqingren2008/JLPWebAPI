package com.callan.service.provider.pojo.db;

/**
 * 视图配置明细字段
 * @author callan
 *
 */
public class JShowDetailView {
	/**
	 * 流水号
	 */
	private  Long id;
	/**
	 * 视图ID
	 */
	private  Long viewid;
	/**
	 * 字段编号
	 */
	private  Long fieldid;
	/**
	 * 字段名
	 */
	private  String fieldtitle;
	/**
	 * 启用标志
	 */
	private  String activeflag;
	/**
	 * 排序字段
	 */
	private  Integer sortno;
	/**
	 * 默认显示项
	 */
	private  String defaultflag;
	/**
	 * 列别名
	 */
	private  String fieldname;
	
	private JTableDict jTableDict;
	
	private JTableFieldDict jTableFieldDict;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getViewid() {
		return viewid;
	}
	public void setViewid(Long viewid) {
		this.viewid = viewid;
	}
	public Long getFieldid() {
		return fieldid;
	}
	public void setFieldid(Long fieldid) {
		this.fieldid = fieldid;
	}
	public String getFieldtitle() {
		return fieldtitle;
	}
	public void setFieldtitle(String fieldtitle) {
		this.fieldtitle = fieldtitle;
	}
	public String getActiveflag() {
		return activeflag;
	}
	public void setActiveflag(String activeflag) {
		this.activeflag = activeflag;
	}
	public Integer getSortno() {
		return sortno;
	}
	public void setSortno(Integer sortno) {
		this.sortno = sortno;
	}
	public String getDefaultflag() {
		return defaultflag;
	}
	public void setDefaultflag(String defaultflag) {
		this.defaultflag = defaultflag;
	}
	public String getFieldname() {
		return fieldname;
	}
	public void setFieldname(String fieldname) {
		this.fieldname = fieldname;
	}
	public JTableDict getjTableDict() {
		return jTableDict;
	}
	public void setjTableDict(JTableDict jTableDict) {
		this.jTableDict = jTableDict;
	}
	public JTableFieldDict getjTableFieldDict() {
		return jTableFieldDict;
	}
	public void setjTableFieldDict(JTableFieldDict jTableFieldDict) {
		this.jTableFieldDict = jTableFieldDict;
	}
	
	
}
