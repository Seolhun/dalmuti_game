package com.sist.client;
import javax.swing.*;
import java.awt.*;
public class RoomPanel extends JPanel{
	int numCnt;
	JLabel roomNum;
	JLabel roomName;
	JLabel roomMember;
	JButton roomBtn;
	boolean isClose;
	public RoomPanel(){
		numCnt=0;
		roomBtn = new JButton();
		roomName= new JLabel("Empty");
		roomNum = new JLabel();
		roomMember = new JLabel();
		
		roomBtn.setIcon(new ImageIcon(SetImager.setImage( getClass().getResource("/image/room0.png"), 157 , 140)));
		roomMember.setIcon(new ImageIcon(SetImager.setImage(getClass().getResource("/image/mb0.png"), 115, 30)));
		roomName.setHorizontalAlignment(JTextField.CENTER);
		roomName.setFont(new Font("³ª´®½ºÄù¾î", Font.BOLD, 13));
		roomBtn.setBorderPainted(false);
		roomBtn.setContentAreaFilled(false);
		roomBtn.setFocusPainted(false);
		roomBtn.setOpaque(false);
		isClose = false;
		
		setLayout(null);
		roomNum.setBounds(0, 0, 30, 30);
		roomMember.setBounds(37,0,150,30);
		roomBtn.setBounds(0,30,157,140);
		roomName.setBounds(0, 170, 157, 30);
		add(roomBtn);
		add(roomNum);
		add(roomMember);
		add(roomName);
	}


}
