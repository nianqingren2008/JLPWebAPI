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
import com.callan.service.provider.pojo.db.JQueryrecord;


@Mapper
public interface JQueryrecordMapper {
	/**
	 * 
	 * @return
	 */
	@Select("SELECT * FROM J_QUERYRECORD")
    public List<JQueryrecord> getAll();
	
	
	/**
	 * 
	 * @param id
	 * @return
	 */
    @Select("SELECT * FROM J_QUERYRECORD WHERE id = #{id} and activeflag='"+JLPConts.ActiveFlag+"'")
    @Results({
		@Result(property="id",column="id"),
		//users映射List<User> users，many=@Many是调用关联查询方法，"id"是关联查询条件，FetchType.LAZY是延迟加载
		@Result(property="detailList",column="queryId", 
			many=@Many(select="com.callan.service.provider.dao.mapper.JQueryrecordDetailMapper.getByQueryId"))
    })
    JQueryrecord getOne(Long id);
    

    /**
     * 
     * @param id
     */
    @Delete("DELETE FROM J_QUERYRECORD WHERE id =#{id}")
    void delete(Long id);


    @Insert("insert into J_QUERYRECORD (ID, USERID, QUERYNAME, UPDATEDATE, CREATEDATE, ACTIVEFLAG, SORTNO, COUNT)"
    		+ " values (#{ID}, #{USERID}, #{QUERYNAME}, #{UPDATEDATE}, #{CREATEDATE}, #{ACTIVEFLAG}, #{SORTNO}, #{COUNT})")
	public void save(JQueryrecord queryrecord);
}
