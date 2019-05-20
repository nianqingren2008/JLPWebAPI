package com.callan.service.provider.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.callan.service.provider.pojo.db.JRight;
import com.callan.service.provider.pojo.db.JSensitiveWord;


@Mapper
public interface JSensitiveWordMapper {
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
}
