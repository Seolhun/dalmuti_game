package com.sist.client;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.*;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;

public class ResultForm extends JFrame {

	AvatarPanel avatarLb;
	JLabel pointLb = new JLabel("Æ÷ÀÎÆ® :");
	static JLabel pointLF = new JLabel();
	static String originPoint;
	static String addPoint;

	public ResultForm(String originPoint, String addPoint) {
		// TODO Auto-generated constructor stub
		super("°á°ú È­¸é");
		this.originPoint = originPoint;
		this.addPoint = addPoint;
		this.getContentPane().setBackground(new Color(255, 255, 255));
		setLayout(null);
		pointLF.setText(originPoint);
		pointLb.setBounds(125, 290, 50, 30);
		pointLF.setBounds(180, 290, 90, 30);
		pointLF.setFont(new Font("³ª´®½ºÄù¾î", Font.PLAIN, 12));
		pointLb.setFont(new Font("³ª´®½ºÄù¾î", Font.PLAIN, 12));
		pointLF.setForeground(Color.BLACK);
		
		add(pointLF);
		add(pointLb);
		setSize(400, 400);
		setVisible(true);

	}

	static class AvatarPanel extends JPanel implements ActionListener {
		private Image originIMG;
		private Image resultIMG;
		private Timer timer;
		private float alpha = 1f;
		
		public AvatarPanel(){
			
		}

		public AvatarPanel(String srcAvt, String dscAvt) {
			System.out.println(srcAvt);
			System.out.println(dscAvt);
			originIMG = (new ImageIcon(setImage(getClass().getResource("/image/" + dscAvt + ".png"), 150, 250)))
					.getImage();
			resultIMG = (new ImageIcon(setImage(getClass().getResource("/image/" + srcAvt + ".png"), 150, 250)))
					.getImage();
			timer = new Timer(50, this);
			timer.start();

			setSize(150, 250);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			alpha += -0.01f;
			if (alpha <= 0) {
				alpha = 0;
				setResult(addPoint);
				timer.stop();
			}
			repaint();
		}

		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2d = (Graphics2D) g;
			g2d.drawImage(originIMG, 0, 0, 150, 250, null);
			g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
			g2d.drawImage(resultIMG, 0, 0, 150, 250, null);
		}

	}

	public static void setResult(String res) {
		pointLF.setText(String.valueOf(Integer.parseInt(originPoint) + Integer.parseInt(res)));
		pointLF.setForeground(Color.RED);
	}

	public static Image setImage(URL filename, int w, int h) {
		ImageIcon ii = new ImageIcon(filename);
		Image i = ii.getImage().getScaledInstance(w, h, Image.SCALE_SMOOTH);
		return i;
	}

}
