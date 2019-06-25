package com.callan.service.provider.dao.mapper;

import com.callan.service.provider.pojo.db.JExportdataclass;
import org.apache.ibatis.jdbc.SQL;

public class JExportdataclassSqlProvider {

    public String insertSelective(JExportdataclass record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("J_EXPORTDATACLASS");
        
        if (record.getId() != null) {
            sql.VALUES("ID", "#{id,jdbcType=DECIMAL}");
        }
        
        if (record.getTitle() != null) {
            sql.VALUES("TITLE", "#{title,jdbcType=VARCHAR}");
        }
        
        if (record.getInfo() != null) {
            sql.VALUES("INFO", "#{info,jdbcType=VARCHAR}");
        }
        
        if (record.getCreatedate() != null) {
            sql.VALUES("CREATEDATE", "#{createdate,jdbcType=TIMESTAMP}");
        }
        
        if (record.getActiveglag() != null) {
            sql.VALUES("ACTIVEGLAG", "#{activeglag,jdbcType=CHAR}");
        }
        
        if (record.getType() != null) {
            sql.VALUES("TYPE", "#{type,jdbcType=CHAR}");
        }
        
        return sql.toString();
    }

    public String updateByPrimaryKeySelective(JExportdataclass record) {
        SQL sql = new SQL();
        sql.UPDATE("J_EXPORTDATACLASS");
        
        if (record.getTitle() != null) {
            sql.SET("TITLE = #{title,jdbcType=VARCHAR}");
        }
        
        if (record.getInfo() != null) {
            sql.SET("INFO = #{info,jdbcType=VARCHAR}");
        }
        
        if (record.getCreatedate() != null) {
            sql.SET("CREATEDATE = #{createdate,jdbcType=TIMESTAMP}");
        }
        
        if (record.getActiveglag() != null) {
            sql.SET("ACTIVEGLAG = #{activeglag,jdbcType=CHAR}");
        }
        
        if (record.getType() != null) {
            sql.SET("TYPE = #{type,jdbcType=CHAR}");
        }
        
        sql.WHERE("ID = #{id,jdbcType=DECIMAL}");
        
        return sql.toString();
    }
}