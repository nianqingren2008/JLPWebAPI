package com.callan.service.provider.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.callan.service.provider.config.JLPConts;
import com.callan.service.provider.pojo.tableclassdict.JTableclassdict;

@Mapper
public interface JTableclassdictMapper {

	@Select("SELECT * FROM J_TABLECLASSDICT WHERE  activeflag='"+JLPConts.ActiveFlag+"'")
	public List<JTableclassdict> getAll() ;
	
  

}
