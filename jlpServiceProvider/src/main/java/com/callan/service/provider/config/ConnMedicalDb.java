package com.callan.service.provider.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnMedicalDb {

	public static Connection getConnection() {
		Connection dbConn=null;
		try {
			Class.forName(JLPConts.db_driver);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		try {
			dbConn = DriverManager.getConnection(JLPConts.db_url, JLPConts.db_user, JLPConts.db_pwd);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("连接数据库成功");
		return dbConn;
	}
	

}
