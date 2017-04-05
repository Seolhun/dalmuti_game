package com.sist.server;

import java.util.Vector;
import java.util.*;

public class Room {
	String roomName,roomState,roomPwd;
	int passCnt;
	int readyCnt;
	int maxcount;
	int current;
	int priorityCnt;
	int kickCnt,kickYes;
	boolean isFirst;
	Vector<Server.Client> userVc = new Vector<Server.Client>();
	Vector<Server.Client> turn = new Vector<Server.Client> ();
 	Vector<Server.Client> ranking = new Vector<Server.Client>();
	int[] tmpRank = new int[5];
	
	public Room(String rn, String rs, String rp, int max) {
		roomName = rn;
		roomState = rs;
		roomPwd = rp;
		maxcount = max;
		current = 0;
		readyCnt = 0;
		passCnt =0;
		priorityCnt=0;
		kickCnt=0;
		kickYes=0;
		isFirst=true;
	}
}