package com.callan.service.provider.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.callan.service.provider.pojo.db.JShowview;

@Mapper
public interface JShowviewMapper {
	/**
	 * 
	 * @return
	 */
	@Select("SELECT * FROM J_SHOWVIEW")
    @Results({
        @Result(property = "id",  column = "id" ),
        @Result(property = "activeflag", column = "activeflag")
    })
    public List<JShowview> getAll();
	
	
	/**
	 * 
	 * @param id
	 * @return
	 */
    @Select("SELECT * FROM J_SHOWVIEW WHERE id = #{id}")
    JShowview getOne(Long id);
    
    /**
     * 
     * @param user
     */
    @Insert("INSERT INTO J_SHOWVIEW(ID,CODE,NAME,ACTIVEFLAG,MAINTABLECODE) VALUES(#{id}, #{CODE}, #{NAME},#{ACTIVEFLAG},#{MAINTABLECODE})")
    void insert(JShowview user);

    /**
     * 
     * @param user
     */
    @Update("UPDATE J_SHOWVIEW SET CODE=#{CODE},NAME=#{NAME},ACTIVEFLAG=#{ACTIVEFLAG},MAINTABLECODE=#{MAINTABLECODE} WHERE id =#{id}")
    void update(JShowview user);

    /**
     * 
     * @param id
     */
    @Delete("DELETE FROM J_SHOWVIEW WHERE id =#{id}")
    void delete(Long id);
}
