package com.sist.client;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

import javafx.scene.control.TextField;
public class PriorityCard extends JFrame {
   JLabel la1;
   JButton[] bu = new JButton[5];
   JButton okBtn;
   JTextField[] tf = new JTextField[5];
   JLabel line;
   ImageIcon icon,icon1,icon2,icon3,icon4;
   Image poster;
   ButtonGroup bg;
   String[] selectedCard = new String[5];
   int[] secret=new int[5];

   public PriorityCard() {
	   setEnabled(false);
      
	 
	   //»ý¼º
      icon = new ImageIcon(getClass().getResource("/image/cardBack.png"));
      icon1 = new ImageIcon(getClass().getResource("/image/cardBack.png"));
      icon2 = new ImageIcon(getClass().getResource("/image/cardBack.png"));
      icon3 = new ImageIcon(getClass().getResource("/image/cardBack.png"));
      icon4 = new ImageIcon(getClass().getResource("/image/cardBack.png"));
      
      la1 = new JLabel("¼ø¼­ °í¸£±â");
      la1.setFont(new Font("³ª´®½ºÄù¾î", Font.PLAIN, 12));
      
      
      bu[0] = new JButton(icon);
      bu[1] = new JButton(icon1);
      bu[2]= new JButton(icon2);
      bu[3]= new JButton(icon3);
      bu[4]= new JButton(icon4);
      okBtn = new JButton("È®ÀÎ");
      okBtn.setFont(new Font("³ª´®½ºÄù¾î", Font.PLAIN, 12));
      bg=new ButtonGroup();
      for(int i=0;i<5;i++){
    	  tf[i] = new JTextField();
    	  tf[i].setHorizontalAlignment(JTextField.CENTER);
      }
      
      line = new JLabel();
      line.setText("TEST");
      line.setFont(new Font("³ª´®½ºÄù¾î", Font.PLAIN, 12));
      line.setHorizontalAlignment(JTextField.CENTER);
      
      
      add(tf[0]);
      add(tf[1]);
      add(tf[2]);
      add(tf[3]);
      add(tf[4]);
      add(line);
      
      setResizable(false);
      //¹èÄ¡
      setLayout(null);
      la1.setBounds(230, 10, 150, 40);
      bu[0].setBounds(10, 60, 100, 150);
      bu[1].setBounds(120, 60, 100, 150);
      bu[2].setBounds(230, 60, 100, 150);
      bu[3].setBounds(340, 60, 100, 150);
      bu[4].setBounds(450, 60, 100, 150);
      tf[0].setBounds(10, 220, 100, 30);
      tf[1].setBounds(120, 220, 100, 30);
      tf[2].setBounds(230, 220, 100, 30);
      tf[3].setBounds(340, 220, 100, 30);
      tf[4].setBounds(450, 220, 100, 30);
      line.setBounds(10, 260, 550, 30);
      okBtn.setBounds(250, 300, 60, 30);
      
      for(int i=0;i<5;i++){
    	  tf[i].setEditable(false);
    	 // bu[i].setEnabled(false);
      }
      add(la1);
      add(bu[0]);
      add(bu[1]);
      add(bu[2]);
      add(bu[3]);
      add(bu[4]);
      add(okBtn);
      okBtn.setEnabled(false);
 /*     bu[0].addActionListener(this);
      bu[1].addActionListener(this);
      bu[2].addActionListener(this);
      bu[3].addActionListener(this);
      bu[4].addActionListener(this);*/
      
      //±¸Çö
      setSize(580, 380);
      setVisible(false);
      setLocation(350, 200);
      setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
      requestFocusInWindow();
      
      //¸Þ¼Òµå
   }
   

   public void ChooseCard() {
      
      return ;
   }



}