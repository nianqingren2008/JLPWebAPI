package com.callan.service.provider.dao.mapper;

import com.callan.service.provider.config.JLPConts;
import com.callan.service.provider.pojo.db.DPatientglobal;
import com.callan.service.provider.pojo.db.DPatientvisit;

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

public interface DPatientvisitMapper {
    @Delete({
        "delete from D_PATIENTVISIT",
        "where ID = #{id,jdbcType=DECIMAL}"
    })
    int deleteByPrimaryKey(Long id);

    @Insert({
        "insert into D_PATIENTVISIT (ID, PATIENTGLOBALID, ",
        "PATIENTID, VISITID, ",
        "INPATID, RYDEPTCODE, ",
        "RYDEPT, RYDATETIME, ",
        "CYDEPTCODE, CYDEPT, ",
        "CYDATETIME, CHIEFCOMPLAINT, ",
        "RECHISDATE, DIAGNOSERANGE, ",
        "DIAGNOSECONTENT, AFFIRMDATE, ",
        "JLCREATEDATE, JLACTIVEFLAG)",
        "values (#{id,jdbcType=DECIMAL}, #{patientglobalid,jdbcType=DECIMAL}, ",
        "#{patientid,jdbcType=VARCHAR}, #{visitid,jdbcType=VARCHAR}, ",
        "#{inpatid,jdbcType=VARCHAR}, #{rydeptcode,jdbcType=VARCHAR}, ",
        "#{rydept,jdbcType=VARCHAR}, #{rydatetime,jdbcType=TIMESTAMP}, ",
        "#{cydeptcode,jdbcType=VARCHAR}, #{cydept,jdbcType=VARCHAR}, ",
        "#{cydatetime,jdbcType=TIMESTAMP}, #{chiefcomplaint,jdbcType=VARCHAR}, ",
        "#{rechisdate,jdbcType=TIMESTAMP}, #{diagnoserange,jdbcType=VARCHAR}, ",
        "#{diagnosecontent,jdbcType=VARCHAR}, #{affirmdate,jdbcType=TIMESTAMP}, ",
        "#{jlcreatedate,jdbcType=TIMESTAMP}, #{jlactiveflag,jdbcType=CHAR})"
    })
    int insert(DPatientvisit record);

    @InsertProvider(type=DPatientvisitSqlProvider.class, method="insertSelective")
    int insertSelective(DPatientvisit record);

    @Select({
        "select",
        "ID, PATIENTGLOBALID, PATIENTID, VISITID, INPATID, RYDEPTCODE, RYDEPT, RYDATETIME, ",
        "CYDEPTCODE, CYDEPT, CYDATETIME, CHIEFCOMPLAINT, RECHISDATE, DIAGNOSERANGE, DIAGNOSECONTENT, ",
        "AFFIRMDATE, JLCREATEDATE, JLACTIVEFLAG",
        "from D_PATIENTVISIT",
        "where ID = #{id,jdbcType=DECIMAL}"
    })
    @Results({
        @Result(column="ID", property="id", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="PATIENTGLOBALID", property="patientglobalid", jdbcType=JdbcType.DECIMAL),
        @Result(column="PATIENTID", property="patientid", jdbcType=JdbcType.VARCHAR),
        @Result(column="VISITID", property="visitid", jdbcType=JdbcType.VARCHAR),
        @Result(column="INPATID", property="inpatid", jdbcType=JdbcType.VARCHAR),
        @Result(column="RYDEPTCODE", property="rydeptcode", jdbcType=JdbcType.VARCHAR),
        @Result(column="RYDEPT", property="rydept", jdbcType=JdbcType.VARCHAR),
        @Result(column="RYDATETIME", property="rydatetime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="CYDEPTCODE", property="cydeptcode", jdbcType=JdbcType.VARCHAR),
        @Result(column="CYDEPT", property="cydept", jdbcType=JdbcType.VARCHAR),
        @Result(column="CYDATETIME", property="cydatetime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="CHIEFCOMPLAINT", property="chiefcomplaint", jdbcType=JdbcType.VARCHAR),
        @Result(column="RECHISDATE", property="rechisdate", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="DIAGNOSERANGE", property="diagnoserange", jdbcType=JdbcType.VARCHAR),
        @Result(column="DIAGNOSECONTENT", property="diagnosecontent", jdbcType=JdbcType.VARCHAR),
        @Result(column="AFFIRMDATE", property="affirmdate", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="JLCREATEDATE", property="jlcreatedate", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="JLACTIVEFLAG", property="jlactiveflag", jdbcType=JdbcType.CHAR)
    })
    DPatientvisit selectByPrimaryKey(Long id);

    @UpdateProvider(type=DPatientvisitSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(DPatientvisit record);

    @Update({
        "update D_PATIENTVISIT",
        "set PATIENTGLOBALID = #{patientglobalid,jdbcType=DECIMAL},",
          "PATIENTID = #{patientid,jdbcType=VARCHAR},",
          "VISITID = #{visitid,jdbcType=VARCHAR},",
          "INPATID = #{inpatid,jdbcType=VARCHAR},",
          "RYDEPTCODE = #{rydeptcode,jdbcType=VARCHAR},",
          "RYDEPT = #{rydept,jdbcType=VARCHAR},",
          "RYDATETIME = #{rydatetime,jdbcType=TIMESTAMP},",
          "CYDEPTCODE = #{cydeptcode,jdbcType=VARCHAR},",
          "CYDEPT = #{cydept,jdbcType=VARCHAR},",
          "CYDATETIME = #{cydatetime,jdbcType=TIMESTAMP},",
          "CHIEFCOMPLAINT = #{chiefcomplaint,jdbcType=VARCHAR},",
          "RECHISDATE = #{rechisdate,jdbcType=TIMESTAMP},",
          "DIAGNOSERANGE = #{diagnoserange,jdbcType=VARCHAR},",
          "DIAGNOSECONTENT = #{diagnosecontent,jdbcType=VARCHAR},",
          "AFFIRMDATE = #{affirmdate,jdbcType=TIMESTAMP},",
          "JLCREATEDATE = #{jlcreatedate,jdbcType=TIMESTAMP},",
          "JLACTIVEFLAG = #{jlactiveflag,jdbcType=CHAR}",
        "where ID = #{id,jdbcType=DECIMAL}"
    })
    int updateByPrimaryKey(DPatientvisit record);

	
	@Select("SELECT * FROM D_PATIENTVISIT where JLACTIVEFLAG='"+JLPConts.ActiveFlag+"'")
    public List<DPatientvisit> getAll();
	
	@Select("SELECT * FROM D_PATIENTVISIT where JLACTIVEFLAG='"+JLPConts.ActiveFlag+"' and PATIENTID = #{patientid,jdbcType=VARCHAR}")
	List<DPatientvisit> getByPatientGlobalId(Long patientid);
	
}