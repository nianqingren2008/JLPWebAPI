package com.callan.service.provider.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.callan.service.provider.config.JLPException;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedCaseInsensitiveMap;

import com.callan.service.provider.config.ConnPathologyDb;
import com.callan.service.provider.config.JLPLog;
import com.callan.service.provider.config.ThreadPoolConfig;
import com.callan.service.provider.dao.IPathologyDao;

@Service
public class PathologyDaoImpl implements IPathologyDao {

	@Override
	public List<Map<String, Object>> queryForSQL(String sql, Object[] params) throws Exception {
		Connection conn = null;
		PreparedStatement pst = null;
		List dataList = new ArrayList<>();
		ConnPathologyDb connPathologyDb = new ConnPathologyDb();
		try {
			conn = connPathologyDb.getConnection();
			pst = conn.prepareStatement(sql);
			int index = 1;
			for (Object para : params) {
				pst.setObject(index, para);
				index++;
			}
			ResultSet rs = pst.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData();
			int count = rsmd.getColumnCount();
			while (rs.next()) {
				Map<String, Object> hm = new LinkedCaseInsensitiveMap<Object>();
				for (int i = 1; i <= count; i++) {
					String key = rsmd.getColumnLabel(i).toLowerCase();
					Object value = rs.getObject(i);
					hm.put(key, value);
				}
				dataList.add(hm);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new JLPException(e.getMessage());
		} finally {
			connPathologyDb.releaseConnection();
		}
		return dataList;
	}

}
