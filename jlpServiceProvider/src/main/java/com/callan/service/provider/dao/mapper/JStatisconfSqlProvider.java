package com.callan.service.provider.dao.mapper;

import com.callan.service.provider.pojo.db.JStatisconf;
import org.apache.ibatis.jdbc.SQL;

public class JStatisconfSqlProvider {

    public String insertSelective(JStatisconf record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("J_STATISCONF");
        
        if (record.getId() != null) {
            sql.VALUES("ID", "#{id,jdbcType=DECIMAL}");
        }
        
        if (record.getCode() != null) {
            sql.VALUES("CODE", "#{code,jdbcType=VARCHAR}");
        }
        
        if (record.getTitle() != null) {
            sql.VALUES("TITLE", "#{title,jdbcType=VARCHAR}");
        }
        
        if (record.getCreatedate() != null) {
            sql.VALUES("CREATEDATE", "#{createdate,jdbcType=TIMESTAMP}");
        }
        
        if (record.getActiveflag() != null) {
            sql.VALUES("ACTIVEFLAG", "#{activeflag,jdbcType=CHAR}");
        }
        
        if (record.getPagecode() != null) {
            sql.VALUES("PAGECODE", "#{pagecode,jdbcType=VARCHAR}");
        }
        
        if (record.getPagetitle() != null) {
            sql.VALUES("PAGETITLE", "#{pagetitle,jdbcType=VARCHAR}");
        }
        
        return sql.toString();
    }

    public String updateByPrimaryKeySelective(JStatisconf record) {
        SQL sql = new SQL();
        sql.UPDATE("J_STATISCONF");
        
        if (record.getCode() != null) {
            sql.SET("CODE = #{code,jdbcType=VARCHAR}");
        }
        
        if (record.getTitle() != null) {
            sql.SET("TITLE = #{title,jdbcType=VARCHAR}");
        }
        
        if (record.getCreatedate() != null) {
            sql.SET("CREATEDATE = #{createdate,jdbcType=TIMESTAMP}");
        }
        
        if (record.getActiveflag() != null) {
            sql.SET("ACTIVEFLAG = #{activeflag,jdbcType=CHAR}");
        }
        
        if (record.getPagecode() != null) {
            sql.SET("PAGECODE = #{pagecode,jdbcType=VARCHAR}");
        }
        
        if (record.getPagetitle() != null) {
            sql.SET("PAGETITLE = #{pagetitle,jdbcType=VARCHAR}");
        }
        
        sql.WHERE("ID = #{id,jdbcType=DECIMAL}");
        
        return sql.toString();
    }
}