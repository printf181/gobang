package com.what21.chess;
import java.awt.*;

import javax.swing.*;

import java.awt.event.*;
import java.io.*;
import java.net.*;
/**
 * 客服端
 */
public class TCPClient{

    static Socket s;
    setMenu t;
    public TCPClient(){
    	 //连接服务器
        connetServer();
        //设计GUI界面
       t=new setMenu(s);
        //t.setBounds(0,0,200,200);
        //add(t);
        //启动接收数据线程
        new Thread(new recvMsg(s)).start();
    }
    /**
     * 连接服务器
     */
    private static void connetServer() {
        //创建客服端的Socket服务，指定目的主机和端口
        try {
        //"10.136.128.26"是访问服务器的IP地址，30001是端口号
            s = new Socket("123.206.58.247",30001);
        } catch (Exception e) {
            System.out.println("客服端连接服务器失败");
            e.printStackTrace();
        }               
    }
}