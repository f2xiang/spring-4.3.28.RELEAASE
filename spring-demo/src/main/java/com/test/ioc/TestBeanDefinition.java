package com.test.ioc;

import com.test.ioc.anno.Config;
import com.test.ioc.bean.Person;
import com.test.ioc.bean.Student;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.ChildBeanDefinition;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @Description: 
 * @Author: fengx
 * @CreateDate: 2020/8/21 18:48
 */
public class TestBeanDefinition {
    
    public static void main(String[] args) {
        //        testRootBeanDefinition();
//        testChildBeanDefinition();
//        testGenericBeanDefinition();
        testAnnotatedGenericBeanDefinition();
    }



    /**
     * RootBeanDefinition
     */
    public static void testRootBeanDefinition() {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        // bean的属性集合
        MutablePropertyValues pvs = new MutablePropertyValues();
        pvs.add("name", "zhangsan");
        pvs.add("age", "11");
        // BeanDefinition
        RootBeanDefinition rootBeanDefinition = new RootBeanDefinition(Person.class, null, pvs);
        // 把BeanDefinition注册到 工厂 的map， （beanName， BeanDefinition）
        ctx.registerBeanDefinition("person",rootBeanDefinition);
        ctx.refresh(); // 实例化
        Person bean = ctx.getBean(Person.class);
        System.out.println(bean);
    }


    /**
     * ChildBeanDefinition
     */
    public static void testChildBeanDefinition() {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();

        // 属性
        MutablePropertyValues pvs = new MutablePropertyValues();
        pvs.add("name", "zhangsan");
        pvs.add("age", "33");


        // 父
        RootBeanDefinition rootBeanDefinition = new RootBeanDefinition(Person.class, null, pvs);
        // 子
        ChildBeanDefinition childBeanDefinition = new ChildBeanDefinition("person");
        childBeanDefinition.getPropertyValues().add("school", "小学");
        childBeanDefinition.setBeanClass(Student.class);

        // 把BeanDefinition注册到 工厂 的map， （beanName， BeanDefinition）
        ctx.registerBeanDefinition("person",rootBeanDefinition);
        ctx.registerBeanDefinition("student",childBeanDefinition);
        ctx.refresh(); // 实例化
        Person person = ctx.getBean(Person.class);
        Student student = ctx.getBean(Student.class);
        System.out.println(person);
        System.out.println(student);

    }





    /**
     * GenericBeanDefinition: 集成了 ChildBeanDefinition 和 ChildBeanDefinition
     */
    public static void testGenericBeanDefinition () {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();

        // 属性
        MutablePropertyValues pvs = new MutablePropertyValues();
        pvs.add("name", "zhangsan");
        pvs.add("age", "33");


        // 父
        GenericBeanDefinition rootBeanDefinition = new GenericBeanDefinition ();
        rootBeanDefinition.setPropertyValues(pvs);
        rootBeanDefinition.setBeanClass(Person.class);

        // 子
        GenericBeanDefinition childBeanDefinition = new GenericBeanDefinition();
        childBeanDefinition.setParentName("person");
        childBeanDefinition.getPropertyValues().add("school", "小学");
        childBeanDefinition.setBeanClass(Student.class);

        // 把BeanDefinition注册到 工厂 的map， （beanName， BeanDefinition）
        ctx.registerBeanDefinition("person",rootBeanDefinition);
        ctx.registerBeanDefinition("student",childBeanDefinition);
        ctx.refresh(); // 实例化
        Person person = ctx.getBean(Person.class);
        Student student = ctx.getBean(Student.class);
        System.out.println(person);
        System.out.println(student);

    }



    /**
     * 注解相关：
     *  @Configuration -> AnnotatedGenericBeanDefinition
     *  @Bean  -> ConfigurationClassBeanDefinition
     *   @Service、@Controller、@Repository 以及 @Component -> ScannedGenericBeanDefinition
     */
    public static void testAnnotatedGenericBeanDefinition () {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(Config.class);
        BeanDefinition configDefinition= ctx.getBeanFactory().getBeanDefinition("config");
        BeanDefinition personDefinition= ctx.getBeanFactory().getBeanDefinition("person");

    }
}
