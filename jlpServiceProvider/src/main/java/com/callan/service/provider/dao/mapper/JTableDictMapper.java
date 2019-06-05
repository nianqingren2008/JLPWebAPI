package com.callan.service.provider.dao.mapper;

import com.callan.service.provider.pojo.db.JTabledict;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;

public interface JTabledictMapper {
    @Delete({
        "delete from J_TABLEDICT",
        "where ID = #{id,jdbcType=DECIMAL}"
    })
    int deleteByPrimaryKey(Long id);

    @Insert({
        "insert into J_TABLEDICT (ID, CODE, ",
        "NAME, DESCRIPTION, ",
        "SORTNO, ACTIVEFLAG)",
        "values (#{id,jdbcType=DECIMAL}, #{code,jdbcType=VARCHAR}, ",
        "#{name,jdbcType=VARCHAR}, #{description,jdbcType=VARCHAR}, ",
        "#{sortno,jdbcType=DECIMAL}, #{activeflag,jdbcType=CHAR})"
    })
    int insert(JTabledict record);

    @InsertProvider(type=JTabledictSqlProvider.class, method="insertSelective")
    int insertSelective(JTabledict record);

    @Select({
        "select",
        "ID, CODE, NAME, DESCRIPTION, SORTNO, ACTIVEFLAG",
        "from J_TABLEDICT",
        "where ID = #{id,jdbcType=DECIMAL}"
    })
    @Results({
        @Result(column="ID", property="id", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="CODE", property="code", jdbcType=JdbcType.VARCHAR),
        @Result(column="NAME", property="name", jdbcType=JdbcType.VARCHAR),
        @Result(column="DESCRIPTION", property="description", jdbcType=JdbcType.VARCHAR),
        @Result(column="SORTNO", property="sortno", jdbcType=JdbcType.DECIMAL),
        @Result(column="ACTIVEFLAG", property="activeflag", jdbcType=JdbcType.CHAR)
    })
    JTabledict selectByPrimaryKey(Long id);

    @UpdateProvider(type=JTabledictSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(JTabledict record);

    @Update({
        "update J_TABLEDICT",
        "set CODE = #{code,jdbcType=VARCHAR},",
          "NAME = #{name,jdbcType=VARCHAR},",
          "DESCRIPTION = #{description,jdbcType=VARCHAR},",
          "SORTNO = #{sortno,jdbcType=DECIMAL},",
          "ACTIVEFLAG = #{activeflag,jdbcType=CHAR}",
        "where ID = #{id,jdbcType=DECIMAL}"
    })
    int updateByPrimaryKey(JTabledict record);
}