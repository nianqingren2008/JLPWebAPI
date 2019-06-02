package com.callan.service.provider.pojo.db;

import java.util.Date;

public class DPatientglobal {
	/*
	 * 患者唯一ID
	 */
    private Long id;
    /*
     * 姓名
     */
    private String name; 
    /*
     * 出生日期
     */
    private Date birthday; 
    /*
     * 性别
     */
    private String sex; 
    /*
     * 地址
     */
    private String address; 
    /*
     * 家庭电话
     */
    private String homenum; 
    /*
     * 手机
     */
    private String telnum; 
    /*
     * 文化程度
     */
    private String culturelevel; 
    /*
     * 身高(cm)
     */
    private String height; 
    /*
     * 体重(kg)
     */
    private String weight; 
    /*
     * 民族
     */
    private String nation; 
    /*
     * 籍贯
     */
    private String nativeplace; 
    /*
     * 婚姻状况
     */
    private String patientmarried; 
    /*
     * 证件类型
     */
    private String papertype; 
    /*
     * 证件号码
     */

    private String idcard; 
    /*
     * 职业信息
     */
    private String patientjob; 
    /*
     * 联系人姓名
     */
    private String relationship; 
    /*
     * 联系人手机
     */
    private String relationphone; 
    /*
     * 联系人手机
     */
    private String relationcall; 
    /*
     * 记录创建时间
     */
    private Date jlcreatedate; 
    /*
     * 记录启用标志
     */
    private String jlactiveflag; 

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
        this.name = name == null ? null : name.trim();
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex == null ? null : sex.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getHomenum() {
        return homenum;
    }

    public void setHomenum(String homenum) {
        this.homenum = homenum == null ? null : homenum.trim();
    }

    public String getTelnum() {
        return telnum;
    }

    public void setTelnum(String telnum) {
        this.telnum = telnum == null ? null : telnum.trim();
    }

    public String getCulturelevel() {
        return culturelevel;
    }

    public void setCulturelevel(String culturelevel) {
        this.culturelevel = culturelevel == null ? null : culturelevel.trim();
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height == null ? null : height.trim();
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight == null ? null : weight.trim();
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation == null ? null : nation.trim();
    }

    public String getNativeplace() {
        return nativeplace;
    }

    public void setNativeplace(String nativeplace) {
        this.nativeplace = nativeplace == null ? null : nativeplace.trim();
    }

    public String getPatientmarried() {
        return patientmarried;
    }

    public void setPatientmarried(String patientmarried) {
        this.patientmarried = patientmarried == null ? null : patientmarried.trim();
    }

    public String getPapertype() {
        return papertype;
    }

    public void setPapertype(String papertype) {
        this.papertype = papertype == null ? null : papertype.trim();
    }

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard == null ? null : idcard.trim();
    }

    public String getPatientjob() {
        return patientjob;
    }

    public void setPatientjob(String patientjob) {
        this.patientjob = patientjob == null ? null : patientjob.trim();
    }

    public String getRelationship() {
        return relationship;
    }

    public void setRelationship(String relationship) {
        this.relationship = relationship == null ? null : relationship.trim();
    }

    public String getRelationphone() {
        return relationphone;
    }

    public void setRelationphone(String relationphone) {
        this.relationphone = relationphone == null ? null : relationphone.trim();
    }

    public String getRelationcall() {
        return relationcall;
    }

    public void setRelationcall(String relationcall) {
        this.relationcall = relationcall == null ? null : relationcall.trim();
    }

    public Date getJlcreatedate() {
        return jlcreatedate;
    }

    public void setJlcreatedate(Date jlcreatedate) {
        this.jlcreatedate = jlcreatedate;
    }

    public String getJlactiveflag() {
        return jlactiveflag;
    }

    public void setJlactiveflag(String jlactiveflag) {
        this.jlactiveflag = jlactiveflag == null ? null : jlactiveflag.trim();
    }
}