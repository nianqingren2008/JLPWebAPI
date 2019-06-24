package com.callan.service.provider.dao.mapper;

import com.callan.service.provider.pojo.db.JProjectdatastatusdict;
import org.apache.ibatis.jdbc.SQL;

public class JProjectdatastatusdictSqlProvider {

    public String insertSelective(JProjectdatastatusdict record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("J_PROJECTDATASTATUSDICT");
        
        if (record.getId() != null) {
            sql.VALUES("ID", "#{id,jdbcType=DECIMAL}");
        }
        
        if (record.getName() != null) {
            sql.VALUES("NAME", "#{name,jdbcType=VARCHAR}");
        }
        
        if (record.getProjectid() != null) {
            sql.VALUES("PROJECTID", "#{projectid,jdbcType=DECIMAL}");
        }
        
        if (record.getActiveflag() != null) {
            sql.VALUES("ACTIVEFLAG", "#{activeflag,jdbcType=CHAR}");
        }
        
        if (record.getCreatedate() != null) {
            sql.VALUES("CREATEDATE", "#{createdate,jdbcType=TIMESTAMP}");
        }
        
        if (record.getStatustype() != null) {
            sql.VALUES("STATUSTYPE", "#{statustype,jdbcType=CHAR}");
        }
        
        return sql.toString();
    }

    public String updateByPrimaryKeySelective(JProjectdatastatusdict record) {
        SQL sql = new SQL();
        sql.UPDATE("J_PROJECTDATASTATUSDICT");
        
        if (record.getName() != null) {
            sql.SET("NAME = #{name,jdbcType=VARCHAR}");
        }
        
        if (record.getProjectid() != null) {
            sql.SET("PROJECTID = #{projectid,jdbcType=DECIMAL}");
        }
        
        if (record.getActiveflag() != null) {
            sql.SET("ACTIVEFLAG = #{activeflag,jdbcType=CHAR}");
        }
        
        if (record.getCreatedate() != null) {
            sql.SET("CREATEDATE = #{createdate,jdbcType=TIMESTAMP}");
        }
        
        if (record.getStatustype() != null) {
            sql.SET("STATUSTYPE = #{statustype,jdbcType=CHAR}");
        }
        
        sql.WHERE("ID = #{id,jdbcType=DECIMAL}");
        
        return sql.toString();
    }
}