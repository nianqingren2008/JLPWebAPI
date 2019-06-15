package com.callan.service.provider.dao.mapper;

import com.callan.service.provider.pojo.db.DLabmasterinfo;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;

public interface DLabmasterinfoMapper {
    @Delete({
        "delete from D_LABMASTERINFO",
        "where ID = #{id,jdbcType=DECIMAL}"
    })
    int deleteByPrimaryKey(Long id);

    @Insert({
        "insert into D_LABMASTERINFO (ID, PATIENTGLOBALID, ",
        "PATIENTID, NAME, ",
        "BIRTHDAY, VISITID, ",
        "INPUTID, OUTPATID, ",
        "PATIENTSOURCE, APPLYNO, ",
        "REQDATETIME, TESTCAUSE, ",
        "SPECIMEN, SPCMRECDATETIME, ",
        "REPORTTIME, TESTTYPE, ",
        "JLCREATEDATE, JLACTIVEFLAG)",
        "values (#{id,jdbcType=DECIMAL}, #{patientglobalid,jdbcType=DECIMAL}, ",
        "#{patientid,jdbcType=VARCHAR}, #{name,jdbcType=VARCHAR}, ",
        "#{birthday,jdbcType=TIMESTAMP}, #{visitid,jdbcType=VARCHAR}, ",
        "#{inputid,jdbcType=VARCHAR}, #{outpatid,jdbcType=VARCHAR}, ",
        "#{patientsource,jdbcType=VARCHAR}, #{applyno,jdbcType=VARCHAR}, ",
        "#{reqdatetime,jdbcType=TIMESTAMP}, #{testcause,jdbcType=VARCHAR}, ",
        "#{specimen,jdbcType=VARCHAR}, #{spcmrecdatetime,jdbcType=TIMESTAMP}, ",
        "#{reporttime,jdbcType=TIMESTAMP}, #{testtype,jdbcType=VARCHAR}, ",
        "#{jlcreatedate,jdbcType=TIMESTAMP}, #{jlactiveflag,jdbcType=CHAR})"
    })
    int insert(DLabmasterinfo record);

    @InsertProvider(type=DLabmasterinfoSqlProvider.class, method="insertSelective")
    int insertSelective(DLabmasterinfo record);

    @Select({
        "select",
        "ID, PATIENTGLOBALID, PATIENTID, NAME, BIRTHDAY, VISITID, INPUTID, OUTPATID, ",
        "PATIENTSOURCE, APPLYNO, REQDATETIME, TESTCAUSE, SPECIMEN, SPCMRECDATETIME, REPORTTIME, ",
        "TESTTYPE, JLCREATEDATE, JLACTIVEFLAG",
        "from D_LABMASTERINFO",
        "where ID = #{id,jdbcType=DECIMAL}"
    })
    @Results({
        @Result(column="ID", property="id", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="PATIENTGLOBALID", property="patientglobalid", jdbcType=JdbcType.DECIMAL),
        @Result(column="PATIENTID", property="patientid", jdbcType=JdbcType.VARCHAR),
        @Result(column="NAME", property="name", jdbcType=JdbcType.VARCHAR),
        @Result(column="BIRTHDAY", property="birthday", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="VISITID", property="visitid", jdbcType=JdbcType.VARCHAR),
        @Result(column="INPUTID", property="inputid", jdbcType=JdbcType.VARCHAR),
        @Result(column="OUTPATID", property="outpatid", jdbcType=JdbcType.VARCHAR),
        @Result(column="PATIENTSOURCE", property="patientsource", jdbcType=JdbcType.VARCHAR),
        @Result(column="APPLYNO", property="applyno", jdbcType=JdbcType.VARCHAR),
        @Result(column="REQDATETIME", property="reqdatetime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="TESTCAUSE", property="testcause", jdbcType=JdbcType.VARCHAR),
        @Result(column="SPECIMEN", property="specimen", jdbcType=JdbcType.VARCHAR),
        @Result(column="SPCMRECDATETIME", property="spcmrecdatetime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="REPORTTIME", property="reporttime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="TESTTYPE", property="testtype", jdbcType=JdbcType.VARCHAR),
        @Result(column="JLCREATEDATE", property="jlcreatedate", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="JLACTIVEFLAG", property="jlactiveflag", jdbcType=JdbcType.CHAR)
    })
    DLabmasterinfo selectByPrimaryKey(Long id);

    @UpdateProvider(type=DLabmasterinfoSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(DLabmasterinfo record);

    @Update({
        "update D_LABMASTERINFO",
        "set PATIENTGLOBALID = #{patientglobalid,jdbcType=DECIMAL},",
          "PATIENTID = #{patientid,jdbcType=VARCHAR},",
          "NAME = #{name,jdbcType=VARCHAR},",
          "BIRTHDAY = #{birthday,jdbcType=TIMESTAMP},",
          "VISITID = #{visitid,jdbcType=VARCHAR},",
          "INPUTID = #{inputid,jdbcType=VARCHAR},",
          "OUTPATID = #{outpatid,jdbcType=VARCHAR},",
          "PATIENTSOURCE = #{patientsource,jdbcType=VARCHAR},",
          "APPLYNO = #{applyno,jdbcType=VARCHAR},",
          "REQDATETIME = #{reqdatetime,jdbcType=TIMESTAMP},",
          "TESTCAUSE = #{testcause,jdbcType=VARCHAR},",
          "SPECIMEN = #{specimen,jdbcType=VARCHAR},",
          "SPCMRECDATETIME = #{spcmrecdatetime,jdbcType=TIMESTAMP},",
          "REPORTTIME = #{reporttime,jdbcType=TIMESTAMP},",
          "TESTTYPE = #{testtype,jdbcType=VARCHAR},",
          "JLCREATEDATE = #{jlcreatedate,jdbcType=TIMESTAMP},",
          "JLACTIVEFLAG = #{jlactiveflag,jdbcType=CHAR}",
        "where ID = #{id,jdbcType=DECIMAL}"
    })
    int updateByPrimaryKey(DLabmasterinfo record);
}