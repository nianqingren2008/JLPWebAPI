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
import com.callan.service.provider.pojo.db.JProjectdatastatusdict;

public interface JProjectdatastatusdictMapper {
    @Delete({
        "delete from J_PROJECTDATASTATUSDICT",
        "where ID = #{id,jdbcType=DECIMAL}"
    })
    int deleteByPrimaryKey(Long id);

    @Insert({
        "insert into J_PROJECTDATASTATUSDICT (ID, NAME, ",
        "PROJECTID, ACTIVEFLAG, ",
        "CREATEDATE, STATUSTYPE)",
        "values (#{id,jdbcType=DECIMAL}, #{name,jdbcType=VARCHAR}, ",
        "#{projectid,jdbcType=DECIMAL}, #{activeflag,jdbcType=CHAR}, ",
        "#{createdate,jdbcType=TIMESTAMP}, #{statustype,jdbcType=CHAR})"
    })
    int insert(JProjectdatastatusdict record);

    @InsertProvider(type=JProjectdatastatusdictSqlProvider.class, method="insertSelective")
    int insertSelective(JProjectdatastatusdict record);

    @Select({
        "select",
        "ID, NAME, PROJECTID, ACTIVEFLAG, CREATEDATE, STATUSTYPE",
        "from J_PROJECTDATASTATUSDICT",
        "where ID = #{id,jdbcType=DECIMAL} and ACTIVEFLAG='"+JLPConts.ActiveFlag+"'"
    })
    @Results({
        @Result(column="ID", property="id", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="NAME", property="name", jdbcType=JdbcType.VARCHAR),
        @Result(column="PROJECTID", property="projectid", jdbcType=JdbcType.DECIMAL),
        @Result(column="ACTIVEFLAG", property="activeflag", jdbcType=JdbcType.CHAR),
        @Result(column="CREATEDATE", property="createdate", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="STATUSTYPE", property="statustype", jdbcType=JdbcType.CHAR)
    })
    JProjectdatastatusdict selectByPrimaryKey(Long id);

    @UpdateProvider(type=JProjectdatastatusdictSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(JProjectdatastatusdict record);

    @Update({
        "update J_PROJECTDATASTATUSDICT",
        "set NAME = #{name,jdbcType=VARCHAR},",
          "PROJECTID = #{projectid,jdbcType=DECIMAL},",
          "ACTIVEFLAG = #{activeflag,jdbcType=CHAR},",
          "CREATEDATE = #{createdate,jdbcType=TIMESTAMP},",
          "STATUSTYPE = #{statustype,jdbcType=CHAR}",
        "where ID = #{id,jdbcType=DECIMAL}"
    })
    int updateByPrimaryKey(JProjectdatastatusdict record);

//    @Select({
//        "select",
//        "ID, NAME, PROJECTID, ACTIVEFLAG, CREATEDATE, STATUSTYPE",
//        "from J_PROJECTDATASTATUSDICT",
//        "where PROJECTID = #{id,jdbcType=DECIMAL} and ID in(#{projectstatusIDStr}) "
//        + " and ACTIVEFLAG='"+JLPConts.ActiveFlag+"'"
//    })
//    @Results({
//        @Result(column="ID", property="id", jdbcType=JdbcType.DECIMAL, id=true),
//        @Result(column="NAME", property="name", jdbcType=JdbcType.VARCHAR),
//        @Result(column="PROJECTID", property="projectid", jdbcType=JdbcType.DECIMAL),
//        @Result(column="ACTIVEFLAG", property="activeflag", jdbcType=JdbcType.CHAR),
//        @Result(column="CREATEDATE", property="createdate", jdbcType=JdbcType.TIMESTAMP),
//        @Result(column="STATUSTYPE", property="statustype", jdbcType=JdbcType.CHAR)
//    })
//	List<JProjectdatastatusdict> getByProjectIdAndProjectstatusIDs(Long projectId, String projectstatusIDStr);

    @Select({
        "select",
        "ID, NAME, PROJECTID, ACTIVEFLAG, CREATEDATE, STATUSTYPE",
        "from J_PROJECTDATASTATUSDICT",
        "where projectId = #{projectId,jdbcType=DECIMAL} and ACTIVEFLAG='"+JLPConts.ActiveFlag+"'"
    })
    @Results({
        @Result(column="ID", property="id", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="NAME", property="name", jdbcType=JdbcType.VARCHAR),
        @Result(column="PROJECTID", property="projectid", jdbcType=JdbcType.DECIMAL),
        @Result(column="ACTIVEFLAG", property="activeflag", jdbcType=JdbcType.CHAR),
        @Result(column="CREATEDATE", property="createdate", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="STATUSTYPE", property="statustype", jdbcType=JdbcType.CHAR)
    })
	List<JProjectdatastatusdict> getByProjectId(Long projectId);

}