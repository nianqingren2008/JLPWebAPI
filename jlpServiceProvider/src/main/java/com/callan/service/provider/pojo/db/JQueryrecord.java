package com.callan.service.provider.pojo.db;

import java.util.Date;
import java.util.List;

/**
 *  查询记录
 */
public class JQueryrecord {
	/*
    * 流水号  J_QUERYRECORD
    */
    private Long id;
    /*
    * 用户ID
    */
    private Long userid;
    /*
    * 查询名称
    */
    private String queryname;
    /*
    * 更新时间
    */
    private Date updatedate;
    /*
    * 创建时间
    */
    private Date createdate;
    /*
    * 有效标志
    */
    private String activeflag;
    /*
    * 排序字段
    */
    private int sortno;
    /*
    * 记录总数
    */
    private Long count;
    
    private List<JQueryrecordDetails> detailList;
    

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserid() {
		return userid;
	}

	public void setUserid(Long userid) {
		this.userid = userid;
	}

	public String getQueryname() {
		return queryname;
	}

	public void setQueryname(String queryname) {
		this.queryname = queryname;
	}

	public Date getUpdatedate() {
		return updatedate;
	}

	public void setUpdatedate(Date updatedate) {
		this.updatedate = updatedate;
	}

	public Date getCreatedate() {
		return createdate;
	}

	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}

	public String getActiveflag() {
		return activeflag;
	}

	public void setActiveflag(String activeflag) {
		this.activeflag = activeflag;
	}

	public int getSortno() {
		return sortno;
	}

	public void setSortno(int sortno) {
		this.sortno = sortno;
	}

	public Long getCount() {
		return count;
	}

	public void setCount(Long count) {
		this.count = count;
	}

	public List<JQueryrecordDetails> getDetailList() {
		return detailList;
	}

	public void setDetailList(List<JQueryrecordDetails> detailList) {
		this.detailList = detailList;
	}

    
    
}
