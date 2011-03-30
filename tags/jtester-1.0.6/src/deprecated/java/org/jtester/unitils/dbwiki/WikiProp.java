package org.jtester.unitils.dbwiki;

import static java.lang.annotation.ElementType.METHOD;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
@Target( { METHOD })
public @interface WikiProp {
	public String name() default "";
}
