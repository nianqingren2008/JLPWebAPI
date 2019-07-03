package com.callan.service.provider.dao.mapper;

import com.callan.service.provider.pojo.db.JUsertokens;
import org.apache.ibatis.jdbc.SQL;

public class JUsertokensSqlProvider {

    public String insertSelective(JUsertokens record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("J_USERTOKENS");
        
        if (record.getId() != null) {
            sql.VALUES("ID", "#{id,jdbcType=DECIMAL}");
        }
        
        if (record.getToken() != null) {
            sql.VALUES("TOKEN", "#{token,jdbcType=VARCHAR}");
        }
        
        if (record.getLoginip() != null) {
            sql.VALUES("LOGINIP", "#{loginip,jdbcType=VARCHAR}");
        }
        
        if (record.getCreatedate() != null) {
            sql.VALUES("CREATEDATE", "#{createdate,jdbcType=TIMESTAMP}");
        }
        
        if (record.getActivedate() != null) {
            sql.VALUES("ACTIVEDATE", "#{activedate,jdbcType=TIMESTAMP}");
        }
        
        if (record.getActiveflag() != null) {
            sql.VALUES("ACTIVEFLAG", "#{activeflag,jdbcType=CHAR}");
        }
        
        return sql.toString();
    }

    public String updateByPrimaryKeySelective(JUsertokens record) {
        SQL sql = new SQL();
        sql.UPDATE("J_USERTOKENS");
        
        if (record.getToken() != null) {
            sql.SET("TOKEN = #{token,jdbcType=VARCHAR}");
        }
        
        if (record.getLoginip() != null) {
            sql.SET("LOGINIP = #{loginip,jdbcType=VARCHAR}");
        }
        
        if (record.getCreatedate() != null) {
            sql.SET("CREATEDATE = #{createdate,jdbcType=TIMESTAMP}");
        }
        
        if (record.getActivedate() != null) {
            sql.SET("ACTIVEDATE = #{activedate,jdbcType=TIMESTAMP}");
        }
        
        if (record.getActiveflag() != null) {
            sql.SET("ACTIVEFLAG = #{activeflag,jdbcType=CHAR}");
        }
        
        sql.WHERE("ID = #{id,jdbcType=DECIMAL}");
        
        return sql.toString();
    }
}