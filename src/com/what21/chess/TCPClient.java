package com.what21.chess;
import java.awt.*;

import javax.swing.*;

import java.awt.event.*;
import java.io.*;
import java.net.*;
/**
 * �ͷ���
 */
public class TCPClient{

    static Socket s;
    setMenu t;
    public TCPClient(){
    	 //���ӷ�����
        connetServer();
        //���GUI����
       t=new setMenu(s);
        //t.setBounds(0,0,200,200);
        //add(t);
        //�������������߳�
        new Thread(new recvMsg(s)).start();
    }
    /**
     * ���ӷ�����
     */
    private static void connetServer() {
        //�����ͷ��˵�Socket����ָ��Ŀ�������Ͷ˿�
        try {
        //"10.136.128.26"�Ƿ��ʷ�������IP��ַ��30001�Ƕ˿ں�
            s = new Socket("123.206.58.247",30001);
        } catch (Exception e) {
            System.out.println("�ͷ������ӷ�����ʧ��");
            e.printStackTrace();
        }               
    }
}