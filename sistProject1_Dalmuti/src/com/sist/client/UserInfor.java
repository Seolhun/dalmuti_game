package com.sist.client;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.URL;

	
public class UserInfor extends JFrame implements ActionListener {
	JLabel privateInfo,avatar,IdLabel,NickLabel,LevelLabel,PointLabel,LevelField;
	JTextField IdField,NickField,PointField;
	JPanel p;
	JButton Okbtn;
	
	public UserInfor(){
		//데이터 할당
		SetUserInfor();
	}
		
	public void SetUserInfor(){
		//배치
		privateInfo=new JLabel("개인정보");
		privateInfo.setHorizontalAlignment(JLabel.CENTER);
		privateInfo.setFont(new Font("나눔스퀘어", Font.PLAIN, 12));
		avatar =new JLabel();
		IdLabel =new JLabel("아이디",JLabel.CENTER);
		NickLabel =new JLabel("대화명",JLabel.CENTER);
		LevelLabel =new JLabel("계  급",JLabel.CENTER);
		PointLabel =new JLabel("승  점",JLabel.CENTER); 
		IdField =new JTextField(); // 서버에서 아이디 데이터 불러오기 입력 필요
		NickField =new JTextField();  // 서버에서 대화명 데이터 불러오기 입력 필요
		LevelField =new	JLabel(); //계급 데이터
		PointField =new JTextField();  // 서버에서 승점 데이터 불러오기 입력 필요
		Okbtn =new JButton("확  인");
		Okbtn.setFont(new Font("나눔스퀘어", Font.PLAIN, 12));
		
		IdLabel.setFont(new Font("나눔스퀘어", Font.PLAIN, 12));
		NickLabel.setFont(new Font("나눔스퀘어", Font.PLAIN, 12));
		LevelLabel.setFont(new Font("나눔스퀘어", Font.PLAIN, 12));
		PointLabel.setFont(new Font("나눔스퀘어", Font.PLAIN, 12));
		IdField.setFont(new Font("나눔스퀘어", Font.PLAIN, 12));
		NickField.setFont(new Font("나눔스퀘어", Font.PLAIN, 12));	
		LevelField.setFont(new Font("나눔스퀘어", Font.PLAIN, 12));
		PointField.setFont(new Font("나눔스퀘어", Font.PLAIN, 12));
		
		IdField.setEditable(false);
		NickField.setEditable(false);
		PointField.setEditable(false);
		
		p=new JPanel();
		
		p.setLayout(null);
		privateInfo.setBounds(10, 10, 480, 30);
		avatar.setBounds(5, 20, 145, 230);
		
		IdLabel.setBounds(155, 55, 60, 40);
		NickLabel.setBounds(155, 100, 60, 40);
		LevelLabel.setBounds(155, 145, 60, 40);
		PointLabel.setBounds(155, 190, 60, 40);
		
		IdField.setBounds(220, 55, 100, 40);
		NickField.setBounds(220, 100, 100, 40);
		LevelField.setBounds(220, 145, 100, 40);
		PointField.setBounds(220, 190, 100, 40);
		
		Okbtn.setBounds(135, 255, 70, 40);
		
		//패널 추가
		p.add(privateInfo); p.add(avatar); p.add(IdLabel);
		p.add(NickLabel); p.add(LevelLabel); p.add(PointLabel);
		p.add(IdField);
		p.add(NickField); p.add(LevelField); p.add(PointField);
		p.add(Okbtn);
		add(p);
		Okbtn.addActionListener(this);
		setSize(340, 340);
		setLocation(300,250);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
		
	public static void main (String[] args){
		new UserInfor();
	}
	public void setLabelImage(String avt, String lv){
		avatar.setIcon(new ImageIcon(SetImager.setImage(getClass().getResource("/image/"+avt+".png"), avatar.getWidth(), avatar.getHeight())));
		LevelField.setIcon(new ImageIcon(getClass().getResource("/image/rank"+lv+".png")));
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==Okbtn){
			dispose();
			return;
		}
	}
}
