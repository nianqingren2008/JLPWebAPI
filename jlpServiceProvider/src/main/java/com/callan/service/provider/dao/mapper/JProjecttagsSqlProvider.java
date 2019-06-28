package com.callan.service.provider.dao.mapper;

import com.callan.service.provider.pojo.db.JProjecttags;
import org.apache.ibatis.jdbc.SQL;

public class JProjecttagsSqlProvider {

    public String insertSelective(JProjecttags record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("J_PROJECTTAGS");
        
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
        
        if (record.getDataid() != null) {
            sql.VALUES("DATAID", "#{dataid,jdbcType=DECIMAL}");
        }
        
        if (record.getTagid() != null) {
            sql.VALUES("TAGID", "#{tagid,jdbcType=DECIMAL}");
        }
        
        if (record.getTagvalue() != null) {
            sql.VALUES("TAGVALUE", "#{tagvalue,jdbcType=VARCHAR}");
        }
        
        if (record.getActiveflag() != null) {
            sql.VALUES("ACTIVEFLAG", "#{activeflag,jdbcType=CHAR}");
        }
        
        if (record.getCreatedate() != null) {
            sql.VALUES("CREATEDATE", "#{createdate,jdbcType=TIMESTAMP}");
        }
        
        if (record.getPatientglobalid() != null) {
            sql.VALUES("PATIENTGLOBALID", "#{patientglobalid,jdbcType=DECIMAL}");
        }
        
        return sql.toString();
    }

    public String updateByPrimaryKeySelective(JProjecttags record) {
        SQL sql = new SQL();
        sql.UPDATE("J_PROJECTTAGS");
        
        if (record.getUserid() != null) {
            sql.SET("USERID = #{userid,jdbcType=DECIMAL}");
        }
        
        if (record.getProjectid() != null) {
            sql.SET("PROJECTID = #{projectid,jdbcType=DECIMAL}");
        }
        
        if (record.getTableid() != null) {
            sql.SET("TABLEID = #{tableid,jdbcType=DECIMAL}");
        }
        
        if (record.getDataid() != null) {
            sql.SET("DATAID = #{dataid,jdbcType=DECIMAL}");
        }
        
        if (record.getTagid() != null) {
            sql.SET("TAGID = #{tagid,jdbcType=DECIMAL}");
        }
        
        if (record.getTagvalue() != null) {
            sql.SET("TAGVALUE = #{tagvalue,jdbcType=VARCHAR}");
        }
        
        if (record.getActiveflag() != null) {
            sql.SET("ACTIVEFLAG = #{activeflag,jdbcType=CHAR}");
        }
        
        if (record.getCreatedate() != null) {
            sql.SET("CREATEDATE = #{createdate,jdbcType=TIMESTAMP}");
        }
        
        if (record.getPatientglobalid() != null) {
            sql.SET("PATIENTGLOBALID = #{patientglobalid,jdbcType=DECIMAL}");
        }
        
        sql.WHERE("ID = #{id,jdbcType=DECIMAL}");
        
        return sql.toString();
    }
}