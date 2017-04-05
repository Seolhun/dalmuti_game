package com.sist.client;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.net.*;
import java.util.StringTokenizer;

import com.sist.common.Function;
import com.sist.common.MemberDTO;
import com.sist.common.UserDTO;

/*
 * Login 화면 클래스
 */
public class Login extends JPanel implements ActionListener, Runnable {
	JoinMember jm = new JoinMember();
	JLabel la1, la2, la3;
	JTextField tf;
	JPasswordField pf;
	JButton b1, b2, b3;
	Image img;
	JRadioButton idSave;
	Thread t;
	MemberDTO member = new MemberDTO();

	Socket s;
	OutputStream out;
	BufferedReader in;

	boolean jmcheck = true;


	String ServerIP = "211.238.142.37";
	
	public Login() {
		img = Toolkit.getDefaultToolkit().getImage(Login.class.getResource("/image/back2.jpg"));

		setLayout(null);// FlowLayout
		la1 = new JLabel("아이디");
		la2 = new JLabel("패스워드");
		la3 = new JLabel("아직 아이디가 없으신가요?");
		tf = new JTextField(); // 아이디어 입력 칸
		pf = new JPasswordField(); // 비밀번호 입력 칸
		b1 = new JButton("로그인");
		b2 = new JButton("나가기");
		b3 = new JButton("회원가입");
		idSave = new JRadioButton("아이디 저장");
		b1.setFont(new Font("나눔스퀘어", Font.PLAIN, 12));
		b2.setFont(new Font("나눔스퀘어", Font.PLAIN, 12));
		b3.setFont(new Font("나눔스퀘어", Font.PLAIN, 12));

		// 배치

		la1.setBounds(50, 155, 40, 30);
		la1.setForeground(Color.black);
		la1.setFont(new Font("나눔스퀘어", Font.BOLD, 12));
		// 아이ㅣ디
		idSave.setBounds(192, 280, 100, 30);
		idSave.setFont(new Font("나눔스퀘어", Font.PLAIN, 12));
		idSave.setOpaque(false);

		tf.setBounds(50, 185, 230, 30);
		tf.setOpaque(false);

		tf.setFont(new Font("나눔스퀘어", Font.PLAIN, 12));
		la2.setBounds(50, 220, 60, 30);
		la2.setForeground(Color.black);
		la2.setFont(new Font("나눔스퀘어", Font.BOLD, 12));

		pf.setBounds(50, 250, 230, 30);
		pf.setOpaque(false);

		JPanel p = new JPanel();
		p.add(b1);
		p.add(b2);
		p.setBounds(50, 315, 220, 35);

		la3.setBounds(60, 360, 180, 30);
		la3.setForeground(Color.white);
		la3.setFont(new Font("나눔스퀘어", Font.ITALIC, 12));

		b3.setBounds(210, 365, 68, 20);
		b3.setBorderPainted(false);

		p.setOpaque(false);
		add(la1);
		add(tf);
		add(idSave);
		add(la2);
		add(pf);
		add(p);
		add(la3);
		add(b3);

		b3.addActionListener(this);
		jm.btnCheckId.addActionListener(this);
		jm.btnCancel.addActionListener(this);
		jm.btnInsert.addActionListener(this);

		// 액션처리
	}

	@Override
	protected void paintComponent(Graphics g) {
		g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == b3) { // 회원가입 버튼
			jm.setLocation(420, 200);
			jm.setVisible(true);
			// 실행시 마다 초기화
			jm.tfId.setText("");
			jm.pfPwd1.setText("");
			jm.pfPwd2.setText("");
			jm.tfNickName.setText("");
			jm.tfTel1.setText("");
			jm.tfTel2.setText("");
			jm.tfTel3.setText("");
			jm.tfName.setText("");
			jm.pfPwd1.setEnabled(false);
			jm.pfPwd2.setEnabled(false);
			jm.tfName.setEnabled(false);
			jm.tfNickName.setEnabled(false);
			jm.tfTel1.setEnabled(false);
			jm.tfTel2.setEnabled(false);
			jm.tfTel3.setEnabled(false);
			jm.btnInsert.setEnabled(false);
			try {
				s = new Socket(ServerIP, 9191);
				in = new BufferedReader(new InputStreamReader(s.getInputStream()));
				out = s.getOutputStream();
			} catch (Exception e1) {
			}
			t = new Thread(this);
			t.start();

			// System.out.println(t.getName());

		} // 취소 버튼시
		else if (e.getSource() == jm.btnCancel) {
			jm.dispose(); // 창닫기 (현재창만 닫힘)
			try{
				out.write((Function.JOINCANCEL+"|\n").getBytes());
			}catch(Exception ex){}
			

		} // 아이디 체크 이벤트
		else if (e.getSource() == jm.btnCheckId) {
			if (jm.tfId.getText().equals("")) {
				JOptionPane.showMessageDialog(this, "ID 값을 입력 하세요");
				jm.tfId.requestFocusInWindow();
			} else if (jm.tfId.getText().length() < 4) {
				JOptionPane.showMessageDialog(this, "ID 4자리 이상 입력 하세요");
				jm.tfId.setText("");
			} else {
				try {
					out.write((Function.IDCHECK + "|" + jm.tfId.getText() + "\n").getBytes());
				} catch (Exception e1) {
				}
			}
		} else if (e.getSource() == jm.btnInsert) {
			if (jm.tfId.getText().length() < 4) {
				JOptionPane.showMessageDialog(this, "아이디는 4자리 이상 입력 하세요");
				jm.tfId.setText("");
				return;
			} 
			else if(jm.tfName.getText().length() < 2) {
				JOptionPane.showMessageDialog(this, "이름은 2자 이상 필수입력 사항입니다.");
				return;
			} else if (jm.tfNickName.getText().length() < 2) {
			JOptionPane.showMessageDialog(this, "닉네임은 2자 이상 필수입력 사항입니다.");
			return;
			} else if (jm.pfPwd2.getPassword().length < 4) {
			JOptionPane.showMessageDialog(this, "4자리 이상 비밀번호를 입력해 주세요");
			return;
			} else if (jm.pfPwd1.getPassword().length != jm.pfPwd2.getPassword().length) {
			JOptionPane.showMessageDialog(this, "같은 비밀 번호를 입력 하세요");
			jm.pfPwd2.setText("");
			return;
			}
			else {
				for (int i = 0; i < jm.pfPwd1.getPassword().length; i++) {
					if (jm.pfPwd1.getPassword()[i] != jm.pfPwd2.getPassword()[i]) {
						JOptionPane.showMessageDialog(this, "같은 비밀 번호를 입력 하세요");
						jm.pfPwd2.setText("");
						return;
					}
				}
			}
			if (jm.tfTel1.getText().length() < 1) {
				jm.tfTel1.setText("000");
				
			} 
			else if (jm.tfTel2.getText().length() < 1) {
				jm.tfTel1.setText("000");
				
			} 
			else if (jm.tfTel3.getText().length() < 1) {
				jm.tfTel1.setText("000");
				
			}
			member.setId(jm.tfId.getText());
			member.setName(jm.tfName.getText());
			member.setNickName(jm.tfNickName.getText());
			member.setPwd(jm.pfPwd1.getPassword());
			member.setTel(jm.tfTel1.getText() + jm.tfTel2.getText() + jm.tfTel3.getText());
			member.setGen(String.valueOf(jm.Gender));
			member.setAvatar(jm.Avatar);
			try {
				out.write((Function.JOIN + "|" + member.toString() + "\n").getBytes());
			} catch (Exception e1) {
			}
		}
	}

	private Image setImage(URL filename, int w, int h) {
		ImageIcon ii = new ImageIcon(filename);
		Image i = ii.getImage().getScaledInstance(w, h, Image.SCALE_SMOOTH);
		return i;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		System.out.println("this is run");
		try {
			while (true) {
				String msg = in.readLine();
				System.out.println(msg);
				StringTokenizer st = new StringTokenizer(msg, "|");
				int no = Integer.parseInt(st.nextToken());
				switch (no) {
				case Function.IDCHECK: {
					if (!Boolean.parseBoolean(st.nextToken())) {
						JOptionPane.showMessageDialog(this, "사용 가능한 아이디 입니다.");
						jm.pfPwd1.setEnabled(true);
						jm.pfPwd2.setEnabled(true);
						jm.tfName.setEnabled(true);
						jm.tfNickName.setEnabled(true);
						jm.tfTel1.setEnabled(true);
						jm.tfTel2.setEnabled(true);
						jm.tfTel3.setEnabled(true);
						jm.btnInsert.setEnabled(true);
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
					s.close();
					in.close();
					out.close();
					t.interrupt();
					break;
				}
				}
			}
		} catch (Exception ex) {
			ex.getStackTrace();
		}
	}
}