package com.callan.service.provider.dao.mapper;

import com.callan.service.provider.config.JLPConts;
import com.callan.service.provider.pojo.db.JProjecttags;

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

public interface JProjecttagsMapper {
    @Delete({
        "delete from J_PROJECTTAGS",
        "where ID = #{id,jdbcType=DECIMAL}"
    })
    int deleteByPrimaryKey(Long id);

    @Insert({
        "insert into J_PROJECTTAGS (ID, USERID, ",
        "PROJECTID, TABLEID, ",
        "DATAID, TAGID, TAGVALUE, ",
        "ACTIVEFLAG, CREATEDATE, ",
        "PATIENTGLOBALID)",
        "values (#{id,jdbcType=DECIMAL}, #{userid,jdbcType=DECIMAL}, ",
        "#{projectid,jdbcType=DECIMAL}, #{tableid,jdbcType=DECIMAL}, ",
        "#{dataid,jdbcType=DECIMAL}, #{tagid,jdbcType=DECIMAL}, #{tagvalue,jdbcType=VARCHAR}, ",
        "#{activeflag,jdbcType=CHAR}, #{createdate,jdbcType=TIMESTAMP}, ",
        "#{patientglobalid,jdbcType=DECIMAL})"
    })
    int insert(JProjecttags record);

    @InsertProvider(type=JProjecttagsSqlProvider.class, method="insertSelective")
    int insertSelective(JProjecttags record);

    @Select({
        "select",
        "ID, USERID, PROJECTID, TABLEID, DATAID, TAGID, TAGVALUE, ACTIVEFLAG, CREATEDATE, ",
        "PATIENTGLOBALID",
        "from J_PROJECTTAGS",
        "where ID = #{id,jdbcType=DECIMAL}"
    })
    @Results({
        @Result(column="ID", property="id", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="USERID", property="userid", jdbcType=JdbcType.DECIMAL),
        @Result(column="PROJECTID", property="projectid", jdbcType=JdbcType.DECIMAL),
        @Result(column="TABLEID", property="tableid", jdbcType=JdbcType.DECIMAL),
        @Result(column="DATAID", property="dataid", jdbcType=JdbcType.DECIMAL),
        @Result(column="TAGID", property="tagid", jdbcType=JdbcType.DECIMAL),
        @Result(column="TAGVALUE", property="tagvalue", jdbcType=JdbcType.VARCHAR),
        @Result(column="ACTIVEFLAG", property="activeflag", jdbcType=JdbcType.CHAR),
        @Result(column="CREATEDATE", property="createdate", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="PATIENTGLOBALID", property="patientglobalid", jdbcType=JdbcType.DECIMAL)
    })
    JProjecttags selectByPrimaryKey(Long id);

    @UpdateProvider(type=JProjecttagsSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(JProjecttags record);

    @Update({
        "update J_PROJECTTAGS",
        "set USERID = #{userid,jdbcType=DECIMAL},",
          "PROJECTID = #{projectid,jdbcType=DECIMAL},",
          "TABLEID = #{tableid,jdbcType=DECIMAL},",
          "DATAID = #{dataid,jdbcType=DECIMAL},",
          "TAGID = #{tagid,jdbcType=DECIMAL},",
          "TAGVALUE = #{tagvalue,jdbcType=VARCHAR},",
          "ACTIVEFLAG = #{activeflag,jdbcType=CHAR},",
          "CREATEDATE = #{createdate,jdbcType=TIMESTAMP},",
          "PATIENTGLOBALID = #{patientglobalid,jdbcType=DECIMAL}",
        "where ID = #{id,jdbcType=DECIMAL}"
    })
    int updateByPrimaryKey(JProjecttags record);

    @Select({
        "select",
        "ID, USERID, PROJECTID, TABLEID, DATAID, TAGID, TAGVALUE, ACTIVEFLAG, CREATEDATE, ",
        "PATIENTGLOBALID",
        "from J_PROJECTTAGS",
        "where ACTIVEFLAG = '"+JLPConts.ActiveFlag+"'"
        		+ " and TAGID = #{tagId,jdbcType=DECIMAL}"
    })
    @Results({
        @Result(column="ID", property="id", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="USERID", property="userid", jdbcType=JdbcType.DECIMAL),
        @Result(column="PROJECTID", property="projectid", jdbcType=JdbcType.DECIMAL),
        @Result(column="TABLEID", property="tableid", jdbcType=JdbcType.DECIMAL),
        @Result(column="DATAID", property="dataid", jdbcType=JdbcType.DECIMAL),
        @Result(column="TAGID", property="tagid", jdbcType=JdbcType.DECIMAL),
        @Result(column="TAGVALUE", property="tagvalue", jdbcType=JdbcType.VARCHAR),
        @Result(column="ACTIVEFLAG", property="activeflag", jdbcType=JdbcType.CHAR),
        @Result(column="CREATEDATE", property="createdate", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="PATIENTGLOBALID", property="patientglobalid", jdbcType=JdbcType.DECIMAL)
    })
	List<JProjecttags> getByTagId(Long tagId);

    @Select({
        "select",
        "ID, USERID, PROJECTID, TABLEID, DATAID, TAGID, TAGVALUE, ACTIVEFLAG, CREATEDATE, ",
        "PATIENTGLOBALID",
        "from J_PROJECTTAGS",
        "where ACTIVEFLAG = '"+JLPConts.ActiveFlag+"'"
        		+ " and PROJECTID = #{projectId,jdbcType=DECIMAL}"
    })
    @Results({
        @Result(column="ID", property="id", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="USERID", property="userid", jdbcType=JdbcType.DECIMAL),
        @Result(column="PROJECTID", property="projectid", jdbcType=JdbcType.DECIMAL),
        @Result(column="TABLEID", property="tableid", jdbcType=JdbcType.DECIMAL),
        @Result(column="DATAID", property="dataid", jdbcType=JdbcType.DECIMAL),
        @Result(column="TAGID", property="tagid", jdbcType=JdbcType.DECIMAL),
        @Result(column="TAGVALUE", property="tagvalue", jdbcType=JdbcType.VARCHAR),
        @Result(column="ACTIVEFLAG", property="activeflag", jdbcType=JdbcType.CHAR),
        @Result(column="CREATEDATE", property="createdate", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="PATIENTGLOBALID", property="patientglobalid", jdbcType=JdbcType.DECIMAL)
    })
	List<JProjecttags> getByProjectId(Long projectId);
}