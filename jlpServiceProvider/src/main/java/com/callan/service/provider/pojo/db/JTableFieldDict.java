package com.callan.service.provider.pojo.db;

/**
 * 数据表与字段对照信息
 * @author callan
 *
 */
public class JTableFieldDict {
	/**
	 * 流水号
	 */
	private Long id;
	/**
	 * 归属表编码
	 */
	private String tablecode;
	/**
	 * 字段编码
	 */
	private String code;
	/**
	 * 字段名
	 */
	private String name;
	/**
	 * 字段类型
	 */
	private String type;
	/**
	 * 字段描述
	 */
	private String description;
	/**
	 * 排序字段
	 */
	private Integer sortno;
	/**
	 * 显示控件类型
	 */
	private String controltypecode;
	/**
	 * 是否可空
	 */
	private String nullable;
	/**
	 * 字典表信息
	 */
	private String dictname;
	/**
	 * 搜索标志
	 */
	private String queryflag;
	/**
	 * 展示标志
	 */
	private String showflag;
	/**
	 * 排序标志
	 */
	private String sortflag;

	private JShowDetailView jShowDetailView;
	
	
	public JShowDetailView getjShowDetailView() {
		return jShowDetailView;
	}

	public void setjShowDetailView(JShowDetailView jShowDetailView) {
		this.jShowDetailView = jShowDetailView;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTablecode() {
		return tablecode;
	}

	public void setTablecode(String tablecode) {
		this.tablecode = tablecode;
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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

	public String getControltypecode() {
		return controltypecode;
	}

	public void setControltypecode(String controltypecode) {
		this.controltypecode = controltypecode;
	}

	public String getNullable() {
		return nullable;
	}

	public void setNullable(String nullable) {
		this.nullable = nullable;
	}

	public String getDictname() {
		return dictname;
	}

	public void setDictname(String dictname) {
		this.dictname = dictname;
	}

	public String getQueryflag() {
		return queryflag;
	}

	public void setQueryflag(String queryflag) {
		this.queryflag = queryflag;
	}

	public String getShowflag() {
		return showflag;
	}

	public void setShowflag(String showflag) {
		this.showflag = showflag;
	}

	public String getSortflag() {
		return sortflag;
	}

	public void setSortflag(String sortflag) {
		this.sortflag = sortflag;
	}

}
