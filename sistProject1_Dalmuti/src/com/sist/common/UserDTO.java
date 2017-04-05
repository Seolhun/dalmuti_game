package com.sist.common;

import java.io.Serializable;

public class UserDTO implements Serializable { 
	private String id;
	private String nickname;
	private String gender;
	private int pos;
	private String avatar;
	private int level;
	private int point;
	
	//게임 중 필요한 변수
	public int CardNumber;
	public int myTurn = 0;
	public int originRank = 0;

	public UserDTO() {
		// TODO Auto-generated constructor stub
		pos = -1;
		CardNumber = 16;
	}

	public UserDTO(String id, String name, String gender, String avatar, int level, int point) {
		// TODO Auto-generated constructor stub
		this.id = id;
		this.nickname = name;
		this.gender = gender;
		this.avatar = avatar;
		this.level = level;
		this.point = point;
		pos = -1;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public int getPos() {
		return pos;
	}

	public void setPos(int pos) {
		this.pos = pos;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getPoint() {
		return point;
	}

	public void setPoint(int point) {
		this.point = point;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return id + "|" + nickname + "|" + 
				gender + "|" + pos + "|" + 
					avatar + "|" + level + "|" + point;
	}

}
