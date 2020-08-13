### Bean容器的加载

#### main测试类

```java
 public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("beans.xml");
        Bean bean = applicationContext.getBean(Bean.class);
        System.out.println(bean);
    }
   
```



#### ClassPathXmlApplicationContext类

- application建立以后，可以通过refresh()进行重建，这样会将原来的application销毁，然后重新执行初始化

- 构造方法

  ```java
  public ClassPathXmlApplicationContext(String[] configLocations, boolean refresh, ApplicationContext parent)
  			throws BeansException {
  
  		super(parent);
  		//设置配置文件
  		setConfigLocations(configLocations);
  		if (refresh) {
  			//核心方法
  			refresh();
  		}
  	}
  ```





#### AbstractApplicationContext类

refresh方法

```java
	@Override
	public void refresh() throws BeansException, IllegalStateException {
		// 同步，防止你在初始化的过程中被打断
		synchronized (this.startupShutdownMonitor) {
			// 准备工作
			prepareRefresh();

			// 配置文件被拆分成Bean定义，注册在BeanFactory中。
			// 也就是Map<beanName, beanDefinition>
			ConfigurableListableBeanFactory beanFactory = obtainFreshBeanFactory();

			// Prepare the bean factory for use in this context.
			prepareBeanFactory(beanFactory);

			try {
				// 到这一步，所有的Bean加载和注册都已经完成，但是还没开始初始化
				// 如果实现了 BeanFactoryPostProcessor 接口，可以在初始化的时候做点事情
				postProcessBeanFactory(beanFactory);

				// Invoke factory processors registered as beans in the context.
				// 调用 BeanFactoryPostProcessor 各个实现类的 postProcessBeanFactory(factory) 方法
				invokeBeanFactoryPostProcessors(beanFactory);

				// Register bean processors that intercept bean creation.
				// 注册 BeanPostProcessor 的实现类，注意看和 BeanFactoryPostProcessor 的区别
				// 此接口两个方法: postProcessBeforeInitialization 和 postProcessAfterInitialization
				// 两个方法分别在 Bean 初始化之前和初始化之后得到执行。注意，到这里 Bean 还没初始化
				registerBeanPostProcessors(beanFactory);

				// Initialize message source for this context.
				// 初始化当前 ApplicationContext 的 MessageSource, 国际化等
				initMessageSource();

				// Initialize event multicaster for this context.
				// 初始化当前 ApplicationContext 的事件广播器
				initApplicationEventMulticaster();

				// Initialize other special beans in specific context subclasses.
				// 从方法名就可以知道，典型的模板方法(钩子方法)，
				// 具体的子类可以在这里初始化一些特殊的 Bean（在初始化 singleton beans 之前）
				onRefresh();

				// Check for listener beans and register them.
				// 注册监听，监听器需要实现 ApplicationListener 接口
				registerListeners();

				// Instantiate all remaining (non-lazy-init) singletons.
				// 初始化所有的singletons ， non-lazy除外
				// 主要方法
				finishBeanFactoryInitialization(beanFactory);

				// Last step: publish corresponding event.
				// 最后一步，发布事件广播
				finishRefresh();
			}

			catch (BeansException ex) {
				if (logger.isWarnEnabled()) {
					logger.warn("Exception encountered during context initialization - " +
							"cancelling refresh attempt: " + ex);
				}

				// Destroy already created singletons to avoid dangling resources.
				destroyBeans();

				// Reset 'active' flag.
				cancelRefresh(ex);

				// Propagate exception to caller.
				throw ex;
			}

			finally {
				// Reset common introspection caches in Spring's core, since we
				// might not ever need metadata for singleton beans anymore...
				resetCommonCaches();
			}
		}
	}
```



##### obtainFreshBeanFactory()方法

1、初始化 BeanFactory-》**DefaultListableBeanFactory.class**

```java
DefaultListableBeanFactory beanFactory = createBeanFactory();
```



2、定义工厂的属性: 是否允许 Bean 覆盖、是否允许循环引用

3、加载 Bean 到 BeanFactory 中 -》 **loadBeanDefinitions()方法**

​	3.1 读取xml配置文件，检查，解析

```java
preProcessXml(root); //钩子
parseBeanDefinitions(root, this.delegate); //主要代码：将信息放在Factory的相关map中
postProcessXml(root); //钩子
```



​	3.2 将xml配置文件的信息放到工厂的map里。

​		parseBeanDefinitions() => DefaultListableBeanFactory.class的**registerBeanDefinition()方法**

```java
	this.beanDefinitionMap.put(beanName, beanDefinition);
	// beanName的集合，和Map的key（beanName）对应
	this.beanDefinitionNames.add(beanName);
```

具体不细讲，简述一下过程，就是factory有几个map，这里将xml里读出来的对象以Map<beanName, beanDefinition>的形式存储。

以及一个list保存了所有的beanName。

beanDefinition只是一个类的描述而已，和我们需要的对象还差了一大截。

​	3.3 返回携带各种信息的factory



##### 	finishBeanFactoryInitialization()方法
根据factory初始化所有的singletons ， non-lazy除外 

1、初始化特殊类，不展开啦。

2、缓存。还是DefaultListableBeanFactory.class。把之前我们上面讲到的带beanName的List转为数组保存到frozenBeanDefinitionNames里。

```java
public void freezeConfiguration() {
		this.configurationFrozen = true;
		this.frozenBeanDefinitionNames = StringUtils.toStringArray(this.beanDefinitionNames);
	}
```

3、初始化。

​	3.1 拿到带beanName的List，遍历循环

```java
List<String> beanNames = new ArrayList<String>(this.beanDefinitionNames);

```

​	3.2 特殊类FactoryBean等特殊处理。

​	3.3 进入对象实例化。





























