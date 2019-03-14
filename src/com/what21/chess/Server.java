package com.what21.chess;
import java.io.*;
import java.net.*;


import javax.swing.JOptionPane;

public class Server extends Thread{
	 private  static ServerSocket serverSock;
    static boolean connect=false;
    private static Socket socket=null;
    DataOutputStream out;
    DataInputStream in;
   // CheckerBoard p=new CheckerBoard();
    public boolean setSrever(){
      	try{
      		serverSock=new ServerSocket(6666);
      	}catch(IOException e){
      		JOptionPane.showMessageDialog(null, "服务器启动失败");
      		return false;
      		}
      	JOptionPane.showMessageDialog(null, "成功建立服务\n等待好友加入！");
      	this.start();
      	return true;
      	}
public void run()
{
	while(true)
	{try{socket=serverSock.accept();}
		catch(Exception ee){};
		if(socket!=null){JOptionPane.showMessageDialog(null, "好友成功加入！");
		break;
		}}
}
    void setChess(int i,int j)
    {
    	try{
    		OutputStream os=socket.getOutputStream();
			out=new DataOutputStream(os);
			String s="";
			s+=i;
    	out.writeUTF(s);
    	s="";
    	s+=j;
    	out.writeUTF(s);
    	}
    	catch(IOException ee){
			return ;
		}
    	return;
    }
    int getChess()
    {     int a=0;
    	try{
    		    String s,s2;
    			InputStream is=socket.getInputStream();
    			in=new DataInputStream(is);
	    	s=in.readUTF();
	    	if(s.length()>2)
	    	a=getChess();  
	    	else a=Integer.parseInt(s);
    	}
	    	catch(IOException ee){	
	    		JOptionPane.showMessageDialog(null,"好友已离开\n先虐把电脑吧");
	    		return -1;
    		}	
    	//p.canSetChess=1;
    	return a;
    }
}
