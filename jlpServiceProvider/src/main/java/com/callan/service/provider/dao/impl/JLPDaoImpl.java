package com.callan.service.provider.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.LinkedCaseInsensitiveMap;

import com.callan.service.provider.dao.IJLPDao;


@SuppressWarnings({ "unchecked", "rawtypes" })
@Repository("commonSVCDaoImpl")
public class JLPDaoImpl   implements IJLPDao {


    @Resource(name = "jdbcTemplate")
    protected JdbcTemplate jdbcTemplate;

    @Override
    public List<Map<String, Object>> queryForSQLStreaming(String sql, Object[] params) throws Exception {
        Connection conn = null;
        PreparedStatement pst = null;
        List dataList = new ArrayList<>();
        try {
            conn = jdbcTemplate.getDataSource().getConnection();
            pst = conn.prepareStatement(sql, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);

            pst.setFetchSize(10000);
            int index = 1;
            for (Object para : params) {
                pst.setObject(index, para);
                index++;
            }
            ResultSet rs = pst.executeQuery();// 一般的数据读取
            ResultSetMetaData rsmd = rs.getMetaData();
            int count = rsmd.getColumnCount();
            while (rs.next()) {
                Map<String, Object> hm = new LinkedCaseInsensitiveMap<Object>();
                for (int i = 1; i <= count; i++) {
                    String key = rsmd.getColumnLabel(i);
                    Object value = rs.getObject(i);
                    hm.put(key, value);
                }
                dataList.add(hm);
            }
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