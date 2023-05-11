package com.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConn {
	private static Connection conn = null;
	
	private DBConn() {
		
	}
	
	public static Connection getConnection() {
		String url = "jdbc:oracle:thin:@//211.238.142.72/XE";
		String user = "goocamp";
		String pwd = "java$!";
		
		if(conn == null) {
			try {
				Class.forName("oracle.jdbc.driver.OracleDriver");
				conn = DriverManager.getConnection(url, user, pwd);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		
		}
		
		return conn;
	}
	
	public static void close() {
		try {
			if(conn != null && ! conn.isClosed()) {
				conn.close();
			}
		} catch (Exception e) {
		}
		
		conn = null;
	}
}
