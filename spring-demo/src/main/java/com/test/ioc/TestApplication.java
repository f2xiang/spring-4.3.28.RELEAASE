package com.test.ioc;

import com.test.Bean;
import com.test.ioc.bean.Person;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
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

//        testClassPathXmlApplicationContext();
       testXmlBeanFactory();
//        testDefaultListableBeanFactoryRegister();
//        testDefaultListableBeanFactory();
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
        // 通过preInstantiateSingletons可以提前注册 Bean
//        factory.preInstantiateSingletons();

        //第一次getBean的时候创建对象并缓存
        Bean bean = factory.getBean(Bean.class);
        //第2次getBean的时候直接读取缓存的对象
        Bean bean1 = factory.getBean(Bean.class);
    }


    /**
     * DefaultListableBeanFactory
     *
     * XmlBeanFactory 继承DefaultListableBeanFactory，就是将xml通过reader加载
     * 	public XmlBeanFactory(Resource resource, BeanFactory parentBeanFactory) throws BeansException {
         super(parentBeanFactory);
         this.reader.loadBeanDefinitions(resource);
       }
     */
    public static void testDefaultListableBeanFactory() {
        ClassPathResource res = new ClassPathResource("beans.xml");
        DefaultListableBeanFactory factory = new DefaultListableBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(factory);
        reader.loadBeanDefinitions(res);
        Bean bean = factory.getBean(Bean.class);
        System.out.println(bean);
    }

    /**
     * register
     */
    public static void testDefaultListableBeanFactoryRegister() {
        // 去掉解析的过程，手动给factory的map里加上一个beanDefinition对象
        DefaultListableBeanFactory factory = new DefaultListableBeanFactory();
        GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
        beanDefinition.setBeanClass(Person.class);
        MutablePropertyValues mv = new MutablePropertyValues();
        mv.addPropertyValue("name", "张三");
        mv.addPropertyValue("age", 11);
        beanDefinition.setPropertyValues(mv);
        factory.registerBeanDefinition("bean", beanDefinition);

        // 如果我们想要提前初始化
//        factory.preInstantiateSingletons();


        // 此时还没初始化对象
        // 在从工厂里根据beanDefinition拿出这个真实对象
        // getBean才初始化
        Object bean = factory.getBean("bean");
        System.out.println(bean);


        
    }

}
