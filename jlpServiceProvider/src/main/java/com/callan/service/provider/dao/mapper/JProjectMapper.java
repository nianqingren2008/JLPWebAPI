package com.callan.service.provider.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.callan.service.provider.config.JLPConts;
import com.callan.service.provider.pojo.db.JProject;


@Mapper
public interface JProjectMapper {
	/**
	 * 
	 * @return
	 */
	@Select("SELECT * FROM J_PROJECT")
    public List<JProject> getAll();
	
	
	@Select("SELECT * FROM J_PROJECT WHERE USERID = #{userId} and activeflag='"+JLPConts.ActiveFlag+"'")
    public List<JProject> getByUserId(long userId);
	
	/**
	 * 
	 * @param id
	 * @return
	 */
    @Select("SELECT * FROM J_PROJECT WHERE id = #{id}")
//    @Results({
//		@Result(property="id",column="id"),
//		//users映射List<User> users，many=@Many是调用关联查询方法，"id"是关联查询条件，FetchType.LAZY是延迟加载
//		@Result(property="itemList",column="qrId", 
//			many=@Many(select="com.callan.service.provider.dao.mapper.JAdvancedqrItemMapper.getByQrId"))
//    })
    JProject getOne(Long id);
    

    /**
     * 
     * @param id
     */
    @Delete("DELETE FROM J_PROJECT WHERE id =#{id}")
    void delete(Long id);
}
