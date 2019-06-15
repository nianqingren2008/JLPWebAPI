package com.callan.service.provider.dao.mapper;

import com.callan.service.provider.config.JLPConts;
import com.callan.service.provider.pojo.db.JPageview;

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

public interface JPageviewMapper {
    @Delete({
        "delete from J_PAGEVIEW",
        "where CODE = #{code,jdbcType=VARCHAR}"
    })
    int deleteByPrimaryKey(String code);

    @Insert({
        "insert into J_PAGEVIEW (CODE, PAGENAME, ",
        "ACTIVEFLAG)",
        "values (#{code,jdbcType=VARCHAR}, #{pagename,jdbcType=VARCHAR}, ",
        "#{activeflag,jdbcType=CHAR})"
    })
    int insert(JPageview record);

    @InsertProvider(type=JPageviewSqlProvider.class, method="insertSelective")
    int insertSelective(JPageview record);

    @Select({
        "select",
        "CODE, PAGENAME, ACTIVEFLAG",
        "from J_PAGEVIEW",
        "where CODE = #{code,jdbcType=VARCHAR}"
    })
    @Results({
        @Result(column="CODE", property="code", jdbcType=JdbcType.VARCHAR, id=true),
        @Result(column="PAGENAME", property="pagename", jdbcType=JdbcType.VARCHAR),
        @Result(column="ACTIVEFLAG", property="activeflag", jdbcType=JdbcType.CHAR)
    })
    JPageview selectByPrimaryKey(String code);

    @UpdateProvider(type=JPageviewSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(JPageview record);

    @Update({
        "update J_PAGEVIEW",
        "set PAGENAME = #{pagename,jdbcType=VARCHAR},",
          "ACTIVEFLAG = #{activeflag,jdbcType=CHAR}",
        "where CODE = #{code,jdbcType=VARCHAR}"
    })
    int updateByPrimaryKey(JPageview record);

    @Select({
        "select",
        "CODE, PAGENAME, ACTIVEFLAG",
        "from J_PAGEVIEW",
        "where ACTIVEFLAG = '"+ JLPConts.ActiveFlag+"'"
    })
    @Results({
        @Result(column="CODE", property="code", jdbcType=JdbcType.VARCHAR, id=true),
        @Result(column="PAGENAME", property="pagename", jdbcType=JdbcType.VARCHAR),
        @Result(column="ACTIVEFLAG", property="activeflag", jdbcType=JdbcType.CHAR)
    })
	List<JPageview> getAll();
}