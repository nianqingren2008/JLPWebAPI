package com.callan.service.provider.dao.mapper;

import com.callan.service.provider.pojo.db.JUsershowviewfield;
import org.apache.ibatis.jdbc.SQL;

public class JUsershowviewfieldSqlProvider {

    public String insertSelective(JUsershowviewfield record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("J_USERSHOWVIEWFIELD");
        
        if (record.getId() != null) {
            sql.VALUES("ID", "#{id,jdbcType=DECIMAL}");
        }
        
        if (record.getUserid() != null) {
            sql.VALUES("USERID", "#{userid,jdbcType=DECIMAL}");
        }
        
        if (record.getPagename() != null) {
            sql.VALUES("PAGENAME", "#{pagename,jdbcType=VARCHAR}");
        }
        
        if (record.getFieldid() != null) {
            sql.VALUES("FIELDID", "#{fieldid,jdbcType=DECIMAL}");
        }
        
        if (record.getActiveflag() != null) {
            sql.VALUES("ACTIVEFLAG", "#{activeflag,jdbcType=CHAR}");
        }
        
        return sql.toString();
    }

    public String updateByPrimaryKeySelective(JUsershowviewfield record) {
        SQL sql = new SQL();
        sql.UPDATE("J_USERSHOWVIEWFIELD");
        
        if (record.getUserid() != null) {
            sql.SET("USERID = #{userid,jdbcType=DECIMAL}");
        }
        
        if (record.getPagename() != null) {
            sql.SET("PAGENAME = #{pagename,jdbcType=VARCHAR}");
        }
        
        if (record.getFieldid() != null) {
            sql.SET("FIELDID = #{fieldid,jdbcType=DECIMAL}");
        }
        
        if (record.getActiveflag() != null) {
            sql.SET("ACTIVEFLAG = #{activeflag,jdbcType=CHAR}");
        }
        
        sql.WHERE("ID = #{id,jdbcType=DECIMAL}");
        
        return sql.toString();
    }
}