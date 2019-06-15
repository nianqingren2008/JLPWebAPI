package com.callan.service.provider.dao.mapper;

import com.callan.service.provider.config.JLPConts;
import com.callan.service.provider.pojo.db.DMedicalrecords;

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

public interface DMedicalrecordsMapper {
    @Delete({
        "delete from D_MEDICALRECORDS",
        "where ID = #{id,jdbcType=DECIMAL}"
    })
    int deleteByPrimaryKey(Long id);

    @Insert({
        "insert into D_MEDICALRECORDS (ID, PATIENTGLOBALID, ",
        "PATIENTID, VISITID, ",
        "RECHISDATE, JLCREATEDATE, ",
        "JLACTIVEFLAG, BLID, ",
        "MEDICALRECORDS)",
        "values (#{id,jdbcType=DECIMAL}, #{patientglobalid,jdbcType=DECIMAL}, ",
        "#{patientid,jdbcType=VARCHAR}, #{visitid,jdbcType=VARCHAR}, ",
        "#{rechisdate,jdbcType=TIMESTAMP}, #{jlcreatedate,jdbcType=TIMESTAMP}, ",
        "#{jlactiveflag,jdbcType=CHAR}, #{blid,jdbcType=VARCHAR}, ",
        "#{medicalrecords,jdbcType=CLOB})"
    })
    int insert(DMedicalrecords record);

    @InsertProvider(type=DMedicalrecordsSqlProvider.class, method="insertSelective")
    int insertSelective(DMedicalrecords record);

    @Select({
        "select",
        "ID, PATIENTGLOBALID, PATIENTID, VISITID, RECHISDATE, JLCREATEDATE, JLACTIVEFLAG, ",
        "BLID, MEDICALRECORDS",
        "from D_MEDICALRECORDS",
        "where ID = #{id,jdbcType=DECIMAL} and JLACTIVEFLAG='"+JLPConts.ActiveFlag+"'"
    })
    @Results({
        @Result(column="ID", property="id", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="PATIENTGLOBALID", property="patientglobalid", jdbcType=JdbcType.DECIMAL),
        @Result(column="PATIENTID", property="patientid", jdbcType=JdbcType.VARCHAR),
        @Result(column="VISITID", property="visitid", jdbcType=JdbcType.VARCHAR),
        @Result(column="RECHISDATE", property="rechisdate", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="JLCREATEDATE", property="jlcreatedate", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="JLACTIVEFLAG", property="jlactiveflag", jdbcType=JdbcType.CHAR),
        @Result(column="BLID", property="blid", jdbcType=JdbcType.VARCHAR),
        @Result(column="MEDICALRECORDS", property="medicalrecords", jdbcType=JdbcType.CLOB)
    })
    DMedicalrecords selectByPrimaryKey(Long id);

    @UpdateProvider(type=DMedicalrecordsSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(DMedicalrecords record);

    @Update({
        "update D_MEDICALRECORDS",
        "set PATIENTGLOBALID = #{patientglobalid,jdbcType=DECIMAL},",
          "PATIENTID = #{patientid,jdbcType=VARCHAR},",
          "VISITID = #{visitid,jdbcType=VARCHAR},",
          "RECHISDATE = #{rechisdate,jdbcType=TIMESTAMP},",
          "JLCREATEDATE = #{jlcreatedate,jdbcType=TIMESTAMP},",
          "JLACTIVEFLAG = #{jlactiveflag,jdbcType=CHAR},",
          "BLID = #{blid,jdbcType=VARCHAR},",
          "MEDICALRECORDS = #{medicalrecords,jdbcType=CLOB}",
        "where ID = #{id,jdbcType=DECIMAL}"
    })
    int updateByPrimaryKeyWithBLOBs(DMedicalrecords record);

    @Update({
        "update D_MEDICALRECORDS",
        "set PATIENTGLOBALID = #{patientglobalid,jdbcType=DECIMAL},",
          "PATIENTID = #{patientid,jdbcType=VARCHAR},",
          "VISITID = #{visitid,jdbcType=VARCHAR},",
          "RECHISDATE = #{rechisdate,jdbcType=TIMESTAMP},",
          "JLCREATEDATE = #{jlcreatedate,jdbcType=TIMESTAMP},",
          "JLACTIVEFLAG = #{jlactiveflag,jdbcType=CHAR},",
          "BLID = #{blid,jdbcType=VARCHAR}",
        "where ID = #{id,jdbcType=DECIMAL}"
    })
    int updateByPrimaryKey(DMedicalrecords record);

    @Select({
        "select",
        " ID, PATIENTGLOBALID, PATIENTID, VISITID, RECHISDATE, JLCREATEDATE, JLACTIVEFLAG, ",
        " BLID, MEDICALRECORDS",
        " from D_MEDICALRECORDS",
        " where PATIENTID = #{patientId,jdbcType=VARCHAR} ",
        " and VISITID = #{visitId,jdbcType=VARCHAR} ",
        " and JLACTIVEFLAG='"+JLPConts.ActiveFlag+"'"
    })
    @Results({
        @Result(column="ID", property="id", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="PATIENTGLOBALID", property="patientglobalid", jdbcType=JdbcType.DECIMAL),
        @Result(column="PATIENTID", property="patientid", jdbcType=JdbcType.VARCHAR),
        @Result(column="VISITID", property="visitid", jdbcType=JdbcType.VARCHAR),
        @Result(column="RECHISDATE", property="rechisdate", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="JLCREATEDATE", property="jlcreatedate", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="JLACTIVEFLAG", property="jlactiveflag", jdbcType=JdbcType.CHAR),
        @Result(column="BLID", property="blid", jdbcType=JdbcType.VARCHAR),
        @Result(column="MEDICALRECORDS", property="medicalrecords", jdbcType=JdbcType.CLOB)
    })
    
	List<DMedicalrecords> getByPatientId(String patientId, String visitId);
}