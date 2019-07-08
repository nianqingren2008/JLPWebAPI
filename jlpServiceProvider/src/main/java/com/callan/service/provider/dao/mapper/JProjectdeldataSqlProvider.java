package com.callan.service.provider.dao.mapper;

import com.callan.service.provider.pojo.db.JProjectdeldata;
import org.apache.ibatis.jdbc.SQL;

public class JProjectdeldataSqlProvider {

    public String insertSelective(JProjectdeldata record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("J_PROJECTDELDATA");
        
        if (record.getId() != null) {
            sql.VALUES("ID", "#{id,jdbcType=DECIMAL}");
        }
        
        if (record.getUserid() != null) {
            sql.VALUES("USERID", "#{userid,jdbcType=DECIMAL}");
        }
        
        if (record.getProjectid() != null) {
            sql.VALUES("PROJECTID", "#{projectid,jdbcType=DECIMAL}");
        }
        
        if (record.getTableid() != null) {
            sql.VALUES("TABLEID", "#{tableid,jdbcType=DECIMAL}");
        }
        
        if (record.getPatientglobalid() != null) {
            sql.VALUES("PATIENTGLOBALID", "#{patientglobalid,jdbcType=DECIMAL}");
        }
        
        if (record.getDataid() != null) {
            sql.VALUES("DATAID", "#{dataid,jdbcType=DECIMAL}");
        }
        
        if (record.getActiveflag() != null) {
            sql.VALUES("ACTIVEFLAG", "#{activeflag,jdbcType=CHAR}");
        }
        
        if (record.getCreatedate() != null) {
            sql.VALUES("CREATEDATE", "#{createdate,jdbcType=TIMESTAMP}");
        }
        
        return sql.toString();
    }

    public String updateByPrimaryKeySelective(JProjectdeldata record) {
        SQL sql = new SQL();
        sql.UPDATE("J_PROJECTDELDATA");
        
        if (record.getUserid() != null) {
            sql.SET("USERID = #{userid,jdbcType=DECIMAL}");
        }
        
        if (record.getProjectid() != null) {
            sql.SET("PROJECTID = #{projectid,jdbcType=DECIMAL}");
        }
        
        if (record.getTableid() != null) {
            sql.SET("TABLEID = #{tableid,jdbcType=DECIMAL}");
        }
        
        if (record.getPatientglobalid() != null) {
            sql.SET("PATIENTGLOBALID = #{patientglobalid,jdbcType=DECIMAL}");
        }
        
        if (record.getDataid() != null) {
            sql.SET("DATAID = #{dataid,jdbcType=DECIMAL}");
        }
        
        if (record.getActiveflag() != null) {
            sql.SET("ACTIVEFLAG = #{activeflag,jdbcType=CHAR}");
        }
        
        if (record.getCreatedate() != null) {
            sql.SET("CREATEDATE = #{createdate,jdbcType=TIMESTAMP}");
        }
        
        sql.WHERE("ID = #{id,jdbcType=DECIMAL}");
        
        return sql.toString();
    }
}