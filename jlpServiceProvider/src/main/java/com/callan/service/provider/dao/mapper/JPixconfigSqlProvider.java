package com.callan.service.provider.dao.mapper;

import com.callan.service.provider.pojo.db.JPixconfig;
import org.apache.ibatis.jdbc.SQL;

public class JPixconfigSqlProvider {

    public String insertSelective(JPixconfig record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("J_PIXCONFIG");
        
        if (record.getId() != null) {
            sql.VALUES("ID", "#{id,jdbcType=DECIMAL}");
        }
        
        if (record.getFieldname() != null) {
            sql.VALUES("FIELDNAME", "#{fieldname,jdbcType=VARCHAR}");
        }
        
        if (record.getWeightcoefficient() != null) {
            sql.VALUES("WEIGHTCOEFFICIENT", "#{weightcoefficient,jdbcType=DECIMAL}");
        }
        
        if (record.getActiveflag() != null) {
            sql.VALUES("ACTIVEFLAG", "#{activeflag,jdbcType=CHAR}");
        }
        
        return sql.toString();
    }

    public String updateByPrimaryKeySelective(JPixconfig record) {
        SQL sql = new SQL();
        sql.UPDATE("J_PIXCONFIG");
        
        if (record.getFieldname() != null) {
            sql.SET("FIELDNAME = #{fieldname,jdbcType=VARCHAR}");
        }
        
        if (record.getWeightcoefficient() != null) {
            sql.SET("WEIGHTCOEFFICIENT = #{weightcoefficient,jdbcType=DECIMAL}");
        }
        
        if (record.getActiveflag() != null) {
            sql.SET("ACTIVEFLAG = #{activeflag,jdbcType=CHAR}");
        }
        
        sql.WHERE("ID = #{id,jdbcType=DECIMAL}");
        
        return sql.toString();
    }
}