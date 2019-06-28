package com.callan.service.provider.dao.mapper;

import com.callan.service.provider.config.JLPConts;
import com.callan.service.provider.pojo.db.JProjectdatastatus;

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

public interface JProjectdatastatusMapper {
    @Delete({
        "delete from J_PROJECTDATASTATUS",
        "where ID = #{id,jdbcType=DECIMAL}"
    })
    int deleteByPrimaryKey(Long id);

    @Insert({
        "insert into J_PROJECTDATASTATUS (ID, USERID, ",
        "PROJECTID, PATIENTGLOBALID, ",
        "STATUS, ACTIVEFLAG, ",
        "CREATEDATE, CHANGESTATUS)",
        "values (#{id,jdbcType=DECIMAL}, #{userid,jdbcType=DECIMAL}, ",
        "#{projectid,jdbcType=DECIMAL}, #{patientglobalid,jdbcType=DECIMAL}, ",
        "#{status,jdbcType=DECIMAL}, #{activeflag,jdbcType=CHAR}, ",
        "#{createdate,jdbcType=TIMESTAMP}, #{changestatus,jdbcType=CHAR})"
    })
    int insert(JProjectdatastatus record);

    @InsertProvider(type=JProjectdatastatusSqlProvider.class, method="insertSelective")
    int insertSelective(JProjectdatastatus record);

    @Select({
        "select",
        "ID, USERID, PROJECTID, PATIENTGLOBALID, STATUS, ACTIVEFLAG, CREATEDATE, CHANGESTATUS",
        "from J_PROJECTDATASTATUS",
        "where ID = #{id,jdbcType=DECIMAL}"
    })
    @Results({
        @Result(column="ID", property="id", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="USERID", property="userid", jdbcType=JdbcType.DECIMAL),
        @Result(column="PROJECTID", property="projectid", jdbcType=JdbcType.DECIMAL),
        @Result(column="PATIENTGLOBALID", property="patientglobalid", jdbcType=JdbcType.DECIMAL),
        @Result(column="STATUS", property="status", jdbcType=JdbcType.DECIMAL),
        @Result(column="ACTIVEFLAG", property="activeflag", jdbcType=JdbcType.CHAR),
        @Result(column="CREATEDATE", property="createdate", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="CHANGESTATUS", property="changestatus", jdbcType=JdbcType.CHAR)
    })
    JProjectdatastatus selectByPrimaryKey(Long id);

    @UpdateProvider(type=JProjectdatastatusSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(JProjectdatastatus record);

    @Update({
        "update J_PROJECTDATASTATUS",
        "set USERID = #{userid,jdbcType=DECIMAL},",
          "PROJECTID = #{projectid,jdbcType=DECIMAL},",
          "PATIENTGLOBALID = #{patientglobalid,jdbcType=DECIMAL},",
          "STATUS = #{status,jdbcType=DECIMAL},",
          "ACTIVEFLAG = #{activeflag,jdbcType=CHAR},",
          "CREATEDATE = #{createdate,jdbcType=TIMESTAMP},",
          "CHANGESTATUS = #{changestatus,jdbcType=CHAR}",
        "where ID = #{id,jdbcType=DECIMAL}"
    })
    int updateByPrimaryKey(JProjectdatastatus record);

    @Select({
        "select",
        "ID, USERID, PROJECTID, PATIENTGLOBALID, STATUS, ACTIVEFLAG, CREATEDATE, CHANGESTATUS",
        "from J_PROJECTDATASTATUS",
        "where projectid = #{projectid,jdbcType=DECIMAL}"
        + " and patientGlobalId = #{patientGlobalId,jdbcType=DECIMAL}"
        + " and ACTIVEFLAG='"+JLPConts.ActiveFlag+"'"
    })
    @Results({
        @Result(column="ID", property="id", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="USERID", property="userid", jdbcType=JdbcType.DECIMAL),
        @Result(column="PROJECTID", property="projectid", jdbcType=JdbcType.DECIMAL),
        @Result(column="PATIENTGLOBALID", property="patientglobalid", jdbcType=JdbcType.DECIMAL),
        @Result(column="STATUS", property="status", jdbcType=JdbcType.DECIMAL),
        @Result(column="ACTIVEFLAG", property="activeflag", jdbcType=JdbcType.CHAR),
        @Result(column="CREATEDATE", property="createdate", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="CHANGESTATUS", property="changestatus", jdbcType=JdbcType.CHAR)
    })
	List<JProjectdatastatus> getByProjectIdAndPatientglobalid(Long projectid, Long patientGlobalId);
}