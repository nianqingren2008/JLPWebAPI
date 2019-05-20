package com.callan.service.provider.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.callan.service.provider.pojo.db.JTableDict;


@Mapper
public interface JTableDictMapper {
	/**
	 * 
	 * @return
	 */
	@Select("SELECT * FROM J_TABLEDICT")
    public List<JTableDict> getAll();
	
	
	/**
	 * 
	 * @param id
	 * @return
	 */
    @Select("SELECT * FROM J_TABLEDICT WHERE id = #{id}")
    JTableDict getOne(Long id);
    
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
    @Delete("DELETE FROM J_TABLEDICT WHERE id =#{id}")
    void delete(Long id);
}
