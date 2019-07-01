package com.callan.service.provider.dao.mapper;

import com.callan.service.provider.pojo.db.JControltypedict;
import org.apache.ibatis.jdbc.SQL;

public class JControltypedictSqlProvider {

    public String insertSelective(JControltypedict record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("J_CONTROLTYPEDICT");
        
        if (record.getCode() != null) {
            sql.VALUES("CODE", "#{code,jdbcType=VARCHAR}");
        }
        
        if (record.getName() != null) {
            sql.VALUES("NAME", "#{name,jdbcType=VARCHAR}");
        }
        
        if (record.getActiveflag() != null) {
            sql.VALUES("ACTIVEFLAG", "#{activeflag,jdbcType=CHAR}");
        }
        
        return sql.toString();
    }

    public String updateByPrimaryKeySelective(JControltypedict record) {
        SQL sql = new SQL();
        sql.UPDATE("J_CONTROLTYPEDICT");
        
        if (record.getName() != null) {
            sql.SET("NAME = #{name,jdbcType=VARCHAR}");
        }
        
        if (record.getActiveflag() != null) {
            sql.SET("ACTIVEFLAG = #{activeflag,jdbcType=CHAR}");
        }
        
        sql.WHERE("CODE = #{code,jdbcType=VARCHAR}");
        
        return sql.toString();
    }
}