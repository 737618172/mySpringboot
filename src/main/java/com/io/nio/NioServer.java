package com.io.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

public class NioServer {

    public static void main(String[] args) throws IOException {

        ServerSocketChannel channel = ServerSocketChannel.open();
        channel.configureBlocking(false);
//        ServerSocket socket = channel.socket();
        channel.bind(new InetSocketAddress(9000));
        Selector selector = Selector.open();
        SelectionKey register = channel.register(selector, SelectionKey.OP_ACCEPT);


        while (true) {
            int select = selector.select(1000);
            if (0 == select) {
                continue;
            }

            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectionKeys.iterator();

            while (iterator.hasNext()) {
                SelectionKey next = iterator.next();

                if (next.isAcceptable()) {
                    SocketChannel accept = channel.accept();
                    accept.configureBlocking(false);

                    int interestSet = SelectionKey.OP_READ /*| SelectionKey.OP_WRITE*/;
                    SelectionKey register1 = accept.register(selector, interestSet);
                    System.out.println("accept from " + accept.getRemoteAddress());
                }

                if (next.isReadable()) {
                    SocketChannel sc = (SocketChannel) next.channel();
                    ByteBuffer readBuffer = ByteBuffer.allocate(20);

                    try {
                        /*从底层socket读缓冲区中读入数据*/
                        sc.read(readBuffer);
                    } catch (Exception e) {
                        System.out.println(sc.getRemoteAddress() + "离线了");
                        next.cancel();
                        sc.close();
                    }
//                    readBuffer.flip();
                    String text = new String(readBuffer.array());
                    System.out.println(text);

                    Set<SelectionKey> keys = selector.keys();
                    for (SelectionKey s : keys) {
                        Channel channel1 = s.channel();
                        if (channel1 instanceof SocketChannel && channel1 != sc) {
                            ByteBuffer wrap = ByteBuffer.wrap(text.getBytes());
                            ((SocketChannel) channel1).write(wrap);
                        }
                    }
                }

                iterator.remove();
            }
        }
    }

    /*自定义Buffer类中包含读缓冲区和写缓冲区，用于注册通道时的附加对象*/
    public static class Buffers {

        ByteBuffer readBuffer;
        ByteBuffer writeBuffer;

        public Buffers(int readCapacity, int writeCapacity) {
            readBuffer = ByteBuffer.allocate(readCapacity);
            writeBuffer = ByteBuffer.allocate(writeCapacity);
        }

        public ByteBuffer getReadBuffer() {
            return readBuffer;
        }

        public ByteBuffer gerWriteBuffer() {
            return writeBuffer;
        }
    }
}
