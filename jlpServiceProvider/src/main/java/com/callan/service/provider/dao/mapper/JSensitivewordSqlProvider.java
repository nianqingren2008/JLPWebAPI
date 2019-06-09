package com.callan.service.provider.dao.mapper;

import org.apache.ibatis.jdbc.SQL;

import com.callan.service.provider.pojo.db.JSensitiveWord;

public class JSensitivewordSqlProvider {

    public String insertSelective(JSensitiveWord record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("J_SENSITIVEWORD");
        
        if (record.getId() != null) {
            sql.VALUES("ID", "#{id,jdbcType=DECIMAL}");
        }
        
        if (record.getName() != null) {
            sql.VALUES("NAME", "#{name,jdbcType=VARCHAR}");
        }
        
        if (record.getActiveflag() != null) {
            sql.VALUES("ACTIVEFLAG", "#{activeflag,jdbcType=CHAR}");
        }
        
        if (record.getCreatedate() != null) {
            sql.VALUES("CREATEDATE", "#{createdate,jdbcType=TIMESTAMP}");
        }
        
        return sql.toString();
    }

    public String updateByPrimaryKeySelective(JSensitiveWord record) {
        SQL sql = new SQL();
        sql.UPDATE("J_SENSITIVEWORD");
        
        if (record.getName() != null) {
            sql.SET("NAME = #{name,jdbcType=VARCHAR}");
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