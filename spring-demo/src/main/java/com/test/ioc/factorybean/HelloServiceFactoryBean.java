package com.test.ioc.factorybean;

import com.test.ioc.service.HelloService;
import org.springframework.beans.factory.FactoryBean;

/**
 * @Description: HelloServiceFactoryBean工厂类
 * @Author: fengx
 * @CreateDate: 2020/8/28 17:16
 */
public class HelloServiceFactoryBean implements FactoryBean<HelloService> {

    @Override
    public HelloService getObject() throws Exception {
        System.out.println("初始化了");
        return new HelloService();
    }


    @Override
    public Class<?> getObjectType() {
        return HelloService.class;
    }

    @Override
    public boolean isSingleton() {
        return false;
    }




}
