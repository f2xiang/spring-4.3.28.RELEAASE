package com.test.ioc;
/**
 * @Description: 
 * @Author: fengx
 * @CreateDate: 2020/8/7 13:35
 */
public class Person {
    private String name;
    private int age;

    public Person() {
        System.out.println("无惨构造被成功初始化");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Person{" + "name='" + name + '\'' + ", age=" + age + '}';
    }
}
