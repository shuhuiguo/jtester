package org.jtester.unitils.dbfit;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Target({ TYPE, METHOD })
@Retention(RUNTIME)
public @interface DbFit {
	/**
	 * 单元测试前运行的wiki文件
	 * 
	 * @return
	 */
	String[] when() default {};

	/**
	 * 单元测试后校验的wiki文件
	 * 
	 * @return
	 */
	String[] then() default {};

	/**
	 * 设置fit wiki参数
	 * 
	 * @return
	 */
	FitVar[] vars() default {};

	/**
	 * 根据规则自动查找每个方法的dbfit文件
	 * 
	 * @return
	 */
	AUTO auto() default AUTO.DEFAULT;

	/**
	 * 在方法上自动查找when文件的规则
	 * 
	 * @return
	 */
	String whenMethod() default WHEN_METHOD_DEFAULT_WIKI;

	/**
	 * 在方法上自动查找then文件的规则
	 * 
	 * @return
	 */
	String thenMethod() default THEN_METHOD_DEFAULT_WIKI;

	/**
	 * 在类上自动查找when文件的规则<br>
	 * ${class} 是测试类的simpleClassName
	 * 
	 * @return
	 */
	String whenClass() default WHEN_CLAZZ_DEFAULT_WIKI;

	// /**
	// * 在类上自动查找then文件的规则<br>
	// * ${class} 是测试类的simpleClassName
	// *
	// * @return
	// */
	// String thenClass() default "data/${class}.then.wiki";

	/**
	 * 测试方法默认的when wiki文件命名<br>
	 * ${class} 是测试类的simpleClassName
	 */
	public final static String WHEN_METHOD_DEFAULT_WIKI = "data/${class}/${method}.when.wiki";

	/**
	 * 测试方法默认的then wiki文件命名<br>
	 * ${class} 是测试类的simpleClassName
	 */
	public final static String THEN_METHOD_DEFAULT_WIKI = "data/${class}/${method}.then.wiki";

	/**
	 * 测试类默认的when wiki文件名
	 */
	public final static String WHEN_CLAZZ_DEFAULT_WIKI = "data/${class}.wiki";

	public static enum AUTO {
		/**
		 * 不自动查找
		 */
		UN_AUTO, // <br>
		/**
		 * 自动查找
		 */
		AUTO, // <br>

		/**
		 * 默认方式<br>
		 * o 如果class级别定义了，每个方法默认继承class中的定义方式<br>
		 * o 如果class级别没有定义，则默认是自动查找的
		 */
		DEFAULT;
	}
}
