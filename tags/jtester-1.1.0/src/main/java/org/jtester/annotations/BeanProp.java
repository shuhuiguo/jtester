package org.jtester.annotations;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@SuppressWarnings("rawtypes")
@Target({ FIELD, METHOD })
@Retention(RUNTIME)
public @interface BeanProp {
	String prop();

	String value() default "";;

	String ref() default "";

	Class impl() default BeanProp.class;
}
