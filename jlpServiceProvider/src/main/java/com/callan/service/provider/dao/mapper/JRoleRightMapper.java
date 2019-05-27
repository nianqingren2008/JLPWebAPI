package com.callan.service.provider.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.FetchType;

import com.callan.service.provider.pojo.db.JRoleRight;


@Mapper
public interface JRoleRightMapper {
	/**
	 * 
	 * @return
	 */
	@Select("SELECT * FROM J_ROLERIGHT")
    public List<JRoleRight> getAll();
	
	
	/**
	 * 
	 * @param id
	 * @return
	 */
    @Select("SELECT * FROM J_ROLERIGHT WHERE id = #{id}")
    JRoleRight getOne(Long id);
    

    /**
     * 
     * @param id
     */
    @Delete("DELETE FROM J_ROLERIGHT WHERE id =#{id}")
    void delete(Long id);
    
    @Select("SELECT * FROM J_ROLERIGHT where roleId=#{roleId}")
    @Results({
		@Result(property="rightid",column="rightid"),
		//users映射List<User> users，many=@Many是调用关联查询方法，"id"是关联查询条件，FetchType.LAZY是延迟加载
		@Result(property="jRight",column="id", 
			one=@One(select="com.callan.service.provider.dao.mapper.JRightMapper.getOne",fetchType=FetchType.LAZY))
    })
    List<JRoleRight> getByRoleId(Long roleId);
}
