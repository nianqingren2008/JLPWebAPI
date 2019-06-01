package com.callan.service.provider.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.callan.service.provider.pojo.db.JFiletype;


@Mapper
public interface JFiletypeMapper {
	/**
	 * 
	 * @return
	 */
	@Select("SELECT * FROM j_filetype")
    public List<JFiletype> getAll();
	
	
	
	/**
	 * 
	 * @param id
	 * @return
	 */
    @Select("SELECT * FROM j_filetype WHERE id = #{id}")
    JFiletype getOne(Long id);
    

    /**
     * 
     * @param id
     */
    @Delete("DELETE FROM j_filetype WHERE id =#{id}")
    void delete(Long id);
}
