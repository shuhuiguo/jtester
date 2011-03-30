package org.jtester.module.tracer.jdbc;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.util.Calendar;

import org.jtester.hamcrest.reflection.ReflectionComparatorMode;
import org.jtester.module.tracer.jdbc.ProxyCallableStatement;
import org.jtester.testng.JTester;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

@Test(groups = "jtester")
public class ProxyCallableStatementTest extends JTester {
	ProxyCallableStatement proxy;

	@Test(dataProvider = "returnProxyCallableStatementMethod")
	public void testMethod(String methodName, Class<?>[] clazzes, Object[] paras, Object result) throws Exception {
		proxy = new ProxyCallableStatement(null, testStatementProxy(methodName, paras, result));

		Method method = ProxyCallableStatement.class.getMethod(methodName, clazzes);
		Object result2 = method.invoke(proxy, paras);
		want.object(result2).isEqualTo(result);
	}

	@DataProvider
	public Object[][] returnProxyCallableStatementMethod() {
		return new Object[][] {// <br>
				{ "registerOutParameter", new Class<?>[] { int.class, int.class }, new Object[] { 0, 0 }, null },
				{ "registerOutParameter", new Class<?>[] { int.class, int.class, int.class }, new Object[] { 0, 0, 0 },
						null },
				{ "wasNull", new Class<?>[] {}, new Object[] {}, true },

				{ "getString", new Class<?>[] { int.class }, new Object[] { 0 }, "test" },
				{ "getBoolean", new Class<?>[] { int.class }, new Object[] { 0 }, true },
				{ "getByte", new Class<?>[] { int.class }, new Object[] { 0 }, Byte.MAX_VALUE },
				{ "getShort", new Class<?>[] { int.class }, new Object[] { 0 }, (short) 0 },
				{ "getInt", new Class<?>[] { int.class }, new Object[] { 0 }, 4 },
				{ "getLong", new Class<?>[] { int.class }, new Object[] { 1 }, 4L },
				{ "getFloat", new Class<?>[] { int.class }, new Object[] { 1 }, 4f },
				{ "getDouble", new Class<?>[] { int.class }, new Object[] { 1 }, 4d },
				{ "getBigDecimal", new Class<?>[] { int.class, int.class }, new Object[] { 1, 1 },
						new BigDecimal("1.1") },
				{ "getBytes", new Class<?>[] { int.class }, new Object[] { 1 }, new byte[2] },
				{ "getDate", new Class<?>[] { int.class }, new Object[] { 0 }, null },
				{ "getTime", new Class<?>[] { int.class }, new Object[] { 0 }, null },
				{ "getTimestamp", new Class<?>[] { int.class }, new Object[] { 0 }, null },
				{ "getObject", new Class<?>[] { int.class }, new Object[] { 0 }, null },
				{ "getBigDecimal", new Class<?>[] { int.class }, new Object[] { 0 }, null },

				{ "getRef", new Class<?>[] { int.class }, new Object[] { 0 }, null },
				{ "getBlob", new Class<?>[] { int.class }, new Object[] { 0 }, null },
				{ "getClob", new Class<?>[] { int.class }, new Object[] { 0 }, null },
				{ "getArray", new Class<?>[] { int.class }, new Object[] { 0 }, null },
				{ "getDate", new Class<?>[] { int.class, Calendar.class }, new Object[] { 0, null }, null },
				{ "getTime", new Class<?>[] { int.class, Calendar.class }, new Object[] { 0, null }, null },
				{ "getTimestamp", new Class<?>[] { int.class, Calendar.class }, new Object[] { 0, null }, null },
				{ "registerOutParameter", new Class<?>[] { int.class, int.class, String.class },
						new Object[] { 0, 0, "" }, null },
//				{ "getInt", new Class<?>[] { int.class }, new Object[] { 0 }, null }, /** <br> */
//				{ "getURL", new Class<?>[] { String.class }, new Object[] { "" }, null }, /** <br> */
				{ "getURL", new Class<?>[] { String.class }, new Object[] { "" }, null } /** <br> */
		};
	}

	// { "getObject", new Class<?>[] { int.class, Map.class }, new Object[] { 0,
	// null }, null }, /** <br> */
	private CallableStatement testStatementProxy(final String methodName, final Object[] paras, final Object result) {
		return (CallableStatement) Proxy.newProxyInstance(this.getClass().getClassLoader(),
				new Class<?>[] { CallableStatement.class }, new InvocationHandler() {
					public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
						String name = method.getName();
						want.string(name).isEqualTo(methodName);
						want.array(paras).reflectionEq(args, ReflectionComparatorMode.IGNORE_DEFAULTS);
						return result;
					}
				});
	}
}
