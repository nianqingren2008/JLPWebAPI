package com.callan.service.provider.dao.mapper;

import com.callan.service.provider.pojo.db.JProject;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;

public interface JProjectMapper {
    @Delete({
        "delete from J_PROJECT",
        "where ID = #{id,jdbcType=DECIMAL}"
    })
    int deleteByPrimaryKey(Long id);

    @Insert({
        "insert into J_PROJECT (ID, USERID, ",
        "PROJECTNAME, PROJECTENNAME, ",
        "PROJECTTYPE, PROJECTDESCRIBE, ",
        "STARTDATE, ENDDATE, ",
        "SPONSOR, PROJECTREGISTNO, ",
        "ETHICALRECORDNO, PATIENTCOUNT, ",
        "MEDICALRECORDCOUNT, STATUS, ",
        "SHARETYPE, UPDATEDATE, ",
        "CREATEDATE, ACTIVEFLAG, ",
        "IMAGEURL, DATASTATUS)",
        "values (#{id,jdbcType=DECIMAL}, #{userid,jdbcType=DECIMAL}, ",
        "#{projectname,jdbcType=VARCHAR}, #{projectenname,jdbcType=VARCHAR}, ",
        "#{projecttype,jdbcType=VARCHAR}, #{projectdescribe,jdbcType=VARCHAR}, ",
        "#{startdate,jdbcType=TIMESTAMP}, #{enddate,jdbcType=TIMESTAMP}, ",
        "#{sponsor,jdbcType=VARCHAR}, #{projectregistno,jdbcType=VARCHAR}, ",
        "#{ethicalrecordno,jdbcType=VARCHAR}, #{patientcount,jdbcType=DECIMAL}, ",
        "#{medicalrecordcount,jdbcType=DECIMAL}, #{status,jdbcType=VARCHAR}, ",
        "#{sharetype,jdbcType=CHAR}, #{updatedate,jdbcType=TIMESTAMP}, ",
        "#{createdate,jdbcType=TIMESTAMP}, #{activeflag,jdbcType=CHAR}, ",
        "#{imageurl,jdbcType=VARCHAR}, #{datastatus,jdbcType=CHAR})"
    })
    int insert(JProject record);

    @InsertProvider(type=JProjectSqlProvider.class, method="insertSelective")
    int insertSelective(JProject record);

    @Select({
        "select",
        "ID, USERID, PROJECTNAME, PROJECTENNAME, PROJECTTYPE, PROJECTDESCRIBE, STARTDATE, ",
        "ENDDATE, SPONSOR, PROJECTREGISTNO, ETHICALRECORDNO, PATIENTCOUNT, MEDICALRECORDCOUNT, ",
        "STATUS, SHARETYPE, UPDATEDATE, CREATEDATE, ACTIVEFLAG, IMAGEURL, DATASTATUS",
        "from J_PROJECT",
        "where ID = #{id,jdbcType=DECIMAL}"
    })
    @Results({
        @Result(column="ID", property="id", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="USERID", property="userid", jdbcType=JdbcType.DECIMAL),
        @Result(column="PROJECTNAME", property="projectname", jdbcType=JdbcType.VARCHAR),
        @Result(column="PROJECTENNAME", property="projectenname", jdbcType=JdbcType.VARCHAR),
        @Result(column="PROJECTTYPE", property="projecttype", jdbcType=JdbcType.VARCHAR),
        @Result(column="PROJECTDESCRIBE", property="projectdescribe", jdbcType=JdbcType.VARCHAR),
        @Result(column="STARTDATE", property="startdate", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="ENDDATE", property="enddate", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="SPONSOR", property="sponsor", jdbcType=JdbcType.VARCHAR),
        @Result(column="PROJECTREGISTNO", property="projectregistno", jdbcType=JdbcType.VARCHAR),
        @Result(column="ETHICALRECORDNO", property="ethicalrecordno", jdbcType=JdbcType.VARCHAR),
        @Result(column="PATIENTCOUNT", property="patientcount", jdbcType=JdbcType.DECIMAL),
        @Result(column="MEDICALRECORDCOUNT", property="medicalrecordcount", jdbcType=JdbcType.DECIMAL),
        @Result(column="STATUS", property="status", jdbcType=JdbcType.VARCHAR),
        @Result(column="SHARETYPE", property="sharetype", jdbcType=JdbcType.CHAR),
        @Result(column="UPDATEDATE", property="updatedate", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="CREATEDATE", property="createdate", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="ACTIVEFLAG", property="activeflag", jdbcType=JdbcType.CHAR),
        @Result(column="IMAGEURL", property="imageurl", jdbcType=JdbcType.VARCHAR),
        @Result(column="DATASTATUS", property="datastatus", jdbcType=JdbcType.CHAR)
    })
    JProject selectByPrimaryKey(Long id);

    @UpdateProvider(type=JProjectSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(JProject record);

    @Update({
        "update J_PROJECT",
        "set USERID = #{userid,jdbcType=DECIMAL},",
          "PROJECTNAME = #{projectname,jdbcType=VARCHAR},",
          "PROJECTENNAME = #{projectenname,jdbcType=VARCHAR},",
          "PROJECTTYPE = #{projecttype,jdbcType=VARCHAR},",
          "PROJECTDESCRIBE = #{projectdescribe,jdbcType=VARCHAR},",
          "STARTDATE = #{startdate,jdbcType=TIMESTAMP},",
          "ENDDATE = #{enddate,jdbcType=TIMESTAMP},",
          "SPONSOR = #{sponsor,jdbcType=VARCHAR},",
          "PROJECTREGISTNO = #{projectregistno,jdbcType=VARCHAR},",
          "ETHICALRECORDNO = #{ethicalrecordno,jdbcType=VARCHAR},",
          "PATIENTCOUNT = #{patientcount,jdbcType=DECIMAL},",
          "MEDICALRECORDCOUNT = #{medicalrecordcount,jdbcType=DECIMAL},",
          "STATUS = #{status,jdbcType=VARCHAR},",
          "SHARETYPE = #{sharetype,jdbcType=CHAR},",
          "UPDATEDATE = #{updatedate,jdbcType=TIMESTAMP},",
          "CREATEDATE = #{createdate,jdbcType=TIMESTAMP},",
          "ACTIVEFLAG = #{activeflag,jdbcType=CHAR},",
          "IMAGEURL = #{imageurl,jdbcType=VARCHAR},",
          "DATASTATUS = #{datastatus,jdbcType=CHAR}",
        "where ID = #{id,jdbcType=DECIMAL}"
    })
    int updateByPrimaryKey(JProject record);
}