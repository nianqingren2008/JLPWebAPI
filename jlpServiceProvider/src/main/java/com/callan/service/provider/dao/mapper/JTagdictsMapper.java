package com.callan.service.provider.dao.mapper;

import com.callan.service.provider.pojo.db.JTagdicts;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;

public interface JTagdictsMapper {
    @Delete({
        "delete from J_TAGDICT",
        "where ID = #{id,jdbcType=DECIMAL}"
    })
    int deleteByPrimaryKey(Long id);

    @Insert({
        "insert into J_TAGDICT (ID, NAME, ",
        "DESCRIPTION, TAGTYPE, ",
        "VALUETYPE, USERID, ",
        "PROJECTID, CREATEDATE, ",
        "ACTIVEFLAG, SHOWFLAG, ",
        "TAGATTACHED, TOTALNUM, ",
        "CURRENTNUM)",
        "values (#{id,jdbcType=DECIMAL}, #{name,jdbcType=VARCHAR}, ",
        "#{description,jdbcType=VARCHAR}, #{tagtype,jdbcType=CHAR}, ",
        "#{valuetype,jdbcType=CHAR}, #{userid,jdbcType=DECIMAL}, ",
        "#{projectid,jdbcType=DECIMAL}, #{createdate,jdbcType=TIMESTAMP}, ",
        "#{activeflag,jdbcType=CHAR}, #{showflag,jdbcType=CHAR}, ",
        "#{tagattached,jdbcType=VARCHAR}, #{totalnum,jdbcType=DECIMAL}, ",
        "#{currentnum,jdbcType=DECIMAL})"
    })
    int insert(JTagdicts record);

    @InsertProvider(type=JTagdictsSqlProvider.class, method="insertSelective")
    int insertSelective(JTagdicts record);

    @Select({
        "select",
        "ID, NAME, DESCRIPTION, TAGTYPE, VALUETYPE, USERID, PROJECTID, CREATEDATE, ACTIVEFLAG, ",
        "SHOWFLAG, TAGATTACHED, TOTALNUM, CURRENTNUM",
        "from J_TAGDICT",
        "where ID = #{id,jdbcType=DECIMAL}"
    })
    @Results({
        @Result(column="ID", property="id", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="NAME", property="name", jdbcType=JdbcType.VARCHAR),
        @Result(column="DESCRIPTION", property="description", jdbcType=JdbcType.VARCHAR),
        @Result(column="TAGTYPE", property="tagtype", jdbcType=JdbcType.CHAR),
        @Result(column="VALUETYPE", property="valuetype", jdbcType=JdbcType.CHAR),
        @Result(column="USERID", property="userid", jdbcType=JdbcType.DECIMAL),
        @Result(column="PROJECTID", property="projectid", jdbcType=JdbcType.DECIMAL),
        @Result(column="CREATEDATE", property="createdate", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="ACTIVEFLAG", property="activeflag", jdbcType=JdbcType.CHAR),
        @Result(column="SHOWFLAG", property="showflag", jdbcType=JdbcType.CHAR),
        @Result(column="TAGATTACHED", property="tagattached", jdbcType=JdbcType.VARCHAR),
        @Result(column="TOTALNUM", property="totalnum", jdbcType=JdbcType.DECIMAL),
        @Result(column="CURRENTNUM", property="currentnum", jdbcType=JdbcType.DECIMAL)
    })
    JTagdicts selectByPrimaryKey(Long id);

    @UpdateProvider(type=JTagdictsSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(JTagdicts record);

    @Update({
        "update J_TAGDICT",
        "set NAME = #{name,jdbcType=VARCHAR},",
          "DESCRIPTION = #{description,jdbcType=VARCHAR},",
          "TAGTYPE = #{tagtype,jdbcType=CHAR},",
          "VALUETYPE = #{valuetype,jdbcType=CHAR},",
          "USERID = #{userid,jdbcType=DECIMAL},",
          "PROJECTID = #{projectid,jdbcType=DECIMAL},",
          "CREATEDATE = #{createdate,jdbcType=TIMESTAMP},",
          "ACTIVEFLAG = #{activeflag,jdbcType=CHAR},",
          "SHOWFLAG = #{showflag,jdbcType=CHAR},",
          "TAGATTACHED = #{tagattached,jdbcType=VARCHAR},",
          "TOTALNUM = #{totalnum,jdbcType=DECIMAL},",
          "CURRENTNUM = #{currentnum,jdbcType=DECIMAL}",
        "where ID = #{id,jdbcType=DECIMAL}"
    })
    int updateByPrimaryKey(JTagdicts record);
}