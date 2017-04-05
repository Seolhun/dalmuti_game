package com.sist.client;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.URL;

	
public class UserInfor extends JFrame implements ActionListener {
	JLabel privateInfo,avatar,IdLabel,NickLabel,LevelLabel,PointLabel,LevelField;
	JTextField IdField,NickField,PointField;
	JPanel p;
	JButton Okbtn;
	
	public UserInfor(){
		//������ �Ҵ�
		SetUserInfor();
	}
		
	public void SetUserInfor(){
		//��ġ
		privateInfo=new JLabel("��������");
		privateInfo.setHorizontalAlignment(JLabel.CENTER);
		privateInfo.setFont(new Font("����������", Font.PLAIN, 12));
		avatar =new JLabel();
		IdLabel =new JLabel("���̵�",JLabel.CENTER);
		NickLabel =new JLabel("��ȭ��",JLabel.CENTER);
		LevelLabel =new JLabel("��  ��",JLabel.CENTER);
		PointLabel =new JLabel("��  ��",JLabel.CENTER); 
		IdField =new JTextField(); // �������� ���̵� ������ �ҷ����� �Է� �ʿ�
		NickField =new JTextField();  // �������� ��ȭ�� ������ �ҷ����� �Է� �ʿ�
		LevelField =new	JLabel(); //��� ������
		PointField =new JTextField();  // �������� ���� ������ �ҷ����� �Է� �ʿ�
		Okbtn =new JButton("Ȯ  ��");
		Okbtn.setFont(new Font("����������", Font.PLAIN, 12));
		
		IdLabel.setFont(new Font("����������", Font.PLAIN, 12));
		NickLabel.setFont(new Font("����������", Font.PLAIN, 12));
		LevelLabel.setFont(new Font("����������", Font.PLAIN, 12));
		PointLabel.setFont(new Font("����������", Font.PLAIN, 12));
		IdField.setFont(new Font("����������", Font.PLAIN, 12));
		NickField.setFont(new Font("����������", Font.PLAIN, 12));	
		LevelField.setFont(new Font("����������", Font.PLAIN, 12));
		PointField.setFont(new Font("����������", Font.PLAIN, 12));
		
		IdField.setEditable(false);
		NickField.setEditable(false);
		PointField.setEditable(false);
		
		p=new JPanel();
		
		p.setLayout(null);
		privateInfo.setBounds(10, 10, 480, 30);
		avatar.setBounds(5, 20, 145, 230);
		
		IdLabel.setBounds(155, 55, 60, 40);
		NickLabel.setBounds(155, 100, 60, 40);
		LevelLabel.setBounds(155, 145, 60, 40);
		PointLabel.setBounds(155, 190, 60, 40);
		
		IdField.setBounds(220, 55, 100, 40);
		NickField.setBounds(220, 100, 100, 40);
		LevelField.setBounds(220, 145, 100, 40);
		PointField.setBounds(220, 190, 100, 40);
		
		Okbtn.setBounds(135, 255, 70, 40);
		
		//�г� �߰�
		p.add(privateInfo); p.add(avatar); p.add(IdLabel);
		p.add(NickLabel); p.add(LevelLabel); p.add(PointLabel);
		p.add(IdField);
		p.add(NickField); p.add(LevelField); p.add(PointField);
		p.add(Okbtn);
		add(p);
		Okbtn.addActionListener(this);
		setSize(340, 340);
		setLocation(300,250);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
		
	public static void main (String[] args){
		new UserInfor();
	}
	public void setLabelImage(String avt, String lv){
		avatar.setIcon(new ImageIcon(SetImager.setImage(getClass().getResource("/image/"+avt+".png"), avatar.getWidth(), avatar.getHeight())));
		LevelField.setIcon(new ImageIcon(getClass().getResource("/image/rank"+lv+".png")));
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==Okbtn){
			dispose();
			return;
		}
	}
}
