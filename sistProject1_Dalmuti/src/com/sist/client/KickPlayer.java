package com.sist.client;
import java.awt.*;
import javax.swing.*;

public class KickPlayer extends JFrame  {
	JComboBox t ;
	JButton ok,can;
	public KickPlayer(){
		JPanel p = new JPanel();
		t = new JComboBox();
		ok = new JButton("�߹�");
		ok.setFont(new Font("����������", Font.PLAIN, 12));
		can = new JButton("���");
		can.setFont(new Font("����������", Font.PLAIN, 12));
		p.add(t);
		p.add(ok);
		p.add(can);
		add("Center",p);
		setSize(300,80);
		setLocation(400,450);
		setTitle("�߹� ��ǥ");
		
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
}
