package com.callan.service.provider.dao.mapper;

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

import com.callan.service.provider.config.JLPConts;
import com.callan.service.provider.pojo.db.JUsershowviewfield;

public interface JUsershowviewfieldMapper {
    @Delete({
        "delete from J_USERSHOWVIEWFIELD",
        "where ID = #{id,jdbcType=DECIMAL}"
    })
    int deleteByPrimaryKey(Long id);

    @Insert({
        "insert into J_USERSHOWVIEWFIELD (ID, USERID, ",
        "PAGENAME, FIELDID, ",
        "ACTIVEFLAG)",
        "values (#{id,jdbcType=DECIMAL}, #{userid,jdbcType=DECIMAL}, ",
        "#{pagename,jdbcType=VARCHAR}, #{fieldid,jdbcType=DECIMAL}, ",
        "#{activeflag,jdbcType=CHAR})"
    })
    int insert(JUsershowviewfield record);

    @InsertProvider(type=JUsershowviewfieldSqlProvider.class, method="insertSelective")
    int insertSelective(JUsershowviewfield record);

    @Select({
        "select",
        "ID, USERID, PAGENAME, FIELDID, ACTIVEFLAG",
        "from J_USERSHOWVIEWFIELD",
        "where ID = #{id,jdbcType=DECIMAL}"
        + " and ACTIVEFLAG='"+JLPConts.ActiveFlag+"'"
    })
    @Results({
        @Result(column="ID", property="id", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="USERID", property="userid", jdbcType=JdbcType.DECIMAL),
        @Result(column="PAGENAME", property="pagename", jdbcType=JdbcType.VARCHAR),
        @Result(column="FIELDID", property="fieldid", jdbcType=JdbcType.DECIMAL),
        @Result(column="ACTIVEFLAG", property="activeflag", jdbcType=JdbcType.CHAR)
    })
    JUsershowviewfield selectByPrimaryKey(Long id);

    @UpdateProvider(type=JUsershowviewfieldSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(JUsershowviewfield record);

    @Update({
        "update J_USERSHOWVIEWFIELD",
        "set USERID = #{userid,jdbcType=DECIMAL},",
          "PAGENAME = #{pagename,jdbcType=VARCHAR},",
          "FIELDID = #{fieldid,jdbcType=DECIMAL},",
          "ACTIVEFLAG = #{activeflag,jdbcType=CHAR}",
        "where ID = #{id,jdbcType=DECIMAL}"
    })
    int updateByPrimaryKey(JUsershowviewfield record);

    @Select({
        "select",
        "ID, USERID, PAGENAME, FIELDID, ACTIVEFLAG",
        "from J_USERSHOWVIEWFIELD",
        "where PAGENAME = #{pagename,jdbcType=DECIMAL}"
        + " and USERID = #{userId,jdbcType=DECIMAL}"
        + " and ACTIVEFLAG='"+JLPConts.ActiveFlag+"'"
    })
    @Results({
        @Result(column="ID", property="id", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="USERID", property="userid", jdbcType=JdbcType.DECIMAL),
        @Result(column="PAGENAME", property="pagename", jdbcType=JdbcType.VARCHAR),
        @Result(column="FIELDID", property="fieldid", jdbcType=JdbcType.DECIMAL),
        @Result(column="ACTIVEFLAG", property="activeflag", jdbcType=JdbcType.CHAR)
    })
	List<JUsershowviewfield> getByPagenameAndUserId(String pagename, Long userId);
}