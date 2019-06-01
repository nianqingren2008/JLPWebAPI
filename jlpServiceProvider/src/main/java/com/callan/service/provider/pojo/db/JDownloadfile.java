package com.callan.service.provider.pojo.db;

import java.util.Date;

public class JDownloadfile {
	/*
	 * 流水号
	 */
	private Long id;
	/*
	 * 相对路径
	 */
	private String relativepath;
	/*
	 * 文件大小
	 */
	private Long filelength;
	/*
	 * 文件类型
	 */
	private byte fieltype;
	/*
	 * 文件校验码
	 */
	private String filemd5;
	/*
	 * 有效时间
	 */
	private Date activedate;
	/*
	 * 创建时间
	 */
	private Date createdate;
	/*
	 * 启用标志
	 */
	private String activeflag;
	/*
	 * 用户ID
	 */
	private Long userid;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRelativepath() {
		return relativepath;
	}

	public void setRelativepath(String relativepath) {
		this.relativepath = relativepath;
	}

	public Long getFilelength() {
		return filelength;
	}

	public void setFilelength(Long filelength) {
		this.filelength = filelength;
	}

	public byte getFieltype() {
		return fieltype;
	}

	public void setFieltype(byte fieltype) {
		this.fieltype = fieltype;
	}

	public String getFilemd5() {
		return filemd5;
	}

	public void setFilemd5(String filemd5) {
		this.filemd5 = filemd5;
	}

	public Date getActivedate() {
		return activedate;
	}

	public void setActivedate(Date activedate) {
		this.activedate = activedate;
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

	public Long getUserid() {
		return userid;
	}

	public void setUserid(Long userid) {
		this.userid = userid;
	}

}
