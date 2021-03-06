package com.callan.service.provider.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.callan.service.provider.config.JLPConts;
import com.callan.service.provider.pojo.db.JAdvancedqrItem;


@Mapper
public interface JAdvancedqrItemMapper {
	/**
	 * 
	 * @return
	 */
	@Select("SELECT * FROM J_ADVANCEDQRITEM")
    public List<JAdvancedqrItem> getAll();
	
	
	@Select("SELECT * FROM J_ADVANCEDQRITEM WHERE qrid = #{qrid} and activeflag='"+JLPConts.ActiveFlag+"'")
    public List<JAdvancedqrItem> getByQrId(long qrId);
	
	/**
	 * 
	 * @param id
	 * @return
	 */
    @Select("SELECT * FROM J_ADVANCEDQRITEM WHERE id = #{id}")
    JAdvancedqrItem getOne(Long id);
    

    /**
     * 
     * @param id
     */
    @Delete("DELETE FROM J_ADVANCEDQRITEM WHERE id =#{id}")
    void delete(Long id);

    @Insert("insert into J_ADVANCEDQRITEM (ID, QRID, ITEMTYPE, ITEMNO, LEFTQUETO, MODELID"
    		+ ", QUERYID, RIGHTQUETO, SETCOMBINATORTYPE"
    		+ ", MODELTYPE, CREATEDATE, ACTIVEFLAG) " + 
    		"values ( #{id,jdbcType=DECIMAL}, #{qrid,jdbcType=DECIMAL}, #{itemtype,jdbcType=VARCHAR}, #{itemno,jdbcType=DECIMAL}"
    		+ ", #{leftqueto,jdbcType=VARCHAR}, #{modelid,jdbcType=VARCHAR}, #{queryid,jdbcType=DECIMAL}"
    		+ ", #{rightqueto,jdbcType=VARCHAR},#{setcombinatortype,jdbcType=VARCHAR}, #{modeltype,jdbcType=VARCHAR}"
    		+ ", #{createdate,jdbcType=TIMESTAMP}, #{activeflag,jdbcType=VARCHAR})")
	public void save(JAdvancedqrItem item);
}
