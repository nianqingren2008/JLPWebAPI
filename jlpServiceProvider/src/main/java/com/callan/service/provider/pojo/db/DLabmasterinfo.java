package com.callan.service.provider.pojo.db;

import java.util.Date;

public class DLabmasterinfo {
    private Long id;

    private Long patientglobalid;

    private String patientid;

    private String name;

    private Date birthday;

    private String visitid;

    private String inputid;

    private String outpatid;

    private String patientsource;

    private String applyno;

    private Date reqdatetime;

    private String testcause;

    private String specimen;

    private Date spcmrecdatetime;

    private Date reporttime;

    private String testtype;

    private Date jlcreatedate;

    private String jlactiveflag;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPatientglobalid() {
        return patientglobalid;
    }

    public void setPatientglobalid(Long patientglobalid) {
        this.patientglobalid = patientglobalid;
    }

    public String getPatientid() {
        return patientid;
    }

    public void setPatientid(String patientid) {
        this.patientid = patientid == null ? null : patientid.trim();
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

    public String getVisitid() {
        return visitid;
    }

    public void setVisitid(String visitid) {
        this.visitid = visitid == null ? null : visitid.trim();
    }

    public String getInputid() {
        return inputid;
    }

    public void setInputid(String inputid) {
        this.inputid = inputid == null ? null : inputid.trim();
    }

    public String getOutpatid() {
        return outpatid;
    }

    public void setOutpatid(String outpatid) {
        this.outpatid = outpatid == null ? null : outpatid.trim();
    }

    public String getPatientsource() {
        return patientsource;
    }

    public void setPatientsource(String patientsource) {
        this.patientsource = patientsource == null ? null : patientsource.trim();
    }

    public String getApplyno() {
        return applyno;
    }

    public void setApplyno(String applyno) {
        this.applyno = applyno == null ? null : applyno.trim();
    }

    public Date getReqdatetime() {
        return reqdatetime;
    }

    public void setReqdatetime(Date reqdatetime) {
        this.reqdatetime = reqdatetime;
    }

    public String getTestcause() {
        return testcause;
    }

    public void setTestcause(String testcause) {
        this.testcause = testcause == null ? null : testcause.trim();
    }

    public String getSpecimen() {
        return specimen;
    }

    public void setSpecimen(String specimen) {
        this.specimen = specimen == null ? null : specimen.trim();
    }

    public Date getSpcmrecdatetime() {
        return spcmrecdatetime;
    }

    public void setSpcmrecdatetime(Date spcmrecdatetime) {
        this.spcmrecdatetime = spcmrecdatetime;
    }

    public Date getReporttime() {
        return reporttime;
    }

    public void setReporttime(Date reporttime) {
        this.reporttime = reporttime;
    }

    public String getTesttype() {
        return testtype;
    }

    public void setTesttype(String testtype) {
        this.testtype = testtype == null ? null : testtype.trim();
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