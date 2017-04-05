package com.sist.common;

import java.io.Serializable;
import java.util.Date;

/*
 * 회원 정보 관리 변수 클래스
 */
public class MemberDTO implements Serializable  {
	private String Id;
	private String Name;
	private String NickName;
	private String Tel;
	private String Pwd;
	private String Gen;
	private String Avatar;

	public String getGen() {
		return Gen;
	}

	public void setGen(String gen) {
		Gen = gen;
	}

	public String getId() {
		return Id;
	}

	public void setId(String id) {
		Id = id;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getNickName() {
		return NickName;
	}

	public void setNickName(String nickName) {
		NickName = nickName;
	}

	public String getPwd() {
		return Pwd;
	}

	public void setPwd(String pwd) {
		Pwd = pwd;
	}

	public void setPwd(char[] pwd) {
		String pwdtmp = new String(pwd);
		Pwd = pwdtmp;
	}

	public String getTel() {
		return Tel;
	}

	public void setTel(String tel) {
		Tel = tel;
	}

	public String getAvatar() {
		return Avatar;
	}

	public void setAvatar(String avatar) {
		Avatar = avatar;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return Id + "|" + Pwd + "|" + Name + "|" + NickName + "|" + Gen + "|" + Tel + "|" + Avatar;
	}

	

	
}
