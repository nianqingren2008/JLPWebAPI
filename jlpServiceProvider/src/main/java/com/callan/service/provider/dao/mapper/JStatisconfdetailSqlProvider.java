package com.callan.service.provider.dao.mapper;

import com.callan.service.provider.pojo.db.JStatisconfdetail;
import org.apache.ibatis.jdbc.SQL;

public class JStatisconfdetailSqlProvider {

    public String insertSelective(JStatisconfdetail record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("J_STATISCONFDETAIL");
        
        if (record.getId() != null) {
            sql.VALUES("ID", "#{id,jdbcType=DECIMAL}");
        }
        
        if (record.getStatisconfid() != null) {
            sql.VALUES("STATISCONFID", "#{statisconfid,jdbcType=DECIMAL}");
        }
        
        if (record.getDetailtype() != null) {
            sql.VALUES("DETAILTYPE", "#{detailtype,jdbcType=CHAR}");
        }
        
        if (record.getFieldid() != null) {
            sql.VALUES("FIELDID", "#{fieldid,jdbcType=DECIMAL}");
        }
        
        if (record.getFieldtypetrans() != null) {
            sql.VALUES("FIELDTYPETRANS", "#{fieldtypetrans,jdbcType=VARCHAR}");
        }
        
        if (record.getSortno() != null) {
            sql.VALUES("SORTNO", "#{sortno,jdbcType=DECIMAL}");
        }
        
        if (record.getCreatedate() != null) {
            sql.VALUES("CREATEDATE", "#{createdate,jdbcType=TIMESTAMP}");
        }
        
        if (record.getActiveflag() != null) {
            sql.VALUES("ACTIVEFLAG", "#{activeflag,jdbcType=CHAR}");
        }
        
        if (record.getDefaulvalue() != null) {
            sql.VALUES("DEFAULVALUE", "#{defaulvalue,jdbcType=CHAR}");
        }
        
        return sql.toString();
    }

    public String updateByPrimaryKeySelective(JStatisconfdetail record) {
        SQL sql = new SQL();
        sql.UPDATE("J_STATISCONFDETAIL");
        
        if (record.getStatisconfid() != null) {
            sql.SET("STATISCONFID = #{statisconfid,jdbcType=DECIMAL}");
        }
        
        if (record.getDetailtype() != null) {
            sql.SET("DETAILTYPE = #{detailtype,jdbcType=CHAR}");
        }
        
        if (record.getFieldid() != null) {
            sql.SET("FIELDID = #{fieldid,jdbcType=DECIMAL}");
        }
        
        if (record.getFieldtypetrans() != null) {
            sql.SET("FIELDTYPETRANS = #{fieldtypetrans,jdbcType=VARCHAR}");
        }
        
        if (record.getSortno() != null) {
            sql.SET("SORTNO = #{sortno,jdbcType=DECIMAL}");
        }
        
        if (record.getCreatedate() != null) {
            sql.SET("CREATEDATE = #{createdate,jdbcType=TIMESTAMP}");
        }
        
        if (record.getActiveflag() != null) {
            sql.SET("ACTIVEFLAG = #{activeflag,jdbcType=CHAR}");
        }
        
        if (record.getDefaulvalue() != null) {
            sql.SET("DEFAULVALUE = #{defaulvalue,jdbcType=CHAR}");
        }
        
        sql.WHERE("ID = #{id,jdbcType=DECIMAL}");
        
        return sql.toString();
    }
}