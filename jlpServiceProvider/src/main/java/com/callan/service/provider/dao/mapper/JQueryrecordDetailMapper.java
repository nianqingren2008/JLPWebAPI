package com.callan.service.provider.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.callan.service.provider.config.JLPConts;
import com.callan.service.provider.pojo.db.JQueryrecordDetails;


@Mapper
public interface JQueryrecordDetailMapper {
	/**
	 * 
	 * @return
	 */
	@Select("SELECT * FROM j_queryrecorddetails")
    public List<JQueryrecordDetails> getAll();
	
	@Select("SELECT * FROM j_queryrecorddetails WHERE id =#{id}")
    public JQueryrecordDetails getOne(long id);
	
	@Select("SELECT * FROM j_queryrecorddetails WHERE queryId = #{queryId} and activeflag='"+JLPConts.ActiveFlag+"'")
    public List<JQueryrecordDetails> getByQueryId(long queryId);

    /**
     * 
     * @param id
     */
    @Delete("DELETE FROM j_queryrecorddetails WHERE id =#{id}")
    void delete(Long id);

    @Insert("insert into j_queryrecorddetails (ID, QUERYID, DETAILID, LEFTBRACKETS,"
    		+ " FIELDNAME, RELATIONTYPE, FIELDVALUETYPE, FIELDVALUE, RIGHTBRACKETS, "
    		+ "LOGICALTYPE, UPDATEDATE, CREATEDATE, ACTIVEFLAG) "
    		+ "values (#{id,jdbcType=DECIMAL}, #{ queryid,jdbcType=DECIMAL}, #{ detailid,jdbcType=DECIMAL}, #{ leftbrackets,jdbcType=VARCHAR}, "
    		+ "#{ fieldname,jdbcType=VARCHAR}, #{ relationtype,jdbcType=VARCHAR}, #{fieldvaluetype,jdbcType=DECIMAL}, #{ fieldvalue,jdbcType=VARCHAR}, "
    		+ "#{ rightbrackets,jdbcType=VARCHAR}, #{logicaltype,jdbcType=VARCHAR}, #{ updatedate,jdbcType=TIMESTAMP}"
    		+ ", #{ createdate,jdbcType=TIMESTAMP}, #{ activeflag,jdbcType=VARCHAR})")
	public void save(JQueryrecordDetails queryrecord);
}
