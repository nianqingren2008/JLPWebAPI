package com.callan.service.provider.dao.mapper;

import com.callan.service.provider.pojo.db.DDischargesummary;
import org.apache.ibatis.jdbc.SQL;

public class DDischargesummarySqlProvider {

    public String insertSelective(DDischargesummary record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("D_DISCHARGESUMMARY");
        
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
        
        if (record.getModeltype() != null) {
            sql.VALUES("MODELTYPE", "#{modeltype,jdbcType=VARCHAR}");
        }
        
        if (record.getModelname() != null) {
            sql.VALUES("MODELNAME", "#{modelname,jdbcType=VARCHAR}");
        }
        
        if (record.getCydate() != null) {
            sql.VALUES("CYDATE", "#{cydate,jdbcType=TIMESTAMP}");
        }
        
        if (record.getInhospitalday() != null) {
            sql.VALUES("INHOSPITALDAY", "#{inhospitalday,jdbcType=VARCHAR}");
        }
        
        if (record.getDeptname() != null) {
            sql.VALUES("DEPTNAME", "#{deptname,jdbcType=VARCHAR}");
        }
        
        if (record.getDeptid() != null) {
            sql.VALUES("DEPTID", "#{deptid,jdbcType=VARCHAR}");
        }
        
        if (record.getLscontentdate() != null) {
            sql.VALUES("LSCONTENTDATE", "#{lscontentdate,jdbcType=TIMESTAMP}");
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
        
        if (record.getLscontent() != null) {
            sql.VALUES("LSCONTENT", "#{lscontent,jdbcType=CLOB}");
        }
        
        return sql.toString();
    }

    public String updateByPrimaryKeySelective(DDischargesummary record) {
        SQL sql = new SQL();
        sql.UPDATE("D_DISCHARGESUMMARY");
        
        if (record.getPatientglobalid() != null) {
            sql.SET("PATIENTGLOBALID = #{patientglobalid,jdbcType=DECIMAL}");
        }
        
        if (record.getPatientid() != null) {
            sql.SET("PATIENTID = #{patientid,jdbcType=VARCHAR}");
        }
        
        if (record.getVisitid() != null) {
            sql.SET("VISITID = #{visitid,jdbcType=VARCHAR}");
        }
        
        if (record.getModeltype() != null) {
            sql.SET("MODELTYPE = #{modeltype,jdbcType=VARCHAR}");
        }
        
        if (record.getModelname() != null) {
            sql.SET("MODELNAME = #{modelname,jdbcType=VARCHAR}");
        }
        
        if (record.getCydate() != null) {
            sql.SET("CYDATE = #{cydate,jdbcType=TIMESTAMP}");
        }
        
        if (record.getInhospitalday() != null) {
            sql.SET("INHOSPITALDAY = #{inhospitalday,jdbcType=VARCHAR}");
        }
        
        if (record.getDeptname() != null) {
            sql.SET("DEPTNAME = #{deptname,jdbcType=VARCHAR}");
        }
        
        if (record.getDeptid() != null) {
            sql.SET("DEPTID = #{deptid,jdbcType=VARCHAR}");
        }
        
        if (record.getLscontentdate() != null) {
            sql.SET("LSCONTENTDATE = #{lscontentdate,jdbcType=TIMESTAMP}");
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
        
        if (record.getLscontent() != null) {
            sql.SET("LSCONTENT = #{lscontent,jdbcType=CLOB}");
        }
        
        sql.WHERE("ID = #{id,jdbcType=DECIMAL}");
        
        return sql.toString();
    }
}