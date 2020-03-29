package com.io.netty.discardserver;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

public class ServerHandler extends SimpleChannelInboundHandler<String> { // (1)

    public static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String o) throws Exception {
        Channel channel = ctx.channel();
        channelGroup.add(channel);
        for (Channel c : channelGroup) {
            if(channel != c)
                c.writeAndFlush("["+channel.remoteAddress()+ "]"+o+"\n");
//            else
//                c.writeAndFlush("[you]" + o + "\n");
        }
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        channelGroup.writeAndFlush(channel.remoteAddress()+"加入连接"+"\n");
        channelGroup.add(channel);
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        channelGroup.remove(channel);
        for(Channel c:channelGroup){
            c.writeAndFlush(channel.remoteAddress()+"断开连接");
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception { // (5)
        Channel incoming = ctx.channel();
        System.out.println("客户端:"+incoming.remoteAddress()+"在线");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception { // (6)
        Channel incoming = ctx.channel();
        System.out.println("客户端:"+incoming.remoteAddress()+"掉线");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) { // (4)
        // 当出现异常就关闭连接
        Channel channel = ctx.channel();
        channelGroup.remove(channel);
        System.out.println(channel.remoteAddress()+"断开连接");
        for(Channel c:channelGroup){
            c.writeAndFlush(channel.remoteAddress()+"断开连接");
        }
        ctx.close();
    }

}
