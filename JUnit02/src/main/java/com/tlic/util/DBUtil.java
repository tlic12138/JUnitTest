package com.tlic.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBUtil {

	/**
	 * 获取连接
	 * @return 连接对象
	 * @throws SQLException
	 */
	public static Connection getConnection() throws SQLException {
		Connection con = null;
		con = DriverManager.getConnection("jdbc:mysql://localhost:3306/junit?characterEncoding=UTF-8&nullCatalogMeansCurrent=true","root","tlic");
		return con;
	}
	
	public static void close(Connection con) {
			try {
				if(con != null) con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}
	public static void close(PreparedStatement ps) {
		try {
			if(ps != null) ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public static void close(ResultSet rs) {
		try {
			if(rs != null) rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
}
