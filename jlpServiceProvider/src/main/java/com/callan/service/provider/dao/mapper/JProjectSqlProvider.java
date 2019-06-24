package com.callan.service.provider.dao.mapper;

import com.callan.service.provider.pojo.db.JProject;
import org.apache.ibatis.jdbc.SQL;

public class JProjectSqlProvider {

    public String insertSelective(JProject record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("J_PROJECT");
        
        if (record.getId() != null) {
            sql.VALUES("ID", "#{id,jdbcType=DECIMAL}");
        }
        
        if (record.getUserid() != null) {
            sql.VALUES("USERID", "#{userid,jdbcType=DECIMAL}");
        }
        
        if (record.getProjectname() != null) {
            sql.VALUES("PROJECTNAME", "#{projectname,jdbcType=VARCHAR}");
        }
        
        if (record.getProjectenname() != null) {
            sql.VALUES("PROJECTENNAME", "#{projectenname,jdbcType=VARCHAR}");
        }
        
        if (record.getProjecttype() != null) {
            sql.VALUES("PROJECTTYPE", "#{projecttype,jdbcType=VARCHAR}");
        }
        
        if (record.getProjectdescribe() != null) {
            sql.VALUES("PROJECTDESCRIBE", "#{projectdescribe,jdbcType=VARCHAR}");
        }
        
        if (record.getStartdate() != null) {
            sql.VALUES("STARTDATE", "#{startdate,jdbcType=TIMESTAMP}");
        }
        
        if (record.getEnddate() != null) {
            sql.VALUES("ENDDATE", "#{enddate,jdbcType=TIMESTAMP}");
        }
        
        if (record.getSponsor() != null) {
            sql.VALUES("SPONSOR", "#{sponsor,jdbcType=VARCHAR}");
        }
        
        if (record.getProjectregistno() != null) {
            sql.VALUES("PROJECTREGISTNO", "#{projectregistno,jdbcType=VARCHAR}");
        }
        
        if (record.getEthicalrecordno() != null) {
            sql.VALUES("ETHICALRECORDNO", "#{ethicalrecordno,jdbcType=VARCHAR}");
        }
        
        if (record.getPatientcount() != null) {
            sql.VALUES("PATIENTCOUNT", "#{patientcount,jdbcType=DECIMAL}");
        }
        
        if (record.getMedicalrecordcount() != null) {
            sql.VALUES("MEDICALRECORDCOUNT", "#{medicalrecordcount,jdbcType=DECIMAL}");
        }
        
        if (record.getStatus() != null) {
            sql.VALUES("STATUS", "#{status,jdbcType=VARCHAR}");
        }
        
        if (record.getSharetype() != null) {
            sql.VALUES("SHARETYPE", "#{sharetype,jdbcType=CHAR}");
        }
        
        if (record.getUpdatedate() != null) {
            sql.VALUES("UPDATEDATE", "#{updatedate,jdbcType=TIMESTAMP}");
        }
        
        if (record.getCreatedate() != null) {
            sql.VALUES("CREATEDATE", "#{createdate,jdbcType=TIMESTAMP}");
        }
        
        if (record.getActiveflag() != null) {
            sql.VALUES("ACTIVEFLAG", "#{activeflag,jdbcType=CHAR}");
        }
        
        if (record.getImageurl() != null) {
            sql.VALUES("IMAGEURL", "#{imageurl,jdbcType=VARCHAR}");
        }
        
        if (record.getDatastatus() != null) {
            sql.VALUES("DATASTATUS", "#{datastatus,jdbcType=CHAR}");
        }
        
        return sql.toString();
    }

    public String updateByPrimaryKeySelective(JProject record) {
        SQL sql = new SQL();
        sql.UPDATE("J_PROJECT");
        
        if (record.getUserid() != null) {
            sql.SET("USERID = #{userid,jdbcType=DECIMAL}");
        }
        
        if (record.getProjectname() != null) {
            sql.SET("PROJECTNAME = #{projectname,jdbcType=VARCHAR}");
        }
        
        if (record.getProjectenname() != null) {
            sql.SET("PROJECTENNAME = #{projectenname,jdbcType=VARCHAR}");
        }
        
        if (record.getProjecttype() != null) {
            sql.SET("PROJECTTYPE = #{projecttype,jdbcType=VARCHAR}");
        }
        
        if (record.getProjectdescribe() != null) {
            sql.SET("PROJECTDESCRIBE = #{projectdescribe,jdbcType=VARCHAR}");
        }
        
        if (record.getStartdate() != null) {
            sql.SET("STARTDATE = #{startdate,jdbcType=TIMESTAMP}");
        }
        
        if (record.getEnddate() != null) {
            sql.SET("ENDDATE = #{enddate,jdbcType=TIMESTAMP}");
        }
        
        if (record.getSponsor() != null) {
            sql.SET("SPONSOR = #{sponsor,jdbcType=VARCHAR}");
        }
        
        if (record.getProjectregistno() != null) {
            sql.SET("PROJECTREGISTNO = #{projectregistno,jdbcType=VARCHAR}");
        }
        
        if (record.getEthicalrecordno() != null) {
            sql.SET("ETHICALRECORDNO = #{ethicalrecordno,jdbcType=VARCHAR}");
        }
        
        if (record.getPatientcount() != null) {
            sql.SET("PATIENTCOUNT = #{patientcount,jdbcType=DECIMAL}");
        }
        
        if (record.getMedicalrecordcount() != null) {
            sql.SET("MEDICALRECORDCOUNT = #{medicalrecordcount,jdbcType=DECIMAL}");
        }
        
        if (record.getStatus() != null) {
            sql.SET("STATUS = #{status,jdbcType=VARCHAR}");
        }
        
        if (record.getSharetype() != null) {
            sql.SET("SHARETYPE = #{sharetype,jdbcType=CHAR}");
        }
        
        if (record.getUpdatedate() != null) {
            sql.SET("UPDATEDATE = #{updatedate,jdbcType=TIMESTAMP}");
        }
        
        if (record.getCreatedate() != null) {
            sql.SET("CREATEDATE = #{createdate,jdbcType=TIMESTAMP}");
        }
        
        if (record.getActiveflag() != null) {
            sql.SET("ACTIVEFLAG = #{activeflag,jdbcType=CHAR}");
        }
        
        if (record.getImageurl() != null) {
            sql.SET("IMAGEURL = #{imageurl,jdbcType=VARCHAR}");
        }
        
        if (record.getDatastatus() != null) {
            sql.SET("DATASTATUS = #{datastatus,jdbcType=CHAR}");
        }
        
        sql.WHERE("ID = #{id,jdbcType=DECIMAL}");
        
        return sql.toString();
    }
}