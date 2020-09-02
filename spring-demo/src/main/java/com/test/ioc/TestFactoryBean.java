package com.test.ioc;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @Description: FactoryBean
 * @Author: fengx
 * @CreateDate: 2020/8/28 17:15
 */
public class TestFactoryBean {

    public static void main(String[] args) {
//        testFactoryBean();
        testFactoryBean1();



    }

    /**
     * 从FactoryBean 里 拿到getObject的对象
     */
    private static void testFactoryBean() {
        // 容器启动完成并不会马上加载
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("ioc.xml");
        //  <bean id="helloService" class="com.test.ioc.factorybean.HelloServiceFactoryBean"></bean>
        // getBean才开始去加载这个对象，除非实现SmartFactoryBean接口并设置isEagerInit = true
        Object helloService = ctx.getBean("helloService");
        Object helloService1 = ctx.getBean("helloService");
        // 拿到了HelloService  而不是 HelloServiceFactoryBean
        System.out.println(helloService.getClass().toString());
    }


    /**
     * 获取FactoryBean 对象本身
     * &的妙用
     */
    private static void testFactoryBean1() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("ioc.xml");
        Object helloService = ctx.getBean("&helloService");
        // 拿到了HelloServiceFactoryBean
        System.out.println(helloService.getClass().toString());
    }


}
