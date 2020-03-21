package com.spring;

import org.springframework.beans.factory.FactoryBean;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class MyFactoryBean implements FactoryBean {

    @Override
    public Object getObject() throws Exception {
        return Proxy.newProxyInstance(MyFactoryBean.class.getClassLoader(), new Class[]{ProxyObject.class}, new InvocationHandler() {
            @Override
            public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
                System.out.println("before");
                return method.invoke(this, objects);
            }
        });
    }


    @Override
    public Class<?> getObjectType() {
        return ProxyObject.class;
    }
}
