package com.sist.client;
import java.awt.*;
import javax.swing.*;

public class KickPlayer extends JFrame  {
	JComboBox t ;
	JButton ok,can;
	public KickPlayer(){
		JPanel p = new JPanel();
		t = new JComboBox();
		ok = new JButton("Ãß¹æ");
		ok.setFont(new Font("³ª´®½ºÄù¾î", Font.PLAIN, 12));
		can = new JButton("Ãë¼Ò");
		can.setFont(new Font("³ª´®½ºÄù¾î", Font.PLAIN, 12));
		p.add(t);
		p.add(ok);
		p.add(can);
		add("Center",p);
		setSize(300,80);
		setLocation(400,450);
		setTitle("Ãß¹æ ÅõÇ¥");
		
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
}
