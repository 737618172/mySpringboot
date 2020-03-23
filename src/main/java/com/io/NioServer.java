package com.io;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;

public class NioServer {

    public static void main(String[] args) throws IOException {

        ServerSocketChannel channel = ServerSocketChannel.open();
        channel.configureBlocking(false);
//        ServerSocket socket = channel.socket();
        channel.bind(new InetSocketAddress(9000));
        Selector selector = Selector.open();
        SelectionKey register = channel.register(selector,SelectionKey.OP_ACCEPT);

        while(true){
            int select = selector.select();
            System.out.println(select);
        }
    }
}
