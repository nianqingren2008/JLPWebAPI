package com.callan.service.provider.dao.mapper;

import com.callan.service.provider.config.JLPConts;
import com.callan.service.provider.pojo.db.JPageviewdetail;

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

public interface JPageviewdetailMapper {
    @Delete({
        "delete from J_PAGEVIEWDETAIL",
        "where ID = #{id,jdbcType=DECIMAL}"
    })
    int deleteByPrimaryKey(Integer id);

    @Insert({
        "insert into J_PAGEVIEWDETAIL (ID, PAGECODE, ",
        "VIEWCODE, SORTNO, ",
        "ACTIVEFLAG)",
        "values (#{id,jdbcType=DECIMAL}, #{pagecode,jdbcType=VARCHAR}, ",
        "#{viewcode,jdbcType=VARCHAR}, #{sortno,jdbcType=DECIMAL}, ",
        "#{activeflag,jdbcType=CHAR})"
    })
    int insert(JPageviewdetail record);

    @InsertProvider(type=JPageviewdetailSqlProvider.class, method="insertSelective")
    int insertSelective(JPageviewdetail record);

    @Select({
        "select",
        "ID, PAGECODE, VIEWCODE, SORTNO, ACTIVEFLAG",
        "from J_PAGEVIEWDETAIL",
        "where ID = #{id,jdbcType=DECIMAL}"
    })
    @Results({
        @Result(column="ID", property="id", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="PAGECODE", property="pagecode", jdbcType=JdbcType.VARCHAR),
        @Result(column="VIEWCODE", property="viewcode", jdbcType=JdbcType.VARCHAR),
        @Result(column="SORTNO", property="sortno", jdbcType=JdbcType.DECIMAL),
        @Result(column="ACTIVEFLAG", property="activeflag", jdbcType=JdbcType.CHAR)
    })
    JPageviewdetail selectByPrimaryKey(Integer id);

    @UpdateProvider(type=JPageviewdetailSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(JPageviewdetail record);

    @Update({
        "update J_PAGEVIEWDETAIL",
        "set PAGECODE = #{pagecode,jdbcType=VARCHAR},",
          "VIEWCODE = #{viewcode,jdbcType=VARCHAR},",
          "SORTNO = #{sortno,jdbcType=DECIMAL},",
          "ACTIVEFLAG = #{activeflag,jdbcType=CHAR}",
        "where ID = #{id,jdbcType=DECIMAL}"
    })
    int updateByPrimaryKey(JPageviewdetail record);

    @Select({
        "select",
        "ID, PAGECODE, VIEWCODE, SORTNO, ACTIVEFLAG",
        "from J_PAGEVIEWDETAIL",
        "where ACTIVEFLAG='"+JLPConts.ActiveFlag+"'"
    })
    @Results({
        @Result(column="ID", property="id", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="PAGECODE", property="pagecode", jdbcType=JdbcType.VARCHAR),
        @Result(column="VIEWCODE", property="viewcode", jdbcType=JdbcType.VARCHAR),
        @Result(column="SORTNO", property="sortno", jdbcType=JdbcType.DECIMAL),
        @Result(column="ACTIVEFLAG", property="activeflag", jdbcType=JdbcType.CHAR)
    })
	List<JPageviewdetail> getAll();
}