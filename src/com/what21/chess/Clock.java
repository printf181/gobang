package com.what21.chess;

import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.Timer;
import java.awt.geom.*;


class Clock extends JPanel{
	   Thread timer;
	   int oldh=-1,oldm=-1,olds=-1,hq=-1,mq=-1,sq=-1;
	   final double RAD=Math.PI/180.0;
	   public Clock(){
		   super();
		   this.setOpaque(false);
		   ActionListener taskPerformer=new ActionListener(){
			   public void actionPerformed(ActionEvent evt){
				   repaint();
			   }
		   };
		   new Timer(1000,taskPerformer).start();
	   }
	public void paint(Graphics g){
		   int h,m,s,q,x1,x2,y1,y2;
		   int x0=655,y0=60;
		   int R=50;
		   Date d=new Date();
		   h=d.getHours();
		   m=d.getMinutes();
		   s=d.getSeconds();
		   g.setColor(Color.LIGHT_GRAY);
		   g.fillOval(x0-R, y0-R, 2*R, 2*R);
		   g.setColor(Color.black);
		   g.drawOval(x0-R+3, y0-R+3, 2*R-7, 2*R-7);
		   for(int i=0;i<12;i++){
			   y1=(int)(y0-Math.cos(i*30*RAD)*R*(float)4/5);
			   x1=(int)(x0+Math.sin(i*30*RAD)*R*(float)4/5);
			   y2=(int)(y0-Math.cos(i*30*RAD)*(R-3));
			   x2=(int)(x0+Math.sin(i*30*RAD)*(R-3));
			   g.drawLine(x1, y1, x2, y2);
		   }
		   q=h*30+m/2;
		   drawHand(g,x0,y0,10,R*4/7,q,Color.blue);
		   q=m*6+s/10;
		   drawHand(g,x0,y0,5,R*2/3,q,Color.yellow);
		   q=s*6;
		   drawHand(g,x0,y0,2,R*4/5,q,Color.red);
		   g.setColor(Color.black);
		   g.drawOval(x0-2, y0-2, 4, 4);
	   }
	   void drawHand(Graphics g,int x0,int y0,int w,int L,int q,Color c){
		   int x[]=new int[4];
		   int y[]=new int[4];
		   x[0]=x0-(int)(Math.sin(RAD*q)*L/6);
		   y[0]=y0+(int)(Math.cos(RAD*q)*L/6);
		   x[1]=x0+(int)(Math.sin(RAD*(90-q))*w);
		   y[1]=y0+(int)(Math.cos(RAD*(90-q))*w);
		   x[2]=x0+(int)(Math.sin(RAD*q)*L);
		   y[2]=y0-(int)(Math.cos(RAD*q)*L);
		   x[3]=x0-(int)(Math.sin(RAD*(90-q))*w);
		   y[3]=y0-(int)(Math.cos(RAD*(90-q))*w);
	       g.setColor(c);
	       g.fillPolygon(x,y,4);
	   }
}
