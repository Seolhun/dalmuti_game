package com.sist.client;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.net.URL;

import javax.swing.*;

import com.sun.org.apache.bcel.internal.generic.PUSH;

public class JoinMember extends JFrame implements ActionListener, KeyListener {

	JPanel p = new JPanel();
	JLabel av1, av2;
	JTextField tfId, tfName, tfNickName, tfGender;
	JTextField tfTel1, tfTel2, tfTel3; // ��ȭ
	JPasswordField pfPwd1, pfPwd2; // ��й�ȣ
	JButton btnInsert, btnCancel, btnCheckId; // ����, ���
	JRadioButton rbMan, rbWoman, rbAv1, rbAv2;

	String Avatar = "1";
	char Gender = 'M';

	public JoinMember() {
		add(p);
		p.setLayout(null);
		setIdField();
		setPwdField();
		setNameField();
		setTelField();
		setNicknameField();
		setGenderField();
		setAvatarField();
		completeMember();
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	// ���̵� ���� �޼ҵ�
	private void setIdField() {
		JLabel j1 = new JLabel();
		j1.setText("���̵�(*)");
		j1.setBounds(20, 10, 120, 40);
		j1.setFont(new Font("����������", Font.PLAIN, 12));
		p.add(j1);

		tfId = new JTextField();
		tfId.setFont(new Font("����������", Font.PLAIN, 12));
		tfId.setDocument((new JTextFieldLimit(10)));
		p.add(tfId);
		tfId.setBounds(155, 10, 100, 40);
		// tfId.addActionListener(this);
		tfId.addKeyListener(this);
		tfId.setFocusTraversalKeysEnabled(false);

		btnCheckId = new JButton("�ߺ��˻�");
		btnCheckId.setFont(new Font("����������", Font.PLAIN, 12));
		p.add(btnCheckId);
		btnCheckId.setBounds(265, 11, 70, 40);
		btnCheckId.setEnabled(true);
	}

	// ��й�ȣ ���� �޼ҵ�
	private void setPwdField() {
		// ��й�ȣ
		JLabel j2 = new JLabel();
		j2.setFont(new Font("����������", Font.PLAIN, 12));
		j2.setText("��й�ȣ(*)");
		j2.setBounds(20, 60, 120, 40);
		p.add(j2);
		pfPwd1 = new JPasswordField();
		pfPwd1.setFont(new Font("����������", Font.PLAIN, 12));
		p.add(pfPwd1);
		pfPwd1.setDocument((new JTextFieldLimit(10)));
		pfPwd1.setBounds(155, 60, 180, 40);
		// pfPwd1.addActionListener(this);
		pfPwd1.addKeyListener(this);
		pfPwd1.setFocusTraversalKeysEnabled(false);
		pfPwd1.setEnabled(false);

		// ��й�ȣ Ȯ��
		JLabel j3 = new JLabel();
		j3.setText("��й�ȣ Ȯ��(*)");
		j3.setFont(new Font("����������", Font.PLAIN, 12));
		j3.setBounds(20, 110, 120, 40);
		p.add(j3);

		pfPwd2 = new JPasswordField();
		p.add(pfPwd2);
		pfPwd2.setFont(new Font("����������", Font.PLAIN, 12));
		pfPwd2.setDocument((new JTextFieldLimit(10)));
		pfPwd2.setBounds(155, 110, 180, 40);
		// pfPwd2.addActionListener(this);
		pfPwd2.addKeyListener(this);
		pfPwd2.setFocusTraversalKeysEnabled(false);

		pfPwd2.setEnabled(false);

	}

	// �̸� �޼ҵ�
	private void setNameField() {
		JLabel j4 = new JLabel();
		j4.setText(" �̸�(*)");
		j4.setFont(new Font("����������", Font.PLAIN, 12));
		j4.setBounds(20, 160, 120, 40);
		p.add(j4);

		tfName = new JTextField();
		tfName.setFont(new Font("����������", Font.PLAIN, 12));
		tfName.setDocument((new JTextFieldLimit(10)));
		p.add(tfName);
		tfName.setBounds(155, 160, 180, 40);
		// tfName.addActionListener(this);
		tfName.addKeyListener(this);
		tfName.setFocusTraversalKeysEnabled(false);

		tfName.setEnabled(false);
	}

	// �ڵ��� ��ȣ �޼ҵ�
	private void setTelField() {
		JLabel j5 = new JLabel();
		j5.setText(" �ڵ��� ��ȣ");
		j5.setFont(new Font("����������", Font.PLAIN, 12));
		j5.setBounds(20, 210, 120, 40);
		p.add(j5);

		tfTel1 = new JTextField();
		tfTel1.setDocument((new JTextFieldLimit(3)));
		tfTel2 = new JTextField();
		tfTel2.setDocument((new JTextFieldLimit(4)));
		tfTel3 = new JTextField();
		tfTel3.setDocument((new JTextFieldLimit(4)));
		tfTel1.setFont(new Font("����������", Font.PLAIN, 12));
		tfTel2.setFont(new Font("����������", Font.PLAIN, 12));
		tfTel3.setFont(new Font("����������", Font.PLAIN, 12));
		p.add(tfTel1);
		p.add(tfTel2);
		p.add(tfTel3);
		tfTel1.setBounds(155, 210, 50, 40);
		tfTel2.setBounds(215, 210, 55, 40);
		tfTel3.setBounds(280, 210, 55, 40);

		tfTel1.addKeyListener(this);
		tfTel1.setFocusTraversalKeysEnabled(false);
		tfTel2.addKeyListener(this);
		tfTel2.setFocusTraversalKeysEnabled(false);
		tfTel3.addKeyListener(this);
		tfTel3.setFocusTraversalKeysEnabled(false);

		tfTel1.setEnabled(false);
		tfTel2.setEnabled(false);
		tfTel3.setEnabled(false);
	}

	// �г��� �޼ҵ�
	private void setNicknameField() {
		JLabel j6 = new JLabel();
		j6.setText("�г���(*)");
		j6.setFont(new Font("����������", Font.PLAIN, 12));
		j6.setBounds(20, 260, 120, 40);
		p.add(j6);

		tfNickName = new JTextField();
		tfNickName.setFont(new Font("����������", Font.PLAIN, 12));
		tfNickName.setDocument((new JTextFieldLimit(10)));
		p.add(tfNickName);
		tfNickName.setBounds(155, 260, 180, 40);
		tfNickName.addKeyListener(this);
		tfNickName.setFocusTraversalKeysEnabled(false);

		tfNickName.setEnabled(false);
	}

	// ���� �޼ҵ�
	private void setGenderField() {
		JLabel j7 = new JLabel();
		j7.setText(" ����");
		j7.setFont(new Font("����������", Font.PLAIN, 12));
		j7.setBounds(20, 310, 120, 40);
		p.add(j7);

		rbMan = new JRadioButton("����");
		rbMan.setFont(new Font("����������", Font.PLAIN, 12));
		rbWoman = new JRadioButton("����");
		rbWoman.setFont(new Font("����������", Font.PLAIN, 12));
		ButtonGroup bg1 = new ButtonGroup();
		bg1.add(rbMan);
		bg1.add(rbWoman);
		p.add(rbMan);
		p.add(rbWoman);

		rbMan.setBounds(160, 310, 80, 40);
		rbWoman.setBounds(260, 310, 80, 40);
		rbMan.setSelected(true);

		rbMan.addActionListener(this);
		rbWoman.addActionListener(this);
	}

	// �ƹ�Ÿ �޼ҵ�
	private void setAvatarField() {
		// **���� ������ �ƹ�Ÿ �̹��� �����ϱ�
		JLabel j8 = new JLabel();
		j8.setText(" �ƹ�Ÿ");
		j8.setFont(new Font("����������", Font.PLAIN, 12));
		j8.setBounds(20, 395, 50, 40);
		p.add(j8);

		// �̹��� �ּҺҷ�����
		av1 = new JLabel(new ImageIcon(SetImager.setImage(getClass().getResource("/image/M1.png"), 80, 100)));
		av2 = new JLabel(new ImageIcon(SetImager.setImage(getClass().getResource("/image/M2.png"), 80, 100)));
		
		av1.setBounds(155, 360, 80, 100);
		av2.setBounds(255, 360, 80, 100);

		// �̹��� ���ù�ư
		rbAv1 = new JRadioButton("");
		rbAv2 = new JRadioButton("");
		rbAv1.setSelected(true);
		rbAv1.setBounds(175, 470, 30, 30);
		rbAv2.setBounds(285, 470, 30, 30);

		// ��ư �ߺ�����
		ButtonGroup bg = new ButtonGroup();
		bg.add(rbAv1);
		bg.add(rbAv2);

		// �ǳڿ� �߰�
		p.add(av1);
		p.add(av2);
		p.add(rbAv1);
		p.add(rbAv2);
		rbAv1.addActionListener(this);
		rbAv2.addActionListener(this);

	}

	private void completeMember() {
		// ���Թ�ư �Ϸ�
		btnInsert = new JButton("�Ϸ�");
		btnCancel = new JButton("���");
		btnInsert.setFont(new Font("����������", Font.PLAIN, 12));
		btnCancel.setFont(new Font("����������", Font.PLAIN, 12));
		p.add(btnInsert);
		p.add(btnCancel);
		btnInsert.setBounds(110, 510, 80, 30);
		btnCancel.setBounds(200, 510, 80, 30);
		btnInsert.setEnabled(false);
		this.setSize(380, 580);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == rbMan) {
			Gender = 'M';
		} else if (e.getSource() == rbWoman) {
			Gender = 'W';
		} else if (e.getSource() == rbAv1) {
			Avatar = "1";
		} else if (e.getSource() == rbAv2) {
			Avatar = "2";
		}
		av1.setIcon(new ImageIcon(
				SetImager.setImage((getClass().getResource("/image/" + Gender + "1.png")), av1.getWidth(), av1.getHeight())));
		av2.setIcon(new ImageIcon(
				SetImager.setImage((getClass().getResource("/image/" + Gender + "2.png")), av2.getWidth(), av2.getHeight())));

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == tfId && e.getKeyCode() == KeyEvent.VK_TAB) {
			if (tfId.getText().length() < 4) {
				JOptionPane.showMessageDialog(this, "���̵�� 4�ڸ� �̻� �Է� �ϼ���");
				tfId.setText("");
				return;
			}
			btnCheckId.requestFocusInWindow();
		} else if (e.getSource() == pfPwd1 && e.getKeyCode() == KeyEvent.VK_TAB) {
			if (pfPwd1.getPassword().length < 4) {
				JOptionPane.showMessageDialog(this, "��й�ȣ�� 4�ڸ� �̻� �Է� �ϼ���");
				pfPwd1.setText("");
				return;
			}
			pfPwd2.requestFocusInWindow();
		} else if (e.getSource() == pfPwd2 && e.getKeyCode() == KeyEvent.VK_TAB) {
			if (pfPwd2.getPassword().length < 4) {
				JOptionPane.showMessageDialog(this, "4�ڸ� �̻� ��й�ȣ�� �Է��� �ּ���");
				return;
			}
			if (pfPwd1.getPassword().length != pfPwd2.getPassword().length) {
				JOptionPane.showMessageDialog(this, "���� ��� ��ȣ�� �Է� �ϼ���");
				pfPwd2.setText("");
				return;
			} else {
				for (int i = 0; i < pfPwd1.getPassword().length; i++) {
					if (pfPwd1.getPassword()[i] != pfPwd2.getPassword()[i]) {
						JOptionPane.showMessageDialog(this, "���� ��� ��ȣ�� �Է� �ϼ���");
						pfPwd2.setText("");
						return;
					}
				}
			}
			tfName.requestFocusInWindow();
			return;
		} else if (e.getSource() == tfName && e.getKeyCode() == KeyEvent.VK_TAB) {
			if (tfName.getText().length() < 1) {
				JOptionPane.showMessageDialog(this, "�̸��� �ʼ��Է� �����Դϴ�.");
				return;
			}
			tfTel1.requestFocusInWindow();
		} else if (e.getSource() == tfTel1 && e.getKeyCode() == KeyEvent.VK_TAB) {
			tfTel2.requestFocusInWindow();
			return;
		} else if (e.getSource() == tfTel2 && e.getKeyCode() == KeyEvent.VK_TAB) {
			tfTel3.requestFocusInWindow();
			return;
		} else if (e.getSource() == tfTel3 && e.getKeyCode() == KeyEvent.VK_TAB) {
			tfNickName.requestFocusInWindow();
			return;
		} else if (e.getSource() == tfNickName && e.getKeyCode() == KeyEvent.VK_TAB) {
			if (tfNickName.getText().length() < 2) {
				JOptionPane.showMessageDialog(this, "�г����� 2�� �̻� �Է����ּ���.");
				return;
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}
}