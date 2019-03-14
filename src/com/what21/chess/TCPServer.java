package com.what21.chess;
     /*服务器*/
import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class TCPServer {
             //创建一个集合，集合存放客服端的对象
    ArrayList<Socket> arrayListSocket = new ArrayList<Socket>();
    TCPServer() {
        System.out.println("------监听---------");
        ServerSocket ss;
        try {
            ss = new ServerSocket(30001);
            while (true) {                                                   
                                //等待客服端连接，一旦有客服端连接服务器，将客服端对象存放到集合中
                Socket socket = ss.accept();
                arrayListSocket.add(socket);
                String ip = socket.getInetAddress().getHostAddress();
                System.out.println("ip: " + ip + " 上线了！");
                new Thread(new acceptClient(socket, ip)).start();   //新建一个客服端的线程
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    class acceptClient implements Runnable {
        Socket s;
        String ip;
        acceptClient(Socket s, String ip) {
            this.s = s;
            this.ip = ip;
        }
        @Override
        public void run() {
            try {
                // 通知用户上线消息
                for (int i = 0; i < arrayListSocket.size(); i++) {
                    Socket client = arrayListSocket.get(i);
                    // 下面PrintWriter是可以自带刷新缓存工作，可以简化写入流的操作，
                    PrintWriter pout = new PrintWriter(
                                 client.getOutputStream(), true);
                    pout.println(ip + "上线了！！！");
                }
                while (true) {
                    // 读取Socket读取流中的数据
                    BufferedReader bufIn = new BufferedReader(
                            new InputStreamReader(s.getInputStream()));
                    String line = bufIn.readLine();
                    // 下面PrintWriter是可以自带刷新缓存工作，可以简化写入流的操作，
                    //通过for（）循序服务器将数据发送的arrayListSocket集合中所有客服端
                    for (int i = 0; i < arrayListSocket.size(); i++) {
                        Socket client = arrayListSocket.get(i);
                        PrintWriter pout = new PrintWriter(
                                client.getOutputStream(), true);
                        pout.println(line);
                    }
                }

            } catch (IOException e) {
                System.out.println("接受:" + s.getInetAddress() + " 信息失败");
                e.printStackTrace();
            }
        }

    }
    public static void main(String[] args) throws Exception, IOException {
        //创建服务器对象
        new TCPServer();
    }
}