package com.callan.service.provider.dao.mapper;

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

import com.callan.service.provider.config.JLPConts;
import com.callan.service.provider.pojo.db.DPatientgloballist;

public interface DPatientgloballistMapper {
    @Delete({
        "delete from D_PATIENTGLOBALLIST",
        "where ID = #{id,jdbcType=DECIMAL}"
    })
    int deleteByPrimaryKey(Long id);

    @Insert({
        "insert into D_PATIENTGLOBALLIST (ID, PATIENTGLOBALID, ",
        "PATIENTID, NAME, ",
        "BIRTHDAY, SEX, ",
        "ADDRESS, HOMENUM, ",
        "TELNUM, CULTURELEVEL, ",
        "HEIGHT, WEIGHT, ",
        "NATION, NATIVEPLACE, ",
        "PATIENTMARRIED, PAPERTYPE, ",
        "IDCARD, PATIENTJOB, ",
        "RELATIONSHIP, RELATIONPHONE, ",
        "RELATIONCALL, UPDATEDATE, ",
        "JLCREATEDATE, JLACTIVEFLAG)",
        "values (#{id,jdbcType=DECIMAL}, #{patientglobalid,jdbcType=DECIMAL}, ",
        "#{patientid,jdbcType=DECIMAL}, #{name,jdbcType=VARCHAR}, ",
        "#{birthday,jdbcType=TIMESTAMP}, #{sex,jdbcType=VARCHAR}, ",
        "#{address,jdbcType=VARCHAR}, #{homenum,jdbcType=VARCHAR}, ",
        "#{telnum,jdbcType=VARCHAR}, #{culturelevel,jdbcType=VARCHAR}, ",
        "#{height,jdbcType=VARCHAR}, #{weight,jdbcType=VARCHAR}, ",
        "#{nation,jdbcType=VARCHAR}, #{nativeplace,jdbcType=VARCHAR}, ",
        "#{patientmarried,jdbcType=VARCHAR}, #{papertype,jdbcType=VARCHAR}, ",
        "#{idcard,jdbcType=VARCHAR}, #{patientjob,jdbcType=VARCHAR}, ",
        "#{relationship,jdbcType=VARCHAR}, #{relationphone,jdbcType=VARCHAR}, ",
        "#{relationcall,jdbcType=VARCHAR}, #{updatedate,jdbcType=TIMESTAMP}, ",
        "#{jlcreatedate,jdbcType=TIMESTAMP}, #{jlactiveflag,jdbcType=CHAR})"
    })
    int insert(DPatientgloballist record);

    @InsertProvider(type=DPatientgloballistSqlProvider.class, method="insertSelective")
    int insertSelective(DPatientgloballist record);

    @Select({
        "select",
        "ID, PATIENTGLOBALID, PATIENTID, NAME, BIRTHDAY, SEX, ADDRESS, HOMENUM, TELNUM, ",
        "CULTURELEVEL, HEIGHT, WEIGHT, NATION, NATIVEPLACE, PATIENTMARRIED, PAPERTYPE, ",
        "IDCARD, PATIENTJOB, RELATIONSHIP, RELATIONPHONE, RELATIONCALL, UPDATEDATE, JLCREATEDATE, ",
        "JLACTIVEFLAG",
        "from D_PATIENTGLOBALLIST",
        "where ID = #{id,jdbcType=DECIMAL}"
    })
    @Results({
        @Result(column="ID", property="id", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="PATIENTGLOBALID", property="patientglobalid", jdbcType=JdbcType.DECIMAL),
        @Result(column="PATIENTID", property="patientid", jdbcType=JdbcType.DECIMAL),
        @Result(column="NAME", property="name", jdbcType=JdbcType.VARCHAR),
        @Result(column="BIRTHDAY", property="birthday", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="SEX", property="sex", jdbcType=JdbcType.VARCHAR),
        @Result(column="ADDRESS", property="address", jdbcType=JdbcType.VARCHAR),
        @Result(column="HOMENUM", property="homenum", jdbcType=JdbcType.VARCHAR),
        @Result(column="TELNUM", property="telnum", jdbcType=JdbcType.VARCHAR),
        @Result(column="CULTURELEVEL", property="culturelevel", jdbcType=JdbcType.VARCHAR),
        @Result(column="HEIGHT", property="height", jdbcType=JdbcType.VARCHAR),
        @Result(column="WEIGHT", property="weight", jdbcType=JdbcType.VARCHAR),
        @Result(column="NATION", property="nation", jdbcType=JdbcType.VARCHAR),
        @Result(column="NATIVEPLACE", property="nativeplace", jdbcType=JdbcType.VARCHAR),
        @Result(column="PATIENTMARRIED", property="patientmarried", jdbcType=JdbcType.VARCHAR),
        @Result(column="PAPERTYPE", property="papertype", jdbcType=JdbcType.VARCHAR),
        @Result(column="IDCARD", property="idcard", jdbcType=JdbcType.VARCHAR),
        @Result(column="PATIENTJOB", property="patientjob", jdbcType=JdbcType.VARCHAR),
        @Result(column="RELATIONSHIP", property="relationship", jdbcType=JdbcType.VARCHAR),
        @Result(column="RELATIONPHONE", property="relationphone", jdbcType=JdbcType.VARCHAR),
        @Result(column="RELATIONCALL", property="relationcall", jdbcType=JdbcType.VARCHAR),
        @Result(column="UPDATEDATE", property="updatedate", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="JLCREATEDATE", property="jlcreatedate", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="JLACTIVEFLAG", property="jlactiveflag", jdbcType=JdbcType.CHAR)
    })
    DPatientgloballist selectByPrimaryKey(Long id);

    @UpdateProvider(type=DPatientgloballistSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(DPatientgloballist record);

    @Update({
        "update D_PATIENTGLOBALLIST",
        "set PATIENTGLOBALID = #{patientglobalid,jdbcType=DECIMAL},",
          "PATIENTID = #{patientid,jdbcType=DECIMAL},",
          "NAME = #{name,jdbcType=VARCHAR},",
          "BIRTHDAY = #{birthday,jdbcType=TIMESTAMP},",
          "SEX = #{sex,jdbcType=VARCHAR},",
          "ADDRESS = #{address,jdbcType=VARCHAR},",
          "HOMENUM = #{homenum,jdbcType=VARCHAR},",
          "TELNUM = #{telnum,jdbcType=VARCHAR},",
          "CULTURELEVEL = #{culturelevel,jdbcType=VARCHAR},",
          "HEIGHT = #{height,jdbcType=VARCHAR},",
          "WEIGHT = #{weight,jdbcType=VARCHAR},",
          "NATION = #{nation,jdbcType=VARCHAR},",
          "NATIVEPLACE = #{nativeplace,jdbcType=VARCHAR},",
          "PATIENTMARRIED = #{patientmarried,jdbcType=VARCHAR},",
          "PAPERTYPE = #{papertype,jdbcType=VARCHAR},",
          "IDCARD = #{idcard,jdbcType=VARCHAR},",
          "PATIENTJOB = #{patientjob,jdbcType=VARCHAR},",
          "RELATIONSHIP = #{relationship,jdbcType=VARCHAR},",
          "RELATIONPHONE = #{relationphone,jdbcType=VARCHAR},",
          "RELATIONCALL = #{relationcall,jdbcType=VARCHAR},",
          "UPDATEDATE = #{updatedate,jdbcType=TIMESTAMP},",
          "JLCREATEDATE = #{jlcreatedate,jdbcType=TIMESTAMP},",
          "JLACTIVEFLAG = #{jlactiveflag,jdbcType=CHAR}",
        "where ID = #{id,jdbcType=DECIMAL}"
    })
    int updateByPrimaryKey(DPatientgloballist record);

    @Select({
        "select",
        "ID, PATIENTGLOBALID, PATIENTID, NAME, BIRTHDAY, SEX, ADDRESS, HOMENUM, TELNUM, ",
        "CULTURELEVEL, HEIGHT, WEIGHT, NATION, NATIVEPLACE, PATIENTMARRIED, PAPERTYPE, ",
        "IDCARD, PATIENTJOB, RELATIONSHIP, RELATIONPHONE, RELATIONCALL, UPDATEDATE, JLCREATEDATE, ",
        "JLACTIVEFLAG",
        "from D_PATIENTGLOBALLIST",
        "where JLACTIVEFLAG='"+JLPConts.ActiveFlag+"'"
    })
    @Results({
        @Result(column="ID", property="id", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="PATIENTGLOBALID", property="patientglobalid", jdbcType=JdbcType.DECIMAL),
        @Result(column="PATIENTID", property="patientid", jdbcType=JdbcType.DECIMAL),
        @Result(column="NAME", property="name", jdbcType=JdbcType.VARCHAR),
        @Result(column="BIRTHDAY", property="birthday", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="SEX", property="sex", jdbcType=JdbcType.VARCHAR),
        @Result(column="ADDRESS", property="address", jdbcType=JdbcType.VARCHAR),
        @Result(column="HOMENUM", property="homenum", jdbcType=JdbcType.VARCHAR),
        @Result(column="TELNUM", property="telnum", jdbcType=JdbcType.VARCHAR),
        @Result(column="CULTURELEVEL", property="culturelevel", jdbcType=JdbcType.VARCHAR),
        @Result(column="HEIGHT", property="height", jdbcType=JdbcType.VARCHAR),
        @Result(column="WEIGHT", property="weight", jdbcType=JdbcType.VARCHAR),
        @Result(column="NATION", property="nation", jdbcType=JdbcType.VARCHAR),
        @Result(column="NATIVEPLACE", property="nativeplace", jdbcType=JdbcType.VARCHAR),
        @Result(column="PATIENTMARRIED", property="patientmarried", jdbcType=JdbcType.VARCHAR),
        @Result(column="PAPERTYPE", property="papertype", jdbcType=JdbcType.VARCHAR),
        @Result(column="IDCARD", property="idcard", jdbcType=JdbcType.VARCHAR),
        @Result(column="PATIENTJOB", property="patientjob", jdbcType=JdbcType.VARCHAR),
        @Result(column="RELATIONSHIP", property="relationship", jdbcType=JdbcType.VARCHAR),
        @Result(column="RELATIONPHONE", property="relationphone", jdbcType=JdbcType.VARCHAR),
        @Result(column="RELATIONCALL", property="relationcall", jdbcType=JdbcType.VARCHAR),
        @Result(column="UPDATEDATE", property="updatedate", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="JLCREATEDATE", property="jlcreatedate", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="JLACTIVEFLAG", property="jlactiveflag", jdbcType=JdbcType.CHAR)
    })
	List<DPatientgloballist> getAll();
}