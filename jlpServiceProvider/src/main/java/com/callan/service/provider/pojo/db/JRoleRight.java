package com.callan.service.provider.pojo.db;

import java.util.Date;

/**
 * 角色权限表
 *
 */
public class JRoleRight {
	private Long id;
	private Long roleid;
	private Long rightid;
	private String activeflag;
	private Date createdate;
	private JRight jRight;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getRoleid() {
		return roleid;
	}

	public void setRoleid(Long roleid) {
		this.roleid = roleid;
	}

	public Long getRightid() {
		return rightid;
	}

	public void setRightid(Long rightid) {
		this.rightid = rightid;
	}

	public String getActiveflag() {
		return activeflag;
	}

	public void setActiveflag(String activeflag) {
		this.activeflag = activeflag;
	}

	public Date getCreatedate() {
		return createdate;
	}

	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}

	public JRight getjRight() {
		return jRight;
	}

	public void setjRight(JRight jRight) {
		this.jRight = jRight;
	}

}
