package com.callan.service.provider.pojo.patient;

import java.util.Date;


public class PatientModel {
	 /*
     *患者ID号(患者ID、门诊号、住院号)
    */
    private String patientId;
    /*
     *姓名
    */
    private String name;
    /*
     *出生日期
    */
    private Date birthday;
    /*
     *性别
    */
    private String sex;
    /*
     *地址
    */
    private String address;
    /*
     *家庭电话
    */
    private String homenum;
    /*
     *手机
    */
    private String telnum;
    /*
     *文化程度
    */
    private String culturelevel;
    /*
     *身高(cm)
    */
    private String height;
    /*
     *体重(kg)
    */
    private String weight;
    /*
     *民族
    */
    private String nation;
    /*
     *籍贯
    */
    private String nativeplace;
    /*
     *婚姻状况
    */
    private String patientmarried;
    /*
     *证件类型
    */
    private String papertype;
    /*
     *证件号码
    */
    private String idcard;
    /*
     *职业信息
    */
    private String ratientjob;
    /*
     *联系人姓名
    */
    private String relationship;
    /*
     *联系人关系
    */
    private String relationphone;
    /*
     *联系人手机
    */
    private String relationcall;
	public String getPatientId() {
		return patientId;
	}
	public void setPatientId(String patientId) {
		this.patientId = patientId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
		this.sex = sex;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getHomenum() {
		return homenum;
	}
	public void setHomenum(String homenum) {
		this.homenum = homenum;
	}
	public String getTelnum() {
		return telnum;
	}
	public void setTelnum(String telnum) {
		this.telnum = telnum;
	}
	public String getCulturelevel() {
		return culturelevel;
	}
	public void setCulturelevel(String culturelevel) {
		this.culturelevel = culturelevel;
	}
	public String getHeight() {
		return height;
	}
	public void setHeight(String height) {
		this.height = height;
	}
	public String getWeight() {
		return weight;
	}
	public void setWeight(String weight) {
		this.weight = weight;
	}
	public String getNation() {
		return nation;
	}
	public void setNation(String nation) {
		this.nation = nation;
	}
	public String getNativeplace() {
		return nativeplace;
	}
	public void setNativeplace(String nativeplace) {
		this.nativeplace = nativeplace;
	}
	public String getPatientmarried() {
		return patientmarried;
	}
	public void setPatientmarried(String patientmarried) {
		this.patientmarried = patientmarried;
	}
	public String getPapertype() {
		return papertype;
	}
	public void setPapertype(String papertype) {
		this.papertype = papertype;
	}
	public String getIdcard() {
		return idcard;
	}
	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}
	public String getRatientjob() {
		return ratientjob;
	}
	public void setRatientjob(String ratientjob) {
		this.ratientjob = ratientjob;
	}
	public String getRelationship() {
		return relationship;
	}
	public void setRelationship(String relationship) {
		this.relationship = relationship;
	}
	public String getRelationphone() {
		return relationphone;
	}
	public void setRelationphone(String relationphone) {
		this.relationphone = relationphone;
	}
	public String getRelationcall() {
		return relationcall;
	}
	public void setRelationcall(String relationcall) {
		this.relationcall = relationcall;
	}
}
