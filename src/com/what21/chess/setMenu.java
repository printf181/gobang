package com.what21.chess;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
/**
* 创建GUI界面的类
*/
class setMenu extends JPanel{
    static JButton btn_send;
    static JButton btn_clear;
    static JButton btn_close;
    static JTextField txf_name;
    static JTextField txf_send;
    static JTextArea messageArea;
     Socket s;
    setMenu(Socket s){
        this.s = s;
        createMenu();
        someListener();
    }

/**
* 相关事件监听操作
* */
    private void someListener() {
        btn_send.addActionListener(new SendMsg3(s));
        btn_close.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        txf_send.addKeyListener(new SendMsg3(s));
        btn_clear.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                txf_send.setText("");
            }
        });
        messageArea.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                e.consume();// 屏蔽掉法输入
            }
        });
    }

    private void createMenu() {
     this.setLayout(null);
    JLabel label_name = new JLabel("名字：");
    label_name.setBounds(0,0, 50, 20);
    this.add(label_name);
    txf_name = new JTextField(10);
    txf_name.setBounds(50,0,80,20);
    this.add(txf_name);
    messageArea = new JTextArea();
    messageArea.setBounds(0,20,150, 120);
    this.add(messageArea);
    JLabel label_send = new JLabel("发送内容：");
    label_send.setBounds(0, 150, 70 , 20);
    this.add(label_send);
    txf_send = new JTextField(30);
    txf_send.setBounds(70, 150, 80, 20);
    this.add(txf_send);
    btn_send = new JButton("发送");
    btn_clear = new JButton("清除");
    btn_close = new JButton("关闭");
    }
    
    
    class SendMsg3 implements ActionListener, KeyListener {

        Socket s;
        SendMsg3(Socket s){
            this.s =  s;
        }
        @Override
        public void keyTyped(KeyEvent e) {
        }
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == e.VK_ENTER) {
                sendMsg();
            }
        }
        public void keyReleased(KeyEvent e) {
        }
        public void actionPerformed(ActionEvent e) {
            sendMsg();

        }


        /**
         * 发送信息
         */
        private void sendMsg() {
            // 如果名字为空 给出提示信息
            if(txf_name.getText().isEmpty()){
                new JOptionPane().showMessageDialog(null, "请一定要取别名！");
            }
            String line = txf_name.getText() + ": "+txf_send.getText();
            try {
                if (!line.isEmpty()) {
                    //下面PrintWriter是可以自带刷新缓存工作，可以简化写入流的操作，
                    PrintWriter pout = new PrintWriter(s.getOutputStream(),true);
                    pout.println(line);
                    System.out.print("input:"+line);
                    txf_send.setText("");
                } else {
                    System.out.println("发送数据不为空，请重新输入");
                    new JOptionPane().showMessageDialog(null, "发送数据不为空，请重新输入！");
                }
            } catch (Exception e1) {
                // TODO Auto-generated catch block
                System.out.println("发送失败");
                e1.printStackTrace();
            }
        }
    }
}