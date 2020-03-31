package com;

import com.io.netty.websocket.NettyWebSocketServer;
import com.spring.ProxyObject;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.servlet.ServletException;
import java.io.File;

public class Application {

    public static void main(String[] args) throws ServletException {

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
//        ProxyObject proxyObject = (ProxyObject) context.getBean("proxyObject");
//        String pp = proxyObject.pp();
//        System.out.println(pp);
//        new Thread(()->NettyWebSocketServer.run()).start();
        Boot.Run();
    }
}
