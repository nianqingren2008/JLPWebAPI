package com.callan.service.provider.dao.mapper;

import com.callan.service.provider.pojo.db.DMedicalmainrecords;
import org.apache.ibatis.jdbc.SQL;

public class DMedicalmainrecordsSqlProvider {

    public String insertSelective(DMedicalmainrecords record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("D_MEDICALMAINRECORDS");
        
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
        
        if (record.getName() != null) {
            sql.VALUES("NAME", "#{name,jdbcType=VARCHAR}");
        }
        
        if (record.getSex() != null) {
            sql.VALUES("SEX", "#{sex,jdbcType=VARCHAR}");
        }
        
        if (record.getBirthday() != null) {
            sql.VALUES("BIRTHDAY", "#{birthday,jdbcType=TIMESTAMP}");
        }
        
        if (record.getAge() != null) {
            sql.VALUES("AGE", "#{age,jdbcType=VARCHAR}");
        }
        
        if (record.getIdcard() != null) {
            sql.VALUES("IDCARD", "#{idcard,jdbcType=VARCHAR}");
        }
        
        if (record.getAddress() != null) {
            sql.VALUES("ADDRESS", "#{address,jdbcType=VARCHAR}");
        }
        
        if (record.getRelationship() != null) {
            sql.VALUES("RELATIONSHIP", "#{relationship,jdbcType=VARCHAR}");
        }
        
        if (record.getRelationcall() != null) {
            sql.VALUES("RELATIONCALL", "#{relationcall,jdbcType=VARCHAR}");
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
        
        if (record.getDiagnoserange() != null) {
            sql.VALUES("DIAGNOSERANGE", "#{diagnoserange,jdbcType=VARCHAR}");
        }
        
        if (record.getDiagnosecontent() != null) {
            sql.VALUES("DIAGNOSECONTENT", "#{diagnosecontent,jdbcType=VARCHAR}");
        }
        
        if (record.getDiagnosecode() != null) {
            sql.VALUES("DIAGNOSECODE", "#{diagnosecode,jdbcType=VARCHAR}");
        }
        
        if (record.getCystatus() != null) {
            sql.VALUES("CYSTATUS", "#{cystatus,jdbcType=VARCHAR}");
        }
        
        if (record.getCystatuscode() != null) {
            sql.VALUES("CYSTATUSCODE", "#{cystatuscode,jdbcType=VARCHAR}");
        }
        
        if (record.getOperationcode() != null) {
            sql.VALUES("OPERATIONCODE", "#{operationcode,jdbcType=VARCHAR}");
        }
        
        if (record.getOperationway() != null) {
            sql.VALUES("OPERATIONWAY", "#{operationway,jdbcType=VARCHAR}");
        }
        
        if (record.getOperationincision() != null) {
            sql.VALUES("OPERATIONINCISION", "#{operationincision,jdbcType=VARCHAR}");
        }
        
        if (record.getOperationhealing() != null) {
            sql.VALUES("OPERATIONHEALING", "#{operationhealing,jdbcType=VARCHAR}");
        }
        
        if (record.getBlid() != null) {
            sql.VALUES("BLID", "#{blid,jdbcType=VARCHAR}");
        }
        
        if (record.getJlcreatedate() != null) {
            sql.VALUES("JLCREATEDATE", "#{jlcreatedate,jdbcType=TIMESTAMP}");
        }
        
        if (record.getJlactiveflag() != null) {
            sql.VALUES("JLACTIVEFLAG", "#{jlactiveflag,jdbcType=CHAR}");
        }
        
        return sql.toString();
    }

    public String updateByPrimaryKeySelective(DMedicalmainrecords record) {
        SQL sql = new SQL();
        sql.UPDATE("D_MEDICALMAINRECORDS");
        
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
        
        if (record.getName() != null) {
            sql.SET("NAME = #{name,jdbcType=VARCHAR}");
        }
        
        if (record.getSex() != null) {
            sql.SET("SEX = #{sex,jdbcType=VARCHAR}");
        }
        
        if (record.getBirthday() != null) {
            sql.SET("BIRTHDAY = #{birthday,jdbcType=TIMESTAMP}");
        }
        
        if (record.getAge() != null) {
            sql.SET("AGE = #{age,jdbcType=VARCHAR}");
        }
        
        if (record.getIdcard() != null) {
            sql.SET("IDCARD = #{idcard,jdbcType=VARCHAR}");
        }
        
        if (record.getAddress() != null) {
            sql.SET("ADDRESS = #{address,jdbcType=VARCHAR}");
        }
        
        if (record.getRelationship() != null) {
            sql.SET("RELATIONSHIP = #{relationship,jdbcType=VARCHAR}");
        }
        
        if (record.getRelationcall() != null) {
            sql.SET("RELATIONCALL = #{relationcall,jdbcType=VARCHAR}");
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
        
        if (record.getDiagnoserange() != null) {
            sql.SET("DIAGNOSERANGE = #{diagnoserange,jdbcType=VARCHAR}");
        }
        
        if (record.getDiagnosecontent() != null) {
            sql.SET("DIAGNOSECONTENT = #{diagnosecontent,jdbcType=VARCHAR}");
        }
        
        if (record.getDiagnosecode() != null) {
            sql.SET("DIAGNOSECODE = #{diagnosecode,jdbcType=VARCHAR}");
        }
        
        if (record.getCystatus() != null) {
            sql.SET("CYSTATUS = #{cystatus,jdbcType=VARCHAR}");
        }
        
        if (record.getCystatuscode() != null) {
            sql.SET("CYSTATUSCODE = #{cystatuscode,jdbcType=VARCHAR}");
        }
        
        if (record.getOperationcode() != null) {
            sql.SET("OPERATIONCODE = #{operationcode,jdbcType=VARCHAR}");
        }
        
        if (record.getOperationway() != null) {
            sql.SET("OPERATIONWAY = #{operationway,jdbcType=VARCHAR}");
        }
        
        if (record.getOperationincision() != null) {
            sql.SET("OPERATIONINCISION = #{operationincision,jdbcType=VARCHAR}");
        }
        
        if (record.getOperationhealing() != null) {
            sql.SET("OPERATIONHEALING = #{operationhealing,jdbcType=VARCHAR}");
        }
        
        if (record.getBlid() != null) {
            sql.SET("BLID = #{blid,jdbcType=VARCHAR}");
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