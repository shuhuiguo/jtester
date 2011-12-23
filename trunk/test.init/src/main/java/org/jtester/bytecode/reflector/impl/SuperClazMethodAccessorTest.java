package org.jtester.bytecode.reflector.impl;

import java.util.HashMap;

import org.jtester.annotations.SpringApplicationContext;
import org.jtester.annotations.SpringBeanByName;
import org.jtester.bytecode.reflector.service.MyService;
import org.jtester.bytecode.reflector.service.MyServiceImpl;
import org.jtester.bytecode.reflector.service.MyServiceImpl.MyTestException;
import org.jtester.reflector.MethodAccessor;
import org.jtester.testng.JTester;
import org.testng.annotations.Test;

@SpringApplicationContext("org/jtester/bytecode/reflector/mybeans.xml")
@Test(groups = "jtester")
@SuppressWarnings({ "unchecked", "rawtypes" })
public class SuperClazMethodAccessorTest extends JTester {
	@SpringBeanByName
	private ExMyService myService;

	@Test
	public void protectedInvoked() {
		String ret = myService.protectedInvoked();
		want.string(ret).isEqualTo("protectedInvoked");
	}

	@Test
	public void privateInvoked() throws Exception {
		String ret = myService.privateInvoked("test");
		want.string(ret).isEqualTo("privateInvoked:test");
	}

	@Test
	public void testPrimitivePara() {
		int ret = myService.primitivePara(2, true);
		want.number(ret).isEqualTo(4);
	}

	@Test
	public void mapPara() {
		HashMap map = new HashMap();
		map.put(1, "test");
		int ret = myService.mapPara(map);
		want.number(ret).isEqualTo(1);
	}

	@Test(expectedExceptions = MyTestException.class)
	public void invokeException() {
		myService.invokeException();
	}

	public void reflectSetField() {
		reflector.setField(myService, "privateStr", "test");
		want.object(myService).propertyEq("privateStr", "test");

		Object o = reflector.getField(myService, "privateStr");
		want.object(o).isEqualTo("test");
	}

	public static interface ExMyService extends MyService {
		public String protectedInvoked();

		public String privateInvoked(String in) throws Exception;

		public int primitivePara(int i, boolean bl);

		public int mapPara(HashMap map);

		public void invokeException();
	}

	public static class ExMyServciceImpl extends MyServiceImpl implements ExMyService {
		public String protectedInvoked() {
			return super.protectedInvoked();
		}

		public String privateInvoked(String in) throws Exception {
			MethodAccessor<String> method = new MethodAccessor<String>(MyServiceImpl.class, "privateInvoked",
					String.class);
			return method.invoke(this, new Object[] { in });
		}

		public int primitivePara(int i, boolean bl) {
			Object ret = reflector.invoke(MyServiceImpl.class, this, "primitivePara", i, bl);
			return (Integer) ret;
		}

		public int mapPara(HashMap map) {
			Object o = reflector.invoke(MyServiceImpl.class, this, "mapPara", map);
			return (Integer) o;
		}

		public void invokeException() {
			reflector.invoke(MyServiceImpl.class, this, "invokeException");
		}
	}
}
