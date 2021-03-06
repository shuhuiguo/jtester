1.1.4
o 调整ITableOp和InsertOp类的api设计。
         原 db.query(table)操作调整为 db.table(table).query()
         原db.queryToList(sql)操作调整为 db.query(sql)
o 增加在程序中批量制造数据的功能，支持数据生成策略的指定。
        目前支持 repeat(Object... datas), increase(from, step), random(Type) 3种内置的策略
        以及自定义策略
o 增强数据库类型的支持，oracle的BLOB和CLOB现在也可以通过wiki和程序方式进行插入和验证。
o 增强日期类型的断言，支持根据目录字符串的格式自行格式化实际的日期值，然后和目标进行比较。
                    例：want.date(date).eqByFormat("2011-09-06");
           want.date(date).eqByFormat("2011-09-06 12:54:36");
           want.date(date).eqByFormat("12:54:36");
o 增强对象属性比较时，如果属性是日期格式，可以和字符串直接进行比较。
                    例: want.object(myObj).propertyEq("myDate","2011-09-06");
           want.object(myObj).reflectEqMap(new HashMap(){
	           	{
	           		this.put("myDate","2011-09-06 12:54:36");
	           	}
           	});
o 支持插入数据时，某个字段的数据生成器可以使用已经设置的字段。
    例：db.table("tdd_user").insert(2, new DataMap() {
			{
				this.put("user_name", new String[] { "darui.wu", "jobs.he" });
				this.put("email", new DataGenerator() {
					@Override
					public Object generate(int index) {
						return value("user_name") + "@alibaba-inc.com";
					}
				});
			}
		});
o 插入数据时，支持json格式构造数据，例: db.table("tdd_user").insert("{'id':1,'first_name':'darui.wu'}");
		db.table("tdd_user").insert(new DataSet(){
			{
				data("{'id':1,'first_name':'darui.wu'}");
				data("{'id':2,'first_name':'jobs.he'}");
				data("{'id':3,'first_name':'martin.flow'}");
			}
		});

1.1.3
o fixed bug： JSON序列化数组类型（记录class的时候），输出的json结尾少了“}”。
o 根据QA和开发使用DbFit的反馈，新增了在程序中直接操作数据库和判断数据库状态的功能。
           db.clean(...), db.insert(...), db.query(...)等
     并且支持插入数据时，如果没有指定字段，可以根据该字段的数据库类型，自动填充默认值。
o 增加了spring自动注册（注入容器)时，可以指定properties的值，以及ref bean的属性。

1.1.2
o jtester自身提供json序列化和反序列化的功能，移除jtester对simple-json，xstream等jar包的依赖
o 增加了reflectionEqWithMap方法，方便指定多属性的反射比较。
o 重构了属性断言的实现代码。
o 将hamcrest核心类引入jtester工程中，移除对hamcrest-all的jar依赖，以及避免和junit4.4引入的hamcrest-1.1产生包冲突。


1.1.1
o 修复JTesterFitnesse直接执行sql语句 statment句柄没有close的bug。
o 增加反射直接构造对象空实例的方法（非通过构造函数实例化）。
o 修复oracle语句中将时间格式的:mm:ss当做变量的缺陷。
o 有很多case需要在spring启动前mock，
     在test class中增加@SpringInitMethod方法，支持spring启动前的mock工作。

1.1.0
o 升级testng到6.0.1,jmockit 到0.999.9版本，支持最新的testng插件。
o 增加query时mysql unsigned类型的支持。
o 增加@SpringBeanByName和@SpringBeanByType属性init，支持在测试中定义bean时可以
     像配置文件那样动态指定init-method方法。
o 修复jmockit中共享NonStrict只在第一次测试时记录，测试完后清空，后续测试失败的bug。
o 重构了反射调用部分的代码。

1.0.9
o 修复延时类断言wanted(Class)时，当实际断言值是null时，导致的NullPointer异常.
o 移除jmock功能模块，去掉jmock依赖。
o 每个测试完毕后，重新从spring容器中读取bean注入到测试类中，避免测试中bean的值被前一个测试重新赋值过。
o 自动注入spring bean时，实现类的默认构造函数是private或protected时，也允许定义bean。
o 给jmockit的expectation增加了any(class)和is(value)形式的快捷断言。
o 增加了Expecations when(api.do()).doDelegate()语法。

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