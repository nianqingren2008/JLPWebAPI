package com.callan.service.provider.dao.mapper;

import com.callan.service.provider.pojo.db.DLabmasterinfo;
import org.apache.ibatis.jdbc.SQL;

public class DLabmasterinfoSqlProvider {

    public String insertSelective(DLabmasterinfo record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("D_LABMASTERINFO");
        
        if (record.getId() != null) {
            sql.VALUES("ID", "#{id,jdbcType=DECIMAL}");
        }
        
        if (record.getPatientglobalid() != null) {
            sql.VALUES("PATIENTGLOBALID", "#{patientglobalid,jdbcType=DECIMAL}");
        }
        
        if (record.getPatientid() != null) {
            sql.VALUES("PATIENTID", "#{patientid,jdbcType=VARCHAR}");
        }
        
        if (record.getName() != null) {
            sql.VALUES("NAME", "#{name,jdbcType=VARCHAR}");
        }
        
        if (record.getBirthday() != null) {
            sql.VALUES("BIRTHDAY", "#{birthday,jdbcType=TIMESTAMP}");
        }
        
        if (record.getVisitid() != null) {
            sql.VALUES("VISITID", "#{visitid,jdbcType=VARCHAR}");
        }
        
        if (record.getInputid() != null) {
            sql.VALUES("INPUTID", "#{inputid,jdbcType=VARCHAR}");
        }
        
        if (record.getOutpatid() != null) {
            sql.VALUES("OUTPATID", "#{outpatid,jdbcType=VARCHAR}");
        }
        
        if (record.getPatientsource() != null) {
            sql.VALUES("PATIENTSOURCE", "#{patientsource,jdbcType=VARCHAR}");
        }
        
        if (record.getApplyno() != null) {
            sql.VALUES("APPLYNO", "#{applyno,jdbcType=VARCHAR}");
        }
        
        if (record.getReqdatetime() != null) {
            sql.VALUES("REQDATETIME", "#{reqdatetime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getTestcause() != null) {
            sql.VALUES("TESTCAUSE", "#{testcause,jdbcType=VARCHAR}");
        }
        
        if (record.getSpecimen() != null) {
            sql.VALUES("SPECIMEN", "#{specimen,jdbcType=VARCHAR}");
        }
        
        if (record.getSpcmrecdatetime() != null) {
            sql.VALUES("SPCMRECDATETIME", "#{spcmrecdatetime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getReporttime() != null) {
            sql.VALUES("REPORTTIME", "#{reporttime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getTesttype() != null) {
            sql.VALUES("TESTTYPE", "#{testtype,jdbcType=VARCHAR}");
        }
        
        if (record.getJlcreatedate() != null) {
            sql.VALUES("JLCREATEDATE", "#{jlcreatedate,jdbcType=TIMESTAMP}");
        }
        
        if (record.getJlactiveflag() != null) {
            sql.VALUES("JLACTIVEFLAG", "#{jlactiveflag,jdbcType=CHAR}");
        }
        
        return sql.toString();
    }

    public String updateByPrimaryKeySelective(DLabmasterinfo record) {
        SQL sql = new SQL();
        sql.UPDATE("D_LABMASTERINFO");
        
        if (record.getPatientglobalid() != null) {
            sql.SET("PATIENTGLOBALID = #{patientglobalid,jdbcType=DECIMAL}");
        }
        
        if (record.getPatientid() != null) {
            sql.SET("PATIENTID = #{patientid,jdbcType=VARCHAR}");
        }
        
        if (record.getName() != null) {
            sql.SET("NAME = #{name,jdbcType=VARCHAR}");
        }
        
        if (record.getBirthday() != null) {
            sql.SET("BIRTHDAY = #{birthday,jdbcType=TIMESTAMP}");
        }
        
        if (record.getVisitid() != null) {
            sql.SET("VISITID = #{visitid,jdbcType=VARCHAR}");
        }
        
        if (record.getInputid() != null) {
            sql.SET("INPUTID = #{inputid,jdbcType=VARCHAR}");
        }
        
        if (record.getOutpatid() != null) {
            sql.SET("OUTPATID = #{outpatid,jdbcType=VARCHAR}");
        }
        
        if (record.getPatientsource() != null) {
            sql.SET("PATIENTSOURCE = #{patientsource,jdbcType=VARCHAR}");
        }
        
        if (record.getApplyno() != null) {
            sql.SET("APPLYNO = #{applyno,jdbcType=VARCHAR}");
        }
        
        if (record.getReqdatetime() != null) {
            sql.SET("REQDATETIME = #{reqdatetime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getTestcause() != null) {
            sql.SET("TESTCAUSE = #{testcause,jdbcType=VARCHAR}");
        }
        
        if (record.getSpecimen() != null) {
            sql.SET("SPECIMEN = #{specimen,jdbcType=VARCHAR}");
        }
        
        if (record.getSpcmrecdatetime() != null) {
            sql.SET("SPCMRECDATETIME = #{spcmrecdatetime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getReporttime() != null) {
            sql.SET("REPORTTIME = #{reporttime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getTesttype() != null) {
            sql.SET("TESTTYPE = #{testtype,jdbcType=VARCHAR}");
        }
        
        if (record.getJlcreatedate() != null) {
            sql.SET("JLCREATEDATE = #{jlcreatedate,jdbcType=TIMESTAMP}");
        }
        
        if (record.getJlactiveflag() != null) {
            sql.SET("JLACTIVEFLAG = #{jlactiveflag,jdbcType=CHAR}");
        }
        
        sql.WHERE("ID = #{id,jdbcType=DECIMAL}");
        
        return sql.toString();
    }
}