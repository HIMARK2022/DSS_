package com.himark.dss;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBConnection {
	// DB와 연결되는 Connection 객체 반환
	public static Connection getConnectivity(String url, String dbId, String dbPwd) {
		Connection conn = null;

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(url, dbId, dbPwd);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return conn;
	}
	
	// DB 연결 테스트
	public static void connectionTest(Connection conn) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		// 직급 테이블로 테스트
		String sql = "select * from pos";
		
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				System.out.println("직급 아이디: " + rs.getString("pos_id") + ", 직급명: " + rs.getString("pos_name"));
			}
			System.out.println("출력이 완료되었습니다.\n");
			
			pstmt.close();
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
}
