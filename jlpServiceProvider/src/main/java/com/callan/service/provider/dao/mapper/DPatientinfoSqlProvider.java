package com.callan.service.provider.dao.mapper;

import com.callan.service.provider.pojo.db.DPatientinfo;
import org.apache.ibatis.jdbc.SQL;

public class DPatientinfoSqlProvider {

    public String insertSelective(DPatientinfo record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("D_PATIENTINFO");
        
        if (record.getId() != null) {
            sql.VALUES("ID", "#{id,jdbcType=DECIMAL}");
        }
        
        if (record.getPatientglobalid() != null) {
            sql.VALUES("PATIENTGLOBALID", "#{patientglobalid,jdbcType=DECIMAL}");
        }
        
        if (record.getPatientid() != null) {
            sql.VALUES("PATIENTID", "#{patientid,jdbcType=VARCHAR}");
        }
        
        if (record.getName() != null) {
            sql.VALUES("NAME", "#{name,jdbcType=VARCHAR}");
        }
        
        if (record.getSex() != null) {
            sql.VALUES("SEX", "#{sex,jdbcType=VARCHAR}");
        }
        
        if (record.getAddress() != null) {
            sql.VALUES("ADDRESS", "#{address,jdbcType=VARCHAR}");
        }
        
        if (record.getHomenum() != null) {
            sql.VALUES("HOMENUM", "#{homenum,jdbcType=VARCHAR}");
        }
        
        if (record.getTelnum() != null) {
            sql.VALUES("TELNUM", "#{telnum,jdbcType=VARCHAR}");
        }
        
        if (record.getBirthday() != null) {
            sql.VALUES("BIRTHDAY", "#{birthday,jdbcType=TIMESTAMP}");
        }
        
        if (record.getCulturelevel() != null) {
            sql.VALUES("CULTURELEVEL", "#{culturelevel,jdbcType=VARCHAR}");
        }
        
        if (record.getHeight() != null) {
            sql.VALUES("HEIGHT", "#{height,jdbcType=VARCHAR}");
        }
        
        if (record.getWeight() != null) {
            sql.VALUES("WEIGHT", "#{weight,jdbcType=VARCHAR}");
        }
        
        if (record.getNation() != null) {
            sql.VALUES("NATION", "#{nation,jdbcType=VARCHAR}");
        }
        
        if (record.getNativeplace() != null) {
            sql.VALUES("NATIVEPLACE", "#{nativeplace,jdbcType=VARCHAR}");
        }
        
        if (record.getPatientmarried() != null) {
            sql.VALUES("PATIENTMARRIED", "#{patientmarried,jdbcType=VARCHAR}");
        }
        
        if (record.getPapertype() != null) {
            sql.VALUES("PAPERTYPE", "#{papertype,jdbcType=VARCHAR}");
        }
        
        if (record.getIdcard() != null) {
            sql.VALUES("IDCARD", "#{idcard,jdbcType=VARCHAR}");
        }
        
        if (record.getPatientjob() != null) {
            sql.VALUES("PATIENTJOB", "#{patientjob,jdbcType=VARCHAR}");
        }
        
        if (record.getRelationship() != null) {
            sql.VALUES("RELATIONSHIP", "#{relationship,jdbcType=VARCHAR}");
        }
        
        if (record.getRelationphone() != null) {
            sql.VALUES("RELATIONPHONE", "#{relationphone,jdbcType=VARCHAR}");
        }
        
        if (record.getRelationcall() != null) {
            sql.VALUES("RELATIONCALL", "#{relationcall,jdbcType=VARCHAR}");
        }
        
        if (record.getJlcreatedate() != null) {
            sql.VALUES("JLCREATEDATE", "#{jlcreatedate,jdbcType=TIMESTAMP}");
        }
        
        if (record.getJlactiveflag() != null) {
            sql.VALUES("JLACTIVEFLAG", "#{jlactiveflag,jdbcType=CHAR}");
        }
        
        return sql.toString();
    }

    public String updateByPrimaryKeySelective(DPatientinfo record) {
        SQL sql = new SQL();
        sql.UPDATE("D_PATIENTINFO");
        
        if (record.getPatientglobalid() != null) {
            sql.SET("PATIENTGLOBALID = #{patientglobalid,jdbcType=DECIMAL}");
        }
        
        if (record.getPatientid() != null) {
            sql.SET("PATIENTID = #{patientid,jdbcType=VARCHAR}");
        }
        
        if (record.getName() != null) {
            sql.SET("NAME = #{name,jdbcType=VARCHAR}");
        }
        
        if (record.getSex() != null) {
            sql.SET("SEX = #{sex,jdbcType=VARCHAR}");
        }
        
        if (record.getAddress() != null) {
            sql.SET("ADDRESS = #{address,jdbcType=VARCHAR}");
        }
        
        if (record.getHomenum() != null) {
            sql.SET("HOMENUM = #{homenum,jdbcType=VARCHAR}");
        }
        
        if (record.getTelnum() != null) {
            sql.SET("TELNUM = #{telnum,jdbcType=VARCHAR}");
        }
        
        if (record.getBirthday() != null) {
            sql.SET("BIRTHDAY = #{birthday,jdbcType=TIMESTAMP}");
        }
        
        if (record.getCulturelevel() != null) {
            sql.SET("CULTURELEVEL = #{culturelevel,jdbcType=VARCHAR}");
        }
        
        if (record.getHeight() != null) {
            sql.SET("HEIGHT = #{height,jdbcType=VARCHAR}");
        }
        
        if (record.getWeight() != null) {
            sql.SET("WEIGHT = #{weight,jdbcType=VARCHAR}");
        }
        
        if (record.getNation() != null) {
            sql.SET("NATION = #{nation,jdbcType=VARCHAR}");
        }
        
        if (record.getNativeplace() != null) {
            sql.SET("NATIVEPLACE = #{nativeplace,jdbcType=VARCHAR}");
        }
        
        if (record.getPatientmarried() != null) {
            sql.SET("PATIENTMARRIED = #{patientmarried,jdbcType=VARCHAR}");
        }
        
        if (record.getPapertype() != null) {
            sql.SET("PAPERTYPE = #{papertype,jdbcType=VARCHAR}");
        }
        
        if (record.getIdcard() != null) {
            sql.SET("IDCARD = #{idcard,jdbcType=VARCHAR}");
        }
        
        if (record.getPatientjob() != null) {
            sql.SET("PATIENTJOB = #{patientjob,jdbcType=VARCHAR}");
        }
        
        if (record.getRelationship() != null) {
            sql.SET("RELATIONSHIP = #{relationship,jdbcType=VARCHAR}");
        }
        
        if (record.getRelationphone() != null) {
            sql.SET("RELATIONPHONE = #{relationphone,jdbcType=VARCHAR}");
        }
        
        if (record.getRelationcall() != null) {
            sql.SET("RELATIONCALL = #{relationcall,jdbcType=VARCHAR}");
        }
        
        if (record.getJlcreatedate() != null) {
            sql.SET("JLCREATEDATE = #{jlcreatedate,jdbcType=TIMESTAMP}");
        }
        
        if (record.getJlactiveflag() != null) {
            sql.SET("JLACTIVEFLAG = #{jlactiveflag,jdbcType=CHAR}");
        }
        
        sql.WHERE("ID = #{id,jdbcType=DECIMAL}");
        
        return sql.toString();
    }
}