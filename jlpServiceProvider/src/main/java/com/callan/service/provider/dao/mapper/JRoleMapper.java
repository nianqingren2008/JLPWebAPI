package com.callan.service.provider.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;

import com.callan.service.provider.config.JLPConts;
import com.callan.service.provider.pojo.db.JRole;

public interface JRoleMapper {
	
	/**
	 * 
	 * @return
	 */
	@Select("SELECT * FROM J_ROLE WHERE  activeflag='"+JLPConts.ActiveFlag+"'")
    public List<JRole> getAll();
	
	/**
	 * 
	 * @param id
	 * @return
	 */
    @Select("SELECT * FROM J_ROLE WHERE id = #{id}")
    @Results({
		@Result(property="id",column="id"),
		//users映射List<User> users，many=@Many是调用关联查询方法，"id"是关联查询条件，FetchType.LAZY是延迟加载
		@Result(property="list",column="roleId", 
			many=@Many(select="com.callan.service.provider.dao.mapper.JRoleRightMapper.getByRoleId"))
    })
    JRole getOne(Long id);
    

    /**
     * 
     * @param id
     */
    @Delete("DELETE FROM J_ROLE WHERE id =#{id}")
    void delete(Long id);
	
    
    
    
    @Delete({
        "delete from J_ROLE",
        "where ID = #{id,jdbcType=DECIMAL}"
    })
    int deleteByPrimaryKey(Long id);

    @Insert({
        "insert into J_ROLE (ID, NAME, ",
        "ACTIVEFLAG, CREATEDATE)",
        "values (#{id,jdbcType=DECIMAL}, #{name,jdbcType=VARCHAR}, ",
        "#{activeflag,jdbcType=CHAR}, #{createdate,jdbcType=TIMESTAMP})"
    })
    int insert(JRole record);

    @InsertProvider(type=JRoleSqlProvider.class, method="insertSelective")
    int insertSelective(JRole record);

    @Select({
        "select",
        "ID, NAME, ACTIVEFLAG, CREATEDATE",
        "from J_ROLE",
        "where ID = #{id,jdbcType=DECIMAL}"
    })
    @Results({
        @Result(column="ID", property="id", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="NAME", property="name", jdbcType=JdbcType.VARCHAR),
        @Result(column="ACTIVEFLAG", property="activeflag", jdbcType=JdbcType.CHAR),
        @Result(column="CREATEDATE", property="createdate", jdbcType=JdbcType.TIMESTAMP)
    })
    JRole selectByPrimaryKey(Long id);

    @UpdateProvider(type=JRoleSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(JRole record);

    @Update({
        "update J_ROLE",
        "set NAME = #{name,jdbcType=VARCHAR},",
          "ACTIVEFLAG = #{activeflag,jdbcType=CHAR},",
          "CREATEDATE = #{createdate,jdbcType=TIMESTAMP}",
        "where ID = #{id,jdbcType=DECIMAL}"
    })
    int updateByPrimaryKey(JRole record);
}