package com.spring;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;

import java.util.Iterator;

@Component
public class MyBeanFactoryPostProcessor implements BeanFactoryPostProcessor {
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
//        BeanDefinition beanDefinition = configurableListableBeanFactory.getBeanDefinition("myFactoryBean");
//        System.out.println(beanDefinition.getBeanClassName());
        BeanDefinition userDao = configurableListableBeanFactory.getBeanDefinition("userDao");
        userDao.setInitMethodName("test2");
        System.out.println(userDao);
    }



}
