1.1.9
o fix wiki文件和java编码方式时，oracle数据库CLOB类型数据的插入问题。
o 对JTester内置的@BeforeMethod @AfterMethod @BeforeClass @AfterClass相对于其它同配置的方法的运行顺序进行固化，不在依赖于TestNG的随机顺序。
o fix PropertyAccessor#getPropertyByOgnl方法bug，当ongl是以"."分割时，目标对象是Map对象时，可以根据ongl的一部分或全部来取值，
     并且取出来的值可以按照剩下的ongl表达式继续取值。
o fix DataMap插入oracle时间类型的字段时，不支持java.util.Date类型的问题，在插入数据时，根据数据库类型对字段做了转换。
o 升级testng和jmockit版本。
o 解决spring和cglib的一个bug：当重复加载释放spring容器时，导致的内存泄漏。

1.1.8 
o fix 列表中任一元素必须符合所有断言的断言 bug。
o 优化部分断言语法。
o 将reflectEqMap改名为propertyEqMap，更符合api要表述的意思，保留reflectEqMap一段时间。
o fix 列表元素hasItems的bug，如果列表中元素也是列表时，无法使用hasItems的错误。

1.1.7
o bugfix: 解决JTesterHookable的run(IHookCallBack callBack, ITestResult testResult)会将原始的反调异常吃掉，结果框架抛出一个另外一个异常的错误。
o 新增: 增加断言reflectionEqMap(int count, DataMap expected, EqMode... modes)，用于批量验证对象的属性。
o 新增: 增加断言propertyEq(String property, String expected, StringMode mode, StringMode... more)，
     用于验证属性是string的情况下，可以使用StringMode方式来比较。

1.1.6
o 修复dbfit对mysql支持，当ID是big int类型时，Long类型和BigInteger类型相等判断的bug。
o 增加程序查询数据库数据做断言时，queryWhere(String) 和queryWhere(DataMap)的api。
o DataMap增加put(String key,Object data1,Object data2,Object...more)的api，作为put(Stirng key,Object[] array)的快捷api。

1.1.5
o bugfix，字符串比较时在忽略空格的情况下，如果字符串是中文判断空格的bug。
o bugfix, 将json字符串反序列化为对象的情况下，如果map对象的属性值是string[]情况下的类型转换异常。
o bugfix, 数据库断言count(*)在oracle数据库下返回的是BigDecimal，报cast to Long异常。
o 增加程序准备数据时直接执行sql文件的功能。