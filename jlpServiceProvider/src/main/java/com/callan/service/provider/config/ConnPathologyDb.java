package com.callan.service.provider.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Value;

public class ConnPathologyDb {

	@Value("${pathdb_driver}")
	public String pathdb_driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
	@Value("${pathdb_url}")
	public String pathdb_url = "jdbc:sqlserver://pathdb.tjh.com:1433;DatabaseName=pathnet";
	@Value("${pathdb_user}")
	public String pathdb_user = "pacs_path_view";
	@Value("${pathdb_pwd}")
	public String pathdb_pwd = "pacs_path_view";

	static ThreadLocal<Connection> threadPool = new ThreadLocal<Connection>();

	public Connection getConnection() {
		Connection dbConn = null;
		if (threadPool.get() != null) {
			return threadPool.get();
		} else {
			try {
				Class.forName(pathdb_driver);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}

			try {
				dbConn = DriverManager.getConnection(pathdb_url, pathdb_user, pathdb_pwd);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			if (dbConn != null) {
				threadPool.set(dbConn);
			}
			return dbConn;
		}
	}

	public void releaseConnection() {
		try {
			threadPool.get().close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		threadPool.remove();
	}

	// 测试用例
	public static void main(String[] args) throws Exception {
		String sql = "select  top 10  t.F_BLH,t.F_TXURL  from V_PACS_FCK_ZLK_TX t  ";
		ConnPathologyDb db = new ConnPathologyDb();
		java.sql.Statement stmt = db.getConnection().createStatement();
		ResultSet rs = stmt.executeQuery(sql);
		while (rs.next()) {
			System.out.println(rs.getString("F_BLH"));

		}
	}

}
