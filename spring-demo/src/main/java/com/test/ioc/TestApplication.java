package com.test.ioc;

import com.test.Bean;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;

/**
 * @Description: 
 * @Author: fengx
 * @CreateDate: 2020/8/7 13:34
 */
public class TestApplication {
    public static void main(String[] args) {

        testClassPathXmlApplicationContext();
//       testXmlBeanFactory();
    }

    /**
     * ClassPathXmlApplicationContext
     */
    public static void testClassPathXmlApplicationContext() {
        //初始化工厂,定义信息，并创建和缓存对象
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("beans.xml");
        //直接从缓存读取
        Bean bean = applicationContext.getBean(Bean.class);
        System.out.println(bean);
    }

    /**
     * XmlBeanFactory
     */
    public static void testXmlBeanFactory() {
        //初始化工厂,定义信息
        XmlBeanFactory factory = new XmlBeanFactory(new ClassPathResource("beans.xml"));
        //第一次getBean的时候创建对象并缓存
        Bean bean = factory.getBean(Bean.class);
        //第2次getBean的时候直接读取缓存的对象
        Bean bean1 = factory.getBean(Bean.class);
    }




}
