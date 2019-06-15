package com.callan.service.provider.dao.mapper;

import com.callan.service.provider.pojo.db.DMedicalrecords;
import org.apache.ibatis.jdbc.SQL;

public class DMedicalrecordsSqlProvider {

    public String insertSelective(DMedicalrecords record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("D_MEDICALRECORDS");
        
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
        
        if (record.getRechisdate() != null) {
            sql.VALUES("RECHISDATE", "#{rechisdate,jdbcType=TIMESTAMP}");
        }
        
        if (record.getJlcreatedate() != null) {
            sql.VALUES("JLCREATEDATE", "#{jlcreatedate,jdbcType=TIMESTAMP}");
        }
        
        if (record.getJlactiveflag() != null) {
            sql.VALUES("JLACTIVEFLAG", "#{jlactiveflag,jdbcType=CHAR}");
        }
        
        if (record.getBlid() != null) {
            sql.VALUES("BLID", "#{blid,jdbcType=VARCHAR}");
        }
        
        if (record.getMedicalrecords() != null) {
            sql.VALUES("MEDICALRECORDS", "#{medicalrecords,jdbcType=CLOB}");
        }
        
        return sql.toString();
    }

    public String updateByPrimaryKeySelective(DMedicalrecords record) {
        SQL sql = new SQL();
        sql.UPDATE("D_MEDICALRECORDS");
        
        if (record.getPatientglobalid() != null) {
            sql.SET("PATIENTGLOBALID = #{patientglobalid,jdbcType=DECIMAL}");
        }
        
        if (record.getPatientid() != null) {
            sql.SET("PATIENTID = #{patientid,jdbcType=VARCHAR}");
        }
        
        if (record.getVisitid() != null) {
            sql.SET("VISITID = #{visitid,jdbcType=VARCHAR}");
        }
        
        if (record.getRechisdate() != null) {
            sql.SET("RECHISDATE = #{rechisdate,jdbcType=TIMESTAMP}");
        }
        
        if (record.getJlcreatedate() != null) {
            sql.SET("JLCREATEDATE = #{jlcreatedate,jdbcType=TIMESTAMP}");
        }
        
        if (record.getJlactiveflag() != null) {
            sql.SET("JLACTIVEFLAG = #{jlactiveflag,jdbcType=CHAR}");
        }
        
        if (record.getBlid() != null) {
            sql.SET("BLID = #{blid,jdbcType=VARCHAR}");
        }
        
        if (record.getMedicalrecords() != null) {
            sql.SET("MEDICALRECORDS = #{medicalrecords,jdbcType=CLOB}");
        }
        
        sql.WHERE("ID = #{id,jdbcType=DECIMAL}");
        
        return sql.toString();
    }
}