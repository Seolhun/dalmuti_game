package com.sist.client;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;

public class Invite extends JFrame {
	JTable table;
	DefaultTableModel model;
	JButton inBtn,canBtn;
	JComboBox box;

	public Invite() {
		box = new JComboBox();
		box.setFont(new Font("����������", Font.PLAIN, 12));
		String[] col = { "��ŷ", "���̵�", "��ȭ��", "��ġ" };
		String[][] row = new String[0][4];
		model = new DefaultTableModel(row, col) {
			@Override
			public Class getColumnClass(int column) {
				// TODO Auto-generated method stub
				return getValueAt(0, column).getClass();
			}

			@Override
			public boolean isCellEditable(int row, int column) { // table row
																	// �������̰� �ϱ�
				// TODO Auto-generated method stub
				return false;
			}

		};
		table = new JTable(model);
		table.setFont(new Font("����������", Font.PLAIN, 12));
		table.setShowHorizontalLines(true);
		table.getTableHeader().setReorderingAllowed(false);
		table.getTableHeader().setResizingAllowed(false);
		table.getColumn("��ŷ").setPreferredWidth(10);
		TableColumn column;
		for (int i = 1; i < col.length; i++) {
			column = table.getColumnModel().getColumn(i);
			DefaultTableCellRenderer rnd = new DefaultTableCellRenderer();
			if (i == 1) {
				column.setPreferredWidth(100);
				rnd.setHorizontalAlignment(JLabel.CENTER);
			} else if (i == 2) {
				column.setPreferredWidth(100);
				rnd.setHorizontalAlignment(JLabel.CENTER);
			} else if (i == 3) {
				column.setPreferredWidth(10);
				rnd.setHorizontalAlignment(JLabel.CENTER);
			}
			column.setCellRenderer(rnd);
		}
		JScrollPane js = new JScrollPane(table);
		inBtn= new JButton("�ʴ�");
		inBtn.setFont(new Font("����������", Font.PLAIN, 12));
		canBtn = new JButton("���");
		canBtn.setFont(new Font("����������", Font.PLAIN, 12));
		setLayout(null);
		box.setBounds(150,5,100,30);
		inBtn.setBounds(260,5,80,30);
		add(box);
		add(inBtn);
		js.setBounds(10, 40, 480, 270);
		canBtn.setBounds(200, 320, 80, 30);
		add(js);
		add(canBtn);
		
		setLocation(400, 300);
		setSize(515, 400);
		// setVisible(true);
	}


}