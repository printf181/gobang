package com.what21.chess;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.ImageObserver;
import java.io.IOException;

import javax.swing.*;
/*
 * 画棋子棋盘，对外接口paint()
 * 
 * */
public class CheckerBoard extends JPanel implements MouseListener{
	private static final long serialVersionUID = 4802277944291967336L;
public String qipan_name="qipan0.jpg";//棋盘的图片名
public String qizi1_name="c5.png",qizi2_name="c0.png";//棋子的图片名
public int row=15,col=15;//横纵数目
public  int num[][]=new int[15][15];//棋子的状态0为空，1是玩家1，2是电脑或玩家2
public static boolean peo_peo=false;//是否是人人对战,1代表是，默认人机，联机被看为人机
public static int count=0;//判断人人下棋者
public static int Inter=0;//联机为1，默认为零
public static int serve=0;//联机的状态，先手为1，后手为0，建立服务器的为先手
public static int canSetChess=1;//是否可以下棋
public static int step=0;
private static int hui[][][]=new int[225][15][15];
Internet int1=new Internet();//客户端
Server ser=new Server();//服务端
public  void paint(Graphics g)
{
	super.paint(g);
	Image img = new ImageIcon("img/" + qipan_name).getImage();
	// 调入棋盘图片
	g.drawImage(img, 0, 0, 567, 567, this);
	Image c1 = new ImageIcon("img/" + qizi1_name).getImage();
	Image c2 = new ImageIcon("img/" + qizi2_name).getImage();
	//绘制棋盘
	for (int i = 0; i < num.length; i++) {
		for (int j = 0; j < num[i].length; j++) {
			if (num[i][j] == 1) {
				g.drawImage(c1, i * 35 + 20, j * 35 + 20, 35, 35, this);
			} else if (num[i][j] == 2) {
				g.drawImage(c2, i * 35 + 20, j * 35 + 20, 35, 35, this);
			}
		}
		// 重绘棋子
	}
	
}


int maxi=0;//电脑的i，j位置
int maxj=0;
private void people(int i,int j)//人下子
{
	Graphics g = this.getGraphics();
	Computer com=new Computer();
	Image c1;
	if(num[i][j]!=0)
	{
		JOptionPane.showMessageDialog(null, "该位置有棋子，请重新落子");
		return ;
	}
	else 
	{
		if(peo_peo)
		{
			count++;
			if(count%2==1)
			{
				 c1 = new ImageIcon("img/" + qizi1_name).getImage();
				num[i][j]=1;
				com.copyChess(num);
				if(com.getSame(i,j,1)==5){JOptionPane.showMessageDialog(null,"先手赢了");
				canSetChess=3;
			}}
				else {
					 c1 = new ImageIcon("img/" + qizi2_name).getImage();
				num[i][j]=2;
				com.copyChess(num);
				if(com.getSame(i,j,2)==5){JOptionPane.showMessageDialog(null,"后手赢了");
				canSetChess=3;}
		}}
		else{
			 c1 = new ImageIcon("img/" + qizi1_name).getImage();
			 canSetChess=0;
		num[i][j]=1;
		com.copyChess(num);
		if(com.getSame(i,j,1)==5){JOptionPane.showMessageDialog(null,"厉害了,您赢了");
		canSetChess=3;
		}
		}	
		g.drawImage(c1, i * 35 + 20, j * 35 + 20, 35, 35, this);
		if(Inter==1)
		{
			if(serve==0)
			int1.setChess(i, j);
			else if(serve==1)
				ser.setChess(i, j);
			canSetChess=0;
		}
		step++;
	}
}


class getC extends Thread
{
	Computer com=new Computer();
	int i,j;

	public void run()
	{    
			try{
				   if(serve==0)
					{
					   i=int1.getChess();
					j=int1.getChess();}
				   else 
				   {
					   i=ser.getChess();
					   j=ser.getChess();
				   }
				   if(i==-1||j==-1){
					   Inter=0;
					   canSetChess=3;
				   computer();
				   }
				   else {
					   num[i][j]=2;
				   repaint();	
				   com.copyChess(num);
				   if(com.getSame(i,j,2)==5){JOptionPane.showMessageDialog(null,"很遗憾，您输了");
					canSetChess=3;
					}
				   else canSetChess=1;
				return ;
				}
			}catch(Exception e){
				System.out.println(e.getMessage());
				e.printStackTrace();
			}
	}
}
 

private void computer()//电脑下子
{
	Graphics g = this.getGraphics();

	Computer com=new Computer();
	
	if(Inter==0)
	{
		if(step==1)
			{for(int i=6;i<=8;i++)
				for(int j=6;j<=8;j++)
					if(num[i][j]==0){maxi=i;maxj=j;break;}
			step++;
			canSetChess=1;}
		else {com.copyChess(num);
	 int maxequal=-500000;
	 int k=0;
	 int wuzi=0;
	 maxi=maxj=1;
	for(int i=0;i<15;i++)
		{for(int j=0;j<15;j++)
		{
			if(num[i][j]==0)
				{
				if(com.generator(i, j)==true)
				{com.num[i][j]=2;
				k=com. alphaBeta1(1,-10000000,10000000,i,j);
				com.num[i][j]=0;
				if(maxequal<k)
			{
				maxequal=k;
				maxi=i;
				maxj=j;	
			}	
				if(com.getQuan(i,j,2)>=100000)
				{
					maxi=i;
				maxj=j;wuzi=1;break;}
				}
				if(wuzi==1)break;
				}
			
		}if(wuzi==1)break;
		}}
	Image c2 = new ImageIcon("img/" + qizi2_name).getImage();
    System.out.println(maxi+" "+maxj);
	g.drawImage(c2, maxi * 35 + 20, maxj * 35 + 20, 35, 35, this);
	num[maxi][maxj]=2;
	com.copyChess(num);
	canSetChess=1;
	if(com.getSame(maxi,maxj,2)>=5)
		{JOptionPane.showMessageDialog(null,"很遗憾，您输了！");
		canSetChess=3;
		}
}
	if(Inter==1) {
		
		getC t=new getC();
		t.start(); 
		canSetChess=3;
	}
	
}
@Override
//监听鼠标的动静
public void mouseClicked(MouseEvent e) {
	// TODO Auto-generated method stub
	int x=e.getX();
int y=e.getY();
int i = (x - 25) / 35;
int j = (y - 75) / 35;
 
if(i>=0&&j>=0&&i<=14&&j<=14&&canSetChess==1)	
	{
		people(i,j);
	}

    if(i>=0&&j>=0&&i<=14&&j<=14&&canSetChess==0)
 {
		 computer();
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
