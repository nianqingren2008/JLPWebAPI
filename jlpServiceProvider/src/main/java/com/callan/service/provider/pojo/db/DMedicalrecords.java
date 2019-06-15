package com.callan.service.provider.pojo.db;

import java.util.Date;

public class DMedicalrecords {
    private Long id;

    private Long patientglobalid;

    private String patientid;

    private String visitid;

    private Date rechisdate;

    private Date jlcreatedate;

    private String jlactiveflag;

    private String blid;

    private String medicalrecords;

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

    public Date getRechisdate() {
        return rechisdate;
    }

    public void setRechisdate(Date rechisdate) {
        this.rechisdate = rechisdate;
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

    public String getMedicalrecords() {
        return medicalrecords;
    }

    public void setMedicalrecords(String medicalrecords) {
        this.medicalrecords = medicalrecords == null ? null : medicalrecords.trim();
    }
}