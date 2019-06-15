package com.callan.service.provider.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.callan.service.provider.config.JLPConts;
import com.callan.service.provider.pojo.db.JDownloadfile;
import com.callan.service.provider.pojo.task.JTask;
import com.callan.service.provider.pojo.task.JTaskdownload;

@Mapper
public interface JTaskMapper {

	/**
     * 
     * @param id
     */
    @Delete("DELETE FROM J_TASK WHERE id =#{id}")
    void delete(Long id);

	/**
	 * 
	 * @param taskdownload
	 */
	@Insert("INSERT J_TASKDOWNLOAD (id,taskid,projectid,queryid,dataexportclass,exportfields,"
			+ "projectstatuses,tagdata,tagtranspose,filetypeid,imageexportclass,"
			+ "imageclass,fileid,imagefileid,createdate,activeflag)"
			+ " VALUES(#{id}, #{taskid}, #{projectid},#{queryid},#{dataexportclass},"
			+ "#{exportfields},#{projectstatuses},#{tagdata},#{tagtranspose},#{imageexportclass}),"
			+ "#{imageclass},#{imagefileid},#{createdate},#{activeflag})")
	public void addTaskdownloads(JTaskdownload taskdownload);

	 /**
	  * 
	  * @param task
	  */
		@Insert("INSERT J_TASK (id,userid,name,tasktype,status,progress,startdate,enddate,createdate,activeflag)"
	 		+ " VALUES(#{id}, #{userid}, #{name},#{tasktype},#{status},#{progress},#{startdate},#{enddate},#{createdate},#{activeflag})")
		public void addTasktasktask(JTask task);

		/**
		 * 
		 * @param userId
		 * @return 
		 */
		@Select("SELECT * FROM J_TASK WHERE USERID = #{userId} and activeflag='"+JLPConts.ActiveFlag+"'")
	 public List<JTask> getByUserId(Long userId);

		/**
		 * 
		 * @return
		 */
		@Select("SELECT * FROM J_TASKDOWNLOAD WHERE  activeflag='"+JLPConts.ActiveFlag+"'")
		public List<JTaskdownload> getAllDowndLoad();

		/**
		 * 
		 * @return
		 */
		@Select("SELECT * FROM J_DOWNLOADFILE WHERE  activeflag='"+JLPConts.ActiveFlag+"'")
		public List<JDownloadfile> getDownloadfile();

		/**
		 * 
		 * @param id
		 * @return
		 */
		@Select("SELECT * FROM J_DOWNLOADFILE WHERE  ID = #{id}   AND ACTIVEFLAG='"+JLPConts.ActiveFlag+"'")
		public JDownloadfile getDownloadfileById(long id);

		/**
		 * 
		 * @param id
		 * @param userId
		 * @return
		 */
		@Select("SELECT * FROM J_TASK WHERE  ID = #{id}  AND USERID = #{userId}  AND ACTIVEFLAG='"+JLPConts.ActiveFlag+"'")
		public JTask getByIdAndUserId(Long id, Long userId);

		/*
		 * 
		 */
		@Select("SELECT * FROM J_TASK WHERE   ACTIVEFLAG='"+JLPConts.ActiveFlag+"'")
		public List<JTask>  getAll();

 

	
}
