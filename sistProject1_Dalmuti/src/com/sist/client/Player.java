package com.sist.client;

import java.awt.*;
import java.net.URL;
import javax.swing.*;
import com.sist.common.UserDTO;
import java.awt.geom.Ellipse2D;

public class Player extends JPanel {
	int leftCard;
	ImageIcon grade;
	JLabel pGrade;
	JLabel nickName;
	JLabel myTurn;
	ImageIcon playerIcon;
	ImageIcon MyturnIcon;

	JLabel avata;
	UserDTO dto;
	public Player(UserDTO dto) {
		// TODO Auto-generated constructor stub
		this.dto = dto;
		try {
			playerIcon = new ImageIcon(
					SetImager.setImage(getClass().getResource("/image/" + dto.getGender() + dto.getAvatar() + ".png"), 150, 230));
			avata = new JLabel(playerIcon);
			grade = new ImageIcon(getClass().getResource("/image/rank" + dto.getLevel() + ".png"));
			pGrade = new JLabel(grade);
			MyturnIcon= new ImageIcon(SetImager.setImage(getClass().getResource("/image/myturn.png"), 50, 50));
			myTurn =new JLabel(MyturnIcon);
			avata = new JLabel(playerIcon);
			this.nickName = new JLabel();
			this.nickName.setFont(new Font("³ª´®½ºÄù¾î", Font.PLAIN, 12));
			this.nickName.setForeground(new Color(0, 0, 0));
			this.nickName.setHorizontalAlignment(JTextField.CENTER);
			// this.nickName.setEditable(false);
			this.nickName.setText(dto.getNickname());
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		setCursor(new Cursor(Cursor.HAND_CURSOR));
		add(pGrade);
		add(myTurn);
		add(this.nickName);	
		
		setBackground(new Color(233, 233, 233));
		this.nickName.setBackground(new Color(233, 233, 233));
		
		setSize(450, 300);
		setLayout(null);

		avata.setBounds(10, 10, 150, 230);

		//pGrade.setBounds(10, 245, 20, 30);
		pGrade.setBounds(10, 245, 30, 30);
		myTurn.setBounds(0, 0, 50, 50);
		myTurn.setVisible(false);

		this.nickName.setBounds(35, 245, 115, 30);
		this.add(avata);
		this.add(pGrade);
		this.add(pGrade);
	}
	public void returnToAvatarImg(){
		playerIcon = new ImageIcon(
				SetImager.setImage(getClass().getResource("/image/" + dto.getGender() + dto.getAvatar() + ".png"), 150, 230));
		avata.setIcon(playerIcon);
		avata.repaint();
	}

	protected void paintComponent(Graphics g) {
		if (g instanceof Graphics2D) {
			final int R = 240;
			final int G = 240;
			final int B = 240;

			Paint p = new GradientPaint(0.0f, 0.0f, new Color(R, G, B, 0), 0.0f, getHeight(), new Color(R, G, B, 255),
					true);
			Graphics2D g2d = (Graphics2D) g;
			g2d.setPaint(p);
			g2d.fillRect(0, 0, getWidth(), getHeight());
		}
	}
}