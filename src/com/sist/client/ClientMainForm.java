package com.sist.client;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.*;

import com.sist.common.Function;
import com.sist.common.MemberDTO;
import com.sist.common.UserDTO;

/* client side 메인 메소드 
 * 여기서 서버와의 소켓연결을 진행 할 예정
 * login - waitroom - gameplay 3개의 패널을 cardlayout으로 관리
 */
public class ClientMainForm extends JFrame implements ActionListener, Runnable, MouseListener {
	Login login = new Login();
	WaitRoom wr = new WaitRoom();
	MakeRoom mr = new MakeRoom();
	CardLayout card = new CardLayout();
	GamePlay gp = new GamePlay();
	JoinMember jm = new JoinMember();
	SendMessage sm = new SendMessage();
	RecvMessage rm = new RecvMessage();
	UserInfor ui = new UserInfor();
	PriorityCard pc = new PriorityCard();
	Invite iv = new Invite();
	KickPlayer kp = new KickPlayer();

	Socket s;
	OutputStream out;
	BufferedReader in;
	


	String ServerIP = "211.238.142.37";


	UserDTO user = new UserDTO();
	MemberDTO member = new MemberDTO();

	ImageIcon roomEmp = new ImageIcon(SetImager.setImage( getClass().getResource("/image/room0.png"), 157 , 140));
	ImageIcon openRoom = new ImageIcon(SetImager.setImage( getClass().getResource("/image/room1.png"), 157 , 140));
	ImageIcon closeRoom = new ImageIcon(SetImager.setImage( getClass().getResource("/image/room2.png"), 157 , 140));
	ImageIcon[] roomMember = new ImageIcon[6];
	

	int selRow = -1;

	ClientMainForm(String Framebar) {
		super.setTitle(Framebar);
		setLayout(card);

		add("LOG", login);
		add("WR", wr);
		add("GP", gp);

		setSize(650, 480);
		setLocation(300, 250);
		setVisible(true);

		// 아이디 저장
		//File file = new File("temp/idsave.txt");
		try {
			login.tf.setText("");
			FileReader fis = new FileReader("temp/idsave.txt");
			int i = 0;
			String temp = "";
			while ((i = fis.read()) != -1) {
				temp += (String.valueOf((char) (i)));
			}
			StringTokenizer st = new StringTokenizer(temp, "|");
			String idsave = st.nextToken();
			boolean idboolean = Boolean.parseBoolean(st.nextToken());

			login.tf.setText(idsave);
			if(idsave.length()>3){
				login.pf.requestFocus();
			}
			login.idSave.setSelected(idboolean);
			fis.close();
			System.out.println(temp);
		} catch (Exception e) {
			e.getMessage();
		}
		for(int i=0;i<6;i++){
			roomMember[i] = new ImageIcon(SetImager.setImage( getClass().getResource("/image/mb"+i+".png"), 115 , 30));
		}

		login.b1.addActionListener(this);
		login.b2.addActionListener(this);
		//login.b3.addActionListener(this);
		login.idSave.addActionListener(this);
		login.pf.addActionListener(this);

		wr.b1.addActionListener(this);
		wr.b2.addActionListener(this); // 쪽지보내기
		wr.b3.addActionListener(this); // 개인정보보기
		wr.b4.addActionListener(this);
		wr.tf.addActionListener(this);
		wr.table2.addMouseListener(this);
		for (int i = 0; i < 12; i++) {
			//wr.roomBtn[i].addActionListener(this);
			wr.rp[i].roomBtn.addActionListener(this);
		}

		gp.tf.addActionListener(this);
		gp.opBtn2.addActionListener(this);
		gp.opBtn3.addActionListener(this);
		gp.opBtn4.addActionListener(this);
		gp.readyBtn.addActionListener(this);
		gp.startBtn.addActionListener(this);
		gp.summitBtn.addActionListener(this);
		gp.passBtn.addActionListener(this);
		gp.chatBtn.addActionListener(this);

		mr.b1.addActionListener(this);
		mr.b2.addActionListener(this);

		sm.b1.addActionListener(this);
		sm.b2.addActionListener(this);

		rm.b1.addActionListener(this);
		rm.b2.addActionListener(this);

		/*jm.btnCancel.addActionListener(this);
		jm.btnCheckId.addActionListener(this);
		jm.btnInsert.addActionListener(this);*/

		pc.bu[0].addActionListener(this);
		pc.bu[1].addActionListener(this);
		pc.bu[2].addActionListener(this);
		pc.bu[3].addActionListener(this);
		pc.bu[4].addActionListener(this);
		pc.okBtn.addActionListener(this);

		iv.canBtn.addActionListener(this);
		iv.inBtn.addActionListener(this);
		iv.table.addMouseListener(this);

		kp.ok.addActionListener(this);
		kp.can.addActionListener(this);

		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setResizable(false);
	}

	public void connectServer(String id, String pw) {
		try {
			s = new Socket(ServerIP, 9191);
			in = new BufferedReader(new InputStreamReader(s.getInputStream()));
			out = s.getOutputStream();
			out.write((Function.LOGIN + "|" + id + "|" + pw + "\n").getBytes());
		} catch (Exception e) {
		}
		new Thread(this).start();
	}

	public static void main(String[] args) {

		// TODO Auto-generated method stub
		try {
			UIManager.setLookAndFeel("com.jtattoo.plaf.mint.MintLookAndFeel"); // look
																				// and
																				// feel
		} catch (Exception e) {
		}

		new ClientMainForm("The Great Dalmuti");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		// 아이디 저장 액션
		if (login.idSave.isSelected()) {
			File file = new File("temp/idsave.txt");
			if (file.exists()==true) {
				try {
					String[] temp = { login.tf.getText(), "true" };
					FileWriter fos = new FileWriter("temp/idsave.txt");
					fos.write(temp[0] + "|" + temp[1]);// getbyte 바이트로 바꾸기 or
														// FileWriter or
					// FileReader를 사용
					fos.close();

				} catch (Exception ex) {
					System.out.println(ex.getMessage());
				}
			} else if(file.exists()==false){
				File file1 = new File("temp/idsave.txt");
				try {
					String[] temp = { login.tf.getText(), "true" };
					FileWriter fos = new FileWriter("temp/idsave.txt");
					fos.write(temp[0] + "|" + temp[1]);// getbyte 바이트로 바꾸기 or
														// FileWriter or
					// FileReader를 사용
					fos.close();

				} catch (Exception ex) {
					System.out.println(ex.getMessage());
				}
			}
		} else {
			try {
				String[] temp = {"", "false" };
				FileWriter fos = new FileWriter("temp/idsave.txt");
				fos.write(temp[0] + "|" + temp[1]);// getbyte 바이트로 바꾸기 or
													// FileWriter or
				// FileReader를 사용
				fos.close();

			} catch (Exception ex) {
				System.out.println(ex.getMessage());
			}
		}
		if (e.getSource() == login.tf || e.getSource() == login.b1) {
			if (login.tf.getText().length() == 0) {
				JOptionPane.showMessageDialog(this, "아이디를 입력해주세요");
				return;
			}
			// 시험
		}
		if (e.getSource() == login.pf || e.getSource() == login.b1) {
			if (login.pf.getPassword().length == 0) {
				JOptionPane.showMessageDialog(this, "비밀번호를 입력해주세요");
				return;
			}
			if (e.getSource() == login.b1 || e.getSource() == login.pf) { // login
																			// 버튼
				String id = login.tf.getText();
				char[] tmp = login.pf.getPassword();
				String pw = "";
				for (int i = 0; i < tmp.length; i++) {
					pw = pw + tmp[i];
				}

				connectServer(id, pw);
			}
		} else if (e.getSource() == login.b2) { // 취소 버튼
			System.exit(0);
		}

		/*else if (e.getSource() == login.b3) { // 회원가입 버튼
			jm.setLocation(420, 200);
			jm.setVisible(true);
		}*/

		else if (e.getSource() == wr.b1) {
			if(mr.roomNum.getItemCount()==0){
				for(int i=0;i<12;i++){
					//if(wr.roomName[i].getText().equals("Empty")){
					if(wr.rp[i].roomName.getText().equals("Empty")){
						mr.roomNum.addItem(i+"번방");
					}
				}
			}
			mr.setVisible(true);
			mr.tf.setText("");
			mr.tf.requestFocus();
		}

		// 쪽지보내기
		else if (e.getSource() == wr.b2) {
			if (selRow == -1) {
				JOptionPane.showMessageDialog(this, "쪽지를 보낼 대상을 선택하세요");
				return;
			}
			String yid = wr.model2.getValueAt(selRow, 1).toString();
			sm.tf.setText(yid);
			sm.ta.setText("");
			sm.setVisible(true);
		} else if (e.getSource() == wr.b3) {
			int row = wr.table2.getSelectedRow();
			String tmp = (String) wr.table2.getValueAt(row, 1);
			try {
				out.write((Function.USERINFO + "|" + tmp + "\n").getBytes());
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			ui.setVisible(true);
		}

		else if (e.getSource() == wr.b4) {
			try {
				out.write((Function.GAMEEXIT + "|" + "\n").getBytes());
			} catch (Exception ex) {
			}
			System.exit(0);
		}

		else if (e.getSource() == wr.tf) { // 대기실 채팅
			String data = wr.tf.getText();
			if (data.length() < 1)
				return;
			wr.initStyle();
			String color = wr.box.getSelectedItem().toString();
			wr.tf.setText("");

			try {
				out.write((Function.WAITROOMCHAT + "|" + data.trim() + "|" + color.trim() + "\n").getBytes());

			} catch (Exception ex) {
			}
		}

		else if (e.getSource() == gp.opBtn4) { // 게임방 종료
			try {
				System.out.println("user위치:" + user.getPos());
				out.write((Function.ROOMOUT + "|" + user.getPos() + "\n").getBytes());
				setLocation(230, 200);
				setSize(800, 600);
				card.show(getContentPane(), "WR");
				setTitle("대기실");
			} catch (Exception ex) {
			}
		}

		else if (e.getSource() == gp.tf || e.getSource() == gp.chatBtn) { // 게임방
																			// 채팅

			String data = gp.tf.getText();
			if (data.length() < 1)
				return;
			gp.tf.setText("");
			try {
				out.write((Function.GAMECHAT + "|" + user.getPos() + "|" + data.trim() + "|" + "\n").getBytes());
			} catch (Exception ex) {
			}
		}

		else if (e.getSource() == sm.b1) {
			String id = sm.tf.getText();
			String msg = sm.ta.getText();
			if (msg.length() < 1) {
				JOptionPane.showMessageDialog(this, "쪽지 보낼 내용을 입력하세요", "알림", JOptionPane.INFORMATION_MESSAGE);
				sm.ta.requestFocus();
			} else {
				try {
					out.write((Function.MSGSEND + "|" + id + "|" + msg.replace('\n', '\t') + "\n").getBytes());
				} catch (Exception ex) {
				}
				sm.setVisible(false);
			}
		}

		else if (e.getSource() == sm.b2) { // 보내기,취소
			sm.setVisible(false);
			selRow = -1;
		}

		else if (e.getSource() == rm.b1) {
			String id = rm.tf.getText();
			sm.ta.setText("");
			sm.tf.setText(id);
			rm.setVisible(false);
			sm.setVisible(true);
		}

		else if (e.getSource() == rm.b2) {
			rm.setVisible(false);
			selRow = -1;
		}

		else if (e.getSource() == mr.b1) {
			String tmp = mr.roomNum.getSelectedItem().toString();
			char rNum = tmp.charAt(0);

			String rname = mr.tf.getText().trim();
			if (rname.length() < 1) {
				JOptionPane.showMessageDialog(this, "방이름을 입력하세요");
				mr.tf.requestFocus();
				return;
			}
			String state = "", pwd = "";
			if (mr.open.isSelected()) {
				state = "공개";
				pwd = " ";
			} else {
				state = "비공개";
				pwd = String.valueOf(mr.pf.getPassword());
			}
			int numMember = mr.box.getSelectedIndex() + 5;
			try {
				out.write((Function.MAKEROOM + "|" + rNum + "|" + rname + "|" + state + "|" + pwd + "|" + numMember
						+ "\n").getBytes());
			} catch (Exception ex) {
			}
			mr.dispose();
			mr.roomNum.removeAllItems();
		}

		else if (e.getSource() == mr.b2) {
			mr.tf.setText("");
			mr.setVisible(false);
			mr.roomNum.removeAllItems();
		}

		// 확인 버튼시
/*		else if (e.getSource() == jm.btnInsert) {
			member.setId(jm.tfId.getText());
			member.setName(jm.tfName.getText());
			member.setNickName(jm.tfNickName.getText());
			member.setPwd(jm.pfPwd1.getPassword());
			member.setTel(jm.tfTel1.getText() + jm.tfTel2.getText() + jm.tfTel3.getText());
			member.setGen(String.valueOf(jm.Gender));
			member.setAvatar(jm.Avatar);
			try {
				s = new Socket(ServerIP, 9191);
				in = new BufferedReader(new InputStreamReader(s.getInputStream()));
				out = s.getOutputStream();
				out.write((Function.JOIN + "|" + member.toString() + "\n").getBytes());
			} catch (Exception e1) {
			}
			new Thread(this).start();
		} else if (e.getSource() == jm.btnCheckId) {
			if (jm.tfId.equals("")) {
				JOptionPane.showMessageDialog(this, "ID 값을 입력 하세요");
				jm.tfId.requestFocusInWindow();
			} else {
				if (jm.idCheck) {
					JOptionPane.showMessageDialog(this, "아이디는 4자리 이상 입력 하세요");
					jm.tfId.setText("");
					jm.tfId.requestFocusInWindow();
				} else {
					try {
						s = new Socket(ServerIP, 9191);
						in = new BufferedReader(new InputStreamReader(s.getInputStream()));
						out = s.getOutputStream();
						out.write((Function.IDCHECK + "|" + jm.tfId.getText() + "\n").getBytes());
					} catch (Exception e1) {
					}
					new Thread(this).start();
				}
			}
		}
		// 취소 버튼시
		else if (e.getSource() == jm.btnCancel) {
			jm.dispose(); // 창닫기 (현재창만 닫힘)
		}*/

		else if (e.getSource() == gp.readyBtn) {
			if (gp.playList.size() != 4) {
				JOptionPane.showMessageDialog(this, "인원이 모두 입장할 때 까지 대기하셔야 합니다.");
				return;
			}
			gp.readyBtn.setEnabled(false);
			try {
				out.write((Function.GAMEREADY + "|" + user.getPos() + "\n").getBytes());
			} catch (Exception ex) {
			}
		}

		else if (e.getSource() == gp.startBtn) {
			try {
				out.write((Function.HOSTSTARTBUTTON + "|" + user.getPos() + "\n").getBytes());
			} catch (Exception ex) {
			}
		}

		else if (e.getSource() == gp.summitBtn) {
			Vector<Card> dummy = new Vector<Card>();
			String isEmpty = "no";
			int[] pCard = new int[3];
			if (!gp.checkCardRule()) {
				return;
			}
			for (int i = 0; i < GamePlay.havingCard.size(); i++) {
				if (GamePlay.havingCard.get(i).calledCheck == true) {
					GamePlay.havingCard.get(i).setVisible(false);
					dummy.add(GamePlay.havingCard.remove(i));
					i--;
				}
			}
			for (int i = 0; i < GamePlay.havingCard.size(); i++) {
				GamePlay.havingCard.get(i).calledCheck = false;
				GamePlay.havingCard.get(i).setEnabled(true);
			}
			int jcount = 0;
			for (int i = 0; i < dummy.size(); i++) {
				if (dummy.get(i).cardNumber == 13) {
					jcount++;
				} else
					pCard[0] = dummy.get(i).cardNumber;
			}
			pCard[1] = dummy.size();
			pCard[2] = jcount;

			if (dummy.isEmpty()) {
				JOptionPane.showMessageDialog(this, "한장 이상의 카드를 선택해야 제출이 가능합니다");
				return;
			}
			gp.setCardBounds();
			dummy.removeAllElements();
			GamePlay.selectedCard.removeAllElements();
			gp.jp.setVisible(false);
			gp.chkThread = true;
			gp.controlButton(1);
			gp.controlCard(0);

			try {
				System.out.println("Cards :" + GamePlay.havingCard.size());
				if (GamePlay.havingCard.isEmpty()) {
					isEmpty = "yes";
					out.write((Function.SUBMITPRESS + "|" + user.getPos() + "|" + pCard[0] + "|" + pCard[1] + "|"
							+ pCard[2] + "|" + isEmpty + "\n").getBytes());
				} else {
					out.write((Function.SUBMITPRESS + "|" + user.getPos() + "|" + pCard[0] + "|" + pCard[1] + "|"
							+ pCard[2] + "|" + isEmpty + "\n").getBytes());

				}

			} catch (Exception ex) {
			}

		}

		else if (e.getSource() == gp.passBtn) {
			gp.controlPassBtn();
			for (Card card : GamePlay.havingCard) {
				if (card.calledCheck) {
					JOptionPane.showMessageDialog(this, "제출 상태인 카드를 원상복귀 하셔야 합니다.");
					return;
				}
			}

			int[] pCard = new int[3];
			/*
			 * for (int i = 0; i < GamePlay.havingCard.size(); i++) {
			 * GamePlay.havingCard.get(i).setEnabled(false); }
			 */
			gp.passBtn.setEnabled(false);
			gp.summitBtn.setEnabled(false);
			gp.jp.setVisible(false);
			gp.chkThread = true;
			pCard[0] = GamePlay.pastCard[0];
			pCard[1] = GamePlay.pastCard[1];
			pCard[2] = GamePlay.pastCard[2];
			gp.controlButton(1);
			gp.controlCard(0);
			gp.setCardBounds();

			try {
				out.write((Function.PASSPRESS + "|" + user.getPos() + "|" + String.valueOf(pCard[0]) + "|"
						+ String.valueOf(pCard[1]) + "|" + String.valueOf(pCard[2]) + "\n").getBytes());

			} catch (Exception ex) {
			}

		} else if (e.getSource() == pc.bu[0]) {
			ImageIcon icon = new ImageIcon(
					getClass().getResource("/image/card" + String.valueOf(pc.secret[0] + 1) + ".jpg"));
			pc.bu[0].setIcon(icon);
			for (int i = 0; i < 5; i++) {
				pc.bu[i].setEnabled(true);
			}
			pc.setEnabled(false);
			try {
				out.write((Function.PRIORITYTURN + "|" + user.getPos() + "|" + user.getId() + "|"
						+ String.valueOf(pc.secret[0]) + "\n").getBytes());
			} catch (Exception ex) {
			}

		} else if (e.getSource() == pc.bu[1]) {
			ImageIcon icon = new ImageIcon(
					getClass().getResource("/image/card" + String.valueOf(pc.secret[1] + 1) + ".jpg"));
			pc.bu[1].setIcon(icon);
			for (int i = 0; i < 5; i++) {
				pc.bu[i].setEnabled(true);
			}
			pc.setEnabled(false);

			try {
				out.write((Function.PRIORITYTURN + "|" + user.getPos() + "|" + user.getId() + "|"
						+ String.valueOf(pc.secret[1]) + "\n").getBytes());
			} catch (Exception ex) {
			}

		} else if (e.getSource() == pc.bu[2]) {
			ImageIcon icon = new ImageIcon(
					getClass().getResource("/image/card" + String.valueOf(pc.secret[2] + 1) + ".jpg"));
			pc.bu[2].setIcon(icon);
			for (int i = 0; i < 5; i++) {
				pc.bu[i].setEnabled(true);

			}
			pc.setEnabled(false);
			try {
				out.write((Function.PRIORITYTURN + "|" + user.getPos() + "|" + user.getId() + "|"
						+ String.valueOf(pc.secret[2]) + "\n").getBytes());
			} catch (Exception ex) {
			}

		} else if (e.getSource() == pc.bu[3]) {
			ImageIcon icon = new ImageIcon(
					getClass().getResource("/image/card" + String.valueOf(pc.secret[3] + 1) + ".jpg"));
			pc.bu[3].setIcon(icon);
			for (int i = 0; i < 5; i++) {
				pc.bu[i].setEnabled(true);
			}
			pc.setEnabled(false);
			try {
				out.write((Function.PRIORITYTURN + "|" + user.getPos() + "|" + user.getId() + "|"
						+ String.valueOf(pc.secret[3]) + "\n").getBytes());
			} catch (Exception ex) {
			}

		} else if (e.getSource() == pc.bu[4]) {
			ImageIcon icon = new ImageIcon(
					getClass().getResource("/image/card" + String.valueOf(pc.secret[4] + 1) + ".jpg"));
			pc.bu[4].setIcon(icon);
			for (int i = 0; i < 5; i++) {
				pc.bu[i].setEnabled(true);
			}
			pc.setEnabled(false);
			try {
				out.write((Function.PRIORITYTURN + "|" + user.getPos() + "|" + user.getId() + "|"
						+ String.valueOf(pc.secret[4]) + "\n").getBytes());
			} catch (Exception ex) {
			}

		} else if (e.getSource() == pc.okBtn) {
			pc.setVisible(false);
			pc.dispose();
			this.setEnabled(true);
			this.setOpacity(1.f);
		}

		else if (e.getSource() == gp.opBtn2) {
			for (int i = iv.model.getRowCount() - 1; i >= 0; i--) {
				iv.model.removeRow(i);
			}
			iv.box.removeAllItems();
			for (int i = 0; i < wr.model2.getRowCount(); i++) {
				ImageIcon level = (ImageIcon) wr.model2.getValueAt(i, 0);
				String id = wr.model2.getValueAt(i, 1).toString();
				String nick = wr.model2.getValueAt(i, 2).toString();
				String pos = wr.model2.getValueAt(i, 4).toString();
				if (pos.equals("대기실")) {
					Object[] temp = { level, id, nick, pos };
				}
			}
			for (int i = iv.box.getItemCount() - 1; i >= 0; i--) {
				iv.box.remove(i);
			}
			for (int i = 0; i < wr.model2.getRowCount(); i++) {
				ImageIcon level = (ImageIcon) wr.model2.getValueAt(i, 0);
				String id = wr.model2.getValueAt(i, 1).toString();
				String nick = wr.model2.getValueAt(i, 2).toString();
				String pos = wr.model2.getValueAt(i, 4).toString();
				if (pos.equals("대기실")) {
					Object[] temp = { level, id, nick, pos };
					iv.model.addRow(temp);
					iv.box.addItem(id);
				}
			}
			iv.setVisible(true);

		} else if (e.getSource() == iv.canBtn) {
			iv.setVisible(false);
		} else if (e.getSource() == iv.inBtn) {
			String id = iv.box.getSelectedItem().toString();
			try {
				out.write((Function.INVITE + "|" + id + "\n").getBytes());
			} catch (Exception ex) {
			}
			iv.setVisible(false);
		} else if (e.getSource() == gp.opBtn3) {
			kp.setVisible(true);
			kp.t.removeAllItems();
			for (int i = 0; i < gp.playList.size(); i++) {
				kp.t.addItem(gp.playList.elementAt(i).dto.getNickname());
			}
		} else if (e.getSource() == kp.can) {
			kp.setVisible(false);
		} else if (e.getSource() == kp.ok) {
			String kick = kp.t.getSelectedItem().toString();
			try {
				out.write((Function.KICKVOTE + "|" + user.getPos() + "|" + user.getNickname() + "|" + kick + "\n")
						.getBytes());
			} catch (Exception ex) {
			}
			kp.setVisible(false);
		}


		for(int i=0;i<12;i++){
			if(e.getSource()==wr.rp[i].roomBtn){
				String tmp = String.valueOf(wr.rp[i].numCnt);
				String pass=" ";
				if(wr.rp[i].isClose==true){
					pass=JOptionPane.showInputDialog("비밀번호를 입력하세요.");
				}	else {
					pass = " ";

				}
				if (pass.length() < 1) {
					JOptionPane.showMessageDialog(this, "비밀번호를 입력하세요");
					break;
				}		
				if(wr.rp[i].numCnt == 0){
					mr.roomNum.addItem(i+"번방");
					wr.b1.doClick();
					break;
				}
				else if (wr.rp[i].numCnt == 5) {
					JOptionPane.showMessageDialog(this, "이미 방이 찼습니다");
					break;
				}

				try {
					user.setPos(i);
					out.write((Function.ROOMIN + "|" + i + "|" + pass + "\n").getBytes());
				} catch (Exception ex) {
				}
			}
		}

	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			while (true) {
				String msg = in.readLine();
				System.out.println(msg);
				StringTokenizer st = new StringTokenizer(msg, "|");
				int no = Integer.parseInt(st.nextToken());
				switch (no) {
				case Function.LOGIN: {
					Object[] tmp = { new ImageIcon(getClass().getResource("/image/rank" + st.nextToken() + ".png")),
							st.nextToken(), st.nextToken(), st.nextToken(), st.nextToken() };
					wr.model2.addRow(tmp);
					setLocation(230, 200);
					setSize(800, 600);
					card.show(getContentPane(), "WR");
					setTitle("대기실");
					break;
				}

				case Function.LOGINFAILED: {
					JOptionPane.showMessageDialog(this, st.nextToken());
					break;
				}

				case Function.OTHERLOGIN: {
					Object[] tmp = { new ImageIcon(getClass().getResource("/image/rank" + st.nextToken() + ".png")),
							st.nextToken(), st.nextToken(), st.nextToken(), st.nextToken() };
					wr.model2.addRow(tmp);
					break;
				}
				case Function.MYINFO: {
					user.setId(st.nextToken());
					user.setNickname(st.nextToken());
					user.setGender(st.nextToken());
					user.setPos(Integer.parseInt(st.nextToken()));
					user.setAvatar(st.nextToken());
					user.setLevel(Integer.parseInt(st.nextToken()));
					user.setPoint(Integer.parseInt(st.nextToken()));
					break;
				}
				/*case Function.IDCHECK: {
					if (!Boolean.parseBoolean(st.nextToken())) {
						JOptionPane.showMessageDialog(this, "사용 가능한 아이디 입니다.");
						jm.pfPwd1.setEnabled(true);
						jm.pfPwd1.requestFocusInWindow();
					} else {
						JOptionPane.showMessageDialog(this, "중복된 아이디 입니다");
						jm.tfId.requestFocusInWindow();
					}
					break;
				}
				case Function.JOIN: {
					JOptionPane.showMessageDialog(this, st.nextToken());
					jm.dispose();
					break;
				}*/
				case Function.GAMEEXIT: {
					Object[] tmp = { st.nextToken(), st.nextToken(), st.nextToken(), st.nextToken() };
					for (int i = 0; i < wr.model2.getRowCount(); i++) {
						if (wr.model2.getValueAt(i, 1).equals(tmp[1])) {
							wr.model2.removeRow(i);
						}
					}
					break;
				}
				case Function.WAITROOMCHAT: {
					String[] tmp = { st.nextToken(), st.nextToken() };
					wr.append(tmp[0], tmp[1]);
					break;
				}
				case Function.GAMECHAT: {
					String[] tmp = { st.nextToken(), st.nextToken() };
					gp.ta.append(tmp[0] + "\n");
					gp.ta.setSelectionStart(gp.ta.getText().length());
					break;

				}
				case Function.MSGSEND: {
					rm.ta.setText("");
					rm.tf.setText(st.nextToken());
					rm.ta.setText(st.nextToken().replace('\t', '\n'));
					rm.setVisible(true);
					break;
				}
				case Function.MAKEROOM: {
					int rNum = Integer.parseInt(st.nextToken());
					String roomName = st.nextToken();
					String roomState = st.nextToken();
					String rCnt = st.nextToken();

					int userNum = Integer.parseInt(rCnt.substring(0,1));
					
					for(int i=0;i<6;i++){
						if(i==userNum){
							wr.rp[rNum].roomMember.setIcon(roomMember[i]);
						}
					}
					if(userNum!=0){
						if(roomState.equals("비공개")){
							wr.rp[rNum].roomBtn.setIcon(closeRoom);
							wr.rp[rNum].isClose = true;
						}
						else{
							wr.rp[rNum].roomBtn.setIcon(openRoom);
						}
					}
					wr.repaint();


					//wr.rp[rNum].roomMember.setText(rCnt);
					wr.rp[rNum].numCnt = userNum;
					wr.rp[rNum].roomName.setText(roomName);

					break;
				}
				case Function.USERINFO: {
					String[] tmp = { st.nextToken(), st.nextToken(), st.nextToken(), st.nextToken(), st.nextToken(),
							st.nextToken(), st.nextToken() };
					if(Integer.parseInt(tmp[5])>5){
						tmp[5]="5";
					}
					ui.IdField.setText(tmp[0]);
					ui.NickField.setText(tmp[1]);
					ui.PointField.setText(tmp[6]);
					ui.setLabelImage(tmp[2] + tmp[4], tmp[5]);
					ui.repaint();
					ui.setVisible(true);
					break;
				}
				case Function.UPDATEPOINT : {
					Object[] tmp = { new ImageIcon(getClass().getResource("/image/rank" + st.nextToken() + ".png")),
							st.nextToken(), st.nextToken(), st.nextToken(), st.nextToken() };
					for (int i = 0; i < wr.model2.getRowCount(); i++) {
						String str = wr.model2.getValueAt(i, 1).toString();
						if (str.equals(tmp[1].toString())) {
							wr.model2.removeRow(i);
							wr.model2.addRow(tmp);
							break;
							
						}
					}
					break;
				}
				case Function.WAITROOMUPDATE: {
					int rNum = Integer.parseInt(st.nextToken());
					String current = st.nextToken();
					String max = st.nextToken();
					String temp = "";
					if (Integer.parseInt(current) == 0) {

						wr.rp[rNum].roomBtn.setIcon(roomEmp);
						wr.repaint();
						wr.rp[rNum].numCnt =0;
						//wr.rp[rNum].roomMember.setText(current+"/"+max);
						wr.rp[rNum].roomMember.setIcon(roomMember[Integer.parseInt(current)]);
						wr.rp[rNum].roomName.setText("Empty");

						// break;
					} else {
						wr.rp[rNum].roomBtn.setIcon(openRoom);
						wr.repaint();
						wr.rp[rNum].numCnt = Integer.parseInt(current);
						//wr.rp[rNum].roomMember.setText(current+"/"+max);
						wr.rp[rNum].roomMember.setIcon(roomMember[Integer.parseInt(current)]);

					}
					break;
				}
				case Function.ROOMIN: {
					user.setPos(Integer.parseInt(st.nextToken()));
					setLocation(100, 50);
					setSize(1100, 900);
					card.show(getContentPane(), "GP");
					setTitle(user.getPos() + "번 방");
					break;
				}

				case Function.ROOMADD: {

					// 개체 직렬화
					// UserDTO dto;
					UserDTO dto = new UserDTO();
					dto.setId(st.nextToken());
					dto.setNickname(st.nextToken());
					dto.setGender(st.nextToken());
					dto.setPos(Integer.parseInt(st.nextToken()));
					dto.setAvatar(st.nextToken());
					dto.setLevel(Integer.parseInt(st.nextToken()));
					dto.setPoint(Integer.parseInt(st.nextToken()));
					// dto.myTurn = gp.playList.size()-1;
					Player p = new Player(dto);

					gp.playList.addElement(p);
					gp.validate();
					gp.setPlayLayout();
					gp.repaint();
					System.out.println("ROOMADD 종료");
					break;
				}

				case Function.ROOMOUT: {
					String id = st.nextToken();

					for (int i = 0; i < gp.playList.size(); i++) {
						if (gp.playList.get(i).dto.getId().equals(id)) {
							gp.playList.get(i).removeAll();
							gp.playList.get(i).validate();
							gp.remove(gp.playList.get(i));
							gp.playList.remove(i);
							gp.validate();
						} else {
							gp.playList.get(i).returnToAvatarImg();
							gp.playList.get(i).repaint();
						}

					}
					gp.repaint();
					gp.controlButton(2);
					for (int i = 0; i < 5; i++) {
						pc.bu[i].setIcon(pc.icon);
						pc.tf[i].setText(" ");
						pc.selectedCard[i] = null;
					}
					pc.okBtn.setEnabled(false);
					break;
				}
				case Function.MYROOMOUT: {
					for (int i = gp.playList.size() - 1; i >= 0; i--) {
						gp.playList.get(i).removeAll();
						gp.playList.get(i).validate();
						gp.remove(gp.playList.get(i));
						gp.playList.remove(i);
						gp.validate();
					}
					gp.controlButton(2);
					gp.cardIndex.removeAllElements();
					GamePlay.havingCard.removeAllElements();
					GamePlay.selectedCard.removeAllElements();
					GamePlay.pastCard[0] = -1;
					GamePlay.pastCard[1] = 1;
					GamePlay.pastCard[2] = 0;
					gp.setPastCard(-1, 1, 0);
					for(int i=gp.model.getRowCount()-1 ; i>=0;i--){
						gp.model.removeRow(i);
					}
					gp.ta.setText("");
					gp.tf.setText("");
					for (int i = 0; i < 5; i++) {
						pc.bu[i].setIcon(pc.icon);
						pc.tf[i].setText(" ");
						pc.selectedCard[i] = null;
					}
					pc.okBtn.setEnabled(false);
					gp.repaint();
					setLocation(230, 200);
					setSize(800, 600);
					card.show(getContentPane(), "WR");
					setTitle("대기실");
					break;
				}
				case Function.INVITE: {
					String id = st.nextToken();
					String pos = st.nextToken();
					String pwd = "";
					this.setEnabled(false);
					this.setOpacity(0.2f);
					int a = JOptionPane.showConfirmDialog(this, id + "님이 " + pos + "번 방에 초대하였습니다", "초대",
							JOptionPane.YES_NO_OPTION);
					if (a == JOptionPane.YES_OPTION) {

						//비방일때 문제 발생
						if(wr.rp[Integer.parseInt(pos)].isClose==true){
							pwd=JOptionPane.showInputDialog("비밀번호를 입력하세요.");
						}
						else{
							pwd = " ";
						}
						this.setEnabled(true);
						this.setOpacity(1.0f);
						if(wr.rp[Integer.parseInt(pos)].numCnt !=5){
							out.write((Function.ROOMIN + "|" + pos +"|"+pwd+ "\n").getBytes());
						}
						else{
							JOptionPane.showMessageDialog(this, "이미 방이 찼습니다");
						}
					} else {
						this.setEnabled(true);
						this.setOpacity(1.0f);
						out.write((Function.INVITENO + "|" + id + "\n").getBytes());
					}

					break;
				}
				case Function.INVITENO: {
					String id = st.nextToken();
					JOptionPane.showMessageDialog(this, id + "님이 초대를 거절하셨습니다");
					break;
				}

				case Function.USERINFOREF: {
					String id = st.nextToken();
					String pos = st.nextToken();
					String str = "";
					for (int i = 0; i < wr.model2.getRowCount(); i++) {
						str = wr.model2.getValueAt(i, 1).toString();
						if (str.equals(id)) {
							if (Integer.parseInt(pos) != -1) {
								wr.model2.setValueAt(pos + "번방", i, 4);
								break;
							} else {
								wr.model2.setValueAt("대기실", i, 4);
								break;
							}
						}
					}
					break;
				}
				case Function.HOSTSTARTBUTTON: {
					gp.controlButton(3);
					JOptionPane.showMessageDialog(this, "게임을 시작합니다. START 버튼을 눌러주세요.");
					break;
				}
				case Function.PRIORITYGAME: {
					for (int i = 0; i < 5; i++) {
						pc.secret[i] = Integer.parseInt(st.nextToken());
						pc.bu[i].setEnabled(true);
					}
					pc.setVisible(true);
					pc.setEnabled(false);
					this.setEnabled(false);
					this.setOpacity(0.2f);
					break;
				}

				case Function.GAMESTART: {
					gp.opBtn4.setEnabled(false);
					gp.opBtn2.setEnabled(false);
					gp.opBtn3.setEnabled(false);
					gp.controlButton(1);
					for (int i = gp.model.getRowCount() - 1; i >= 0; i--) {
						gp.model.removeRow(i);
					}
					break;
				}
				case Function.CARDLIST: {
					int cnt = 0;
					while (st.hasMoreTokens()) {
						gp.cardIndex.add(Integer.parseInt(st.nextToken()));
					}
					gp.setCardLayout();
					gp.controlCard(0);
					break;
				}

				case Function.SUBMITCARDNUMBER: {

					String pastID = st.nextToken();
					int pCard2 = Integer.parseInt(st.nextToken());
					for (Player player : gp.playList) {
						if (player.dto.getId().equals(pastID)) {
							player.dto.CardNumber -= pCard2;
							player.avata.setToolTipText(String.valueOf(player.dto.CardNumber) + "장 남음");
						}
					}
					break;
				}

				case Function.PRIORITYTURN: {
					String thisTurn = st.nextToken();
					int num = Integer.parseInt(st.nextToken());
					String pUser = st.nextToken();

					pc.line.setText(thisTurn + "님이 선택할 차례입니다.");

					if (num >= 0) {
						for (int i = 0; i < 5; i++) {
							if (pc.secret[i] == num) {
								pc.selectedCard[i] = pUser;
								pc.tf[i].setText(pUser);
								ImageIcon icon = new ImageIcon(getClass()
										.getResource("/image/card" + String.valueOf(pc.secret[i] + 1) + ".jpg"));
								pc.bu[i].setIcon(icon);
							}
						}
					}
					if (user.getId().equals(thisTurn)) {
						for (int i = 0; i < 5; i++) {
							if (pc.selectedCard[i] != null) {
								pc.bu[i].setEnabled(false);

							}

						}
						pc.setEnabled(true);
					}
					break;
				}

				case Function.PRIORITYFINISH: {
					int num = Integer.parseInt(st.nextToken());
					String pUser = st.nextToken();
					pc.line.setText("모든 선택이 완료되었습니다.");
					for (int i = 0; i < 5; i++) {
						if (pc.secret[i] == num) {
							pc.selectedCard[i] = pUser;
							pc.tf[i].setText(pUser);
							ImageIcon icon = new ImageIcon(
									getClass().getResource("/image/card" + String.valueOf(pc.secret[i] + 1) + ".jpg"));
							pc.bu[i].setIcon(icon);
						}
					}
					pc.setEnabled(true);
					pc.okBtn.setEnabled(true);
					for (int i = 0; i < 5; i++) {
						pc.bu[i].setEnabled(false);
					}
					break;
				}

				case Function.GAMEPLAY: {
					String thisTurn = st.nextToken();
					GamePlay.pastCard[0] = Integer.parseInt(st.nextToken());
					GamePlay.pastCard[1] = Integer.parseInt(st.nextToken());
					GamePlay.pastCard[2] = Integer.parseInt(st.nextToken());

					if (user.getId().equals(thisTurn)) {
						gp.setPastCard(GamePlay.pastCard[0], GamePlay.pastCard[1], GamePlay.pastCard[2]);
						gp.passBtn.setEnabled(true);
						gp.summitBtn.setEnabled(true);
						gp.controlCard(1);
						gp.setTimer();
						for (int i = 0; i < gp.playList.size(); i++) {
							gp.playList.get(i).myTurn.setVisible(false);
							gp.playList.get(i).validate();
							gp.playList.get(i).repaint();
						}
						
					} else {
						gp.setPastCard(GamePlay.pastCard[0], GamePlay.pastCard[1], GamePlay.pastCard[2]);
						for (int i = 0; i < gp.playList.size(); i++) {
							if (gp.playList.get(i).dto.getId().equals(thisTurn)) {
								gp.playList.get(i).myTurn.setVisible(true);								
							} else {
								gp.playList.get(i).myTurn.setVisible(false);
							}
							gp.playList.get(i).validate();
							gp.playList.get(i).repaint();
						}
					}
					gp.validate();
					gp.repaint();
					break;
				}
				case Function.ROOMPOINTUPDATE : {
					String uId = st.nextToken();
					String ulvl = st.nextToken();
					for(int i=0; i< gp.playList.size();i++){
						if(gp.playList.get(i).dto.getId().equals(uId)){
							gp.playList.get(i).pGrade.setIcon((new ImageIcon(SetImager.setImage(
									getClass().getResource("/image/rank" +ulvl  + ".png"),15, 15))));
							gp.playList.get(i).validate();
							gp.playList.get(i).repaint();
						}
					}
					gp.validate();
					gp.repaint();
					break;
					
				}
				case Function.AVATARCHANGE: {
					Map<String, Integer> resultRank = new HashMap<String, Integer>();
					while (st.hasMoreTokens()) {
						resultRank.put(st.nextToken(), Integer.parseInt(st.nextToken()));
					}
					user.originRank = user.myTurn;
					user.myTurn = resultRank.get(user.getId());
					System.out.println("OringRank:" + user.originRank + "MyTurn:" + user.myTurn);

					for (Player player : gp.playList) {
						int Rank = resultRank.get(player.dto.getId());
						System.out.println("Rank :" + Rank);
						player.dto.myTurn = Rank;

						player.avata.setIcon(new ImageIcon(SetImager.setImage(
								getClass().getResource(
										"/image/" + Rank + player.dto.getGender() + player.dto.getAvatar() + ".png"),
								150, 230)));
						player.avata.repaint();
					}
					break;
				}

				case Function.PLAYFINISH: {
					if (!GamePlay.havingCard.isEmpty()) {
						for (Card removeCard : GamePlay.havingCard) {
							removeCard.calledCheck = false;
							removeCard.setVisible(false);
						}
						GamePlay.havingCard.removeAllElements();
					}

					for (Player p : gp.playList) {
						p.dto.CardNumber = 16;
					}

					ResultForm rf;
					rf = new ResultForm(String.valueOf(user.getPoint()), String.valueOf(8 - (user.myTurn * 2)));
					user.setPoint(user.getPoint() + 8 - (user.myTurn * 2));
					if (user.getPoint() / 10 > user.getLevel()) {
						JOptionPane.showMessageDialog(this, "레벨업 하셨습니다.!");
						user.setLevel(user.getPoint() / 10);
					}
					try {
						out.write((Function.UPDATEPOINT + "|" + user.getLevel() + "|" + user.getPoint() + "\n")
								.getBytes());
					} catch (Exception ex) {
					}
					
					System.out.println("원래턴:" + user.originRank + "결정된턴:" + user.myTurn);
					rf.avatarLb = new ResultForm.AvatarPanel(user.originRank + user.getGender() + user.getAvatar(),
							user.myTurn + user.getGender() + user.getAvatar());
					rf.add(rf.avatarLb);
					rf.avatarLb.setBounds(125, 30, 150, 250);
					rf.setLocation(250, 250);

					System.out.println("PLAYFINISH :" + user.getId());
					gp.gameSet = true;
					gp.chkThread = false;
					gp.jp.setVisible(false);
					gp.cardIndex.clear();
					GamePlay.pastCard[0] = -1;
					GamePlay.pastCard[1] = 1;
					GamePlay.pastCard[2] = 0;

					gp.controlButton(2);
					gp.opBtn4.setEnabled(true);
					gp.opBtn3.setEnabled(true);
					gp.opBtn2.setEnabled(true);
					gp.readyBtn.setEnabled(true);

					break;
				}

				case Function.PLAYEREMPTY: {
					String nick = st.nextToken();
					String cnt = String.valueOf(gp.model.getRowCount() + 1);

					Object[] data = { "      " + cnt,
							new ImageIcon(getClass().getResource("/image/rank" + st.nextToken() + ".png")), nick };
					gp.model.addRow(data);

					break;
				}
				case Function.KICKVOTE: {
					String id = st.nextToken();
					String kick = st.nextToken();
					int tmp = JOptionPane.showConfirmDialog(null, id + "님이 " + kick + "님에 대한 추방 투표를 시작했습니다.", "추방 투표",
							JOptionPane.YES_NO_OPTION);
					out.write((Function.KICKRESULT + "|" + user.getPos() + "|" + kick + "|" + tmp + "\n").getBytes());
					break;
				}
				case Function.KICKRESULT: {
					String id = st.nextToken();
					String res = st.nextToken();
					if (res.equals("yes")) {
						JOptionPane.showMessageDialog(this, id + "님이 추방당하였습니다.");
					} else {
						JOptionPane.showMessageDialog(this, id + "님 추방투표가 부결되었습니다.");
					}
					break;
				}
				case Function.KICKOUT: {
					gp.opBtn4.doClick();
					break;
				}

				case Function.PASSWORDERROR: {
					JOptionPane.showMessageDialog(this, "비밀번호가 틀렸습니다.");
					break;
				}

				}
			}
		} catch (Exception e) {
			e.getStackTrace();
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == wr.table2) {
			int row = wr.table2.getSelectedRow();
			selRow = row;
			String id = wr.model2.getValueAt(row, 1).toString();
			if (user.getId().equals(id)) {
				wr.b2.setEnabled(false);
				wr.b3.setEnabled(false);

			} else {
				wr.b2.setEnabled(true);
				wr.b3.setEnabled(true);
			}
		} else if (e.getSource() == iv.table) {
			if (e.getClickCount() == 2) {
				int row = iv.table.getSelectedRow();
				String id = iv.model.getValueAt(row, 1).toString();
				try {
					out.write((Function.INVITE + "|" + id + "\n").getBytes());
				} catch (Exception ex) {
				}

				iv.setVisible(false);
			}
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}