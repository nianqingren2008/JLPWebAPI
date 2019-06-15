package com.callan.service.provider.dao.mapper;

import org.apache.ibatis.jdbc.SQL;

import com.callan.service.provider.pojo.task.JTaskdownload;

public class JTaskdownloadSqlProvider {

    public String insertSelective(JTaskdownload record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("J_TASKDOWNLOAD");
        
        if (record.getId() != null) {
            sql.VALUES("ID", "#{id,jdbcType=DECIMAL}");
        }
        
        if (record.getTaskid() != null) {
            sql.VALUES("TASKID", "#{taskid,jdbcType=DECIMAL}");
        }
        
        if (record.getProjectid() != null) {
            sql.VALUES("PROJECTID", "#{projectid,jdbcType=DECIMAL}");
        }
        
        if (record.getQueryid() != null) {
            sql.VALUES("QUERYID", "#{queryid,jdbcType=DECIMAL}");
        }
        
        if (record.getDataexportclass() != null) {
            sql.VALUES("DATAEXPORTCLASS", "#{dataexportclass,jdbcType=DECIMAL}");
        }
        
        if (record.getExportfields() != null) {
            sql.VALUES("EXPORTFIELDS", "#{exportfields,jdbcType=VARCHAR}");
        }
        
        if (record.getProjectstatuses() != null) {
            sql.VALUES("PROJECTSTATUSES", "#{projectstatuses,jdbcType=VARCHAR}");
        }
        
        if (record.getTagdata() != null) {
            sql.VALUES("TAGDATA", "#{tagdata,jdbcType=VARCHAR}");
        }
        
        if (record.getTagtranspose() != null) {
            sql.VALUES("TAGTRANSPOSE", "#{tagtranspose,jdbcType=VARCHAR}");
        }
        
        if (record.getFiletypeid() != null) {
            sql.VALUES("FILETYPEID", "#{filetypeid,jdbcType=DECIMAL}");
        }
        
        if (record.getImageexportclass() != null) {
            sql.VALUES("IMAGEEXPORTCLASS", "#{imageexportclass,jdbcType=DECIMAL}");
        }
        
        if (record.getImageclass() != null) {
            sql.VALUES("IMAGECLASS", "#{imageclass,jdbcType=VARCHAR}");
        }
        
        if (record.getFileid() != null) {
            sql.VALUES("FILEID", "#{fileid,jdbcType=DECIMAL}");
        }
        
        if (record.getImagefileid() != null) {
            sql.VALUES("IMAGEFILEID", "#{imagefileid,jdbcType=DECIMAL}");
        }
        
        if (record.getCreatedate() != null) {
            sql.VALUES("CREATEDATE", "#{createdate,jdbcType=TIMESTAMP}");
        }
        
        if (record.getActiveflag() != null) {
            sql.VALUES("ACTIVEFLAG", "#{activeflag,jdbcType=CHAR}");
        }
        
        return sql.toString();
    }

    public String updateByPrimaryKeySelective(JTaskdownload record) {
        SQL sql = new SQL();
        sql.UPDATE("J_TASKDOWNLOAD");
        
        if (record.getTaskid() != null) {
            sql.SET("TASKID = #{taskid,jdbcType=DECIMAL}");
        }
        
        if (record.getProjectid() != null) {
            sql.SET("PROJECTID = #{projectid,jdbcType=DECIMAL}");
        }
        
        if (record.getQueryid() != null) {
            sql.SET("QUERYID = #{queryid,jdbcType=DECIMAL}");
        }
        
        if (record.getDataexportclass() != null) {
            sql.SET("DATAEXPORTCLASS = #{dataexportclass,jdbcType=DECIMAL}");
        }
        
        if (record.getExportfields() != null) {
            sql.SET("EXPORTFIELDS = #{exportfields,jdbcType=VARCHAR}");
        }
        
        if (record.getProjectstatuses() != null) {
            sql.SET("PROJECTSTATUSES = #{projectstatuses,jdbcType=VARCHAR}");
        }
        
        if (record.getTagdata() != null) {
            sql.SET("TAGDATA = #{tagdata,jdbcType=VARCHAR}");
        }
        
        if (record.getTagtranspose() != null) {
            sql.SET("TAGTRANSPOSE = #{tagtranspose,jdbcType=VARCHAR}");
        }
        
        if (record.getFiletypeid() != null) {
            sql.SET("FILETYPEID = #{filetypeid,jdbcType=DECIMAL}");
        }
        
        if (record.getImageexportclass() != null) {
            sql.SET("IMAGEEXPORTCLASS = #{imageexportclass,jdbcType=DECIMAL}");
        }
        
        if (record.getImageclass() != null) {
            sql.SET("IMAGECLASS = #{imageclass,jdbcType=VARCHAR}");
        }
        
        if (record.getFileid() != null) {
            sql.SET("FILEID = #{fileid,jdbcType=DECIMAL}");
        }
        
        if (record.getImagefileid() != null) {
            sql.SET("IMAGEFILEID = #{imagefileid,jdbcType=DECIMAL}");
        }
        
        if (record.getCreatedate() != null) {
            sql.SET("CREATEDATE = #{createdate,jdbcType=TIMESTAMP}");
        }
        
        if (record.getActiveflag() != null) {
            sql.SET("ACTIVEFLAG = #{activeflag,jdbcType=CHAR}");
        }
        
        sql.WHERE("ID = #{id,jdbcType=DECIMAL}");
        
        return sql.toString();
    }
}