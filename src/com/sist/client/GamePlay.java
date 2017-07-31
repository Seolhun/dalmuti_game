package com.sist.client;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.table.*;



import javax.swing.border.BevelBorder;

public class GamePlay extends JPanel implements ActionListener {
	Vector<Player> playList = new Vector<Player>();
	ImageIcon[] cardImageList = new ImageIcon[13];
	ImageIcon cardback;
	int userNumber = 5;
	Vector<Integer> cardIndex; // 서버에서 받을거
	static Vector<Card> havingCard;
	static Vector<Integer> selectedCard;
	static int[] pastCard = new int[3]; // 0 : 카드 번호, 1 : 카드 장수 , 2 : 조커의 갯수
	JTextArea ta;
	JTextField tf;
	JProgressBar jp;
	JButton chatBtn;
	JButton passBtn, summitBtn, readyBtn, startBtn;
	JTable table;
	DefaultTableModel model;
	// JButton opBtn1 = new JButton("쪽지보내기");
	JButton opBtn2, opBtn3, opBtn4;
	JPanel btnPanel = new JPanel();
	//JLabel alarm;
	boolean gameSet = false;
	boolean chkThread = false;
	boolean bCheck = false;
	Font font1=new Font("나눔스퀘어", Font.PLAIN, 12);
	
	
	JLabel[] lab;
	Thread t;
	JPanel pastCardPanel = new JPanel() {
		protected void paintComponent(Graphics g) {
			if (g instanceof Graphics2D) {
				final int R = 240;
				final int G = 240;
				final int B = 240;

				Paint p = new GradientPaint(0.0f, 0.0f, new Color(R, G, B, 0), 0.0f, getHeight(),
						new Color(R, G, B, 255), true);
				Graphics2D g2d = (Graphics2D) g;
				g2d.setPaint(p);
				
			
				g2d.fillRoundRect(0, 0, getWidth(),getHeight(), 40, 60);
				
			}
		}
	};
	JPanel CardPanel = new JPanel() {
		protected void paintComponent(Graphics g) {
			if (g instanceof Graphics2D) {
				final int R = 240;
				final int G = 240;
				final int B = 240;

				Paint p = new GradientPaint(0.0f, 0.0f, new Color(R, G, B, 0), 0.0f, getHeight(),
						new Color(R, G, B, 255), true);
				Graphics2D g2d = (Graphics2D) g;
				g2d.setPaint(p);
				g2d.fillRect(0, 0, getWidth(), getHeight());
			}
		}
	};
	Image backimg;

	// 배경 넣기
	public void paintComponent(Graphics g) {
		backimg = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/image/test10.jpg"));
		g.drawImage(backimg, 0, 0, getWidth(), getHeight(), this);
	}

	// 카드 이미지 초기화
	{
		for (int i = 0; i < 13; i++) {
			cardImageList[i] = new ImageIcon(getClass().getResource("/image/card" + (i + 1) + ".jpg"));
		}
		cardback = new ImageIcon(SetImager.setImage(getClass().getResource("/image/cardBack.png"),100,150));
		selectedCard = new Vector<Integer>();
		havingCard = new Vector<Card>();
		cardIndex = new Vector<Integer>();
		pastCard[0] = -1;
		pastCard[1] = 1;
		pastCard[2] = 0;
	}

	GamePlay() {
		setLayout(null);
		setChatLayout();
		setButtonLayout();
		setPastCard(pastCard[0], pastCard[1], pastCard[2]);
		controlButton(2);
		CardPanel.setLayout(null);
		CardPanel.setBackground(new Color(233, 233, 233));
		CardPanel.setBounds(0, 650, 850, 220);
		add(CardPanel);
	}

	public void setCardBounds() {
		for (int i = 0; i < havingCard.size(); i++) {
			havingCard.get(i).setBorder(new BevelBorder(BevelBorder.RAISED));
			havingCard.get(i).setBounds((userNumber * 10) * i, 55, 100, 155);
		}
	}

	public void setCardLayout() {
		CardPanel.removeAll();
		Collections.sort(cardIndex);
		for (int i = 0; i < cardIndex.size(); i++) {
			Card tmp = new Card(cardIndex.get(i), cardImageList[cardIndex.get(i) - 1]);
			tmp.addActionListener(this);
			havingCard.addElement(tmp);
		}
		for (int i = 0; i < cardIndex.size(); i++) {
			CardPanel.add(havingCard.get(i));
		}
		setCardBounds();
		CardPanel.validate();
	}

	public void setPlayLayout() {
		for (int i = 0; i < playList.size(); i++) {
			if (i == 0) {
				playList.get(0).setBounds(75, 10, 170, 280);
			} else if (i == 1) {
				playList.get(1).setBounds(595, 10, 170, 280);
			} else if (i == 2) {
				playList.get(2).setBounds(30, 320, 170, 280);
			} else if (i == 3) {
				playList.get(3).setBounds(635, 320, 170, 280);
			}
		}
		for (int i = 0; i < playList.size(); i++) {

			this.add(playList.get(i));
		}
	}
	
	//순위표 
	private void setChatLayout() {
		String[] col = { "순위", "랭킹", "닉네임" };
		String[][] row = new String[0][3];
		model = new DefaultTableModel(row, col) {
			@Override
			public Class getColumnClass(int column) {
				// TODO Auto-generated method stub
				return getValueAt(0, column).getClass();
			}

			@Override
			public boolean isCellEditable(int row, int column) { // table row
																	// 못움직이게 하기
				// TODO Auto-generated method stub
				return false;
			}
		};
		table = new JTable(model);
		
		JTableHeader header = table.getTableHeader();
        final TableCellRenderer headerRenderer = header.getDefaultRenderer();
        header.setDefaultRenderer( new TableCellRenderer() {
             public Component getTableCellRendererComponent( JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column ) {
                  Component comp = headerRenderer.getTableCellRendererComponent( table, value, isSelected, hasFocus, row, column );
                       comp.setFont( font1 );
                  return comp;
             }
        });
		table.setFont(font1);
		table.setShowHorizontalLines(true);
		table.getTableHeader().setReorderingAllowed(false);
		table.getTableHeader().setResizingAllowed(false);
		table.getColumn("순위").setPreferredWidth(10);
		table.getColumn("랭킹").setPreferredWidth(10);
		TableColumn column;
		for (int i = 2; i < col.length; i++) {
			column = table.getColumnModel().getColumn(i);
			DefaultTableCellRenderer rnd = new DefaultTableCellRenderer();
			if (i == 2) {
				column.setPreferredWidth(100);
				rnd.setHorizontalAlignment(JLabel.CENTER);
			}
			column.setCellRenderer(rnd);
		}

		JScrollPane js1 = new JScrollPane(table);
		ta = new JTextArea(); // 채팅들어가는 부분
		ta.setFont(new Font("나눔스퀘어", Font.PLAIN, 12));
		JScrollPane js = new JScrollPane(ta);
		ta.setLineWrap(true); // 자동 줄바꿈
		ta.setEditable(false);

		tf = new JTextField(); // 채팅창 입력부분
		tf.setFont(new Font("나눔스퀘어", Font.PLAIN, 12));
		chatBtn = new JButton("입력");
		chatBtn.setFont(new Font("나눔스퀘어", Font.PLAIN, 12));
		pastCardPanel.setBackground(new Color(233, 233, 233));
		pastCardPanel.setLayout(null);
		jp = new JProgressBar(0, 150);
		jp.setFont(new Font("나눔스퀘어", Font.BOLD, 12));
		jp.setPreferredSize(new Dimension(350, 30));
		jp.setStringPainted(true);
		jp.setVisible(false);
		jp.setBounds(10, 620, 850, 30);
		add(jp);
		/*alarm = new JLabel();
		alarm.setFont(new Font("나눔스퀘어", Font.PLAIN, 12));
		alarm.setBounds(20, 580, 30, 30);*/

		js1.setBounds(850, 345, 235, 150);

		opBtn2 = new JButton(new ImageIcon("src/image/opbtn2.png"));
		opBtn3 = new JButton(new ImageIcon("src/image/opbtn3.png"));
		opBtn4 = new JButton(new ImageIcon("src/image/opbtn4.png"));
		opBtn2.setBorderPainted(false);
		opBtn3.setBorderPainted(false);
		opBtn4.setBorderPainted(false);
		opBtn2.setContentAreaFilled(false);
		opBtn3.setContentAreaFilled(false);
		opBtn4.setContentAreaFilled(false);
		opBtn2.setMargin(new Insets(0, 0, 0, 0));
		opBtn3.setMargin(new Insets(0, 0, 0, 0));
		opBtn4.setMargin(new Insets(0, 0, 0, 0));

		// JPanel p = new JPanel();
		add(opBtn2);
		add(opBtn3);
		add(opBtn4);
		opBtn2.setBounds(850, 510, 70, 40);
		opBtn3.setBounds(930, 510, 70, 40);
		opBtn4.setBounds(1010, 510, 70, 40);
		// panBtn.add(opBtn1);

		add(js);
		add(js1);
		add(tf);
		add(chatBtn);

		add(pastCardPanel);
		pastCardPanel.setBounds(310, 240, 220, 250);
		js.setBounds(850, 5, 235, 300);
		tf.setBounds(850, 310, 170, 25);
		chatBtn.setBounds(1025, 310, 55, 25);

	}

	public void setPastCard(int num, int cnt, int jnum) {

		pastCardPanel.removeAll();
		if (lab != null) {
			for (int i = 0; i < lab.length; i++) {
				lab[i].setVisible(false);
				lab[i] = null;
			}
			lab = null;
			System.gc();
		}
		lab = new JLabel[cnt];
		if (pastCard[0] == -1) {
			lab[0] = new JLabel(cardback);
			lab[0].setBorder(new BevelBorder(BevelBorder.RAISED));
			lab[0].setBounds(10, 10, 100, 150);
			pastCardPanel.add(lab[0]);
		} else {
			for (int i = 0; i < cnt - jnum; i++) {
				lab[i] = new JLabel(cardImageList[num - 1]);
				lab[i].setBorder(new BevelBorder(BevelBorder.RAISED));
				lab[i].setBounds(10 + 30 * (i % 4), 10 + ((i / 4) * 25), 100, 150);
			}
			for (int i = cnt - jnum; i < cnt; i++) {
				lab[i] = new JLabel(cardImageList[12]);
				lab[i].setBorder(new BevelBorder(BevelBorder.RAISED));
				lab[i].setBounds(10 + 30 * (i % 4), 10 + ((i / 4) * 25), 100, 150);
			}
			for (int i = lab.length - 1; i >= 0; i--) {
				pastCardPanel.add(lab[i]);
			}
		}
		pastCardPanel.validate();
		this.repaint();

	}

	public void controlButton(int a) {
		if (a == 1) {
			summitBtn.setVisible(true);
			passBtn.setVisible(true);
			readyBtn.setVisible(false);
			startBtn.setVisible(false);
			summitBtn.setEnabled(false);
			passBtn.setEnabled(false);
			for (Player player : playList) {
				player.avata.setToolTipText(String.valueOf(player.dto.CardNumber + "장 남음"));
			}
			controlCard(0);
		} else if (a == 2) {
			summitBtn.setVisible(false);
			passBtn.setVisible(false);
			readyBtn.setVisible(true);
			startBtn.setVisible(true);
			startBtn.setEnabled(false);
		} else if (a == 3) {
			summitBtn.setVisible(false);
			passBtn.setVisible(false);
			readyBtn.setVisible(true);
			startBtn.setVisible(true);
			startBtn.setEnabled(true);
		}

	}

	public void controlCard(int param) {
		if (param == 1) {
			for (int i = 0; i < havingCard.size(); i++) {
				havingCard.elementAt(i).setEnabled(true);
			}
		} else {
			for (int i = 0; i < havingCard.size(); i++) {
				havingCard.elementAt(i).setEnabled(false);
			}
		}
	}

	public void controlPassBtn() {
		for (int i = 0; i < havingCard.size(); i++) {
			if (havingCard.elementAt(i).calledCheck == true) {
				havingCard.elementAt(i).doClick();
			}
		}
	}

	private void setButtonLayout() {
		/* 카드 제출 , 패스 버튼 */

		ImageIcon summit = new ImageIcon(getClass().getResource("/image/submitbtn.png"));
		ImageIcon pass = new ImageIcon(getClass().getResource("/image/passbtn.png"));
		ImageIcon start = new ImageIcon(getClass().getResource("/image/startbtn.png"));
		ImageIcon ready = new ImageIcon(getClass().getResource("/image/readybtn.png"));
		summitBtn = new JButton(summit);
		summitBtn.setBorderPainted(false);
		summitBtn.setContentAreaFilled(false);
		summitBtn.setMargin(new Insets(0, 0, 0, 0));

		passBtn = new JButton(pass);
		passBtn.setBorderPainted(false);
		passBtn.setContentAreaFilled(false);
		passBtn.setMargin(new Insets(0, 0, 0, 0));

		readyBtn = new JButton(ready);
		readyBtn.setBorderPainted(false);
		readyBtn.setContentAreaFilled(false);
		readyBtn.setMargin(new Insets(0, 0, 0, 0));

		startBtn = new JButton(start);
		startBtn.setBorderPainted(false);
		startBtn.setContentAreaFilled(false);
		startBtn.setMargin(new Insets(0, 0, 0, 0));

		startBtn.setBounds(880, 720, 95, 95);
		readyBtn.setBounds(980, 720, 95, 95);
		summitBtn.setBounds(880, 720, 95, 95);
		passBtn.setBounds(980, 720, 95, 95);

		add(startBtn);
		add(readyBtn);
		add(summitBtn);
		add(passBtn);

		startBtn.setToolTipText("<시작하기>");
		readyBtn.setToolTipText("<준비하기>");
		summitBtn.setToolTipText("<제출하기>");
		passBtn.setToolTipText("<패스하기>");

		startBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
		readyBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
		summitBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
		passBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
	}

	/* 15초 제한 Timer 메소드 */
	public void setTimer() {
		chkThread = false;
		jp.setVisible(true);
		jp.setValue(150);
		jp.setStringPainted(true);
		jp.setBackground(Color.WHITE);
		// jp.setForeground(Color.ORANGE);
		t = new Thread(new Runnable() {
			public void run() {
				for (int i = jp.getMaximum(); i >= 0; i--) {
					switch (i) {
					case 150:
						jp.setForeground(new Color(70, 210, 35));
						break;
					case 80:
						jp.setForeground(new Color(215, 201, 30));
						break;
					case 50:
						jp.setForeground(new Color(220, 133, 15));
						break;
					case 30:
						jp.setForeground(Color.RED);
						break;
					}
					if (chkThread) {
						break;
					}
					if (i == 0) {
						passBtn.doClick();
					}
					int time = i / 10;
					jp.setValue(i);
					jp.setString("남은시간 : " + time + "초");

					try {
						Thread.sleep(100);
					} catch (Exception e) {
					}
				}

			}
		});
		t.start();
	}

	public boolean checkCardRule() {
		if (pastCard[0] == -1) {
			return true;
		} else if (pastCard[1] == 1) {
			if (selectedCard.get(0) >= pastCard[0]) {
				JOptionPane.showMessageDialog(this, pastCard[0] + "이상의 숫자 카드는 낼 수 없습니다.");
				return false;
			}
		} else {
			for (int i = 0; i < selectedCard.size(); i++) {
				if (selectedCard.get(i) == 13) {
					continue;
				} else {
					if (selectedCard.get(i) >= pastCard[0]) {
						JOptionPane.showMessageDialog(this, pastCard[0] + "이상의 숫자 카드는 낼 수 없습니다.");
						return false;
					}
				}
			}
		}
		int cnt = 0;
		for (int i = 0; i < havingCard.size(); i++) {
			if (havingCard.get(i).calledCheck == true) {
				cnt++;
			}
		}
		if (cnt == pastCard[1]) {
			return true;
		} else {
			JOptionPane.showMessageDialog(this, pastCard[1] + "개의 카드를 내셔야 합니다.");
			return false;
		}

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

		if (e.getSource() instanceof Card) {
			
			Card c = (Card) e.getSource();

			if (c.calledCheck) {
				c.setLocation(c.getX(), c.getY() + 50);
				this.repaint();
				c.calledCheck = false;
				GamePlay.selectedCard.removeElement(c.cardNumber);
				int count = 0, i = 0;
				for (; i < GamePlay.havingCard.size(); i++) {
					if (!(GamePlay.havingCard.get(i).calledCheck))
						count++;
				}
				if (i == count) {
					for (i = 0; i < GamePlay.havingCard.size(); i++)
						GamePlay.havingCard.get(i).setEnabled(true);
				}

			}
			/* 카드를 선택할 경우 */
			else {
				if (GamePlay.pastCard[0] == -1 || GamePlay.selectedCard.size() < GamePlay.pastCard[1]) {
					c.setLocation(c.getX(), c.getY() - 50);
					this.repaint();
					c.calledCheck = true;
					GamePlay.selectedCard.addElement(c.cardNumber);
					System.out.println("낼 카드 수:" + GamePlay.selectedCard.size());
					if (c.cardNumber != 13) {
						for (int i = 0; i < GamePlay.havingCard.size(); i++) {
							if (GamePlay.havingCard.get(i).cardNumber != 13
									&& c.cardNumber != GamePlay.havingCard.get(i).cardNumber) {
								GamePlay.havingCard.get(i).setEnabled(false);
							}
						}
					}
				} else
					JOptionPane.showMessageDialog(null, "그만내세요");
					
			}
		}

	}
}