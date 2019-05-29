package com.callan.service.provider.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.LinkedCaseInsensitiveMap;

import com.callan.service.provider.dao.IJLPDao;

@SuppressWarnings({ "unchecked", "rawtypes" })
@Repository
public class JLPDaoImpl   implements IJLPDao {
	Log logger = LogFactory.getLog(JLPDaoImpl.class);
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Map<String, Object>> queryForSQLStreaming(String sql,int pageNum, int pageSize) throws Exception {
        Connection conn = null;
        PreparedStatement pst = null;
        List dataList = new ArrayList<>();
        try {
        	long start = System.currentTimeMillis();
            conn = jdbcTemplate.getDataSource().getConnection();
            pst = conn.prepareStatement(sql, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);

            pst.setFetchSize(50);
//            int index = 1;
//            for (Object para : params) {
//                pst.setObject(index, para);
//                index++;
//            }
            ResultSet rs = pst.executeQuery();// 一般的数据读取
            long end = System.currentTimeMillis();
            logger.info("dao ---11111---  " + (end-start));
            ResultSetMetaData rsmd = rs.getMetaData();
            int count = rsmd.getColumnCount();
            int index = 0;
            start = System.currentTimeMillis();
            while (rs.next()) {
            	index++;
            	if(index >= pageNum * pageSize) {
            		 Map<String, Object> hm = new LinkedCaseInsensitiveMap<Object>();
                     for (int i = 1; i <= count; i++) {
                         String key = rsmd.getColumnLabel(i).toLowerCase();
                         Object value = rs.getObject(i);
                         hm.put(key, value);
                     }
                     dataList.add(hm);
                     if(dataList.size() == pageSize) {
                     	break;
                     }
            	}
            }
            end = System.currentTimeMillis();
            logger.info("dao -----22222-  " + (end-start));
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return dataList;
    }

	@Override
	public List<Map<String, Object>> queryForSQL(String sql, Object[] params) throws Exception {
		return jdbcTemplate.queryForList(sql, params);
	}

}