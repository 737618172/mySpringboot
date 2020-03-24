package com.io.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class NioClient {
    public static void main(String[] args) throws IOException, InterruptedException {

        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.configureBlocking(false);
        Selector selector = Selector.open();
        int interestSet = SelectionKey.OP_READ | SelectionKey.OP_WRITE;

        socketChannel.register(selector, interestSet);
        boolean connect = socketChannel.connect(new InetSocketAddress(9000));
        if(!connect){
            while(!socketChannel.finishConnect()){
                System.out.println("连接失败");
            }
        }

        String text = "clientMsg";
        ByteBuffer wrap = ByteBuffer.wrap(text.getBytes());
        socketChannel.write(wrap);


        while(true){
            selector.select();

            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            while(iterator.hasNext()){
                SelectionKey next = iterator.next();

                if(next.isReadable()){
                    NioServer.Buffers buffers = (NioServer.Buffers) next.attachment();
                    ByteBuffer readBuffer = buffers.getReadBuffer();
                    SocketChannel channel = (SocketChannel) next.channel();
                    channel.read(readBuffer);
                    readBuffer.flip();
                    System.out.println(new String(readBuffer.array()));
                }
            }
        }
    }
}
