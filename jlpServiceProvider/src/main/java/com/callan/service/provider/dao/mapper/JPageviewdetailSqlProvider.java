package com.callan.service.provider.dao.mapper;

import com.callan.service.provider.pojo.db.JPageviewdetail;
import org.apache.ibatis.jdbc.SQL;

public class JPageviewdetailSqlProvider {

    public String insertSelective(JPageviewdetail record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("J_PAGEVIEWDETAIL");
        
        if (record.getId() != null) {
            sql.VALUES("ID", "#{id,jdbcType=DECIMAL}");
        }
        
        if (record.getPagecode() != null) {
            sql.VALUES("PAGECODE", "#{pagecode,jdbcType=VARCHAR}");
        }
        
        if (record.getViewcode() != null) {
            sql.VALUES("VIEWCODE", "#{viewcode,jdbcType=VARCHAR}");
        }
        
        if (record.getSortno() != null) {
            sql.VALUES("SORTNO", "#{sortno,jdbcType=DECIMAL}");
        }
        
        if (record.getActiveflag() != null) {
            sql.VALUES("ACTIVEFLAG", "#{activeflag,jdbcType=CHAR}");
        }
        
        return sql.toString();
    }

    public String updateByPrimaryKeySelective(JPageviewdetail record) {
        SQL sql = new SQL();
        sql.UPDATE("J_PAGEVIEWDETAIL");
        
        if (record.getPagecode() != null) {
            sql.SET("PAGECODE = #{pagecode,jdbcType=VARCHAR}");
        }
        
        if (record.getViewcode() != null) {
            sql.SET("VIEWCODE = #{viewcode,jdbcType=VARCHAR}");
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