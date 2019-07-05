package com.callan.service.provider.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.callan.service.provider.config.JLPConts;
import com.callan.service.provider.pojo.db.JRight;


@Mapper
public interface JRightMapper {
	/**
	 * 
	 * @return
	 */
	@Select("SELECT * FROM J_RIGHT where ACTIVEFLAG='"+JLPConts.ActiveFlag+"'")
    public List<JRight> getAll();
	
	
	/**
	 * 
	 * @param id
	 * @return
	 */
    @Select("SELECT * FROM J_RIGHT WHERE id = #{id}")
    JRight getOne(Long id);
    

    /**
     * 
     * @param id
     */
    @Delete("DELETE FROM J_RIGHT WHERE id =#{id}")
    void delete(Long id);
}
