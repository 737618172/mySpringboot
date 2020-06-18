package com.dao;


import com.entity.Param;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;


@Import(Param.class)
@Component
public class UserDao implements InitializingBean {

    public UserDao(){
        System.out.println("noArgs");
    }

    @PostConstruct
    public void postConstruct(){
        System.out.println("postConstruct------------------------------");
    }

    public void test2(){
        System.out.println(222);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("afterPropertiesSet");
    }
}
