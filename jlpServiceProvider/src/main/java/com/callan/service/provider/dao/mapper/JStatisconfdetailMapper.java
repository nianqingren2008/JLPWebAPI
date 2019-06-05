package com.callan.service.provider.dao.mapper;

import com.callan.service.provider.pojo.db.JStatisconfdetail;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;

public interface JStatisconfdetailMapper {
    @Delete({
        "delete from J_STATISCONFDETAIL",
        "where ID = #{id,jdbcType=DECIMAL}"
    })
    int deleteByPrimaryKey(Long id);

    @Insert({
        "insert into J_STATISCONFDETAIL (ID, STATISCONFID, ",
        "DETAILTYPE, FIELDID, ",
        "FIELDTYPETRANS, SORTNO, ",
        "CREATEDATE, ACTIVEFLAG, ",
        "DEFAULVALUE)",
        "values (#{id,jdbcType=DECIMAL}, #{statisconfid,jdbcType=DECIMAL}, ",
        "#{detailtype,jdbcType=CHAR}, #{fieldid,jdbcType=DECIMAL}, ",
        "#{fieldtypetrans,jdbcType=VARCHAR}, #{sortno,jdbcType=DECIMAL}, ",
        "#{createdate,jdbcType=TIMESTAMP}, #{activeflag,jdbcType=CHAR}, ",
        "#{defaulvalue,jdbcType=CHAR})"
    })
    int insert(JStatisconfdetail record);

    @InsertProvider(type=JStatisconfdetailSqlProvider.class, method="insertSelective")
    int insertSelective(JStatisconfdetail record);

    @Select({
        "select",
        "ID, STATISCONFID, DETAILTYPE, FIELDID, FIELDTYPETRANS, SORTNO, CREATEDATE, ACTIVEFLAG, ",
        "DEFAULVALUE",
        "from J_STATISCONFDETAIL",
        "where ID = #{id,jdbcType=DECIMAL}"
    })
    @Results({
        @Result(column="ID", property="id", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="STATISCONFID", property="statisconfid", jdbcType=JdbcType.DECIMAL),
        @Result(column="DETAILTYPE", property="detailtype", jdbcType=JdbcType.CHAR),
        @Result(column="FIELDID", property="fieldid", jdbcType=JdbcType.DECIMAL),
        @Result(column="FIELDTYPETRANS", property="fieldtypetrans", jdbcType=JdbcType.VARCHAR),
        @Result(column="SORTNO", property="sortno", jdbcType=JdbcType.DECIMAL),
        @Result(column="CREATEDATE", property="createdate", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="ACTIVEFLAG", property="activeflag", jdbcType=JdbcType.CHAR),
        @Result(column="DEFAULVALUE", property="defaulvalue", jdbcType=JdbcType.CHAR)
    })
    JStatisconfdetail selectByPrimaryKey(Long id);

    @UpdateProvider(type=JStatisconfdetailSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(JStatisconfdetail record);

    @Update({
        "update J_STATISCONFDETAIL",
        "set STATISCONFID = #{statisconfid,jdbcType=DECIMAL},",
          "DETAILTYPE = #{detailtype,jdbcType=CHAR},",
          "FIELDID = #{fieldid,jdbcType=DECIMAL},",
          "FIELDTYPETRANS = #{fieldtypetrans,jdbcType=VARCHAR},",
          "SORTNO = #{sortno,jdbcType=DECIMAL},",
          "CREATEDATE = #{createdate,jdbcType=TIMESTAMP},",
          "ACTIVEFLAG = #{activeflag,jdbcType=CHAR},",
          "DEFAULVALUE = #{defaulvalue,jdbcType=CHAR}",
        "where ID = #{id,jdbcType=DECIMAL}"
    })
    int updateByPrimaryKey(JStatisconfdetail record);
}