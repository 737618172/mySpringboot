package com.io.netty.websocket;

import com.io.netty.http.HttpServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;

public class NettyWebSocketServer {


    public static void main(String[] args) {
        EventLoopGroup bossGroup = null;
        EventLoopGroup workerGroup = null;
        try {
            bossGroup = new NioEventLoopGroup(1);
            workerGroup = new NioEventLoopGroup();
            ServerBootstrap boot = new ServerBootstrap();
            boot.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast( new HttpServerCodec());
                            pipeline.addLast(new HttpObjectAggregator(8192));
                            pipeline.addLast(new WebSocketServerProtocolHandler("/"));
                            pipeline.addLast(new WebSocketHandler());
                        }
                    });

            System.out.println("服务器启动");
            // start
            ChannelFuture future = boot.bind(8030).sync();
            future.channel().closeFuture().sync();
            System.out.println("服务器关闭");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // shutdown
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
