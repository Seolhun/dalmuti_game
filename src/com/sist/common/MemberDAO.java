package com.sist.common;

import java.sql.*;

public class MemberDAO extends DBConnector {
	
	// 회원 가입
	public boolean joinMember(MemberDTO dto) {
		String sql = "";
		int count = 1;
		try {
			getConnection();
			sql = "INSERT INTO userinfo VALUES(?,?,?,?,?,?,?,?,?)";
			ps = conn.prepareStatement(sql);
			ps.setString(count++, dto.getId());
			ps.setString(count++, dto.getPwd());
			ps.setString(count++, dto.getName());
			ps.setString(count++, dto.getNickName());
			ps.setString(count++, dto.getTel());
			ps.setString(count++, dto.getGen());
			ps.setInt(count++, Integer.parseInt(dto.getAvatar()));
			ps.setInt(count++, 1);
			ps.setInt(count++, 0);
			ps.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		} finally {
			disConnection();
		}
		return true;
	}

	public boolean idCheckMember(String id) {
		try {
			getConnection();
			String sql = "SELECT userid FROM userinfo " + "WHERE userid=?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, id);
			ResultSet rs = ps.executeQuery();
			if (rs.next())
				return true;
			else
				return false;
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		} finally {
			disConnection();
		}
	}

}
