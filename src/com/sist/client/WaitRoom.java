package com.sist.client;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.event.*;
import javax.swing.text.Document;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import java.net.*;
import java.util.Enumeration;
import java.io.*;

/*
 * ´ë±â½Ç Å¬·¡½º
 */
public class WaitRoom extends JPanel implements ActionListener , MouseListener{
	JTable table2;
	DefaultTableModel model2;
	JTextPane tp;
	JTextField tf;
	JComboBox box;
	JButton b1, b2, b3, b4;
	JLabel adv, upper;
	RoomPanel[] rp = new RoomPanel[12];
	/*JPanel[] roomPanel = new JPanel[12];
	JLabel[] roomMember = new JLabel[12];
	JLabel[] roomNum = new JLabel[12];
	JLabel[] roomName = new JLabel[12];
	JButton[] roomBtn = new JButton[12];
	boolean[] isClose = new boolean[12];*/
	//int[] userCnt = new int[12];
	ImageIcon btnBack = new ImageIcon(setImage( getClass().getResource("/image/room0.png"), 157 , 140)   );
	
	WaitRoom() {
		
		JPanel roomBack = new JPanel();
		roomBack.setBackground(new Color(236,147,220));
		roomBack.setLayout(null);
		Dimension size = new Dimension();
		size.setSize(480, 815);
		roomBack.setPreferredSize(size);
		for(int i=0;i<12;i++){
			rp[i] = new RoomPanel();
			rp[i].roomNum.setIcon(new ImageIcon(SetImager.setImage(getClass().getResource("/image/r"+i+".png"), 30 ,30)));
			/*roomName[i] = new JLabel("Empty");
			roomMember[i] = new JLabel();
			roomNum[i] = new JLabel();
			roomNum[i].setIcon(new ImageIcon(SetImager.setImage(getClass().getResource("/image/r"+i+".png"), 30 ,30)));
			roomMember[i].setIcon(new ImageIcon(SetImager.setImage(getClass().getResource("/image/mb0.png"), 115, 30)));
			roomName[i].setHorizontalAlignment(JTextField.CENTER);*/
			//roomMember[i].setHorizontalAlignment(JTextField.CENTER);
		    //roomNum[i].setHorizontalAlignment(JTextField.CENTER);
			/*roomBtn[i] = new JButton(btnBack);
			roomBtn[i].setBorderPainted(false);
			roomBtn[i].setContentAreaFilled(false);
			roomBtn[i].setFocusPainted(false);
			roomBtn[i].setOpaque(false);*/
			
			//roomNum[i].setFont(new Font("³ª´®½ºÄù¾î", Font.PLAIN, 13));
			//roomName[i].setFont(new Font("³ª´®½ºÄù¾î", Font.BOLD, 13));
			//roomMember[i].setFont(new Font("³ª´®½ºÄù¾î", Font.PLAIN, 13));
	

			
			//roomPanel[i] = new JPanel();
			int col = i%3;
			int row = i/3;
			rp[i].setBounds(0+col*163, 0+row*205, 157, 200);	
			rp[i].setBackground(new Color(228, 209, 231));
			roomBack.add(rp[i]);
			/*roomPanel[i].setLayout(null);
			roomNum[i].setBounds(0, 0, 30, 30);
			roomMember[i].setBounds(37,0,150,30);
			roomBtn[i].setBounds(0,30,157,140);
			roomName[i].setBounds(0, 170, 157, 30);
			roomPanel[i].add(roomBtn[i]);
			roomPanel[i].add(roomNum[i]);
			roomPanel[i].add(roomMember[i]);
			roomPanel[i].add(roomName[i]);*/
		
			//isClose[i] = false;
		}
		JScrollPane js1 = new JScrollPane(roomBack, 
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
	

		String[] col2 = { "·©Å·", "¾ÆÀÌµð", "´ëÈ­¸í", "¼ºº°", "À§Ä¡" };
		String[][] row2 = new String[0][5];
		model2 = new DefaultTableModel(row2, col2) {
			@Override
			public Class getColumnClass(int column) {
				// TODO Auto-generated method stub
				return getValueAt(0, column).getClass();
			}

			@Override
			public boolean isCellEditable(int row, int column) { // table row
																	// ¸ø¿òÁ÷ÀÌ°Ô ÇÏ±â
				// TODO Auto-generated method stub
				return false;
			}
		};
		
		table2 = new JTable(model2);
		
		JTableHeader header = table2.getTableHeader();
        final Font boldFont = new Font("³ª´®½ºÄù¾î", Font.PLAIN, 12);
        final TableCellRenderer headerRenderer = header.getDefaultRenderer();
        header.setDefaultRenderer( new TableCellRenderer() {
             public Component getTableCellRendererComponent( JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column ) {
                  Component comp = headerRenderer.getTableCellRendererComponent( table, value, isSelected, hasFocus, row, column );
                       comp.setFont( boldFont );
                  return comp;
             }
        });
		
		table2.setShowHorizontalLines(true);
		table2.getTableHeader().setReorderingAllowed(false);
		table2.getTableHeader().setResizingAllowed(false); 
	
		table2.getColumn("·©Å·").setPreferredWidth(10);
		table2.setFont(new Font("³ª´®½ºÄù¾î", Font.PLAIN, 12));
		TableColumn column2;
		
		for(int i=1;i<col2.length;i++){
			column2 = table2.getColumnModel().getColumn(i);
			DefaultTableCellRenderer rnd = new DefaultTableCellRenderer();
			if(i==1){
				column2.setPreferredWidth(100);
				rnd.setHorizontalAlignment(JLabel.CENTER);
			}
			else if(i==2){
				column2.setPreferredWidth(100);
				rnd.setHorizontalAlignment(JLabel.CENTER);
			}
			else if(i==3){
				column2.setPreferredWidth(10);
				rnd.setHorizontalAlignment(JLabel.CENTER);
			}
			else if(i==4){
				column2.setPreferredWidth(50);
				rnd.setHorizontalAlignment(JLabel.CENTER);
			}
			column2.setCellRenderer(rnd);
		}
		
		JScrollPane js2 = new JScrollPane(table2);

		tp = new JTextPane();
		tp.setFont(new Font("³ª´®½ºÄù¾î", Font.PLAIN, 12));
		tp.setEditable(false);
		JScrollPane js3 = new JScrollPane(tp);
		tf = new JTextField();
		tf.setFont(new Font("³ª´®½ºÄù¾î", Font.PLAIN, 12));
		tf.addActionListener(this);
		box = new JComboBox();
		box.setFont(new Font("³ª´®½ºÄù¾î", Font.PLAIN, 12));
		box.addItem("black");
		box.addItem("green");
		box.addItem("pink");
		box.addItem("cyan");
		box.addItem("orange");

		JPanel p = new JPanel();
		p.setLayout(new GridLayout(2, 2, 5, 5));
		b1 = new JButton("¹æ¸¸µé±â");
		b2 = new JButton("ÂÊÁöº¸³»±â");
		b3 = new JButton("À¯ÀúÁ¤º¸");
		b4 = new JButton("³ª°¡±â");
		b1.setFont(new Font("³ª´®½ºÄù¾î", Font.PLAIN, 12));
		b2.setFont(new Font("³ª´®½ºÄù¾î", Font.PLAIN, 12));
		b3.setFont(new Font("³ª´®½ºÄù¾î", Font.PLAIN, 12));
		b4.setFont(new Font("³ª´®½ºÄù¾î", Font.PLAIN, 12));
		p.add(b1);
		p.add(b2);
		p.add(b3);
		p.add(b4);
		ImageIcon ad = new ImageIcon(getClass().getResource("/image/condition.gif"));
		adv = new JLabel(ad);
		adv.setBounds(520,355,255,125);

		ImageIcon upicon = new ImageIcon(getClass().getResource("/image/upper.png"));
		upper = new JLabel(upicon);
		upper.setBounds(10,10,770,100);
		upper.setToolTipText("<Dalmuti °ÔÀÓ¹æ¹ý ¾È³»>");
		upper.setCursor(new Cursor(Cursor.HAND_CURSOR));
		
		
		add(adv);
		add(upper);
		setLayout(null);
		add(js1);
		add(js2);
		add(js3);
		add(p);
		add(tf);
		add(box);
		box.addActionListener(this);
		adv.addMouseListener(this);
		upper.addMouseListener(this);
		
		
		js1.setBounds(10, 115, 500, 250);
		js2.setBounds(10, 370, 500, 180);
		js3.setBounds(515, 115, 265, 200);
		tf.setBounds(515, 320, 160, 30);
		box.setBounds(680, 320, 90, 30);
		p.setBounds(515, 485, 265, 70);

	}
	private void changeAllSwingComponentDefaultFont() {
		// TODO Auto-generated method stub
		
	}
	// »ö»ó º¯°æ 
    public void initStyle()
    {
    	Style def=StyleContext.getDefaultStyleContext().getStyle(StyleContext.DEFAULT_STYLE);
    	
    	Style green=tp.addStyle("green", def);
    	StyleConstants.setForeground(green, Color.green);
    	
    	Style pink=tp.addStyle("pink", def);
    	StyleConstants.setForeground(pink, Color.pink);
    	
    	Style cyan=tp.addStyle("cyan", def);
    	StyleConstants.setForeground(cyan, Color.cyan);
    	
    	Style orange=tp.addStyle("orange", def);
    	StyleConstants.setForeground(orange, Color.ORANGE);
    	
    }

    public Image setImage(URL filename, int w, int h) {
		ImageIcon ii = new ImageIcon(filename);
		Image i = ii.getImage().getScaledInstance(w, h, Image.SCALE_SMOOTH);
		return i;
	}
    // ¹®ÀÚ¿­ °áÇÕ 
    public void append(String msg,String color)
    throws Exception
    {
    	Document doc=tp.getDocument();
		doc.insertString(doc.getLength(), msg+"\n", tp.getStyle(color));
	
		tp.setSelectionStart(tp.getText().length()); //½ºÅ©·Ñ¹Ù ÃÖ½ÅÈ­
	
		
    }
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		if(e.getSource()==tf)
		{
			String data=tf.getText();
			if(data.length()<1)
				return;
			initStyle();
			
			String color=box.getSelectedItem().toString();
			
			try {
				append(data,color);
				
	
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			tf.setText("");
			
		}
		if(e.getSource()==box)
		{
			tf.requestFocus();
		}
		
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == adv){
			try {
				Desktop.getDesktop().browse(new URI("https://www.cjp.co.kr/event/condition3/"));
			} catch (IOException ex) {
				ex.printStackTrace();
			} catch (URISyntaxException ex) {
				ex.printStackTrace();
			}
		}
		else if(e.getSource() == upper){
			try {
				Desktop.getDesktop().browse(new URI("https://namu.wiki/w/%EB%8B%AC%EB%AC%B4%ED%8B%B0"));
			} catch (IOException ex) {
				ex.printStackTrace();
			} catch (URISyntaxException ex) {
				ex.printStackTrace();
			}
		}
	}
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	
}