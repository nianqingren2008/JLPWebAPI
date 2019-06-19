package com.callan.service.provider.config;

import java.beans.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConnPathologyDb {

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
	
	 //测试用例
    public static void main(String[] args) throws Exception{
        String  sql="select  top 10  t.F_BLH,t.F_TXURL  from V_PACS_FCK_ZLK_TX t  ";
        java.sql.Statement stmt = ConnPathologyDb.getConnection().createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        while(rs.next()){
        	System.out.println(rs.getString("F_BLH"));
        	
        }
    }
	

}
