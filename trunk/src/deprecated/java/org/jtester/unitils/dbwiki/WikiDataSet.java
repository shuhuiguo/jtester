package org.jtester.unitils.dbwiki;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.unitils.dbunit.datasetfactory.DataSetFactory;
import org.unitils.dbunit.datasetloadstrategy.DataSetLoadStrategy;

@Target( { TYPE, METHOD })
@Retention(RUNTIME)
@Inherited
public @interface WikiDataSet {
	String[] value() default {};

	// String wikiProp() default "";

	Class<? extends DataSetFactory> factory() default DataSetFactory.class;

	Class<? extends DataSetLoadStrategy> loadStrategy() default DataSetLoadStrategy.class;
}
