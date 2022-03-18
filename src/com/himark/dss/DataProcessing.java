package com.himark.dss;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.himark.data.Dept;
import com.himark.data.Duty;
import com.himark.data.Manager;
import com.himark.data.Pos;
import com.himark.data.User;

public class DataProcessing {
	// 테이블 관리
	public static void manageTable(Connection m_conn, Connection c_conn, String tableName) {
		// 변수 선언
		PreparedStatement m_pstmt = null;
		PreparedStatement c_pstmt = null;
		ResultSet m_rs = null;
		ResultSet c_rs = null;

		String sql = "select * from " + tableName; // 테이블에 따라 select

		try {
			// 마크애니 테이블 데이터 가져오기
			m_pstmt = m_conn.prepareStatement(sql);
			m_rs = m_pstmt.executeQuery();

			// 고객사 테이블 데이터 가져오기
			c_pstmt = c_conn.prepareStatement(sql);
			c_rs = c_pstmt.executeQuery();

			if (tableName.equals("user")) {
				LinkedList<User> m_list = new LinkedList<User>();
				LinkedList<User> c_list = new LinkedList<User>();

				while (m_rs.next()) {
					m_list.add(new User(m_rs.getString("user_id"), m_rs.getString("user_name"),
										m_rs.getString("pos_id"), m_rs.getString("duty_id"), 
										m_rs.getString("dept_id"), m_rs.getString("authority_code")));
				}

				while (c_rs.next()) {
					c_list.add(new User(c_rs.getString("user_id"), c_rs.getString("user_name"),
										c_rs.getString("pos_id"), c_rs.getString("duty_id"), 
										c_rs.getString("dept_id"), c_rs.getString("authority_code")));
				}

				// 확인
				System.out.println("마크애니 user");
				for (User user : m_list) {
					System.out.println(user);
				}
				System.out.println();

				System.out.println("고객사 user");
				for (User user : c_list) {
					System.out.println(user);
				}
				System.out.println();
				
				// close
				close(m_pstmt, c_pstmt, m_rs, c_rs);
				
				// Map에 마크애니 데이터 넣기
				Map<String, User> userMap = m_list.stream()
										   .collect(Collectors.toMap(User::getUserId, Function.identity()));

				// Map 출력 확인
				for (String key : userMap.keySet()) {
					System.out.println("User: " + key + ", " + userMap.get(key).getUserName()
									   + ", " + userMap.get(key).getPosId()
									   + ", " + userMap.get(key).getDutyId()
									   + ", " + userMap.get(key).getDeptId()
									   + ", " + userMap.get(key).getAuthorityCode());
				}

				// left join
				List<User> leftJoinUser = c_list.stream()
										 .filter(it -> !userMap.containsKey(it.getUserId()))
										 .collect(Collectors.toList());

				System.out.println("\n==== After left join ====");
				for (User user : leftJoinUser) {
					System.out.println(user);
				}
				System.out.println();

				// 데이터 삽입
				sql = "insert into user(user_id, user_password, user_name, pos_id, duty_id, dept_id, authority_code, current_state) values(?, ?, ?, ?, ?, ?, ?, ?)";
				m_pstmt = m_conn.prepareStatement(sql);
				for (int i = 0; i < leftJoinUser.size(); i++) {
					m_pstmt.setString(1, leftJoinUser.get(i).getUserId());
					m_pstmt.setString(2, leftJoinUser.get(i).getUserId());
					m_pstmt.setString(3, leftJoinUser.get(i).getUserName());
					m_pstmt.setString(4, leftJoinUser.get(i).getPosId());
					m_pstmt.setString(5, leftJoinUser.get(i).getDutyId());
					m_pstmt.setString(6, leftJoinUser.get(i).getDeptId());
					m_pstmt.setString(7, leftJoinUser.get(i).getAuthorityCode());
					m_pstmt.setString(8, "O1");
					m_pstmt.addBatch();
					m_pstmt.clearParameters();

					// OutOfMemory를 고려하여 10,000건 단위로 insert
					if ((i % 10000) == 0) {
						m_pstmt.executeBatch();
						m_pstmt.clearBatch();
					}
				}
				// 나머지 구문에 대해 insert
				m_pstmt.executeBatch();
				
				
			} else if (tableName.equals("dept")) {
				LinkedList<Dept> m_list = new LinkedList<Dept>();
				LinkedList<Dept> c_list = new LinkedList<Dept>();

				while (m_rs.next()) {
					m_list.add(new Dept(m_rs.getString("dept_id"), m_rs.getString("dept_name"),
										m_rs.getString("upper_dept_id")));
				}

				while (c_rs.next()) {
					c_list.add(new Dept(c_rs.getString("dept_id"), c_rs.getString("dept_name"),
										c_rs.getString("upper_dept_id")));
				}

				// 확인
				System.out.println("마크애니 dept");
				for (Dept dept : m_list) {
					System.out.println(dept);
				}
				System.out.println();

				System.out.println("고객사 dept");
				for (Dept dept : c_list) {
					System.out.println(dept);
				}
				System.out.println();

				
				// Map에 마크애니 데이터 넣기
				Map<String, Dept> deptMap = m_list.stream()
										   .collect(Collectors.toMap(Dept::getDeptId, Function.identity()));

				// Map 출력 확인
				for (String key : deptMap.keySet()) {
					System.out.println("Dept: " + key + ", " + deptMap.get(key).getDeptName()
							 		   + ", " + deptMap.get(key).getUpperDeptId());
				}

				// left join
				List<Dept> leftJoinDept = c_list.stream()
										 .filter(it -> !deptMap.containsKey(it.getDeptId()))
										 .collect(Collectors.toList());

				System.out.println("\n==== After left join ====");
				for (Dept dept : leftJoinDept) {
					System.out.println(dept);
				}
				System.out.println();
				
				// close
				close(m_pstmt, c_pstmt, m_rs, c_rs);
				
				// 데이터 삽입
				sql = "insert into dept(dept_id, dept_name, upper_dept_id) values(?, ?, ?)";
				m_pstmt = m_conn.prepareStatement(sql);
				for (int i = 0; i < leftJoinDept.size(); i++) {
					m_pstmt.setString(1, leftJoinDept.get(i).getDeptId());
					m_pstmt.setString(2, leftJoinDept.get(i).getDeptName());
					m_pstmt.setString(3, leftJoinDept.get(i).getUpperDeptId());
					m_pstmt.addBatch();
					m_pstmt.clearParameters();

					// OutOfMemory를 고려하여 10,000건 단위로 insert
					if ((i % 10000) == 0) {
						m_pstmt.executeBatch();
						m_pstmt.clearBatch();
					}
				}
				// 나머지 구문에 대해 insert
				m_pstmt.executeBatch();
				
			} else if (tableName.equals("duty")) {
				LinkedList<Duty> m_list = new LinkedList<Duty>();
				LinkedList<Duty> c_list = new LinkedList<Duty>();

				while (m_rs.next()) {
					m_list.add(new Duty(m_rs.getString("duty_id"), m_rs.getString("duty_name")));
				}

				while (c_rs.next()) {
					c_list.add(new Duty(c_rs.getString("duty_id"), c_rs.getString("duty_name")));
				}

				// 확인
				System.out.println("마크애니 duty");
				for (Duty duty : m_list) {
					System.out.println(duty);
				}
				System.out.println();

				System.out.println("마크애니 duty");
				for (Duty duty : c_list) {
					System.out.println(duty);
				}
				System.out.println();
				
				// close
				close(m_pstmt, c_pstmt, m_rs, c_rs);
				
				// Map에 마크애니 데이터 넣기
				Map<String, Duty> dutyMap = m_list.stream()
										   .collect(Collectors.toMap(Duty::getDutyId, Function.identity()));

				// Map 출력 확인
				for (String key : dutyMap.keySet()) {
					System.out.println("Duty: " + key + ", " + dutyMap.get(key).getDutyName());
				}

				// left join
				List<Duty> leftJoinDuty = c_list.stream()
										 .filter(it -> !dutyMap.containsKey(it.getDutyId()))
										 .collect(Collectors.toList());

				System.out.println("\n==== After left join ====");
				for (Duty duty : leftJoinDuty) {
					System.out.println(duty);
				}
				System.out.println();

				// 데이터 삽입
				sql = "insert into duty(duty_id, duty_name) values(?, ?)";
				m_pstmt = m_conn.prepareStatement(sql);
				for (int i = 0; i < leftJoinDuty.size(); i++) {
					m_pstmt.setString(1, leftJoinDuty.get(i).getDutyId());
					m_pstmt.setString(2, leftJoinDuty.get(i).getDutyName());
					m_pstmt.addBatch();
					m_pstmt.clearParameters();

					// OutOfMemory를 고려하여 10,000건 단위로 insert
					if ((i % 10000) == 0) {
						m_pstmt.executeBatch();
						m_pstmt.clearBatch();
					}
				}
				// 나머지 구문에 대해 insert
				m_pstmt.executeBatch();
				
			} else if (tableName.equals("pos")) {
				LinkedList<Pos> m_list = new LinkedList<Pos>();
				LinkedList<Pos> c_list = new LinkedList<Pos>();
				
				while (m_rs.next()) {
					m_list.add(new Pos(m_rs.getString("pos_id"), m_rs.getString("pos_name")));
				}
				
				while (m_rs.next()) {
					m_list.add(new Pos(m_rs.getString("pos_id"), m_rs.getString("pos_name")));
				}

				while (c_rs.next()) {
					c_list.add(new Pos(c_rs.getString("pos_id"), c_rs.getString("pos_name")));
				}

				// 확인
				System.out.println("마크애니 pos");
				for (Pos pos : m_list) {
					System.out.println(pos);
				}
				System.out.println();

				System.out.println("고객사 pos");
				for (Pos pos : c_list) {
					System.out.println(pos);
				}
				System.out.println();
				
				// close
				close(m_pstmt, c_pstmt, m_rs, c_rs);
				
				// Map에 마크애니 데이터 넣기
				Map<String, Pos> posMap = m_list.stream()
										 .collect(Collectors.toMap(Pos::getPosId, Function.identity()));

				// Map 출력 확인
				for (String key : posMap.keySet()) {
					System.out.println("Pos: " + key + ", " + posMap.get(key).getPosName());
				}

				// left join
				List<Pos> leftJoinPos = c_list.stream()
									   .filter(it -> !posMap.containsKey(it.getPosId()))
									   .collect(Collectors.toList());
				
				System.out.println("\n==== After left join ====");
				for (Pos pos : leftJoinPos) {
					System.out.println(pos);
				}
				System.out.println();

				// 데이터 삽입
				sql = "insert into pos(pos_id, pos_name) values(?, ?)";
				m_pstmt = m_conn.prepareStatement(sql);
				for (int i = 0; i < leftJoinPos.size(); i++) {
					m_pstmt.setString(1, leftJoinPos.get(i).getPosId());
					m_pstmt.setString(2, leftJoinPos.get(i).getPosName());
					m_pstmt.addBatch();
					m_pstmt.clearParameters();

					// OutOfMemory를 고려하여 10,000건 단위로 insert
					if ((i % 10000) == 0) {
						m_pstmt.executeBatch();
						m_pstmt.clearBatch();
					}
				}
				// 나머지 구문에 대해 insert
				m_pstmt.executeBatch();
				
			} else {
				LinkedList<Manager> m_list = new LinkedList<Manager>();
				
				sql="truncate table manager";
				m_pstmt = m_conn.prepareStatement(sql);
				m_pstmt.executeUpdate();
				
				sql = "select A.user_id as '승인자아이디', B.user_id  as '승인대상' from (select u.user_id, de.dept_id from user as u left join dept as de on u.dept_id = de.upper_dept_id join duty as du on u.duty_id = du.duty_id where du.duty_name not in('사원')) as A join (select U.user_id, U.dept_id from user as u join (select de.dept_id from user as u left join dept as de on u.dept_id = de.upper_dept_id) as D on u.dept_id = D.dept_id join duty as du on u.duty_id = du.duty_id  where du.duty_name not in('사원') order by u.user_id ASC) as B on A.dept_id = B.dept_id union all select C.user_id, D.user_id from (select u.user_id, u.dept_id from user as u join duty as d on u.duty_id = d.duty_id where d.duty_name = '팀장') as C right join ( select u.user_id, u.dept_id from user as u join (select de.dept_id from user as u left join dept as de on u.dept_id = de.upper_dept_id) as D on u.dept_id = D.dept_id join duty as du on u.duty_id = du.duty_id where du.duty_name in('사원') order by u.user_id ASC) as D on C.dept_id = D.dept_id ;";
				
				m_pstmt = m_conn.prepareStatement(sql);
				m_rs = m_pstmt.executeQuery();
				
				while (m_rs.next()) {
					m_list.add(new Manager(m_rs.getString("승인자아이디"),m_rs.getString("승인대상"),"T1",null,null));
				}
				

				// 확인
				System.out.println("마크애니 manager");
				for (Manager manager : m_list) {
					System.out.println(manager);
				}
				System.out.println();
				
				// close
				close(m_pstmt, c_pstmt, m_rs, c_rs);
				
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		close(m_pstmt, c_pstmt, m_rs, c_rs);
	}

	// 리소스 닫기
	public static void close(PreparedStatement m_pstmt, PreparedStatement c_pstmt, ResultSet m_rs, ResultSet c_rs) {
		try {
			m_rs.close();
			c_rs.close();
			m_pstmt.close();
			c_pstmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}