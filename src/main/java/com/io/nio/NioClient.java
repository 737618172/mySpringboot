package com.io.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class NioClient {
    public static void main(String[] args) throws IOException {

        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.configureBlocking(false);
        Selector selector = Selector.open();
        int interestSet = SelectionKey.OP_READ | SelectionKey.OP_WRITE;

        socketChannel.register(selector, interestSet);
        socketChannel.connect(new InetSocketAddress(9000));

        while(true){
            int select = selector.select(500);
            if(select!=0){
                System.out.println(select);
            }

            Set<SelectionKey> keySet = selector.selectedKeys();
            Iterator<SelectionKey> it = keySet.iterator();
            while(it.hasNext()){
                SelectionKey next = it.next();
                it.remove();

                /*通过SelectionKey获取对应的通道*/
                NioServer.Buffers buffers = (NioServer.Buffers)next.attachment();
                ByteBuffer readBuffer = buffers.getReadBuffer();
                ByteBuffer writeBuffer = buffers.gerWriteBuffer();

                /*通过SelectionKey获取通道对应的缓冲区*/
                SocketChannel sc = (SocketChannel) next.channel();

                if(next.isReadable()){
                    /*从socket的读缓冲区读取到程序定义的缓冲区中*/
                    sc.read(readBuffer);
                    readBuffer.flip();
                    /*字节到utf8解码*/
                    /*显示接收到由服务器发送的信息*/
                    System.out.println(readBuffer);
                    readBuffer.clear();
                }

                if(next.isWritable()){
                    System.out.println("readable");
                    writeBuffer.put(("123" + "  " + "321").getBytes("UTF-8"));
                    writeBuffer.flip();
                    /*将程序定义的缓冲区中的内容写入到socket的写缓冲区中*/
                    sc.write(writeBuffer);
                    writeBuffer.clear();
                }
            }
        }

    }
}
