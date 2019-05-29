package com.callan.service.provider.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.mapping.FetchType;

import com.callan.service.provider.pojo.db.JShowView;
import com.callan.service.provider.pojo.db.JUser;


@Mapper
public interface JUserMapper {
	/**
	 * 
	 * @return
	 */
	@Select("SELECT * FROM J_USERS")
    public List<JUser> getAll();
	
	
	/**
	 * 
	 * @param id
	 * @return
	 */
    @Select("SELECT * FROM J_USERS WHERE id = #{id}")
//    @Results({
//		@Result(property="id",column="id"),
//		//users映射List<User> users，many=@Many是调用关联查询方法，"id"是关联查询条件，FetchType.LAZY是延迟加载
//		@Result(property="jRole",column="userrole", 
//			one=@One(select="com.callan.service.provider.dao.mapper.JRoleMapper.getOne"))
//    })
    JUser getOne(Long id);
    

    /**
     * 
     * @param id
     */
    @Delete("DELETE FROM J_USERS WHERE id =#{id}")
    void delete(Long id);
    
    /**
     * 
     * @param user
     */
    @Update("UPDATE J_USERS SET Loginpwd=#{Loginpwd} WHERE id =#{id}")
    void updatePwd(JUser entity);

    @Select("SELECT * FROM J_USERS WHERE logincode = #{logincode} and loginpwd = #{loginpwd}")
	public List<JUser> getUserByCodeAndPwd(String logincode, String loginpwd);


    @Select("SELECT userrole FROM J_USERS WHERE token = #{token}")
	public Long getUserRoleByToken(String token);
    
    @Select("SELECT id FROM J_USERS WHERE token = #{token}")
	public Long getIdByToken(String token);
    
}


