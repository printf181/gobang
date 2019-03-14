package com.what21.chess;


import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.event.*;
/*
 * ������壬ѡ����
 * */
public class CheckerFrame extends JFrame {
	private static int qipann=0;
	private static int qizin=0;
	Clock c;
	TCPClient tc;
	
	Internet inte=new Internet();
	Server ser=new Server();
	private static final long serialVersionUID = 4802277944291967336L;
	CheckerBoard p = null;
	
	  Label L1;
	  JButton L2;
	  JButton L3;
	  TextField text1;
	  Button B1;
	  
	public CheckerFrame() {
		this.setSize(750, 600);
		ImageIcon background = new ImageIcon("img/bound.jpg");  //����ͼƬ
		   JLabel label = new JLabel(background);  
		           // �ѱ�ǩ�Ĵ�Сλ������ΪͼƬ�պ�����������  
		         label.setBounds(0, 0, this.getWidth(), this.getHeight());  
		          // �����ݴ���ת��ΪJPanel���������÷���setOpaque()��ʹ���ݴ���͸��  
		          JPanel imagePanel = (JPanel) this.getContentPane();  
		           imagePanel.setOpaque(false);  
		           // �ѱ���ͼƬ���ӵ��ֲ㴰�����ײ���Ϊ����  
		           this.getLayeredPane().add(label, new Integer(Integer.MIN_VALUE));  
		           //���ÿɼ�  
		           
		           //������ս
		     	 text1=new TextField();
		    	  text1.setBounds(600, 250, 100, 25);
		    	  L2=new JButton("����");
		    	  L2.setBounds(600, 275, 100, 25);
		    	  L3=new JButton("��������");
		    	  L3.setBounds(600, 200, 100, 25);
		    	  add(text1);
		    	  add(L3);
		    	  add(L2);
		     	  
		     //�����ʱ��	  
		c=new Clock();
    	tc=new TCPClient();
		p = new CheckerBoard();
       // setLayout(null);	
		p.setBounds(0, 0, 576,576);
    	c.setBounds(550, 0,75,75);
		tc.t.setBounds(576, 350, 180, 180);
		this.add(tc.t);
		this.setLocation(200, 100);	
		this.setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	  this.add(p);
    	  this.add(c);
    	  
    	  JMenuBar menuber = new JMenuBar();
    		JMenu jm1 = new JMenu("ѡ��");
    		JMenu jm2 = new JMenu("����");
    		JMenu jm3 = new JMenu("����");
    		JMenuItem jm1_1 = new JMenuItem("���¿�ʼ");
    		JMenuItem jm1_3 = new JMenuItem("�������");
    		JMenuItem jm1_4 = new JMenuItem("��Һ���");
    		JMenuItem jm1_2 = new JMenuItem("�˳���Ϸ");
    		JMenuItem jm2_1 = new JMenuItem("��������");
    		JMenuItem jm2_2 = new JMenuItem("��������");
    		JMenuItem jm2_3 = new JMenuItem("���˻�");
    		JMenuItem jm2_4 = new JMenuItem("�����˻�");
    		JMenuItem jm2_6 = new JMenuItem("�е��˻�");
    		JMenuItem jm2_5 = new JMenuItem("��������");
    		JMenuItem jm3_1 = new JMenuItem("��������");

		jm1.add(jm1_1);
		jm1.add(jm1_2);
		jm1.add(jm1_3);
		jm1.add(jm1_4);
		jm2.add(jm2_1);
		jm2.add(jm2_2);
		jm2.add(jm2_3);
		jm2.add(jm2_6);
		jm2.add(jm2_4);
		jm2.add(jm2_5);
		jm3.add(jm3_1);
		menuber.add(jm1);
		menuber.add(jm2);
		menuber.add(jm3);
//		add(p);
		this.setJMenuBar(menuber);
		this.addMouseListener(p);
		// �����鹹�ཨ������
		jm1_2.addActionListener(new ActionListener() {
			// �����鹹��
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		jm1_1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				init();
				p.canSetChess = 1;
				if(p.Inter==1&&p.serve==0)p.canSetChess = 0;
				p.step=0;
				repaint();
			}
		});
		jm1_3.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				init();
				p.canSetChess = 1;
				p.step=0;
				p.Inter=0;
				repaint();
			}
		});
		jm1_4.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				init();
				p.num[7][7]=2;
				p.canSetChess = 1;
				p.Inter=0;
				repaint();
			}
		});
		jm2_1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
                  qipann++;
				String qipan_name = "qipan" + qipann%3 + ".jpg";
				p.qipan_name = qipan_name;
				repaint();
			}
		});
		jm2_2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				qizin++;
				String qizi1_name = "c" + qizin%5 + ".png";
				String qizi2_name = "c" + (qizin + 1)%5 + ".png";
				p.qizi1_name = qizi1_name;
				p.qizi2_name = qizi2_name;
				repaint();
			}
		});
		jm2_3.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) {
				init();
				Computer com=new Computer();
				p.peo_peo=false;
				p.canSetChess = 1;
				p.step=0;
				com.easy();
				p.Inter=0;
				repaint();
			}
			
		}	
				);
		jm2_4.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) {
				init();
				Computer com=new Computer();
				p.peo_peo=false;
				p.canSetChess = 1;
				p.step=0;
				com.trouble();
				p.Inter=0;
				repaint();
			}
			
		}	
				);
		jm2_6.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) {
				init();
				Computer com=new Computer();
				p.peo_peo=false;
				p.canSetChess = 1;
				p.step=0;
				com.trouble1();
				p.Inter=0;
				repaint();
			}
			
		}	
				);
		jm2_5.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) {
				init();
			p.canSetChess = 1;
			p.peo_peo=true;
			p.count=0;
			p.Inter=0;
			repaint();
			}
			
		}	
				);
		jm3_1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String msg = "��������\n" + "1�����������;\n" + "2���γ�5��ͬɫ���Ӽ�ΪӮ;\n\n\n";
				JOptionPane.showMessageDialog(null, msg);

			}
		});
		L2.addActionListener(new ActionListener() {
  			// �����鹹��
  			@Override
  			public void actionPerformed(ActionEvent e) {
  				String s=text1.getText();        //-----------------------------		
  				JOptionPane.showMessageDialog(null,s+"�������ӣ����Ժ�");
  				boolean k=inte.send(s);
  				if(k==true)
  					{JOptionPane.showMessageDialog(null,"���Ӻ��ѳɹ�");
  					init();
  					p.canSetChess = 0;
  					p.peo_peo=false;
  					p.count=0;
  					p.Inter=1;
  					p.serve=0;
  					repaint();
  					}
  				else JOptionPane.showMessageDialog(null,s+"����ʧ��");
  			}
  		});
		
		L3.addActionListener(new ActionListener() {
  			// �����鹹��
  			@Override
  			public void actionPerformed(ActionEvent e) {
  				 boolean k=ser.setSrever();
  				if(k==true)
  					{
  					init();
  					p.canSetChess = 1;
  					p.peo_peo=false;
  					p.count=0;
  					p.Inter=1;
  					p.serve=1;
  					repaint();
  					}
  			}
  		});
		this.setIconImage(Toolkit.getDefaultToolkit().createImage("img/c5.png"));
		this.setVisible(true);
	}
	
	private void init() {
		// TODO Auto-generated method stub
		
		for (int i = 0; i < p.row; i++) {
			for (int j = 0; j < p.col; j++) {
				p.num[i][j] = 0;
			}
		}
	}
}
