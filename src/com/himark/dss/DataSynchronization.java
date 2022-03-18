package com.himark.dss;

import java.sql.Connection;

public class DataSynchronization {
	public static void main(String[] args) {
		// 마크애니 DB
		String m_url = "jdbc:mysql://192.168.1.11:3306/markanydb?useSSL=false";
		String m_dbId = "markany";
		String m_dbPwd = "markany";
		
		// 고객사 DB (개발 완료 후 jar 실행 시 입력받을 수 있도록 처리)
//		String c_url = "jdbc:mysql://" + args[0] + ":3306/" + args[1] + "?useSSL=false";
//		String c_dbId = args[2];
//		String c_dbPwd = args[3];
		
		// 고객사 DB
		String c_url = "jdbc:mysql://192.168.1.11:3306/clientdb?useSSL=false";
		String c_dbId = "client";
		String c_dbPwd = "client";
		
		// DB 연결
		Connection m_conn = DBConnection.getConnectivity(m_url, m_dbId, m_dbPwd);
		Connection c_conn = DBConnection.getConnectivity(c_url, c_dbId, c_dbPwd);
		
		// DB 연결 테스트
//		System.out.println("마크애니 DB 연결 테스트");
//		DBConnection.connectionTest(m_conn);
		
//		System.out.println("고객사 DB 테스트");
//		DBConnection.connectionTest(c_conn);
		
		// 인사연동
		DataProcessing.manageTable(m_conn, c_conn, "user");
		DataProcessing.manageTable(m_conn, c_conn, "dept");
		DataProcessing.manageTable(m_conn, c_conn, "duty");
		DataProcessing.manageTable(m_conn, c_conn, "pos");
		DataProcessing.manageTable(m_conn, c_conn, "manager");
	}
		
}
