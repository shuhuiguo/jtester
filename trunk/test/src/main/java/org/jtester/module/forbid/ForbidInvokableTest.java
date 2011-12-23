package org.jtester.module.forbid;

import java.util.Arrays;

import org.jtester.exception.ForbidCallException;
import org.jtester.module.core.ForbidModule;
import org.jtester.module.forbid.model.ForTesedForbidBeanFactory;
import org.jtester.module.forbid.model.ForTestedForbid;
import org.jtester.reflector.imposteriser.JTesterProxy;
import org.jtester.reflector.invokabel.ForbidInvokable;
import org.jtester.testng.JTester;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.testng.annotations.Test;

@Test
public class ForbidInvokableTest extends JTester {

	@Test(expectedExceptions = ForbidCallException.class)
	public void testInvoke() {
		ForTestedForbid bean = JTesterProxy.proxy(new ForbidInvokable(ForTestedForbid.class),
				ForTestedForbid.class.getName());
		bean.donothing();
	}

	@SuppressWarnings({ "unchecked" })
	public void testSpringStart() {

//		new MockUp<ForbidModule>() {
//			@Mock
//			public byte[] getForbidBytesByClass(Class clazz) {
//				want.object(clazz).isEqualTo(ForTesedForbidBeanFactory.class);
//				byte[] bytes = ClazzHelper.getBytes(ForTesedForbidBeanFactoryMock.class);
//
//				ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);
//				ClassVisitor cv = new ClassAdapter(cw) {
//					@Override
//					public void visit(final int version, final int access, final String name, final String signature,
//							final String superName, final String[] interfaces) {
//						cv.visit(version, access, "org/jtester/module/forbid/model/ForTesedForbidBeanFactory",
//								signature, superName, interfaces);
//					}
//				};
//				ClassReader cr = new ClassReader(bytes);
//				cr.accept(cv, ClassReader.SKIP_DEBUG);
//				final byte[] mockbytes = cw.toByteArray();
//				return mockbytes;
//			}
//		};

		ForbidModule module = new ForbidModule();
		reflector.setField(module, "forbids", Arrays.asList(ForTesedForbidBeanFactory.class));
		reflector.invoke(module, "afterInit");

		String[] locations = new String[] { "org/jtester/module/forbid/model/forbid-beans.xml" };
		AbstractApplicationContext context = new ClassPathXmlApplicationContext(locations, true, null);

		ForTestedForbid bean = (ForTestedForbid) context.getBean("forbidbean");
		bean.donothing();
		want.fail("这个测试无法运行到这里，初始化spring容器的时候就应该报错了。");
	}
}
