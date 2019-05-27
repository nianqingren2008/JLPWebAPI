package com.callan.service.provider.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.FetchType;

import com.callan.service.provider.pojo.db.JRole;


@Mapper
public interface JRoleMapper {
	/**
	 * 
	 * @return
	 */
	@Select("SELECT * FROM J_ROLE")
    public List<JRole> getAll();
	
	
	/**
	 * 
	 * @param id
	 * @return
	 */
    @Select("SELECT * FROM J_ROLE WHERE id = #{id}")
    @Results({
		@Result(property="roleid",column="roleid"),
		//users映射List<User> users，many=@Many是调用关联查询方法，"id"是关联查询条件，FetchType.LAZY是延迟加载
		@Result(property="role",column="roleid", 
			many=@Many(select="com.callan.service.provider.dao.mapper.JRoleRightMapper.getByRoleId",fetchType=FetchType.LAZY))
    })
    JRole getOne(Long id);
    

    /**
     * 
     * @param id
     */
    @Delete("DELETE FROM J_ROLE WHERE id =#{id}")
    void delete(Long id);
}
