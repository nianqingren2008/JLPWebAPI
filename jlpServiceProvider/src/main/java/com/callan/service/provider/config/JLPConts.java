package com.callan.service.provider.config;

public class JLPConts {
	public final static String PatientGlobalTable = "D_PATIENTGLOBAL";
	public final static String ActiveFlag = "1"; //有效
	public static final String Inactive = "0";   //失效
	public static String db_driver="com.microsoft.sqlserver.jdbc.SQLServerDriver";
	public static String db_url="jdbc:sqlserver://pathdb.tjh.com:1433;DatabaseName=pathnet";
	public static String db_user="pacs_path_view";
	public static String db_pwd="pacs_path_view";
}
