package com.callan.service.provider.dao.mapper;

import com.callan.service.provider.config.JLPConts;
import com.callan.service.provider.pojo.db.JUsertokens;

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

public interface JUsertokensMapper {
    @Delete({
        "delete from J_USERTOKENS",
        "where ID = #{id,jdbcType=DECIMAL}"
    })
    int deleteByPrimaryKey(Long id);

    @Insert({
        "insert into J_USERTOKENS (ID, TOKEN, ",
        "LOGINIP, CREATEDATE, ",
        "ACTIVEDATE, ACTIVEFLAG)",
        "values (#{id,jdbcType=DECIMAL}, #{token,jdbcType=VARCHAR}, ",
        "#{loginip,jdbcType=VARCHAR}, #{createdate,jdbcType=TIMESTAMP}, ",
        "#{activedate,jdbcType=TIMESTAMP}, #{activeflag,jdbcType=CHAR})"
    })
    int insert(JUsertokens record);

    @InsertProvider(type=JUsertokensSqlProvider.class, method="insertSelective")
    int insertSelective(JUsertokens record);

    @Select({
        "select",
        "ID, TOKEN, LOGINIP, CREATEDATE, ACTIVEDATE, ACTIVEFLAG",
        "from J_USERTOKENS",
        "where ID = #{id,jdbcType=DECIMAL} and ACTIVEDATE='"+JLPConts.ActiveFlag+"'"
    })
    @Results({
        @Result(column="ID", property="id", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="TOKEN", property="token", jdbcType=JdbcType.VARCHAR),
        @Result(column="LOGINIP", property="loginip", jdbcType=JdbcType.VARCHAR),
        @Result(column="CREATEDATE", property="createdate", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="ACTIVEDATE", property="activedate", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="ACTIVEFLAG", property="activeflag", jdbcType=JdbcType.CHAR)
    })
    JUsertokens selectByPrimaryKey(Long id);

    @UpdateProvider(type=JUsertokensSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(JUsertokens record);

    @Update({
        "update J_USERTOKENS",
        "set TOKEN = #{token,jdbcType=VARCHAR},",
          "LOGINIP = #{loginip,jdbcType=VARCHAR},",
          "CREATEDATE = #{createdate,jdbcType=TIMESTAMP},",
          "ACTIVEDATE = #{activedate,jdbcType=TIMESTAMP},",
          "ACTIVEFLAG = #{activeflag,jdbcType=CHAR}",
        "where ID = #{id,jdbcType=DECIMAL}"
    })
    int updateByPrimaryKey(JUsertokens record);

    @Select({
        "select",
        "ID, TOKEN, LOGINIP, CREATEDATE, ACTIVEDATE, ACTIVEFLAG",
        "from J_USERTOKENS",
        "where ID = #{id,jdbcType=DECIMAL} and ACTIVEDATE='"+JLPConts.ActiveFlag+"'"
    })
    @Results({
        @Result(column="ID", property="id", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="TOKEN", property="token", jdbcType=JdbcType.VARCHAR),
        @Result(column="LOGINIP", property="loginip", jdbcType=JdbcType.VARCHAR),
        @Result(column="CREATEDATE", property="createdate", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="ACTIVEDATE", property="activedate", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="ACTIVEFLAG", property="activeflag", jdbcType=JdbcType.CHAR)
    })
	List<JUsertokens> getAll();
}