package com.io.bio;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) throws IOException {
        Socket client = new Socket("127.0.0.1",7890);
        Scanner scanner = new Scanner(System.in);
        String next = scanner.next();
        byte[] bytes = next.getBytes();
        client.getOutputStream().write(bytes);

    }
}
