package com.callan.service.provider.dao.mapper;

import com.callan.service.provider.pojo.db.JTagvaluedicts;
import org.apache.ibatis.jdbc.SQL;

public class JTagvaluedictsSqlProvider {

    public String insertSelective(JTagvaluedicts record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("J_TAGVALUEDICT");
        
        if (record.getId() != null) {
            sql.VALUES("ID", "#{id,jdbcType=DECIMAL}");
        }
        
        if (record.getTagid() != null) {
            sql.VALUES("TAGID", "#{tagid,jdbcType=DECIMAL}");
        }
        
        if (record.getValuetype() != null) {
            sql.VALUES("VALUETYPE", "#{valuetype,jdbcType=CHAR}");
        }
        
        if (record.getValue() != null) {
            sql.VALUES("VALUE", "#{value,jdbcType=VARCHAR}");
        }
        
        if (record.getMinvalue() != null) {
            sql.VALUES("MINVALUE", "#{minvalue,jdbcType=VARCHAR}");
        }
        
        if (record.getMaxvalue() != null) {
            sql.VALUES("MAXVALUE", "#{maxvalue,jdbcType=VARCHAR}");
        }
        
        if (record.getCreatedate() != null) {
            sql.VALUES("CREATEDATE", "#{createdate,jdbcType=TIMESTAMP}");
        }
        
        if (record.getActiveflag() != null) {
            sql.VALUES("ACTIVEFLAG", "#{activeflag,jdbcType=CHAR}");
        }
        
        return sql.toString();
    }

    public String updateByPrimaryKeySelective(JTagvaluedicts record) {
        SQL sql = new SQL();
        sql.UPDATE("J_TAGVALUEDICT");
        
        if (record.getTagid() != null) {
            sql.SET("TAGID = #{tagid,jdbcType=DECIMAL}");
        }
        
        if (record.getValuetype() != null) {
            sql.SET("VALUETYPE = #{valuetype,jdbcType=CHAR}");
        }
        
        if (record.getValue() != null) {
            sql.SET("VALUE = #{value,jdbcType=VARCHAR}");
        }
        
        if (record.getMinvalue() != null) {
            sql.SET("MINVALUE = #{minvalue,jdbcType=VARCHAR}");
        }
        
        if (record.getMaxvalue() != null) {
            sql.SET("MAXVALUE = #{maxvalue,jdbcType=VARCHAR}");
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