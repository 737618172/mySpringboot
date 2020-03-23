package com.io;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    static byte[] bytes = new byte[1024];
    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(7890);
        System.out.println("wait");
        while (true){
            Socket accept = server.accept();
            System.out.println("已连接");
            int read = accept.getInputStream().read(bytes);
            System.out.println(new String(bytes));
        }
    }
}
