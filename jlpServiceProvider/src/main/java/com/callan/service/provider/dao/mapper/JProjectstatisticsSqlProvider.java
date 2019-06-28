package com.callan.service.provider.dao.mapper;

import com.callan.service.provider.pojo.db.JProjectstatistics;
import org.apache.ibatis.jdbc.SQL;

public class JProjectstatisticsSqlProvider {

    public String insertSelective(JProjectstatistics record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("J_PROJECTSTATISTICS");
        
        if (record.getId() != null) {
            sql.VALUES("ID", "#{id,jdbcType=DECIMAL}");
        }
        
        if (record.getProjectid() != null) {
            sql.VALUES("PROJECTID", "#{projectid,jdbcType=DECIMAL}");
        }
        
        if (record.getStatisticstype() != null) {
            sql.VALUES("STATISTICSTYPE", "#{statisticstype,jdbcType=VARCHAR}");
        }
        
        if (record.getStatisticstypedataid() != null) {
            sql.VALUES("STATISTICSTYPEDATAID", "#{statisticstypedataid,jdbcType=DECIMAL}");
        }
        
        if (record.getCount() != null) {
            sql.VALUES("COUNT", "#{count,jdbcType=DECIMAL}");
        }
        
        if (record.getCreatedate() != null) {
            sql.VALUES("CREATEDATE", "#{createdate,jdbcType=TIMESTAMP}");
        }
        
        if (record.getActiveflag() != null) {
            sql.VALUES("ACTIVEFLAG", "#{activeflag,jdbcType=CHAR}");
        }
        
        return sql.toString();
    }

    public String updateByPrimaryKeySelective(JProjectstatistics record) {
        SQL sql = new SQL();
        sql.UPDATE("J_PROJECTSTATISTICS");
        
        if (record.getProjectid() != null) {
            sql.SET("PROJECTID = #{projectid,jdbcType=DECIMAL}");
        }
        
        if (record.getStatisticstype() != null) {
            sql.SET("STATISTICSTYPE = #{statisticstype,jdbcType=VARCHAR}");
        }
        
        if (record.getStatisticstypedataid() != null) {
            sql.SET("STATISTICSTYPEDATAID = #{statisticstypedataid,jdbcType=DECIMAL}");
        }
        
        if (record.getCount() != null) {
            sql.SET("COUNT = #{count,jdbcType=DECIMAL}");
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