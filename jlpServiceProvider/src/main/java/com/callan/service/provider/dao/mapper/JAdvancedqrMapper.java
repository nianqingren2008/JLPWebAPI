package com.callan.service.provider.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
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
    @Results({
		@Result(property="id",column="id"),
		//users映射List<User> users，many=@Many是调用关联查询方法，"id"是关联查询条件，FetchType.LAZY是延迟加载
		@Result(property="itemList",column="qrId", 
			many=@Many(select="com.callan.service.provider.dao.mapper.JAdvancedqrItemMapper.getByQrId"))
    })
    JAdvancedqr getOne(Long id);
    

    /**
     * 
     * @param id
     */
    @Delete("DELETE FROM J_ADVANCEDQR WHERE id =#{id}")
    void delete(Long id);

    @Select("SELECT * FROM J_ADVANCEDQR WHERE projectid = #{projectid} and activeflag='"+JLPConts.ActiveFlag+"'")
	public List<JAdvancedqr> getByProjectId(Long projectid);


  @Insert("INSERT INTO J_ADVANCEDQR(ID,userid,projectid,aqname,sortno) "
  		+ " VALUES(#{id}, #{userid}, #{projectid},#{aqname},#{sortno})")
	public void save(JAdvancedqr jAdvancedqr);
}
