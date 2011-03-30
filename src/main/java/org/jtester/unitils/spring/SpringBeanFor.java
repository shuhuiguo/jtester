package org.jtester.unitils.spring;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * auto bean inject 版的@SpringBeanByName和@SpringBean<br>
 * o value 指定bean的具体实现类<br>
 * o bean 显式指定bean name，如果为空则取field的名称
 * 
 * @author darui.wudr
 * 
 */
@Target(FIELD)
@Retention(RUNTIME)
public @interface SpringBeanFor {
	/**
	 * spring bean name,如果为空则取字段名称
	 * 
	 * @return
	 */
	String value() default "";
}
