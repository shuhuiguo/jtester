package org.jtester.module.spring.annotations;

import static java.lang.annotation.ElementType.*;
import java.lang.annotation.Retention;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import java.lang.annotation.Target;

@Target({ TYPE, FIELD, METHOD })
@Retention(RUNTIME)
public @interface SpringContext {

	String[] value() default {};

	/**
	 * 忽略 NoSuchBeanDefinitionException
	 * 
	 * @return 是否忽略
	 */
	boolean ignoreNoSuchBean() default false;

	/**
	 * 是否全局共享
	 * 
	 * @return
	 */
	boolean share() default false;
}
