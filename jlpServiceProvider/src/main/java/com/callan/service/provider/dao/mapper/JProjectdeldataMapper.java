package com.callan.service.provider.dao.mapper;

import com.callan.service.provider.pojo.db.JProjectdeldata;

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

public interface JProjectdeldataMapper {
    @Delete({
        "delete from J_PROJECTDELDATA",
        "where ID = #{id,jdbcType=DECIMAL}"
    })
    int deleteByPrimaryKey(Long id);

    @Insert({
        "insert into J_PROJECTDELDATA (ID, USERID, ",
        "PROJECTID, TABLEID, ",
        "PATIENTGLOBALID, DATAID, ",
        "ACTIVEFLAG, CREATEDATE)",
        "values (#{id,jdbcType=DECIMAL}, #{userid,jdbcType=DECIMAL}, ",
        "#{projectid,jdbcType=DECIMAL}, #{tableid,jdbcType=DECIMAL}, ",
        "#{patientglobalid,jdbcType=DECIMAL}, #{dataid,jdbcType=DECIMAL}, ",
        "#{activeflag,jdbcType=CHAR}, #{createdate,jdbcType=TIMESTAMP})"
    })
    int insert(JProjectdeldata record);

    @InsertProvider(type=JProjectdeldataSqlProvider.class, method="insertSelective")
    int insertSelective(JProjectdeldata record);

    @Select({
        "select",
        "ID, USERID, PROJECTID, TABLEID, PATIENTGLOBALID, DATAID, ACTIVEFLAG, CREATEDATE",
        "from J_PROJECTDELDATA",
        "where ID = #{id,jdbcType=DECIMAL}"
    })
    @Results({
        @Result(column="ID", property="id", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="USERID", property="userid", jdbcType=JdbcType.DECIMAL),
        @Result(column="PROJECTID", property="projectid", jdbcType=JdbcType.DECIMAL),
        @Result(column="TABLEID", property="tableid", jdbcType=JdbcType.DECIMAL),
        @Result(column="PATIENTGLOBALID", property="patientglobalid", jdbcType=JdbcType.DECIMAL),
        @Result(column="DATAID", property="dataid", jdbcType=JdbcType.DECIMAL),
        @Result(column="ACTIVEFLAG", property="activeflag", jdbcType=JdbcType.CHAR),
        @Result(column="CREATEDATE", property="createdate", jdbcType=JdbcType.TIMESTAMP)
    })
    JProjectdeldata selectByPrimaryKey(Long id);

    @UpdateProvider(type=JProjectdeldataSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(JProjectdeldata record);

    @Update({
        "update J_PROJECTDELDATA",
        "set USERID = #{userid,jdbcType=DECIMAL},",
          "PROJECTID = #{projectid,jdbcType=DECIMAL},",
          "TABLEID = #{tableid,jdbcType=DECIMAL},",
          "PATIENTGLOBALID = #{patientglobalid,jdbcType=DECIMAL},",
          "DATAID = #{dataid,jdbcType=DECIMAL},",
          "ACTIVEFLAG = #{activeflag,jdbcType=CHAR},",
          "CREATEDATE = #{createdate,jdbcType=TIMESTAMP}",
        "where ID = #{id,jdbcType=DECIMAL}"
    })
    int updateByPrimaryKey(JProjectdeldata record);

    @Select({
        "select",
        "ID, USERID, PROJECTID, TABLEID, PATIENTGLOBALID, DATAID, ACTIVEFLAG, CREATEDATE",
        "from J_PROJECTDELDATA",
        "where PROJECTID = #{projectId,jdbcType=DECIMAL} "
        + " and TABLEID = #{tableId,jdbcType=DECIMAL} "
        + " and DATAID = #{dataId,jdbcType=DECIMAL} "
    })
    @Results({
        @Result(column="ID", property="id", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="USERID", property="userid", jdbcType=JdbcType.DECIMAL),
        @Result(column="PROJECTID", property="projectid", jdbcType=JdbcType.DECIMAL),
        @Result(column="TABLEID", property="tableid", jdbcType=JdbcType.DECIMAL),
        @Result(column="PATIENTGLOBALID", property="patientglobalid", jdbcType=JdbcType.DECIMAL),
        @Result(column="DATAID", property="dataid", jdbcType=JdbcType.DECIMAL),
        @Result(column="ACTIVEFLAG", property="activeflag", jdbcType=JdbcType.CHAR),
        @Result(column="CREATEDATE", property="createdate", jdbcType=JdbcType.TIMESTAMP)
    })
	List<JProjectdeldata> getByProjectIdAndTableId(Long projectId, Long tableId, Long dataId);
}