package com.callan.service.provider.dao.mapper;

import com.callan.service.provider.config.JLPConts;
import com.callan.service.provider.pojo.db.JPixconfig;

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

public interface JPixconfigMapper {
    @Delete({
        "delete from J_PIXCONFIG",
        "where ID = #{id,jdbcType=DECIMAL}"
    })
    int deleteByPrimaryKey(Integer id);

    @Insert({
        "insert into J_PIXCONFIG (ID, FIELDNAME, ",
        "WEIGHTCOEFFICIENT, ACTIVEFLAG)",
        "values (#{id,jdbcType=DECIMAL}, #{fieldname,jdbcType=VARCHAR}, ",
        "#{weightcoefficient,jdbcType=DECIMAL}, #{activeflag,jdbcType=CHAR})"
    })
    int insert(JPixconfig record);

    @InsertProvider(type=JPixconfigSqlProvider.class, method="insertSelective")
    int insertSelective(JPixconfig record);

    @Select({
        "select",
        "ID, FIELDNAME, WEIGHTCOEFFICIENT, ACTIVEFLAG",
        "from J_PIXCONFIG",
        "where ID = #{id,jdbcType=DECIMAL}"
    })
    @Results({
        @Result(column="ID", property="id", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="FIELDNAME", property="fieldname", jdbcType=JdbcType.VARCHAR),
        @Result(column="WEIGHTCOEFFICIENT", property="weightcoefficient", jdbcType=JdbcType.DECIMAL),
        @Result(column="ACTIVEFLAG", property="activeflag", jdbcType=JdbcType.CHAR)
    })
    JPixconfig selectByPrimaryKey(Integer id);

    @UpdateProvider(type=JPixconfigSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(JPixconfig record);

    @Update({
        "update J_PIXCONFIG",
        "set FIELDNAME = #{fieldname,jdbcType=VARCHAR},",
          "WEIGHTCOEFFICIENT = #{weightcoefficient,jdbcType=DECIMAL},",
          "ACTIVEFLAG = #{activeflag,jdbcType=CHAR}",
        "where ID = #{id,jdbcType=DECIMAL}"
    })
    int updateByPrimaryKey(JPixconfig record);

    @Select({
        "select",
        "ID, FIELDNAME, WEIGHTCOEFFICIENT, ACTIVEFLAG",
        "from J_PIXCONFIG",
        "where ACTIVEFLAG = '"+JLPConts.ActiveFlag+"'"
    })
    @Results({
        @Result(column="ID", property="id", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="FIELDNAME", property="fieldname", jdbcType=JdbcType.VARCHAR),
        @Result(column="WEIGHTCOEFFICIENT", property="weightcoefficient", jdbcType=JdbcType.DECIMAL),
        @Result(column="ACTIVEFLAG", property="activeflag", jdbcType=JdbcType.CHAR)
    })
	List<JPixconfig> getAll();
}