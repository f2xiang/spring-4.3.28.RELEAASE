package com.test.ioc.bean;
/**
 * @Description: 
 * @Author: fengx
 * @CreateDate: 2020/8/21 17:21
 */
public class Student  {
    private String name;
    private int age;

    private String school;

    public Student() {
        System.out.println("Student无惨构造被成功初始化");
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
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
        return "Student{" + "name='" + name + '\'' + ", age=" + age + ", school='" + school + '\'' + '}';
    }
}
