package com.callan.service.provider.dao.mapper;

import com.callan.service.provider.pojo.db.JTablefielddict;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;

public interface JTablefielddictMapper {
    @Delete({
        "delete from J_TABLEFIELDDICT",
        "where ID = #{id,jdbcType=DECIMAL}"
    })
    int deleteByPrimaryKey(Long id);

    @Insert({
        "insert into J_TABLEFIELDDICT (ID, TABLECODE, ",
        "CODE, NAME, TYPE, ",
        "DESCRIPTION, SORTNO, ",
        "CONTROLTYPECODE, NULLABLE, ",
        "DICTNAME, QUERYFLAG, ",
        "SHOWFLAG, SORTFLAG)",
        "values (#{id,jdbcType=DECIMAL}, #{tablecode,jdbcType=VARCHAR}, ",
        "#{code,jdbcType=VARCHAR}, #{name,jdbcType=VARCHAR}, #{type,jdbcType=VARCHAR}, ",
        "#{description,jdbcType=VARCHAR}, #{sortno,jdbcType=DECIMAL}, ",
        "#{controltypecode,jdbcType=VARCHAR}, #{nullable,jdbcType=CHAR}, ",
        "#{dictname,jdbcType=VARCHAR}, #{queryflag,jdbcType=CHAR}, ",
        "#{showflag,jdbcType=CHAR}, #{sortflag,jdbcType=CHAR})"
    })
    int insert(JTablefielddict record);

    @InsertProvider(type=JTablefielddictSqlProvider.class, method="insertSelective")
    int insertSelective(JTablefielddict record);

    @Select({
        "select",
        "ID, TABLECODE, CODE, NAME, TYPE, DESCRIPTION, SORTNO, CONTROLTYPECODE, NULLABLE, ",
        "DICTNAME, QUERYFLAG, SHOWFLAG, SORTFLAG",
        "from J_TABLEFIELDDICT",
        "where ID = #{id,jdbcType=DECIMAL}"
    })
    @Results({
        @Result(column="ID", property="id", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="TABLECODE", property="tablecode", jdbcType=JdbcType.VARCHAR),
        @Result(column="CODE", property="code", jdbcType=JdbcType.VARCHAR),
        @Result(column="NAME", property="name", jdbcType=JdbcType.VARCHAR),
        @Result(column="TYPE", property="type", jdbcType=JdbcType.VARCHAR),
        @Result(column="DESCRIPTION", property="description", jdbcType=JdbcType.VARCHAR),
        @Result(column="SORTNO", property="sortno", jdbcType=JdbcType.DECIMAL),
        @Result(column="CONTROLTYPECODE", property="controltypecode", jdbcType=JdbcType.VARCHAR),
        @Result(column="NULLABLE", property="nullable", jdbcType=JdbcType.CHAR),
        @Result(column="DICTNAME", property="dictname", jdbcType=JdbcType.VARCHAR),
        @Result(column="QUERYFLAG", property="queryflag", jdbcType=JdbcType.CHAR),
        @Result(column="SHOWFLAG", property="showflag", jdbcType=JdbcType.CHAR),
        @Result(column="SORTFLAG", property="sortflag", jdbcType=JdbcType.CHAR)
    })
    JTablefielddict selectByPrimaryKey(Long id);

    @UpdateProvider(type=JTablefielddictSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(JTablefielddict record);

    @Update({
        "update J_TABLEFIELDDICT",
        "set TABLECODE = #{tablecode,jdbcType=VARCHAR},",
          "CODE = #{code,jdbcType=VARCHAR},",
          "NAME = #{name,jdbcType=VARCHAR},",
          "TYPE = #{type,jdbcType=VARCHAR},",
          "DESCRIPTION = #{description,jdbcType=VARCHAR},",
          "SORTNO = #{sortno,jdbcType=DECIMAL},",
          "CONTROLTYPECODE = #{controltypecode,jdbcType=VARCHAR},",
          "NULLABLE = #{nullable,jdbcType=CHAR},",
          "DICTNAME = #{dictname,jdbcType=VARCHAR},",
          "QUERYFLAG = #{queryflag,jdbcType=CHAR},",
          "SHOWFLAG = #{showflag,jdbcType=CHAR},",
          "SORTFLAG = #{sortflag,jdbcType=CHAR}",
        "where ID = #{id,jdbcType=DECIMAL}"
    })
    int updateByPrimaryKey(JTablefielddict record);
}