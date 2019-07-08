package com.callan.service.provider.dao.mapper;

import com.callan.service.provider.config.JLPConts;
import com.callan.service.provider.pojo.db.JFiletype;
import com.callan.service.provider.pojo.db.JStatisconf;

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

public interface JStatisconfMapper {
    @Delete({
        "delete from J_STATISCONF",
        "where ID = #{id,jdbcType=DECIMAL}"
    })
    int deleteByPrimaryKey(Long id);

    @Insert({
        "insert into J_STATISCONF (ID, CODE, ",
        "TITLE, CREATEDATE, ",
        "ACTIVEFLAG, PAGECODE, ",
        "PAGETITLE)",
        "values (#{id,jdbcType=DECIMAL}, #{code,jdbcType=VARCHAR}, ",
        "#{title,jdbcType=VARCHAR}, #{createdate,jdbcType=TIMESTAMP}, ",
        "#{activeflag,jdbcType=CHAR}, #{pagecode,jdbcType=VARCHAR}, ",
        "#{pagetitle,jdbcType=VARCHAR})"
    })
    int insert(JStatisconf record);

    @InsertProvider(type=JStatisconfSqlProvider.class, method="insertSelective")
    int insertSelective(JStatisconf record);

    @Select({
        "select",
        "ID, CODE, TITLE, CREATEDATE, ACTIVEFLAG, PAGECODE, PAGETITLE",
        "from J_STATISCONF",
        "where ID = #{id,jdbcType=DECIMAL}"
    })
    @Results({
        @Result(column="ID", property="id", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="CODE", property="code", jdbcType=JdbcType.VARCHAR),
        @Result(column="TITLE", property="title", jdbcType=JdbcType.VARCHAR),
        @Result(column="CREATEDATE", property="createdate", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="ACTIVEFLAG", property="activeflag", jdbcType=JdbcType.CHAR),
        @Result(column="PAGECODE", property="pagecode", jdbcType=JdbcType.VARCHAR),
        @Result(column="PAGETITLE", property="pagetitle", jdbcType=JdbcType.VARCHAR)
    })
    JStatisconf selectByPrimaryKey(Long id);

    @UpdateProvider(type=JStatisconfSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(JStatisconf record);

    @Update({
        "update J_STATISCONF",
        "set CODE = #{code,jdbcType=VARCHAR},",
          "TITLE = #{title,jdbcType=VARCHAR},",
          "CREATEDATE = #{createdate,jdbcType=TIMESTAMP},",
          "ACTIVEFLAG = #{activeflag,jdbcType=CHAR},",
          "PAGECODE = #{pagecode,jdbcType=VARCHAR},",
          "PAGETITLE = #{pagetitle,jdbcType=VARCHAR}",
        "where ID = #{id,jdbcType=DECIMAL}"
    })
    int updateByPrimaryKey(JStatisconf record);
    
    
    /**
	 * 
	 * @return
	 */
	@Select("SELECT * FROM J_STATISCONF WHERE ACTIVEFLAG='"+JLPConts.ActiveFlag+"' ")
    public List<JStatisconf> getAll();

	@Select({
        "select",
        "ID, CODE, TITLE, CREATEDATE, ACTIVEFLAG, PAGECODE, PAGETITLE",
        "from J_STATISCONF",
        "where PAGECODE = #{pageCode,jdbcType=VARCHAR} and ACTIVEFLAG='"+JLPConts.ActiveFlag+"'"
    })
    @Results({
        @Result(column="ID", property="id", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="CODE", property="code", jdbcType=JdbcType.VARCHAR),
        @Result(column="TITLE", property="title", jdbcType=JdbcType.VARCHAR),
        @Result(column="CREATEDATE", property="createdate", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="ACTIVEFLAG", property="activeflag", jdbcType=JdbcType.CHAR),
        @Result(column="PAGECODE", property="pagecode", jdbcType=JdbcType.VARCHAR),
        @Result(column="PAGETITLE", property="pagetitle", jdbcType=JdbcType.VARCHAR)
    })
	List<JStatisconf> getByPageCode(String pageCode);
}