package com.callan.service.provider.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.callan.service.provider.config.JLPConts;
import com.callan.service.provider.pojo.db.JSystemconfig;


@Mapper
public interface JSystemConfigMapper {
	/**
	 * 
	 * @return
	 */
	@Select("SELECT * FROM j_systemconfig where activeflag='"+JLPConts.ActiveFlag+"'")
    public List<JSystemconfig> getAll();
	
	
	@Select("SELECT * FROM j_systemconfig WHERE classType = #{classType} and keyName = #{keyName} and activeflag='"+JLPConts.ActiveFlag+"'")
    public JSystemconfig getByIdAndFileCode(String classType,String keyName);
	
	/**
	 * 
	 * @param id
	 * @return
	 */
    @Select("SELECT * FROM j_systemconfig WHERE id = #{id}")
    JSystemconfig getOne(Long id);
    

    /**
     * 
     * @param id
     */
    @Delete("DELETE FROM j_systemconfig WHERE id =#{id}")
    void delete(Long id);
}
