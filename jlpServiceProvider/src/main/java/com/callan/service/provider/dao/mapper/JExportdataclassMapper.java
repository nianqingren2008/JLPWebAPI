package com.callan.service.provider.dao.mapper;

import com.callan.service.provider.config.JLPConts;
import com.callan.service.provider.pojo.db.JExportdataclass;

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

public interface JExportdataclassMapper {
    @Delete({
        "delete from J_EXPORTDATACLASS",
        "where ID = #{id,jdbcType=DECIMAL}"
    })
    int deleteByPrimaryKey(Short id);

    @Insert({
        "insert into J_EXPORTDATACLASS (ID, TITLE, ",
        "INFO, CREATEDATE, ",
        "ACTIVEGLAG, TYPE)",
        "values (#{id,jdbcType=DECIMAL}, #{title,jdbcType=VARCHAR}, ",
        "#{info,jdbcType=VARCHAR}, #{createdate,jdbcType=TIMESTAMP}, ",
        "#{activeglag,jdbcType=CHAR}, #{type,jdbcType=CHAR})"
    })
    int insert(JExportdataclass record);

    @InsertProvider(type=JExportdataclassSqlProvider.class, method="insertSelective")
    int insertSelective(JExportdataclass record);

    @Select({
        "select",
        "ID, TITLE, INFO, CREATEDATE, ACTIVEGLAG, TYPE",
        "from J_EXPORTDATACLASS",
        "where ID = #{id,jdbcType=DECIMAL}"
    })
    @Results({
        @Result(column="ID", property="id", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="TITLE", property="title", jdbcType=JdbcType.VARCHAR),
        @Result(column="INFO", property="info", jdbcType=JdbcType.VARCHAR),
        @Result(column="CREATEDATE", property="createdate", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="ACTIVEGLAG", property="activeglag", jdbcType=JdbcType.CHAR),
        @Result(column="TYPE", property="type", jdbcType=JdbcType.CHAR)
    })
    JExportdataclass selectByPrimaryKey(Short id);

    @UpdateProvider(type=JExportdataclassSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(JExportdataclass record);

    @Update({
        "update J_EXPORTDATACLASS",
        "set TITLE = #{title,jdbcType=VARCHAR},",
          "INFO = #{info,jdbcType=VARCHAR},",
          "CREATEDATE = #{createdate,jdbcType=TIMESTAMP},",
          "ACTIVEGLAG = #{activeglag,jdbcType=CHAR},",
          "TYPE = #{type,jdbcType=CHAR}",
        "where ID = #{id,jdbcType=DECIMAL}"
    })
    int updateByPrimaryKey(JExportdataclass record);

    @Select({
        "select",
        "ID, TITLE, INFO, CREATEDATE, ACTIVEGLAG, TYPE",
        "from J_EXPORTDATACLASS",
        "where ACTIVEGLAG = '" + JLPConts.ActiveFlag + "'"
    })
    @Results({
        @Result(column="ID", property="id", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="TITLE", property="title", jdbcType=JdbcType.VARCHAR),
        @Result(column="INFO", property="info", jdbcType=JdbcType.VARCHAR),
        @Result(column="CREATEDATE", property="createdate", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="ACTIVEGLAG", property="activeglag", jdbcType=JdbcType.CHAR),
        @Result(column="TYPE", property="type", jdbcType=JdbcType.CHAR)
    })
	List<JExportdataclass> getAll();
}