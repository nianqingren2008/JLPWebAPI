package com.callan.service.provider.pojo.db;

import java.util.Date;

public class DDischargesummary {
    private Long id;

    private Long patientglobalid;

    private String patientid;

    private String visitid;

    private String modeltype;

    private String modelname;

    private Date cydate;

    private String inhospitalday;

    private String deptname;

    private String deptid;

    private Date lscontentdate;

    private Date jlcreatedate;

    private String jlactiveflag;

    private String blid;

    private String lscontent;

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

    public String getVisitid() {
        return visitid;
    }

    public void setVisitid(String visitid) {
        this.visitid = visitid == null ? null : visitid.trim();
    }

    public String getModeltype() {
        return modeltype;
    }

    public void setModeltype(String modeltype) {
        this.modeltype = modeltype == null ? null : modeltype.trim();
    }

    public String getModelname() {
        return modelname;
    }

    public void setModelname(String modelname) {
        this.modelname = modelname == null ? null : modelname.trim();
    }

    public Date getCydate() {
        return cydate;
    }

    public void setCydate(Date cydate) {
        this.cydate = cydate;
    }

    public String getInhospitalday() {
        return inhospitalday;
    }

    public void setInhospitalday(String inhospitalday) {
        this.inhospitalday = inhospitalday == null ? null : inhospitalday.trim();
    }

    public String getDeptname() {
        return deptname;
    }

    public void setDeptname(String deptname) {
        this.deptname = deptname == null ? null : deptname.trim();
    }

    public String getDeptid() {
        return deptid;
    }

    public void setDeptid(String deptid) {
        this.deptid = deptid == null ? null : deptid.trim();
    }

    public Date getLscontentdate() {
        return lscontentdate;
    }

    public void setLscontentdate(Date lscontentdate) {
        this.lscontentdate = lscontentdate;
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

    public String getBlid() {
        return blid;
    }

    public void setBlid(String blid) {
        this.blid = blid == null ? null : blid.trim();
    }

    public String getLscontent() {
        return lscontent;
    }

    public void setLscontent(String lscontent) {
        this.lscontent = lscontent == null ? null : lscontent.trim();
    }
}