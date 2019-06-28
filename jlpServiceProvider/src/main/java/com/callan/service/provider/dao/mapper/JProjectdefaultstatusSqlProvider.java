package com.callan.service.provider.dao.mapper;

import com.callan.service.provider.pojo.db.JProjectdefaultstatus;
import org.apache.ibatis.jdbc.SQL;

public class JProjectdefaultstatusSqlProvider {

    public String insertSelective(JProjectdefaultstatus record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("J_PROJECTDEFAULTSTATUS");
        
        if (record.getId() != null) {
            sql.VALUES("ID", "#{id,jdbcType=DECIMAL}");
        }
        
        if (record.getProjectid() != null) {
            sql.VALUES("PROJECTID", "#{projectid,jdbcType=DECIMAL}");
        }
        
        if (record.getDatastatusid() != null) {
            sql.VALUES("DATASTATUSID", "#{datastatusid,jdbcType=DECIMAL}");
        }
        
        if (record.getCreatedate() != null) {
            sql.VALUES("CREATEDATE", "#{createdate,jdbcType=TIMESTAMP}");
        }
        
        if (record.getActiveflag() != null) {
            sql.VALUES("ACTIVEFLAG", "#{activeflag,jdbcType=CHAR}");
        }
        
        return sql.toString();
    }

    public String updateByPrimaryKeySelective(JProjectdefaultstatus record) {
        SQL sql = new SQL();
        sql.UPDATE("J_PROJECTDEFAULTSTATUS");
        
        if (record.getProjectid() != null) {
            sql.SET("PROJECTID = #{projectid,jdbcType=DECIMAL}");
        }
        
        if (record.getDatastatusid() != null) {
            sql.SET("DATASTATUSID = #{datastatusid,jdbcType=DECIMAL}");
        }
        
        if (record.getCreatedate() != null) {
            sql.SET("CREATEDATE = #{createdate,jdbcType=TIMESTAMP}");
        }
        
        if (record.getActiveflag() != null) {
            sql.SET("ACTIVEFLAG = #{activeflag,jdbcType=CHAR}");
        }
        
        sql.WHERE("ID = #{id,jdbcType=DECIMAL}");
        
        return sql.toString();
    }
}