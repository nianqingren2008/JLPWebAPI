package com.callan.service.provider.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.callan.service.provider.config.JLPConts;
import com.callan.service.provider.pojo.db.JDownloadfile;
import com.callan.service.provider.pojo.db.JProject;


@Mapper
public interface JDownloadFileMapper {
	/**
	 * 
	 * @return
	 */
	@Select("SELECT * FROM j_downloadfile")
    public List<JDownloadfile> getAll();
	
	
	@Select("SELECT * FROM j_downloadfile WHERE id = #{id} and fileCode = #{fileCode} and activeflag='"+JLPConts.ActiveFlag+"'")
    public JDownloadfile getByIdAndFileCode(long id,String fileCode);
	
	/**
	 * 
	 * @param id
	 * @return
	 */
    @Select("SELECT * FROM j_downloadfile WHERE id = #{id}")
    JDownloadfile getOne(Long id);
    

    /**
     * 
     * @param id
     */
    @Delete("DELETE FROM j_downloadfile WHERE id =#{id}")
    void delete(Long id);
}
