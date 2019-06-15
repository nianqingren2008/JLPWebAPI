package com.callan.service.provider.dao.mapper;

import com.callan.service.provider.config.JLPConts;
import com.callan.service.provider.pojo.db.DMedicalmainrecords;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;

public interface DMedicalmainrecordsMapper {
    @Delete({
        "delete from D_MEDICALMAINRECORDS",
        "where ID = #{id,jdbcType=DECIMAL}"
    })
    int deleteByPrimaryKey(Long id);

    @Insert({
        "insert into D_MEDICALMAINRECORDS (ID, PATIENTGLOBALID, ",
        "PATIENTID, VISITID, ",
        "INPATID, NAME, SEX, ",
        "BIRTHDAY, AGE, ",
        "IDCARD, ADDRESS, ",
        "RELATIONSHIP, RELATIONCALL, ",
        "RYDEPTCODE, RYDEPT, ",
        "RYDATETIME, CYDEPTCODE, ",
        "CYDEPT, CYDATETIME, ",
        "DIAGNOSERANGE, DIAGNOSECONTENT, ",
        "DIAGNOSECODE, CYSTATUS, ",
        "CYSTATUSCODE, OPERATIONCODE, ",
        "OPERATIONWAY, OPERATIONINCISION, ",
        "OPERATIONHEALING, BLID, ",
        "JLCREATEDATE, JLACTIVEFLAG)",
        "values (#{id,jdbcType=DECIMAL}, #{patientglobalid,jdbcType=DECIMAL}, ",
        "#{patientid,jdbcType=VARCHAR}, #{visitid,jdbcType=VARCHAR}, ",
        "#{inpatid,jdbcType=VARCHAR}, #{name,jdbcType=VARCHAR}, #{sex,jdbcType=VARCHAR}, ",
        "#{birthday,jdbcType=TIMESTAMP}, #{age,jdbcType=VARCHAR}, ",
        "#{idcard,jdbcType=VARCHAR}, #{address,jdbcType=VARCHAR}, ",
        "#{relationship,jdbcType=VARCHAR}, #{relationcall,jdbcType=VARCHAR}, ",
        "#{rydeptcode,jdbcType=VARCHAR}, #{rydept,jdbcType=VARCHAR}, ",
        "#{rydatetime,jdbcType=TIMESTAMP}, #{cydeptcode,jdbcType=VARCHAR}, ",
        "#{cydept,jdbcType=VARCHAR}, #{cydatetime,jdbcType=TIMESTAMP}, ",
        "#{diagnoserange,jdbcType=VARCHAR}, #{diagnosecontent,jdbcType=VARCHAR}, ",
        "#{diagnosecode,jdbcType=VARCHAR}, #{cystatus,jdbcType=VARCHAR}, ",
        "#{cystatuscode,jdbcType=VARCHAR}, #{operationcode,jdbcType=VARCHAR}, ",
        "#{operationway,jdbcType=VARCHAR}, #{operationincision,jdbcType=VARCHAR}, ",
        "#{operationhealing,jdbcType=VARCHAR}, #{blid,jdbcType=VARCHAR}, ",
        "#{jlcreatedate,jdbcType=TIMESTAMP}, #{jlactiveflag,jdbcType=CHAR})"
    })
    int insert(DMedicalmainrecords record);

    @InsertProvider(type=DMedicalmainrecordsSqlProvider.class, method="insertSelective")
    int insertSelective(DMedicalmainrecords record);

    @Select({
        "select",
        "ID, PATIENTGLOBALID, PATIENTID, VISITID, INPATID, NAME, SEX, BIRTHDAY, AGE, ",
        "IDCARD, ADDRESS, RELATIONSHIP, RELATIONCALL, RYDEPTCODE, RYDEPT, RYDATETIME, ",
        "CYDEPTCODE, CYDEPT, CYDATETIME, DIAGNOSERANGE, DIAGNOSECONTENT, DIAGNOSECODE, ",
        "CYSTATUS, CYSTATUSCODE, OPERATIONCODE, OPERATIONWAY, OPERATIONINCISION, OPERATIONHEALING, ",
        "BLID, JLCREATEDATE, JLACTIVEFLAG",
        "from D_MEDICALMAINRECORDS",
        "where ID = #{id,jdbcType=DECIMAL} and JLACTIVEFLAG='"+JLPConts.ActiveFlag+"'"
    })
    @Results({
        @Result(column="ID", property="id", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="PATIENTGLOBALID", property="patientglobalid", jdbcType=JdbcType.DECIMAL),
        @Result(column="PATIENTID", property="patientid", jdbcType=JdbcType.VARCHAR),
        @Result(column="VISITID", property="visitid", jdbcType=JdbcType.VARCHAR),
        @Result(column="INPATID", property="inpatid", jdbcType=JdbcType.VARCHAR),
        @Result(column="NAME", property="name", jdbcType=JdbcType.VARCHAR),
        @Result(column="SEX", property="sex", jdbcType=JdbcType.VARCHAR),
        @Result(column="BIRTHDAY", property="birthday", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="AGE", property="age", jdbcType=JdbcType.VARCHAR),
        @Result(column="IDCARD", property="idcard", jdbcType=JdbcType.VARCHAR),
        @Result(column="ADDRESS", property="address", jdbcType=JdbcType.VARCHAR),
        @Result(column="RELATIONSHIP", property="relationship", jdbcType=JdbcType.VARCHAR),
        @Result(column="RELATIONCALL", property="relationcall", jdbcType=JdbcType.VARCHAR),
        @Result(column="RYDEPTCODE", property="rydeptcode", jdbcType=JdbcType.VARCHAR),
        @Result(column="RYDEPT", property="rydept", jdbcType=JdbcType.VARCHAR),
        @Result(column="RYDATETIME", property="rydatetime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="CYDEPTCODE", property="cydeptcode", jdbcType=JdbcType.VARCHAR),
        @Result(column="CYDEPT", property="cydept", jdbcType=JdbcType.VARCHAR),
        @Result(column="CYDATETIME", property="cydatetime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="DIAGNOSERANGE", property="diagnoserange", jdbcType=JdbcType.VARCHAR),
        @Result(column="DIAGNOSECONTENT", property="diagnosecontent", jdbcType=JdbcType.VARCHAR),
        @Result(column="DIAGNOSECODE", property="diagnosecode", jdbcType=JdbcType.VARCHAR),
        @Result(column="CYSTATUS", property="cystatus", jdbcType=JdbcType.VARCHAR),
        @Result(column="CYSTATUSCODE", property="cystatuscode", jdbcType=JdbcType.VARCHAR),
        @Result(column="OPERATIONCODE", property="operationcode", jdbcType=JdbcType.VARCHAR),
        @Result(column="OPERATIONWAY", property="operationway", jdbcType=JdbcType.VARCHAR),
        @Result(column="OPERATIONINCISION", property="operationincision", jdbcType=JdbcType.VARCHAR),
        @Result(column="OPERATIONHEALING", property="operationhealing", jdbcType=JdbcType.VARCHAR),
        @Result(column="BLID", property="blid", jdbcType=JdbcType.VARCHAR),
        @Result(column="JLCREATEDATE", property="jlcreatedate", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="JLACTIVEFLAG", property="jlactiveflag", jdbcType=JdbcType.CHAR)
    })
    DMedicalmainrecords selectByPrimaryKey(Long id);

    @UpdateProvider(type=DMedicalmainrecordsSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(DMedicalmainrecords record);

    @Update({
        "update D_MEDICALMAINRECORDS",
        "set PATIENTGLOBALID = #{patientglobalid,jdbcType=DECIMAL},",
          "PATIENTID = #{patientid,jdbcType=VARCHAR},",
          "VISITID = #{visitid,jdbcType=VARCHAR},",
          "INPATID = #{inpatid,jdbcType=VARCHAR},",
          "NAME = #{name,jdbcType=VARCHAR},",
          "SEX = #{sex,jdbcType=VARCHAR},",
          "BIRTHDAY = #{birthday,jdbcType=TIMESTAMP},",
          "AGE = #{age,jdbcType=VARCHAR},",
          "IDCARD = #{idcard,jdbcType=VARCHAR},",
          "ADDRESS = #{address,jdbcType=VARCHAR},",
          "RELATIONSHIP = #{relationship,jdbcType=VARCHAR},",
          "RELATIONCALL = #{relationcall,jdbcType=VARCHAR},",
          "RYDEPTCODE = #{rydeptcode,jdbcType=VARCHAR},",
          "RYDEPT = #{rydept,jdbcType=VARCHAR},",
          "RYDATETIME = #{rydatetime,jdbcType=TIMESTAMP},",
          "CYDEPTCODE = #{cydeptcode,jdbcType=VARCHAR},",
          "CYDEPT = #{cydept,jdbcType=VARCHAR},",
          "CYDATETIME = #{cydatetime,jdbcType=TIMESTAMP},",
          "DIAGNOSERANGE = #{diagnoserange,jdbcType=VARCHAR},",
          "DIAGNOSECONTENT = #{diagnosecontent,jdbcType=VARCHAR},",
          "DIAGNOSECODE = #{diagnosecode,jdbcType=VARCHAR},",
          "CYSTATUS = #{cystatus,jdbcType=VARCHAR},",
          "CYSTATUSCODE = #{cystatuscode,jdbcType=VARCHAR},",
          "OPERATIONCODE = #{operationcode,jdbcType=VARCHAR},",
          "OPERATIONWAY = #{operationway,jdbcType=VARCHAR},",
          "OPERATIONINCISION = #{operationincision,jdbcType=VARCHAR},",
          "OPERATIONHEALING = #{operationhealing,jdbcType=VARCHAR},",
          "BLID = #{blid,jdbcType=VARCHAR},",
          "JLCREATEDATE = #{jlcreatedate,jdbcType=TIMESTAMP},",
          "JLACTIVEFLAG = #{jlactiveflag,jdbcType=CHAR}",
        "where ID = #{id,jdbcType=DECIMAL}"
    })
    int updateByPrimaryKey(DMedicalmainrecords record);

    @Select({
        "select",
        "ID, PATIENTGLOBALID, PATIENTID, VISITID, INPATID, NAME, SEX, BIRTHDAY, AGE, ",
        "IDCARD, ADDRESS, RELATIONSHIP, RELATIONCALL, RYDEPTCODE, RYDEPT, RYDATETIME, ",
        "CYDEPTCODE, CYDEPT, CYDATETIME, DIAGNOSERANGE, DIAGNOSECONTENT, DIAGNOSECODE, ",
        "CYSTATUS, CYSTATUSCODE, OPERATIONCODE, OPERATIONWAY, OPERATIONINCISION, OPERATIONHEALING, ",
        "BLID, JLCREATEDATE, JLACTIVEFLAG",
        "from D_MEDICALMAINRECORDS",
        "where PATIENTID = #{patientId,jdbcType=VARCHAR} and JLACTIVEFLAG='"+JLPConts.ActiveFlag+"'"
    })
    @Results({
        @Result(column="ID", property="id", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="PATIENTGLOBALID", property="patientglobalid", jdbcType=JdbcType.DECIMAL),
        @Result(column="PATIENTID", property="patientid", jdbcType=JdbcType.VARCHAR),
        @Result(column="VISITID", property="visitid", jdbcType=JdbcType.VARCHAR),
        @Result(column="INPATID", property="inpatid", jdbcType=JdbcType.VARCHAR),
        @Result(column="NAME", property="name", jdbcType=JdbcType.VARCHAR),
        @Result(column="SEX", property="sex", jdbcType=JdbcType.VARCHAR),
        @Result(column="BIRTHDAY", property="birthday", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="AGE", property="age", jdbcType=JdbcType.VARCHAR),
        @Result(column="IDCARD", property="idcard", jdbcType=JdbcType.VARCHAR),
        @Result(column="ADDRESS", property="address", jdbcType=JdbcType.VARCHAR),
        @Result(column="RELATIONSHIP", property="relationship", jdbcType=JdbcType.VARCHAR),
        @Result(column="RELATIONCALL", property="relationcall", jdbcType=JdbcType.VARCHAR),
        @Result(column="RYDEPTCODE", property="rydeptcode", jdbcType=JdbcType.VARCHAR),
        @Result(column="RYDEPT", property="rydept", jdbcType=JdbcType.VARCHAR),
        @Result(column="RYDATETIME", property="rydatetime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="CYDEPTCODE", property="cydeptcode", jdbcType=JdbcType.VARCHAR),
        @Result(column="CYDEPT", property="cydept", jdbcType=JdbcType.VARCHAR),
        @Result(column="CYDATETIME", property="cydatetime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="DIAGNOSERANGE", property="diagnoserange", jdbcType=JdbcType.VARCHAR),
        @Result(column="DIAGNOSECONTENT", property="diagnosecontent", jdbcType=JdbcType.VARCHAR),
        @Result(column="DIAGNOSECODE", property="diagnosecode", jdbcType=JdbcType.VARCHAR),
        @Result(column="CYSTATUS", property="cystatus", jdbcType=JdbcType.VARCHAR),
        @Result(column="CYSTATUSCODE", property="cystatuscode", jdbcType=JdbcType.VARCHAR),
        @Result(column="OPERATIONCODE", property="operationcode", jdbcType=JdbcType.VARCHAR),
        @Result(column="OPERATIONWAY", property="operationway", jdbcType=JdbcType.VARCHAR),
        @Result(column="OPERATIONINCISION", property="operationincision", jdbcType=JdbcType.VARCHAR),
        @Result(column="OPERATIONHEALING", property="operationhealing", jdbcType=JdbcType.VARCHAR),
        @Result(column="BLID", property="blid", jdbcType=JdbcType.VARCHAR),
        @Result(column="JLCREATEDATE", property="jlcreatedate", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="JLACTIVEFLAG", property="jlactiveflag", jdbcType=JdbcType.CHAR)
    })
	List<DMedicalmainrecords> getByPatientId(String patientId);
}