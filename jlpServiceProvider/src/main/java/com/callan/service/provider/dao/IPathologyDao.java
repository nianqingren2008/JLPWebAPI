package com.callan.service.provider.dao;

import java.util.List;
import java.util.Map;

public interface IPathologyDao {

    public List<Map<String, Object>> queryForSQL(String sql,Object[] params) throws Exception;

}
