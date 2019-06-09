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

import com.callan.service.provider.pojo.db.JSensitiveWord;

public interface JSensitivewordMapper {
	
	
	/**
	 * 
	 * @return
	 */
	@Select("SELECT * FROM J_SENSITIVEWORD")
    public List<JSensitiveWord> getAll();
	
	
	/**
	 * 
	 * @param id
	 * @return
	 */
    @Select("SELECT * FROM J_SENSITIVEWORD WHERE id = #{id}")
    JSensitiveWord getOne(Long id);
    

    /**
     * 
     * @param id
     */
    @Delete("DELETE FROM J_SENSITIVEWORD WHERE id =#{id}")
    void delete(Long id);
    
    
    
	
    @Delete({
        "delete from J_SENSITIVEWORD",
        "where ID = #{id,jdbcType=DECIMAL}"
    })
    int deleteByPrimaryKey(Long id);

    @Insert({
        "insert into J_SENSITIVEWORD (ID, NAME, ",
        "ACTIVEFLAG, CREATEDATE)",
        "values (#{id,jdbcType=DECIMAL}, #{name,jdbcType=VARCHAR}, ",
        "#{activeflag,jdbcType=CHAR}, #{createdate,jdbcType=TIMESTAMP})"
    })
    int insert(JSensitiveWord record);

    @InsertProvider(type=JSensitivewordSqlProvider.class, method="insertSelective")
    int insertSelective(JSensitiveWord record);

    @Select({
        "select",
        "ID, NAME, ACTIVEFLAG, CREATEDATE",
        "from J_SENSITIVEWORD",
        "where ID = #{id,jdbcType=DECIMAL}"
    })
    @Results({
        @Result(column="ID", property="id", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="NAME", property="name", jdbcType=JdbcType.VARCHAR),
        @Result(column="ACTIVEFLAG", property="activeflag", jdbcType=JdbcType.CHAR),
        @Result(column="CREATEDATE", property="createdate", jdbcType=JdbcType.TIMESTAMP)
    })
    JSensitiveWord selectByPrimaryKey(Long id);

    @UpdateProvider(type=JSensitivewordSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(JSensitiveWord record);

    @Update({
        "update J_SENSITIVEWORD",
        "set NAME = #{name,jdbcType=VARCHAR},",
          "ACTIVEFLAG = #{activeflag,jdbcType=CHAR},",
          "CREATEDATE = #{createdate,jdbcType=TIMESTAMP}",
        "where ID = #{id,jdbcType=DECIMAL}"
    })
    int updateByPrimaryKey(JSensitiveWord record);
}