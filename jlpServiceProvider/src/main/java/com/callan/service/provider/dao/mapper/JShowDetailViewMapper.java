package com.callan.service.provider.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.callan.service.provider.pojo.db.JShowDetailView;


@Mapper
public interface JShowDetailViewMapper {
	/**
	 * 
	 * @return
	 */
	@Select("SELECT * FROM J_SHOWDETAILVIEW")
    @Results({
        @Result(property = "id",  column = "id" ),
        @Result(property = "activeflag", column = "activeflag")
    })
    public List<JShowDetailView> getAll();
	
	
	/**
	 * 
	 * @param id
	 * @return
	 */
    @Select("SELECT * FROM J_SHOWDETAILVIEW WHERE id = #{id}")
    JShowDetailView getOne(Long id);
    
//    /**
//     * 
//     * @param user
//     */
//    @Insert("INSERT INTO J_SHOWDETAILVIEW(ID,CODE,NAME,ACTIVEFLAG,MAINTABLECODE) VALUES(#{id}, #{CODE}, #{NAME},#{ACTIVEFLAG},#{MAINTABLECODE})")
//    void insert(JShowDetailView user);
//
//    /**
//     * 
//     * @param user
//     */
//    @Update("UPDATE J_SHOWDETAILVIEW SET CODE=#{CODE},NAME=#{NAME},ACTIVEFLAG=#{ACTIVEFLAG},MAINTABLECODE=#{MAINTABLECODE} WHERE id =#{id}")
//    void update(JShowDetailView user);

    /**
     * 
     * @param id
     */
    @Delete("DELETE FROM J_SHOWDETAILVIEW WHERE id =#{id}")
    void delete(Long id);
}
