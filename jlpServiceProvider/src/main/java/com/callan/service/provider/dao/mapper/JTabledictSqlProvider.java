package com.callan.service.provider.dao.mapper;

import com.callan.service.provider.pojo.db.JTableDict;
import org.apache.ibatis.jdbc.SQL;

public class JTabledictSqlProvider {

    public String insertSelective(JTableDict record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("J_TABLEDICT");
        
        if (record.getId() != null) {
            sql.VALUES("ID", "#{id,jdbcType=DECIMAL}");
        }
        
        if (record.getCode() != null) {
            sql.VALUES("CODE", "#{code,jdbcType=VARCHAR}");
        }
        
        if (record.getName() != null) {
            sql.VALUES("NAME", "#{name,jdbcType=VARCHAR}");
        }
        
        if (record.getDescription() != null) {
            sql.VALUES("DESCRIPTION", "#{description,jdbcType=VARCHAR}");
        }
        
        if (record.getSortno() != null) {
            sql.VALUES("SORTNO", "#{sortno,jdbcType=DECIMAL}");
        }
        
        if (record.getActiveflag() != null) {
            sql.VALUES("ACTIVEFLAG", "#{activeflag,jdbcType=CHAR}");
        }
        
        return sql.toString();
    }

    public String updateByPrimaryKeySelective(JTableDict record) {
        SQL sql = new SQL();
        sql.UPDATE("J_TABLEDICT");
        
        if (record.getCode() != null) {
            sql.SET("CODE = #{code,jdbcType=VARCHAR}");
        }
        
        if (record.getName() != null) {
            sql.SET("NAME = #{name,jdbcType=VARCHAR}");
        }
        
        if (record.getDescription() != null) {
            sql.SET("DESCRIPTION = #{description,jdbcType=VARCHAR}");
        }
        
        if (record.getSortno() != null) {
            sql.SET("SORTNO = #{sortno,jdbcType=DECIMAL}");
        }
        
        if (record.getActiveflag() != null) {
            sql.SET("ACTIVEFLAG = #{activeflag,jdbcType=CHAR}");
        }
        
        sql.WHERE("ID = #{id,jdbcType=DECIMAL}");
        
        return sql.toString();
    }
}