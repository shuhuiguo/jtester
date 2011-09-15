1.1.5
o bugfix，字符串比较时在忽略空格的情况下，如果字符串是中文判断空格的bug。
o bugfix, 将json字符串反序列化为对象的情况下，如果map对象的属性值是string[]情况下的类型转换异常。
o bugfix, 数据库断言count(*)在oracle数据库下返回的是BigDecimal，报cast to Long异常。
o 增加程序准备数据时直接执行sql文件的功能。