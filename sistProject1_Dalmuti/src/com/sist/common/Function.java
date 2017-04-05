package com.sist.common;

public class Function {
	public static final int LOGIN = 100;
	public static final int LOGINFAILED = 199;
	public static final int OTHERLOGIN = 102;
	public static final int MYINFO = 101;
	public static final int JOIN = 110;
	public static final int JOINCANCEL = 111;
	public static final int IDCHECK = 120;

	public static final int MAKEROOM = 200;
	public static final int ROOMIN = 201;
	public static final int ROOMADD = 202;
	public static final int ROOMOUT = 210;
	public static final int MYROOMOUT = 211;
	public static final int PASSWORDERROR = 220;

	public static final int USERINFOREF = 300;
	public static final int WAITROOMUPDATE = 301;


	public static final int WAITROOMCHAT = 400; // 대기실 채팅
	public static final int USERINFO = 410; // 유저정보 요청

	public static final int MSGSEND = 500;// 쪽지 보내기
	public static final int INVITE = 510;
	public static final int INVITENO = 511;


	public static final int GAMEREADY = 600; //게임에서 ready
	public static final int HOSTSTARTBUTTON = 601;  //host한테 start 활성화
	public static final int GAMESTART = 602;
	public static final int CARDLIST = 603;
	public static final int AVATARCHANGE = 609;
	public static final int GAMEPLAY = 610;
	public static final int SUBMITPRESS = 611;
	public static final int SUBMITCARDNUMBER = 612;
	public static final int PASSPRESS =613;
	public static final int PLAYEREMPTY =614;
	public static final int PLAYFINISH =620;
	public static final int UPDATEPOINT = 621;
	public static final int ROOMPOINTUPDATE = 622;
	public static final int GAMECHAT =660;
	
	public static final int PRIORITYGAME= 700;
	public static final int PRIORITYTURN =701;
	public static final int PRIORITYFINISH = 702;
	
	public static final int KICKVOTE = 800;
	public static final int KICKRESULT = 810;
	public static final int KICKOUT = 820;
	
	public static final int GAMEEXIT=999;

}
