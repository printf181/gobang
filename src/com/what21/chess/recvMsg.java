package com.what21.chess;
import java.awt.*;
import javax.swing.*;


import java.awt.event.*;
import java.io.*;
import java.net.*;
//接受信息的子类    Runnable接口
class recvMsg implements Runnable{
    private Socket s;
    private JTextArea messageArea;
    recvMsg(Socket s){
        this.s = s;
        this.messageArea = setMenu.messageArea;
    }
    @Override
    public void run() {
        //定义一个Socket读取流，读取服务器端返回的信息
                BufferedReader bufIn = null;
                System.out.println("-------rev----------");
                while(true){
                    try {
                        bufIn = new BufferedReader(new InputStreamReader(s.getInputStream()));
                    } catch (IOException e1) {
                        System.out.println("接收信息失败");
                        e1.printStackTrace();
                    }
                    try {
                        String recvMsg = bufIn.readLine();
                        messageArea.append(recvMsg+"\r\n");
                    } catch (IOException e) {

                        e.printStackTrace();
                    }
                }
    }
}
