package org.jtester.reflector;

import org.jtester.IAssertion;
import org.jtester.IReflector;
import org.jtester.annotations.SpringApplicationContext;
import org.jtester.annotations.SpringBeanByName;
import org.jtester.beans.MyService;
import org.jtester.beans.MyServiceImpl;
import org.junit.Test;

@SpringApplicationContext("org/jtester/bytecode/reflector/mybeans-invoke.xml")
public class SuperClazMethodAccessorTest_Invoke implements IAssertion, IReflector {
	@SpringBeanByName
	private ExMyService myService;

	@Test
	public void privateInvoked() throws Throwable {
		String ret = myService.privateInvoked("test");
		want.string(ret).isEqualTo("privateInvoked:test");
	}

	@Test
	public void privateInvoked_nullPara() throws Throwable {
		String ret = myService.privateInvoked(null);
		want.string(ret).isEqualTo("privateInvoked:null");
	}

	public static interface ExMyService extends MyService {
		public String protectedInvoked();

		public String privateInvoked(String in) throws Throwable;
	}

	public static class ExMyServciceImpl extends MyServiceImpl implements ExMyService {
		public String protectedInvoked() {
			return super.protectedInvoked();
		}

		public String privateInvoked(String in) throws Throwable {
			return reflector.invoke(MyServiceImpl.class, this, "privateInvoked", in);
		}
	}
}
