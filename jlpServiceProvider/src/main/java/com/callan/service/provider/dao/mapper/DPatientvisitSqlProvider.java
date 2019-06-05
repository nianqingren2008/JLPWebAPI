package com.callan.service.provider.dao.mapper;

import com.callan.service.provider.pojo.db.DPatientvisit;
import org.apache.ibatis.jdbc.SQL;

public class DPatientvisitSqlProvider {

    public String insertSelective(DPatientvisit record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("D_PATIENTVISIT");
        
        if (record.getId() != null) {
            sql.VALUES("ID", "#{id,jdbcType=DECIMAL}");
        }
        
        if (record.getPatientglobalid() != null) {
            sql.VALUES("PATIENTGLOBALID", "#{patientglobalid,jdbcType=DECIMAL}");
        }
        
        if (record.getPatientid() != null) {
            sql.VALUES("PATIENTID", "#{patientid,jdbcType=VARCHAR}");
        }
        
        if (record.getVisitid() != null) {
            sql.VALUES("VISITID", "#{visitid,jdbcType=VARCHAR}");
        }
        
        if (record.getInpatid() != null) {
            sql.VALUES("INPATID", "#{inpatid,jdbcType=VARCHAR}");
        }
        
        if (record.getRydeptcode() != null) {
            sql.VALUES("RYDEPTCODE", "#{rydeptcode,jdbcType=VARCHAR}");
        }
        
        if (record.getRydept() != null) {
            sql.VALUES("RYDEPT", "#{rydept,jdbcType=VARCHAR}");
        }
        
        if (record.getRydatetime() != null) {
            sql.VALUES("RYDATETIME", "#{rydatetime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getCydeptcode() != null) {
            sql.VALUES("CYDEPTCODE", "#{cydeptcode,jdbcType=VARCHAR}");
        }
        
        if (record.getCydept() != null) {
            sql.VALUES("CYDEPT", "#{cydept,jdbcType=VARCHAR}");
        }
        
        if (record.getCydatetime() != null) {
            sql.VALUES("CYDATETIME", "#{cydatetime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getChiefcomplaint() != null) {
            sql.VALUES("CHIEFCOMPLAINT", "#{chiefcomplaint,jdbcType=VARCHAR}");
        }
        
        if (record.getRechisdate() != null) {
            sql.VALUES("RECHISDATE", "#{rechisdate,jdbcType=TIMESTAMP}");
        }
        
        if (record.getDiagnoserange() != null) {
            sql.VALUES("DIAGNOSERANGE", "#{diagnoserange,jdbcType=VARCHAR}");
        }
        
        if (record.getDiagnosecontent() != null) {
            sql.VALUES("DIAGNOSECONTENT", "#{diagnosecontent,jdbcType=VARCHAR}");
        }
        
        if (record.getAffirmdate() != null) {
            sql.VALUES("AFFIRMDATE", "#{affirmdate,jdbcType=TIMESTAMP}");
        }
        
        if (record.getJlcreatedate() != null) {
            sql.VALUES("JLCREATEDATE", "#{jlcreatedate,jdbcType=TIMESTAMP}");
        }
        
        if (record.getJlactiveflag() != null) {
            sql.VALUES("JLACTIVEFLAG", "#{jlactiveflag,jdbcType=CHAR}");
        }
        
        return sql.toString();
    }

    public String updateByPrimaryKeySelective(DPatientvisit record) {
        SQL sql = new SQL();
        sql.UPDATE("D_PATIENTVISIT");
        
        if (record.getPatientglobalid() != null) {
            sql.SET("PATIENTGLOBALID = #{patientglobalid,jdbcType=DECIMAL}");
        }
        
        if (record.getPatientid() != null) {
            sql.SET("PATIENTID = #{patientid,jdbcType=VARCHAR}");
        }
        
        if (record.getVisitid() != null) {
            sql.SET("VISITID = #{visitid,jdbcType=VARCHAR}");
        }
        
        if (record.getInpatid() != null) {
            sql.SET("INPATID = #{inpatid,jdbcType=VARCHAR}");
        }
        
        if (record.getRydeptcode() != null) {
            sql.SET("RYDEPTCODE = #{rydeptcode,jdbcType=VARCHAR}");
        }
        
        if (record.getRydept() != null) {
            sql.SET("RYDEPT = #{rydept,jdbcType=VARCHAR}");
        }
        
        if (record.getRydatetime() != null) {
            sql.SET("RYDATETIME = #{rydatetime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getCydeptcode() != null) {
            sql.SET("CYDEPTCODE = #{cydeptcode,jdbcType=VARCHAR}");
        }
        
        if (record.getCydept() != null) {
            sql.SET("CYDEPT = #{cydept,jdbcType=VARCHAR}");
        }
        
        if (record.getCydatetime() != null) {
            sql.SET("CYDATETIME = #{cydatetime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getChiefcomplaint() != null) {
            sql.SET("CHIEFCOMPLAINT = #{chiefcomplaint,jdbcType=VARCHAR}");
        }
        
        if (record.getRechisdate() != null) {
            sql.SET("RECHISDATE = #{rechisdate,jdbcType=TIMESTAMP}");
        }
        
        if (record.getDiagnoserange() != null) {
            sql.SET("DIAGNOSERANGE = #{diagnoserange,jdbcType=VARCHAR}");
        }
        
        if (record.getDiagnosecontent() != null) {
            sql.SET("DIAGNOSECONTENT = #{diagnosecontent,jdbcType=VARCHAR}");
        }
        
        if (record.getAffirmdate() != null) {
            sql.SET("AFFIRMDATE = #{affirmdate,jdbcType=TIMESTAMP}");
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