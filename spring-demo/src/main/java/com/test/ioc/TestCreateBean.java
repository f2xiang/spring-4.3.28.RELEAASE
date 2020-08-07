package com.test.ioc;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @Description: 
 * @Author: fengx
 * @CreateDate: 2020/8/7 13:34
 */
public class TestCreateBean {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("ioc.xml");
        Person bean = applicationContext.getBean(Person.class);
        System.out.println(bean);
    }
}
