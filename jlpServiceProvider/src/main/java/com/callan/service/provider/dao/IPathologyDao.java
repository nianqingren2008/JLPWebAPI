package com.callan.service.provider.dao;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import com.callan.service.provider.config.JLPException;

public interface IPathologyDao {

    public List<Map<String, Object>> queryForSQL(Connection conn,String sql,Object[] params) throws JLPException;

}
