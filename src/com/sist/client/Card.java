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
   ���� �ϴܿ� ��ġ�ϴ� ������ ī�� 
*/

public class Card extends JButton {
	int cardNumber; // ī�� ��ȣ
	ImageIcon img;
	boolean calledCheck = false; // ī�尡 ���� ���� ���� ��� true, �ƴ� ��� false
	
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