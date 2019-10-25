package yin.style.swdemintools.java;

import java.io.*;
import java.net.*;

public class Server extends ServerSocket {
    public Server(int serverPort) throws IOException {
        super(serverPort);
        try {
            while (true) {
                Socket socket = accept();
                new ServerThread(socket);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            close();
        }
    }

    class ServerThread extends Thread       /*建立服务端线程*/ {
        private Socket socket;
        private BufferedReader in;
        private PrintWriter out;

        public ServerThread(Socket s) throws IOException {
            this.socket = s;
            in = new BufferedReader(new InputStreamReader(
                    socket.getInputStream(), "GB2312"));        /*国标码*/
            out = new PrintWriter(socket.getOutputStream(), true);
            start();                /*开始线程*/
        }

        public void run() {
            try {
                while (true) {
                    String line = in.readLine();
                    if ("finish".equals(line)) {
                        System.out.println("Server stopped listening");
                        break;
                    }
                    System.out.println("received：" + line);
                    String msg = "'" + line + "' has received.";
                    out.println(msg);
                    out.flush();
                }
                out.close();
                in.close();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws IOException {
        int SERVICE_PORT = 10101;  /*绑定端口号*/
        System.out.println("[" + SERVICE_PORT + "]" + "Server starts running......");
        new Server(10101);       /*绑定端口号*/
    }

}