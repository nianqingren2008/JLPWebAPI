package com.callan.service.provider.dao.mapper;

import com.callan.service.provider.pojo.db.JTagdicts;
import org.apache.ibatis.jdbc.SQL;

public class JTagdictsSqlProvider {

    public String insertSelective(JTagdicts record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("J_TAGDICT");
        
        if (record.getId() != null) {
            sql.VALUES("ID", "#{id,jdbcType=DECIMAL}");
        }
        
        if (record.getName() != null) {
            sql.VALUES("NAME", "#{name,jdbcType=VARCHAR}");
        }
        
        if (record.getDescription() != null) {
            sql.VALUES("DESCRIPTION", "#{description,jdbcType=VARCHAR}");
        }
        
        if (record.getTagtype() != null) {
            sql.VALUES("TAGTYPE", "#{tagtype,jdbcType=CHAR}");
        }
        
        if (record.getValuetype() != null) {
            sql.VALUES("VALUETYPE", "#{valuetype,jdbcType=CHAR}");
        }
        
        if (record.getUserid() != null) {
            sql.VALUES("USERID", "#{userid,jdbcType=DECIMAL}");
        }
        
        if (record.getProjectid() != null) {
            sql.VALUES("PROJECTID", "#{projectid,jdbcType=DECIMAL}");
        }
        
        if (record.getCreatedate() != null) {
            sql.VALUES("CREATEDATE", "#{createdate,jdbcType=TIMESTAMP}");
        }
        
        if (record.getActiveflag() != null) {
            sql.VALUES("ACTIVEFLAG", "#{activeflag,jdbcType=CHAR}");
        }
        
        if (record.getShowflag() != null) {
            sql.VALUES("SHOWFLAG", "#{showflag,jdbcType=CHAR}");
        }
        
        if (record.getTagattached() != null) {
            sql.VALUES("TAGATTACHED", "#{tagattached,jdbcType=VARCHAR}");
        }
        
        if (record.getTotalnum() != null) {
            sql.VALUES("TOTALNUM", "#{totalnum,jdbcType=DECIMAL}");
        }
        
        if (record.getCurrentnum() != null) {
            sql.VALUES("CURRENTNUM", "#{currentnum,jdbcType=DECIMAL}");
        }
        
        return sql.toString();
    }

    public String updateByPrimaryKeySelective(JTagdicts record) {
        SQL sql = new SQL();
        sql.UPDATE("J_TAGDICT");
        
        if (record.getName() != null) {
            sql.SET("NAME = #{name,jdbcType=VARCHAR}");
        }
        
        if (record.getDescription() != null) {
            sql.SET("DESCRIPTION = #{description,jdbcType=VARCHAR}");
        }
        
        if (record.getTagtype() != null) {
            sql.SET("TAGTYPE = #{tagtype,jdbcType=CHAR}");
        }
        
        if (record.getValuetype() != null) {
            sql.SET("VALUETYPE = #{valuetype,jdbcType=CHAR}");
        }
        
        if (record.getUserid() != null) {
            sql.SET("USERID = #{userid,jdbcType=DECIMAL}");
        }
        
        if (record.getProjectid() != null) {
            sql.SET("PROJECTID = #{projectid,jdbcType=DECIMAL}");
        }
        
        if (record.getCreatedate() != null) {
            sql.SET("CREATEDATE = #{createdate,jdbcType=TIMESTAMP}");
        }
        
        if (record.getActiveflag() != null) {
            sql.SET("ACTIVEFLAG = #{activeflag,jdbcType=CHAR}");
        }
        
        if (record.getShowflag() != null) {
            sql.SET("SHOWFLAG = #{showflag,jdbcType=CHAR}");
        }
        
        if (record.getTagattached() != null) {
            sql.SET("TAGATTACHED = #{tagattached,jdbcType=VARCHAR}");
        }
        
        if (record.getTotalnum() != null) {
            sql.SET("TOTALNUM = #{totalnum,jdbcType=DECIMAL}");
        }
        
        if (record.getCurrentnum() != null) {
            sql.SET("CURRENTNUM = #{currentnum,jdbcType=DECIMAL}");
        }
        
        sql.WHERE("ID = #{id,jdbcType=DECIMAL}");
        
        return sql.toString();
    }
}