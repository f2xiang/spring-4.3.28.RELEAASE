package com.test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @Description: 
 * @Author: fengx
 * @CreateDate: 2020/8/5 20:25
 */
public class MainTest {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("beans.xml");
        Bean bean = applicationContext.getBean(Bean.class);
        System.out.println(bean);
    }
}
