package com.sist.client;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
/*
   게임 하단에 위치하는 각각의 카드 
*/

public class Card extends JButton {
	int cardNumber; // 카드 번호
	ImageIcon img;
	boolean calledCheck = false; // 카드가 위로 선택 됐을 경우 true, 아닐 경우 false
	
	public Card(int inputcardNumber, ImageIcon img) {
		// TODO Auto-generated constructor stub
		cardNumber = inputcardNumber;
		try {
			super.setIcon(img);
		} catch (Exception e) {
			e.printStackTrace();
		setVisible(true);
		}
	}
}