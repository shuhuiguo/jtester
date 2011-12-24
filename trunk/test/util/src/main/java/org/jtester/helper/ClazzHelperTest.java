package org.jtester.helper;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import org.jtester.IAssertion;
import org.jtester.beans.TestedClazz;
import org.jtester.beans.TestedIntf;
import org.jtester.junit.DataFrom;
import org.junit.Test;

@SuppressWarnings("rawtypes")
public class ClazzHelperTest implements IAssertion {

	@Test
	@DataFrom("provideClazzName")
	public void getPackFromClassName(String clazz, String pack) {
		want.string(ClazzHelper.getPackFromClassName(clazz)).isEqualTo(pack);
	}

	public static Object[][] provideClazzName() {
		return new String[][] { { "", "" }, { "EefErr", "" },
				{ "org.jtester.utility.ClazzUtilTest", "org.jtester.utility" } };
	}

	@Test
	@DataFrom("dataProvider_testGetPathFromPath")
	public void testGetPathFromPath(String clazName, String path) {
		String _path = ClazzHelper.getPathFromPath(clazName);
		want.string(_path).isEqualTo(path);
	}

	public static Object[][] dataProvider_testGetPathFromPath() {
		return new Object[][] { { "a.b.c.ImplClazz", "a/b/c" }, // <br>
				{ "ImplClazz", "" }, /** <br> **/
				{ ".ImplClazz", "" }
		/** <br> **/
		};
	}

	@Test
	public void testGetBytes() {
		byte[] bytes = ClazzHelper.getBytes(ClazzHelper.class);
		want.array(bytes).notNull().sizeGt(1);
	}

	@Test
	@DataFrom("proxy_types")
	public void testGetUnProxyType(Class type, Class expected) {
		Class actual = ClazzHelper.getUnProxyType(type);
		want.object(actual).isEqualTo(expected);
	}

	public static Object[][] proxy_types() {
		Object proxy = Proxy.newProxyInstance(ClazzHelperTest.class.getClassLoader(), new Class[] { TestedIntf.class },
				new InvocationHandler() {
					public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
						return null;
					}
				});
		return new Object[][] { { TestedClazz.class, TestedClazz.class },// <br>
				{ new TestedClazz() {
					{
					}
				}.getClass(), TestedClazz.class }, // <br>
				{ new TestedIntf() {
					{

					}
				}.getClass(), Object.class },// <br>
				{ proxy.getClass(), Object.class } // <br>
		};
	}
}