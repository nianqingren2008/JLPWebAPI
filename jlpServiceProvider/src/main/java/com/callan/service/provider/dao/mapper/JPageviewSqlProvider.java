package com.callan.service.provider.dao.mapper;

import com.callan.service.provider.pojo.db.JPageview;
import org.apache.ibatis.jdbc.SQL;

public class JPageviewSqlProvider {

    public String insertSelective(JPageview record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("J_PAGEVIEW");
        
        if (record.getCode() != null) {
            sql.VALUES("CODE", "#{code,jdbcType=VARCHAR}");
        }
        
        if (record.getPagename() != null) {
            sql.VALUES("PAGENAME", "#{pagename,jdbcType=VARCHAR}");
        }
        
        if (record.getActiveflag() != null) {
            sql.VALUES("ACTIVEFLAG", "#{activeflag,jdbcType=CHAR}");
        }
        
        return sql.toString();
    }

    public String updateByPrimaryKeySelective(JPageview record) {
        SQL sql = new SQL();
        sql.UPDATE("J_PAGEVIEW");
        
        if (record.getPagename() != null) {
            sql.SET("PAGENAME = #{pagename,jdbcType=VARCHAR}");
        }
        
        if (record.getActiveflag() != null) {
            sql.SET("ACTIVEFLAG = #{activeflag,jdbcType=CHAR}");
        }
        
        sql.WHERE("CODE = #{code,jdbcType=VARCHAR}");
        
        return sql.toString();
    }
}