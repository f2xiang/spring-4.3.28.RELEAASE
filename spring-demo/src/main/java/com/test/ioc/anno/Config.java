package com.test.ioc.anno;

import com.test.ioc.bean.Person;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description: @Configuration 和  @Bean 的 解析过程
 * @Author: fengx
 * @CreateDate: 2020/8/21 19:04
 */

@Configuration
public class Config {

    @Bean
    public Person person() {
        return new Person();
    }
}
