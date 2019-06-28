package com.callan.service.provider.dao.mapper;

import com.callan.service.provider.config.JLPConts;
import com.callan.service.provider.pojo.db.JProjectdefaultstatus;
import com.callan.service.provider.pojo.db.JProjectstatistics;

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

public interface JProjectdefaultstatusMapper {
    @Delete({
        "delete from J_PROJECTDEFAULTSTATUS",
        "where ID = #{id,jdbcType=DECIMAL}"
    })
    int deleteByPrimaryKey(Long id);

    @Insert({
        "insert into J_PROJECTDEFAULTSTATUS (ID, PROJECTID, ",
        "DATASTATUSID, CREATEDATE, ",
        "ACTIVEFLAG)",
        "values (#{id,jdbcType=DECIMAL}, #{projectid,jdbcType=DECIMAL}, ",
        "#{datastatusid,jdbcType=DECIMAL}, #{createdate,jdbcType=TIMESTAMP}, ",
        "#{activeflag,jdbcType=CHAR})"
    })
    int insert(JProjectdefaultstatus record);

    @InsertProvider(type=JProjectdefaultstatusSqlProvider.class, method="insertSelective")
    int insertSelective(JProjectdefaultstatus record);

    @Select({
        "select",
        "ID, PROJECTID, DATASTATUSID, CREATEDATE, ACTIVEFLAG",
        "from J_PROJECTDEFAULTSTATUS",
        "where ID = #{id,jdbcType=DECIMAL}"
    })
    @Results({
        @Result(column="ID", property="id", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="PROJECTID", property="projectid", jdbcType=JdbcType.DECIMAL),
        @Result(column="DATASTATUSID", property="datastatusid", jdbcType=JdbcType.DECIMAL),
        @Result(column="CREATEDATE", property="createdate", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="ACTIVEFLAG", property="activeflag", jdbcType=JdbcType.CHAR)
    })
    JProjectdefaultstatus selectByPrimaryKey(Long id);

    @UpdateProvider(type=JProjectdefaultstatusSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(JProjectdefaultstatus record);

    @Update({
        "update J_PROJECTDEFAULTSTATUS",
        "set PROJECTID = #{projectid,jdbcType=DECIMAL},",
          "DATASTATUSID = #{datastatusid,jdbcType=DECIMAL},",
          "CREATEDATE = #{createdate,jdbcType=TIMESTAMP},",
          "ACTIVEFLAG = #{activeflag,jdbcType=CHAR}",
        "where ID = #{id,jdbcType=DECIMAL}"
    })
    int updateByPrimaryKey(JProjectdefaultstatus record);

    @Select({
        "select",
        "ID, PROJECTID, DATASTATUSID, CREATEDATE, ACTIVEFLAG",
        "from J_PROJECTDEFAULTSTATUS",
        "where datastatusid = #{datastatusid,jdbcType=DECIMAL} and ACTIVEFLAG='"+JLPConts.ActiveFlag+"'"
    })
    @Results({
        @Result(column="ID", property="id", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="PROJECTID", property="projectid", jdbcType=JdbcType.DECIMAL),
        @Result(column="DATASTATUSID", property="datastatusid", jdbcType=JdbcType.DECIMAL),
        @Result(column="CREATEDATE", property="createdate", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="ACTIVEFLAG", property="activeflag", jdbcType=JdbcType.CHAR)
    })
	List<JProjectdefaultstatus> getByDatastatusid(Long datastatusid);

    @Select({
        "select",
        "ID, PROJECTID, DATASTATUSID, CREATEDATE, ACTIVEFLAG",
        "from J_PROJECTDEFAULTSTATUS",
        "where projectId = #{projectId,jdbcType=DECIMAL} and ACTIVEFLAG='"+JLPConts.ActiveFlag+"'"
    })
    @Results({
        @Result(column="ID", property="id", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="PROJECTID", property="projectid", jdbcType=JdbcType.DECIMAL),
        @Result(column="DATASTATUSID", property="datastatusid", jdbcType=JdbcType.DECIMAL),
        @Result(column="CREATEDATE", property="createdate", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="ACTIVEFLAG", property="activeflag", jdbcType=JdbcType.CHAR)
    })
    List<JProjectdefaultstatus> getByProjectId(Long projectId);
}