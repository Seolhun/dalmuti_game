package com.sist.client;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
public class MakeRoom extends JFrame implements ActionListener{
	JLabel la0,la1,la2,la3,la4;
	JTextField tf;
	JPasswordField pf;
	JComboBox box,roomNum;
	JRadioButton open, noopen;
	JButton b1, b2;
	public MakeRoom() {
		// TODO Auto-generated constructor stub
		la0 = new JLabel("¹æ¹øÈ£");
		la1 = new JLabel("¹æÀÌ¸§");
		la2 = new JLabel("»óÅÂ");
		la3 = new JLabel("ºñ¹Ð¹øÈ£");
		la4 = new JLabel("ÀÎ¿ø");
		la0.setFont(new Font("³ª´®½ºÄù¾î", Font.PLAIN, 12));
		la1.setFont(new Font("³ª´®½ºÄù¾î", Font.PLAIN, 12));
		la2.setFont(new Font("³ª´®½ºÄù¾î", Font.PLAIN, 12));
		la3.setFont(new Font("³ª´®½ºÄù¾î", Font.PLAIN, 12));
		la4.setFont(new Font("³ª´®½ºÄù¾î", Font.PLAIN, 12));
		
		tf = new JTextField();
		pf = new JPasswordField();
		box = new JComboBox();
		tf.setFont(new Font("³ª´®½ºÄù¾î", Font.PLAIN, 12));
		pf.setFont(new Font("³ª´®½ºÄù¾î", Font.PLAIN, 12));
		box.setFont(new Font("³ª´®½ºÄù¾î", Font.PLAIN, 12));
		roomNum = new JComboBox();
		
		box.addItem("5¸í");
	
		open = new JRadioButton("°ø°³");
		noopen = new JRadioButton("ºñ°ø°³");
		open.setFont(new Font("³ª´®½ºÄù¾î", Font.PLAIN, 12));
		noopen.setFont(new Font("³ª´®½ºÄù¾î", Font.PLAIN, 12));
		ButtonGroup bg = new ButtonGroup();
		bg.add(open);
		bg.add(noopen);
		open.setSelected(true);
		la3.setVisible(false);
		pf.setVisible(false);
		
		b1 = new JButton("¹æ¸¸µé±â");
		b2 = new JButton("Ãë¼Ò");
		b1.setFont(new Font("³ª´®½ºÄù¾î", Font.PLAIN, 12));
		b2.setFont(new Font("³ª´®½ºÄù¾î", Font.PLAIN, 12));
		JPanel p = new JPanel();
		p.add(b1);
		p.add(b2);
		setLayout(null);
		la0.setBounds(10, 15, 40, 30);
		roomNum.setBounds(55, 15, 150, 30);
		la1.setBounds(10,50,40,30);
		tf.setBounds(55, 50, 150, 30);
		
		la2.setBounds(10,85,40,30);
		open.setBounds(55, 85, 70, 30);
		noopen.setBounds(130, 85, 70, 30);
		
		la3.setBounds(30, 120, 60, 30);
		pf.setBounds(105, 120, 100, 30);
		
		la4.setBounds(10, 155, 40, 30);
		box.setBounds(55, 155, 150, 30);
		
		p.setBounds(10, 190, 195, 35);
		add(la0);add(roomNum);
		add(la1);add(tf);
		add(la2);add(open);add(noopen);
		add(la3);add(pf);
		add(la4);add(box);
		add(p);
		
		open.addActionListener(this);
		noopen.addActionListener(this);
		setSize(230,270);
		setLocation(500,400);
		
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == open){
			pf.setText("");
			la3.setVisible(false);
			pf.setVisible(false);
		}
		else if(e.getSource() == noopen){
			la3.setVisible(true);
			pf.setVisible(true);
		}
	}

}
