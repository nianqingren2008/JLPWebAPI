package com.callan.service.provider.dao.mapper;

import com.callan.service.provider.pojo.db.JTablefielddict;
import org.apache.ibatis.jdbc.SQL;

public class JTablefielddictSqlProvider {

    public String insertSelective(JTablefielddict record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("J_TABLEFIELDDICT");
        
        if (record.getId() != null) {
            sql.VALUES("ID", "#{id,jdbcType=DECIMAL}");
        }
        
        if (record.getTablecode() != null) {
            sql.VALUES("TABLECODE", "#{tablecode,jdbcType=VARCHAR}");
        }
        
        if (record.getCode() != null) {
            sql.VALUES("CODE", "#{code,jdbcType=VARCHAR}");
        }
        
        if (record.getName() != null) {
            sql.VALUES("NAME", "#{name,jdbcType=VARCHAR}");
        }
        
        if (record.getType() != null) {
            sql.VALUES("TYPE", "#{type,jdbcType=VARCHAR}");
        }
        
        if (record.getDescription() != null) {
            sql.VALUES("DESCRIPTION", "#{description,jdbcType=VARCHAR}");
        }
        
        if (record.getSortno() != null) {
            sql.VALUES("SORTNO", "#{sortno,jdbcType=DECIMAL}");
        }
        
        if (record.getControltypecode() != null) {
            sql.VALUES("CONTROLTYPECODE", "#{controltypecode,jdbcType=VARCHAR}");
        }
        
        if (record.getNullable() != null) {
            sql.VALUES("NULLABLE", "#{nullable,jdbcType=CHAR}");
        }
        
        if (record.getDictname() != null) {
            sql.VALUES("DICTNAME", "#{dictname,jdbcType=VARCHAR}");
        }
        
        if (record.getQueryflag() != null) {
            sql.VALUES("QUERYFLAG", "#{queryflag,jdbcType=CHAR}");
        }
        
        if (record.getShowflag() != null) {
            sql.VALUES("SHOWFLAG", "#{showflag,jdbcType=CHAR}");
        }
        
        if (record.getSortflag() != null) {
            sql.VALUES("SORTFLAG", "#{sortflag,jdbcType=CHAR}");
        }
        
        return sql.toString();
    }

    public String updateByPrimaryKeySelective(JTablefielddict record) {
        SQL sql = new SQL();
        sql.UPDATE("J_TABLEFIELDDICT");
        
        if (record.getTablecode() != null) {
            sql.SET("TABLECODE = #{tablecode,jdbcType=VARCHAR}");
        }
        
        if (record.getCode() != null) {
            sql.SET("CODE = #{code,jdbcType=VARCHAR}");
        }
        
        if (record.getName() != null) {
            sql.SET("NAME = #{name,jdbcType=VARCHAR}");
        }
        
        if (record.getType() != null) {
            sql.SET("TYPE = #{type,jdbcType=VARCHAR}");
        }
        
        if (record.getDescription() != null) {
            sql.SET("DESCRIPTION = #{description,jdbcType=VARCHAR}");
        }
        
        if (record.getSortno() != null) {
            sql.SET("SORTNO = #{sortno,jdbcType=DECIMAL}");
        }
        
        if (record.getControltypecode() != null) {
            sql.SET("CONTROLTYPECODE = #{controltypecode,jdbcType=VARCHAR}");
        }
        
        if (record.getNullable() != null) {
            sql.SET("NULLABLE = #{nullable,jdbcType=CHAR}");
        }
        
        if (record.getDictname() != null) {
            sql.SET("DICTNAME = #{dictname,jdbcType=VARCHAR}");
        }
        
        if (record.getQueryflag() != null) {
            sql.SET("QUERYFLAG = #{queryflag,jdbcType=CHAR}");
        }
        
        if (record.getShowflag() != null) {
            sql.SET("SHOWFLAG = #{showflag,jdbcType=CHAR}");
        }
        
        if (record.getSortflag() != null) {
            sql.SET("SORTFLAG = #{sortflag,jdbcType=CHAR}");
        }
        
        sql.WHERE("ID = #{id,jdbcType=DECIMAL}");
        
        return sql.toString();
    }
}