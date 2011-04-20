1.0.9
o 修复延时类断言wanted(Class)时，当实际断言值是null时，导致的NullPointer异常.

1.0.8
o 修复ORACLE字段映射到Map和PoJo时由于ORACLE字段是大写的导致camel name不符合习惯的问题。
o 修复Spring Bean注入到TestCase中字段类型不配置异常被吃掉的bug。
o 去除objensis jar的依赖。
o 增加模块加载时智能判断模块是否有效。

1.0.7 2011-03-25
o DbFit增加sql文件支持。
o JTester中支持直接执行sql语句，查询sql，并且映射到Map或PoJo的数据结构中。
o 重构了SpringBeanByName SpringBeanByType SpringApplicationContext的package名称。

1.0.6 2011-02-28
o 修复spring容器共享sqlMapClient时，ibatis版本间SqlMapConfigParser实现不一致导致的bug
o 给Module Listener增加一个事件点afterTestClass。并把spring容器释放从JTester基类中移到这个事件点来实现。
o 把Mockito.teardown()操作从afterTestTeardown移到事件点afterTestClass中，
      修复测试中使用@UsingMocksAndStubs时过早teardown mockit导致的错误。
o 修改spring初始化和close时的提示信息。

1.0.5 2011-02-18
o 增加cabar数据库类型数据类型支持
o 增加共享ibatis的sqlclient实例的特性
o 重构部分代码

1.0.4 2011-01-11
o 增加QA测试war包中内部接口的功能
o 增加自动配置spring bean的时候，可以指定bean init方法，方便开发测试类似ResoureceManager的service。
o 修改@SpringBeanFor的实现，使用beanFactory往容器中注入一个真正的bean实现，而不是在getBean的时候偷换实例。
o dbfit中mysql bigint字段类型的支持。
o 修正多线程状态下的测试事务的管理。
o 完善测试上下文状态的管理。
o 重构部分代码