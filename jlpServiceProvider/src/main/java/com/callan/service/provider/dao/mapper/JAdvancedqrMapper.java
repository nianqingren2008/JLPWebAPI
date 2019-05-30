package com.callan.service.provider.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.callan.service.provider.config.JLPConts;
import com.callan.service.provider.pojo.db.JAdvancedqr;


@Mapper
public interface JAdvancedqrMapper {
	/**
	 * 
	 * @return
	 */
	@Select("SELECT * FROM J_ADVANCEDQR")
    public List<JAdvancedqr> getAll();
	
	
	@Select("SELECT * FROM J_ADVANCEDQR WHERE USERID = #{userId} and activeflag='"+JLPConts.ActiveFlag+"'")
    public List<JAdvancedqr> getByUserId(long userId);
	
	/**
	 * 
	 * @param id
	 * @return
	 */
    @Select("SELECT * FROM J_ADVANCEDQR WHERE id = #{id}")
    JAdvancedqr getOne(Long id);
    

    /**
     * 
     * @param id
     */
    @Delete("DELETE FROM J_ADVANCEDQR WHERE id =#{id}")
    void delete(Long id);
}
