package com.io.bio;

import java.io.IOException;
import java.net.Socket;

public class Client2 {

    public static void main(String[] args) throws IOException {
        Socket client = new Socket("127.0.0.1",7890);
        byte[] bytes = "234".getBytes();
        client.getOutputStream().write(bytes);

    }
}
