package com.callan.service.provider.pojo.task;

import java.util.Date;

public class JQueryrecord {

   // 流水号
    private long Id;
    
   // 用户ID
    private long Userid;
    
   // 查询名称
    private String Queryname;
    
   // 更新时间
    private Date Updatedate;
    
   // 创建时间
    private Date Createdate;
    
   // 有效标志
    private String Activeflag;
    
   // 排序字段
    private int Sortno;
    
   // 记录总数
    private long Count;


    public long getId() {
		return Id;
	}

    public void setId(long id) {
		Id = id;
	}

    public long getUserid() {
		return Userid;
	}

    public void setUserid(long userid) {
		Userid = userid;
	}
    public String getQueryname() {
		return Queryname;
	}

    public void setQueryname(String queryname) {
		Queryname = queryname;
	}

    public Date getUpdatedate() {
		return Updatedate;
	}

	private void setUpdatedate(Date updatedate) {
		Updatedate = updatedate;
	}

	public Date getCreatedate() {
		return Createdate;
	}

	public void setCreatedate(Date createdate) {
		Createdate = createdate;
	}

	public String getActiveflag() {
		return Activeflag;
	}

	public void setActiveflag(String activeflag) {
		Activeflag = activeflag;
	}

	public int getSortno() {
		return Sortno;
	}

	public void setSortno(int sortno) {
		Sortno = sortno;
	}

	public long getCount() {
		return Count;
	}

	public void setCount(long count) {
		Count = count;
	}
    
    
    
}
