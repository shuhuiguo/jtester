package org.jtester.module.helper;

import java.util.Arrays;
import java.util.List;

import mockit.Mock;

import org.jtester.IAssertion;
import org.jtester.helper.ConfigurationHelper;
import org.junit.Test;

public class ModulesLoaderTest implements IAssertion {
	/**
	 * 测试database.type未设置时,database和dbfit模块失效
	 */
	@Test
	public void testFilterModules() {
		new MockUp<ConfigurationHelper>() {
			@SuppressWarnings("unused")
			@Mock
			public String databaseType() {
				return null;
			}
		};
		List<String> list = reflector.invokeStatic(ModulesLoader.class, "filterModules",
				Arrays.asList("database", "dbfit", "jmock", "jmockit", "inject", "spring", "tracer"));
		want.collection(list).not(the.collection().hasItems("database")).sizeEq(4);
	}
}
