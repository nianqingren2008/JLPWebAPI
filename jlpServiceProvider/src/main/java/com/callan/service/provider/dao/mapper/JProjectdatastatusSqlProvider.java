package com.callan.service.provider.dao.mapper;

import com.callan.service.provider.pojo.db.JProjectdatastatus;
import org.apache.ibatis.jdbc.SQL;

public class JProjectdatastatusSqlProvider {

    public String insertSelective(JProjectdatastatus record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("J_PROJECTDATASTATUS");
        
        if (record.getId() != null) {
            sql.VALUES("ID", "#{id,jdbcType=DECIMAL}");
        }
        
        if (record.getUserid() != null) {
            sql.VALUES("USERID", "#{userid,jdbcType=DECIMAL}");
        }
        
        if (record.getProjectid() != null) {
            sql.VALUES("PROJECTID", "#{projectid,jdbcType=DECIMAL}");
        }
        
        if (record.getPatientglobalid() != null) {
            sql.VALUES("PATIENTGLOBALID", "#{patientglobalid,jdbcType=DECIMAL}");
        }
        
        if (record.getStatus() != null) {
            sql.VALUES("STATUS", "#{status,jdbcType=DECIMAL}");
        }
        
        if (record.getActiveflag() != null) {
            sql.VALUES("ACTIVEFLAG", "#{activeflag,jdbcType=CHAR}");
        }
        
        if (record.getCreatedate() != null) {
            sql.VALUES("CREATEDATE", "#{createdate,jdbcType=TIMESTAMP}");
        }
        
        if (record.getChangestatus() != null) {
            sql.VALUES("CHANGESTATUS", "#{changestatus,jdbcType=CHAR}");
        }
        
        return sql.toString();
    }

    public String updateByPrimaryKeySelective(JProjectdatastatus record) {
        SQL sql = new SQL();
        sql.UPDATE("J_PROJECTDATASTATUS");
        
        if (record.getUserid() != null) {
            sql.SET("USERID = #{userid,jdbcType=DECIMAL}");
        }
        
        if (record.getProjectid() != null) {
            sql.SET("PROJECTID = #{projectid,jdbcType=DECIMAL}");
        }
        
        if (record.getPatientglobalid() != null) {
            sql.SET("PATIENTGLOBALID = #{patientglobalid,jdbcType=DECIMAL}");
        }
        
        if (record.getStatus() != null) {
            sql.SET("STATUS = #{status,jdbcType=DECIMAL}");
        }
        
        if (record.getActiveflag() != null) {
            sql.SET("ACTIVEFLAG = #{activeflag,jdbcType=CHAR}");
        }
        
        if (record.getCreatedate() != null) {
            sql.SET("CREATEDATE = #{createdate,jdbcType=TIMESTAMP}");
        }
        
        if (record.getChangestatus() != null) {
            sql.SET("CHANGESTATUS = #{changestatus,jdbcType=CHAR}");
        }
        
        sql.WHERE("ID = #{id,jdbcType=DECIMAL}");
        
        return sql.toString();
    }
}