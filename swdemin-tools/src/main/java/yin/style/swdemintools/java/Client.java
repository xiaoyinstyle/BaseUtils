package yin.style.swdemintools.java;

import java.io.*;
import java.net.*;

public class Client {
    private Socket socket;                  /*定义socket连接*/
    private BufferedReader reader;          /*从字符输入流读取文本*/
    private PrintWriter writer;             /*打印到文本输出流*/

    public Client(String ipAddress, int serverPort) {                 /*定义客户端端口号*/
        try {
            socket = new Socket(ipAddress, serverPort);
            reader = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));        /*标准输入输出流*/
            writer = new PrintWriter(socket.getOutputStream());
            System.out.println("[" + ipAddress + ":" + serverPort + "]" + "starts success......");

            while (true) {

                BufferedReader in = new BufferedReader(
                        new InputStreamReader(System.in));
                String message = in.readLine();
                writer.println(message);
                writer.flush();                             /*不关闭流，清空输入缓存区*/

                if ("finish".equals(message)) {
                    System.out.println("Server stopped listening");
                    break;
                }
                String received = reader.readLine();
                System.out.println(received);
            }

            writer.close();
            reader.close();            /*关闭流*/
            socket.close();

        } catch (UnknownHostException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();       /*在命令行打印异常信息在程序中出错的位置及原因*/
        }
    }

    static int SERVICE_PORT = 10101;  /*绑定端口号*/
    // static       String IP_ADDRESS = "106.14.219.221";  /*绑定端口号*/
    static String IP_ADDRESS = "127.0.0.1";  /*绑定端口号*/

    public static void main(String[] args) {
        System.out.println("Client starts running......");
        new Client(IP_ADDRESS, SERVICE_PORT);      /*绑定端口号*/
    }
}