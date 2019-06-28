package com.callan.service.provider.dao.mapper;

import com.callan.service.provider.config.JLPConts;
import com.callan.service.provider.pojo.db.JProjectstatistics;

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

public interface JProjectstatisticsMapper {
    @Delete({
        "delete from J_PROJECTSTATISTICS",
        "where ID = #{id,jdbcType=DECIMAL}"
    })
    int deleteByPrimaryKey(Long id);

    @Insert({
        "insert into J_PROJECTSTATISTICS (ID, PROJECTID, ",
        "STATISTICSTYPE, STATISTICSTYPEDATAID, ",
        "COUNT, CREATEDATE, ",
        "ACTIVEFLAG)",
        "values (#{id,jdbcType=DECIMAL}, #{projectid,jdbcType=DECIMAL}, ",
        "#{statisticstype,jdbcType=VARCHAR}, #{statisticstypedataid,jdbcType=DECIMAL}, ",
        "#{count,jdbcType=DECIMAL}, #{createdate,jdbcType=TIMESTAMP}, ",
        "#{activeflag,jdbcType=CHAR})"
    })
    int insert(JProjectstatistics record);

    @InsertProvider(type=JProjectstatisticsSqlProvider.class, method="insertSelective")
    int insertSelective(JProjectstatistics record);

    @Select({
        "select",
        "ID, PROJECTID, STATISTICSTYPE, STATISTICSTYPEDATAID, COUNT, CREATEDATE, ACTIVEFLAG",
        "from J_PROJECTSTATISTICS",
        "where ID = #{id,jdbcType=DECIMAL}"
    })
    @Results({
        @Result(column="ID", property="id", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="PROJECTID", property="projectid", jdbcType=JdbcType.DECIMAL),
        @Result(column="STATISTICSTYPE", property="statisticstype", jdbcType=JdbcType.VARCHAR),
        @Result(column="STATISTICSTYPEDATAID", property="statisticstypedataid", jdbcType=JdbcType.DECIMAL),
        @Result(column="COUNT", property="count", jdbcType=JdbcType.DECIMAL),
        @Result(column="CREATEDATE", property="createdate", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="ACTIVEFLAG", property="activeflag", jdbcType=JdbcType.CHAR)
    })
    JProjectstatistics selectByPrimaryKey(Long id);

    @UpdateProvider(type=JProjectstatisticsSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(JProjectstatistics record);

    @Update({
        "update J_PROJECTSTATISTICS",
        "set PROJECTID = #{projectid,jdbcType=DECIMAL},",
          "STATISTICSTYPE = #{statisticstype,jdbcType=VARCHAR},",
          "STATISTICSTYPEDATAID = #{statisticstypedataid,jdbcType=DECIMAL},",
          "COUNT = #{count,jdbcType=DECIMAL},",
          "CREATEDATE = #{createdate,jdbcType=TIMESTAMP},",
          "ACTIVEFLAG = #{activeflag,jdbcType=CHAR}",
        "where ID = #{id,jdbcType=DECIMAL}"
    })
    int updateByPrimaryKey(JProjectstatistics record);

    @Select({
        "select",
        "ID, PROJECTID, STATISTICSTYPE, STATISTICSTYPEDATAID, COUNT, CREATEDATE, ACTIVEFLAG",
        "from J_PROJECTSTATISTICS",
        "where projectid = #{projectid,jdbcType=DECIMAL}"
        + " and STATISTICSTYPEDATAID=#{statisticsTypedataId,jdbcType=DECIMAL}"
        + " and ACTIVEFLAG='"+JLPConts.ActiveFlag+"'"
        + " and STATISTICSTYPE='1'"
    })
    @Results({
        @Result(column="ID", property="id", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="PROJECTID", property="projectid", jdbcType=JdbcType.DECIMAL),
        @Result(column="STATISTICSTYPE", property="statisticstype", jdbcType=JdbcType.VARCHAR),
        @Result(column="STATISTICSTYPEDATAID", property="statisticstypedataid", jdbcType=JdbcType.DECIMAL),
        @Result(column="COUNT", property="count", jdbcType=JdbcType.DECIMAL),
        @Result(column="CREATEDATE", property="createdate", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="ACTIVEFLAG", property="activeflag", jdbcType=JdbcType.CHAR)
    })
	List<JProjectstatistics> getByProjectIdAndStatisticstypedataid(Long projectid, Long statisticsTypedataId);
}