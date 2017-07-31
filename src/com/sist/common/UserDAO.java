package com.sist.common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.sist.client.Login;

public class UserDAO extends DBConnector {
	public UserDTO getUserDTO(String id) {
		UserDTO user = null;
		try {
			getConnection();
			String sql = "SELECT unickname, ugender, uavatar, ulevel, upoint From userinfo" + " WHERE userid=?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, id);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				user = new UserDTO();
				user.setId(id.trim());
				user.setNickname((rs.getString(1)).trim());
				user.setGender((rs.getString(2)).trim());
				user.setAvatar(rs.getString(3).trim());
				user.setLevel(Integer.parseInt(rs.getString(4)));
				user.setPoint(Integer.parseInt(rs.getString(5)));
			}
			rs.close();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		} finally {
			disConnection();
		}
		return user;
	}

	// �α���
	public String loginUser(String id, String passwd) {
		String result = "";
		try {
			// ����
			getConnection();
			String sql = "SELECT COUNT(*) FROM userinfo " + "WHERE userid=?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, id);
			ResultSet rs = ps.executeQuery();
			rs.next(); // Ŀ����ġ ����
			int count = rs.getInt(1);
			rs.close();
			if (count == 0) {
				result = "���� ���� �ʴ� ���̵� �Դϴ�.";
				// �ش� ���̵� �������� �ʴ� �ٴ� �޼���
			} else {
				sql = "SELECT upasswd FROM userinfo " + "WHERE userid=?";
				ps = conn.prepareStatement(sql);
				ps.setString(1, id);
				rs = ps.executeQuery();
				rs.next();
				String db_passwd = rs.getString(1);
				rs.close();
				if (db_passwd.equals(passwd)) {
					result = "�α��ο� �����Ͽ����ϴ�.";
					// �α��� ����

				} else {
					result = "��� ��ȣ�� �ٸ��ϴ�.";
					// ��� ��ȣ�� �ٸ��ٴ� �޼���
				}
			}
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		} finally {
			disConnection();
		}
		return result;
	}
	@Override
	public void updatePoint(int lv,int point, String id) {
		// TODO Auto-generated method stub
		try {
			getConnection();
			String sql="UPDATE userinfo SET "
  			      +"ulevel=?,upoint=?"+"WHERE userid=?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, lv);
			ps.setInt(2, point);
			ps.setString(3, id);
			ps.executeUpdate();
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}finally {
			disConnection();
		}
	}
}
