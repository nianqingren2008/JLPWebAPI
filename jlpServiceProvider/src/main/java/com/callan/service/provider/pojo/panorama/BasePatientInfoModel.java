package com.callan.service.provider.pojo.panorama;

public class BasePatientInfoModel {
	private String patientID;
	private String sex;
	private String patientName;
	private String birthday;
	private Integer totalRecords;
	private Integer totalInpatients;

	public String getPatientID() {
		return patientID;
	}

	public void setPatientID(String patientID) {
		this.patientID = patientID;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getPatientName() {
		return patientName;
	}

	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public Integer getTotalRecords() {
		return totalRecords;
	}

	public void setTotalRecords(Integer totalRecords) {
		this.totalRecords = totalRecords;
	}

	public Integer getTotalInpatients() {
		return totalInpatients;
	}

	public void setTotalInpatients(Integer totalInpatients) {
		this.totalInpatients = totalInpatients;
	}

}
