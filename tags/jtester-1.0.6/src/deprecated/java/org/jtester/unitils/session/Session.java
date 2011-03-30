package org.jtester.unitils.session;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Target( { METHOD, TYPE })
@Retention(RUNTIME)
public @interface Session {
	boolean inSession() default true;

	String transactionBeanName() default "transactionManager";

	boolean commit() default true;
}
