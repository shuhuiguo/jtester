package org.jtester.reflector;

import org.jtester.annotations.SpringApplicationContext;
import org.jtester.annotations.SpringBeanByName;
import org.jtester.reflector.service.MyService;
import org.jtester.reflector.service.MyServiceImpl;
import org.jtester.testng.JTester;
import org.testng.annotations.Test;

@SpringApplicationContext("org/jtester/reflector/mybeans-invoke.xml")
@Test(groups = "jtester")
public class SuperClazMethodAccessorTest_Invoke extends JTester {
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
