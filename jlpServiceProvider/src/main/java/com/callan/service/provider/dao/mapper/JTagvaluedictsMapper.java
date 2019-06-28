package com.callan.service.provider.dao.mapper;

import com.callan.service.provider.config.JLPConts;
import com.callan.service.provider.pojo.db.JTagvaluedicts;

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

public interface JTagvaluedictsMapper {
    @Delete({
        "delete from J_TAGVALUEDICT",
        "where ID = #{id,jdbcType=DECIMAL}"
    })
    int deleteByPrimaryKey(Long id);

    @Insert({
        "insert into J_TAGVALUEDICT (ID, TAGID, ",
        "VALUETYPE, VALUE, MINVALUE, ",
        "MAXVALUE, CREATEDATE, ",
        "ACTIVEFLAG)",
        "values (#{id,jdbcType=DECIMAL}, #{tagid,jdbcType=DECIMAL}, ",
        "#{valuetype,jdbcType=CHAR}, #{value,jdbcType=VARCHAR}, #{minvalue,jdbcType=VARCHAR}, ",
        "#{maxvalue,jdbcType=VARCHAR}, #{createdate,jdbcType=TIMESTAMP}, ",
        "#{activeflag,jdbcType=CHAR})"
    })
    int insert(JTagvaluedicts record);

    @InsertProvider(type=JTagvaluedictsSqlProvider.class, method="insertSelective")
    int insertSelective(JTagvaluedicts record);

    @Select({
        "select",
        "ID, TAGID, VALUETYPE, VALUE, MINVALUE, MAXVALUE, CREATEDATE, ACTIVEFLAG",
        "from J_TAGVALUEDICT",
        "where ID = #{id,jdbcType=DECIMAL} and ACTIVEFLAG = '"+JLPConts.ActiveFlag+"'"
    })
    @Results({
        @Result(column="ID", property="id", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="TAGID", property="tagid", jdbcType=JdbcType.DECIMAL),
        @Result(column="VALUETYPE", property="valuetype", jdbcType=JdbcType.CHAR),
        @Result(column="VALUE", property="value", jdbcType=JdbcType.VARCHAR),
        @Result(column="MINVALUE", property="minvalue", jdbcType=JdbcType.VARCHAR),
        @Result(column="MAXVALUE", property="maxvalue", jdbcType=JdbcType.VARCHAR),
        @Result(column="CREATEDATE", property="createdate", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="ACTIVEFLAG", property="activeflag", jdbcType=JdbcType.CHAR)
    })
    JTagvaluedicts selectByPrimaryKey(Long id);

    @UpdateProvider(type=JTagvaluedictsSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(JTagvaluedicts record);

    @Update({
        "update J_TAGVALUEDICT",
        "set TAGID = #{tagid,jdbcType=DECIMAL},",
          "VALUETYPE = #{valuetype,jdbcType=CHAR},",
          "VALUE = #{value,jdbcType=VARCHAR},",
          "MINVALUE = #{minvalue,jdbcType=VARCHAR},",
          "MAXVALUE = #{maxvalue,jdbcType=VARCHAR},",
          "CREATEDATE = #{createdate,jdbcType=TIMESTAMP},",
          "ACTIVEFLAG = #{activeflag,jdbcType=CHAR}",
        "where ID = #{id,jdbcType=DECIMAL}"
    })
    int updateByPrimaryKey(JTagvaluedicts record);

    @Select({
        "select",
        "ID, TAGID, VALUETYPE, VALUE, MINVALUE, MAXVALUE, CREATEDATE, ACTIVEFLAG",
        "from J_TAGVALUEDICT",
        "where ACTIVEFLAG = '"+JLPConts.ActiveFlag+"'"
    })
    @Results({
        @Result(column="ID", property="id", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="TAGID", property="tagid", jdbcType=JdbcType.DECIMAL),
        @Result(column="VALUETYPE", property="valuetype", jdbcType=JdbcType.CHAR),
        @Result(column="VALUE", property="value", jdbcType=JdbcType.VARCHAR),
        @Result(column="MINVALUE", property="minvalue", jdbcType=JdbcType.VARCHAR),
        @Result(column="MAXVALUE", property="maxvalue", jdbcType=JdbcType.VARCHAR),
        @Result(column="CREATEDATE", property="createdate", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="ACTIVEFLAG", property="activeflag", jdbcType=JdbcType.CHAR)
    })
	List<JTagvaluedicts> getAll();
}