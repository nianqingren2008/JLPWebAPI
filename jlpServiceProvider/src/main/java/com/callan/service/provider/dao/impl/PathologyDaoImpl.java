package com.callan.service.provider.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.util.LinkedCaseInsensitiveMap;

import com.callan.service.provider.config.ConnPathologyDb;
import com.callan.service.provider.config.JLPException;
import com.callan.service.provider.dao.IPathologyDao;
import com.microsoft.sqlserver.jdbc.SQLServerException;

@Service
public class PathologyDaoImpl implements IPathologyDao {

	@Override
	public List<Map<String, Object>> queryForSQL(Connection conn,String sql, Object[] params) {
		PreparedStatement pst = null;
		List dataList = new ArrayList<>();
		
		try {
			if(conn == null) {
				throw new JLPException("获取数据库连接失败");
			}
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
			
		}
		return dataList;
	}

}
