package com.callan.service.provider.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;

import com.callan.service.provider.config.JLPConts;
import com.callan.service.provider.pojo.base.CacheResponse;
import com.callan.service.provider.pojo.task.JTaskdownload;

public interface JTaskdownloadMapper {
    @Delete({
        "delete from J_TASKDOWNLOAD",
        "where ID = #{id,jdbcType=DECIMAL}"
    })
    int deleteByPrimaryKey(Long id);

    @Insert({
        "insert into J_TASKDOWNLOAD (ID, TASKID, ",
        "PROJECTID, QUERYID, ",
        "DATAEXPORTCLASS, EXPORTFIELDS, ",
        "PROJECTSTATUSES, TAGDATA, ",
        "TAGTRANSPOSE, FILETYPEID, ",
        "IMAGEEXPORTCLASS, IMAGECLASS, ",
        "FILEID, IMAGEFILEID, ",
        "CREATEDATE, ACTIVEFLAG)",
        "values (#{id,jdbcType=DECIMAL}, #{taskid,jdbcType=DECIMAL}, ",
        "#{projectid,jdbcType=DECIMAL}, #{queryid,jdbcType=DECIMAL}, ",
        "#{dataexportclass,jdbcType=DECIMAL}, #{exportfields,jdbcType=VARCHAR}, ",
        "#{projectstatuses,jdbcType=VARCHAR}, #{tagdata,jdbcType=VARCHAR}, ",
        "#{tagtranspose,jdbcType=VARCHAR}, #{filetypeid,jdbcType=DECIMAL}, ",
        "#{imageexportclass,jdbcType=DECIMAL}, #{imageclass,jdbcType=VARCHAR}, ",
        "#{fileid,jdbcType=DECIMAL}, #{imagefileid,jdbcType=DECIMAL}, ",
        "#{createdate,jdbcType=TIMESTAMP}, #{activeflag,jdbcType=CHAR})"
    })
    int insert(JTaskdownload record);

    @InsertProvider(type=JTaskdownloadSqlProvider.class, method="insertSelective")
    int insertSelective(JTaskdownload record);

    @Select({
        "select",
        "ID, TASKID, PROJECTID, QUERYID, DATAEXPORTCLASS, EXPORTFIELDS, PROJECTSTATUSES, ",
        "TAGDATA, TAGTRANSPOSE, FILETYPEID, IMAGEEXPORTCLASS, IMAGECLASS, FILEID, IMAGEFILEID, ",
        "CREATEDATE, ACTIVEFLAG",
        "from J_TASKDOWNLOAD",
        "where ID = #{id,jdbcType=DECIMAL}"
    })
    @Results({
        @Result(column="ID", property="id", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="TASKID", property="taskid", jdbcType=JdbcType.DECIMAL),
        @Result(column="PROJECTID", property="projectid", jdbcType=JdbcType.DECIMAL),
        @Result(column="QUERYID", property="queryid", jdbcType=JdbcType.DECIMAL),
        @Result(column="DATAEXPORTCLASS", property="dataexportclass", jdbcType=JdbcType.DECIMAL),
        @Result(column="EXPORTFIELDS", property="exportfields", jdbcType=JdbcType.VARCHAR),
        @Result(column="PROJECTSTATUSES", property="projectstatuses", jdbcType=JdbcType.VARCHAR),
        @Result(column="TAGDATA", property="tagdata", jdbcType=JdbcType.VARCHAR),
        @Result(column="TAGTRANSPOSE", property="tagtranspose", jdbcType=JdbcType.VARCHAR),
        @Result(column="FILETYPEID", property="filetypeid", jdbcType=JdbcType.DECIMAL),
        @Result(column="IMAGEEXPORTCLASS", property="imageexportclass", jdbcType=JdbcType.DECIMAL),
        @Result(column="IMAGECLASS", property="imageclass", jdbcType=JdbcType.VARCHAR),
        @Result(column="FILEID", property="fileid", jdbcType=JdbcType.DECIMAL),
        @Result(column="IMAGEFILEID", property="imagefileid", jdbcType=JdbcType.DECIMAL),
        @Result(column="CREATEDATE", property="createdate", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="ACTIVEFLAG", property="activeflag", jdbcType=JdbcType.CHAR)
    })
    JTaskdownload selectByPrimaryKey(Long id);

    @UpdateProvider(type=JTaskdownloadSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(JTaskdownload record);

    @Update({
        "update J_TASKDOWNLOAD",
        "set TASKID = #{taskid,jdbcType=DECIMAL},",
          "PROJECTID = #{projectid,jdbcType=DECIMAL},",
          "QUERYID = #{queryid,jdbcType=DECIMAL},",
          "DATAEXPORTCLASS = #{dataexportclass,jdbcType=DECIMAL},",
          "EXPORTFIELDS = #{exportfields,jdbcType=VARCHAR},",
          "PROJECTSTATUSES = #{projectstatuses,jdbcType=VARCHAR},",
          "TAGDATA = #{tagdata,jdbcType=VARCHAR},",
          "TAGTRANSPOSE = #{tagtranspose,jdbcType=VARCHAR},",
          "FILETYPEID = #{filetypeid,jdbcType=DECIMAL},",
          "IMAGEEXPORTCLASS = #{imageexportclass,jdbcType=DECIMAL},",
          "IMAGECLASS = #{imageclass,jdbcType=VARCHAR},",
          "FILEID = #{fileid,jdbcType=DECIMAL},",
          "IMAGEFILEID = #{imagefileid,jdbcType=DECIMAL},",
          "CREATEDATE = #{createdate,jdbcType=TIMESTAMP},",
          "ACTIVEFLAG = #{activeflag,jdbcType=CHAR}",
        "where ID = #{id,jdbcType=DECIMAL}"
    })
    int updateByPrimaryKey(JTaskdownload record);

    @Select({
        "select",
        "ID, TASKID, PROJECTID, QUERYID, DATAEXPORTCLASS, EXPORTFIELDS, PROJECTSTATUSES, ",
        "TAGDATA, TAGTRANSPOSE, FILETYPEID, IMAGEEXPORTCLASS, IMAGECLASS, FILEID, IMAGEFILEID, ",
        "CREATEDATE, ACTIVEFLAG",
        "from J_TASKDOWNLOAD",
        "where ACTIVEFLAG = '"+JLPConts.ActiveFlag+"' "
    })
	public List<JTaskdownload> getAll();
}