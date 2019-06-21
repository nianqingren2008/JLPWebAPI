package com.callan.service.provider.pojo.db;

import java.util.Date;

public class DPatientvisit {
    private Long id;

    private String patientglobalid;

    private String patientid;

    private String visitid;

    private String inpatid;

    private String rydeptcode;

    private String rydept;

    private Date rydatetime;

    private String cydeptcode;

    private String cydept;

    private Date cydatetime;

    private String chiefcomplaint;

    private Date rechisdate;

    private String diagnoserange;

    private String diagnosecontent;

    private Date affirmdate;

    private Date jlcreatedate;

    private String jlactiveflag;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPatientglobalid() {
        return patientglobalid;
    }

    public void setPatientglobalid(String patientglobalid) {
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

    public String getInpatid() {
        return inpatid;
    }

    public void setInpatid(String inpatid) {
        this.inpatid = inpatid == null ? null : inpatid.trim();
    }

    public String getRydeptcode() {
        return rydeptcode;
    }

    public void setRydeptcode(String rydeptcode) {
        this.rydeptcode = rydeptcode == null ? null : rydeptcode.trim();
    }

    public String getRydept() {
        return rydept;
    }

    public void setRydept(String rydept) {
        this.rydept = rydept == null ? null : rydept.trim();
    }

    public Date getRydatetime() {
        return rydatetime;
    }

    public void setRydatetime(Date rydatetime) {
        this.rydatetime = rydatetime;
    }

    public String getCydeptcode() {
        return cydeptcode;
    }

    public void setCydeptcode(String cydeptcode) {
        this.cydeptcode = cydeptcode == null ? null : cydeptcode.trim();
    }

    public String getCydept() {
        return cydept;
    }

    public void setCydept(String cydept) {
        this.cydept = cydept == null ? null : cydept.trim();
    }

    public Date getCydatetime() {
        return cydatetime;
    }

    public void setCydatetime(Date cydatetime) {
        this.cydatetime = cydatetime;
    }

    public String getChiefcomplaint() {
        return chiefcomplaint;
    }

    public void setChiefcomplaint(String chiefcomplaint) {
        this.chiefcomplaint = chiefcomplaint == null ? null : chiefcomplaint.trim();
    }

    public Date getRechisdate() {
        return rechisdate;
    }

    public void setRechisdate(Date rechisdate) {
        this.rechisdate = rechisdate;
    }

    public String getDiagnoserange() {
        return diagnoserange;
    }

    public void setDiagnoserange(String diagnoserange) {
        this.diagnoserange = diagnoserange == null ? null : diagnoserange.trim();
    }

    public String getDiagnosecontent() {
        return diagnosecontent;
    }

    public void setDiagnosecontent(String diagnosecontent) {
        this.diagnosecontent = diagnosecontent == null ? null : diagnosecontent.trim();
    }

    public Date getAffirmdate() {
        return affirmdate;
    }

    public void setAffirmdate(Date affirmdate) {
        this.affirmdate = affirmdate;
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