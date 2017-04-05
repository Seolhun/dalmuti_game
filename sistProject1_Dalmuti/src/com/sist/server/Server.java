package com.sist.server;

import java.util.*;


import com.sist.common.*;
import com.sist.server.Room;
import com.sist.server.Server.Client;

import java.io.*;
import java.net.*;

public class Server implements Runnable {
	ServerSocket ss;
	Vector<Client> waitVc = new Vector<Client>();// 접속자 정보 저장
	Vector<Room> roomVc = new Vector<Room>();// 방정보 저장
	CardList cl;
	ArrayList<Integer> cardIndex;

	public Server() {
		for(int i=0;i<12;i++){
			Room room = new Room("Empty"," "," ",5);
			roomVc.addElement(room);
		}
		try {
			ss = new ServerSocket(9191);
			System.out.println("Start Server...");
		} catch (Exception ex) {
		}
	}

	// 접속을 받는다
	public void run() {
		try {
			while (true) {
				Socket s = ss.accept();
				Client client = new Client(s);
				client.start();
			}
		} catch (Exception ex) {
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Server server = new Server();
		new Thread(server).start();
	}

	class Client extends Thread {

		MemberDTO member;
		UserDTO user;
		DBConnector dbconn;
		DAOContainer c= new DAOContainer();
		Socket s;
		BufferedReader in;// 읽기
		OutputStream out;// 쓰기 TCP

		ObjectOutputStream oos;
		ObjectInputStream ois;

		boolean clientRunChck = true;
		
		public Client(Socket s) {
			try {
				this.s = s;
				in = new BufferedReader(new InputStreamReader(s.getInputStream()));
				out = s.getOutputStream();
			} catch (Exception ex) {
			}
		}

		// 통신
		public void run() {
			try {
				while (clientRunChck) {
					// 요청 받는다
					String msg = in.readLine();
					System.out.println(msg);
					StringTokenizer st = new StringTokenizer(msg, "|");
					int no = Integer.parseInt(st.nextToken());
					switch (no) {
					case Function.LOGIN: {
						dbconn = c.getBean("userdao");					
						String id = st.nextToken();
						String pwd = st.nextToken();
						String login = dbconn.loginUser(id, pwd);
						boolean CheckingId = false;
						for (Client c : waitVc) {
							if (c.user.getId().equals(id)) {
								CheckingId = true;
								break;
							}
						}
						if (CheckingId) {
							messageTo(Function.LOGINFAILED + "|" + "접속중인 아이디 입니다.");
							dbconn = null;
							System.gc();
							break;
						}
						System.out.println(login);
						if (login.equals("로그인에 성공하였습니다.")) {
							user = dbconn.getUserDTO(id);
						} else {
							messageTo(Function.LOGINFAILED + "|" + login);
							dbconn = null;
							System.gc();
							break;
						}
						messageAll(Function.OTHERLOGIN + "|" + user.getLevel() + "|" + user.getId() + "|"
								+ user.getNickname() + "|" + user.getGender() + "|"
								+ (user.getPos() == -1 ? "대기실" : user.getPos()+"번방"));

						waitVc.addElement(this);
						for (Client c : waitVc) {
							messageTo(Function.LOGIN + "|" + c.user.getLevel() + "|" + c.user.getId() + "|"
									+ c.user.getNickname() + "|" + c.user.getGender() + "|"
									+ (c.user.getPos() == -1 ? "대기실" : c.user.getPos()+"번방"));
						}

						for (Room room : roomVc) {
							messageTo(Function.MAKEROOM + "|" + roomVc.indexOf(room) + "|" + room.roomName + "|"
									+ room.roomState + "|" + room.current + "/" + room.maxcount);
						}
						messageTo(Function.MYINFO + "|" + user.toString());
						dbconn = null;
						System.gc();
						break;
					}
					case Function.JOINCANCEL : {
						in.close();
						out.close();
						s.close();
						clientRunChck = false;
					
						break;
					}
					
					case Function.IDCHECK: {
						String checkId = st.nextToken();
						// DB CONNECTOR
						dbconn = c.getBean("memberdao");
						boolean checkRes = dbconn.idCheckMember(checkId);
						System.out.println(checkRes);
						messageTo(Function.IDCHECK + "|" + String.valueOf(checkRes));
						break;
					}
					case Function.MAKEROOM: {
						int rNum = Integer.parseInt(st.nextToken());
						String rName = st.nextToken();
						String state = st.nextToken();
						String pwd = st.nextToken();
						String cnt = st.nextToken();
						System.out.println(rNum);
						Room room = roomVc.get(rNum);
						room.userVc.addElement(this);
						room.roomName = rName;
						room.roomState = state;
						room.roomPwd = pwd;
						
						room.current++;
						user.setPos(rNum);
						messageAll(Function.MAKEROOM + "|" + rNum + "|" + room.roomName + "|"
								+ room.roomState + "|" + room.current + "/" + room.maxcount);
						
						messageTo(Function.ROOMIN + "|" + user.getPos());
						messageAll(Function.USERINFOREF + "|" + user.getId() + "|" + user.getPos());
						break;
					}

					case Function.ROOMIN: {
						int roomNum = Integer.parseInt(st.nextToken());
						String pwd = st.nextToken();
						String gameMsg = "님이 입장했습니다.";
						
						Room room = roomVc.get(roomNum);
						if(!pwd.equals(room.roomPwd)){
							messageTo(Function.PASSWORDERROR+"|");
							break;
						}
						room.current++;
						user.setPos(roomNum);

						for (Client c : room.userVc) {
							messageTo(Function.ROOMADD + "|" + c.user.toString());
						}
						room.userVc.addElement(this);
						messageTo(Function.ROOMIN + "|" + user.getPos());
					
						for (Client c : room.userVc) {
							if (!user.getId().equals(c.user.getId())) {
								c.messageTo(Function.ROOMADD + "|" + user.toString());
							}
						}

						messageAll(Function.WAITROOMUPDATE + "|" + roomNum + "|" + room.current + "|" + room.maxcount);
						messageAll(Function.USERINFOREF + "|" + user.getId() + "|" + user.getPos());
						messageMember(Function.GAMECHAT + "|[" + user.getNickname() + "] " + gameMsg + "|" + "black", roomNum);
						break;
					}
					case Function.JOIN: {
						member = new MemberDTO();
						member.setId(st.nextToken());
						member.setPwd(st.nextToken());
						member.setName(st.nextToken());
						member.setNickName(st.nextToken());
						member.setGen(st.nextToken());
						member.setTel(st.nextToken());
						member.setAvatar(st.nextToken());
						dbconn = c.getBean("memberdao");
						if (dbconn.joinMember(member))
							messageTo((Function.JOIN + "|" + "가입완료"));
						else
							messageTo((Function.JOIN + "|" + "가입실패"));
						
						in.close();
						out.close();
						s.close();
						clientRunChck = false;
						break;
					}
					case Function.GAMEEXIT: {
						waitVc.removeElement(this);
						messageAll(Function.GAMEEXIT + "|" + user.getLevel() + "|" + user.getId() + "|"
								+ user.getGender() + "|" + user.getPos());
						
						dbconn = c.getBean("userdao");
						dbconn.updatePoint(user.getLevel(), user.getPoint(), user.getId());
						dbconn= null;
						clientRunChck = false;
						in.close();
						out.close();
						s.close();
						System.gc();
						break;
					}
					case Function.MSGSEND: {
						String yid = st.nextToken();
						String strMsg = st.nextToken();

						for (Client user : waitVc) {
							if (yid.equals(user.user.getId())) {
								user.messageTo(Function.MSGSEND + "|" + this.user.getId() + "|" + strMsg);
								break;
							}
						}
						break;
					}
					case Function.WAITROOMCHAT: {
						String wrMsg = st.nextToken();
						String wrColor = st.nextToken();
						messageAll(Function.WAITROOMCHAT + "|[" + user.getId() + "] " + wrMsg + "|" + wrColor);
						break;
					}
					case Function.GAMECHAT: {
						int rNum = Integer.parseInt(st.nextToken());
						String gameMsg = st.nextToken();
						messageMember(Function.GAMECHAT + "|[" + user.getNickname() + "] " + gameMsg + "|" + "black", rNum);
						break;
					}
					
					case Function.ROOMOUT: {
						int rnum = Integer.parseInt(st.nextToken());
						String gameMsg = "님이 퇴장했습니다.";
						Room room = roomVc.get(rnum);
						room.current--;
						room.isFirst = true;
						room.priorityCnt=0;
						for( int i =0;i<5;i++){
							room.tmpRank[i] = 0;
						}
						room.turn.removeAllElements();
						room.ranking.removeAllElements();
						for (Client c : room.userVc) {
							c.messageTo(Function.ROOMOUT + "|" + user.getId());
							// c.messageTo(Function.GAMECHAT +"|["+ user.getId()
							// + "] 님이 퇴장하셨습니다.");
						}

						messageTo(Function.MYROOMOUT + "|");

						for (Client c : room.userVc) {
							if (user.getId().equals(c.user.getId())) {
								room.userVc.remove(c);
								break;
							}
						}
						// 대기실 처리
						user.setPos(-1);
						messageAll(Function.WAITROOMUPDATE + "|" + rnum + "|" + room.current + "|" + room.maxcount 
								+"|"+user.getId());
						messageAll(Function.USERINFOREF + "|" + user.getId() + "|" + user.getPos());
						messageMember(Function.GAMECHAT + "|[" + user.getNickname() + "] " + gameMsg + "|" + "black", rnum);
						/*if (room.current < 1)
							roomVc.remove(room);*/
						if(room.current<1){
							room.roomName="Empty";
						}
						break;
					}
					case Function.INVITE : {
						String userId = st.nextToken();
						
						for (Client user : waitVc) {
							if (userId.equals(user.user.getId())) {
								user.messageTo(Function.INVITE + "|" + this.user.getId() + "|" + this.user.getPos());
								//break;
							}
						}
						break;
					}
					case Function.INVITENO: {
						String userId = st.nextToken();
						for (Client user : waitVc) {
							if (userId.equals(user.user.getId())) {
								user.messageTo(Function.INVITENO + "|" + this.user.getId());
								//break;
							}
						}
						break;
					}
					case Function.USERINFO: {
						String userId = st.nextToken();
						for (Client c : waitVc) {
							if (userId.equals(c.user.getId())) {
								messageTo(Function.USERINFO + "|" + c.user.toString());
							}
						}
						break;
					}
					case Function.GAMEREADY: {
						int rNum = Integer.parseInt(st.nextToken());
						user.CardNumber = 16;
						Room room = roomVc.get(rNum);
						room.readyCnt++;

						if (room.readyCnt == room.maxcount) {
							Client host = room.userVc.elementAt(0);
							host.messageTo(Function.HOSTSTARTBUTTON + "|");
						}
						break;
					}
					case Function.PRIORITYTURN: {
						int rNum = Integer.parseInt(st.nextToken());
						Room room = roomVc.get(rNum);
						String pUser=st.nextToken();
						String pNum=st.nextToken();
						int tmp = Integer.parseInt(pNum);
						System.out.println("test:"+room.priorityCnt);
						room.tmpRank[room.priorityCnt] = tmp ;
						room.turn.addElement(this);
						
						room.priorityCnt++;
						
						if(room.priorityCnt==room.maxcount){
							messageMember(Function.PRIORITYFINISH +"|" +pNum+"|"+pUser, rNum);
							for(int i=0;i<room.maxcount;i++){
								for(int j=0;j<room.userVc.size();j++){
									if(i==room.tmpRank[j]){
										Client c = room.turn.elementAt(j);
										room.ranking.addElement(c);
										break;
									}
								}
							}
							room.turn.removeAllElements();
							String mesg = "";
							for(Client ranker:room.ranking){
								if(room.ranking.indexOf(ranker) == room.ranking.size()-1)
									mesg += ranker.user.getId() + "|" + room.ranking.indexOf(ranker);
								else
									mesg += ranker.user.getId() + "|" + room.ranking.indexOf(ranker) + "|";
							}
							
							messageMember(Function.AVATARCHANGE + "|" + mesg, rNum);
							
						}
						else{
							Client t = room.userVc.elementAt(room.priorityCnt);
							messageMember(Function.PRIORITYTURN+"|"+t.user.getId()+"|"+pNum+"|"+pUser, rNum);
						}
						break;
					}
					case Function.HOSTSTARTBUTTON: {
						int rNum = Integer.parseInt(st.nextToken());
						Room room = roomVc.get(rNum);
						if(room.isFirst){
							int[] a = new int[5];
							for (int i = 0; i < 5; i++) {
								int b = (int) (Math.random() * 5);
								a[i] = b;
								for (int j = 0; j < i; j++) {
									if (a[i] == a[j]) {
										i--;
										j--;
										continue;
									}
								}
							}
							messageMember(Function.PRIORITYGAME+"|"+a[0]+"|"+a[1]+"|"+a[2]+"|"+a[3]+"|"+a[4], rNum);
							room.isFirst=false;
							Client t = room.userVc.elementAt(0);
							messageMember(Function.PRIORITYTURN + "|"+t.user.getId()+"|-1|empty" ,rNum);
							break;
						}
						messageMember(Function.GAMESTART + "|", rNum);
						String temp = "SYSTEM";
						String gameMsg = "게임을 시작합니다.";
						messageMember(Function.GAMECHAT + "|[" + temp + "] " + gameMsg + "|" + "black", rNum);
						cl = new CardList();
						cl.shuffleCardList();
						
						for (int j = 0; j < room.maxcount; j++) {
							//room.turn.addElement(room.userVc.elementAt(j));
							cardIndex = cl.getCardList(room.maxcount, j);
							Collections.sort(cardIndex);
							Client c = room.userVc.elementAt(j);
							String tmp = "";
							
							//정상작동용

							/*for (int k = 0; k < cardIndex.size(); k++) {

								tmp += cardIndex.get(k) + "|";
							}*/
							
							//1장 테스트용
							for (int k = 0; k < 1; k++) {

								tmp += cardIndex.get(k) + "|";
							}
							c.messageTo(Function.CARDLIST + "|" + tmp);

						}
					
						room.turn = (Vector<Client>) room.ranking.clone();
						room.ranking.removeAllElements();
					
						Client c = room.turn.elementAt(0);
						messageMember(Function.GAMEPLAY + "|" + c.user.getId() + "|-1|1|0", rNum);
						break;
					}
					case Function.UPDATEPOINT:{
						user.setLevel(Integer.parseInt(st.nextToken()));
						user.setPoint(Integer.parseInt(st.nextToken()));
						messageAll(Function.UPDATEPOINT + "|" + user.getLevel() + "|" + user.getId() + "|"
								+ user.getNickname() + "|" + user.getGender() + "|"
								+ (user.getPos() == -1 ? "대기실" : user.getPos()+"번방"));
						messageMember(Function.ROOMPOINTUPDATE+"|"+user.getId()+"|"+user.getLevel(), user.getPos());
						break;
					}
				
					case Function.SUBMITPRESS: {
						int rNum = Integer.parseInt(st.nextToken());
						String pCard1 = st.nextToken();
						String pCard2 = st.nextToken();
						String pCard3 = st.nextToken();
						String empty = st.nextToken();
						Room room = roomVc.get(rNum);
						user.CardNumber -= Integer.parseInt(pCard2);
						room.passCnt = 0;

						messageMember(Function.SUBMITCARDNUMBER + "|" + user.getId() + "|" + pCard2, rNum);
						if (empty.equals("yes")) {
							messageMember(Function.PLAYEREMPTY+"|"+user.getNickname()+"|"+user.getLevel(), rNum);
							int tmp = 0;
							if (room.turn.size() - 1 != room.turn.indexOf(this)) {
								tmp = room.turn.indexOf(this);
							}
							room.passCnt--;
							room.ranking.addElement(this);
							room.turn.remove(this);
							if (room.turn.size() == 1) {
								messageMember(Function.PLAYEREMPTY+"|"+room.turn.elementAt(0).user.getNickname()
										+"|"+room.turn.elementAt(0).user.getLevel(), rNum);
								room.ranking.addElement(room.turn.elementAt(0));
								
								String mesg = "";
								for(Client ranker:room.ranking){
									if(room.ranking.indexOf(ranker) == room.ranking.size()-1)
										mesg += ranker.user.getId() + "|" + room.ranking.indexOf(ranker);
									else
										mesg += ranker.user.getId() + "|" + room.ranking.indexOf(ranker) + "|";
								}
								messageMember(Function.AVATARCHANGE + "|" + mesg, rNum);
								messageMember(Function.PLAYFINISH + "|" , rNum);
								String temp = "SYSTEM";
								String gameMsg="게임이 종료 되었습니다.";
								messageMember(Function.GAMECHAT + "|[" + temp + "] " + gameMsg + "|" + "black", rNum);
								room.readyCnt = 0;
								room.turn.removeAllElements();
								break;
							}
							Client c = room.turn.elementAt(tmp);
							c.messageMember(Function.GAMEPLAY + "|" + c.user.getId() + "|" + pCard1 + "|" + pCard2 + "|"
									+ pCard3, rNum);
						} else {

							
							Client c = room.turn.elementAt((room.turn.indexOf(this)+1)%(room.turn.size()));
							c.messageMember(Function.GAMEPLAY + "|" + c.user.getId() + "|" + pCard1 + "|" + pCard2
									+ "|" + pCard3, rNum);
							
						}
						break;
					}
					case Function.PASSPRESS: {
						int rNum = Integer.parseInt(st.nextToken());
						String pCard1 = st.nextToken();
						String pCard2 = st.nextToken();
						String pCard3 = st.nextToken();
						Room room = roomVc.get(rNum);
						room.passCnt++;
						if (room.passCnt + 1 == room.turn.size()) {
							pCard1 = "-1";
							pCard2 = "1";
							pCard3 = "0";
							room.passCnt = 0;
						}
					
						Client c = room.turn.elementAt((room.turn.indexOf(this)+1)%(room.turn.size()));
						c.messageMember(Function.GAMEPLAY + "|" + c.user.getId() + "|" + pCard1 + "|" + pCard2
								+ "|" + pCard3, rNum);
						break;
					}
					case Function.KICKVOTE : {
						int rNum = Integer.parseInt(st.nextToken());
						String user = st.nextToken();
						String kick = st.nextToken();
						Room room = roomVc.get(rNum);
						messageMember(Function.KICKVOTE+"|"+user+"|"+kick, rNum);
						break;
					}
					case Function.KICKRESULT : {
						int rNum = Integer.parseInt(st.nextToken());
						String id = st.nextToken();
						int result = Integer.parseInt(st.nextToken());
						Room room = roomVc.get(rNum);
						if(result==0){
							room.kickCnt++;
							room.kickYes++;
						}
						else{
							room.kickCnt++;
						}
						if(room.kickCnt == room.current){
							if(room.kickYes>(int)room.kickCnt/2){
								messageMember(Function.KICKRESULT+"|"+id+"|yes", rNum);
								for(int i=0;i<room.userVc.size();i++){
									if(room.userVc.elementAt(i).user.getNickname().equals(id)){
										Client c = room.userVc.elementAt(i);
										c.messageTo(Function.KICKOUT+"|");
									}
									
									
								}
							}
							else{
								messageMember(Function.KICKRESULT+"|"+id+"|no", rNum);
							}
							room.kickCnt=0;
							room.kickYes=0;
						}
						break;
					}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			member = null;
			user = null;
			dbconn = null;
			try {
				in.close();
				out.close();
				s.close();
			} catch (Exception ex) {
			}
			s = null;
			in = null;// 읽기
			out = null;// 쓰기 TCP
			System.gc();
		}

		public synchronized void messageTo(String str) {
			try {
				out.write((str + "\n").getBytes());
			} catch (Exception ex) {			
				waitVc.remove(this);
			}
		}


		public synchronized void messageAll(String str) {
			for(int i=0;i<waitVc.size();i++)
    		{
    			Client client=waitVc.elementAt(i);
    			try
    			{
    				client.messageTo(str);
    			}catch(Exception ex)
    			{
    				waitVc.removeElementAt(i);
    			}
    		}

		}

		public synchronized void messageMember(String str, int rNum) {
			try {
				Room room = roomVc.get(rNum);

				for (Client c : room.userVc) {
					c.messageTo(str);
				}
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
	}
}