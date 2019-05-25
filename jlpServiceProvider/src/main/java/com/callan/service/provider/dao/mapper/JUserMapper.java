package com.callan.service.provider.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.callan.service.provider.pojo.db.JShowView;
import com.callan.service.provider.pojo.db.JUser;


@Mapper
public interface JUserMapper {
	/**
	 * 
	 * @return
	 */
	@Select("SELECT * FROM J_USER")
    public List<JUser> getAll();
	
	
	/**
	 * 
	 * @param id
	 * @return
	 */
    @Select("SELECT * FROM J_USER WHERE id = #{id}")
    JUser getOne(Long id);
    

    /**
     * 
     * @param id
     */
    @Delete("DELETE FROM J_USER WHERE id =#{id}")
    void delete(Long id);
    
    /**
     * 
     * @param user
     */
    @Update("UPDATE J_USER SET Loginpwd=#{Loginpwd} WHERE id =#{id}")
    void updatePwd(JUser entity);

    @Select("SELECT * FROM J_USER WHERE logincode = #{logincode} and loginpwd = #{loginpwd}")
	public List<JUser> getUserByCodeAndPwd(String logincode, String loginpwd);
}
