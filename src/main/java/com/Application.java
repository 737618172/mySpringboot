package com;

import com.io.netty.websocket.NettyWebSocketServer;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.servlet.ServletException;

public class Application {

    public static void main(String[] args) throws ServletException {

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
//        new Thread(()->NettyWebSocketServer.run()).start();
        Object userDao = context.getBean("userDao");
        Boot.Run();
    }
}
