package com.sist.client;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
public class SendMessage extends JFrame{
    JLabel la;
    JTextField tf;
    JTextArea ta;
    JButton b1,b2;
    public SendMessage()
    {
    	la=new JLabel("¹Þ´Â »ç¶÷");
    	la.setFont(new Font("³ª´®½ºÄù¾î", Font.PLAIN, 12));
    	tf=new JTextField(15);
    	tf.setFont(new Font("³ª´®½ºÄù¾î", Font.PLAIN, 12));
    	tf.setEnabled(false);
    	ta=new JTextArea();
    	ta.setFont(new Font("³ª´®½ºÄù¾î", Font.PLAIN, 12));
    	JScrollPane js=new JScrollPane(ta);
    	b1=new JButton("º¸³»±â");
    	b1.setFont(new Font("³ª´®½ºÄù¾î", Font.PLAIN, 12));
    	b2=new JButton("Ãë¼Ò");
    	b2.setFont(new Font("³ª´®½ºÄù¾î", Font.PLAIN, 12));
    	
    	JPanel p1=new JPanel();
    	p1.add(la);p1.add(tf);
    	JPanel p2=new JPanel();
    	p2.add(b1);
    	p2.add(b2);
    	
    	add("North",p1);
    	add("Center",js);
    	add("South",p2);
    	
    	setSize(300, 300);
    	setLocation(300,300);
    }
}
