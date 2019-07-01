package com.callan.service.provider.dao.mapper;

import com.callan.service.provider.pojo.db.JControltypedict;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;

public interface JControltypedictMapper {
    @Delete({
        "delete from J_CONTROLTYPEDICT",
        "where CODE = #{code,jdbcType=VARCHAR}"
    })
    int deleteByPrimaryKey(String code);

    @Insert({
        "insert into J_CONTROLTYPEDICT (CODE, NAME, ",
        "ACTIVEFLAG)",
        "values (#{code,jdbcType=VARCHAR}, #{name,jdbcType=VARCHAR}, ",
        "#{activeflag,jdbcType=CHAR})"
    })
    int insert(JControltypedict record);

    @InsertProvider(type=JControltypedictSqlProvider.class, method="insertSelective")
    int insertSelective(JControltypedict record);

    @Select({
        "select",
        "CODE, NAME, ACTIVEFLAG",
        "from J_CONTROLTYPEDICT",
        "where CODE = #{code,jdbcType=VARCHAR}"
    })
    @Results({
        @Result(column="CODE", property="code", jdbcType=JdbcType.VARCHAR, id=true),
        @Result(column="NAME", property="name", jdbcType=JdbcType.VARCHAR),
        @Result(column="ACTIVEFLAG", property="activeflag", jdbcType=JdbcType.CHAR)
    })
    JControltypedict selectByPrimaryKey(String code);

    @UpdateProvider(type=JControltypedictSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(JControltypedict record);

    @Update({
        "update J_CONTROLTYPEDICT",
        "set NAME = #{name,jdbcType=VARCHAR},",
          "ACTIVEFLAG = #{activeflag,jdbcType=CHAR}",
        "where CODE = #{code,jdbcType=VARCHAR}"
    })
    int updateByPrimaryKey(JControltypedict record);
}