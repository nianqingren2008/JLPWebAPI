package com.callan.service.provider.dao.mapper;

import com.callan.service.provider.config.JLPConts;
import com.callan.service.provider.pojo.db.DDischargesummary;

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

public interface DDischargesummaryMapper {
    @Delete({
        "delete from D_DISCHARGESUMMARY",
        "where ID = #{id,jdbcType=DECIMAL}"
    })
    int deleteByPrimaryKey(Long id);

    @Insert({
        "insert into D_DISCHARGESUMMARY (ID, PATIENTGLOBALID, ",
        "PATIENTID, VISITID, ",
        "MODELTYPE, MODELNAME, ",
        "CYDATE, INHOSPITALDAY, ",
        "DEPTNAME, DEPTID, ",
        "LSCONTENTDATE, JLCREATEDATE, ",
        "JLACTIVEFLAG, BLID, ",
        "LSCONTENT)",
        "values (#{id,jdbcType=DECIMAL}, #{patientglobalid,jdbcType=DECIMAL}, ",
        "#{patientid,jdbcType=VARCHAR}, #{visitid,jdbcType=VARCHAR}, ",
        "#{modeltype,jdbcType=VARCHAR}, #{modelname,jdbcType=VARCHAR}, ",
        "#{cydate,jdbcType=TIMESTAMP}, #{inhospitalday,jdbcType=VARCHAR}, ",
        "#{deptname,jdbcType=VARCHAR}, #{deptid,jdbcType=VARCHAR}, ",
        "#{lscontentdate,jdbcType=TIMESTAMP}, #{jlcreatedate,jdbcType=TIMESTAMP}, ",
        "#{jlactiveflag,jdbcType=CHAR}, #{blid,jdbcType=VARCHAR}, ",
        "#{lscontent,jdbcType=CLOB})"
    })
    int insert(DDischargesummary record);

    @InsertProvider(type=DDischargesummarySqlProvider.class, method="insertSelective")
    int insertSelective(DDischargesummary record);

    @Select({
        "select",
        "ID, PATIENTGLOBALID, PATIENTID, VISITID, MODELTYPE, MODELNAME, CYDATE, INHOSPITALDAY, ",
        "DEPTNAME, DEPTID, LSCONTENTDATE, JLCREATEDATE, JLACTIVEFLAG, BLID, LSCONTENT",
        "from D_DISCHARGESUMMARY",
        "where ID = #{id,jdbcType=DECIMAL}"
    })
    @Results({
        @Result(column="ID", property="id", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="PATIENTGLOBALID", property="patientglobalid", jdbcType=JdbcType.DECIMAL),
        @Result(column="PATIENTID", property="patientid", jdbcType=JdbcType.VARCHAR),
        @Result(column="VISITID", property="visitid", jdbcType=JdbcType.VARCHAR),
        @Result(column="MODELTYPE", property="modeltype", jdbcType=JdbcType.VARCHAR),
        @Result(column="MODELNAME", property="modelname", jdbcType=JdbcType.VARCHAR),
        @Result(column="CYDATE", property="cydate", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="INHOSPITALDAY", property="inhospitalday", jdbcType=JdbcType.VARCHAR),
        @Result(column="DEPTNAME", property="deptname", jdbcType=JdbcType.VARCHAR),
        @Result(column="DEPTID", property="deptid", jdbcType=JdbcType.VARCHAR),
        @Result(column="LSCONTENTDATE", property="lscontentdate", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="JLCREATEDATE", property="jlcreatedate", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="JLACTIVEFLAG", property="jlactiveflag", jdbcType=JdbcType.CHAR),
        @Result(column="BLID", property="blid", jdbcType=JdbcType.VARCHAR),
        @Result(column="LSCONTENT", property="lscontent", jdbcType=JdbcType.CLOB)
    })
    DDischargesummary selectByPrimaryKey(Long id);

    @UpdateProvider(type=DDischargesummarySqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(DDischargesummary record);

    @Update({
        "update D_DISCHARGESUMMARY",
        "set PATIENTGLOBALID = #{patientglobalid,jdbcType=DECIMAL},",
          "PATIENTID = #{patientid,jdbcType=VARCHAR},",
          "VISITID = #{visitid,jdbcType=VARCHAR},",
          "MODELTYPE = #{modeltype,jdbcType=VARCHAR},",
          "MODELNAME = #{modelname,jdbcType=VARCHAR},",
          "CYDATE = #{cydate,jdbcType=TIMESTAMP},",
          "INHOSPITALDAY = #{inhospitalday,jdbcType=VARCHAR},",
          "DEPTNAME = #{deptname,jdbcType=VARCHAR},",
          "DEPTID = #{deptid,jdbcType=VARCHAR},",
          "LSCONTENTDATE = #{lscontentdate,jdbcType=TIMESTAMP},",
          "JLCREATEDATE = #{jlcreatedate,jdbcType=TIMESTAMP},",
          "JLACTIVEFLAG = #{jlactiveflag,jdbcType=CHAR},",
          "BLID = #{blid,jdbcType=VARCHAR},",
          "LSCONTENT = #{lscontent,jdbcType=CLOB}",
        "where ID = #{id,jdbcType=DECIMAL}"
    })
    int updateByPrimaryKeyWithBLOBs(DDischargesummary record);

    @Update({
        "update D_DISCHARGESUMMARY",
        "set PATIENTGLOBALID = #{patientglobalid,jdbcType=DECIMAL},",
          "PATIENTID = #{patientid,jdbcType=VARCHAR},",
          "VISITID = #{visitid,jdbcType=VARCHAR},",
          "MODELTYPE = #{modeltype,jdbcType=VARCHAR},",
          "MODELNAME = #{modelname,jdbcType=VARCHAR},",
          "CYDATE = #{cydate,jdbcType=TIMESTAMP},",
          "INHOSPITALDAY = #{inhospitalday,jdbcType=VARCHAR},",
          "DEPTNAME = #{deptname,jdbcType=VARCHAR},",
          "DEPTID = #{deptid,jdbcType=VARCHAR},",
          "LSCONTENTDATE = #{lscontentdate,jdbcType=TIMESTAMP},",
          "JLCREATEDATE = #{jlcreatedate,jdbcType=TIMESTAMP},",
          "JLACTIVEFLAG = #{jlactiveflag,jdbcType=CHAR},",
          "BLID = #{blid,jdbcType=VARCHAR}",
        "where ID = #{id,jdbcType=DECIMAL}"
    })
    int updateByPrimaryKey(DDischargesummary record);

    @Select({
        "select",
        "ID, PATIENTGLOBALID, PATIENTID, VISITID, MODELTYPE, MODELNAME, CYDATE, INHOSPITALDAY, ",
        "DEPTNAME, DEPTID, LSCONTENTDATE, JLCREATEDATE, JLACTIVEFLAG, BLID, LSCONTENT",
        "from D_DISCHARGESUMMARY",
        " where PATIENTID = #{patientId,jdbcType=VARCHAR} ",
        " and VISITID = #{visitId,jdbcType=VARCHAR} ",
        " and JLACTIVEFLAG='"+JLPConts.ActiveFlag+"'"
    })
    @Results({
        @Result(column="ID", property="id", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="PATIENTGLOBALID", property="patientglobalid", jdbcType=JdbcType.DECIMAL),
        @Result(column="PATIENTID", property="patientid", jdbcType=JdbcType.VARCHAR),
        @Result(column="VISITID", property="visitid", jdbcType=JdbcType.VARCHAR),
        @Result(column="MODELTYPE", property="modeltype", jdbcType=JdbcType.VARCHAR),
        @Result(column="MODELNAME", property="modelname", jdbcType=JdbcType.VARCHAR),
        @Result(column="CYDATE", property="cydate", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="INHOSPITALDAY", property="inhospitalday", jdbcType=JdbcType.VARCHAR),
        @Result(column="DEPTNAME", property="deptname", jdbcType=JdbcType.VARCHAR),
        @Result(column="DEPTID", property="deptid", jdbcType=JdbcType.VARCHAR),
        @Result(column="LSCONTENTDATE", property="lscontentdate", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="JLCREATEDATE", property="jlcreatedate", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="JLACTIVEFLAG", property="jlactiveflag", jdbcType=JdbcType.CHAR),
        @Result(column="BLID", property="blid", jdbcType=JdbcType.VARCHAR),
        @Result(column="LSCONTENT", property="lscontent", jdbcType=JdbcType.CLOB)
    })
	List<DDischargesummary> getByPatientId(String patientId, String visitId);
}