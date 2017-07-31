package com.sist.common;
import java.sql.*;
public class DBConnector {
	protected Connection conn;
	// 서버와 연결
	protected PreparedStatement ps;
	// 문장 전송
	// 서버주소

	private final String URL = "jdbc:oracle:thin:@211.238.142.28:1521:ORCL";

	//private final String URL = "jdbc:mysql://localhost:3306/Dalmuti";
	int header = 0;
	public DBConnector() {
		// TODO Auto-generated constructor stub
		// 오라클 연결 ==> 드라이버 설정
		try {
			// 컴파일 예외 ==> 컴파일시에 예외처리를 확인
			Class.forName("oracle.jdbc.driver.OracleDriver");
			//Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException cf) {
			System.out.println(cf.getMessage());
		}
	}
	// 연결
	public void getConnection() {
		try {
			//conn = DriverManager.getConnection(URL, "scott", "tiger");
			conn = DriverManager.getConnection(URL, "admin", "1234");
		} catch (Exception ex) {
			System.out.println("DBConn:" + ex.getMessage());
			ex.printStackTrace();
		}
	}

	// 연결 해제
	public void disConnection() {
		try {
			if (ps != null)
				ps.close();
			if (conn != null)
				conn.close();
		} catch (Exception ex) {
		}
	}
	public boolean joinMember(MemberDTO dto) {
		return false;
	}
	public boolean idCheckMember(String id){
		return false;
	}
	public UserDTO getUserDTO(String id) {
		return null;
	}
	public String loginUser(String id, String passwd){
		return null;
	}
	public void updatePoint(int lv,int point, String id){
		;
	}
	

}
